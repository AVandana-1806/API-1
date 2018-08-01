package gov.ca.cwds.rest.services.relationship;

import com.google.inject.Inject;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.fixture.investigation.RelationshipToEntityBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.mapper.RelationshipMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class RelationshipFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipFacade.class);
  private static final RelationshipMapper mapper = RelationshipMapper.INSTANCE;
  private final ParticipantDao participantDao;
  private final ClientRelationshipDao cmsRelationshipDao;
  private final RelationshipDao nsRelationshipDao;
  private final ClientDao cmsClientDao;

  @Inject
  public RelationshipFacade(ParticipantDao participantDao, ClientRelationshipDao cmsRelationshipDao,
      RelationshipDao nsRelationshipDao, ClientDao cmsClientDao) {
    this.participantDao = participantDao;
    this.cmsRelationshipDao = cmsRelationshipDao;
    this.nsRelationshipDao = nsRelationshipDao;
    this.cmsClientDao = cmsClientDao;
  }

  public List<ScreeningRelationshipsWithCandidates> getRelationshipsWithCandidatesByScreeningId(
      String screeningId) {
    if (StringUtils.isEmpty(screeningId)) {
      return Collections.emptyList();
    }

    List<gov.ca.cwds.rest.api.Response> relationshipsResponse = getRelationshipsByScreeningId(
        screeningId);
    if (CollectionUtils.isEmpty(relationshipsResponse)) {
      return Collections.emptyList();
    }

    List<ScreeningRelationship> screeningRelationships = formResponse(relationshipsResponse);
    Set<String> participantIds = getParticipantIds(screeningRelationships);
    List<ParticipantEntity> allParticipants = participantDao.findByIds(participantIds);
    Map<ParticipantEntity, List<ScreeningRelationship>> relationshipsByPrimaryParticipant = getRelationshipsMappedByPrimaryParticipant(
        screeningRelationships, allParticipants);
    List<ScreeningRelationshipsWithCandidates> relationshipsWithCandidates = buildRelationshipsWitCandidates(
        relationshipsByPrimaryParticipant, allParticipants);

    return relationshipsWithCandidates;
  }

  private List<ScreeningRelationshipsWithCandidates> buildRelationshipsWitCandidates(
      Map<ParticipantEntity, List<ScreeningRelationship>> relationshipsByPrimaryParticipant,
      List<ParticipantEntity> allParticipants) {
    if (MapUtils.isEmpty(relationshipsByPrimaryParticipant) || CollectionUtils
        .isEmpty(allParticipants)) {
      return Collections.emptyList();
    }

    List<ScreeningRelationshipsWithCandidates> relationshipsWithCandidates = new ArrayList<>();
    relationshipsByPrimaryParticipant.forEach((participant, relationships) -> {
      if (CollectionUtils.isNotEmpty(relationships)) {
        relationshipsWithCandidates
            .add(buildRelationshipWithCandidates(participant, relationships, allParticipants));
      }
    });
    return relationshipsWithCandidates;
  }

  private ScreeningRelationshipsWithCandidates buildRelationshipWithCandidates(
      ParticipantEntity participant, List<ScreeningRelationship> relationships,
      List<ParticipantEntity> allParticipants) {
    List<RelationshipTo> relationshipTos = getRelationshipsTo(relationships, allParticipants);
    return null;
  }

  private List<RelationshipTo> getRelationshipsTo(List<ScreeningRelationship> relationships,
      List<ParticipantEntity> allParticipants) {
    if (CollectionUtils.isEmpty(relationships) || CollectionUtils.isEmpty(allParticipants)) {
      return Collections.emptyList();
    }

    List<RelationshipTo> relationshipTos = new ArrayList<>();
    relationships.forEach(e ->
        relationshipTos.add(buildRelationshipTo(e, allParticipants))
    );
    return relationshipTos;
  }

  private RelationshipTo buildRelationshipTo(ScreeningRelationship relationship,
      List<ParticipantEntity> allParticipants) {
    ParticipantEntity relatedParticipant = allParticipants.stream()
        .filter(e -> relationship.getRelativeId().equals(e.getId())).findFirst().orElse(null);

    // TODO: implement setters
    RelationshipToEntityBuilder builder = new RelationshipToEntityBuilder();
    builder.setTableName("");
    builder.setId("");
    builder.setRelatedFirstName("");
    builder.setRelatedMiddleName("");
    builder.setRelatedLastName("");
    builder.setRelatedNameSuffix("");
    builder.setRelatedGenderCode("");
    builder.setRelationship("");
    builder.setRelationshipToPerson("");
    builder.setCmsRecordDescriptor(new CmsRecordDescriptor());
    builder.setRelationshipContext("");
    builder.setAbsentParentCode("");
    builder.setSameHomeCode("");
    builder.setRelatedDateOfBirth("");
    builder.setRelatedDateOfDeath("");
    builder.setRelationshipStartDate("");
    builder.setRelationshipEndDate("");
    builder.setRelatedPersonSensitive(false);
    builder.setRelatedPersonSealed(false);

    return builder.build();
  }

  private Map<ParticipantEntity, List<ScreeningRelationship>> getRelationshipsMappedByPrimaryParticipant(
      List<ScreeningRelationship> screeningRelationships,
      List<ParticipantEntity> allParticipants) {
    if (CollectionUtils.isEmpty(screeningRelationships) || CollectionUtils
        .isEmpty(allParticipants)) {
      return Collections.emptyMap();
    }

    Map<ParticipantEntity, List<ScreeningRelationship>> mappedRelationships = new HashMap<>();
    screeningRelationships.forEach(relationship -> {
      Optional<ParticipantEntity> primaryParticipant = allParticipants.stream()
          .filter(participant -> relationship.getClientId().equals(participant.getId()))
          .findFirst();
      if (primaryParticipant.isPresent()) {
        if (mappedRelationships.get(primaryParticipant.get()) == null) {
          mappedRelationships.get(primaryParticipant.get()).add(relationship);
        }
      }
    });
    return mappedRelationships;
  }

  private Set<String> getParticipantIds(List<ScreeningRelationship> screeningRelationships) {
    if (CollectionUtils.isEmpty(screeningRelationships)) {
      return Collections.emptySet();
    }

    Set<String> participantIds = new HashSet<>();
    screeningRelationships.forEach(e -> {
      participantIds.add(e.getClientId());
      participantIds.add(e.getRelativeId());
    });

    return participantIds;
  }

  private List<ScreeningRelationship> formResponse(
      List<gov.ca.cwds.rest.api.Response> relationshipsResponse) {
    List<ScreeningRelationship> screeningRelationships = new ArrayList<>();
    relationshipsResponse.forEach(e -> screeningRelationships.add((ScreeningRelationship) e));
    return screeningRelationships;
  }

  public List<gov.ca.cwds.rest.api.Response> getRelationshipsByScreeningId(String screeningId) {
    if (StringUtils.isEmpty(screeningId)) {
      return Collections.emptyList();
    }

    // get participants by screeningId
    Set<String> legacyClientIds = getLegacyClientIdsByScreeningId(screeningId);
    // get relationships from legacy
    Set<ClientRelationship> lagacyRelationships = getCmsRelationships(legacyClientIds);
    // get relationships from pgsql
    List<Relationship> nsRelationships = getNsRelationships(screeningId);

    List<gov.ca.cwds.rest.api.Response> result = new ArrayList<>();

    // compare
    List<ClientRelationship> shouldBeUpdated =
        getRelationshipsThatShouldBeUpdated(lagacyRelationships, nsRelationships);
    List<ClientRelationship> shouldBeCreated =
        getRelationshipsThatShouldBeCreated(lagacyRelationships, nsRelationships);
    result.addAll(createRelationships(shouldBeCreated, screeningId));
    result.addAll(updateRelationships(shouldBeUpdated));
    result = getRelationshipsThatShouldNotBeUpdated(result, nsRelationships);

    // select and return
    return result;
  }

  private List<ScreeningRelationship> updateRelationships(
      List<ClientRelationship> shouldBeUpdated) {
    Date updatedAt = RequestExecutionContext.instance().getRequestStartTime();
    if (CollectionUtils.isEmpty(shouldBeUpdated)) {
      return new ArrayList<>();
    }
    LOGGER.info("shouldBeUpdated {}", shouldBeUpdated);
    List<ScreeningRelationship> result = new ArrayList<>();
    for (ClientRelationship clientRelationship : shouldBeUpdated) {
      Relationship managed = nsRelationshipDao.getByLegacyId(clientRelationship.getId());
      if (managed != null) {
        managed.setRelationshipType(clientRelationship.getClientRelationshipType());
        managed.setStartDate(clientRelationship.getStartDate());
        managed.setAbsentParentIndicator("Y".equals(clientRelationship.getAbsentParentCode()));
        managed.setSameHomeStatus("Y".equals(clientRelationship.getSameHomeCode()));
        managed.setEndDate(clientRelationship.getEndDate());
        managed.setUpdatedAt(updatedAt);
        managed = nsRelationshipDao.update(managed);
        result.add(mapper.map(managed));
      }
    }
    return result;
  }

  private List<ScreeningRelationship> createRelationships(List<ClientRelationship> shouldBeCreated,
      String screeningId) {
    if (CollectionUtils.isEmpty(shouldBeCreated)) {
      return new ArrayList<>();
    }
    LOGGER.info("shouldBeCreated {}", shouldBeCreated);

    Date createdAt = RequestExecutionContext.instance().getRequestStartTime();
    List<ScreeningRelationship> result = new ArrayList<>();
    Set<String> clientIdSet = participantDao.findLegacyIdListByScreeningId(screeningId);

    for (ClientRelationship clientRelationship : shouldBeCreated) {
      ParticipantEntity participantEntity1;
      ParticipantEntity participantEntity2;
      if (!clientIdSet.contains(clientRelationship.getPrimaryClientId())) {
        Client client = cmsClientDao.find(clientRelationship.getPrimaryClientId());
        participantEntity1 = participantDao.create(new ParticipantEntity(
            null,
            client.getBirthDate(),
            client.getDeathDate(),
            client.getFirstName(),
            client.getGender(),
            client.getLastName(),
            client.getSsn(),
            null,
            client.getId(),
            null,
            null,
            client.getMiddleName(),
            client.getNameSuffix(),
            null,
            null,
            null,
            Boolean.FALSE,
            Boolean.FALSE,
            null,
            null,
            null
        ));
      } else {
        participantEntity1 = participantDao
            .findByScreeningIdAndLegacyId(screeningId, clientRelationship.getPrimaryClientId());
      }
      if (!clientIdSet.contains(clientRelationship.getSecondaryClientId())) {
        Client client = cmsClientDao.find(clientRelationship.getSecondaryClientId());
        participantEntity2 = participantDao.create(new ParticipantEntity(
            null,
            client.getBirthDate(),
            client.getDeathDate(),
            client.getFirstName(),
            client.getGender(),
            client.getLastName(),
            client.getSsn(),
            null,
            client.getId(),
            null,
            null,
            client.getMiddleName(),
            client.getNameSuffix(),
            null,
            null,
            null,
            Boolean.FALSE,
            Boolean.FALSE,
            null,
            null,
            null
        ));
      } else {
        participantEntity2 = participantDao
            .findByScreeningIdAndLegacyId(screeningId, clientRelationship.getSecondaryClientId());
      }
      Relationship newRelationship = new Relationship();
      newRelationship.setClientId(participantEntity1.getId());
      newRelationship.setRelativeId(participantEntity2.getId());
      newRelationship.setRelationshipType(clientRelationship.getClientRelationshipType());
      newRelationship.setCreatedAt(createdAt);
      newRelationship.setUpdatedAt(createdAt);
      newRelationship
          .setAbsentParentIndicator("Y".equals(clientRelationship.getAbsentParentCode()));
      newRelationship.setSameHomeStatus("Y".equals(clientRelationship.getSameHomeCode()));
      newRelationship.setLegacyId(clientRelationship.getId());
      newRelationship.setStartDate(clientRelationship.getStartDate());
      newRelationship.setEndDate(clientRelationship.getEndDate());

      newRelationship = nsRelationshipDao.create(newRelationship);
      result.add(mapper.map(newRelationship));
    }
    return result;
  }

  private List<ClientRelationship> getRelationshipsThatShouldBeCreated(
      final Set<ClientRelationship> lagacyRelationships, List<Relationship> nsRelationships) {
    LOGGER.info("lagacyRelationships {}", lagacyRelationships);
    LOGGER.info("nsRelationships {}", nsRelationships);
    List<ClientRelationship> relationshipsToCreate = new ArrayList<>();
    if (CollectionUtils.isEmpty(lagacyRelationships)) {
      return relationshipsToCreate;
    }

    lagacyRelationships.forEach(e -> {
      if (CollectionUtils.isEmpty(nsRelationships)) {
        relationshipsToCreate.add(e);
      } else {
        Optional<Relationship> clientRelationship = nsRelationships.stream()
            .filter(b -> e.getId().equals(b.getLegacyId())).findFirst();
        if (!clientRelationship.isPresent()) {
          relationshipsToCreate.add(e);
        }
      }
    });
    return relationshipsToCreate;
  }

  private List<ClientRelationship> getRelationshipsThatShouldBeUpdated(
      final Set<ClientRelationship> lagacyRelationships, List<Relationship> nsRelationships) {
    if (CollectionUtils.isEmpty(nsRelationships) || CollectionUtils.isEmpty(lagacyRelationships)) {
      return new ArrayList<>();
    }

    List<ClientRelationship> relationshipsToUpdate = new ArrayList<>();
    lagacyRelationships.forEach(e -> {
      boolean update = false;
      for (Relationship relationship : nsRelationships) {
        if (e.getId().equals(relationship.getLegacyId())) {
          if (e.getLastUpdatedTime().getTime() > relationship.getUpdatedAt()
              .getTime()) {
            update = true;
          }
          break;
        }
      }
      if (update) {
        relationshipsToUpdate.add(e);
      }
    });
    return relationshipsToUpdate;
  }

  private Set<String> getLegacyClientIdsByScreeningId(String screeningId) {
    return participantDao.findLegacyIdListByScreeningId(screeningId);
  }

  private List<Relationship> getNsRelationships(String screeningId) {
    return nsRelationshipDao.getRelationshipsByScreeningId(screeningId);
  }

  private Set<ClientRelationship> getCmsRelationships(Set<String> legacyClientIds) {
    LOGGER.info("legacyClientIds {}", legacyClientIds);
    Set<ClientRelationship> relationshipListcms = new HashSet<>();
    Map<String, Collection<ClientRelationship>> primaryRelationshipMap =
        cmsRelationshipDao.findByPrimaryClientIds(legacyClientIds);
    for (Map.Entry<String, Collection<ClientRelationship>> relationshipMapEntry : primaryRelationshipMap
        .entrySet()) {
      relationshipListcms.addAll(relationshipMapEntry.getValue());
    }
    Map<String, Collection<ClientRelationship>> secondaryRelationshipMap =
        cmsRelationshipDao.findBySecondaryClientIds(legacyClientIds);
    for (Map.Entry<String, Collection<ClientRelationship>> relationshipMapEntry : secondaryRelationshipMap
        .entrySet()) {
      relationshipListcms.addAll(relationshipMapEntry.getValue());
    }
    return relationshipListcms;
  }

  private List<gov.ca.cwds.rest.api.Response> getRelationshipsThatShouldNotBeUpdated(
      List<gov.ca.cwds.rest.api.Response> list1, List<Relationship> list2) {
    List<gov.ca.cwds.rest.api.Response> result = new ArrayList<>(list1);
    for (Relationship relationship : list2) {
      boolean exist = false;
      for (gov.ca.cwds.rest.api.Response response : list1) {
        ScreeningRelationship screeningRelationship = (ScreeningRelationship) response;
        if (screeningRelationship.getId().equals(relationship.getId())) {
          exist = true;
          break;
        }
      }
      if (!exist) {
        result.add(mapper.map(relationship));
      }
    }
    return result;
  }
}
