package gov.ca.cwds.rest.services.cms;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.ObjectMapperUtils;
import gov.ca.cwds.auth.realms.PerryUserIdentity;
import gov.ca.cwds.security.realm.PerryAccount;

/**
 * @author CWDS API Team
 */
public class StaffPersonIdRetriever {

  private static final Logger LOGGER = LoggerFactory.getLogger(StaffPersonIdRetriever.class);

  private static final String DEFAULT_STAFF_ID = "0X5";
  private static final String DEFAULT_USER_ID = "CWDST";
  public static final String DEFAULT_GOVERNMENT_ENTITY_CODE = "1126";
  public static final String DEFAULT_COUNTY_SPECIFIC_CODE = "99";

  StaffPersonIdRetriever() {}

  /**
   * Retrieves the Staff Person Id of the current user. Defaults to a hard-coded value if
   * authorization information is not passed in or if StaffId is not included as part of the
   * security token
   * 
   * @return the last updated id for persistence, this is the Staff Person Id of the current user
   */
  public String getStaffPersonId() {
    String staffId = null;
    final PerryUserIdentity perryUserIdentity = getPerryUserIdentity();
    if (perryUserIdentity != null) {
      staffId = perryUserIdentity.getStaffId();
    }
    return staffId;
  }

  /**
   * @return the perry user
   */
  public static PerryUserIdentity getPerryUserIdentity() {
    PerryUserIdentity perryUserIdentity = null;

    final Subject currentUser = SecurityUtils.getSubject();
    final PrincipalCollection principalCollection = currentUser.getPrincipals();

    if (principalCollection != null) {
      @SuppressWarnings("rawtypes")
      final List principals = currentUser.getPrincipals().asList();
      final int principalCount = principals.size();
      final Object currentPrincipal = principalCount > 1 ? principals.get(1) : null;

      perryUserIdentity = getCurrentPrincipal(currentPrincipal);
    } else {
      LOGGER.warn("======= current user has no principals for {}", currentUser);
    }

    if (perryUserIdentity == null) {
      final String localDevProp = System.getenv("LOCAL_DEV");
      if (StringUtils.isNotBlank(localDevProp) && "true".equals(localDevProp)) {
        perryUserIdentity = new PerryUserIdentity();
        perryUserIdentity.setStaffId(DEFAULT_STAFF_ID);
        perryUserIdentity.setUser(DEFAULT_USER_ID);
        perryUserIdentity.setCountyCode(DEFAULT_COUNTY_SPECIFIC_CODE);
        perryUserIdentity.setCountyCwsCode(DEFAULT_GOVERNMENT_ENTITY_CODE);

        LOGGER.error("======= PerryUserIdentity not found, using default for local dev = {}",
            DEFAULT_STAFF_ID);
      }
    }

    return perryUserIdentity;
  }

  private static PerryUserIdentity getCurrentPrincipal(Object currentPrincipal) {
    PerryUserIdentity perryUserIdentity = null;
    if (currentPrincipal != null
        && PerryAccount.class.isAssignableFrom(currentPrincipal.getClass())) {
      try {
        final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();
        final String valueAsString = objectMapper.writeValueAsString(currentPrincipal);
        final PerryUserIdentity currentUserInfo =
            objectMapper.readValue(valueAsString, PerryUserIdentity.class);
        final String staffPersonId = currentUserInfo.getStaffId();

        if (StringUtils.isBlank(staffPersonId)) {
          handleMissingStaffId(currentUserInfo);
        } else {
          perryUserIdentity = currentUserInfo;
          LOGGER.info("======= Perry Staff ID = {}", staffPersonId);
        }
      } catch (IOException e) {
        LOGGER.error("======= Cannot convert principal to PerryUserIdentity", e);
      }
    } else {
      LOGGER.info("======= currentPrinciple is null or not a PerryUserIdentity object{}",
          currentPrincipal);
    }
    return perryUserIdentity;
  }

  private static void handleMissingStaffId(PerryUserIdentity currentUserInfo) {
    final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();
    String json = null;
    try {
      json = objectMapper.writeValueAsString(currentUserInfo);
    } catch (JsonProcessingException e) {
      LOGGER.error("======= Staff ID missing in PerryUserIdentity", e);
    }
    LOGGER.error("======= Staff ID missing in PerryUserIdentity: {}", json);
  }
}
