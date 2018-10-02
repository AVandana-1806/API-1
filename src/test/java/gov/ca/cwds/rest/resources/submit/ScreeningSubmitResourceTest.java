package gov.ca.cwds.rest.resources.submit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.Id;
import gov.ca.cwds.rest.resources.ResourceDelegate;

public class ScreeningSubmitResourceTest {

  private ScreeningSubmitResource resource;
  private ResourceDelegate service;

  @Before
  public void setup() {
    service = mock(ResourceDelegate.class);
    resource = new ScreeningSubmitResource(service);
  }

  @Test
  public void callScreeningToReferralService() throws Exception {
    resource.create("id");
    verify(service).create(new Id("id"));
  }
}
