package gov.ca.cwds.rest.util.jni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.ibm.db2.jcc.DB2Connection;

@SuppressWarnings("squid:S2187")
public class DB2PassUserTest {

  @SuppressWarnings("deprecation")
  public static void main(String[] args) {

    try {
      Class.forName("com.ibm.db2.jcc.DB2Driver");

      final String url = System.getenv("DB_CMS_JDBC_URL");
      final String user = System.getenv("DB_CMS_USER");
      final String password = System.getenv("DB_CMS_PASSWORD");
      final String schema = System.getenv("DB_CMS_SCHEMA");

      try (final Connection conn = DriverManager.getConnection(url, user, password)) {
        conn.setSchema(schema);
        conn.setCatalog(schema);
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        if (conn instanceof DB2Connection) {
          final String userId = "0X5";
          final DB2Connection db2conn = (DB2Connection) conn;

          db2conn.setClientInfo("ClientUser", userId);
          db2conn.setDB2ClientUser(userId); // staff id
          db2conn.setDB2ClientWorkstation("127.0.0.1");
          db2conn.setAutoCommit(true);
          // db2conn.setDB2ClientDebugInfo(arg0, arg1);

          System.out.println("Driver properties: " + db2conn.getClientInfo());

          conn.prepareStatement(
          //@formatter:off
                "UPDATE " + schema + ".TSCNTRLT C \n"
              + "SET C.LST_UPD_TS = CURRENT TIMESTAMP \n"
              + ", C.LST_UPD_ID = CURRENT CLIENT_USERID \n"
              + "WHERE C.DOC_HANDLE = '0001121506110220*RAMESHA 00001'")
          .executeUpdate();
         //@formatter:on

          // Execute SQL to send extended client information to the server.
          // ResultSet auto-closes upon block exit.
          try (ResultSet rs = conn.prepareStatement(
          //@formatter:off
              "SELECT C.DOC_HANDLE, C.DOC_SEGS, C.CMPRS_PRG, C.DOC_NAME, C.DOC_DATE, C.DOC_TIME, C.DOC_LEN "
                  + ", C.LST_UPD_ID, C.LST_UPD_TS, C.DOC_AUTH, C.DOC_SERV "
                  + "FROM " + schema + ".TSCNTRLT C \n" 
                  + "WHERE C.DOC_HANDLE = '0001121506110220*RAMESHA 00001' "
                  + "ORDER BY C.DOC_HANDLE \n"
                  + "FOR READ ONLY WITH UR")
              //@formatter:on
              .executeQuery()) {

            while (rs.next()) {
              final String staffId = rs.getString("LST_UPD_ID");
              System.out.println("Found staffId: " + staffId);
            }
          }
        }

      } finally {
        // auto-close
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

