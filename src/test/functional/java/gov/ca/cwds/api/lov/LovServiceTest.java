package gov.ca.cwds.api.lov;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.api.FunctionalTest;
import static org.hamcrest.Matchers.equalTo;

public class LovServiceTest extends FunctionalTest {
  String lovPath;

  @Before
  public void setup() {
    lovPath = getResourceUrlFor("/lov");
  }

  @Test
  public void endPointIsResponding(){
    given().queryParam("token", token)
    .when().get(lovPath )
    .then().statusCode(200);
  }

  @Test
  public void whenSubmittingAGetRequestThenWeShouldReceiveListOfLovs(){
    given().queryParam("token", token)
    .when().get(lovPath )
        //
        .then().and().body( "findAll {it.category == 'agency_type'}.size()", equalTo(3))
//uncomment after bug is fixed
//      .and().body( "findAll {it.category == 'address_type'}.size()", equalTo(9))
        .and().body( "findAll {it.category == 'allegation_type'}.size()", equalTo(8))
        .and().body( "findAll {it.category == 'approval_status'}.size()", equalTo(1))
        .and().body( "findAll {it.category == 'communication_method'}.size()", equalTo(5))
        .and().body( "findAll {it.category == 'contact_location'}.size()", equalTo(6))
        .and().body( "findAll {it.category == 'contact_purpose'}.size()", equalTo(6))
//uncomment after bug is fixed
//        .and().body( "findAll {it.category == 'contact_status'}.size()", equalTo(3))
        .and().body( "findAll {it.category == 'county_type'}.size()", equalTo(59))
        .and().body( "findAll {it.category == 'cross_report_comm_method'}.size()", equalTo(4))
        .and().body( "findAll {it.category == 'ethnicity_type'}.size()", equalTo(10))
//uncomment after bug is fixed
//        .and().body( "findAll {it.category == 'hispanic_origin_code'}.size()", equalTo(5))
        .and().body( "findAll {it.category == 'language'}.size()", equalTo(33))
        .and().body( "findAll {it.category == 'race_type'}.size()", equalTo(24))
        .and().body( "findAll {it.category == 'relationship_type'}.size()", equalTo(128))
        .and().body( "findAll {it.category == 'safety_alert'}.size()", equalTo(9))
        .and().body( "findAll {it.category == 'screen_response_time'}.size()", equalTo(6))
        .and().body( "findAll {it.category == 'unable_to_determine_code'}.size()", equalTo(1))
        .and().body( "findAll {it.category == 'us_state'}.size()", equalTo(58))
        .and().statusCode(200);
  }
}
