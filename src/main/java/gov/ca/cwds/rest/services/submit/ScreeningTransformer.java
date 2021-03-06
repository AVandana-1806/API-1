package gov.ca.cwds.rest.services.submit;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.CWSDateTime;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Allegation;
import gov.ca.cwds.rest.api.domain.CrossReport;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.util.FerbDateUtils;

/**
 * Business layer object to transform an NS {@link Screening} to a {@link ScreeningToReferral}
 * 
 * @author CWDS API Team
 */
public class ScreeningTransformer {

  private static final String RESPONSIBLE_AGENCY = "C";
  private static final Boolean FAMILY_AWARENESS = Boolean.FALSE;
  private static final Boolean FILED_WITH_LAW_ENFORCEMENT = Boolean.FALSE;
  private static final Short APPROVAL_STATUS =
      SystemCodeCache.global().getSystemCodeId("Request Not Submitted", "APV_STC");

  /**
   * @param screening - screening
   * @param loggedInStaffId - loggedInStaffId
   * @param loggedInStaffCounty - loggedInStaffCounty
   * @return the screening
   */
  public ScreeningToReferral transform(Screening screening, String loggedInStaffId,
      String loggedInStaffCounty) {
    screening.setAssigneeStaffId(loggedInStaffId);
    return createScreeningToReferralWithDefaults(screening, loggedInStaffCounty);
  }

  private ScreeningToReferral createScreeningToReferralWithDefaults(Screening screening,
      String loggedInStaffCounty) {
    Set<Allegation> allegations =
        new AllegationsTransformer().transform(screening.getAllegations());
    String safetyAlertsInformation = new SafetyAlertsInformation().buildSafetyAlertsInfo(screening);
    Short communicationMethodSysId = setCommunicationMethod(screening);
    Short responseTimeSysId = setReferralResponse(screening);
    String limitedAccessCode = StringUtils.isNotBlank(screening.getAccessRestrictions())
        ? (AccessRestrictions.findByNsDescription(screening.getAccessRestrictions().toLowerCase()))
            .getCmsDescription()
        : "N";
    Date limitedAccessDate = setLimitedAccesDate(screening);
    Address address = (screening.getIncidentAddress() != null)
        ? new AddressTransformer().transform(screening.getIncidentAddress())
        : null;
    Set<Participant> participants = (screening.getParticipantIntakeApis() != null)
        ? new ParticipantsTransformer().transform(screening.getParticipantIntakeApis())
        : null;

    Set<CrossReport> crossReports = (screening.getCrossReports() != null)
        ? new CrossReportsTransformer().transform(screening.getCrossReports())
        : null;

    String screeningIncidentDate =
        screening.getIncidentDate() == null ? null : screening.getIncidentDate().toString();

    DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(CWSDateTime.TIMESTAMP_ISO8601_FORMAT);

    String screeningEndDate =
        screening.getEndedAt() == null ? null
            : dateTimeFormatter.format(FerbDateUtils.utcToSystemTime(ZonedDateTime
                .ofInstant(screening.getEndedAt().toInstant(), ZoneOffset.systemDefault())
                .toLocalDateTime()));

    String screeningStartDate = screening.getStartedAt() == null ? null
        : dateTimeFormatter.format(FerbDateUtils.utcToSystemTime(ZonedDateTime
            .ofInstant(screening.getStartedAt().toInstant(), ZoneOffset.systemDefault())
            .toLocalDateTime()));

    return new ScreeningToReferral(Integer.parseInt(screening.getId()),
        LegacyTable.REFERRAL.getName(), screening.getReferralId(), screeningEndDate,
        screening.getIncidentCounty(), screeningIncidentDate, screening.getLocationType(),
        communicationMethodSysId, screening.getCurrentLocationOfChildren(), screening.getName(),
        screening.getReportNarrative(), screening.getReference(), responseTimeSysId,
        screeningStartDate, screening.getAssignee(), screening.getAssigneeStaffId(),
        screening.getAdditionalInformation(), screening.getScreeningDecision(),
        screening.getScreeningDecisionDetail(), APPROVAL_STATUS, FAMILY_AWARENESS,
        FILED_WITH_LAW_ENFORCEMENT, RESPONSIBLE_AGENCY, limitedAccessCode,
        screening.getRestrictionsRationale(), loggedInStaffCounty, limitedAccessDate, null,
        safetyAlertsInformation, address, participants, new HashSet<ScreeningRelationship>(),
        crossReports, allegations, screening.getReportType());
  }

  private Short setReferralResponse(Screening screening) {
    String responseTime = screening.getScreeningDecisionDetail();
    return StringUtils.isNotBlank(responseTime) ? IntakeCodeCache.global()
        .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.REFERRAL_RESPONSE, responseTime)
        : null;
  }

  private Short setCommunicationMethod(Screening screening) {
    return StringUtils.isNotBlank(screening.getCommunicationMethod())
        ? IntakeCodeCache.global().getLegacySystemCodeForIntakeCode(
            SystemCodeCategoryId.COMMUNICATION_METHOD, screening.getCommunicationMethod())
        : null;
  }

  private Date setLimitedAccesDate(Screening screening) {
    return screening.getRestrictionsDate() != null
        ? java.sql.Date.valueOf(screening.getRestrictionsDate())
        : null;
  }

}
