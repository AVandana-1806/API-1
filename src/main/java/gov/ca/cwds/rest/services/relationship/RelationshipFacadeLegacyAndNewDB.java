package gov.ca.cwds.rest.services.relationship;

import gov.ca.cwds.rest.util.ConcurrencyUtil;
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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipBase;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.CandidateTo;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.CandidateTo.CandidateToBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.RelatedTo;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.RelatedTo.RelatedToBuilder;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates.ScreeningRelationshipsWithCandidatesBuilder;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ScreeningRelationshipService;
import gov.ca.cwds.rest.services.mapper.RelationshipMapper;
import gov.ca.cwds.rest.services.screening.participant.ParticipantService;
import gov.ca.cwds.rest.services.screeningparticipant.ClientTransformer;

/**
 * @author CWDS TPT-3 Team
 */
public class RelationshipFacadeLegacyAndNewDB implements RelationshipFacade {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RelationshipFacadeLegacyAndNewDB.class);

  private static final RelationshipMapper mapper = RelationshipMapper.INSTANCE;

  // For now I don't know how it would be shown on the UI I am waiting for this information
  private final static String DB_ERROR_MESSAGE = "Relationship couldn't be created";

  private final Map<String, gov.ca.cwds.rest.api.domain.cms.SystemCode> codesMappedByDescription =
      new HashMap<>();
  private final Map<Short, gov.ca.cwds.rest.api.domain.cms.SystemCode> codesMappedById =
      new HashMap<>();

  private final ParticipantDao participantDao;
  private final ClientRelationshipDao cmsRelationshipDao;
  private final RelationshipDao nsRelationshipDao;
  private final ClientDao cmsClientDao;

  private final ScreeningRelationshipService screeningRelationshipService;
  private final LegacyDescriptorDao legacyDescriptorDao;
  private final ParticipantService participantService;
  private final ClientTransformer clientTransformer;

  @Inject
  public RelationshipFacadeLegacyAndNewDB(ParticipantDao participantDao,
      ClientRelationshipDao cmsRelationshipDao, RelationshipDao nsRelationshipDao,
      ClientDao cmsClientDao, ScreeningRelationshipService screeningRelationshipService,
      LegacyDescriptorDao legacyDescriptorDao, ParticipantService participantService,
      ClientTransformer clientTransformer) {
    this.participantDao = participantDao;
    this.cmsRelationshipDao = cmsRelationshipDao;
    this.nsRelationshipDao = nsRelationshipDao;
    this.cmsClientDao = cmsClientDao;

    this.screeningRelationshipService = screeningRelationshipService;
    this.legacyDescriptorDao = legacyDescriptorDao;
    this.participantService = participantService;
    this.clientTransformer = clientTransformer;
    initSystemCodes();
  }

  @Override
  public gov.ca.cwds.rest.api.Response updateRelationship(String relationshipId,
      ScreeningRelationship relationship) {
    if (relationship.isReversed()) {
      relationship = enrichReversedRelationship(relationship);
    }
    return screeningRelationshipService.update(relationshipId, relationship);
  }

  @Override
  public List<gov.ca.cwds.rest.api.Response> createRelationships(
      List<ScreeningRelationshipBase> relationships) {
    List<gov.ca.cwds.rest.api.Response> responses = new ArrayList<>();
    if (CollectionUtils.isEmpty(relationships)) {
      return responses;
    }

    relationships.forEach(relationship -> {
      try {
        responses.add(createRelationship(relationship));
      } catch (Exception e) {
        ScreeningRelationship faildRelationship = new ScreeningRelationship(relationship);
        faildRelationship.setError(DB_ERROR_MESSAGE); // in the future, Drools will manage this
      }
    });
    return responses;
  }

  @Override
  public List<gov.ca.cwds.rest.api.Response> getRelationshipsWithCandidatesByScreeningId(
      String screeningId) {
    List<gov.ca.cwds.rest.api.Response> response = new ArrayList<>();
    if (StringUtils.isEmpty(screeningId)) {
      return response;
    }

    final RelationshipFacadeData relationshipFacadeData = getRelationshipFacadeData(screeningId);
    relationshipFacadeData.screeningParticipants.forEach(screeningParticipant -> response
        .add(getRelationshipWithCandidates(screeningParticipant, relationshipFacadeData)));
    return response;
  }

  @Override
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
    if (participantDao.getSessionFactory().getCurrentSession().getTransaction()
        .getStatus() == TransactionStatus.ACTIVE) {
      participantDao.getSessionFactory().getCurrentSession().flush();
    }
    return result;
  }

  @Override
  public void deleteParticipantsAndRelationships(String participantId, String screeningId)
      throws ExecutionException, InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    Future<Set<ParticipantEntity>> task1 = executor.submit(() -> participantDao
        .findByRelatedScreeningId(screeningId));
    Future<List<Relationship>> task2 = executor
        .submit(() -> nsRelationshipDao.getRelationshipsByScreeningId(screeningId));
    ConcurrencyUtil.awaitTerminationAfterShutdown(executor);

    Set<ParticipantEntity> deletedParticipants = task1.get();
    List<Relationship> deletedRelationships = task2.get();

    deletedParticipants.forEach(e -> participantDao.delete(e.getId()));
    deletedRelationships.forEach(e -> nsRelationshipDao.delete(e.getId()));


//    executor = Executors.newFixedThreadPool(2);
//    executor.execute(() -> participantDao
//        .deleteParticipantsByRelatedScreningIdWithNullableScreeningId(screeningId));
//    executor.execute(() -> nsRelationshipDao.deleteRelationshipsByRelatedScreeningId(screeningId));
//    ConcurrencyUtil.awaitTerminationAfterShutdown(executor);

    // достаем новые релейшены
//    Set<ScreeningRelationship> allRelationships = fromResponse(
//        getRelationshipsByScreeningId(screeningId));

    // обновляем их по легаси ID и добавляем те у кого нет легаси id
  }

  private ScreeningRelationship enrichReversedRelationship(ScreeningRelationship relationship) {
    final String clientId = relationship.getClientId();
    relationship.setClientId(relationship.getRelativeId());
    relationship.setRelativeId(clientId);
    relationship.setRelationshipType(getOppositeSystemCode(relationship.getRelationshipType()));
    return relationship;
  }

  private void initSystemCodes() {
    if (!MapUtils.isEmpty(codesMappedByDescription)) {
      return;
    }

    // Call the system code cache instead of the raw DAO.
    final Set<gov.ca.cwds.rest.api.domain.cms.SystemCode> systemCodes =
        SystemCodeCache.global().getSystemCodesForMeta("CLNTRELC");

    if (MapUtils.isEmpty(codesMappedByDescription)) {
      systemCodes.forEach(e -> codesMappedByDescription.put(e.getShortDescription(), e));
      systemCodes.forEach(e -> codesMappedById.put(e.getSystemId(), e));
    }
  }

  private ScreeningRelationship createRelationship(ScreeningRelationshipBase relationshipBase) {
    return (ScreeningRelationship) screeningRelationshipService.create(relationshipBase);
  }

  private RelationshipFacadeData getRelationshipFacadeData(String screeningId) {
    final Set<ScreeningRelationship> allRelationships =
        fromResponse(getRelationshipsByScreeningId(screeningId));
    final List<ParticipantEntity> screeningParticipants =
        participantDao.getByScreeningId(screeningId);
    final Set<String> participantIds = getParticipantIds(allRelationships);
    final Map<String, ParticipantEntity> allMappedParticipants =
        getMappedParticipantsById(participantIds);
    final Map<String, LegacyDescriptorEntity> participantsLegacyDescriptors =
        legacyDescriptorDao.findParticipantLegacyDescriptors(participantIds);

    final RelationshipFacadeData relationshipFacadeData = new RelationshipFacadeData();
    relationshipFacadeData.allMappedParticipants = allMappedParticipants;
    relationshipFacadeData.allRelationships = allRelationships;
    relationshipFacadeData.participantsLegacyDescriptors = participantsLegacyDescriptors;
    relationshipFacadeData.screeningId = screeningId;
    relationshipFacadeData.screeningParticipants = screeningParticipants;
    return relationshipFacadeData;
  }

  private Map<String, ParticipantEntity> getMappedParticipantsById(Set<String> participantIds) {
    final List<ParticipantEntity> participantEntities = participantDao.findByIds(participantIds);
    if (CollectionUtils.isEmpty(participantEntities)) {
      return new HashMap<>();
    }

    final Map<String, ParticipantEntity> map = new HashMap<>(participantEntities.size());
    participantEntities.forEach(e -> map.put(e.getId(), e));
    return map;
  }

  private Response getRelationshipWithCandidates(ParticipantEntity screeningParticipant,
      RelationshipFacadeData relationshipFacadeData) {

    final Set<CandidateTo> candidateTos =
        getCandidatesTo(screeningParticipant, relationshipFacadeData);
    final Set<RelatedTo> relatedTos = getRelatedTo(screeningParticipant, relationshipFacadeData);

    return new ScreeningRelationshipsWithCandidatesBuilder().withRelatedTo(relatedTos)
        .witCandidatesTo(candidateTos).withId(screeningParticipant.getId())
        .withDateOfBirth(screeningParticipant.getDateOfBirth())
        .withFirstName(screeningParticipant.getFirstName())
        .withMiddleName(screeningParticipant.getMiddleName())
        .withLastName(screeningParticipant.getLastName())
        .withSuffixName(screeningParticipant.getNameSuffix())
        .withGender(screeningParticipant.getGender())
        .withDateOfDeath(screeningParticipant.getDateOfDeath())
        .withSealed(screeningParticipant.getSealed())
        .withSensitive(screeningParticipant.getSensitive())
        .withAge(screeningParticipant.getApproximateAge())
        .withAgeUnit(screeningParticipant.getApproximateAgeUnits())
        .withEstimatedDob(screeningParticipant.getEstimatedDob()).build();
  }

  private Set<RelatedTo> getRelatedTo(ParticipantEntity screeningParticipant,
      RelationshipFacadeData relationshipFacadeData) {
    Set<RelatedTo> relatedTos = new HashSet<>();

    if (CollectionUtils.isEmpty(relationshipFacadeData.allRelationships)) {
      return relatedTos;
    }

    relationshipFacadeData.allRelationships.forEach(relationship -> {
      final ParticipantEntity participantPrimary =
          relationshipFacadeData.allMappedParticipants.get(relationship.getClientId());
      final ParticipantEntity participantSecondary =
          relationshipFacadeData.allMappedParticipants.get(relationship.getRelativeId());

      if (participantPrimary == null || participantSecondary == null) {
        return;
      }

      if (screeningParticipant.getScreeningId().equals(relationshipFacadeData.screeningId)) {
        if (relationship.getClientId().equals(screeningParticipant.getId())) {
          relatedTos.add(getPrimaryRelatedTo(relationship, participantSecondary,
              relationshipFacadeData.participantsLegacyDescriptors, true));
        } else if (relationship.getRelativeId().equals(screeningParticipant.getId())) {
          relatedTos.add(getPrimaryRelatedTo(relationship, participantPrimary,
              relationshipFacadeData.participantsLegacyDescriptors, false));
        }
      }
    });
    return relatedTos;
  }

  private RelatedTo getPrimaryRelatedTo(ScreeningRelationship relationship,
      ParticipantEntity participantEntity,
      Map<String, LegacyDescriptorEntity> participantsLegacyDescriptors, boolean isPrimary) {

    final RelatedToBuilder relatedToBuilder = new RelatedToBuilder();
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
    relatedToBuilder.withRelationshipEndDate(relationship.getEndDate());
    relatedToBuilder.withRelationshipId(relationship.getId());
    relatedToBuilder.withRelationshipStartDate(relationship.getStartDate());
    relatedToBuilder.withSameHomeCode(relationship.getSameHomeStatus());
    relatedToBuilder.withEstimatedDob(participantEntity.getEstimatedDob());

    final LegacyDescriptorEntity legacyDescriptorEntity =
        participantsLegacyDescriptors.get(participantEntity.getId());
    if (legacyDescriptorEntity != null) {
      LegacyDescriptor legacyDescriptor = new LegacyDescriptor(legacyDescriptorEntity);
      relatedToBuilder.withLegacyDescriptor(legacyDescriptor);
    }

    if (!isPrimary) {
      relatedToBuilder
          .withRelatedPersonRelationship(String.valueOf(relationship.getRelationshipType()))
          .withRelationshipToPerson(
              String.valueOf(getOppositeSystemCode((short) relationship.getRelationshipType())));
      relatedToBuilder.withReversedRelationship(true);
    } else {
      relatedToBuilder.withRelationshipToPerson(String.valueOf(relationship.getRelationshipType()))
          .withRelatedPersonRelationship(
              String.valueOf(getOppositeSystemCode((short) relationship.getRelationshipType())));
      relatedToBuilder.withReversedRelationship(false);
    }

    return relatedToBuilder.build();
  }

  private Set<CandidateTo> getCandidatesTo(ParticipantEntity screeningParticipant,
      RelationshipFacadeData relationshipFacadeData) {
    Set<CandidateTo> candidates = new HashSet<>();
    if (CollectionUtils.isEmpty(relationshipFacadeData.screeningParticipants)) {
      return candidates;
    }

    relationshipFacadeData.screeningParticipants.forEach(participant -> {
      if (participant.getId().equals(screeningParticipant.getId())) {
        return;
      }

      if (!relationshipExist(screeningParticipant, participant,
          relationshipFacadeData.allRelationships)) {
        final CandidateToBuilder builder = new CandidateToBuilder();

        LegacyDescriptorEntity legacyDescriptorEntity =
            relationshipFacadeData.participantsLegacyDescriptors.get(participant.getId());
        if (legacyDescriptorEntity != null) {
          LegacyDescriptor legacyDescriptor = new LegacyDescriptor(legacyDescriptorEntity);
          builder.withLegacyDescriptor(legacyDescriptor);
        }

        builder.withCandidateAge(participant.getApproximateAge())
            .withCandidateAgeUnit(participant.getApproximateAgeUnits())
            .withCandidateDateOfBirth(participant.getDateOfBirth())
            .withCandidateFirstName(participant.getFirstName())
            .withCandidateLastName(participant.getLastName())
            .withCandidateMiddleName(participant.getMiddleName())
            .withCandidateSuffixtName(participant.getNameSuffix()).withId(participant.getId())
            .withEstimatedDob(participant.getEstimatedDob());
        candidates.add(builder.build());
      }
    });
    return candidates;
  }

  private boolean relationshipExist(final ParticipantEntity participant,
      final ParticipantEntity relatedCandidate,
      final Set<ScreeningRelationship> allScreeningRelationships) {
    if (CollectionUtils.isEmpty(allScreeningRelationships)) {
      return false;
    }

    final Optional<ScreeningRelationship> existingRelationshiop = allScreeningRelationships.stream()
        .filter(e -> e.getClientId().equals(participant.getId())
            && e.getRelativeId().equals(relatedCandidate.getId())
            || e.getClientId().equals(relatedCandidate.getId())
            && e.getRelativeId().equals(participant.getId()))
        .findFirst();
    return existingRelationshiop.isPresent();
  }

  private Set<String> getParticipantIds(Set<ScreeningRelationship> screeningRelationships) {
    if (CollectionUtils.isEmpty(screeningRelationships)) {
      return Collections.emptySet();
    }

    final Set<String> participantIds = new HashSet<>();
    screeningRelationships.forEach(e -> {
      participantIds.add(e.getClientId());
      participantIds.add(e.getRelativeId());
    });

    return participantIds;
  }

  private Set<ScreeningRelationship> fromResponse(
      List<gov.ca.cwds.rest.api.Response> relationshipsResponse) {
    Set<ScreeningRelationship> screeningRelationships = new HashSet<>();
    relationshipsResponse.forEach(e -> screeningRelationships.add((ScreeningRelationship) e));
    return screeningRelationships;
  }

  private List<ScreeningRelationship> updateRelationships(
      List<ClientRelationship> shouldBeUpdated) {
    final Date updatedAt = RequestExecutionContext.instance().getRequestStartTime();
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

    final Date createdAt = RequestExecutionContext.instance().getRequestStartTime();
    List<ScreeningRelationship> result = new ArrayList<>();
    Set<String> clientIdSet = participantDao.findLegacyIdListByScreeningId(screeningId);

    ParticipantEntity participantEntity1;
    ParticipantEntity participantEntity2;

    for (ClientRelationship clientRelationship : shouldBeCreated) {
      if (!clientIdSet.contains(clientRelationship.getPrimaryClientId())) {
        final Client client = cmsClientDao.find(clientRelationship.getPrimaryClientId());
        if (client == null) {
          continue;
        }
        participantEntity1 = createParticipant(client, screeningId);
      } else {
        participantEntity1 = participantDao.findByScreeningIdAndLegacyId(screeningId,
            clientRelationship.getPrimaryClientId());
      }

      if (!clientIdSet.contains(clientRelationship.getSecondaryClientId())) {
        final Client client = cmsClientDao.find(clientRelationship.getSecondaryClientId());
        if (client == null) {
          continue;
        }
        participantEntity2 = createParticipant(client, screeningId);
      } else {
        participantEntity2 = participantDao.findByScreeningIdAndLegacyId(screeningId,
            clientRelationship.getSecondaryClientId());
      }

      if (participantEntity1 == null || participantEntity2 == null) {
        return result;
      }

      final Relationship newRelationship = new Relationship();
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

      result.add(mapper.map(nsRelationshipDao.create(newRelationship)));
    }
    return result;
  }

  private ParticipantEntity createParticipant(Client client, String screeningId) {
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
    participantIntakeApi.setRelatedScreeningId(screeningId);
    participantIntakeApi = participantService.persistParticipantObjectInNS(participantIntakeApi);
    participantDao.getSessionFactory().getCurrentSession().flush();
    return participantDao.find(participantIntakeApi.getId());
  }

  private List<ClientRelationship> getRelationshipsThatShouldBeCreated(
      final Set<ClientRelationship> lagacyRelationships, List<Relationship> nsRelationships) {
    LOGGER.info("lagacyRelationships {}", lagacyRelationships);
    LOGGER.info("nsRelationships {}", nsRelationships);
    final List<ClientRelationship> relationshipsToCreate = new ArrayList<>();
    if (CollectionUtils.isEmpty(lagacyRelationships)) {
      return relationshipsToCreate;
    }

    lagacyRelationships.forEach(e -> {
      if (CollectionUtils.isEmpty(nsRelationships)) {
        relationshipsToCreate.add(e);
      } else {
        Optional<Relationship> clientRelationship =
            nsRelationships.stream().filter(b -> e.getId().equals(b.getLegacyId())).findFirst();
        if (!clientRelationship.isPresent()) {
          relationshipsToCreate.add(e);
        }
      }
    });
    return relationshipsToCreate;
  }

  private List<ClientRelationship> getRelationshipsThatShouldBeUpdated(
      final Set<ClientRelationship> legacyRelationships, List<Relationship> nsRelationships) {
    if (CollectionUtils.isEmpty(nsRelationships) || CollectionUtils.isEmpty(legacyRelationships)) {
      return new ArrayList<>();
    }

    final List<ClientRelationship> relationshipsToUpdate = new ArrayList<>();
    legacyRelationships.forEach(e -> {
      boolean update = false;
      for (Relationship relationship : nsRelationships) {
        if (e.getId().equals(relationship.getLegacyId())) {
          if (e.getLastUpdatedTime().getTime() > relationship.getUpdatedAt().getTime()) {
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
    Set<ClientRelationship> relationshipListCms = new HashSet<>();
    Map<String, Collection<ClientRelationship>> primaryRelationshipMap =
        cmsRelationshipDao.findByPrimaryClientIds(legacyClientIds);
    for (Map.Entry<String, Collection<ClientRelationship>> relationshipMapEntry : primaryRelationshipMap
        .entrySet()) {
      relationshipListCms.addAll(relationshipMapEntry.getValue());
    }
    Map<String, Collection<ClientRelationship>> secondaryRelationshipMap =
        cmsRelationshipDao.findBySecondaryClientIds(legacyClientIds);
    for (Map.Entry<String, Collection<ClientRelationship>> relationshipMapEntry : secondaryRelationshipMap
        .entrySet()) {
      relationshipListCms.addAll(relationshipMapEntry.getValue());
    }
    return relationshipListCms;
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

  private short getOppositeSystemCode(int systemCodeId) {
    short oppositeCode = -1;

    gov.ca.cwds.rest.api.domain.cms.SystemCode systemCode =
        codesMappedById.get((short) systemCodeId);
    if (systemCode != null) {
      String str = systemCode.getShortDescription();
      if (StringUtils.isNoneEmpty(str)) {
        String[] descriptionArray = str.split("/");
        if (descriptionArray != null && descriptionArray.length == 2) {
          String part3 = "";
          if (descriptionArray[1].contains("(")) {
            int indexStart = descriptionArray[1].indexOf('(');
            int indexEnd = descriptionArray[1].indexOf(')');
            part3 = descriptionArray[1].substring(indexStart, ++indexEnd);
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


class RelationshipFacadeData {

  Map<String, ParticipantEntity> allMappedParticipants;
  Set<ScreeningRelationship> allRelationships;
  List<ParticipantEntity> screeningParticipants;
  Map<String, LegacyDescriptorEntity> participantsLegacyDescriptors;
  String screeningId;
}
