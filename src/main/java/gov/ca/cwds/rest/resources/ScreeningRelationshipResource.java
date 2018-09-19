package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.DS_NS;
import static gov.ca.cwds.rest.core.Api.SCREENING_RELATIONSHIPS;

import java.util.Arrays;

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

import gov.ca.cwds.inject.ScreeningRelationshipServiceBackedResource;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationshipBase;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.relationship.RelationshipFacade;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ScreeningRelationshipResource}. It delegates
 * functions to {@link ServiceBackedResourceDelegate}. It decorates the
 * {@link ServiceBackedResourceDelegate} not in functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 *
 * @author CWDS API Team
 */
@Api(value = SCREENING_RELATIONSHIPS, tags = {SCREENING_RELATIONSHIPS})
@Path(value = SCREENING_RELATIONSHIPS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreeningRelationshipResource {

  private ResourceDelegate resourceDelegate;
  private RelationshipFacade relationshipFacade;

  /**
   * Constructor
   *
   * @param resourceDelegate The resourceDelegate to delegate to.
   * @param relationshipFacade relationship facade service
   */
  @Inject
  public ScreeningRelationshipResource(
      @ScreeningRelationshipServiceBackedResource ResourceDelegate resourceDelegate,
      RelationshipFacade relationshipFacade) {
    this.resourceDelegate = resourceDelegate;
    this.relationshipFacade = relationshipFacade;
  }

  /**
   * Create an {@link ScreeningRelationshipBase}.
   *
   * @param screeningRelationship The {@link ScreeningRelationshipBase}
   * @return The {@link Response}
   */
  @UnitOfWork(DS_NS)
  @POST
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiResponses(
      value = {@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Unable to process JSON"),
          @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "Not Authorized"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Accept Header not supported"),
          @ApiResponse(code = HttpStatus.SC_UNPROCESSABLE_ENTITY,
              message = "Unable to validate ScreeningRelationship"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not found")})
  @ApiOperation(value = "Create Screening Relationship", code = HttpStatus.SC_CREATED,
      response = ScreeningRelationship.class)
  public Response create(
      @Valid @ApiParam(required = true) ScreeningRelationshipBase screeningRelationship) {
    return resourceDelegate.create(screeningRelationship);
  }

  /**
   * Update an {@link ScreeningRelationship}.
   *
   * @param screeningRelationship The {@link ScreeningRelationship}
   * @param id - id
   * @return The {@link Response}
   */
  @UnitOfWork(DS_NS)
  @PUT
  @Path("/{id}")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiResponses(
      value = {@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Unable to process JSON"),
          @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "Not Authorized"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Accept Header not supported"),
          @ApiResponse(code = HttpStatus.SC_UNPROCESSABLE_ENTITY,
              message = "Unable to validate ScreeningRelationship"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not found")})
  @ApiOperation(value = "Update Screening Relationship", code = HttpStatus.SC_CREATED,
      response = ScreeningRelationship.class)
  public Response update(
      @Valid @ApiParam(required = true) ScreeningRelationship screeningRelationship,
      @PathParam("id") @ApiParam(required = true,
          value = "The id of the Relationship to find") String id) {
    return new ResponseConverter()
        .withUpdatedResponse(relationshipFacade.updateRelationship(id, screeningRelationship));
  }

  /**
   * Get an {@link ScreeningRelationship}.
   *
   * @param id - id
   * @return The {@link Response}
   */
  @UnitOfWork(DS_NS)
  @GET
  @Path("/{id}")
  @ApiResponses(
      value = {@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Unable to process JSON"),
          @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "Not Authorized"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Accept Header not supported"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Relationship not found")})
  @ApiOperation(value = "Find Relationship by id", response = ScreeningRelationship.class)
  public Response get(@PathParam("id") @ApiParam(required = true,
      value = "The id of the Relationship to find") String id) {
    return resourceDelegate.get(id);
  }

  /**
   * Create an {@link ScreeningRelationship}.
   *
   * @param screeningRelationships The {@link ScreeningRelationshipBase}
   * @return The {@link Response}
   */
  @UnitOfWork(DS_NS)
  @POST
  @Path("/batch")
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiResponses(
      value = {@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Unable to process JSON"),
          @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "Not Authorized"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Accept Header not supported"),
          @ApiResponse(code = HttpStatus.SC_UNPROCESSABLE_ENTITY,
              message = "Unable to validate ScreeningRelationship"),
          @ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not found")})
  @ApiOperation(value = "Create Screening Relationships", code = HttpStatus.SC_CREATED,
      response = ScreeningRelationshipBase[].class)
  public Response batchCreate(
      @Valid @ApiParam(required = true) ScreeningRelationshipBase[] screeningRelationships) {
    return new ResponseConverter().withCreatedResponse(
        relationshipFacade.createRelationships(Arrays.asList(screeningRelationships)));
  }

}
