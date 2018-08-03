package gov.ca.cwds.rest.services.hoi;

import java.util.Map;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;

/**
 * @author CWDS API Team
 */
public final class HOIScreeningFactory {

  @Inject
  HOIPersonFactory hoiPersonFactory;

  /**
   * @param screeningEntity NS ScreeningEntity
   * @param countyIntakeLOVCodeEntity NS IntakeLOVCodeEntity
   * @param participantLegacyDescriptors map where key is a participant Id and value is a
   *        LegacyDescriptorEntity
   * @param staffPerson CMS StaffPerson entity corresponding to the screeningEntity.assigneeStaffId
   * @return HOIScreening
   */
  HOIScreening buildHOIScreening(ScreeningEntity screeningEntity,
      IntakeLOVCodeEntity countyIntakeLOVCodeEntity,
      Map<String, LegacyDescriptorEntity> participantLegacyDescriptors, StaffPerson staffPerson) {
    final HOIScreening result = new HOIScreening(screeningEntity);

    if (countyIntakeLOVCodeEntity != null) {
      result.setCounty(new SystemCodeDescriptor(countyIntakeLOVCodeEntity.getLgSysId().shortValue(),
          countyIntakeLOVCodeEntity.getIntakeDisplay()));
    }

    if (screeningEntity.getParticipants() != null) {
      for (ParticipantEntity participantEntity : screeningEntity.getParticipants()) {
        final HOIPerson participant = hoiPersonFactory.buildHOIPerson(participantEntity,
            participantLegacyDescriptors.get(participantEntity.getId()));
        result.getAllPeople().add(participant);

        if (result.getReporter() == null) {
          result.setReporter(hoiPersonFactory.buidHOIReporter(participantEntity,
              participant.getLegacyDescriptor()));
        }
      }
    }

    if (staffPerson != null) {
      result.setAssignedSocialWorker(hoiPersonFactory.buildHOISocialWorker(staffPerson));
    }

    return result;
  }

}
