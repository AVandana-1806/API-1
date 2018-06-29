package gov.ca.cwds.rest.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import gov.ca.cwds.cms.data.access.service.impl.SafetyAlertService;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.legacy.cms.dao.CountiesDao;
import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertActivationReasonTypeDao;
import gov.ca.cwds.data.legacy.cms.entity.LongText;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.util.ParticipantUtils;

/***
 * This class is not longer used to save the safety Alerts for {@link Screening}, But can be used
 * later once we start saving it {@link SafetyAlert} table.
 * 
 * @author CWDS API Team
 *
 */
public class ReferralSafetyAlertsService {

  @Inject
  private ReferralDao referralDao;

  @Inject
  private CountiesDao countiesDao;

  @Inject
  private SafetyAlertActivationReasonTypeDao safetyAlertActivationReasonTypeDao;

  @Inject
  private SafetyAlertService safetyAlertService;

  /**
   * @param screeningToReferral - screeningToReferral
   * @param referralId - referralId
   * @param clientParticipants - clientParticipants
   * @return the saved safety Alerts
   */
  public List<SafetyAlert> create(ScreeningToReferral screeningToReferral, String referralId,
      ClientParticipants clientParticipants) {
    Referral referrral = referralDao.find(referralId);

    Participant victimParticipant = getValidVictim(clientParticipants);
    List<SafetyAlert> safetyAlerts = new ArrayList<>();
    if (screeningToReferral.getAlerts() != null && !screeningToReferral.getAlerts().isEmpty()
        && victimParticipant != null) {
      for (Short safetyAlertReasonType : screeningToReferral.getAlerts()) {
        SafetyAlert alert = new SafetyAlert();
        setSafetAlertFields(referrral, victimParticipant, safetyAlertReasonType, alert);
        safetyAlerts.add(alert);
      }
      safetyAlertService.updateSafetyAlertsByClientId(
          victimParticipant.getLegacyDescriptor().getId(), safetyAlerts);
    }
    return safetyAlerts;
  }

  private void setSafetAlertFields(Referral referrral, Participant victimParticipant,
      Short safetyAlertReasonType, SafetyAlert alert) {
    alert.setFkClientId(victimParticipant.getLegacyDescriptor().getId());
    alert.setActivationDate(LocalDate.now());
    SystemCode systemCode = SystemCodeCache.global().getSystemCode(referrral.getGovtEntityType());
    alert.setActivationGovernmentEntityType(countiesDao.findByLogicalId(systemCode.getLogicalId()));
    LongText longText = new LongText();
    longText.setIdentifier(referrral.getScreenerNoteText());
    alert.setActivationExplanationText(longText);
    alert.setActivationReasonType(
        safetyAlertActivationReasonTypeDao.findBySystemId(safetyAlertReasonType));
  }

  private Participant getValidVictim(ClientParticipants clientParticipants) {
    List<Participant> participants =
        (List<Participant>) ParticipantUtils.getVictims(clientParticipants.getParticipants());
    return participants.isEmpty() ? null : participants.get(0);
  }

  /**
   * @param referralDao - referralDao
   */
  public void setReferralDao(ReferralDao referralDao) {
    this.referralDao = referralDao;
  }

  /**
   * @param countiesDao - countiesDao
   */
  public void setCountiesDao(CountiesDao countiesDao) {
    this.countiesDao = countiesDao;
  }

  /**
   * @param safetyAlertActivationReasonTypeDao - safetyAlertActivationReasonTypeDao
   */
  public void setSafetyAlertActivationReasonTypeDao(
      SafetyAlertActivationReasonTypeDao safetyAlertActivationReasonTypeDao) {
    this.safetyAlertActivationReasonTypeDao = safetyAlertActivationReasonTypeDao;
  }

  /**
   * @param safetyAlertService - safetyAlertService
   */
  public void setSafetyAlertService(SafetyAlertService safetyAlertService) {
    this.safetyAlertService = safetyAlertService;
  }

}
