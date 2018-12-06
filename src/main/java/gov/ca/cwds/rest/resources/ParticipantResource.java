package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.Datasource.CMS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;

import com.google.inject.Inject;
import gov.ca.cwds.inject.ParticipantServiceBackedResource;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.ParticipantService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A resource providing a RESTful interface for {@link ParticipantIntakeApi}. It delegates functions
 * to {@link gov.ca.cwds.rest.services.screening.participant.ParticipantService}.
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_PARTICIPANTS)
@Path(value = RESOURCE_PARTICIPANTS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParticipantResource {

  private ParticipantService participantService;

  @Inject
  public ParticipantResource(@ParticipantServiceBackedResource ParticipantService participantService){
    this.participantService = participantService;
  }

  /**
   * Finds a participant by Legacy id.
   *
   * @param legacyId the legacy id of the Participant
   * @return the response
   */
  @UnitOfWork(value = CMS, readOnly = true)
  @GET
  @Path("/{legacyId}")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find Participant by legacy id", response = ParticipantIntakeApi.class)
  public Response get(
      @PathParam("legacyId")
      @ApiParam(required = true, name = "legacyId", value = "The id of the Address to find")
      String legacyId){
    gov.ca.cwds.rest.api.Response participants = participantService.findByLegacyId(legacyId);
    return new ResponseConverter().withDataResponse(participants);
  }
}
