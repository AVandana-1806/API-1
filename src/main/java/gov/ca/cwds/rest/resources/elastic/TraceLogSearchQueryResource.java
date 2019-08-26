package gov.ca.cwds.rest.resources.elastic;

import static gov.ca.cwds.rest.core.Api.RESOURCE_SEARCH_QUERY;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.domain.CaresSearchQuery;
import gov.ca.cwds.rest.api.domain.elastic.SearchQueryTerms;
import gov.ca.cwds.rest.resources.converter.ResponseConverter;
import gov.ca.cwds.rest.services.elastic.SearchQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = RESOURCE_SEARCH_QUERY, tags = {RESOURCE_SEARCH_QUERY})
@Path(value = RESOURCE_SEARCH_QUERY)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TraceLogSearchQueryResource {

  private SearchQueryService searchQueryService;

  /**
   * Constructor
   *
   * @param searchQueryService the service.
   */
  @Inject
  public TraceLogSearchQueryResource(SearchQueryService searchQueryService) {
    this.searchQueryService = searchQueryService;
  }

  /**
   * Parse a CARES Elasticsearch query.
   *
   * @param user user conducting search
   * @param index index being searched
   * @param jsonPayload JSON Elasticsearch query
   * @return parsed query terms
   */
  @POST
  @Path("/{user}/{index}")
  @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to process JSON"),
      @ApiResponse(code = 406, message = "Accept Header not supported"),
      @ApiResponse(code = 409, message = "Conflict - already exists"),
      @ApiResponse(code = 422, message = "Unable to parse JSON")})
  @Consumes(value = MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Parses a CARES search query", code = HttpStatus.SC_CREATED,
      response = SearchQueryTerms.class)
  public Response create(
      @PathParam("user") @ApiParam(required = true, name = "user",
          value = "user performing search") String user,
      @PathParam("index") @ApiParam(required = true, name = "index",
          value = "index being searched") String index,
      @Valid @ApiParam(hidden = false, required = true,
          value = "Search query JSON") String jsonPayload) {
    final CaresSearchQuery request = new CaresSearchQuery();
    request.setJson(jsonPayload);
    request.setUser(user);
    request.setIndex(index);

    return new ResponseConverter().withCreatedResponse(searchQueryService.create(request));
  }

}
