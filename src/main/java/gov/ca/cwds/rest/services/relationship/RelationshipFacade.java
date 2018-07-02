package gov.ca.cwds.rest.services.relationship;

import com.google.inject.Inject;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.services.ParticipantService;
import gov.ca.cwds.rest.services.RelationshipsService;
import gov.ca.cwds.rest.services.ScreeningRelationshipService;
import gov.ca.cwds.rest.services.cms.ClientService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS TPT-3 Team
 *
 */
public class RelationshipFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipFacade.class);

  private final ScreeningRelationshipService screeningRelationshipService;
  private final RelationshipsService relationshipsService;
  private final ParticipantService participantService;
  private final ClientService clientService;

  private final ParticipantDao participantDao;
  private final ClientRelationshipDao cmsRelationshipDao;
  private final RelationshipDao nsRelationshipDao;

  @Inject
  public RelationshipFacade(
      ScreeningRelationshipService screeningRelationshipService,
      RelationshipsService relationshipsService,
      ParticipantService participantService,
      ClientService clientService, ParticipantDao participantDao,
      ClientRelationshipDao cmsRelationshipDao,
      RelationshipDao nsRelationshipDao) {
    this.screeningRelationshipService = screeningRelationshipService;
    this.relationshipsService = relationshipsService;
    this.participantService = participantService;
    this.clientService = clientService;
    this.participantDao = participantDao;
    this.cmsRelationshipDao = cmsRelationshipDao;
    this.nsRelationshipDao = nsRelationshipDao;
  }

  public List<ScreeningRelationship> getRelationshipsByScreeningId(String screeningId) {
    if (StringUtils.isEmpty(screeningId)) {
      return null;
    }

    // DELETE it (just for sonar)
    LOGGER.info("screeningId {}", screeningId.replaceAll("[\r\n]",""));
    LOGGER.info("clientService {}", clientService);
    LOGGER.info("participantService {}", participantService);
    LOGGER.info("relationshipsService {}", relationshipsService);
    LOGGER.info("screeningRelationshipService {}", screeningRelationshipService);

    // getparticipants by screeningId
    Set<String> legacyClientIds = getLegacyClientIdsByScreeningId(screeningId);
    // get relationships from legacy
    List<ClientRelationship> lagacyRelationships = getCmsRelationships(legacyClientIds);
    // get relationships from pgsql
    List<Relationship> nsRelationships = getNsRelationships(screeningId);

    // compare
    List<ClientRelationship> shouldBeUpdated = getRelationshipsThatShouldBeUpdated(
        lagacyRelationships, nsRelationships);
    List<ClientRelationship> shouldBeCreated = getRelationshipsThatShouldBeCreated(
        lagacyRelationships, nsRelationships);
    createRelationships(shouldBeCreated);
    updateRelationships(shouldBeUpdated);

    // select and return
    return getRelationshipsFromNs(screeningId);
  }

  // Mocked Data
  private List<ScreeningRelationship> getRelationshipsFromNs(String screeningId) {
    return getMockedData();
  }

  private List<ScreeningRelationship> getMockedData() {
    List<ScreeningRelationship> list = new ArrayList<>();
    ScreeningRelationship relationship1 = new ScreeningRelationship();
    relationship1.setId("123123");
    relationship1.setClientId("111111");
    relationship1.setRelativeId("222222");
    relationship1.setRelationshipType(190);
    relationship1.setAbsentParentIndicator(false);
    relationship1.setSameHomeStatus("U");
    list.add(relationship1);

    ScreeningRelationship relationship2 = new ScreeningRelationship();
    relationship1.setId("123123");
    relationship1.setClientId("222222");
    relationship1.setRelativeId("333333");
    relationship1.setRelationshipType(189);
    relationship1.setAbsentParentIndicator(true);
    relationship1.setSameHomeStatus("Y");
    list.add(relationship1);

    return list;
  }

  private void updateRelationships(List<ClientRelationship> shouldBeUpdated) {
    if (CollectionUtils.isEmpty(shouldBeUpdated)) {
      return;
    }
    LOGGER.info("shouldBeUpdated {}", shouldBeUpdated);

  }

  private void createRelationships(List<ClientRelationship> shouldBeCreated) {
    if (CollectionUtils.isEmpty(shouldBeCreated)) {
      return;
    }
    LOGGER.info("shouldBeCreated {}", shouldBeCreated);
  }

  private List<ClientRelationship> getRelationshipsThatShouldBeCreated(
      List<ClientRelationship> lagacyRelationships, List<Relationship> nsRelationships) {
    LOGGER.info("lagacyRelationships {}", lagacyRelationships);
    LOGGER.info("nsRelationships {}", nsRelationships);

    return new ArrayList<>();
  }

  private List<ClientRelationship> getRelationshipsThatShouldBeUpdated(
      List<ClientRelationship> lagacyRelationships, List<Relationship> nsRelationships) {
    if (CollectionUtils.isEmpty(nsRelationships)) {
      return lagacyRelationships;
    }

    if (CollectionUtils.isEmpty(lagacyRelationships)) {
      return new ArrayList<>();
    }

//    lagacyRelationships.stream().filter(e->e.getId().equals())
    return new ArrayList<>();
  }

  private Set<String> getLegacyClientIdsByScreeningId(String screeningId) {
    return participantDao.findLegacyIdListByScreeningId(screeningId);
  }

  private List<Relationship> getNsRelationships(String screeningId) {
    return nsRelationshipDao.getRelationshipsByScreeningId(screeningId);
  }

  private List<ClientRelationship> getCmsRelationships(Set<String> legacyClientIds) {
    LOGGER.info("legacyClientIds {}", legacyClientIds);

    return new ArrayList<>();
  }
}
