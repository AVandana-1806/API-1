package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.junit.ExpectedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.ScreeningReference;
import gov.ca.cwds.rest.api.domain.ScreeningRequest;
import io.dropwizard.testing.junit.ResourceTestRule;

/**
 * NOTE : The CWDS API Team has taken the pattern of delegating Resource functions to
 * {@link ServiceBackedResourceDelegate}. As such the tests in here reflect that assumption.
 * 
 * @author CWDS API Team
 */
public class ScreeningResourceTest {
  private static final String ROOT_RESOURCE = "/screenings/";
  private static final String FOUND_RESOURCE = "/screenings/1";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ResourceDelegate resourceDelegate = mock(ResourceDelegate.class);

  @ClassRule
  public static final ResourceTestRule inMemoryResource =
      ResourceTestRule.builder().addResource(new ScreeningResource(resourceDelegate)).build();

  /*
   * Get Tests
   */

  @Test
  public void getDelegatesToResourceDelegate() throws Exception {
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .get().getStatus();
    verify(resourceDelegate).get(1L);
  }

  /*
   * Create Tests
   */
  @Test
  public void createDelegatesToResourceDelegate() throws Exception {
    ScreeningReference screeningReference = new ScreeningReference("reference");
    inMemoryResource.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(screeningReference, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).create(eq(screeningReference));
  }

  /*
   * Delete Tests
   */
  @Test
  public void deleteReturns501() throws Exception {
    int receivedStatus = inMemoryResource.client().target(FOUND_RESOURCE).request()
        .accept(MediaType.APPLICATION_JSON).delete().getStatus();
    int expectedStatus = 501;
    assertThat(receivedStatus, is(expectedStatus));
  }

  /*
   * Update Tests
   */
  @Test
  public void udpateDelegatesToResourceDelegate() throws Exception {
    Address address = new Address("10 main st", "Sacramento", "CA", 95814);
    ImmutableList.Builder<Long> builder = ImmutableList.builder();
    ImmutableList<Long> ids = builder.add(new Long(123)).add(new Long(345)).build();
    ScreeningRequest screeningRequest = new ScreeningRequest("X5HNJK", "11/22/1973", "Amador",
        "11/22/1973", "Home", "email", "First screening", "immediate", "accept_for_investigation",
        "10/11/2016", "first narrative", address, ids);
    inMemoryResource.client().target(FOUND_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(screeningRequest, MediaType.APPLICATION_JSON));
    verify(resourceDelegate).update(eq(new Long(1)), eq(screeningRequest));
  }
}
