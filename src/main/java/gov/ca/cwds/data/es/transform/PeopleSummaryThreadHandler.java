package gov.ca.cwds.data.es.transform;

import static gov.ca.cwds.data.es.transform.ClientSQLResource.INS_CLI_DUMMY;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.INS_CLI_LST_CHG;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_ADDR;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_AKA;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_CASE;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_CLI;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_CLI_ADDR;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_CLI_COUNTY;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_CSEC;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_ETHNIC;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_PLACE_ADDR;
import static gov.ca.cwds.data.es.transform.ClientSQLResource.SEL_SAFETY;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@SuppressWarnings({"findsecbugs:SQL_INJECTION_JDBC"})
public class PeopleSummaryThreadHandler
    implements ApiMarker, AtomLoadStepHandler<ReplicatedClient> {

  private static final long serialVersionUID = 1L;

  protected static final Logger LOGGER = LoggerFactory.getLogger(PeopleSummaryThreadHandler.class);

  public static final int LG_SZ = 10;
  public static final int FULL_LOAD_SIZE = 13;

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

  private final CaresInterruptibleOperation rocket;

  protected boolean doneHandlerRetrieve = false;

  protected transient Set<String> deletionResults = new HashSet<>();

  /**
   * key = client id. Single thread, non-thread-safe containers OK.
   */
  protected transient Map<String, PlacementHomeAddress> placementHomeAddresses = new HashMap<>(11);

  /**
   * key = client id. Single thread, non-thread-safe containers OK.
   */
  protected transient Map<String, RawClient> rawClients = new HashMap<>(FULL_LOAD_SIZE);

  /**
   * key = client id. Single thread, non-thread-safe containers OK.
   */
  protected transient Map<String, ReplicatedClient> normalized = new HashMap<>(FULL_LOAD_SIZE);

  public PeopleSummaryThreadHandler(CaresInterruptibleOperation rocket) {
    this.rocket = rocket;
  }

  // =================================
  // Search, the next generation.
  // =================================

  protected void read(final PreparedStatement stmt, Consumer<ResultSet> consumer) {
    LOGGER.trace("read(): begin");
    try {
      stmt.setMaxRows(0);
      stmt.setQueryTimeout(QUERY_TIMEOUT_IN_SECONDS);
      stmt.setFetchSize(FETCH_SIZE);

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

  protected <T extends ClientReference> void readAny(final ResultSet rs,
      LiveElasticJdbcReader<T> reader, BiConsumer<ClientReference, T> organizer, String msg) {
    LOGGER.trace("readAny(): begin");
    int counter = 0;
    RawClient c = null;
    T t;

    try {
      while (rocket.isRunning() && rs.next() && (t = reader.read(rs)) != null) {
        // Find associated raw client, if any, and link.
        c = rawClients.get(t.getCltId());
        organizer.accept(c, t);
        CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", msg);
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ DATA! {}", e.getMessage(), e);
    }

    LOGGER.debug("{} {} recs retrieved", msg, counter);
  }

  protected void readClient(final ResultSet rs) {
    LOGGER.trace("readClient(): begin");
    int counter = 0;
    RawClient c = null;

    try {
      while (rocket.isRunning() && rs.next() && (c = new RawClient().read(rs)) != null) {
        rawClients.put(c.getCltId(), c);
        CaresLog.logEvery(LOGGER, LG_SZ, ++counter, "read", "client");
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ CLIENT! {}", e.getMessage(), e);
    }

    LOGGER.info("Retrieved {} client records.", counter);
  }

  protected void readClientAddress(final ResultSet rs) {
    LOGGER.trace("readClientAddress(): begin");
    int counter = 0;
    RawClient c = null;
    RawClientAddress cla = null;

    try {
      while (rocket.isRunning() && rs.next() && (cla = new RawClientAddress().read(rs)) != null) {
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

    LOGGER.info("Retrieved {} client address records.", counter);
  }

  protected void readAddress(final ResultSet rs) {
    LOGGER.trace("readAddress(): begin");
    int counter = 0;
    RawAddress adr = null;
    RawClient c = null;

    try {
      while (rocket.isRunning() && rs.next() && (adr = new RawAddress().read(rs)) != null) {
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

    LOGGER.info("Retrieved {} address records.", counter);
  }

  protected void readClientCounty(final ResultSet rs) {
    LOGGER.trace("readClientCounty(): begin");
    int counter = 0;
    RawClient c = null;
    RawClientCounty cc = null;

    try {
      while (rocket.isRunning() && rs.next() && (cc = new RawClientCounty().read(rs)) != null) {
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

    LOGGER.info("Retrieved {} client county records.", counter);
  }

  protected void readAka(final ResultSet rs) {
    LOGGER.trace("readAka(): begin");
    int counter = 0;
    RawClient c = null;
    RawAka aka = null;

    try {
      while (rocket.isRunning() && rs.next() && (aka = new RawAka().read(rs)) != null) {
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

    LOGGER.info("Retrieved {} aka records.", counter);
  }

  protected void readCase(final ResultSet rs) {
    LOGGER.trace("readCase(): begin");
    int counter = 0;
    RawClient c = null;
    RawCase cas = null;

    try {
      while (rocket.isRunning() && rs.next() && (cas = new RawCase().read(rs)) != null) {
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

    LOGGER.info("Retrieved {} case records.", counter);
  }

  protected void readCsec(final ResultSet rs) {
    LOGGER.trace("readCsec(): begin");
    int counter = 0;
    RawClient c = null;
    RawCsec csec = null;

    try {
      while (rocket.isRunning() && rs.next() && (csec = new RawCsec().read(rs)) != null) {
        c = rawClients.get(csec.getCltId());
        if (c != null) {
          c.addCsec(csec);
          CaresLog.logEvery(LOGGER, 250, ++counter, "read", "csec");
        } else {
          LOGGER.warn("ORPHAN CSEC! id: {}, client: {}", csec.getCsecId(), csec.getCltId());
        }
      }
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO READ CSEC! {}", e.getMessage(), e);
    }

    LOGGER.info("Retrieved {} CSEC records.", counter);
  }

  protected void readEthnicity(final ResultSet rs) {
    LOGGER.trace("readEthnicity(): begin");
    int counter = 0;
    RawClient c = null;
    RawEthnicity eth = null;

    try {
      while (rocket.isRunning() && rs.next() && (eth = new RawEthnicity().read(rs)) != null) {
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

    LOGGER.info("Retrieved {} ethnicity records.", counter);
  }

  protected void readSafetyAlert(final ResultSet rs) {
    LOGGER.trace("readSafetyAlert(): begin");
    int counter = 0;
    RawClient c = null;
    RawSafetyAlert saf = null;

    try {
      while (rocket.isRunning() && rs.next() && (saf = new RawSafetyAlert().read(rs)) != null) {
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

    LOGGER.info("Retrieved {} safety alert records.", counter);
  }

  protected void loadClientRange(Connection con, final PreparedStatement stmtInsClient,
      Pair<String, String> range) throws SQLException {
    LOGGER.trace("loadClientRange(): begin");
    con.commit(); // free db resources

    // Initial Load client ranges.
    try {
      stmtInsClient.setString(1, range.getLeft());
      stmtInsClient.setString(2, range.getRight());
    } catch (Exception e) {
      LOGGER.trace("loadClientRange(): FAILED TO SET RANGES. Last change mode?", e);
    }

    final int clientCount = stmtInsClient.executeUpdate();
    LOGGER.info("Client count: {}", clientCount);
  }

  protected boolean isInitialLoad() {
    return true;
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
  public void handleSecondaryJdbc(Connection con, Pair<String, String> range) throws SQLException {
    LOGGER.trace("handleSecondaryJdbc(): begin");
    try (final PreparedStatement stmtInsClient = con.prepareStatement(INS_CLI_LST_CHG);
        final PreparedStatement stmtInsClientPlaceHome = con.prepareStatement(INS_CLI_DUMMY);
        final PreparedStatement stmtSelPlacementAddress =
            con.prepareStatement(SEL_PLACE_ADDR, TFO, CRO);
        final PreparedStatement stmtSelClient = con.prepareStatement(SEL_CLI, TFO, CRO);
        final PreparedStatement stmtSelCliAddr = con.prepareStatement(SEL_CLI_ADDR, TFO, CRO);
        final PreparedStatement stmtSelCliCnty = con.prepareStatement(SEL_CLI_COUNTY, TFO, CRO);
        final PreparedStatement stmtSelAddress = con.prepareStatement(SEL_ADDR, TFO, CRO);
        final PreparedStatement stmtSelAka = con.prepareStatement(SEL_AKA, TFO, CRO);
        final PreparedStatement stmtSelCase = con.prepareStatement(SEL_CASE, TFO, CRO);
        final PreparedStatement stmtSelCsec = con.prepareStatement(SEL_CSEC, TFO, CRO);
        final PreparedStatement stmtSelEthnicity = con.prepareStatement(SEL_ETHNIC, TFO, CRO);
        final PreparedStatement stmtSelSafety = con.prepareStatement(SEL_SAFETY, TFO, CRO)) {

      // Client keys for this bundle.
      loadClientRange(con, stmtInsClient, range);

      LOGGER.info("Read client");
      read(stmtSelClient, rs -> readClient(rs));

      // SNAP-735: missing addresses.
      LOGGER.info("Read client address");
      read(stmtSelCliAddr, rs -> readClientAddress(rs));

      LOGGER.info("Read address");
      read(stmtSelAddress, rs -> readAddress(rs));

      loadClientRange(con, stmtInsClient, range); // Set bundle client keys again.
      LOGGER.info("Read client county");
      read(stmtSelCliCnty, rs -> readClientCounty(rs));

      LOGGER.info("Read aka");
      read(stmtSelAka, rs -> readAka(rs));

      LOGGER.info("Read case");
      read(stmtSelCase, rs -> readCase(rs));

      LOGGER.info("Read csec");
      read(stmtSelCsec, rs -> readCsec(rs));

      LOGGER.info("Read ethnicity");
      read(stmtSelEthnicity, rs -> readEthnicity(rs));

      LOGGER.info("Read safety alert");
      read(stmtSelSafety, rs -> readSafetyAlert(rs));
      con.commit(); // free db resources again

      LOGGER.info("Insert placement home clients");
      loadClientRange(con, stmtInsClient, range);
      prepPlacementClients(stmtInsClientPlaceHome, range);

      LOGGER.info("Read placement home address");
      readPlacementAddress(stmtSelPlacementAddress);
      con.commit(); // free db resources. Make DBA's happy.
    } catch (Exception e) {
      LOGGER.error("handleSecondaryJdbc: BOOM!", e);

      try {
        rocket.fail(); // Last change: fail the whole job!
        con.rollback();
      } catch (Exception e2) {
        LOGGER.trace("NESTED ROLLBACK EXCEPTION!", e2);
      }
      throw CaresLog.runtime(LOGGER, e, "SECONDARY JDBC FAILED! {}", e.getMessage(), e);
    }

    LOGGER.debug("handleSecondaryJdbc(): DONE");
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
  public void handleJdbcDone(final Pair<String, String> range) {
    final RawToEsConverter conv = new RawToEsConverter();
    this.rawClients.values().stream().map(r -> r.normalize(conv))
        .forEach(c -> normalized.put(c.getId(), c));
    rawClients.clear(); // free memory
    LOGGER.debug("handleJdbcDone: normalized: {}", normalized.size());

    // Merge placement home addresses.
    placementHomeAddresses.values().stream().forEach(this::mapReplicatedClient);

    // TODO: stream to JSON and return.
    // normalized.values().stream().forEach(rocket::addToIndexQueue);
    LOGGER.debug("handleJdbcDone: FINISHED");
  }

  @Override
  public void handleFinishRange(Pair<String, String> range) {
    doneThreadRetrieve();
    clear();
  }

  @Override
  public List<ReplicatedClient> getResults() {
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
    stmt.setMaxRows(0);
    stmt.setQueryTimeout(QUERY_TIMEOUT_IN_SECONDS); // SNAP-709
    stmt.setFetchSize(FETCH_SIZE);

    final int countInsClient = stmt.executeUpdate();
    LOGGER.info("Prepped placement home clients: {}", countInsClient);
  }

  protected void readPlacementAddress(final PreparedStatement stmt) throws SQLException {
    stmt.setMaxRows(0);
    stmt.setQueryTimeout(QUERY_TIMEOUT_IN_SECONDS); // SNAP-709
    stmt.setFetchSize(FETCH_SIZE);

    int cntr = 0;
    PlacementHomeAddress pha;

    // SNAP-709: Connection is closed. ERRORCODE=-4470, SQLSTATE=08003.
    // According to the JDBC specification, you shouldn't close the ResultSet; closing its parent
    // statement should close the result set.
    try (final ResultSet rs = stmt.executeQuery()) {
      while (rocket.isRunning() && rs.next()) {
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