package gov.ca.cwds.rest.resources.investigation;

import static gov.ca.cwds.rest.core.Api.DS_CMS;
import static gov.ca.cwds.rest.core.Api.RESOURCE_INVESTIGATIONS;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.inject.InvestigationCrossReportServiceBackedResource;
import gov.ca.cwds.rest.api.domain.investigation.CrossReport;
import gov.ca.cwds.rest.api.domain.investigation.Investigation;
import gov.ca.cwds.rest.resources.TypedResourceDelegate;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {{@link Investigation}. It delegates functions to
 * {@link TypedResourceDelegate}. It decorates the {@link TypedResourceDelegate} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_INVESTIGATIONS)
@Path(value = RESOURCE_INVESTIGATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CrossReportResource {

  private TypedResourceDelegate<String, CrossReport> typedResourceDelegate;

  /**
   * Constructor
   *
   * @param typedResourceDelegate The typedResourceDelegate to delegate to.
   */
  @Inject
  public CrossReportResource(
      @InvestigationCrossReportServiceBackedResource TypedResourceDelegate<String, CrossReport> typedResourceDelegate) {

    this.typedResourceDelegate = typedResourceDelegate;
  }

  /**
   * Create a {@link CrossReport}.
   * 
   * @param id - CMS Id of Case or Referral
   *
   * @param crossReport The {@link CrossReport}
   *
   * @return The {@link Response}
   */
  @UnitOfWork(value = DS_CMS)
  @POST
  @Path("/{id}/cross_report")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate cross report")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Create Cross Report", code = HttpStatus.SC_CREATED,
      response = CrossReport.class)
  public Response create(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The CMS id of the Referral or Case") String id,
      @Valid @ApiParam(hidden = false, required = true) CrossReport crossReport) {
    return typedResourceDelegate.create(crossReport);
  }

  /**
   * Update a {@link CrossReport}.
   *
   * @param id - CMS Id of Case or Referral
   * @param crossReportId - CMS Id of CrossReport
   * @param crossReport - cross report to update
   * @return - updated cross report
   */
  @UnitOfWork(value = DS_CMS)
  @PUT
  @Path("/{id}/cross_report/{crossreport_id}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to validate cross report")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Update a cross report", code = HttpStatus.SC_OK,
      response = CrossReport.class)
  public Response update(
      @PathParam("id") @ApiParam(required = true, name = "id",
          value = "The CMS Id of the Referral or Case ") String id,
      @PathParam("crossreport_id") @ApiParam(required = true, name = "crossreport_id",
          value = "The CMS Id of the Cross Report") String crossReportId,
      @Valid @ApiParam(hidden = false, required = true) CrossReport crossReport) {
    return typedResourceDelegate.update(crossReportId, crossReport);
  }

}
