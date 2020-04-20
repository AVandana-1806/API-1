package gov.ca.cwds.rest.services.screening.participant;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.cms.data.access.service.impl.CsecHistoryService;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.enums.ScreeningStatus;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.mapper.cms.CsecMapper;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantDaoFactoryImpl;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantMapper;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantMapperFactoryImpl;

/**
 * Business layer object to work on ParticipantIntakeApi
 *
 * @author CWDS API Team
 */
public class ParticipantTransformer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantTransformer.class);

  @Inject
  private ScreeningDao screeningDao;

  @Inject
  private ParticipantDaoFactoryImpl participantDaoFactory;

  @Inject
  private ParticipantMapperFactoryImpl<CmsPersistentObject> participantMapperFactoryImpl;

  @Inject
  private ClientDao clientDao;

  @Inject
  private CsecMapper csecMapper;

  @Inject
  private CsecHistoryService csecHistoryService;

  /**
   * @param incomingParticipantIntakeApi - incomingParticipantIntakeApi
   * @return the
   */
  public ParticipantIntakeApi prepareParticipantObject(
      ParticipantIntakeApi incomingParticipantIntakeApi) {
    if (StringUtils.isBlank(incomingParticipantIntakeApi.getScreeningId())) {
      LOGGER.error("Screening is required to create the particpant {}",
          incomingParticipantIntakeApi.getScreeningId());
      throw new ServiceException("Screening is required to create the particpant");
    }
    ensureScreeningExistsAndOpen(incomingParticipantIntakeApi);
    return loadParticipant(incomingParticipantIntakeApi);
  }

  public ParticipantIntakeApi loadParticipant(ParticipantIntakeApi incomingParticipantIntakeApi) {
    LegacyDescriptor legacyDescriptor = incomingParticipantIntakeApi.getLegacyDescriptor();
    if (legacyDescriptor == null || StringUtils.isBlank(legacyDescriptor.getId())
        || StringUtils.isBlank(legacyDescriptor.getTableName())) {
      return incomingParticipantIntakeApi;
    }

    ParticipantIntakeApi participant =
        findParticipant(legacyDescriptor.getId(), legacyDescriptor.getTableName());
    if (participant != null) {
      addCsecTo(participant);
      participant.setScreeningId(incomingParticipantIntakeApi.getScreeningId());
      participant.setProbationYouth(isProbationYouth(legacyDescriptor.getId()));
      participant.setRelatedScreeningId(incomingParticipantIntakeApi.getScreeningId());
    }
    return participant;
  }

  private void addCsecTo(ParticipantIntakeApi participant) {
    if (participant != null && participant.getLegacyDescriptor() != null) {
      participant.setCsecs(csecMapper
          .toDomain(csecHistoryService.findByClientId(participant.getLegacyDescriptor().getId())));
    }
  }

  private ParticipantIntakeApi findParticipant(String id, String tableName) {
    CmsPersistentObject persistentObject;
    ParticipantMapper<CmsPersistentObject> participantMapper;
    CrudsDao<CmsPersistentObject> crudsDaoObject = participantDaoFactory.create(tableName);
    ParticipantIntakeApi participant = null;
    if ((persistentObject = crudsDaoObject.find(id)) != null) {
      participantMapper = participantMapperFactoryImpl.create(tableName);
      participant = participantMapper.transform(persistentObject);
    } else {
      LOGGER.error("Object is not found with the given identifier {}", id);
    }
    return participant;
  }

  private void ensureScreeningExistsAndOpen(ParticipantIntakeApi participantIntakeApi) {
    ScreeningEntity screening = null;
    if ((screening = screeningDao.find(participantIntakeApi.getScreeningId())) == null) {
      LOGGER.error("Screening not found {}", participantIntakeApi.getScreeningId());
      throw new EntityNotFoundException("Screening not found");
    } else if (ScreeningStatus.SUBMITTED.getStatus().equals(screening.getScreeningStatus())) {
      LOGGER.error("Screeening is already Submitted {}", screening.getScreeningStatus());
      throw new ServiceException("Screeening is already Submitted");
    }
  }

  private Boolean isProbationYouth(String clientId) {
    return clientDao.findProbationYouth(clientId) != null;
  }

  /**
   * @param screeningDao - screeningDao
   */
  public void setScreeningDao(ScreeningDao screeningDao) {
    this.screeningDao = screeningDao;
  }


  /**
   * @param clientDao - clientDao
   */
  public void setClientDao(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  /**
   * @param participantDaoFactory - participantDaoFactory
   */
  public void setParticipantDaoFactory(ParticipantDaoFactoryImpl participantDaoFactory) {
    this.participantDaoFactory = participantDaoFactory;
  }

  /**
   * @param participantMapperFactoryImpl - participantMapperFactoryImpl
   */
  public void setParticipantMapperFactoryImpl(
      ParticipantMapperFactoryImpl<CmsPersistentObject> participantMapperFactoryImpl) {
    this.participantMapperFactoryImpl = participantMapperFactoryImpl;
  }

  /**
   * Set the CSEC service for processing CSEC.
   *
   * @param csecHistoryService - csecHistoryService
   */
  public void setCsecHistoryService(CsecHistoryService csecHistoryService) {
    this.csecHistoryService = csecHistoryService;
  }

  /**
   * Set the mapper to convert CSEC entity to CSEC domain object
   *
   * @param csecMapper - csecMapper
   */
  public void setCsecMapper(CsecMapper csecMapper) {
    this.csecMapper = csecMapper;
  }
}
