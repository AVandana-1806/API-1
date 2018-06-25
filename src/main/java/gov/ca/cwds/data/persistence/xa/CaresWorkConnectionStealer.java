package gov.ca.cwds.data.persistence.xa;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.jdbc.Work;

/**
 * Steal a connection from a Hibernate session and make it available to the caller.
 * 
 * @author CWDS API Team
 */
public class CaresWorkConnectionStealer implements Work {

  private Connection con;

  /**
   * Constructor.
   */
  public CaresWorkConnectionStealer() {}

  /**
   * Call {@link AtomLoadStepHandler#handleSecondaryJdbc(Connection, Pair)}.
   * 
   * @param con current database connection
   */
  @Override
  public void execute(Connection con) throws SQLException {
    this.con = new CandaceConnectionImpl(con);
  }

  public Connection getConnection() {
    return con;
  }

  public void setConnection(Connection conn) {
    this.con = conn;
  }

}
