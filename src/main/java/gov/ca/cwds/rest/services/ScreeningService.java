package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

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
import gov.ca.cwds.rest.api.domain.cms.AgencyType;
import gov.ca.cwds.rest.api.domain.enums.ScreeningStatus;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.resources.parameter.ParticipantResourceParameters;
import gov.ca.cwds.rest.services.mapper.AddressMapper;
import gov.ca.cwds.rest.services.mapper.AgencyMapper;
import gov.ca.cwds.rest.services.mapper.AllegationMapper;
import gov.ca.cwds.rest.services.mapper.CrossReportMapper;
import gov.ca.cwds.rest.services.mapper.ScreeningMapper;
import gov.ca.cwds.rest.services.screening.participant.ParticipantService;

/**
 * Business layer object to work on {@link Screening}.
 *
 * @author CWDS API Team
 */
public class ScreeningService implements CrudsService {

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
  private ParticipantService participantService;

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
      screeningDashboard.add(new ScreeningDashboard(screening));
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
    final Screening screening = (Screening) request;

    if (!primaryKey.equals(screening.getId())) {
      throw new ServiceException(
          "Primary key mismatch, [" + primaryKey + " != " + screening.getId() + "]");
    }
    return (Screening) request;
  }

  /**
   * @param id - id
   * @return the Screening
   */
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
      List<GovernmentAgencyEntity> lastAgencyEntities = filterLastUpdatedByCategory(agencyEntities);

      final Set<gov.ca.cwds.rest.api.domain.GovernmentAgencyIntake> agencies =
          agencyMapper.map(lastAgencyEntities);
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
        participantService.getByScreeningId(screeningId);

    for (ParticipantEntity participantEntity : participantEntities) {
      final ParticipantIntakeApi participantIntakeApi =
          participantService.find(new ParticipantResourceParameters(id, participantEntity.getId()));
      screening.getParticipantIntakeApis().add(participantIntakeApi);
    }

    return screening;
  }

  List<GovernmentAgencyEntity> filterLastUpdatedByCategory(
      List<GovernmentAgencyEntity> agencyEntities) {

    String[] agencyTypes = {AgencyType.DISTRICT_ATTORNEY.name(), AgencyType.LAW_ENFORCEMENT.name(),
        AgencyType.COMMUNITY_CARE_LICENSING.name(), AgencyType.COUNTY_LICENSING.name(),};

    List<GovernmentAgencyEntity> filteredAgencyEntities = new ArrayList<>();
    for (String agencyType : agencyTypes) {
      CollectionUtils.addIgnoreNull(filteredAgencyEntities,
          agencyEntities.stream().filter(a -> agencyType.equals(a.getCategory()))
              .max((a1, a2) -> a1.getUpdatedAt().compareTo(a2.getUpdatedAt())).orElse(null));
    }
    return filteredAgencyEntities;
  }

  /**
   * @param screening - screening
   * @return the POST screening
   */
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

  /**
   * @param id - id
   * @param screening - screening
   * @return the Updated screening
   */
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
        allegationDao.grabSession().detach(managedAllegationEntity);
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
        crossReportDao.grabSession().detach(managedCrossReportEntity);
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
        agencyDao.grabSession().detach(managedAgencyEntity);
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
    final Set<String> participantIdsOld = participantService.getByScreeningId(screening.getId())
        .stream().map(ParticipantEntity::getId).collect(Collectors.toSet());

    for (ParticipantIntakeApi participantIntakeApi : screening.getParticipantIntakeApis()) {
      final String participantIntakeApiId = participantIntakeApi.getId();
      if (participantIntakeApiId == null) {
        participantIntakeApi.setScreeningId(screening.getId());
        final ParticipantIntakeApi createdParticipantIntakeApi =
            participantService.persistParticipantObjectInNS(participantIntakeApi);
        participantIntakeApis.add(createdParticipantIntakeApi);
      } else {
        final ParticipantIntakeApi updatedParticipantIntakeApi = participantService.update(
            new ParticipantResourceParameters(screening.getId(), participantIntakeApiId),
            participantIntakeApi);
        participantIntakeApis.add(updatedParticipantIntakeApi);
        participantIdsOld.remove(participantIntakeApiId);
      }
    }

    // Delete old ones that are not in the new.
    participantIdsOld.forEach(participantId -> participantService
        .delete(new ParticipantResourceParameters(screening.getId(), participantId)));

    screening.setParticipantIntakeApis(participantIntakeApis);
  }

  void setScreeningDao(ScreeningDao screeningDao) {
    this.screeningDao = screeningDao;
  }

  public AllegationIntakeDao getAllegationDao() {
    return allegationDao;
  }

  public void setAllegationDao(AllegationIntakeDao allegationDao) {
    this.allegationDao = allegationDao;
  }

  public AddressesDao getAddressesDao() {
    return addressesDao;
  }

  public void setAddressesDao(AddressesDao addressesDao) {
    this.addressesDao = addressesDao;
  }

  public ScreeningAddressDao getScreeningAddressDao() {
    return screeningAddressDao;
  }

  public void setScreeningAddressDao(ScreeningAddressDao screeningAddressDao) {
    this.screeningAddressDao = screeningAddressDao;
  }

  public AgencyDao getAgencyDao() {
    return agencyDao;
  }

  public void setAgencyDao(AgencyDao agencyDao) {
    this.agencyDao = agencyDao;
  }

  public CrossReportDao getCrossReportDao() {
    return crossReportDao;
  }

  public void setCrossReportDao(CrossReportDao crossReportDao) {
    this.crossReportDao = crossReportDao;
  }

  public ParticipantService getParticipantService() {
    return participantService;
  }

  public void setParticipantService(ParticipantService participantService) {
    this.participantService = participantService;
  }

  public ScreeningMapper getScreeningMapper() {
    return screeningMapper;
  }

  public void setScreeningMapper(ScreeningMapper screeningMapper) {
    this.screeningMapper = screeningMapper;
  }

  public AddressMapper getAddressMapper() {
    return addressMapper;
  }

  public void setAddressMapper(AddressMapper addressMapper) {
    this.addressMapper = addressMapper;
  }

  public AgencyMapper getAgencyMapper() {
    return agencyMapper;
  }

  public void setAgencyMapper(AgencyMapper agencyMapper) {
    this.agencyMapper = agencyMapper;
  }

  public AllegationMapper getAllegationMapper() {
    return allegationMapper;
  }

  public void setAllegationMapper(AllegationMapper allegationMapper) {
    this.allegationMapper = allegationMapper;
  }

  public CrossReportMapper getCrossReportMapper() {
    return crossReportMapper;
  }

  public void setCrossReportMapper(CrossReportMapper crossReportMapper) {
    this.crossReportMapper = crossReportMapper;
  }

  public ScreeningDao getScreeningDao() {
    return screeningDao;
  }

}
