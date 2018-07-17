package gov.ca.cwds.rest.services.screeningparticipant;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;
import gov.ca.cwds.data.persistence.cms.SystemCodeTestHarness;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * @author CWDS API Team
 *
 */
public class ServiceProviderTransformerTest {

  private ServiceProviderTransformer serviceProviderTransformer = new ServiceProviderTransformer();

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private static DateTime lastUpdated = new DateTime("2018-06-11T11:47:07.524-07:00");

  /**
   * Test when the serviceProvider transforms to participantIntakeApi is not null
   */
  @Test
  public void testTranformIsNotNull() throws Exception {
    ServiceProvider serviceProvider = validServiceProvider();
    ParticipantIntakeApi participantIntakeApi =
        serviceProviderTransformer.tranform(serviceProvider);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   * Test the legacy descriptor is set not null when serviceProvider is transformed to
   * participantIntakeApi
   */
  @Test
  public void testLegacyDescriptorNotNull() throws Exception {
    ServiceProvider serviceProvider = validServiceProvider();
    ParticipantIntakeApi participantIntakeApi =
        serviceProviderTransformer.tranform(serviceProvider);
    assertThat(participantIntakeApi.getLegacyDescriptor(), is(notNullValue()));
    assertThat(participantIntakeApi.getLegacyDescriptor().getTableName(),
        is(equalTo(LegacyTable.SERVICE_PROVIDER.getName())));
    assertThat(participantIntakeApi.getLegacyDescriptor().getId(), is(equalTo("Ao9dm8T0Ki")));
  }

  /**
   * 
   */
  @Test
  public void testAddressIsSet() throws Exception {
    ServiceProvider serviceProvider = validServiceProvider();
    ParticipantIntakeApi participantIntakeApi =
        serviceProviderTransformer.tranform(serviceProvider);
    assertThat(participantIntakeApi.getAddresses(), is(notNullValue()));
  }

  /**
   * Test service provider transform response is returned as expected.
   * 
   */
  @Test
  public void testConvertExpectdVsActual() throws Exception {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("Ao9dm8T0Ki", null, lastUpdated,
        LegacyTable.SERVICE_PROVIDER.getName(), LegacyTable.SERVICE_PROVIDER.getDescription());
    List<AddressIntakeApi> addresses = Collections.singletonList(new AddressIntakeApi(null, null,
        "streetNumber streetName", "Sacramento", "CA", "99999", null, legacyDescriptor));
    Set<PhoneNumber> phoneNumbers =
        new HashSet<>(Arrays.asList(new PhoneNumber(null, "999", null)));
    ParticipantIntakeApi expected =
        new ParticipantIntakeApi(null, null, null, legacyDescriptor, "Horacio", null, "G",
            "suffixTitleDescription", null, null, null, null, null, null, new LinkedList<>(), null,
            null, null, new HashSet<>(), addresses, phoneNumbers, false, false);
    ServiceProvider serviceProvider = validServiceProvider();
    ParticipantIntakeApi actual = serviceProviderTransformer.tranform(serviceProvider);
    actual.getLegacyDescriptor().setLastUpdated(lastUpdated);
    assertEquals(expected, actual);
  }

  private ServiceProvider validServiceProvider()
      throws JsonParseException, JsonMappingException, IOException {

    ServiceProvider validServiceProvider = MAPPER.readValue(
        fixture("fixtures/persistent/ServiceProvider/valid/valid.json"), ServiceProvider.class);

    return validServiceProvider;
  }

}
