package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.fixture.ReporterEntityBuilder;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * @author CWDS API Team
 *
 */
public class ReporterTransformerTest {

  private ReporterTransformer reporterTransformer = new ReporterTransformer();

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private static DateTime lastUpdated = new DateTime("2018-06-11T11:47:07.524-07:00");

  /**
   * Test when the reporter transform to participantIntakeApi is not null
   */
  @Test
  public void testTranformIsNotNull() {
    Reporter reporter = new ReporterEntityBuilder().build();
    ParticipantIntakeApi participantIntakeApi = reporterTransformer.tranform(reporter);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   * Test the legacy descriptor is set not null when reporter is transformed to participantIntakeApi
   */
  @Test
  public void testLegacyDescriptorNotNull() {
    Reporter reporter = new ReporterEntityBuilder().build();
    ParticipantIntakeApi participantIntakeApi = reporterTransformer.tranform(reporter);
    assertThat(participantIntakeApi.getLegacyDescriptor(), is(notNullValue()));
    assertThat(participantIntakeApi.getLegacyDescriptor().getTableName(),
        is(equalTo(LegacyTable.REPORTER.getName())));
    assertThat(participantIntakeApi.getLegacyDescriptor().getId(), is(equalTo("Abc0987654")));
  }

  /**
   * 
   */
  @Test
  public void testAddressIsSet() {
    Reporter reporter = new ReporterEntityBuilder().build();
    ParticipantIntakeApi participantIntakeApi = reporterTransformer.tranform(reporter);
    assertThat(participantIntakeApi.getAddresses(), is(notNullValue()));
  }

  /**
   * Test report transform response is returned as per expected.
   * 
   */
  @Test
  public void testConvertExpectdVsActual() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("Abc0987654", null, lastUpdated,
        LegacyTable.REPORTER.getName(), LegacyTable.REPORTER.getDescription());
    List<AddressIntakeApi> addresses = Collections.singletonList(new AddressIntakeApi(null, null,
        "2751 First Street", "Sacramento", "CA", "95833", null, legacyDescriptor));
    Set<PhoneNumber> phoneNumbers =
        new HashSet<>(Arrays.asList(new PhoneNumber("6199221167", null)));
    ParticipantIntakeApi expected = new ParticipantIntakeApi(null, null, null, legacyDescriptor,
        "Fred", "W", "Reporter", "", null, null, null, null, null, null, new LinkedList<>(), null,
        null, null, new HashSet<>(), addresses, phoneNumbers, false, false);
    Reporter reporter = new ReporterEntityBuilder().build();
    ParticipantIntakeApi actual = reporterTransformer.tranform(reporter);
    actual.getLegacyDescriptor().setLastUpdated(lastUpdated);
    assertEquals(expected, actual);
  }

}
