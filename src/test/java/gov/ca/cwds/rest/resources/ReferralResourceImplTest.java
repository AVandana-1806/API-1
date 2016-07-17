package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.ApiResponse;
import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.services.ReferralService;
import gov.ca.cwds.rest.setup.ServiceEnvironment;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;


public class ReferralResourceImplTest {
	private static final String ID_NOT_FOUND = "-1";
	private static final String ID_FOUND = "1";
	
	private static final String FOUND_RESOURCE = "/referral/" + ID_FOUND + "/summary";
	private static final String NOT_FOUND_RESOURCE = "/referral/" + ID_NOT_FOUND + "/summary";
	
	private static final ReferralService referralService = mock(ReferralService.class);
	private static final ServiceEnvironment serviceEnvironment = mock(ServiceEnvironment.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new ReferralResourceImpl(serviceEnvironment)).build();

	@Before
	public void setup() {
		when(referralService.findReferralSummary(ID_NOT_FOUND)).thenReturn(null);
		when(referralService.findReferralSummary(ID_FOUND)).thenReturn(createReferralSummary());
		when(serviceEnvironment.getService(ReferralService.class, Api.Version.JSON_VERSION_1)).thenReturn(referralService);
	}

	@Test
	public void referralSummaryGetReturns200WhenFound() {
		assertThat(resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().getStatus(), is(equalTo(200)));
	}

	@Test
	public void referralSummaryGetReturns404WhenNotFound() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().getStatus(), is(equalTo(404)));
	}
	
	@Test
	public void referralSummaryGetReturns464WhenVersionNotSupport() {
		assertThat(resources.client().target(NOT_FOUND_RESOURCE).request().accept("UNSUPPORTED_VERSION").get().getStatus(), is(equalTo(406)));
	}

	@Test
	public void referralSummaryGetReturnsCorrectStatusWhenNotFound() {
		ApiResponse apiResponse = resources.client().target(NOT_FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().readEntity(ApiResponse.class);
		assertThat(apiResponse.getSuccess(), is(equalTo("false")));
	}
	@Test
	public void referralSummaryGetReturnsCorrectStatusWhenFound() {
		ApiResponse apiResponse = resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().readEntity(ApiResponse.class);
		assertThat(apiResponse.getSuccess(), is(equalTo("true")));
	}
	
	@Test
	public void referralSummaryGeHasNullDataWhenNotFound() {
		ApiResponse apiResponse = resources.client().target(NOT_FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().readEntity(ApiResponse.class);
		assertThat(apiResponse.getData(), is(equalTo(null)));
	}

	@Test
	public void referralSummaryGeHasHasReferralSummaryWhenFound() {
		ApiResponse apiResponse = resources.client().target(FOUND_RESOURCE).request().accept(Api.Version.JSON_VERSION_1.getMediaType()).get().readEntity(ApiResponse.class);
		Object referral = apiResponse.getData().get("referralSummary");
		assertThat(referral, is(notNullValue()));
	}
	
	private ReferralSummary createReferralSummary() {
		return new ReferralSummary(ID_FOUND, "some name", new Date());
	}

}
