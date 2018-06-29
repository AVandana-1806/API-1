package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.RESOURCE_CLIENT;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.hoi.InvolvementHistory;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.hoi.InvolvementHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link InvolvementHistory}. It delegates functions
 * to {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
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
public class HoiUsingClientIdResource {

  private InvolvementHistoryService involvementHistoryService;

  /**
   * Constructor to inject the InvolvementHistoryService.
   *
   * @param involvementHistoryService - InvolvementHistoryService
   */
  @Inject
  public HoiUsingClientIdResource(InvolvementHistoryService involvementHistoryService) {
    this.involvementHistoryService = involvementHistoryService;
  }

  /**
   * Finds history of involvement by client id.
   *
   * @param clientIds the clientId
   * @return the response
   */
  @GET
  @Path("/history_of_involvements")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find history of involvement by client id's",
      response = InvolvementHistory.class)
  public javax.ws.rs.core.Response get(@QueryParam("clientIds") @ApiParam(required = true,
      name = "clientIds", value = "The id's of the clients") final List<String> clientIds) {
    gov.ca.cwds.rest.api.Response clients =
        involvementHistoryService.findInvolvementHistoryByClientIds(clientIds);
    return new ResponseConverter().withDataResponse(clients);
  }

}
