package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.DATASOURCE_NS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_SCREENINGS;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipsWithCandidates;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import java.io.IOException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.ScreeningServiceBackedResource;
import gov.ca.cwds.rest.api.domain.Screening;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.services.relationship.RelationshipFacade;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link Screening}. It delegates functions to {@link
 * ServiceBackedResourceDelegate}. It decorates the {@link ServiceBackedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_SCREENINGS, tags = {RESOURCE_SCREENINGS})
@Path(value = RESOURCE_SCREENINGS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreeningResource {

  private ResourceDelegate resourceDelegate;
  private RelationshipFacade relationshipFacade;

  /**
   * Constructor
   *
   * @param resourceDelegate the resourceDelegate to delegate to
   * @param relationshipFacade the relationshipFacade to delegate to
   */
  @Inject
  public ScreeningResource(@ScreeningServiceBackedResource ResourceDelegate resourceDelegate,
      RelationshipFacade relationshipFacade) {
    this.resourceDelegate = resourceDelegate;
    this.relationshipFacade = relationshipFacade;
  }

  /**
   * Create a {@link Screening}.
   *
   * @param screening - screening
   * @return The {@link Response}
   */
  @UnitOfWork(DATASOURCE_NS)
  @POST
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate Screening")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Creates a new screening", code = HttpStatus.SC_CREATED,
      response = Screening.class)
  public Response create(@Valid @ApiParam(hidden = false, required = true,
      value = "The screening request") Screening screening) {
    return resourceDelegate.create(screening);
  }

  /**
   * Update a {@link Screening}.
   *
   * @param id The id
   * @param screening the screening
   * @return The {@link Response}
   */
  @UnitOfWork(DATASOURCE_NS)
  @PUT
  @Path("/{id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 422, message = "Unable to validate Screening")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update Screening", code = HttpStatus.SC_OK, response = Screening.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true,
          value = "The id of the Screening to update") String id,
      @Valid @ApiParam(required = true, hidden = false,
          value = "The screening request") gov.ca.cwds.rest.api.domain.Screening screening) {
    return resourceDelegate.update(id, screening);
  }

  /**
   * Get an {@link ScreeningRelationship}.
   *
   * @param screeningId The id
   * @return The {@link Response}
   */
  @XAUnitOfWork
  @GET
  @Path("/{screeningId}/relationships")
  @ApiResponses(
      value = {@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Unable to process JSON"),
          @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "Not Authorized"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Accept Header not supported"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Relationship not found")})
  @ApiOperation(value = "Find Relationships by screening id",
      response = ScreeningRelationship.class)
  public Response getRelationshipsByScreeningId(@PathParam("screeningId") @ApiParam(required = true,
      value = "The id of the Screening to find") String screeningId) {
    return new ResponseConverter()
        .withDataResponse(relationshipFacade.getRelationshipsByScreeningId(screeningId));
  }

  /**
   * Get an {@link ScreeningRelationship}.
   *
   * @param screeningId The id
   * @return The {@link Response}
   */
  @UnitOfWork(DATASOURCE_NS)
  @GET
  @Path("/{screeningId}/relationships_with_candidates")
  @ApiResponses(
      value = {@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Unable to process JSON"),
          @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "Not Authorized"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Accept Header not supported"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Relationship not found")})
  @ApiOperation(value = "Find Relationships by screening id with candidates",
      response = ScreeningRelationshipsWithCandidates.class)
  public Response getRelationshipsWithCandidatesByScreeningId(
      @PathParam("screeningId") @ApiParam(required = true,
          value = "The id of the Screening to find") String screeningId) {
    return new ResponseConverter().withDataResponse(
        relationshipFacade.getRelationshipsWithCandidatesByScreeningId(screeningId));
  }

}
