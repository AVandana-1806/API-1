package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.LimitedAccessType;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.hoi.HOIRelatedPerson;

/**
 *
 * @author CWDS API Team
 */
final class HOIParentsFactory {

  HOIParentsFactory() {
    // no-op
  }

  List<HOIRelatedPerson> buildParentsByPrimaryRelationship(Client client, HOICasesData hcd) {
    List<HOIRelatedPerson> parents = new ArrayList<>();
    if (hcd.getRelationshipsByPrimaryClients().containsKey(client.getId())) {
      Collection<ClientRelationship> relationships = hcd.getRelationshipsByPrimaryClients()
          .get(client.getId());
      for (ClientRelationship relation : relationships) {
        Short type = relation.getClientRelationshipType();
        if (HOIRelationshipTypeService.isRelationTypeChild(type)) {
          Client relatedSecondaryClient = hcd.getAllClients().get(relation.getSecondaryClientId());
          addRelatedParent(relatedSecondaryClient, type, parents);
        }
      }
    }
    return parents;
  }

  List<HOIRelatedPerson> buildParentsBySecondaryRelationship(Client client, HOICasesData hcd) {
    List<HOIRelatedPerson> parents = new ArrayList<>();
    if (hcd.getRelationshipsBySecondaryClients().containsKey(client.getId())) {
      Collection<ClientRelationship> relationships = hcd.getRelationshipsBySecondaryClients()
          .get(client.getId());
      for (ClientRelationship relation : relationships) {
        Short type = relation.getClientRelationshipType();
        if (HOIRelationshipTypeService.isRelationTypeParent(type)) {
          Client relatedPrimaryClient = hcd.getAllClients().get(relation.getPrimaryClientId());
          addRelatedParent(relatedPrimaryClient, type, parents);
         }
      }
    }
    return parents;
  }

  private HOIRelatedPerson buildHOIRelatedPerson(Client client, Short type) {
    String clientId = client.getId();
    SystemCodeDescriptor relationship = new SystemCodeDescriptor(type,
        SystemCodeCache.global().getSystemCodeShortDescription(type));
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(clientId, CmsKeyIdGenerator.getUIIdentifierFromKey(clientId),
            new DateTime(client.getLastUpdatedTime()), LegacyTable.CLIENT.getName(),
            LegacyTable.CLIENT.getDescription());
    HOIRelatedPerson person = new HOIRelatedPerson();
    person.setId(clientId);
    person.setFirstName(client.getFirstName());
    person.setLastName(client.getLastName());
    person.setNameSuffix(client.getNameSuffix());
    person.setRelationship(relationship);
    person.setLimitedAccessType(LimitedAccessType.getByValue(client.getSensitivityIndicator()));
    person.setLegacyDescriptor(legacyDescriptor);
    return person;
  }
  
  private void addRelatedParent(Client relatedClient, Short type, List<HOIRelatedPerson> parents) {
    if (relatedClient != null) {
      parents.add(buildHOIRelatedPerson(relatedClient, type));
    }
  }
}
