package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link LongText}
 * 
 * @author CWDS API Team
 */
public class LongTextService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.LongText, gov.ca.cwds.rest.api.domain.cms.LongText> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LongTextService.class);

  private LongTextDao longTextDao;

  /**
   * Constructor
   * 
   * @param longTextDao {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.LongText}
   *        objects
   */
  @Inject
  public LongTextService(LongTextDao longTextDao) {
    super();
    this.longTextDao = longTextDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.LongText find(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.LongText persistedLongText = longTextDao.find(primaryKey);
    if (persistedLongText != null) {
      return new gov.ca.cwds.rest.api.domain.cms.LongText(persistedLongText);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.LongText delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.LongText persistedLongText = longTextDao.delete(primaryKey);
    if (persistedLongText != null) {
      return new gov.ca.cwds.rest.api.domain.cms.LongText(persistedLongText);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedLongText create(gov.ca.cwds.rest.api.domain.cms.LongText request) {
    gov.ca.cwds.rest.api.domain.cms.LongText longText = request;

    try {
      LongText managed = new LongText(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()), longText,
          RequestExecutionContext.instance().getStaffId());
      managed = longTextDao.create(managed);
      return new PostedLongText(managed);
    } catch (EntityExistsException e) {
      LOGGER.warn("LongText already exists : {}", longText);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.LongText update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.LongText request) {
    gov.ca.cwds.rest.api.domain.cms.LongText longText = request;

    try {
      LongText managed =
          new LongText(primaryKey, longText, RequestExecutionContext.instance().getStaffId());
      managed = longTextDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.LongText(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.warn("LongText not found : {}", longText);
      throw new ServiceException(e);
    }
  }

}
