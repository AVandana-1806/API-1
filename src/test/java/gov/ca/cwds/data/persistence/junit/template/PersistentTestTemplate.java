package gov.ca.cwds.data.persistence.junit.template;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.ca.cwds.data.persistence.cms.SystemCodeTestHarness;

/**
 * Persistent Entity JUnits abstract class.
 * 
 * <p>
 * These JUnit tests must be included with any persistent classes.
 * </p>
 * 
 * @author CWDS API Team
 */
public interface PersistentTestTemplate {

  @SuppressWarnings({"unchecked"})
  static <T> T valid(T t) throws JsonParseException, JsonMappingException, IOException {
    final String pkgClassName =
        t.getClass().getName().substring(t.getClass().getName().lastIndexOf('.') + 1);
    return (T) SystemCodeTestHarness.MAPPER.readValue(
        fixture("fixtures/persistent/" + pkgClassName + "/valid/valid.json"), t.getClass());
  }

  @Test
  public void testEmptyConstructor() throws Exception;

  @Test
  public void testPersistentConstructor() throws Exception;

  @Test
  public void testConstructorUsingDomain() throws Exception;

}
