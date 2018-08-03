package gov.ca.cwds.rest.services.cms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.XADataSourceFactory;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * <strong>NOTE:</strong> XA transactions make this helper class obsolete.
 * 
 * @author CWDS API Team
 * @see XADataSourceFactory
 */
public class CmsNSHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsNSHelper.class);

  private SessionFactory cmsSessionFactory;

  private SessionFactory nsSessionFactory;

  public CmsNSHelper(SessionFactory sessionFactory, SessionFactory nsSessionFactory) {
    this.cmsSessionFactory = sessionFactory;
    this.nsSessionFactory = nsSessionFactory;
  }

  public Map<String, Map<CrudsService, Response>> handleResponse(
      Map<CrudsService, Request> cmsRequests, Map<CrudsService, Request> nsRequests) {
    Boolean nonXaMarker = null;
    if (!RequestExecutionContext.instance().isXaTransaction()) {
      nonXaMarker = Boolean.TRUE;
    }
    Optional<Boolean> nonXa = Optional.ofNullable(nonXaMarker);

    final boolean isXa = RequestExecutionContext.instance().isXaTransaction();
    LOGGER.info("CmsNSHelper.handleResponse: isNonXa: {}", isXa);
    final Map<CrudsService, Response> cmsResponse = new HashMap<>();
    final Map<CrudsService, Response> nsResponse = new HashMap<>();
    final Map<String, Map<CrudsService, Response>> response = new HashMap<>();

    Response referral = null;
    Response person;

    // DRS: Don't close sessions! Kills XA.
    final Session sessionCMS = cmsSessionFactory.getCurrentSession();
    final Session sessionNS = nsSessionFactory.getCurrentSession();

    try {
      nonXa.ifPresent(nxa -> ManagedSessionContext.bind(sessionCMS)); // NOSONAR);

      final Transaction transactionCMS = sessionCMS.getTransaction();

      for (Entry<CrudsService, Request> cmsRequestsService : cmsRequests.entrySet()) {
        try {
          final CrudsService service = cmsRequestsService.getKey();
          referral = service.create(cmsRequests.get(service));
          cmsResponse.put(service, referral);

          nonXa.ifPresent(nxa -> sessionCMS.flush());

        } catch (Exception e) {
          LOGGER.error("EXCEPTION CREATING CMS! {}", e.getMessage(), e);

          // NOT IN XA TRANSACTIONS!
          // Throwing an exception should suffice.
          nonXa.ifPresent(nxa -> transactionCMS.rollback());

          throw e;
        }
      }

      nonXa.ifPresent(nxa -> ManagedSessionContext.bind(sessionNS)); // NOSONAR

      final Transaction transactionNS = sessionNS.getTransaction();

      for (Entry<CrudsService, Request> nsRequestsService : nsRequests.entrySet()) {
        try {
          final CrudsService service = nsRequestsService.getKey();
          person = service.create(nsRequests.get(service));
          nsResponse.put(service, person);

          nonXa.ifPresent(nxa -> sessionNS.flush());

        } catch (Exception e) {
          LOGGER.error("EXCEPTION CREATING NS! {}", e.getMessage(), e);

          // NOT IN XA TRANSACTIONS!
          // Throwing an exception should suffice.
          nonXa.ifPresent(nxa -> {
            transactionNS.rollback();
            transactionCMS.rollback();
          });
          throw e;
        }
        try {
          // NOT IN XA TRANSACTIONS!
          nonXa.ifPresent(nxa -> {
            transactionCMS.commit();
            transactionNS.commit();
          });
        } catch (Exception e) {
          LOGGER.error("EXCEPTION ON COMMIT! {}", e.getMessage(), e);
          throw e;
        }
      }
    } finally {
      nonXa.ifPresent(nxa -> {
        ManagedSessionContext.unbind(cmsSessionFactory); // NOSONAR
        ManagedSessionContext.unbind(nsSessionFactory); // NOSONAR
      });
    }

    response.put("cms", cmsResponse);
    response.put("ns", nsResponse);

    return response;
  }

}
