package gov.ca.cwds.rest.resources.converter;

import gov.ca.cwds.fixture.investigation.RelationshipEntityBuilder;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class ResponseConverterTest {
    ResponseConverter converter;
    Relationship relationship;

    @Before
    public void setup(){
        converter = new ResponseConverter();
        relationship = new RelationshipEntityBuilder().build();
    }

    @Test
    public void withDataResponseShouldConvertApiResponseToWSResponseWhenDataIsNotNull(){
        Response response = converter.withDataResponse(relationship);
        assertEquals(relationship, response.getEntity());
    }

    @Test
    public void withDataResponseShouldReturn200StatusWhenDataIsNotNull(){
        Response response = converter.withDataResponse(relationship);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void withDataResponseShouldReturn404StatusWhenDataIsNull(){
        Response response = converter.withDataResponse((gov.ca.cwds.rest.api.Response) null);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void withOKResponseShouldReturn200StatusWhenDataIsNotNull(){
        Response response = converter.withOKResponse(relationship);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void withOkResponseShouldReturn404StatusWhenDataIsNull(){
        Response response = converter.withOKResponse(null);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void withCreatedResponseShouldReturnEntityStatusWhenDataIsNotNull(){
        Response response = converter.withCreatedResponse(relationship);
        assertEquals(relationship, response.getEntity());
    }

    @Test
    public void withCreatedResponseShouldReturn201StatusWhenDataIsNotNull(){
        Response response = converter.withCreatedResponse(relationship);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void withCreatedResponseShouldNotReturnDataStatusWhenDataIsNull(){
        Response response = converter.withCreatedResponse(null);
        assertEquals(null, response.getEntity());
    }

    @Test
    public void withCreatedResponseShouldReturn201StatusWhenDataIsNull(){
        Response response = converter.withCreatedResponse(relationship);
        assertEquals(201, response.getStatus());
    }
}