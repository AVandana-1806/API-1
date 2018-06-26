package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.persistence.cms.EducationProviderContact;
import gov.ca.cwds.fixture.EducationProviderContactEntityBuilder;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;

/**
 * @author CWDS API Team
 *
 */
public class EducationProviderContactTransformerTest {

  private EducationProviderContactTransformer educationProviderContactTransformer =
      new EducationProviderContactTransformer();

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private static DateTime lastUpdated = new DateTime("2018-06-11T11:47:07.524-07:00");

  /**
   * Test when the educationProviderContact transform to participantIntakeApi is not null
   */
  @Test
  public void testTranformIsNotNull() throws Exception {
    EducationProviderContact educationProviderContact = validEducationProviderContact();
    ParticipantIntakeApi participantIntakeApi =
        educationProviderContactTransformer.tranform(educationProviderContact);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   * Test the legacy descriptor is set not null when educationProviderContact is transformed to
   * participantIntakeApi
   */
  @Test
  public void testLegacyDescriptorNotNull() throws Exception {
    EducationProviderContact educationProviderContact = validEducationProviderContact();
    ParticipantIntakeApi participantIntakeApi =
        educationProviderContactTransformer.tranform(educationProviderContact);
    assertThat(participantIntakeApi.getLegacyDescriptor(), is(notNullValue()));
    assertThat(participantIntakeApi.getLegacyDescriptor().getTableName(), is(equalTo("EDPRVCNT")));
    assertThat(participantIntakeApi.getLegacyDescriptor().getId(), is(equalTo("1234567ABC")));
  }

  /**
   * 
   */
  @Test
  public void testAddressIsSet() throws Exception {
    EducationProviderContact educationProviderContact = validEducationProviderContact();
    ParticipantIntakeApi participantIntakeApi =
        educationProviderContactTransformer.tranform(educationProviderContact);
    assertThat(participantIntakeApi.getAddresses(), is(notNullValue()));
  }

  /**
   * Test report transform response is returned as per expected.
   * 
   */
  @Test
  public void testConvertExpectdVsActual() throws Exception {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("1234567ABC", null, lastUpdated,
        "EDPRVCNT", "Education Provider Contact");
    LegacyDescriptor addressLegacyDescriptor =
        new LegacyDescriptor("fk12345678", null, lastUpdated, "ED_PVDRT", "Education Provider");
    Set<AddressIntakeApi> addresses = new HashSet<>(Arrays.asList(new AddressIntakeApi(null, null,
        "streetNumber streetName", "Sacramento", "CA", "99999-0", null, addressLegacyDescriptor)));
    Set<PhoneNumber> phoneNumbers = new HashSet<>(Arrays.asList(new PhoneNumber(null, "0", null)));
    ParticipantIntakeApi expected =
        new ParticipantIntakeApi(null, null, null, legacyDescriptor, "Firstname", "Middle",
            "Lastname", "Esq", null, null, null, null, null, null, new LinkedList<>(), null, null,
            null, new HashSet<>(), addresses, phoneNumbers, false, false);
    EducationProviderContact educationProviderContact = validEducationProviderContact();
    ParticipantIntakeApi actual =
        educationProviderContactTransformer.tranform(educationProviderContact);
    actual.getLegacyDescriptor().setLastUpdated(lastUpdated);
    actual.getAddresses().stream().findFirst().get().getLegacyDescriptor()
        .setLastUpdated(lastUpdated);
    assertEquals(expected, actual);
  }

  private EducationProviderContact validEducationProviderContact()
      throws JsonParseException, JsonMappingException, IOException {

    EducationProviderContact validEducationProviderContact =
        new EducationProviderContactEntityBuilder().build();
    return validEducationProviderContact;
  }

}


