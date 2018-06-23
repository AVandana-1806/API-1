package gov.ca.cwds.rest.resources.contact;

import com.google.inject.Inject;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.PostedContactIntake;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactIntake;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.ContactIntakeApiService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.*;
import org.apache.http.HttpStatus;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_CONTACTS;

@Api(value = RESOURCE_INTAKE_CONTACTS, tags = {RESOURCE_INTAKE_CONTACTS})
@Path(value = RESOURCE_INTAKE_CONTACTS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactIntakeResource {

  private ContactIntakeApiService contactIntakeApiService;

  /**
   * Constructor
   *
   * @param contactIntakeApiService The service.
   */
  @Inject
  public ContactIntakeResource(ContactIntakeApiService contactIntakeApiService) {
    this.contactIntakeApiService = contactIntakeApiService;
  }

  /**
   * Create a {@link Contact}.
   *
   * @param request - screening
   * @return The {@link Response}
   */
  @UnitOfWork(value = "ns")
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
          @ApiResponse(code = 401, message = "Not Authorized"),
          @ApiResponse(code = 406, message = "Accept Header not supported"),
          @ApiResponse(code = 409, message = "Conflict - already exists"),
          @ApiResponse(code = 422, message = "Unable to validate Screening")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Creates a new contact", code = HttpStatus.SC_CREATED,
          response = PostedContactIntake.class)
  public Response create(@Valid @ApiParam(hidden = false, required = true,
          value = "The contact request") ContactIntake request) {
    return new ResponseConverter().withCreatedResponse(contactIntakeApiService.create(request));
  }
}
