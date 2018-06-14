package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.Datasource.NS;
import static gov.ca.cwds.rest.core.Api.PathParam.PARTICIPANT_ID;
import static gov.ca.cwds.rest.core.Api.PathParam.SCREENING_ID;
import static gov.ca.cwds.rest.core.Api.RESOURCE_PARTICIPANTS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;

import com.google.inject.Inject;
import gov.ca.cwds.inject.ParticipantServiceBackedResource;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.core.Api.ResponseMessage;
import gov.ca.cwds.rest.resources.parameter.ScreeningParticipantResourceParameters;
import gov.ca.cwds.rest.services.ParticipantIntakeApiService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;

/**
 * A resource providing a RESTful interface for {@link ParticipantIntakeApi}. It delegates functions
 * to {@link ParticipantIntakeApiService}.
 *
 * @author Intake Team 4
 */
@Api(value = RESOURCE_SCREENINGS + "/{" + SCREENING_ID + "}/" + RESOURCE_PARTICIPANTS, tags = RESOURCE_SCREENINGS)
@Path(value = RESOURCE_SCREENINGS + "/{" + SCREENING_ID + "}/" + RESOURCE_PARTICIPANTS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParticipantIntakeApiResource {

  @Inject
  @ParticipantServiceBackedResource
  private TypedResourceDelegate<ScreeningParticipantResourceParameters, ParticipantIntakeApi> resourceDelegate;

  /**
   * Constructor
   *
   */
  public ParticipantIntakeApiResource() {
    //no ops
  }

  /**
   * Finds a participant by id.
   *
   * @param screeningId the id of the Screening
   * @param participantId the id of the Participant
   * @return the response
   */
  @UnitOfWork(value = NS, readOnly = true)
  @GET
  @Path("/{" + PARTICIPANT_ID + "}")
  @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = ResponseMessage.UNAUTHORIZED),
      @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = ResponseMessage.NOT_FOUND),
      @ApiResponse(code = HttpStatus.SC_NOT_ACCEPTABLE, message = ResponseMessage.NOT_ACCEPTABLE)})
  @ApiOperation(value = "Find Participant", code = HttpStatus.SC_OK,
      response = ParticipantIntakeApi.class)
  public Response get(
      @PathParam(SCREENING_ID) @ApiParam(required = true, name = SCREENING_ID, value = "The id of the Screening") String screeningId,
      @PathParam(PARTICIPANT_ID) @ApiParam(required = true, name = PARTICIPANT_ID, value = "The id of the Participant") String participantId) {
    return resourceDelegate.get(new ScreeningParticipantResourceParameters(screeningId, participantId));
  }

  /**
   * Delete a participant
   *
   * @param screeningId the id of the Screening
   * @param participantId the id of the Participant
   * @return {@link ParticipantIntakeApi}
   */
  @UnitOfWork(value = NS)
  @DELETE
  @Path("/{" + PARTICIPANT_ID + "}")
  @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = ResponseMessage.UNAUTHORIZED),
      @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = ResponseMessage.NOT_FOUND)})
  @ApiOperation(value = "Delete Participant", code = HttpStatus.SC_OK, response = Object.class)
  public Response delete(
      @PathParam(SCREENING_ID) @ApiParam(required = true, name = SCREENING_ID, value = "The id of the Screening") String screeningId,
      @PathParam(PARTICIPANT_ID) @ApiParam(required = true, name = PARTICIPANT_ID, value = "The id of the Participant") String participantId) {
    return resourceDelegate.delete(new ScreeningParticipantResourceParameters(screeningId, participantId));
  }

  /**
   * Create an {@link ParticipantIntakeApi}
   *
   * @param screeningId the id of the Screening
   * @param participant The {@link ParticipantIntakeApi}
   * @return The {@link ParticipantIntakeApi}
   */
  @UnitOfWork(value = NS)
  @POST
  @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = ResponseMessage.BAD_REQUEST),
      @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = ResponseMessage.UNAUTHORIZED),
      @ApiResponse(code = HttpStatus.SC_NOT_ACCEPTABLE, message = ResponseMessage.NOT_ACCEPTABLE),
      @ApiResponse(code = HttpStatus.SC_CONFLICT, message = ResponseMessage.CONFLICT),
      @ApiResponse(code = HttpStatus.SC_UNPROCESSABLE_ENTITY, message = ResponseMessage.UNPROCESSABLE_ENTITY)})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Participant", code = HttpStatus.SC_CREATED,
      response = ParticipantIntakeApi.class)
  public Response create(
      @PathParam(SCREENING_ID) @ApiParam(required = true, name = SCREENING_ID, value = "The id of the Screening") String screeningId,
      @Valid @ApiParam(required = true, value = "Participant JSON object") ParticipantIntakeApi participant) {
        participant.setScreeningId(screeningId);
    return resourceDelegate.create(participant);
  }

  /**
   * Update an {@link ParticipantIntakeApi}
   *
   * @param screeningId the id of the Screening
   * @param participantId the id of the Participant
   * @param participant {@link ParticipantIntakeApi}
   * @return The {@link ParticipantIntakeApi}
   */
  @UnitOfWork(value = NS)
  @PUT
  @Path("/{" + PARTICIPANT_ID + "}")
  @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = ResponseMessage.BAD_REQUEST),
      @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = ResponseMessage.UNAUTHORIZED),
      @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = ResponseMessage.NOT_FOUND),
      @ApiResponse(code = HttpStatus.SC_NOT_ACCEPTABLE, message = ResponseMessage.NOT_ACCEPTABLE),
      @ApiResponse(code = HttpStatus.SC_UNPROCESSABLE_ENTITY, message = ResponseMessage.UNPROCESSABLE_ENTITY)})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update Participant", code = HttpStatus.SC_OK,
      response = ParticipantIntakeApi.class)
  public Response update(
      @PathParam(SCREENING_ID) @ApiParam(required = true, name = SCREENING_ID, value = "Screening id") String screeningId,
      @PathParam(PARTICIPANT_ID) @ApiParam(required = true, name = PARTICIPANT_ID, value = "Participant id") String participantId,
      @Valid @ApiParam(required = true, value = "Participant JSON object") ParticipantIntakeApi participant) {
    return resourceDelegate.update(new ScreeningParticipantResourceParameters(screeningId, participantId), participant);
  }

}
