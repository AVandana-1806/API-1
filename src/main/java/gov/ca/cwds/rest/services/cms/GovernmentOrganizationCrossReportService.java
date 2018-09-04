package gov.ca.cwds.rest.services.cms;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.GovernmentOrganizationCrossReportDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.GovernmentAgency;
import gov.ca.cwds.rest.api.domain.cms.AgencyType;
import gov.ca.cwds.rest.api.domain.cms.CrossReport;
import gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIGovernmentOrganizationCrossReport;

/**
 * Business layer object to work on
 * {@link gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport}
 * 
 * @author CWDS API Team
 */
public class GovernmentOrganizationCrossReportService implements
    TypedCrudsService<String, GovernmentOrganizationCrossReport, GovernmentOrganizationCrossReport> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(GovernmentOrganizationCrossReportService.class);

  private static final Map<String, String> agencyCodeMap;

  static {
    final Map<String, String> map = new HashMap<>();
    map.put("DISTRICT_ATTORNEY", "A");
    map.put("COUNTY_LICENSING", "C");
    map.put("DEPARTMENT_OF_JUSTICE", "D");
    map.put("COMMUNITY_CARE_LICENSING", "L");
    map.put("PROBATION", "P");

    agencyCodeMap = Collections.unmodifiableMap(map);
  }

  private GovernmentOrganizationCrossReportDao governmentOrganizationCrossReportDao;

  // Used to implicitly check for referential Integrity. Better to find way to make explicit
  private RIGovernmentOrganizationCrossReport riGovernmentOrganizationCrossReport;

  /**
   * @param governmentOrganizationCrossReportDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport} objects.
   * @param riGovernmentOrganizationCrossReport - riGovernmentOrganizationCrossReport
   */
  @Inject
  public GovernmentOrganizationCrossReportService(
      GovernmentOrganizationCrossReportDao governmentOrganizationCrossReportDao,
      RIGovernmentOrganizationCrossReport riGovernmentOrganizationCrossReport) {
    this.governmentOrganizationCrossReportDao = governmentOrganizationCrossReportDao;
    this.riGovernmentOrganizationCrossReport = riGovernmentOrganizationCrossReport;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public GovernmentOrganizationCrossReport create(GovernmentOrganizationCrossReport request) {
    final GovernmentOrganizationCrossReport governmentOrganizationCrossReport = request;

    try {
      gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport managed =
          new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(
              CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
              governmentOrganizationCrossReport, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = governmentOrganizationCrossReportDao.create(managed);
      return new gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("governmentOrganizationCrossReport already exists : {}",
          governmentOrganizationCrossReport);
      throw new ServiceException(e);
    }
  }

  /**
   * construct the domain
   * 
   * @param postedCrossReport - postedCrossReport
   * @param agencies agencies
   */
  public void createDomainConstructor(CrossReport postedCrossReport,
      Set<GovernmentAgency> agencies) {
    for (GovernmentAgency agency : agencies) {
      if (AgencyType.LAW_ENFORCEMENT.name().equals(agency.getType())) {
        continue;
      }
      final GovernmentOrganizationCrossReport governmentOrganizationCrossReport =
          new GovernmentOrganizationCrossReport(null, postedCrossReport.getCountySpecificCode(),
              postedCrossReport.getThirdId(), postedCrossReport.getReferralId(), agency.getId(),
              agencyCodeMap.get(agency.getType()));
      create(governmentOrganizationCrossReport);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public GovernmentOrganizationCrossReport delete(String primaryKey) {
    final gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport persistedGovernmentOrganizationCrossReport =
        governmentOrganizationCrossReportDao.delete(primaryKey);
    if (persistedGovernmentOrganizationCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport(
          persistedGovernmentOrganizationCrossReport);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public GovernmentOrganizationCrossReport find(String primaryKey) {
    final gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport persistedGovernmentOrganizationCrossReport =
        governmentOrganizationCrossReportDao.find(primaryKey);
    if (persistedGovernmentOrganizationCrossReport != null) {
      return new gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport(
          persistedGovernmentOrganizationCrossReport);
    }

    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public GovernmentOrganizationCrossReport update(String primaryKey,
      GovernmentOrganizationCrossReport request) {
    final GovernmentOrganizationCrossReport governmentOrganizationCrossReport = request;

    try {
      gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport managed =
          new gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport(primaryKey,
              governmentOrganizationCrossReport, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = governmentOrganizationCrossReportDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.GovernmentOrganizationCrossReport(managed);
    } catch (EntityNotFoundException e) {
      LOGGER.info("governmentOrganizationCrossReport not found : {}",
          governmentOrganizationCrossReport);
      throw new ServiceException(e);
    }
  }

  /**
   * @return the riGovernmentOrganizationCrossReport
   */
  public RIGovernmentOrganizationCrossReport getRiGovernmentOrganizationCrossReport() {
    return riGovernmentOrganizationCrossReport;
  }

}
