package gov.ca.cwds.rest.services.cms;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientScpEthnicityDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link gov.ca.cwds.data.persistence.cms.ClientScpEthnicity}
 * 
 * @author CWDS API Team
 */
public class ClientScpEthnicityService
    implements TypedCrudsService<String, ClientScpEthnicity, ClientScpEthnicity> {

  private static final String CLIENT_ESTABLISHED_CODE = "C";

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientScpEthnicityService.class);
  private ClientScpEthnicityDao clientScpEthnicityDao;

  /**
   * @param clientScpEthnicityDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientScpEthnicity} objects.
   */
  @Inject
  public ClientScpEthnicityService(ClientScpEthnicityDao clientScpEthnicityDao) {
    this.clientScpEthnicityDao = clientScpEthnicityDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ClientScpEthnicity create(ClientScpEthnicity request) {
    ClientScpEthnicity clientScpEthnicity = request;

    try {
      gov.ca.cwds.data.persistence.cms.ClientScpEthnicity managed =
          new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(
              CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
              clientScpEthnicity, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = clientScpEthnicityDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("clientScpEthnicity already exists : {}", clientScpEthnicity);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public ClientScpEthnicity delete(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity persistedClientScpEthnicity =
        clientScpEthnicityDao.delete(primaryKey);
    if (persistedClientScpEthnicity != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity(persistedClientScpEthnicity);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public ClientScpEthnicity find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.ClientScpEthnicity persistedClientScpEthnicity =
        clientScpEthnicityDao.find(primaryKey);
    if (persistedClientScpEthnicity != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity(persistedClientScpEthnicity);
    }
    return null;
  }

  @Override
  public ClientScpEthnicity update(String primaryKey, ClientScpEthnicity request) {

    ClientScpEthnicity clientScpEthnicity = request;

    try {
      gov.ca.cwds.data.persistence.cms.ClientScpEthnicity managed =
          new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(primaryKey, clientScpEthnicity,
              primaryKey, RequestExecutionContext.instance().getRequestStartTime());
      managed = clientScpEthnicityDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ClientScpEthnicity(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("clientScpEthnicity not found : {}", clientScpEthnicity);
      throw new ServiceException(e);
    }
  }

  /**
   * If the client has more than one raceCode or hispanicCode it create a new record as secondary
   * ethnicity in the clientScpEthnicity table.
   * 
   * @param clientId - clientId
   * @param otherRaceCodes - race codes
   */
  public void createOtherEthnicity(String clientId, List<Short> otherRaceCodes) {
    if (otherRaceCodes != null && !otherRaceCodes.isEmpty()) {
      deleteExistingRecords(clientId);
      for (Short code : otherRaceCodes) {
        gov.ca.cwds.data.persistence.cms.ClientScpEthnicity clientScpEthnicity =
            new gov.ca.cwds.data.persistence.cms.ClientScpEthnicity(
                CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
                CLIENT_ESTABLISHED_CODE, clientId, code,
                RequestExecutionContext.instance().getStaffId(),
                RequestExecutionContext.instance().getRequestStartTime());
        clientScpEthnicityDao.create(clientScpEthnicity);
      }
    }
  }

  /**
   * 
   * @param code - race and hispanic codes
   * @param clientId - clientId
   */
  private void deleteExistingRecords(String clientId) {
    List<gov.ca.cwds.data.persistence.cms.ClientScpEthnicity> clientScpEthnicityList =
        clientScpEthnicityDao.getClientScp(clientId);
    for (gov.ca.cwds.data.persistence.cms.ClientScpEthnicity clientScpEthnicity : clientScpEthnicityList) {
      clientScpEthnicityDao.delete(clientScpEthnicity.getPrimaryKey());
    }
  }

}
