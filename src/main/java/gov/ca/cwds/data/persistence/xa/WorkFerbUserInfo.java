package gov.ca.cwds.data.persistence.xa;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;

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

  @Override
  public void execute(Connection con) throws SQLException {
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final String staffId = ctx.getStaffId();
    final String userId = ctx.getUserId();

    con.setAutoCommit(false);

    if (con instanceof DB2Connection) {
      try {
        // https://vsis-www.informatik.uni-hamburg.de/oldServer/teaching/ws-06.07/dbms/materialien/db2-manuals/db2aje90.pdf
        // Properties start on page 232.
        LOGGER.info("DB2 connection, set user info");
        con.setClientInfo("ApplicationName", PROGRAM_NAME);
        con.setClientInfo("clientProgramName", PROGRAM_NAME);
        con.setClientInfo("clientWorkstation", IP_ADDRESS);
        con.setClientInfo("clientAccountingInformation", userId);
        con.setClientInfo("ClientUser", userId);

        final DB2Connection db2con = (DB2Connection) con;
        db2con.setDB2ClientAccountingInformation(userId);
        db2con.setDB2ClientApplicationInformation(userId);
        db2con.setDB2ClientUser(staffId);
        db2con.setDB2ClientWorkstation(WORKSTATION);

        // ALTERNATIVE: call proc SYSPROC.WLM_SET_CLIENT_INFO.
      } catch (Exception e) {
        LOGGER.warn("Unsupported client info", e);
      }
    }
  }
}
