package gov.ca.cwds.rest.services.screeningparticipant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 *
 */
public class ParticipantDaoFactoryImpl implements ParticipantDaoFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantDaoFactoryImpl.class);

  private static final String SOURCE_PACKAGE = "gov.ca.cwds.data.cms.";

  @Inject
  private Injector injector;

  @Override
  public CrudsDao<CmsPersistentObject> create(String tableName) {
    CrudsDao<CmsPersistentObject> crudsDao;
    String name = SOURCE_PACKAGE + LegacyDaoMapperEnum.findByTableName(tableName).getDaoName();
    try {
      @SuppressWarnings("unchecked")
      Class<CrudsDao<CmsPersistentObject>> daoclass =
          (Class<CrudsDao<CmsPersistentObject>>) Class.forName(name);
      crudsDao = injector.getInstance(daoclass);
    } catch (ClassNotFoundException e) {
      LOGGER.error("Unable to load the class {}", name);
      throw new ServiceException(e);
    }
    return crudsDao;
  }

  /**
   * @param injector - injector
   */
  public void setInjector(Injector injector) {
    this.injector = injector;
  }

}
