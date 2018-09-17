package gov.ca.cwds.rest.services.cms;

import static gov.ca.cwds.rest.core.Api.DS_CMS;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.FlushMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.cms.PostedStaffPerson;
import gov.ca.cwds.rest.api.domain.cms.StaffPerson;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.IdGenerator;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business layer object to work on {@link StaffPerson}.
 * 
 * @author CWDS API Team
 */
public class StaffPersonService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StaffPersonService.class);

  private StaffPersonDao staffPersonDao;

  /**
   * Constructor
   * 
   * @param staffPersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   */
  @Inject
  public StaffPersonService(StaffPersonDao staffPersonDao) {
    this.staffPersonDao = staffPersonDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @UnitOfWork(value = DS_CMS, readOnly = true, transactional = false, flushMode = FlushMode.MANUAL)
  @Override
  public gov.ca.cwds.rest.api.domain.cms.StaffPerson find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.StaffPerson persistedStaffPerson =
        staffPersonDao.find(primaryKey);
    if (persistedStaffPerson != null) {
      return new gov.ca.cwds.rest.api.domain.cms.StaffPerson(persistedStaffPerson);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @UnitOfWork(DS_CMS)
  @Override
  public gov.ca.cwds.rest.api.domain.cms.StaffPerson delete(Serializable primaryKey) {
    assert primaryKey instanceof String;
    gov.ca.cwds.data.persistence.cms.StaffPerson persistedStaffPerson =
        staffPersonDao.delete(primaryKey);
    if (persistedStaffPerson != null) {
      return new gov.ca.cwds.rest.api.domain.cms.StaffPerson(persistedStaffPerson);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @UnitOfWork(DS_CMS)
  @Override
  public PostedStaffPerson create(Request request) {
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.StaffPerson;
    StaffPerson staffPerson = (StaffPerson) request;

    try {
      gov.ca.cwds.data.persistence.cms.StaffPerson managed =
          new gov.ca.cwds.data.persistence.cms.StaffPerson(IdGenerator.randomString(3), staffPerson,
              RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());

      managed = staffPersonDao.create(managed);
      return new PostedStaffPerson(managed);
    } catch (EntityExistsException e) {
      LOGGER.warn("StaffPerson already exists : {}", staffPerson);
      throw new ServiceException(e);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @UnitOfWork(DS_CMS)
  @Override
  public StaffPerson update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof String;
    assert request instanceof gov.ca.cwds.rest.api.domain.cms.StaffPerson;
    gov.ca.cwds.rest.api.domain.cms.StaffPerson staffPerson =
        (gov.ca.cwds.rest.api.domain.cms.StaffPerson) request;

    try {
      gov.ca.cwds.data.persistence.cms.StaffPerson managed =
          new gov.ca.cwds.data.persistence.cms.StaffPerson((String) primaryKey, staffPerson,
              RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());

      managed = staffPersonDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.StaffPerson(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.warn("StaffPerson not found : {}", staffPerson);
      throw new ServiceException(e);
    }
  }

}
