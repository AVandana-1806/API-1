package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.persistence.cms.CrossReport;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.cms.CrossReportDao;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.IdGenerator;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Business layer object to work on {@link CrossReport}
 * 
 * @author CWDS API Team
 */
public class CrossReportService implements CrudsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CrossReportService.class);

  private CrossReportDao crossReportDao;

  /**
   * Constructor
   * 
   * @param crossReportDao The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.persistence.cms.CrossReport} objects.
   */
  public CrossReportService(CrossReportDao crossReportDao) {
    this.crossReportDao = crossReportDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport find(Serializable primaryKey) {
    assert (primaryKey instanceof String);

    gov.ca.cwds.rest.api.persistence.cms.CrossReport persistedCrossReport =
        crossReportDao.find(primaryKey);
    if (persistedCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(persistedCrossReport);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport delete(Serializable primaryKey) {
    assert (primaryKey instanceof String);
    gov.ca.cwds.rest.api.persistence.cms.CrossReport persistedCrossReport =
        crossReportDao.delete(primaryKey);
    if (persistedCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(persistedCrossReport);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport create(Request request) {
    assert (request instanceof gov.ca.cwds.rest.api.domain.cms.CrossReport);

    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport =
        ((gov.ca.cwds.rest.api.domain.cms.CrossReport) request);

    try {
      // TODO : refactor to actually determine who is updating. 'q1p' for now
      CrossReport managed = new CrossReport(IdGenerator.randomString(10), crossReport, "q1p");

      managed = crossReportDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("CrossReport already exists : {}", crossReport);
      throw new ServiceException(e);
    }
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.CrossReport update(Serializable primaryKey, Request request) {
    assert (request instanceof gov.ca.cwds.rest.api.domain.cms.CrossReport);
    gov.ca.cwds.rest.api.domain.cms.CrossReport crossReport =
        ((gov.ca.cwds.rest.api.domain.cms.CrossReport) request);

    try {
      CrossReport managed = new CrossReport(crossReport.getThirdId(), crossReport, "q1p");
      managed = crossReportDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.CrossReport(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("CrossReport not found : {}", crossReport);
      throw new ServiceException(e);
    }
  }

}
