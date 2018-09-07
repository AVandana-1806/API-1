package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_LOV;
import static gov.ca.cwds.rest.core.Api.Datasource.NS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.IntakeLovEntry;
import gov.ca.cwds.rest.api.domain.IntakeLovResponse;
import gov.ca.cwds.rest.api.domain.es.ESPersons;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.caching.CacheControl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * A resource providing a RESTful interface for {@link ESPersons}. It delegates functions to
 * {@link SimpleResourceDelegate}. It decorates the {@link SimpleResourceService} not in
 * functionality but with @see
 * <a href= "https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X">Swagger
 * Annotations</a> and
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#jaxrs-resources">Jersey
 * Annotations</a>
 * 
 * @author CWDS API Team
 */
@Api(value = RESOURCE_INTAKE_LOV, tags = {RESOURCE_INTAKE_LOV})
@Path(value = RESOURCE_INTAKE_LOV)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.TEXT_PLAIN)
public class IntakeLovResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(IntakeLovResource.class);

  /**
   * Constructor
   */
  @Inject
  public IntakeLovResource() {
    // Default
  }

  /**
   * Endpoint for Intake LOV.
   * 
   * @return web service response
   */
  @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.HOURS)
  @UnitOfWork(value = NS, cacheMode = CacheMode.NORMAL, flushMode = FlushMode.MANUAL,
      readOnly = true, transactional = false)
  @GET
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 400, message = "Unable to parse parameters")})
  @ApiOperation(value = "Query ElasticSearch Persons on given search terms",
      code = HttpStatus.SC_OK, response = IntakeLovEntry[].class)
  public Response getAll() {
    Response ret;
    try {
      final List<IntakeLov> intakeLovs = IntakeCodeCache.global().getAll();
      final List<IntakeLovEntry> intakeLovEntries = new ArrayList<>(intakeLovs.size());
      for (IntakeLov lov : intakeLovs) {
        intakeLovEntries.add(new IntakeLovEntry(lov));
      }

      ret = Response.status(Response.Status.OK).entity(new IntakeLovResponse(intakeLovEntries))
          .build();
    } catch (Exception e) {
      LOGGER.error("Intake LOV ERROR: {}", e.getMessage(), e);
      throw new ApiException("Intake LOV ERROR. " + e.getMessage(), e);
    }

    return ret;
  }

}
