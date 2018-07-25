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
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
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
public class SubstituteCareProviderTransformerTest {

  private SubstituteCareProviderTransformer substituteCareProviderTransformer =
      new SubstituteCareProviderTransformer();

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private static DateTime lastUpdated = new DateTime("2018-06-11T11:47:07.524-07:00");

  /**
   * Test when the substituteCareProvider transforms to participantIntakeApi is not null
   */
  @Test
  public void testTranformIsNotNull() throws Exception {
    SubstituteCareProvider substituteCareProvider = validSubstituteCareProvider();
    ParticipantIntakeApi participantIntakeApi =
        substituteCareProviderTransformer.tranform(substituteCareProvider);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   * Test the legacy descriptor is set not null when substituteCareProvider is transformed to
   * participantIntakeApi
   */
  @Test
  public void testLegacyDescriptorNotNull() throws Exception {
    SubstituteCareProvider substituteCareProvider = validSubstituteCareProvider();
    ParticipantIntakeApi participantIntakeApi =
        substituteCareProviderTransformer.tranform(substituteCareProvider);
    assertThat(participantIntakeApi.getLegacyDescriptor(), is(notNullValue()));
    assertThat(participantIntakeApi.getLegacyDescriptor().getTableName(),
        is(equalTo(LegacyTable.SUBSTITUTE_CARE_PROVIDER.getName())));
    assertThat(participantIntakeApi.getLegacyDescriptor().getId(), is(equalTo("aQqUhBQF11")));
  }

  /**
   * 
   */
  @Test
  public void testAddressIsSet() throws Exception {
    SubstituteCareProvider substituteCareProvider = validSubstituteCareProvider();
    ParticipantIntakeApi participantIntakeApi =
        substituteCareProviderTransformer.tranform(substituteCareProvider);
    assertThat(participantIntakeApi.getAddresses(), is(notNullValue()));
  }

  /**
   * Test substitute care provider transform response is returned as expected.
   * 
   */
  @Test
  public void testConvertExpectdVsActual() throws Exception {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("aQqUhBQF11", null, lastUpdated,
        LegacyTable.SUBSTITUTE_CARE_PROVIDER.getName(),
        LegacyTable.SUBSTITUTE_CARE_PROVIDER.getDescription());
    List<AddressIntakeApi> addresses = Collections.singletonList(new AddressIntakeApi(null, null,
        "Number 5th St", "Sacramento", "CA", "95814", null, legacyDescriptor));
    Set<PhoneNumber> phoneNumbers = new HashSet<>(Arrays.asList(new PhoneNumber(null, "0", null)));
    ParticipantIntakeApi expected = new ParticipantIntakeApi(null, null, null, legacyDescriptor,
        "Fish", "N", "Tuna", "Description", null, null, null, "000994415",
        validSubstituteCareProvider().getBirthDate(), validSubstituteCareProvider().getDeathDate(),
        new LinkedList<>(), null, null, null, new HashSet<>(), addresses, phoneNumbers, false,
        false);
    SubstituteCareProvider substituteCareProvider = validSubstituteCareProvider();
    ParticipantIntakeApi actual =
        substituteCareProviderTransformer.tranform(substituteCareProvider);
    actual.getLegacyDescriptor().setLastUpdated(lastUpdated);
    assertEquals(expected, actual);
  }

  private SubstituteCareProvider validSubstituteCareProvider()
      throws JsonParseException, JsonMappingException, IOException {

    SubstituteCareProvider validSubstituteCareProvider =
        MAPPER.readValue(fixture("fixtures/persistent/SubstituteCareProvider/valid/valid.json"),
            SubstituteCareProvider.class);

    return validSubstituteCareProvider;
  }

}

