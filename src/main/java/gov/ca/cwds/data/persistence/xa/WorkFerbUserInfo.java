package gov.ca.cwds.data.persistence.xa;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.PooledConnection;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.db2.jcc.DB2Connection;

import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Set user information on the active connection, including user logon and staff id. Also, DB2
 * specific information, for DB2 connections.
 * 
 * <p>
 * Shockingly, SonarQube complains about vendor-specific JDBC methods, thus the SuppressWarnings
 * annotation on {@link #execute(Connection)}.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"fb-contrib:JVR_JDBC_VENDOR_RELIANCE", "squid:CallToDeprecatedMethod",
    "fb-contrib:MDM_INETADDRESS_GETLOCALHOST"})
public class WorkFerbUserInfo implements Work {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkFerbUserInfo.class);

  public static final String PROGRAM_NAME = "Ferb";

  public static final String IP_ADDRESS;
  public static final String WORKSTATION;

  // Find host and IP address up front.
  static {
    String hostAddress = "IP?";
    String hostName = PROGRAM_NAME;
    try {
      final InetAddress i = InetAddress.getLocalHost();
      hostAddress = i.getHostAddress();
      hostName = i.getHostName();
      LOGGER.info("Host name: {}, IP address: {}", hostAddress, hostName);
    } catch (Exception e) {
      LOGGER.error("FAILED TO FIND HOST IP! {}", e.getMessage(), e);
    } finally {
      IP_ADDRESS = hostAddress.substring(0, Math.min(hostAddress.length(), 10));
      WORKSTATION = hostName.substring(0, Math.min(hostName.length(), 17));
    }
  }

  private boolean isDb2 = false;

  public WorkFerbUserInfo() {
    // no-op
  }

  public WorkFerbUserInfo(final boolean isDb2) {
    this.isDb2 = isDb2;
  }

  @Override
  public void execute(Connection con) throws SQLException {
    LOGGER.warn("execute: Connection class: {}", con.getClass().getName());
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final String staffId = ctx.getStaffId();
    final String userId = ctx.getUserId();

    if (isDb2 || con instanceof DB2Connection || (con instanceof PooledConnection
        && ((PooledConnection) con).getConnection() instanceof DB2Connection)) {
      try {
        LOGGER.warn("DB2 connection, set user info: user id: {}, staff id: {}", userId, staffId);
        con.setClientInfo("ApplicationName", PROGRAM_NAME);

        // Unwrap pooled connections.
        DB2Connection db2con;
        if (con instanceof PooledConnection) {
          db2con = (DB2Connection) ((PooledConnection) con).getConnection();
        } else {
          db2con = (DB2Connection) con;
        }

        db2con.setDB2ClientAccountingInformation(userId);
        db2con.setDB2ClientApplicationInformation(userId);
        db2con.setDB2ClientUser(userId);
        db2con.setDB2ClientWorkstation(WORKSTATION);

        //@formatter:off
        final String sql = 
              "SELECT \n"
            + "   CURRENT TIMESTAMP                 AS CUR_TS \n"
            + " , SESSION_USER                      AS CUR_SESSION_USER \n"
            + " , USER                              AS CUR_USER \n"
            + " , CURRENT CLIENT_WRKSTNNAME         AS CUR_WORKSTATION \n"
            + " , CURRENT CLIENT_APPLNAME           AS CUR_APP_NM \n"
            + " , CURRENT CLIENT_ACCTNG             AS CUR_ACCOUNTING \n"
            + " , CURRENT CLIENT_USERID             AS CUR_CLIENT_USERID \n"
            + " , CURRENT APPLICATION COMPATIBILITY AS CUR_COMPATIBILITY \n"
            + " , CURRENT MEMBER                    AS CUR_MEMBER \n"
            + " , CURRENT SCHEMA                    AS CUR_SCHEMA \n"
            + " , CURRENT SQLID                     AS CUR_SQLID \n"
            + " , SESSION TIME ZONE                 AS SESS_TIME_ZONE \n"
            + " , CURRENT TIME ZONE                 AS CUR_TIME_ZONE \n"
            + "FROM SYSIBM.SYSDUMMY1 \n"
            + "FOR READ ONLY WITH UR ";
        //@formatter:on

        try (final PreparedStatement stmt = con.prepareStatement(sql);
            final ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            final String resultClientUserId =
                StringUtils.trimToEmpty(rs.getString("CUR_CLIENT_USERID"));
            final String resultAppName = StringUtils.trimToEmpty(rs.getString("CUR_APP_NM"));
            final String resultAccounting = StringUtils.trimToEmpty(rs.getString("CUR_ACCOUNTING"));
            final String resultWorkstation =
                StringUtils.trimToEmpty(rs.getString("CUR_WORKSTATION"));

            LOGGER.warn("client user: {}, application: {}, accounting: {}, workstation: {}",
                resultClientUserId, resultAppName, resultAccounting, resultWorkstation);
          }
        } finally {
          // Prepared statement goes out of scope.
        }

        // ALTERNATIVE: call proc SYSPROC.WLM_SET_CLIENT_INFO.
      } catch (Exception e) {
        LOGGER.warn("Unsupported client info: {}", e.getMessage(), e);
      }
    }
  }

}
