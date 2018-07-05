package gov.ca.cwds.rest.core;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ApiTest {

  @Test
  public void shouldContainResourceClientServiceEndPointName() {
    assertEquals(Api.RESOURCE_CLIENT, "clients");
  }

  @Test
  public void shouldContainScreeningRelationshipServiceEndPointName() {
    assertEquals(Api.SCREENING_RELATIONSHIPS, "screening_relationships");
  }

  @Test
  public void type() throws Exception {
    assertThat(Api.class, notNullValue());
  }

}
