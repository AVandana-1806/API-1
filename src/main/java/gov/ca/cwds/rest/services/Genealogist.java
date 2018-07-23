package gov.ca.cwds.rest.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;

public class Genealogist {
  ClientDao clientDao;

  /**
   * Constructor
   *
   * @param clientDao The Client Dao object
   */
  @Inject
  public Genealogist(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  public Relationship buildRelationships(List<RelationshipWrapper> relationships, String clientId) {
    if (relationships == null || clientId == null) {
      return new Relationship();
    }

    Set<RelationshipTo> relations = new HashSet<>(relationships.size());
    for (RelationshipWrapper relationship : relationships) {
      boolean clientIsPrimary = clientId.equals(relationship.getPrimaryLegacyId());
      relations.add(createBar(relationship, clientIsPrimary));
    }

    Client primaryClient = findClient(clientId);
    Relationship relationship;
    if (primaryClient != null) {
      relationship = new Relationship(primaryClient, relations);
    } else {
      relationship = new Relationship();
    }
    return relationship;
  }

  private RelationshipTo createBar(RelationshipWrapper relationship, boolean clientIsPrimary) {
    RelationshipTo relationshipTo;


    if (clientIsPrimary) {
      boolean relatedPersonSensitive =
          StringUtils.equalsAnyIgnoreCase(relationship.getSecondarySensitive_Indicator(), "S")
              ? Boolean.TRUE
              : Boolean.FALSE;
      boolean relatedPersonSealed =
          StringUtils.equalsAnyIgnoreCase(relationship.getSecondarySensitive_Indicator(), "R")
              ? Boolean.TRUE
              : Boolean.FALSE;
      relationshipTo = createRelationShipTo(relationship.getSecondaryLegacyId(),
          relationship.getPrimaryRelationshipCode(), relationship.getSecondaryRelationshipCode(),
          relationship.getSecondaryFirstName(), relationship.getSecondaryMiddleName(),
          relationship.getSecondaryLastName(), relationship.getSecondaryNameSuffix(),
          relationship.getSecondaryGenderCode(), relationship.getSecondaryDateOfBirth(), (short) 0,
          "", relationship.getSecondaryDateOfDeath(), relationship.getRelationshipStartDate(),
          relationship.getRelationshipEndDate(), relationship.getAbsentParentCode(),
          relationship.getSameHomeCode(), "", relatedPersonSensitive, relatedPersonSealed);
    } else {
      boolean relatedPersonSensitive =
          StringUtils.equalsAnyIgnoreCase(relationship.getPrimarySensitive_Indicator(), "S")
              ? Boolean.TRUE
              : Boolean.FALSE;
      boolean relatedPersonSealed =
          StringUtils.equalsAnyIgnoreCase(relationship.getPrimarySensitive_Indicator(), "R")
              ? Boolean.TRUE
              : Boolean.FALSE;
      relationshipTo = createRelationShipTo(relationship.getPrimaryLegacyId(),
          relationship.getSecondaryRelationshipCode(), relationship.getPrimaryRelationshipCode(),
          relationship.getPrimaryFirstName(), relationship.getPrimaryMiddleName(),
          relationship.getPrimaryLastName(), relationship.getPrimaryNameSuffix(),
          relationship.getPrimaryGenderCode(), relationship.getPrimaryDateOfBirth(), (short) 0, "",
          relationship.getPrimaryDateOfDeath(), relationship.getRelationshipStartDate(),
          relationship.getRelationshipEndDate(), relationship.getAbsentParentCode(),
          relationship.getSameHomeCode(), "", relatedPersonSensitive, relatedPersonSealed);
    }
    return relationshipTo;
  }

  private RelationshipTo createRelationShipTo(String relationId, String primaryRelationCode,
      String secondaryRelation, String secondaryFirstname, String secondaryMiddleName,
      String secodnaryLastName, String nameSuffix, String relatedGender, String relatedDateOfBirth,
      Short relatedAge, String relatedAgeUnit, String relatedDateOfDeath,
      String relationshipStartDate, String relationshipEndDate, String absentParentCode,
      String sameHomeCode, String relationContext, Boolean relatedPersonSensitive,
      Boolean relatedPersonSealed) {
    return new RelationshipTo(secondaryFirstname, secondaryMiddleName, secodnaryLastName,
        nameSuffix, relatedGender, relatedDateOfBirth,
        relatedDateOfDeath, relationshipStartDate, relationshipEndDate, absentParentCode,
        sameHomeCode, secondaryRelation, relationContext, primaryRelationCode, relationId,
        relatedPersonSensitive, relatedPersonSealed);

  }

  private Client findClient(String id) {
    return this.clientDao.find(id);
  }
}
