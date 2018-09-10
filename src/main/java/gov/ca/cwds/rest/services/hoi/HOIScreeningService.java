package gov.ca.cwds.rest.services.hoi;

import static gov.ca.cwds.rest.core.Api.DS_CMS;
import static gov.ca.cwds.rest.core.Api.DS_NS;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.FlushMode;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.ns.IntakeLOVCodeDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreeningResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on Screening History Of Involvement
 *
 * @author CWDS API Team
 */
public class HOIScreeningService
    extends SimpleResourceService<HOIRequest, HOIScreening, HOIScreeningResponse> {

  @Inject
  ScreeningDao screeningDao;

  @Inject
  ParticipantDao participantDao;

  @Inject
  IntakeLOVCodeDao intakeLOVCodeDao;

  @Inject
  LegacyDescriptorDao legacyDescriptorDao;

  @Inject
  StaffPersonDao staffPersonDao;

  @Inject
  HOIScreeningFactory hoiScreeningFactory;

  @Inject
  AuthorizationService authorizationService;

  private Comparator<HOIScreening> screeningsComparator;

  /**
   * Construct the object
   */
  public HOIScreeningService() {
    super();
    screeningsComparator = (s1, s2) -> {
      if (s2.getStartDate() == null) {
        return 1;
      } else if (s1.getStartDate() == null) {
        return -1;
      } else {
        return s2.getStartDate().compareTo(s1.getStartDate());
      }
    };
  }

  /**
   * @param hoiRequest HOI Request containing a list of Client Id-s
   * @return list of HOI Screenings
   */
  @Override
  public HOIScreeningResponse handleFind(HOIRequest hoiRequest) {
    final HOIScreeningData hoiScreeningData = new HOIScreeningData(hoiRequest.getClientIds());
    loadDataFromNS(hoiScreeningData);
    loadDataFromCMS(hoiScreeningData);
    return new HOIScreeningResponse(buildHoiScreenings(hoiScreeningData));
  }

  @UnitOfWork(value = DS_NS, readOnly = true, transactional = false, flushMode = FlushMode.MANUAL)
  @SuppressWarnings("WeakerAccess") // can't be private because the @UnitOfWork will not play
  protected void loadDataFromNS(HOIScreeningData hoiScreeningData) {
    fetchDataFromNS(hoiScreeningData);
  }

  void fetchDataFromNS(HOIScreeningData hsd) {
    /*
     * NOTE: When we want to enable authorizations for screening history, we can add following line
     * of code back at this spot:<br/>
     * authorizationService&#46;ensureClientAccessAuthorized&#40;clientIds&#41;&#59;
     */
    final Collection<String> filteredClientIds =
        hsd.getClientIds().stream().filter(Objects::nonNull).collect(Collectors.toSet());
    if (filteredClientIds.isEmpty()) {
      return;
    }

    final Set<ScreeningEntity> screeningEntities =
        screeningDao.findScreeningsByClientIds(filteredClientIds);
    hsd.getScreeningEntities().addAll(screeningEntities);

    final Map<String, Set<ParticipantEntity>> participantEntitiesMap =
        participantDao.findByScreeningIds(
            screeningEntities.stream().map(ScreeningEntity::getId).collect(Collectors.toSet()));

    final Set<String> counties = new HashSet<>();
    final Set<String> participantIds = new HashSet<>();
    final Collection<String> assigneeStaffIds = new HashSet<>();

    for (ScreeningEntity screeningEntity : screeningEntities) {
      if (participantEntitiesMap.containsKey(screeningEntity.getId())) {
        screeningEntity.setParticipants(participantEntitiesMap.get(screeningEntity.getId()));
      }

      counties.add(screeningEntity.getIncidentCounty());
      if (screeningEntity.getParticipants() != null) {
        for (ParticipantEntity participantEntity : screeningEntity.getParticipants()) {
          participantIds.add(participantEntity.getId());
        }
      }
      if (screeningEntity.getAssigneeStaffId() != null) {
        assigneeStaffIds.add(screeningEntity.getAssigneeStaffId());
      }
    }

    hsd.setCountyIntakeLOVCodeEntityMap(intakeLOVCodeDao.findIntakeLOVCodesByIntakeCodes(counties));
    hsd.setParticipantLegacyDescriptors(
        legacyDescriptorDao.findParticipantLegacyDescriptors(participantIds));
    hsd.setAssigneeStaffIds(assigneeStaffIds);
  }

  @UnitOfWork(value = DS_CMS, readOnly = true, transactional = false, flushMode = FlushMode.MANUAL)
  @SuppressWarnings("WeakerAccess") // can't be private because the @UnitOfWork will not play
  protected void loadDataFromCMS(HOIScreeningData hoiScreeningData) {
    fetchDataFromCMS(hoiScreeningData);
  }

  void fetchDataFromCMS(HOIScreeningData hsd) {
    hsd.setStaffPersonMap(staffPersonDao.findByIds(hsd.getAssigneeStaffIds()));
  }

  Set<HOIScreening> buildHoiScreenings(HOIScreeningData hsd) {
    final Set<HOIScreening> screenings = new TreeSet<>(screeningsComparator);
    for (ScreeningEntity screeningEntity : hsd.getScreeningEntities()) {
      /*
       * NOTE: When we want to enable authorizations for screening history, we can add following
       * line of code back at this spot:<br/>
       * authorizationService&#46;ensureScreeningAccessAuthorized&#40;screeningEntity&#41;&#59;
       */
      screenings.add(hoiScreeningFactory.buildHOIScreening(screeningEntity,
          hsd.getCountyIntakeLOVCodeEntityMap().get(screeningEntity.getIncidentCounty()),
          hsd.getParticipantLegacyDescriptors(),
          hsd.getStaffPersonMap().get(screeningEntity.getAssigneeStaffId())));
    }
    return screenings;
  }

  @Override
  public HOIScreeningResponse handleRequest(HOIScreening hoiScreening) {
    LOGGER.info("HOIScreeningService handle request not implemented");
    throw new NotImplementedException("handle request not implemented");
  }

}
