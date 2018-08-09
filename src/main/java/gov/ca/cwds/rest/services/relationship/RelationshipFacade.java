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
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.CandidateTo;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.CandidateTo.CandidateToBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.RelatedTo;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.RelatedTo.RelatedToBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.ScreeningRelationshipsWithCandidatesBuilder;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
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
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelationshipFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipFacade.class);
  private static final RelationshipMapper mapper = RelationshipMapper.INSTANCE;
  private final Map<String, gov.ca.cwds.rest.api.domain.cms.SystemCode> codesMappedByDescription = new HashMap<>();
  private final Map<Short, gov.ca.cwds.rest.api.domain.cms.SystemCode> codesMappedById = new HashMap<>();

  private final ParticipantDao participantDao;
  private final ClientRelationshipDao cmsRelationshipDao;
  private final RelationshipDao nsRelationshipDao;
  private final ClientDao cmsClientDao;
  private final SystemCodeCache systemCodeDao;

  @Inject
  public RelationshipFacade(ParticipantDao participantDao, ClientRelationshipDao cmsRelationshipDao,
      RelationshipDao nsRelationshipDao, ClientDao cmsClientDao) {
    this.participantDao = participantDao;
    this.cmsRelationshipDao = cmsRelationshipDao;
    this.nsRelationshipDao = nsRelationshipDao;
    this.cmsClientDao = cmsClientDao;
    this.systemCodeDao = SystemCodeCache.global();
    initSystemCodes();
  }

  private void initSystemCodes() {
    if (!MapUtils.isEmpty(codesMappedByDescription)) {
      return;
    }

    Set<gov.ca.cwds.rest.api.domain.cms.SystemCode> systemCodes = this.systemCodeDao
        .getSystemCodesForMeta("CLNTRELC");

    if (MapUtils.isEmpty(codesMappedByDescription)) {
      systemCodes.forEach(e -> codesMappedByDescription.put(e.getShortDescription(), e));
      systemCodes.forEach(e -> codesMappedById.put(e.getSystemId(), e));
    }
  }

  public List<gov.ca.cwds.rest.api.Response> getRelationshipsWithCandidatesByScreeningId(
      String screeningId) {
    if (StringUtils.isEmpty(screeningId)) {
      return Collections.emptyList();
    }

    List<gov.ca.cwds.rest.api.Response> relationshipsResponse = getRelationshipsByScreeningId(
        screeningId);

    List<ScreeningRelationship> screeningRelationships = formResponse(relationshipsResponse);
    Set<String> participantIds = getParticipantIds(screeningRelationships);
    List<ParticipantEntity> allParticipants = participantDao.findByIds(participantIds);
    allParticipants.addAll(participantDao.getByScreeningId(screeningId));
    Map<ParticipantEntity, List<ScreeningRelationship>> relationshipsByPrimaryParticipant = getRelationshipsMappedByPrimaryParticipant(
        screeningRelationships, allParticipants);

    return buildRelationshipsWitCandidates(
        relationshipsByPrimaryParticipant, allParticipants, screeningId, screeningRelationships);
  }

  private List<gov.ca.cwds.rest.api.Response> buildRelationshipsWitCandidates(
      Map<ParticipantEntity, List<ScreeningRelationship>> relationshipsByPrimaryParticipant,
      List<ParticipantEntity> allParticipants, String screeningId,
      List<ScreeningRelationship> allScreeningRelationships) {
    if (CollectionUtils.isEmpty(allParticipants)) {
      return Collections.emptyList();
    }

    List<gov.ca.cwds.rest.api.Response> relationshipsWithCandidates = new ArrayList<>();

    relationshipsByPrimaryParticipant.forEach((participant, relationships) -> {
      if (relationships != null) {
        getRelationshipWithCandidates(allParticipants, screeningId, relationshipsWithCandidates,
            participant, relationships, allScreeningRelationships);
      }
    });
    return relationshipsWithCandidates;
  }

  private void getRelationshipWithCandidates(List<ParticipantEntity> allParticipants,
      String screeningId, List<Response> relationshipsWithCandidates, ParticipantEntity participant,
      List<ScreeningRelationship> relationships,
      List<ScreeningRelationship> allScreeningRelationships) {
    Optional<ScreeningRelationshipsWithCandidates> screeningRelationshipsWithCandidates = buildRelationshipWithCandidates(
        participant, relationships, allScreeningRelationships, allParticipants,
        screeningId);
    if (screeningRelationshipsWithCandidates.isPresent()) {
      relationshipsWithCandidates
          .add(screeningRelationshipsWithCandidates.get());
    }
  }

  private Optional<ScreeningRelationshipsWithCandidates> buildRelationshipWithCandidates(
      ParticipantEntity participant, List<ScreeningRelationship> relationships,
      List<ScreeningRelationship> allScreeningRelationships,
      List<ParticipantEntity> allParticipants, String screeningId) {

    if (!StringUtils.equals(participant.getScreeningId(), screeningId)) {
      return Optional.empty();
    }

    ScreeningRelationshipsWithCandidatesBuilder screeningRelationshipsWithCandidatesBuilder = new ScreeningRelationshipsWithCandidatesBuilder();
    ScreeningRelationshipsWithCandidates screeningRelationshipsWithCandidates = screeningRelationshipsWithCandidatesBuilder
        .witCandidatesTo(getCandidatesTo(participant, allScreeningRelationships,
            allParticipants))
        .withRelatedTo(getRelationshipsTo(relationships, allParticipants))
        .withId(participant.getId()).withDateOfBirth(participant.getDateOfBirth())
        .withFirstName(participant.getFirstName()).withMiddleName(participant.getMiddleName())
        .withLastName(participant.getLastName())
        .withSuffixName(participant.getNameSuffix()).withGender(participant.getGender())
        .withDateOfDeath(participant.getDateOfDeath()).withSealed(participant.getSealed())
        .withSensitive(participant.getSensitive()).withAge(participant.getApproximateAge())
        .withAgeUnit(participant.getApproximateAgeUnits()).build();
    return Optional.of(screeningRelationshipsWithCandidates);
  }

  private Set<CandidateTo> getCandidatesTo(ParticipantEntity participant,
      List<ScreeningRelationship> relationships,
      List<ParticipantEntity> allParticipants) {
    if (CollectionUtils.isEmpty(allParticipants)) {
      return Collections.emptySet();
    }

    Set<CandidateTo> candidates = new HashSet<>();
    allParticipants.forEach(
        relatedCandidate -> enrichCandidates(participant, relatedCandidate, relationships,
            candidates));
    return candidates;
  }

  private void enrichCandidates(final ParticipantEntity participant,
      final ParticipantEntity relatedCandidate,
      final List<ScreeningRelationship> allScreeningRelationships,
      final Set<CandidateTo> candidates) {

    if (StringUtils.isEmpty(participant.getScreeningId()) ||
        StringUtils.isEmpty(relatedCandidate.getScreeningId()) ||
        !StringUtils.equals(participant.getScreeningId(), relatedCandidate.getScreeningId()) ||
        StringUtils.equals(participant.getId(), relatedCandidate.getId())) {
      return;
    }

    if (relationshipExist(participant, relatedCandidate, allScreeningRelationships)) {
      return;
    }

    CandidateToBuilder builder = new CandidateToBuilder();
    builder.withCandidateAge(relatedCandidate.getApproximateAge())
        .withCandidateAgeUnit(relatedCandidate.getApproximateAgeUnits())
        .withCandidateDateOfBirth(relatedCandidate.getDateOfBirth())
        .withCandidateFirstName(relatedCandidate.getFirstName())
        .withCandidateLastName(relatedCandidate.getLastName())
        .withCandidateMiddleName(relatedCandidate.getMiddleName())
        .withCandidateSuffixtName(relatedCandidate.getNameSuffix())
        .withId(relatedCandidate.getId());
    candidates.add(builder.build());
  }

  private boolean relationshipExist(final ParticipantEntity participant,
      final ParticipantEntity relatedCandidate,
      final List<ScreeningRelationship> allScreeningRelationships) {
    if (CollectionUtils.isEmpty(allScreeningRelationships)) {
      return false;
    }

    Optional<ScreeningRelationship> existingRelationshiop = allScreeningRelationships.stream()
        .filter(
            e -> e.getClientId().equals(participant.getId()) && e.getRelativeId()
                .equals(relatedCandidate.getId())
                || e.getClientId().equals(relatedCandidate.getId()) && e.getRelativeId()
                .equals(participant.getId())).findFirst();
    return existingRelationshiop.isPresent();
  }

  private Set<RelatedTo> getRelationshipsTo(List<ScreeningRelationship> relationships,
      List<ParticipantEntity> allParticipants) {
    if (CollectionUtils.isEmpty(relationships) || CollectionUtils.isEmpty(allParticipants)) {
      return Collections.emptySet();
    }

    Set<RelatedTo> relationshipTos = new HashSet<>();
    relationships.forEach(e ->
        relationshipTos.add(buildRelationshipTo(e, allParticipants))
    );
    return relationshipTos;
  }

  private RelatedTo buildRelationshipTo(ScreeningRelationship relationship,
      List<ParticipantEntity> allParticipants) {
    Optional<ParticipantEntity> relatedParticipant = allParticipants.stream()
        .filter(e -> relationship.getRelativeId().equals(e.getId())).findFirst();

    if (!relatedParticipant.isPresent()) {
      return null;
    }

    RelatedToBuilder relatedToBuilder = new RelatedToBuilder();

    ParticipantEntity participantEntity = relatedParticipant.get();
    relatedToBuilder.withAbsentParentCode(relationship.isAbsentParentIndicator() ? "Y" : "N");
    relatedToBuilder.withRelatedAge(participantEntity.getApproximateAge());
    relatedToBuilder.withRelatedAgeUnit(participantEntity.getApproximateAgeUnits());
    relatedToBuilder.withRelatedPersonId(participantEntity.getId());
    relatedToBuilder.withRelatedDateOfBirth(participantEntity.getDateOfBirth());
    relatedToBuilder.withRelatedFirstName(participantEntity.getFirstName());
    relatedToBuilder.withRelatedGender(participantEntity.getGender());
    relatedToBuilder.withRelatedLastName(participantEntity.getLastName());
    relatedToBuilder.withRelatedMiddleName(participantEntity.getMiddleName());
    relatedToBuilder.withRelatedNameSuffix(participantEntity.getNameSuffix());
    relatedToBuilder
        .withRelatedPersonRelationship(String.valueOf(relationship.getRelationshipType()));
    relatedToBuilder.withRelationshipEndDate(relationship.getEndDate());
    relatedToBuilder.withRelationshipId(relationship.getId());
    relatedToBuilder.withRelationshipStartDate(relationship.getStartDate())
        .withRelationshipToPerson(
            String.valueOf(getOppositeSystemCode((short) relationship.getRelationshipType())));
    relatedToBuilder.withSameHomeCode(relationship.getSameHomeStatus());

    return relatedToBuilder.build();
  }

  private Map<ParticipantEntity, List<ScreeningRelationship>> getRelationshipsMappedByPrimaryParticipant(
      List<ScreeningRelationship> screeningRelationships,
      List<ParticipantEntity> allParticipants) {
    if (CollectionUtils.isEmpty(allParticipants)) {
      return Collections.emptyMap();
    }

    Map<ParticipantEntity, List<ScreeningRelationship>> mappedRelationships = new HashMap<>();
    allParticipants.forEach(participantEntity -> {
      List<ScreeningRelationship> relationships = new ArrayList<>();
      if (screeningRelationships != null) {
        screeningRelationships.forEach(a -> {
          if (a.getClientId().equals(participantEntity.getId())) {
            relationships.add(a);
          }
        });
      }
      mappedRelationships.put(participantEntity, relationships);
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
        if (client == null) {
          continue;
        }

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
        if (client == null) {
          continue;
        }
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

  private short getOppositeSystemCode(short systemCodeId) {
    short oppositeCode = -1;

    gov.ca.cwds.rest.api.domain.cms.SystemCode systemCode = codesMappedById.get(systemCodeId);
    if (systemCode != null) {
      String str = systemCode.getShortDescription();
      if (StringUtils.isNoneEmpty(str)) {
        String[] descriptionArray = str.split("/");
        if (descriptionArray != null && descriptionArray.length == 2) {
          String part3 = "";
          if (descriptionArray[1].contains("(")) {
            int indexStart = descriptionArray[1].indexOf('(');
            int indexEnd = descriptionArray[1].indexOf(')');
            part3 = descriptionArray[1]
                .substring(indexStart, ++indexEnd);
            descriptionArray[1] = descriptionArray[1].replace(part3, "").trim();
            part3 = StringUtils.isEmpty(part3) ? "part3" : " " + part3;
          }
          String oppositeDescription = descriptionArray[1] + "/" + descriptionArray[0] + part3;
          oppositeCode = codesMappedByDescription.get(oppositeDescription).getSystemId();
        }
      }
    }

    return oppositeCode;
  }
}
