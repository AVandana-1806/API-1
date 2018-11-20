package gov.ca.cwds.data.es.transform;

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
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.hoi.InvolvementHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Light-weight endpoint to retrieve <strong>live</strong> client results in the exact same format
 * as Elasticsearch query results.
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CLIENT, tags = {RESOURCE_CLIENT})
@Path(value = RESOURCE_CLIENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LiveElasticClientResource {

  private InvolvementHistoryService involvementHistoryService;

  /**
   * Constructor to inject the InvolvementHistoryService.
   *
   * @param involvementHistoryService - InvolvementHistoryService
   */
  @Inject
  public LiveElasticClientResource(InvolvementHistoryService involvementHistoryService) {
    this.involvementHistoryService = involvementHistoryService;
  }

  /**
   * Finds history of involvement by client id.
   *
   * @param clientIds the clientId
   * @return the response
   */
  @GET
  @Path("/live_elastic_client")
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find 'live' Elasticsearch person documents by client id's",
      response = InvolvementHistory.class)
  public javax.ws.rs.core.Response get(@QueryParam("clientIds") @ApiParam(required = true,
      name = "clientIds", value = "The clients' id's") final List<String> clientIds) {
    gov.ca.cwds.rest.api.Response clients =
        involvementHistoryService.findInvolvementHistoryByClientIds(clientIds);
    return new ResponseConverter().withDataResponse(clients);
  }

}
