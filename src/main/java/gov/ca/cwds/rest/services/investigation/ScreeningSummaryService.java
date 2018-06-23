package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.persistence.ns.Allegation;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.investigation.ScreeningSummaryEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.ScreeningSummary;
import gov.ca.cwds.rest.api.domain.investigation.SimpleAllegation;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Screening Summary.
 * 
 * @author CWDS API Team
 */
public class ScreeningSummaryService
    implements TypedCrudsService<String, ScreeningSummary, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningSummaryService.class);

  private static final String DEFAULT_STUB_KEY = "999999";

  private ScreeningDao screeningDao;

  /**
   * 
   * @param screeningDao - screening dao
   */
  @Inject
  public ScreeningSummaryService(ScreeningDao screeningDao) {
    super();
    this.screeningDao = screeningDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response find(String referralId) {
    if (StringUtils.equals(referralId, DEFAULT_STUB_KEY)) {
      return new ScreeningSummaryEntityBuilder().build();

    }

    return this.findScreeningSummaryByReferralId(referralId);
  }

  /**
   * Find screening summary by referral id.
   * 
   * @param referralId - referral id
   * @return - Screening Summary object.
   */
  private ScreeningSummary findScreeningSummaryByReferralId(String referralId) {
    ScreeningSummary screeningSummary = null;
    // SessionFactory from postgres DB and need to open session in order to execute.
    SessionFactory sessionFactory = screeningDao.getSessionFactory();
    org.hibernate.Session session = sessionFactory.openSession();
    ManagedSessionContext.bind(session);

    ScreeningEntity[] screeningEntities = screeningDao.findScreeningsByReferralId(referralId);
    ScreeningEntity screeningEntity = screeningEntities.length > 0 ? screeningEntities[0] : null;
    screeningSummary = screeningEntity != null
        ? new ScreeningSummary(screeningEntity, this.populateSimpleAllegations(screeningEntity))
        : new ScreeningSummary();

    LOGGER.info("\n\n**** STOP MESSING WITH SESSIONS!!! ****\n");
    // session.close();
    return screeningSummary;
  }

  /**
   * populating list of Simple Allegation
   * 
   * @param screeningEntity - ScreeningEntity object
   * @return - list of simple allegations.
   */
  private Set<SimpleAllegation> populateSimpleAllegations(ScreeningEntity screeningEntity) {
    Set<SimpleAllegation> allegations = new HashSet<>();
    if (screeningEntity != null && screeningEntity.getAllegations() != null) {
      for (Allegation allegation : screeningEntity.getAllegations()) {
        allegations.add(new SimpleAllegation(allegation));
      }
    }

    return allegations;
  }

  @Override
  public Response create(ScreeningSummary request) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public Response delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public Response update(String primaryKey, ScreeningSummary request) {
    throw new NotImplementedException("update not implemented");
  }

}
