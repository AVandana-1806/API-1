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
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.util.ParticipantUtils;

/***
 * 
 * @author CWDS API Team
 *
 */
public class ScreeningSatefyAlertService {

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

    List<Participant> participants =
        (List<Participant>) ParticipantUtils.getVictims(clientParticipants.getParticipants());
    Participant victimParticipant = participants.isEmpty() ? null : participants.get(0);
    List<SafetyAlert> safetyAlerts = new ArrayList<>();
    if (screeningToReferral.getAlerts() != null && !screeningToReferral.getAlerts().isEmpty()
        && victimParticipant != null) {
      for (String intakeCode : screeningToReferral.getAlerts()) {
        SafetyAlert alert = new SafetyAlert();
        alert.setFkClientId(victimParticipant.getLegacyDescriptor().getId());
        alert.setActivationDate(LocalDate.now());
        SystemCode systemCode =
            SystemCodeCache.global().getSystemCode(referrral.getGovtEntityType());
        alert.setActivationGovernmentEntityType(
            countiesDao.findByLogicalId(systemCode.getLogicalId()));
        LongText longText = new LongText();
        longText.setIdentifier(referrral.getScreenerNoteText());
        alert.setActivationExplanationText(longText);
        Short reasonType = IntakeCodeCache.global()
            .getLegacySystemCodeForIntakeCode(SystemCodeCategoryId.SAFETY_ALERTS, intakeCode);
        alert
            .setActivationReasonType(safetyAlertActivationReasonTypeDao.findBySystemId(reasonType));
        safetyAlerts.add(alert);
      }
      safetyAlertService.updateSafetyAlertsByClientId(
          victimParticipant.getLegacyDescriptor().getId(), safetyAlerts);
    }
    return safetyAlerts;
  }

}
