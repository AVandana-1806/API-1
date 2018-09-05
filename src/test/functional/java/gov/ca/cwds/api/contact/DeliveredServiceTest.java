package gov.ca.cwds.api.contact;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.api.builder.HttpRequestHandler;
import gov.ca.cwds.fixture.contacts.DeliveredServiceResourceBuilder;
import gov.ca.cwds.api.FunctionalTest;
import gov.ca.cwds.rest.authenticate.UserGroup;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;

public class DeliveredServiceTest extends FunctionalTest {
  String resourcePath;
  private HttpRequestHandler httpRequestHandler;

  @Before
  public void setup() {
    // logged in staff with Sealed access and
    // USERID->STAFF_PERSON->CWS_OFFICE.Government_Entity_type=1126 (California)
    resourcePath = getResourceUrlFor("/" + Api.RESOURCE_DELIVERY_SERVICE);
    httpRequestHandler = new HttpRequestHandler();
    this.loginUserGroup(UserGroup.STATE_SEALED);
  }
  
  @Test
  public void shouldReturn422WhenInvalidDeliveredService() {
    // CMS DELIVERED_SERVCE/CONTACT_VIST_CODE - is required and cannot be empty
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder()
        .setCftLeadAgencyType(null)
        .setCommunicationMethodType(408)
        .setContactLocationType(415)
        .setContactVisitCode(null)
        .setCountySpecificCode("20")
        .setDetailText("a contact detail")
        .setDetailTextContinuation(null)
        .setEndDate("2018-09-05")
        .setEndTime(null)
        .setPrimaryDeliveredServiceId(null)
        .setHardCopyDocumentOnFileCode("N")
        .setOtherParticipantsDesc("Attorney")
        .setProvidedByCode("S")
        .setProvidedById("aab")
        .setServiceContactType(435)
        .setStartDate("2018-09-05")
        .setStartTime(null)
        .setStatusCode("A")
        .setSupervisionCode(null)
        .setWraparoundServiceIndicator(false)
        .buildDeliveredServiceResource();
    
//    Response response = httpRequestHandler.postRequest(deliveredServiceDomain, resourcePath, token);
//    ResponseBody body = response.getBody();
//    String bodyString = body.asString();
//    System.out.println(bodyString);
    
    httpRequestHandler.postRequest(deliveredServiceDomain, resourcePath, token)
      .then()
      .statusCode(422);

  }
  
  @Test
  public void shouldReturn422WhenStartTimeIsEmpty() {
    // CMS DELIVERED_SERVICE/START_DATE is defined as optional and nullable
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder()
        .setCftLeadAgencyType(null)
        .setCommunicationMethodType(408)
        .setContactLocationType(415)
        .setContactVisitCode("C")
        .setCountySpecificCode("20")
        .setDetailText("a contact detail")
        .setDetailTextContinuation(null)
        .setEndDate("2018-09-05")
        .setEndTime(null)
        .setPrimaryDeliveredServiceId(null)
        .setHardCopyDocumentOnFileCode("N")
        .setOtherParticipantsDesc("Attorney")
        .setProvidedByCode("S")
        .setProvidedById("aab")
        .setServiceContactType(435)
        .setStartDate("2018-09-05")
        .setStartTime(null)
        .setStatusCode("A")
        .setSupervisionCode(null)
        .setWraparoundServiceIndicator(false)
        .buildDeliveredServiceResource();

    httpRequestHandler.postRequest(deliveredServiceDomain, resourcePath, token)
      .then()
      .assertThat()
      .body("issue_details.property", Matchers.hasItem("startTime"))
      .and()
      .statusCode(422);
    
  }
  
  @Test
  public void shouldReturn422WhenProvidedByIdIsStaffId() {
    // CMS DELIVERED_SERVICE/PROVIDED_BY_ID is optional and nullable.  Can contain a
    // staff ID (3 characters)
    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder()
        .setCftLeadAgencyType(null)
        .setCommunicationMethodType(408)
        .setContactLocationType(415)
        .setContactVisitCode("C")
        .setCountySpecificCode("20")
        .setDetailText("a contact detail")
        .setDetailTextContinuation(null)
        .setEndDate("2018-09-05")
        .setEndTime(null)
        .setPrimaryDeliveredServiceId(null)
        .setHardCopyDocumentOnFileCode("N")
        .setOtherParticipantsDesc("Attorney")
        .setProvidedByCode("S")
        .setProvidedById("aab")
        .setServiceContactType(435)
        .setStartDate("2018-09-05")
        .setStartTime(null)
        .setStatusCode("A")
        .setSupervisionCode(null)
        .setWraparoundServiceIndicator(false)
        .buildDeliveredServiceResource();

    httpRequestHandler.postRequest(deliveredServiceDomain, resourcePath, token)
      .then()
      .assertThat()
      .body("issue_details.property", Matchers.hasItem("providedById"))
      .and()
      .statusCode(422);
   
  }
}
