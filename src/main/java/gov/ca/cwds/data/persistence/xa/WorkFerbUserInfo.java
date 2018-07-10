package gov.ca.cwds.data.persistence.xa;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

  public static final String PROGRAM_NAME = "CARES Ferb";

  public static final String SERVER_IP_ADDRESS;
  public static final String SERVER_IP_NAME;

  private static ExecutorService timeoutExecutor = Executors.newFixedThreadPool(2);

  // Find host and IP address up front.
  static {
    String hostAddress = null;
    String hostName = null;
    try {
      final InetAddress i = InetAddress.getLocalHost();
      hostAddress = i.getHostAddress();
      hostName = i.getHostName();
      LOGGER.info("Host name: {}, IP address: {}", hostAddress, hostName);
    } catch (Exception e) {
      LOGGER.error("FAILED TO FIND HOST IP! {}", e.getMessage(), e);
      if (StringUtils.isBlank(hostAddress)) {
        hostAddress = "Ferb unknown";
      }
    } finally {
      SERVER_IP_ADDRESS = hostAddress;
      SERVER_IP_NAME = hostName;
    }
  }

  @Override
  public void execute(Connection con) throws SQLException {
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final String staffId = ctx.getStaffId();
    final String userId = ctx.getUserId();

    con.setAutoCommit(false);
    con.setNetworkTimeout(timeoutExecutor, 90); // NEXT: soft-code timeout

    if (con instanceof DB2Connection) {
      LOGGER.info("DB2 connection, set user info");
      con.setClientInfo("ApplicationName", PROGRAM_NAME);
      con.setClientInfo("ClientUser", userId);
      con.setClientInfo("ClientHostname", SERVER_IP_ADDRESS);

      final DB2Connection db2conn = (DB2Connection) con;
      db2conn.setDB2ClientAccountingInformation(userId);
      db2conn.setDB2ClientApplicationInformation(userId);
      db2conn.setDB2ClientProgramId(userId);
      db2conn.setDB2ClientUser(staffId);
      db2conn.setDB2ClientWorkstation(SERVER_IP_NAME);

      // ALTERNATIVE: call proc SYSPROC.WLM_SET_CLIENT_INFO.
    }
  }

}
