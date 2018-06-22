package gov.ca.cwds.data.persistence.xa;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.db2.jcc.DB2Connection;

import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Set DB2 user information on the active connection, including user logon and staff id.
 * 
 * <p>
 * Shockingly, SonarQube complains about vendor-specific JDBC methods, thus the SuppressWarnings
 * annotation on {@link #execute(Connection)}.
 * </p>
 * 
 * @author CWDS API Team
 */
public class WorkDB2UserInfo implements Work {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkDB2UserInfo.class);

  public static final String PROGRAM_NAME = "CARES Ferb";

  public static final String SERVER_IP_ADDRESS;
  public static final String SERVER_IP_NAME;

  static {
    String hostAddress = null;
    String hostName = null;
    try {
      final InetAddress i = InetAddress.getLocalHost();
      hostAddress = i.getHostAddress();
      hostName = i.getHostName();
      LOGGER.info("Host name: {}, IP address: {}", hostAddress, hostName);
    } catch (Exception e) {
      LOGGER.error("UNABLE TO FIND HOST IP! {}", e.getMessage(), e);
      if (StringUtils.isBlank(hostAddress)) {
        hostAddress = "unknown server";
      }
    } finally {
      SERVER_IP_ADDRESS = hostAddress;
      SERVER_IP_NAME = hostName;
    }
  }

  @SuppressWarnings({"fb-contrib:JVR_JDBC_VENDOR_RELIANCE", "squid:CallToDeprecatedMethod"})
  @Override
  public void execute(Connection con) throws SQLException {
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final String staffId = ctx.getStaffId();
    final String userId = ctx.getUserId();

    con.setAutoCommit(false);
    con.setClientInfo("ApplicationName", PROGRAM_NAME);
    con.setClientInfo("ClientUser", userId);
    con.setClientInfo("ClientHostname", SERVER_IP_NAME);

    if (con instanceof DB2Connection) {
      LOGGER.info("DB2 connection, set user info");
      final DB2Connection db2conn = (DB2Connection) con;
      db2conn.setDB2ClientAccountingInformation(userId);
      db2conn.setDB2ClientApplicationInformation(userId);
      db2conn.setDB2ClientProgramId(PROGRAM_NAME);
      db2conn.setDB2ClientUser(staffId);
      db2conn.setDB2ClientWorkstation(SERVER_IP_ADDRESS);

      // ALTERNATIVE: call proc SYSPROC.WLM_SET_CLIENT_INFO.
    }
  }

}
