package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import gov.ca.cwds.ObjectMapperUtils;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.AgencyDao;
import gov.ca.cwds.data.ns.AllegationIntakeDao;
import gov.ca.cwds.data.ns.CrossReportDao;
import gov.ca.cwds.data.ns.ScreeningAddressDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.AllegationEntity;
import gov.ca.cwds.data.persistence.ns.CrossReportEntity;
import gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningAddressEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.AllegationIntake;
import gov.ca.cwds.rest.api.domain.CrossReportIntake;
import gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningDashboard;
import gov.ca.cwds.rest.api.domain.ScreeningDashboardList;
import gov.ca.cwds.rest.api.domain.enums.ScreeningStatus;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.resources.parameter.ParticipantResourceParameters;
import gov.ca.cwds.rest.services.mapper.AddressMapper;
import gov.ca.cwds.rest.services.mapper.AgencyMapper;
import gov.ca.cwds.rest.services.mapper.AllegationMapper;
import gov.ca.cwds.rest.services.mapper.CrossReportMapper;
import gov.ca.cwds.rest.services.mapper.ScreeningMapper;

/**
 * Business layer object to work on {@link Screening}.
 *
 * @author CWDS API Team
 */
public class ScreeningService implements CrudsService {

  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.createObjectMapper();

  @Named("screenings.index")
  @Inject
  private ElasticsearchDao esDao;

  @Inject
  private AllegationIntakeDao allegationDao;

  @Inject
  private AddressesDao addressesDao;

  @Inject
  private ScreeningAddressDao screeningAddressDao;

  @Inject
  private AgencyDao agencyDao;

  @Inject
  private CrossReportDao crossReportDao;

  @Inject
  private ScreeningDao screeningDao;

  @Inject
  private ParticipantIntakeApiService participantIntakeApiService;

  @Inject
  private ScreeningMapper screeningMapper;

  @Inject
  private AddressMapper addressMapper;

  @Inject
  private AgencyMapper agencyMapper;

  @Inject
  private AllegationMapper allegationMapper;

  @Inject
  private CrossReportMapper crossReportMapper;

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    throw new NotImplementedException("Find is not implemented");
  }

  /**
   * Return the screening dashboard of the logged in user.
   *
   * @return - array of screening dashboard objects
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  public Response findScreeningDashboard() {
    return getScreeningsOfUser(RequestExecutionContext.instance().getStaffId());
  }

  private ScreeningDashboardList getScreeningsOfUser(String staffId) {
    final List<ScreeningWrapper> screenings = screeningDao.findScreeningsByUserId(staffId);
    final List<ScreeningDashboard> screeningDashboard = new ArrayList<>(screenings.size());
    for (ScreeningWrapper screening : screenings) {
      final ScreeningDashboard thisScreening = new ScreeningDashboard(screening);
      screeningDashboard.add(thisScreening);
    }
    return new ScreeningDashboardList(screeningDashboard);
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   *
   * @param request the request
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Screening create(Request request) {
    index(request);
    return (Screening) request;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Screening update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof Screening;
    final Screening screening = (Screening) request;

    if (!primaryKey.equals(screening.getId())) {
      throw new ServiceException(
          "Primary key mismatch, [" + primaryKey + " != " + screening.getId() + "]");
    }

    index(request);
    return (Screening) request;
  }

  /**
   * Convert given screening to JSON.
   *
   * @param screening Screening to convert to JSON format
   * @return Screening as JSON format
   */
  private String toJson(Screening screening) {
    String screeningJson;
    try {
      screeningJson = OBJECT_MAPPER.writeValueAsString(screening);
    } catch (JsonProcessingException e) {
      throw new ServiceException(e);
    }
    return screeningJson;
  }

  /**
   * Index given screening request.
   *
   * @param request The screening request
   * @return The IndexResponse
   */
  private IndexResponse index(Request request) {
    assert request instanceof Screening;
    final Screening screening = (Screening) request;
    final String screeningJson = toJson(screening);

    final IndexRequestBuilder builder =
        esDao.getClient().prepareIndex(esDao.getConfig().getElasticsearchAlias(),
            esDao.getConfig().getElasticsearchDocType(), screening.getId());
    builder.setSource(screeningJson, XContentType.JSON);

    final IndexResponse response = builder.get();
    final RestStatus status = response.status();
    final boolean success = RestStatus.OK == status || RestStatus.CREATED == status;

    if (!success) {
      throw new ServiceException("Could not index screening. Status: "
          + (status != null ? status.getStatus() : "unknown") + ", Response: " + response);
    }

    return response;
  }

  public Screening getScreening(String id) {
    final ScreeningEntity screeningEntity = screeningDao.find(id);
    if (screeningEntity == null) {
      throw new ServiceException("Screening with id=" + id + " is not found");
    }

    final Screening screening = screeningMapper.map(screeningEntity);
    final String screeningId = screeningEntity.getId();

    final List<AllegationEntity> allegationEntities = allegationDao.findByScreeningId(screeningId);
    final Set<AllegationIntake> allegations = allegationMapper.map(allegationEntities);
    screening.getAllegations().addAll(allegations);

    final List<CrossReportEntity> crossReportEntities =
        crossReportDao.findByScreeningId(screeningId);
    final Set<CrossReportIntake> crossReports = crossReportMapper.map(crossReportEntities);
    screening.getCrossReports().addAll(crossReports);

    for (CrossReportIntake crossReport : crossReports) {
      final List<GovernmentAgencyEntity> agencyEntities =
          agencyDao.findByCrossReportId(crossReport.getId());
      final Set<gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake> agencies =
          agencyMapper.map(agencyEntities);
      crossReport.getAgencies().addAll(agencies);
    }

    final List<ScreeningAddressEntity> screeningAddressEntities =
        screeningAddressDao.findByScreeningId(screeningId);
    if (screeningAddressEntities.size() > 1) {
      throw new ServiceException("Screening should have no more then 1 address");
    }

    for (ScreeningAddressEntity screeningAddressEntity : screeningAddressEntities) {
      final Addresses addressEntity = addressesDao.find(screeningAddressEntity.getAddressId());
      final AddressIntakeApi address = addressMapper.map(addressEntity);
      screening.setIncidentAddress(address);
    }

    final List<ParticipantEntity> participantEntities =
        participantIntakeApiService.getByScreeningId(screeningId);

    for (ParticipantEntity participantEntity : participantEntities) {
      final ParticipantIntakeApi participantIntakeApi = participantIntakeApiService
          .find(new ParticipantResourceParameters(id, participantEntity.getId()));
      screening.getParticipantIntakeApis().add(participantIntakeApi);
    }

    return screening;
  }

  @SuppressWarnings("fb-contrib:CFS_CONFUSING_FUNCTION_SEMANTICS")
  public Screening createScreening(Screening screening) {
    if (screening == null) {
      throw new ServiceException("Screening for create is null");
    }
    validateParticipants(screening);

    final ScreeningEntity screeningEntity = screeningMapper.map(screening);
    screeningEntity.setScreeningStatus(ScreeningStatus.OPEN.getStatus());
    ScreeningEntity createdScreeningEntity = screeningDao.create(screeningEntity);

    final String screeningId = createdScreeningEntity.getId();
    screening.setId(screeningId);

    createUpdateDeleteCrossReports(screening);
    createOrUpdateAddresses(screening);
    createUpdateDeleteParticipants(screening);
    createUpdateDeleteAllegations(screening);

    return screening;
  }

  public Screening updateScreening(String id, Screening screening) {
    if (screening == null) {
      throw new ServiceException("Screening for update is null");
    }
    if (!id.equals(screening.getId())) {
      throw new ServiceException("Primary key mismatch, [" + id + " != " + screening.getId() + "]");
    }

    final ScreeningEntity screeningEntity = screeningMapper.map(screening);
    screeningDao.update(screeningEntity);

    createUpdateDeleteCrossReports(screening);
    createOrUpdateAddresses(screening);
    createUpdateDeleteParticipants(screening);
    createUpdateDeleteAllegations(screening);

    return screening;
  }

  private void validateParticipants(Screening screening) {
    for (ParticipantIntakeApi participantIntakeApi : screening.getParticipantIntakeApis()) {
      if (participantIntakeApi.getScreeningId() != null
          && !Objects.equals(participantIntakeApi.getScreeningId(), screening.getId())) {
        throw new ServiceException("Screening id and participant's screeningId doesn't match");
      }
    }
  }

  private void createUpdateDeleteAllegations(Screening screening) {
    final Set<Integer> allegationIdsOld = allegationDao.findByScreeningId(screening.getId())
        .stream().map(AllegationEntity::getId).collect(Collectors.toSet());

    for (AllegationIntake allegation : screening.getAllegations()) {
      final AllegationEntity allegationEntity = allegationMapper.map(allegation);
      allegationEntity.setScreeningId(screening.getId());

      final Date now = new Date();
      allegationEntity.setUpdatedAt(now);
      if (allegationEntity.getId() == null) {
        allegationEntity.setCreatedAt(now);
        final AllegationEntity createdAllegationEntity = allegationDao.create(allegationEntity);
        allegation.setId(String.valueOf(createdAllegationEntity.getId()));
      } else {
        final AllegationEntity managedAllegationEntity =
            allegationDao.find(allegationEntity.getId());
        if (managedAllegationEntity == null) {
          throw new ServiceException(
              "Cannot update allegation that doesn't exist. id = " + allegationEntity.getId());
        }
        allegationEntity.setCreatedAt(managedAllegationEntity.getCreatedAt());

        // DRS: HOT-2176: isolate "possible non-threadsafe access to session".
        allegationDao.getSessionFactory().getCurrentSession().detach(managedAllegationEntity);
        allegationDao.update(allegationEntity);
        allegationIdsOld.remove(allegationEntity.getId());
      }
    }

    // Delete old ones that are not in the new.
    allegationIdsOld.forEach(allegationId -> allegationDao.delete(allegationId));
  }

  private void createUpdateDeleteCrossReports(Screening screening) {
    final Set<String> crossReportIdsOld = crossReportDao.findByScreeningId(screening.getId())
        .stream().map(CrossReportEntity::getId).collect(Collectors.toSet());

    for (CrossReportIntake crossReport : screening.getCrossReports()) {
      final CrossReportEntity crossReportEntity = crossReportMapper.map(crossReport);
      crossReportEntity.setScreeningId(screening.getId());

      final Date now = new Date();
      crossReportEntity.setUpdatedAt(now);
      if (crossReportEntity.getId() == null) {
        crossReportEntity.setCreatedAt(now);
        final CrossReportEntity createdCrossReportEntity = crossReportDao.create(crossReportEntity);
        crossReport.setId(createdCrossReportEntity.getId());
      } else {
        final CrossReportEntity managedCrossReportEntity =
            crossReportDao.find(crossReportEntity.getId());
        if (managedCrossReportEntity == null) {
          throw new ServiceException(
              "Cannot update cross report that doesn't exist. id = " + crossReportEntity.getId());
        }
        crossReportEntity.setCreatedAt(managedCrossReportEntity.getCreatedAt());

        // DRS: HOT-2176: isolate "possible non-threadsafe access to session".
        crossReportDao.getSessionFactory().getCurrentSession().detach(managedCrossReportEntity);
        crossReportDao.update(crossReportEntity);
        crossReportIdsOld.remove(crossReportEntity.getId());
      }

      createOrUpdateAgencies(crossReport);
    }

    // Delete old ones that are not in the new.
    crossReportIdsOld.forEach(crossReportId -> crossReportDao.delete(crossReportId));
  }

  private void createOrUpdateAgencies(CrossReportIntake crossReport) {
    for (GovernmentAgencyIntake agency : crossReport.getAgencies()) {
      final GovernmentAgencyEntity agencyEntity = agencyMapper.map(agency);
      agencyEntity.setCrossReportId(crossReport.getId());

      final Date now = new Date();
      agencyEntity.setUpdatedAt(now);
      if (agencyEntity.getId() == null) {
        agencyEntity.setCreatedAt(now);
        final GovernmentAgencyEntity createdAgencyEntity = agencyDao.create(agencyEntity);
        agency.setId(createdAgencyEntity.getId());
      } else {
        final GovernmentAgencyEntity managedAgencyEntity = agencyDao.find(agencyEntity.getId());
        if (managedAgencyEntity == null) {
          throw new ServiceException(
              "Cannot update agency that doesn't exist. id = " + agency.getId());
        }
        agencyEntity.setCreatedAt(managedAgencyEntity.getCreatedAt());

        // DRS: HOT-2176: isolate "possible non-threadsafe access to session".
        agencyDao.getSessionFactory().getCurrentSession().detach(managedAgencyEntity);
        agencyDao.update(agencyEntity);
      }
    }
  }

  private void createOrUpdateAddresses(Screening screening) {
    final AddressIntakeApi address = screening.getIncidentAddress();
    if (address == null) {
      return;
    }
    Addresses addressEntity = addressMapper.map(address);
    if (addressEntity.getId() == null) {
      final Addresses createdAddress = addressesDao.create(addressEntity);
      final ScreeningAddressEntity screeningAddressesEntity = new ScreeningAddressEntity();

      screeningAddressesEntity.setScreeningId(screening.getId());
      screeningAddressesEntity.setAddressId(createdAddress.getId());
      address.setId(String.valueOf(addressEntity.getId()));
      screeningAddressDao.create(screeningAddressesEntity);
    } else {
      addressesDao.update(addressEntity);
    }
  }

  private void createUpdateDeleteParticipants(Screening screening) {
    final Set<ParticipantIntakeApi> participantIntakeApis = new HashSet<>();
    final Set<String> participantIdsOld =
        participantIntakeApiService.getByScreeningId(screening.getId()).stream()
            .map(ParticipantEntity::getId).collect(Collectors.toSet());

    for (ParticipantIntakeApi participantIntakeApi : screening.getParticipantIntakeApis()) {
      final String participantIntakeApiId = participantIntakeApi.getId();
      if (participantIntakeApiId == null) {
        participantIntakeApi.setScreeningId(screening.getId());
        final ParticipantIntakeApi createdParticipantIntakeApi =
            participantIntakeApiService.create(participantIntakeApi);
        participantIntakeApis.add(createdParticipantIntakeApi);
      } else {
        final ParticipantIntakeApi updatedParticipantIntakeApi = participantIntakeApiService.update(
            new ParticipantResourceParameters(screening.getId(), participantIntakeApiId),
            participantIntakeApi);
        participantIntakeApis.add(updatedParticipantIntakeApi);
        participantIdsOld.remove(participantIntakeApiId);
      }
    }

    // Delete old ones that are not in the new.
    participantIdsOld.forEach(participantId -> participantIntakeApiService
        .delete(new ParticipantResourceParameters(screening.getId(), participantId)));

    screening.setParticipantIntakeApis(participantIntakeApis);
  }

  void setEsDao(ElasticsearchDao esDao) {
    this.esDao = esDao;
  }

  void setScreeningDao(ScreeningDao screeningDao) {
    this.screeningDao = screeningDao;
  }

}
