package gov.ca.cwds.es.live;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

/**
 * Execute DML prior to retrieving records, typically for last change runs.
 * 
 * <p>
 * Examples include populating a global temporary table prior to reading from a view.
 * </p>
 * 
 * @author CWDS API Team
 */
public class LiveElasticWorkPrepareLastChange extends LiveElasticWorkTotalImpl {

  private static final ConditionalLogger LOGGER =
      new CaresConditionalLoggerImpl(LiveElasticWorkPrepareLastChange.class);

  private final Date lastRunTime;
  private final String sql;

  /**
   * Constructor.
   * 
   * @param lastRunTime last successful run time
   * @param sql SQL to run
   * @param prepStmtMaker Function to produce prepared statement
   */
  public LiveElasticWorkPrepareLastChange(Date lastRunTime, String sql,
      final Function<Connection, PreparedStatement> prepStmtMaker) {
    super(prepStmtMaker);
    this.lastRunTime = lastRunTime != null ? new Date(lastRunTime.getTime()) : null;
    this.sql = sql;
  }

  /**
   * Execute the PreparedStatement.
   * 
   * <p>
   * <strong>WARNING!</strong>. DB2 may not optimize a PreparedStatement the same as dynamic SQL and
   * vice versa, since prepared statements may use a <strong>different set of statistics!</strong>
   * </p>
   * 
   * @param con current database connection
   */
  @Override
  public void execute(Connection con) throws SQLException {
    LiveElasticJdbcHelper.enableBatchSettings(con);

    final String strLastRunTime = LiveElasticDateHelper.makeTimestampStringLookBack(lastRunTime);
    LOGGER.trace("strLastRunTime: {}", strLastRunTime);

    try (final PreparedStatement stmt = createPreparedStatement(con)) {
      for (int i = 1; i <= StringUtils.countMatches(sql, "?"); i++) {
        stmt.setString(i, strLastRunTime);
      }

      LOGGER.info("Find keys changed since {}", strLastRunTime);
      setTotalProcessed(stmt.executeUpdate());
      LOGGER.info("Total keys {} changed since {}", getTotalProcessed(), strLastRunTime);
    }
  }

}
