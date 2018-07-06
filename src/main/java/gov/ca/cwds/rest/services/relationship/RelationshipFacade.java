package gov.ca.cwds.rest.services.relationship;

import java.util.*;

import gov.ca.cwds.rest.api.domain.Screening;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  public RelationshipFacade(ScreeningRelationshipService screeningRelationshipService,
      RelationshipsService relationshipsService, ParticipantService participantService,
      ClientService clientService, ParticipantDao participantDao,
      ClientRelationshipDao cmsRelationshipDao, RelationshipDao nsRelationshipDao) {
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
      return Collections.emptyList();
    }

    // DELETE it (just for sonar)
    LOGGER.info("screeningId {}", screeningId.replaceAll("[\r\n]", ""));
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
    List<ClientRelationship> shouldBeUpdated =
        getRelationshipsThatShouldBeUpdated(lagacyRelationships, nsRelationships);
    List<ClientRelationship> shouldBeCreated =
        getRelationshipsThatShouldBeCreated(lagacyRelationships, nsRelationships);
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
    for(ClientRelationship clientRelationship : shouldBeUpdated){
        Relationship managed = nsRelationshipDao.getByLegacyId(clientRelationship.getId());
        if(managed != null){
            managed.setRelationshipType(clientRelationship.getClientRelationshipType());
            managed.setStartDate(clientRelationship.getStartDate());
            managed.setAbsentParentIndicator("Y".equals(clientRelationship.getAbsentParentCode()));
            managed.setSameHomeStatus("Y".equals(clientRelationship.getSameHomeCode()));
            managed.setEndDate(clientRelationship.getEndDate());
            nsRelationshipDao.update(managed);
        }
    }

  }

  private void createRelationships(List<ClientRelationship> shouldBeCreated) {
    if (CollectionUtils.isEmpty(shouldBeCreated)) {
      return;
    }
    LOGGER.info("shouldBeCreated {}", shouldBeCreated);
//    for(ClientRelationship clientRelationship : shouldBeCreated ) {
//        Relationship newRelationship = new Relationship(
//                null,  **create participants for clients**
//
//        )
//    }
  }

  private List<ClientRelationship> getRelationshipsThatShouldBeCreated(
      List<ClientRelationship> lagacyRelationships, List<Relationship> nsRelationships) {
    LOGGER.info("lagacyRelationships {}", lagacyRelationships);
    LOGGER.info("nsRelationships {}", nsRelationships);
    List<ClientRelationship> relationshipsToCreate = new ArrayList<>();
    for (ClientRelationship clientRelationship : lagacyRelationships) {
        boolean exist = false;
        for(Relationship relationship : nsRelationships) {
            if (clientRelationship.getId().equals(relationship.getLegacyId())) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            relationshipsToCreate.add(clientRelationship);
        }
    }
    return relationshipsToCreate;
  }

  private List<ClientRelationship> getRelationshipsThatShouldBeUpdated(
      List<ClientRelationship> lagacyRelationships, List<Relationship> nsRelationships) {
    if (CollectionUtils.isEmpty(nsRelationships)) {
      return lagacyRelationships;
    }

    if (CollectionUtils.isEmpty(lagacyRelationships)) {
      return new ArrayList<>();
    }

    // lagacyRelationships.stream().filter(e->e.getId().equals())
      List<ClientRelationship> relationshipsToUpdate = new ArrayList<>();
      for (ClientRelationship clientRelationship : lagacyRelationships) {
          boolean update = false;
          for(Relationship relationship : nsRelationships) {
              if (clientRelationship.getId().equals(relationship.getLegacyId())) {
                  if( "Y".equals(clientRelationship.getAbsentParentCode())!= relationship.isAbsentParentIndicator()
                          || "Y".equals(clientRelationship.getSameHomeCode()) != relationship.getSameHomeStatus()
                          || clientRelationship.getClientRelationshipType() != relationship.getRelationshipType()
                          || clientRelationship.getStartDate() != relationship.getStartDate()
                          || clientRelationship.getEndDate() != relationship.getEndDate()){
                      update = true;
                  }
                  break;
              }

          }
          if (update) {

              relationshipsToUpdate.add(clientRelationship);
          }
      }
      return relationshipsToUpdate;
  }

  private Set<String> getLegacyClientIdsByScreeningId(String screeningId) {
    return participantDao.findLegacyIdListByScreeningId(screeningId);
  }

  private List<Relationship> getNsRelationships(String screeningId) {
    return nsRelationshipDao.getRelationshipsByScreeningId(screeningId);
  }

  private List<ClientRelationship> getCmsRelationships(Set<String> legacyClientIds) {
    LOGGER.info("legacyClientIds {}", legacyClientIds);
        List<ClientRelationship> relationshipListcms = new ArrayList<>();
      Map<String, Collection<ClientRelationship>> relationshipMap =
              cmsRelationshipDao.findByPrimaryClientIds(legacyClientIds);
      for (Map.Entry<String, Collection<ClientRelationship>> relationshipMapEntry : relationshipMap.entrySet()) {
          String clientId = relationshipMapEntry.getKey();
          relationshipListcms.addAll(relationshipMapEntry.getValue());
      };
    return relationshipListcms;
  }

}
