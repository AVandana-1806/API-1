package gov.ca.cwds.rest.services.hoi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.hoi.HOIPerson;
import gov.ca.cwds.rest.api.domain.hoi.HOIReporter;
import gov.ca.cwds.rest.api.domain.hoi.HOISocialWorker;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.util.CmsRecordUtils;

/**
 * @author CWDS API Team
 */
public final class HOIPersonFactory {

  /**
   * @param participantEntity NS ParticipantEntity
   * @param participantLegacyDescriptor NS LegacyDescriptorEntity
   * @return HOIPerson
   */
  HOIPerson buildHOIPerson(ParticipantEntity participantEntity,
      LegacyDescriptorEntity participantLegacyDescriptor) {
    final HOIPerson result = new HOIPerson(participantEntity);
    if (participantLegacyDescriptor != null) {
      result.setLegacyDescriptor(new LegacyDescriptor(participantLegacyDescriptor));
    }
    return result;
  }

  /**
   * @param participantEntity ns participant
   * @param legacyDescriptor domain LegacyDescriptor
   * @return HOIReporter instance; can be null if the given participant has no reporter role
   */
  HOIReporter buidHOIReporter(ParticipantEntity participantEntity,
      LegacyDescriptor legacyDescriptor) {
    final Set<String> roles = parseRoles(participantEntity.getRoles());
    final HOIReporter.Role reporterRole = findReporterRole(roles);
    if (reporterRole == null) {
      return null;
    }
    return new HOIReporter(reporterRole, participantEntity.getId(),
        participantEntity.getFirstName(), participantEntity.getLastName(),
        participantEntity.getNameSuffix(), legacyDescriptor);
  }

  /**
   * @param staffPerson StaffPerson CMS StaffPerson entity
   * @return corresponding instance of HOISocialWorker or null
   */
  HOISocialWorker buildHOISocialWorker(StaffPerson staffPerson) {
    final CmsRecordDescriptor cmsRecordDescriptor =
        CmsRecordUtils.createLegacyDescriptor(staffPerson.getId(), LegacyTable.STAFF_PERSON);

    final LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(cmsRecordDescriptor.getId(), cmsRecordDescriptor.getUiId(), null,
            cmsRecordDescriptor.getTableName(), cmsRecordDescriptor.getTableDescription());

    return new HOISocialWorker(staffPerson.getId(), staffPerson.getFirstName(),
        staffPerson.getLastName(), staffPerson.getNameSuffix(), legacyDescriptor);
  }

  /**
   * @param roles string array
   * @return set of roles parsed from the input string
   */
  private Set<String> parseRoles(String[] roles) {
    return roles == null ? new HashSet<>() : new HashSet<>(Arrays.asList(roles));
  }

  /**
   * @param roles set of roles
   * @return the first found HOIReporter.Role or null if there is no reporter role in the given set
   */
  private HOIReporter.Role findReporterRole(Set<String> roles) {
    for (String role : roles) {
      final HOIReporter.Role reporterRole = HOIReporter.Role.fromString(role);
      if (reporterRole != null) {
        return reporterRole;
      }
    }
    return null;
  }

}
