package gov.ca.cwds.rest.api.domain.cms;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.fixture.PersonEntityBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.rest.api.domain.PostedPerson;

public class PostedCmsNSReferralTest {
  private String id = "1234567ABC";
  private gov.ca.cwds.data.persistence.cms.Referral referral;
  private gov.ca.cwds.data.persistence.cms.Referral diffReferral;
  private PostedReferral postedReferral;
  private PostedReferral diffPostedReferral;
  private gov.ca.cwds.data.persistence.ns.Person person;
  private gov.ca.cwds.data.persistence.ns.Person diffPerson;
  private PostedPerson postedPerson;
  private PostedPerson diffPostedPerson;
  
  @Before
  public void setup() {
	referral = new ReferralEntityBuilder().setId(id).build();
	diffReferral = new ReferralEntityBuilder().setId("2345678ABC").build();
	postedReferral = new PostedReferral(referral);
	diffPostedReferral = new PostedReferral(diffReferral);
	person = new PersonEntityBuilder().build();
	diffPerson = new PersonEntityBuilder().setFirstName("testFirstName").build();
	postedPerson = new PostedPerson(diffPerson);
  }
  
  @Test
  public void shouldConstructObject() throws Exception {
	PostedCmsNSReferral postedCmsNSReferral = new PostedCmsNSReferral(postedReferral, postedPerson);
	assertThat(postedCmsNSReferral.getReferral(), is(equalTo(postedReferral)));
	assertThat(postedCmsNSReferral.getPerson(), is(equalTo(postedPerson)));	
  }
  
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
	PostedCmsNSReferral postedCmsNSReferral = new PostedCmsNSReferral(postedReferral, postedPerson);	  
    assertTrue(postedCmsNSReferral.equals(postedCmsNSReferral));
  }
  
  @Test
  public void equalsShouldBeFalseWhenDifferentObjects() throws Exception {
    
    PostedCmsNSReferral postedCmsNSReferral = new PostedCmsNSReferral(postedReferral, postedPerson); 
    PostedCmsNSReferral diffPostedCmsNSReferral = new PostedCmsNSReferral(diffPostedReferral, postedPerson);
    assertFalse(postedCmsNSReferral.equals(diffPostedCmsNSReferral));
    
  }
  
  @Test
  public void equalsShouldBeFalseWhenNullObject() throws Exception {
    PostedCmsNSReferral postedCmsNSReferral = new PostedCmsNSReferral(postedReferral, postedPerson); 
    assertFalse(postedCmsNSReferral.equals(null));
  }
  
  @Test
  public void equalsShouldBeFalseWhenDifferentPerson() throws Exception {
    PostedCmsNSReferral postedCmsNSReferral = new PostedCmsNSReferral(postedReferral, postedPerson); 
    PostedCmsNSReferral diffPostedCmsNSReferral = new PostedCmsNSReferral(postedReferral, diffPostedPerson);
    assertFalse(postedCmsNSReferral.equals(diffPostedCmsNSReferral));
    
  }
  
}
