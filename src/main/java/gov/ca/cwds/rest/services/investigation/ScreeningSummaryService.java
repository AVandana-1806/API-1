package gov.ca.cwds.rest.services.investigation;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.internal.ManagedSessionContext;

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
   * <p>
   * COMMENT: if you need to open a Postgres session, then either use {@code UnitOfWork} or
   * {@code XAUnitOfWork}.
   * </p>
   * 
   * @param referralId - referral id
   * @return - Screening Summary object.
   */
  private ScreeningSummary findScreeningSummaryByReferralId(String referralId) {

    final org.hibernate.Session session = screeningDao.grabSession();
    ManagedSessionContext.bind(session);

    final ScreeningEntity[] screeningEntities = screeningDao.findScreeningsByReferralId(referralId);
    final ScreeningEntity screeningEntity =
        screeningEntities.length > 0 ? screeningEntities[0] : null;

    return screeningEntity != null
        ? new ScreeningSummary(screeningEntity, this.populateSimpleAllegations(screeningEntity))
        : new ScreeningSummary();
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
