package gov.ca.cwds.rest.services.screeningparticipant;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome;
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
public class OtherAdultInPlacemtHomeTransformerTest {

  private OtherAdultInPlacemtHomeTransformer otherAdultInPlacemtHomeTransformer =
      new OtherAdultInPlacemtHomeTransformer();

  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private static DateTime lastUpdated = new DateTime("2018-06-11T11:47:07.524-07:00");
  private LocalDateTime updatedTime = LocalDateTime.now();
  private PlacementHome placementHome;

  /**
   * Test when the serviceProvider transforms to participantIntakeApi is not null
   */
  @Test
  public void testTranformIsNotNull() throws Exception {
    OtherAdultInPlacemtHome otherAdultInPlacemtHome = validOtherAdultInPlacemtHome();
    ParticipantIntakeApi participantIntakeApi =
        otherAdultInPlacemtHomeTransformer.tranform(otherAdultInPlacemtHome);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   * Test the legacy descriptor is set not null when serviceProvider is transformed to
   * participantIntakeApi
   */
  @Test
  public void testLegacyDescriptorNotNull() throws Exception {
    OtherAdultInPlacemtHome otherAdultInPlacemtHome = validOtherAdultInPlacemtHome();
    ParticipantIntakeApi participantIntakeApi =
        otherAdultInPlacemtHomeTransformer.tranform(otherAdultInPlacemtHome);
    assertThat(participantIntakeApi.getLegacyDescriptor(), is(notNullValue()));
    assertThat(participantIntakeApi.getLegacyDescriptor().getTableName(),
        is(equalTo(LegacyTable.ADULT_IN_PLACEMENT_HOME.getName())));
    assertThat(participantIntakeApi.getLegacyDescriptor().getId(), is(equalTo("AhfOGkK0QO")));
  }

  /**
   * 
   */
  @Test
  public void testAddressIsSet() throws Exception {
    OtherAdultInPlacemtHome otherAdultInPlacemtHome = validOtherAdultInPlacemtHome();
    ParticipantIntakeApi participantIntakeApi =
        otherAdultInPlacemtHomeTransformer.tranform(otherAdultInPlacemtHome);
    assertThat(participantIntakeApi.getAddresses(), is(notNullValue()));
  }

  /**
   * Test service provider transform response is returned as expected.
   * 
   */
  @Test
  public void testConvertExpectdVsActual() throws Exception {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("AhfOGkK0QO", null, lastUpdated,
        LegacyTable.ADULT_IN_PLACEMENT_HOME.getName(),
        LegacyTable.ADULT_IN_PLACEMENT_HOME.getDescription());
    LegacyDescriptor addressLegacyDescriptor = new LegacyDescriptor("ph12345678", null, lastUpdated,
        LegacyTable.PLACEMENT_HOME.getName(), LegacyTable.PLACEMENT_HOME.getDescription());

    Set<AddressIntakeApi> addresses = new HashSet<>(Arrays.asList(new AddressIntakeApi(null, null,
        "streetNumber streetName", "Sacramento", "CA", "99999", null, addressLegacyDescriptor)));
    Set<PhoneNumber> phoneNumbers = null;
    ParticipantIntakeApi expected =
        new ParticipantIntakeApi(null, null, null, legacyDescriptor, "Karen", null, "Q", null, "M",
            null, null, null, validOtherAdultInPlacemtHome().getBirthDate(),
            validOtherAdultInPlacemtHome().getDeathDate(), new LinkedList<>(), null, null, null,
            new HashSet<>(), addresses, phoneNumbers, false, false);
    OtherAdultInPlacemtHome otherAdultInPlacemtHome = validOtherAdultInPlacemtHome();
    ParticipantIntakeApi actual =
        otherAdultInPlacemtHomeTransformer.tranform(otherAdultInPlacemtHome);
    actual.getLegacyDescriptor().setLastUpdated(lastUpdated);
    actual.getAddresses().stream().findFirst().get().getLegacyDescriptor()
        .setLastUpdated(lastUpdated);
    assertEquals(expected, actual);
  }

  private OtherAdultInPlacemtHome validOtherAdultInPlacemtHome()
      throws JsonParseException, JsonMappingException, IOException {

    OtherAdultInPlacemtHome validOtherAdultInPlacemtHome =
        MAPPER.readValue(fixture("fixtures/persistent/OtherAdultInPlacemtHome/valid/valid.json"),
            OtherAdultInPlacemtHome.class);
    placementHome = mock(PlacementHome.class);
    when(placementHome.getIdentifier()).thenReturn("ph12345678");
    when(placementHome.getCityNm()).thenReturn("Sacramento");
    when(placementHome.getStreetNo()).thenReturn("streetNumber");
    when(placementHome.getStreetNm()).thenReturn("streetName");
    when(placementHome.getZipNo()).thenReturn("99999");
    when(placementHome.getZipSfxNo()).thenReturn("0");
    when(placementHome.getStateCode()).thenReturn((short) 1828);
    when(placementHome.getLastUpdateTime()).thenReturn(updatedTime);

    validOtherAdultInPlacemtHome.setPlacementHome(placementHome);
    return validOtherAdultInPlacemtHome;

  }

}
