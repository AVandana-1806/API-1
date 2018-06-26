package gov.ca.cwds.rest.services.cms;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.auth.AuthorizationRequest;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CmsNSHelperTest extends Doofenshmirtz<Client> {

  private class TestResponse implements Response {

  }

  private class TestCrudsService implements CrudsService {

    @Override
    public Response create(Request arg0) {
      return new TestResponse();
    }

    @Override
    public Response delete(Serializable arg0) {
      return new TestResponse();
    }

    @Override
    public Response find(Serializable arg0) {
      return new TestResponse();
    }

    @Override
    public Response update(Serializable arg0, Request arg1) {
      return new TestResponse();
    }

  }

  Map<CrudsService, Request> cmsRequests;
  Map<CrudsService, Request> nsRequests;

  SessionFactory cmsSessionFactory;
  SessionFactory nsSessionFactory;
  Session cmsSession;
  Session nsSession;

  CmsNSHelper helper;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    cmsRequests = new HashMap<>();
    cmsRequests.put(new TestCrudsService(), new AuthorizationRequest(DEFAULT_CLIENT_ID));

    nsRequests = new HashMap<>();
    nsRequests.put(new TestCrudsService(), new AuthorizationRequest(DEFAULT_CLIENT_ID));

    cmsSession = session;
    cmsSessionFactory = sessionFactory;
    when(cmsSessionFactory.openSession()).thenReturn(cmsSession);

    nsSession = session;
    nsSessionFactory = sessionFactory;
    when(nsSessionFactory.openSession()).thenReturn(nsSession);

    helper = new CmsNSHelper(cmsSessionFactory, nsSessionFactory);
  }

  @Test
  public void shouldCloseHibernateCmsSessionResource() {
    helper.handleResponse(cmsRequests, nsRequests);
    verify(cmsSession, atLeastOnce()).close();
  }

  @Test
  public void shouldCloseHibernateNsSessionResource() {
    helper.handleResponse(cmsRequests, nsRequests);
    verify(nsSession, atLeastOnce()).close();
  }

}
