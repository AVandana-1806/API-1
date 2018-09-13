package gov.ca.cwds.rest.resources;

import static gov.ca.cwds.rest.core.Api.RESOURCE_INTAKE_LOV;
import static gov.ca.cwds.rest.core.Api.Datasource.NS;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.persistence.xa.CandaceDatasourceSlate;
import gov.ca.cwds.data.persistence.xa.CandaceSessionFactoryImpl;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.IntakeLovEntry;
import gov.ca.cwds.rest.api.domain.IntakeLovResponse;
import gov.ca.cwds.rest.api.domain.es.ESPersons;
import io.dropwizard.hibernate.UnitOfWork;
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

  private CandaceDatasourceSlate sessionFactories;

  /**
   * Constructor
   * 
   * @param sessionFactories Ferb session factories
   */
  @Inject
  public IntakeLovResource(CandaceDatasourceSlate sessionFactories) {
    this.sessionFactories = sessionFactories;
  }

  protected void logDatabaseHealth(SessionFactory sessionFactory) {
    final CandaceSessionFactoryImpl sf = (CandaceSessionFactoryImpl) sessionFactory;
    LOGGER.info("DATASOURCE HEALTH: {}", sf.getSessionFactoryName());
    sf.printOutstandingSessions();
  }

  @Path("/db_health")
  @GET
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 400, message = "Unable to parse parameters")})
  @ApiOperation(value = "Query ElasticSearch Persons on given search terms",
      code = HttpStatus.SC_OK, response = IntakeLovEntry[].class)
  public Response showDatabaseConnectionHealth() {
    Response ret;
    try {
      logDatabaseHealth(sessionFactories.getCmsRsSessionFactory());
      logDatabaseHealth(sessionFactories.getNsSessionFactory());
      logDatabaseHealth(sessionFactories.getCmsRsSessionFactory());

      ret = Response.status(Response.Status.OK).entity(new IntakeLovResponse(new ArrayList<>()))
          .build();
    } catch (Exception e) {
      LOGGER.error("FAILED TO SHOW OUTSTANDING DATABASE SESSIONS: {}", e.getMessage(), e);
      throw new ApiException("FAILED TO SHOW OUTSTANDING DATABASE SESSIONS: " + e.getMessage(), e);
    }

    return ret;
  }

  /**
   * Endpoint for Intake LOV.
   * 
   * @return web service response
   */
  // @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.MINUTES)
  @UnitOfWork(value = NS, cacheMode = CacheMode.NORMAL, flushMode = FlushMode.MANUAL,
      readOnly = true, transactional = false)
  @GET
  @ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 400, message = "Unable to parse parameters")})
  @ApiOperation(value = "Show Postgres LOV's", code = HttpStatus.SC_OK,
      response = IntakeLovEntry[].class)
  public Response getAll() {
    Response ret;
    try {
      final List<IntakeLov> lovs = IntakeCodeCache.global().getAll();
      final List<IntakeLovEntry> lovEntries = new ArrayList<>(lovs.size());
      for (IntakeLov lov : lovs) {
        lovEntries.add(new IntakeLovEntry(lov));
      }

      LOGGER.info("Intake LOV domain count: {}, raw count: {}", lovEntries.size(), lovs.size());
      ret = Response.status(Response.Status.OK).entity(new IntakeLovResponse(lovEntries)).build();
    } catch (Exception e) {
      LOGGER.error("Intake LOV ERROR: {}", e.getMessage(), e);
      throw new ApiException("Intake LOV ERROR. " + e.getMessage(), e);
    }

    return ret;
  }

}
