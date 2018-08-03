package gov.ca.cwds.rest.resources.hoi;

import static gov.ca.cwds.rest.core.Api.DS_CMS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_CASE_HISTORY_OF_INVOLVEMENT;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.FlushMode;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.resources.SimpleResourceDelegate;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.hoi.HOICaseService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link HOICase}. It delegates functions to
 * {@link SimpleResourceDelegate}. It decorates the {@link SimpleResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 *
 * @author CWDS API Team
 */
@Api(value = RESOURCE_CASE_HISTORY_OF_INVOLVEMENT)
@Path(value = RESOURCE_CASE_HISTORY_OF_INVOLVEMENT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HoiCaseResource {

  private HOICaseService hoiCaseService;

  /**
   * Constructor.
   * 
   * @param hoiCaseService - hoiCaseService
   */
  @Inject
  public HoiCaseResource(HOICaseService hoiCaseService) {
    this.hoiCaseService = hoiCaseService;
  }

  /**
   * Finds an cases HOI by client ids.
   * 
   * @param clientIds - clientIds
   * @return the hoi cases
   */
  @UnitOfWork(value = DS_CMS, readOnly = true, transactional = false, flushMode = FlushMode.MANUAL)
  @GET
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 406, message = "Accept Header not supported")})
  @ApiOperation(value = "Find cases history of involvement by clientId", response = HOICase[].class)
  public Response get(@QueryParam("clientIds") @ApiParam(required = true, name = "clientIds",
      value = "List of Client Id's") final List<String> clientIds) {
    final gov.ca.cwds.rest.api.Response clients =
        hoiCaseService.handleFind(new HOIRequest(clientIds));
    return new ResponseConverter().withDataResponse(clients);
  }

}
