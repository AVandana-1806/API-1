package gov.ca.cwds.es.live;

import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_ADDR;
import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_AKA;
import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_CASE;
import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_CLI;
import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_CLI_ADDR;
import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_CSEC;
import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_ETHNIC;
import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_PLACE_ADDR;
import static gov.ca.cwds.es.live.LiveElasticClientSQL.SEL_SAFETY;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.std.ApiMarker;

/**
 * Originally from Neutron/jobs {@link AtomLoadStepHandler} for People Summary index, initial load.
 * 
 * <p>
 * Loads {@link RawClient} and {@link PlacementHomeAddress}, normalizes to {@link ReplicatedClient}.
 * </p>
 * 
 * <p>
 * NOT thread safe! Each instance of this class should operate in its own thread.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"findsecbugs:SQL_INJECTION_JDBC", "squid:S2095"})
public class LiveElasticClientHandler implements ApiMarker, AtomLoadStepHandler<ReplicatedClient> {

  private static final long serialVersionUID = 1L;

  protected static final Logger LOGGER = LoggerFactory.getLogger(LiveElasticClientHandler.class);

  public static final int LG_SZ = 10;
  public static final int LOAD_SIZE = 13;

  protected static final int TFO = TYPE_FORWARD_ONLY;
  protected static final int CRO = CONCUR_READ_ONLY;

  /**
   * Let queries run -- until it's time to give up. Default to 2 minutes.
   */
  protected static final int QUERY_TIMEOUT_IN_SECONDS = 120;

  /**
   * Default fetch size for Hibernate and JDBC. Pull records in bulk to minimize network
   * round-trips.
   */
  protected static final int FETCH_SIZE = 500;

  protected final CaresInterruptibleOperation monitor;

  protected final String[] keyList;

  protected boolean doneHandlerRetrieve = false;

  /**
   * key = client id. Single thread, non-thread-safe containers OK.
   */
  protected transient Map<String, PlacementHomeAddress> placementHomeAddresses = new HashMap<>(11);

  /**
   * key = client id. Single thread, non-thread-safe containers OK.
   */
  protected transient Map<String, RawClient> rawClients = new HashMap<>(LOAD_SIZE);

  /**
   * key = client id. Single thread, non-thread-safe containers OK.
   */
  protected transient Map<String, ReplicatedClient> normalized = new HashMap<>(LOAD_SIZE);

  public LiveElasticClientHandler(CaresInterruptibleOperation interruptible, String[] keyList) {
    this.monitor = interruptible;
    this.keyList = keyList;
  }

  // =================================
  // Search, the next generation.
  // =================================

  protected void optimizeStatement(final Statement stmt) throws SQLException {
    stmt.setMaxRows(0);
    stmt.setQueryTimeout(QUERY_TIMEOUT_IN_SECONDS);
    stmt.setFetchSize(FETCH_SIZE);
  }

  protected void read(final PreparedStatement stmt, Consumer<ResultSet> consumer) {
    LOGGER.trace("read(): begin");
    try {
      optimizeStatement(stmt);
      // Close ResultSet for driver stability, despite the JDBC standard (i.e., close parent
      // Statement, not child ResultSet). The DB2 driver gets confused, if you just close parent
      // statement and session without closing the result set.
      try (final ResultSet rs = stmt.executeQuery()) {
        consumer.accept(rs);
      } finally {
        // Auto-close result set.
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "SELECT FAILED! {}", e.getMessage(), e);
    }

    LOGGER.trace("read(): done");
  }

  protected void read(final Statement stmt, String sql, Consumer<ResultSet> consumer) {
    LOGGER.trace("read(): begin");
    try {
      optimizeStatement(stmt);
      try (final ResultSet rs = stmt.executeQuery(sql)) {
        consumer.accept(rs);
      } finally {
        // Auto-close result set.
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "SELECT FAILED! {}", e.getMessage(), e);
    }

    LOGGER.trace("read(): done");
  }

  protected void readClient(final ResultSet rs) {
    int counter = 0;
    RawClient c = null;

    try {
      while (monitor.isRunning() && rs.next() && (c = new RawClient().read(rs)) != null) {
        rawClients.put(c.getCltId(), c);
        CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "client");
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ CLIENT! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} client records.", counter);
  }

  protected void readClientAddress(final ResultSet rs) {
    LOGGER.trace("readClientAddress(): begin");
    int counter = 0;
    RawClient c = null;
    RawClientAddress cla = null;

    try {
      while (monitor.isRunning() && rs.next() && (cla = new RawClientAddress().read(rs)) != null) {
        c = rawClients.get(cla.getCltId());
        if (c != null) {
          c.addClientAddress(cla);
          CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "client address");
        } else {
          LOGGER.warn("ORPHAN CLIENT ADDRESS! id: {}, client: {}", cla.getClaId(), cla.getCltId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ CLIENT ADDRESS! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} client address records.", counter);
  }

  protected void readAddress(final ResultSet rs) {
    LOGGER.trace("readAddress(): begin");
    int counter = 0;
    RawAddress adr = null;
    RawClient c = null;

    try {
      while (monitor.isRunning() && rs.next() && (adr = new RawAddress().read(rs)) != null) {
        c = rawClients.get(adr.getCltId());
        if (c != null) {
          final Map<String, RawClientAddress> cla = c.getClientAddress();
          final String claKey = adr.getClaId();
          if (cla != null && StringUtils.isNotEmpty(claKey) && cla.containsKey(claKey)) {
            cla.get(claKey).setAddress(adr);
          } else {
            LOGGER.warn("ORPHAN ADDRESS! client: {}, client address: {}, address: {}",
                adr.getCltId(), claKey, adr.getAdrId());
          }
          CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "address");
        } else {
          LOGGER.warn("NO CLIENT FOR ADDRESS! client: {}, id: {}", adr.getCltId(), adr.getAdrId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ ADDRESS! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} address records.", counter);
  }

  protected void readClientCounty(final ResultSet rs) {
    LOGGER.trace("readClientCounty(): begin");
    int counter = 0;
    RawClient c = null;
    RawClientCounty cc = null;

    try {
      while (monitor.isRunning() && rs.next() && (cc = new RawClientCounty().read(rs)) != null) {
        c = rawClients.get(cc.getCltId());
        if (c != null) {
          c.addClientCounty(cc);
          CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "client county");
        } else {
          LOGGER.warn("ORPHAN CLIENT COUNTY! id: {}, client: {}", cc.getCltId(), cc.getCltId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ CLIENT COUNTY! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} client county records.", counter);
  }

  protected void readAka(final ResultSet rs) {
    LOGGER.trace("readAka(): begin");
    int counter = 0;
    RawClient c = null;
    RawAka aka = null;

    try {
      while (monitor.isRunning() && rs.next() && (aka = new RawAka().read(rs)) != null) {
        c = rawClients.get(aka.getCltId());
        if (c != null) {
          c.addAka(aka);
          CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "aka");
        } else {
          LOGGER.warn("ORPHAN AKA! id: {}, client: {}", aka.getAkaId(), aka.getCltId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ AKA! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} aka records.", counter);
  }

  protected void readCase(final ResultSet rs) {
    LOGGER.trace("readCase(): begin");
    int counter = 0;
    RawClient c = null;
    RawCase cas = null;

    try {
      while (monitor.isRunning() && rs.next() && (cas = new RawCase().read(rs)) != null) {
        c = rawClients.get(cas.getCltId());
        if (c != null) {
          c.addCase(cas);
          CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "case");
        } else {
          LOGGER.warn("ORPHAN CASE! id: {}, client: {}", cas.getOpenCaseId(), cas.getCltId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ CASE! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} case records.", counter);
  }

  protected void readCsec(final ResultSet rs) {
    LOGGER.trace("readCsec(): begin");
    int counter = 0;
    RawClient c = null;
    RawCsec csec = null;

    try {
      while (monitor.isRunning() && rs.next() && (csec = new RawCsec().read(rs)) != null) {
        c = rawClients.get(csec.getCltId());
        if (c != null) {
          c.addCsec(csec);
          CaresLog.logEvery(LOGGER, 5, ++counter, "read", "csec");
        } else {
          LOGGER.warn("ORPHAN CSEC! id: {}, client: {}", csec.getCsecId(), csec.getCltId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ CSEC! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} CSEC records.", counter);
  }

  protected void readEthnicity(final ResultSet rs) {
    LOGGER.trace("readEthnicity(): begin");
    int counter = 0;
    RawClient c = null;
    RawEthnicity eth = null;

    try {
      while (monitor.isRunning() && rs.next() && (eth = new RawEthnicity().read(rs)) != null) {
        c = rawClients.get(eth.getCltId());
        if (c != null) {
          c.addEthnicity(eth);
          CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "ethnicity");
        } else {
          LOGGER.warn("ORPHAN ETHNICITY! id: {}, client: {}", eth.getClientEthnicityId(),
              eth.getCltId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ ETHNICITY! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} ethnicity records.", counter);
  }

  protected void readSafetyAlert(final ResultSet rs) {
    LOGGER.trace("readSafetyAlert(): begin");
    int counter = 0;
    RawClient c = null;
    RawSafetyAlert saf = null;

    try {
      while (monitor.isRunning() && rs.next() && (saf = new RawSafetyAlert().read(rs)) != null) {
        c = rawClients.get(saf.getCltId());
        if (c != null) {
          c.addSafetyAlert(saf);
          CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "safety");
        } else {
          LOGGER.warn("ORPHAN SAFETY ALERT! id: {}, client: {}", saf.getSafetyAlertId(),
              saf.getCltId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ SAFETY ALERT! {}", e.getMessage(), e);
    }

    LOGGER.debug("Fetched{} safety alert records.", counter);
  }

  protected boolean isInitialLoad() {
    return true;
  }

  protected String getCurrentSchema() {
    // return dao.getSessionFactory().getProperties().get("hibernate.default_schema");
    return System.getProperty("DB_CMS_SCHEMA");
  }

  protected PreparedStatement prepDate(Connection con, String sql) throws SQLException {
    final String strCurTime = LiveElasticDateHelper.makeTimestampStringLookBack(new Date());
    final String newSql =
        sql.replaceAll("TGT_DT", strCurTime).replaceAll("LAST_RUN_DATE", strCurTime);
    return prep(con, newSql);
  }

  protected PreparedStatement prep(Connection con, String sql) throws SQLException {
    con.setSchema(getCurrentSchema());
    final PreparedStatement ret = con.prepareStatement(sql, TFO, CRO);
    final int maxSize = keyList.length;
    final int numParams = StringUtils.countMatches(sql, '?');
    LOGGER.trace("prep(): maxSize: {}, numParams: {}", maxSize, numParams);

    String key;
    for (int i = 1; i <= numParams; i++) {
      key = i <= maxSize ? keyList[i - 1] : "0";
      LOGGER.trace("set key: {}, position: {}", key, i);
      ret.setString(i, key);
    }

    return ret;
  }

  /**
   * {@inheritDoc}
   * 
   * <p>
   * Read placement home addresses per rule R-02294, Client Abstract Most Recent Address.
   * </p>
   * 
   * <p>
   * Commit more often by re-inserting client id's into GT_ID.
   * </p>
   */
  @Override
  public void handleMain(Connection con) throws SQLException {
    LOGGER.trace("begin");
    try (final PreparedStatement stmtClient = prep(con, SEL_CLI);
        final PreparedStatement stmtCliAddr = prep(con, SEL_CLI_ADDR);
        final PreparedStatement stmtAddress = prep(con, SEL_ADDR);
        final PreparedStatement stmtAka = prep(con, SEL_AKA);
        final PreparedStatement stmtCase = prep(con, SEL_CASE);
        final PreparedStatement stmtCsec = prep(con, SEL_CSEC);
        final PreparedStatement stmtEthnicity = prep(con, SEL_ETHNIC);
        final PreparedStatement stmtSafety = prep(con, SEL_SAFETY);
        final PreparedStatement stmtPlcmntAddr = prepDate(con, SEL_PLACE_ADDR)) {
      LOGGER.debug("Read client");
      read(stmtClient, rs -> readClient(rs));

      // SNAP-735: missing addresses.
      LOGGER.debug("Read client address");
      read(stmtCliAddr, rs -> readClientAddress(rs));

      LOGGER.debug("Read address");
      read(stmtAddress, rs -> readAddress(rs));

      LOGGER.debug("Read aka");
      read(stmtAka, rs -> readAka(rs));
      con.commit(); // free db resources

      LOGGER.debug("Read case");
      read(stmtCase, rs -> readCase(rs));

      LOGGER.debug("Read csec");
      read(stmtCsec, rs -> readCsec(rs));

      LOGGER.debug("Read ethnicity");
      read(stmtEthnicity, rs -> readEthnicity(rs));

      LOGGER.debug("Read safety alert");
      read(stmtSafety, rs -> readSafetyAlert(rs));

      LOGGER.debug("Read placement home address");
      readPlacementAddress(stmtPlcmntAddr);

      // Don't need for live clients but may need them for "live" search results.
      // Besides, the county client table lives in the replicated schema.
      // final PreparedStatement stmtCliCnty = prepReplicated(con, SEL_CLI_COUNTY);
      // LOGGER.info("Read client county");
      // read(stmtCliCnty, rs -> readClientCounty(rs));

      con.commit(); // free db resources. Make DBA's happy.
    } catch (Exception e) {
      LOGGER.error("FAILED TO RETRIEVE CLIENTS!", e);

      try {
        monitor.fail();
        con.rollback();
      } catch (Exception e2) {
        LOGGER.warn("NESTED ROLLBACK EXCEPTION!", e2);
      }
      throw CaresLog.runtime(LOGGER, e, "MAIN JDBC FAILED! {}", e.getMessage(), e);
    }

    LOGGER.trace("DONE");
  }

  protected void mapReplicatedClient(PlacementHomeAddress pha) {
    if (normalized.containsKey(pha.getClientId())) {
      final ReplicatedClient rc = normalized.get(pha.getClientId());
      rc.setActivePlacementHomeAddress(pha);
    } else {
      // WARNING: last chg: if the client wasn't picked up from the view, then it's not here.
      LOGGER.warn("Client id for placement home address NOT FOUND! client id: {}",
          pha.getClientId());
    }
  }

  @Override
  public List<ElasticSearchPerson> handleJdbcDone() {
    final RawToEsConverter conv = new RawToEsConverter();
    rawClients.values().stream().map(r -> r.normalize(conv))
        .forEach(c -> normalized.put(c.getId(), c));
    rawClients.clear(); // free memory
    LOGGER.trace("normalized: {}", normalized.size());

    // Merge placement home addresses.
    placementHomeAddresses.values().stream().forEach(this::mapReplicatedClient);

    // Stream to JSON and return.
    return normalized.values().stream().map(LiveElasticTransformer::buildElasticSearchPerson)
        .collect(Collectors.toList());
  }

  @Override
  public void handleFinish() {
    doneThreadRetrieve();
    clear();
  }

  @Override
  public List<ReplicatedClient> getNormalizedObjects() {
    return normalized.values().stream().collect(Collectors.toList());
  }

  public void addAll(Collection<ReplicatedClient> collection) {
    if (!collection.isEmpty()) {
      if (normalized.size() < collection.size()) {
        this.normalized = new HashMap<>(collection.size());
      }
      collection.stream().forEach(c -> normalized.put(c.getId(), c));
    }
  }

  /**
   * Release unneeded heap memory early and often.
   */
  protected void clear() {
    LOGGER.trace("clear containers");
    normalized.clear();
    rawClients.clear();
    placementHomeAddresses.clear();
  }

  protected void prepPlacementClients(final PreparedStatement stmt, final Pair<String, String> p)
      throws SQLException {
    optimizeStatement(stmt);
    final int countInsClient = stmt.executeUpdate();
    LOGGER.info("Prepped placement home clients: {}", countInsClient);
  }

  protected void readPlacementAddress(final PreparedStatement stmt) throws SQLException {
    optimizeStatement(stmt);
    int cntr = 0;
    PlacementHomeAddress pha;

    // SNAP-709: Connection is closed. ERRORCODE=-4470, SQLSTATE=08003.
    // According to the JDBC specification, you shouldn't close the ResultSet; closing its parent
    // statement should close the result set.
    try (final ResultSet rs = stmt.executeQuery()) {
      while (monitor.isRunning() && rs.next()) {
        CaresLog.logEvery(LOGGER, ++cntr, "Placement homes fetched", "recs");
        pha = new PlacementHomeAddress(rs);
        placementHomeAddresses.put(pha.getClientId(), pha);
      }
    } finally {
      // Close result set.
    }

    LOGGER.info("Placement home count: {}", placementHomeAddresses.size());
  }

  public Map<String, ReplicatedClient> getNormalized() {
    return normalized;
  }

  public boolean isDoneHandlerRetrieve() {
    return doneHandlerRetrieve;
  }

  protected void doneThreadRetrieve() {
    this.doneHandlerRetrieve = true;
  }

}
