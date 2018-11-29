package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CLIENT;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.RelationshipsService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link Resource}. It delegates functions to
 * {@link RelationshipsService}.
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CLIENT, tags = {RESOURCE_CLIENT})
@Path(value = RESOURCE_CLIENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientRelationshipResource {

  private RelationshipsService relationshipsService;

  /**
   * Constructor
   *
   * @param relationshipsService The service to delegate to
   */
  @Inject
  public ClientRelationshipResource(RelationshipsService relationshipsService) {
    this.relationshipsService = relationshipsService;
  }

  /**
   * Finds a Relationship for a Client id.
   *
   * @param id the id
   * @return client relationships
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/{id}/relationships")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find relationships by client id", response = Relationship.class)
  public Response get(@PathParam("id") @ApiParam(required = true, name = "id",
      value = "The id of the client to find relationships for") String id) {
    gov.ca.cwds.rest.api.Response relationship = relationshipsService.find(id);
    return new ResponseConverter().withDataResponse(relationship);
  }

  /**
   * Finds a Relationship for a Client id.
   *
   * @param clientIds the list of client ids to return relationships for
   * @return A list of client relationships
   */
  @UnitOfWork(value = "cms")
  @GET
  @Path("/relationships")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find relationships by client id's", response = Relationship.class)
  public Response getRelationships(
      @QueryParam("clientIds") @ApiParam(required = true, name = "clientIds",
          value = "A list of client id's to find relationships for") final List<String> clientIds) {
    gov.ca.cwds.rest.api.Response relationships = relationshipsService.findForIds(clientIds);
    return new ResponseConverter().withDataResponse(relationships);
  }
}
