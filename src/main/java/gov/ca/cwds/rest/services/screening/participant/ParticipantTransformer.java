package gov.ca.cwds.rest.services.screening.participant;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.enums.ScreeningStatus;
import gov.ca.cwds.rest.services.ServiceException;
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

  public ParticipantIntakeApi prepareParticipantObject(
      ParticipantIntakeApi incomingParticipantIntakeApi) {
    if (StringUtils.isBlank(incomingParticipantIntakeApi.getScreeningId())) {
      LOGGER.error("Screening is required to create the particpant {}",
          incomingParticipantIntakeApi.getScreeningId());
      throw new ServiceException("Screening is required to create the particpant");
    }
    ensureScreeningExistsAndOpen(incomingParticipantIntakeApi);
    ParticipantIntakeApi participantIntakeApi = null;
    LegacyDescriptor legacyDescriptor = incomingParticipantIntakeApi.getLegacyDescriptor();

    if (legacyDescriptor != null && StringUtils.isNotBlank(legacyDescriptor.getId())
        && StringUtils.isNotBlank(legacyDescriptor.getTableName())) {
      participantIntakeApi =
          transformParticipant(legacyDescriptor.getId(), legacyDescriptor.getTableName());
      participantIntakeApi.setScreeningId(incomingParticipantIntakeApi.getScreeningId());
      return participantIntakeApi;
    } else {
      return incomingParticipantIntakeApi;
    }
  }

  private ParticipantIntakeApi transformParticipant(String id, String tableName) {
    CmsPersistentObject persistentObject;
    ParticipantMapper<CmsPersistentObject> participantMapper;
    CrudsDao<CmsPersistentObject> crudsDaoObject = participantDaoFactory.create(tableName);
    if ((persistentObject = crudsDaoObject.find(id)) != null) {
      participantMapper = participantMapperFactoryImpl.create(tableName);
      return participantMapper.tranform(persistentObject);
    } else {
      LOGGER.error("Object is not found with the given identifier {}", id);
      throw new ServiceException("");
    }
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

  /**
   * @param screeningDao - screeningDao
   */
  public void setScreeningDao(ScreeningDao screeningDao) {
    this.screeningDao = screeningDao;
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

}
