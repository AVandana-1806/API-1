package gov.ca.cwds.rest.resources.relationship;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.resources.ResourceDelegate;
import gov.ca.cwds.rest.resources.ScreeningRelationshipResource;
import gov.ca.cwds.rest.services.relationship.RelationshipFacade;
import org.junit.Before;
import org.junit.Test;

public class ScreeningRelationshipResourceTest {

  ScreeningRelationshipResource relationsshipResource;
  ResourceDelegate resourceDelegate;
  ScreeningRelationship relationship;
  RelationshipFacade facade;

  @Before
  public void setup() {
    resourceDelegate = mock(ResourceDelegate.class);
    relationsshipResource = new ScreeningRelationshipResource(resourceDelegate, facade);
  }

  @Test
  public void whenCreateIsInvoledThenWeShouldCallService() {
    relationsshipResource.create(relationship);
    verify(resourceDelegate).create(relationship);
  }

  @Test
  public void whenFindIsInvolvedThenWeShouldCallService() {
    relationsshipResource.get("1");
    verify(resourceDelegate).get("1");
  }
}