package gov.ca.cwds.rest.util.jni;

import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.XADataSource;

import org.postgresql.PGProperty;
import org.postgresql.xa.PGXADataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.db2.jcc.DB2XADataSource;

import gov.ca.cwds.rest.api.ApiException;

public class XASample {

  private static final Logger LOGGER = LoggerFactory.getLogger(XASample.class);

  javax.sql.XADataSource xaDS1;
  javax.sql.XADataSource xaDS2;

  javax.sql.XAConnection xaconn1;
  javax.sql.XAConnection xaconn2;

  javax.transaction.xa.XAResource xares1;
  javax.transaction.xa.XAResource xares2;

  java.sql.Connection conn1;
  java.sql.Connection conn2;

  private static DB2XADataSource buildDB2DataSource(String user, String password, String serverName,
      int port, String databaseName) {
    final DB2XADataSource ds = new DB2XADataSource();
    ds.setUser(user);
    ds.setPassword(password);
    ds.setServerName(serverName);
    ds.setPortNumber(port);
    ds.setDatabaseName(databaseName);
    ds.setDriverType(4);

    try {
      ds.getProperties().setProperty(DB2XADataSource.propertyKey_serverName, serverName);
      ds.getProperties().setProperty(DB2XADataSource.propertyKey_portNumber, String.valueOf(port));
      ds.getProperties().setProperty(DB2XADataSource.propertyKey_databaseName, databaseName);
      ds.getProperties().setProperty(DB2XADataSource.propertyKey_driverType, "4");
    } catch (SQLException e) {
      LOGGER.error("ERROR BUILDING DB2 DATASOURCE!", e);
      throw new ApiException("datasource property error", e);
    }

    return ds;
  }

  /**
   * <a href=
   * "https://www.postgresql.org/docs/current/static/runtime-config-resource.html#GUC-MAX-PREPARED-TRANSACTIONS">Postgres
   * distributed transactions</a>.
   * 
   * @param user obvious
   * @param password obvious
   * @param serverName obvious
   * @param port obvious
   * @param databaseName obvious
   * @return Postgres datasource
   */
  private static PGXADataSource buildPGDataSource(String user, String password, String serverName,
      int port, String databaseName) {
    final PGXADataSource ds = new PGXADataSource();
    ds.setUser(user);
    ds.setPassword(password);
    ds.setServerName(serverName);
    ds.setPortNumber(port);
    ds.setDatabaseName(databaseName);

    try {
      ds.setProperty("user", user);
      ds.setProperty("password", password);
      ds.setProperty(PGProperty.PG_DBNAME, databaseName);
      ds.setProperty(PGProperty.PG_HOST, serverName);
      ds.setProperty(PGProperty.PG_PORT, String.valueOf(port));
    } catch (SQLException e) {
      LOGGER.error("ERROR BUILDING PG DATASOURCE!", e);
      throw new ApiException("datasource property error", e);
    }

    LOGGER.info("pgxa data source");
    return ds;
  }

  private static void setupInitialContext() {
    LOGGER.info("setupInitialContext();");
    try {
      NamingManager.setInitialContextFactoryBuilder(new InitialContextFactoryBuilder() {

        @Override
        public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment)
            throws NamingException {
          return new InitialContextFactory() {

            @Override
            public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
              return new InitialContext() {

                private Hashtable<String, XADataSource> dataSources = new Hashtable<>();

                @Override
                public Object lookup(String name) throws NamingException {
                  if (dataSources.isEmpty()) { // init datasources
                    dataSources.put("jdbc/postgres",
                        buildPGDataSource(System.getenv("NS_USER"), System.getenv("NS_PASSWORD"),
                            System.getenv("NS_HOST"), Integer.parseInt(System.getenv("NS_PORT")),
                            System.getenv("NS_SERVICE")));

                    dataSources.put("jdbc/docker",
                        buildDB2DataSource(System.getenv("DB2_USER"), System.getenv("DB2_PASSWORD"),
                            System.getenv("DB2_HOST"), Integer.parseInt(System.getenv("DB2_PORT")),
                            System.getenv("DB2_SERVICE")));
                  }

                  if (dataSources.containsKey(name)) {
                    return dataSources.get(name);
                  }

                  throw new NamingException("Unable to find datasource: " + name);
                }
              };
            }

          };
        }

      });
    } catch (NamingException ne) {
      ne.printStackTrace();
    }
  }

  /**
   * As the transaction manager, this program supplies the global transaction ID and the branch
   * qualifier. The global transaction ID and the branch qualifier must not be equal to each other,
   * and the combination must be unique for this transaction manager.
   * 
   * @param args command line
   */
  public void runThis(String[] args) {
    byte[] gtrid = new byte[] {0x44, 0x11, 0x55, 0x66};
    byte[] bqual = new byte[] {0x00, 0x22, 0x00};
    int rc1 = 0;
    int rc2 = 0;

    try {
      Class.forName("com.ibm.db2.jcc.DB2Driver");
      Class.forName("org.postgresql.Driver");
      setupInitialContext();

      // Note that javax.sql.XADataSource is used instead of a specific driver implementation such
      // as com.ibm.db2.jcc.DB2XADataSource.
      xaDS1 = (javax.sql.XADataSource) InitialContext.doLookup("jdbc/postgres");
      xaDS2 = (javax.sql.XADataSource) InitialContext.doLookup("jdbc/docker");

      // The XADatasource contains the user ID and password.
      // Get the XAConnection object from each XADataSource
      xaconn1 = xaDS1.getXAConnection();
      xaconn2 = xaDS2.getXAConnection();

      // Get the java.sql.Connection object from each XAConnection
      conn1 = xaconn1.getConnection();
      conn2 = xaconn2.getConnection();

      // Get the XAResource object from each XAConnection
      xares1 = xaconn1.getXAResource();
      xares2 = xaconn2.getXAResource();

      // Create the Xid object for this distributed transaction.
      // This example uses the com.ibm.db2.jcc.DB2Xid implementation of the Xid interface. This Xid
      // can be used with any JDBC driver that supports JTA.
      javax.transaction.xa.Xid xid1 = new com.ibm.db2.jcc.DB2Xid(100, gtrid, bqual);

      // Start the distributed transaction on the two connections.
      // The two connections do NOT need to be started and ended together.
      // They might be done in different threads, along with their SQL operations.
      xares1.start(xid1, javax.transaction.xa.XAResource.TMNOFLAGS);
      xares2.start(xid1, javax.transaction.xa.XAResource.TMNOFLAGS);

      // ===================
      // ADD SQL HERE!
      // ===================

      // Run DML on connection 1.
      conn1.prepareStatement("UPDATE public.people SET first_name='bart201' WHERE id = '50'")
          .executeUpdate();

      // Run DML on connection 2.
      conn2.prepareStatement(
          // "update CWSINT.CLIENT_T c set c.BR_FAC_NM = 'conn 2' where c.IDENTIFIER =
          // 'AaiU7IW0Rt'")
          "update CWSNS1.ALLGTN_T c set c.LOC_DSC = 'test 6' where c.IDENTIFIER = 'AAABG3EMNL'")
          .executeUpdate();

      // Now end the distributed transaction on the two connections.
      xares1.end(xid1, javax.transaction.xa.XAResource.TMSUCCESS);
      xares2.end(xid1, javax.transaction.xa.XAResource.TMSUCCESS);

      // If connection 2 work had been done in another thread, a thread.join() call would be needed
      // here to wait until the connection 2 work is done.

      try {
        LOGGER.debug("try");
        // Now prepare both branches of the distributed transaction.
        // Both branches must prepare successfully before changes can be committed.
        // If the distributed transaction fails, an XAException is thrown.
        rc1 = xares1.prepare(xid1);
        if (rc1 == javax.transaction.xa.XAResource.XA_OK) {
          // Prepare succeeded. Prepare the second connection.
          rc2 = xares2.prepare(xid1);

          if (rc2 == javax.transaction.xa.XAResource.XA_OK) {
            // Both connections prepared successfully and neither was read-only.
            xares1.commit(xid1, false);
            xares2.commit(xid1, false);
          } else if (rc2 == javax.transaction.xa.XAException.XA_RDONLY) {
            // The second connection is read-only, so just commit the first connection.
            xares1.commit(xid1, false);
          }
        } else if (rc1 == javax.transaction.xa.XAException.XA_RDONLY) {
          // SQL for the first connection is read-only (such as a SELECT).
          // The prepare committed it. Prepare the second connection.
          rc2 = xares2.prepare(xid1);
          if (rc2 == javax.transaction.xa.XAResource.XA_OK) {
            // The first connection is read-only but the second is not. Commit the second
            // connection.
            xares2.commit(xid1, false);
          } else if (rc2 == javax.transaction.xa.XAException.XA_RDONLY) {
            // Both connections are read-only, and both already committed, so there is nothing more
            // to do.
          }
        }
      } catch (javax.transaction.xa.XAException xae) {
        // Distributed transaction failed, so roll it back. Report XAException on prepare/commit.
        LOGGER.info("Distributed transaction prepare/commit failed. Roll back.");
        LOGGER.error("XAException: error code = {}, message = {}", xae.errorCode, xae.getMessage(),
            xae);

        try {
          xares1.rollback(xid1);
        } catch (javax.transaction.xa.XAException xae1) { // Report failure of rollback.
          LOGGER.warn("distributed Transaction rollback xares1 failed");
          LOGGER.error("XAException: error code = {}, message = {}", xae1.errorCode,
              xae1.getMessage(), xae1);
        }

        try {
          xares2.rollback(xid1);
        } catch (javax.transaction.xa.XAException xae2) { // Report failure of rollback.
          LOGGER.warn("distributed Transaction rollback xares2 failed");
          LOGGER.error("XAException: error code = {}, message = {}", xae2.errorCode,
              xae2.getMessage(), xae2);
        }
      }

      try {
        conn1.close();
        xaconn1.close();
      } catch (Exception e) {
        LOGGER.warn("Failed to close connection 1: {}", e.toString(), e);
      }

      try {
        conn2.close();
        xaconn2.close();
      } catch (Exception e) {
        LOGGER.warn("Failed to close connection 2: {}", e.toString(), e);
      }

    } catch (java.sql.SQLException sqe) {
      LOGGER.error("SQLException caught: {}", sqe.getMessage(), sqe);
    } catch (javax.transaction.xa.XAException xae) {
      LOGGER.error("XA error is {}", xae.getMessage(), xae);
    } catch (javax.naming.NamingException nme) {
      LOGGER.error("Naming Exception: {}", nme.getMessage(), nme);
    } catch (ClassNotFoundException cnfe) {
      LOGGER.error("Class Not Found Exception: {}", cnfe.getMessage(), cnfe);
    }
  }

  public static void main(String args[]) throws java.sql.SQLException {
    new XASample().runThis(args);
  }

}
