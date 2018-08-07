package gov.ca.cwds.rest.business.reminders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StateIdDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.data.persistence.cms.StateId;
import gov.ca.cwds.rest.api.domain.AgeUnit;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.PostedScreeningToReferral;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.cms.TickleService;
import gov.ca.cwds.rest.validation.ParticipantValidator;

/**
 * <p>
 * <strong>BUSINESS RULE: "R - 05443" and "R - 03341" - TICKLE for State ID Missing</strong>
 * <p>
 * Access Logic
 * <p>
 * CHILD_CLIENT (where system date - CLIENT.Birth_Date &lt; 26 years <br>
 * or where CLIENT.Estimated_DOB_Code = Y and Age and Age Unit &lt; 26 years)<br>
 * does not have a state_id row with End Date blank and CHILD_CLIENT&gt;CASE.End_Date = null,<br>
 * then <br>
 * {Create TICKLE, set Tickle_Message_Type = 'State ID Missing',
 * <p>
 * {if the client is the focus child in a case (or the case in focus), <br>
 * set TICKLE.Affected_By_Case_Or_Referral_Id = CHILD_CLIENT&gt;CASE.Id of that case <br>
 * and TICKLE.Affected_By_Code = 'CC' and TICKLE.Affected_By_Other_Id = CLIENT.Id.,<br>
 * set TICKLE.Due_Date = CLIENT.Creation Date + 30 days
 * <p>
 * else If the client is in a referral (or the referral in focus) and
 * CHILD_CLIENT&gt;REFERRAL.Closure_Date = null,<br>
 * set TICKLE.Affected_By_Case_Or_Referral_Id = CHILD_CLIENT&gt;REFERRAL.Id and
 * TICKLE.Affected_By_Code = 'RL'<br>
 * and TICKLE.Affected_By_Other_Id = CLIENT.Id, set TICKLE.Due_Date = CLIENT.Creation Date + 30
 * days.}}
 * <p>
 * If (CHILD_CLIENT does not have a state_id row with End Date blank<br>
 * where .Government_Entity_Type = (CASE or REFERRAL &gt; ASSIGNMENT<br>
 * (where Type _Of_Assignment_Code = 'P' or (Type _Of_Assignment_Code = 'S'<br>
 * and Secondary_Assignment_role_type = 'ICPC Case Worker') and .End_Date = null<br>
 * or &gt; system date)) &gt; CASELOAD &gt; ASSIGNMENT UNIT &gt; CWS OFFICE.Government_Entity_Type,
 * <br>
 * then {Create TICKLE, set Tickle_Message_Type = 'State ID Missing',
 * <p>
 * {if the client is the focus child in a case (or the case in focus) and
 * CHILD_CLIENT&gt;CASE.End_Date = null,<br>
 * set TICKLE.Affected_By_Case_Or_Referral_Id = STATE_ID_NUMBER&gt;CLIENT&gt;CHILD_CLIENT&gt;CASE.Id
 * of that case<br>
 * and TICKLE.Affected_By_Code = 'CC' and TICKLE.Affected_By_Other_Id =
 * STATE_ID_NUMBER&gt;CLIENT.Id.,<br>
 * set TICKLE.Due_Date = CASE &gt; ASSIGNMENT (where Type _Of_Assignment_Code = 'P' or<br>
 * (Type _Of_Assignment_Code = 'S' and Secondary_Assignment_role_type = 'ICPC Case Worker')<br>
 * county change date +30 days.
 * <p>
 * Else If the client is in a referral (or the referral in focus) and REFERRAL.Closure_Date =
 * null,<br>
 * set TICKLE.Affected_By_Case_Or_Referral_Id =
 * STATE_ID_NUMBER&gt;CLIENT&gt;CHILD_CLIENT&gt;REFERRAL.Id<br>
 * and TICKLE.Affected_By_Code = 'RL' and
 * </p>
 *
 * --- Do not implement part of DocTool rule that describes 'deleting' a reminder. That is out of
 * the context for HotLine 1.0.<br>
 * --- Do not implement or consider any mention of Secondary Assignments. Secondary Assignments are
 * not created by /referrals/POST for HotLine 1.0.<br>
 * --- Do not implement or consider any mention of Assignment Disposition. Assignments will not be
 * dispositioned by /referrals/POST for HotLine 1.0.<br>
 *
 * @author CWDS API Team
 */
public class R05443AndR03341StateIdMissing {

  private static final Logger LOGGER = LoggerFactory.getLogger(R05443AndR03341StateIdMissing.class);

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  private static final String REFERRAL_REFERRALCLIENT = "RL";
  private static final String DEFAULT_TRUE_INDICATOR = "Y";
  private static final short STATE_ID_MISSING = (short) 2062;
  private static final int YEARS_LIMIT = 26;
  private static final int TICKLE_DAYS_SHIFT = 30;

  private ClientDao clientDao;
  private ReferralDao referralDao;
  private ReferralClientDao referralClientDao;
  private StateIdDao stateIdDao;
  private TickleService tickleService;


  /**
   * @param clientDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Client}
   *        objects
   * @param referralDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects
   * @param referralClientDao - The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ReferralClient} objects
   * @param stateIdDao - The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.StateId}
   *        objects
   * @param tickleService tickleService
   */
  @Inject
  public R05443AndR03341StateIdMissing(ClientDao clientDao, ReferralDao referralDao,
      ReferralClientDao referralClientDao, StateIdDao stateIdDao, TickleService tickleService) {
    this.clientDao = clientDao;
    this.referralDao = referralDao;
    this.referralClientDao = referralClientDao;
    this.stateIdDao = stateIdDao;
    this.tickleService = tickleService;
  }

  /**
   * @param postedScreeningToReferral - postedScreeningToReferral
   * @throws ServiceException - ServiceException
   */
  public void stateIdMissing(PostedScreeningToReferral postedScreeningToReferral) {
    Set<Participant> participants = postedScreeningToReferral.getParticipants();
    Referral referral = referralDao.find(postedScreeningToReferral.getReferralId());
    for (Participant participant : participants) {
      if (isClientAccepted(participant)) {
        String legacyId = participant.getLegacyDescriptor().getId();
        if (legacyId == null) {
          return;
        }
        Client client = clientDao.find(legacyId);

        if (client == null) {
          return;
        }

        final Integer estimatedAgeInYears = getEstimatedAgeInYears(referral, client);
        final Integer years = getYearsFromDob(participant);

        if (isLimitExceeded(years) || isLimitExceeded(estimatedAgeInYears)) {
          final List<StateId> stateIds = stateIdDao.findAllByClientId(client.getId());
          boolean hasStateIdWithNoEndDate = isHasStateIdWithNoEndDate(stateIds);

          processTickle(referral, client, hasStateIdWithNoEndDate);
        }
      }
    }
  }

  private void processTickle(Referral referral, Client client, boolean hasStateIdWithNoEndDate) {
    if (!hasStateIdWithNoEndDate && referral.getClosureDate() == null) {
      Calendar tickleDate = Calendar.getInstance();
      tickleDate.setTime(client.getCreationDate());
      tickleDate.add(Calendar.DATE, TICKLE_DAYS_SHIFT);

      createTickle(referral, client, tickleDate);
    }
  }

  private boolean isHasStateIdWithNoEndDate(List<StateId> stateIds) {
    boolean hasStateIdWithNoEndDate = false;
    for (StateId stateId : stateIds) {
      if (stateId.getEndDate() == null) {
        hasStateIdWithNoEndDate = true;
        break;
      }
    }
    return hasStateIdWithNoEndDate;
  }

  private boolean isLimitExceeded(Integer years) {
    return years != null && years < YEARS_LIMIT;
  }

  private Integer getYearsFromDob(Participant participant) {
    Integer years = null;
    String dateOfBirth = participant.getDateOfBirth();
    if (dateOfBirth != null) {
      years = ReminderHelper.checkForAgeDifference(dateOfBirth);
    }
    return years;
  }

  private boolean isClientAccepted(Participant participant) {
    return ParticipantValidator.hasVictimRole(participant)
        || ParticipantValidator.isPerpetrator(participant);
  }

  private Integer getEstimatedAgeInYears(Referral referral, Client client) {
    if (client != null && DEFAULT_TRUE_INDICATOR.equals(client.getEstimatedDobCode())) {
      ReferralClient.PrimaryKey primaryKey =
          new ReferralClient.PrimaryKey(referral.getPrimaryKey(), client.getPrimaryKey());
      ReferralClient referralClient = referralClientDao.find(primaryKey);
      if (referralClient != null) {
        String agePeriodCode = referralClient.getAgePeriodCode();
        AgeUnit from = null;

        if (StringUtils.isNotBlank(agePeriodCode)) {
          from = AgeUnit.valueOf(referralClient.getAgePeriodCode());
        }

        if (from != null) {
          return AgeUnit.convertTo(referralClient.getAgeNumber(), from, AgeUnit.Y);
        }
      }
    }
    return null;
  }

  private void createTickle(Referral referral, Client client, Calendar dueDate) {
    final gov.ca.cwds.rest.api.domain.cms.Tickle tickle =
        new gov.ca.cwds.rest.api.domain.cms.Tickle(referral.getId(), REFERRAL_REFERRALCLIENT,
            client.getId(), null, dateFormat.format(dueDate.getTime()),
            referral.getScreenerNoteText(), STATE_ID_MISSING);
    tickleService.create(tickle);
    LOGGER.info("stateIdMissing reminder is created");
  }

}
