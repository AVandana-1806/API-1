package gov.ca.cwds.es.live;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * Execute DML prior to retrieving records by integer range, typically for last change runs.
 * 
 * <p>
 * Examples include populating a global temporary table prior to reading from a view.
 * </p>
 * 
 * @author CWDS API Team
 */
public class LiveElasticWorkPrepareRownumBundle extends LiveElasticWorkTotalImpl {

  private static final ConditionalLogger LOGGER =
      new CaresConditionalLoggerImpl(LiveElasticWorkPrepareRownumBundle.class);

  private final int start;
  private final int end;

  /**
   * Constructor.
   * 
   * @param start beginning of range
   * @param end end of range
   * @param prepStmtMaker Function to produce prepared statement
   */
  public LiveElasticWorkPrepareRownumBundle(int start, int end,
      final Function<Connection, PreparedStatement> prepStmtMaker) {
    super(prepStmtMaker);
    this.start = start;
    this.end = end;
  }

  /**
   * Execute the PreparedStatement.
   * 
   * @param con current database connection
   */
  @Override
  public void execute(Connection con) throws SQLException {
    try (final PreparedStatement stmt = createPreparedStatement(con)) {
      stmt.setInt(1, start);
      stmt.setInt(2, end);

      setTotalProcessed(stmt.executeUpdate());
      LOGGER.info("Total keys {} inserted", getTotalProcessed());
    }
  }

}
