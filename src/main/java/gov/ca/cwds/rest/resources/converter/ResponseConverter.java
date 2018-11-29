package gov.ca.cwds.rest.resources.converter;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;

import javax.ws.rs.core.Response;

/**
 * This class looks important but lacks any Javadoc comment, so it has one now. :-)
 * 
 * @author CWDS API Team
 */
public class ResponseConverter {

  public Response withDataResponse(gov.ca.cwds.rest.api.Response serviceResponse) {
    return serviceResponse != null ? Response.ok(serviceResponse).build() : notFound();
  }

  public Response withDataResponse(List<gov.ca.cwds.rest.api.Response> serviceResponse) {
    return serviceResponse != null ? Response.ok(serviceResponse).build() : notFound();
  }

  public Response withOKResponse(gov.ca.cwds.rest.api.Response serviceResponse) {
    return serviceResponse != null ? Response.status(OK).build() : notFound();
  }

  public Response withCreatedResponse(gov.ca.cwds.rest.api.Response serviceResponse) {
    return Response.status(CREATED).entity(serviceResponse).build();
  }

  public Response withUpdatedResponse(gov.ca.cwds.rest.api.Response serviceResponse) {
    return serviceResponse != null ? Response.status(OK).entity(serviceResponse).build()
        : notFound();
  }

  public Response withCreatedResponse(List<gov.ca.cwds.rest.api.Response> serviceResponse) {
    return serviceResponse != null ? Response.status(CREATED).entity(serviceResponse).build()
        : notFound();
  }

  private Response notFound() {
    return Response.status(NOT_FOUND).entity((Object) null).build();
  }

}
