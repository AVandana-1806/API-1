package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome;
import gov.ca.cwds.fixture.OtherChildInPlacemtHomeEntityBuilder;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * @author CWDS API Team
 *
 */
public class OtherChildInPlacemtHomeTransformerTest {

  private OtherChildInPlacemtHomeTransformer otherChildInPlacemtHomeTransformer =
      new OtherChildInPlacemtHomeTransformer();
  private LocalDateTime updatedTime = LocalDateTime.now();
  private static DateTime lastUpdated = new DateTime("2018-06-11T11:47:07.524-07:00");
  private PlacementHome placementHome;

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  /**
   * 
   */
  @Before
  public void setup() {
    placementHome = mock(PlacementHome.class);
    when(placementHome.getIdentifier()).thenReturn("ph12345678");
    when(placementHome.getCityNm()).thenReturn("Sacramento");
    when(placementHome.getStreetNo()).thenReturn("streetNumber");
    when(placementHome.getStreetNm()).thenReturn("streetName");
    when(placementHome.getZipNo()).thenReturn("99999");
    when(placementHome.getZipSfxNo()).thenReturn("0");
    when(placementHome.getStateCode()).thenReturn((short) 1828);
    when(placementHome.getLastUpdateTime()).thenReturn(updatedTime);
  }

  /**
   * Test when the OtherChildInPlacemtHome transforms to participantIntakeApi is not null
   */
  @Test
  public void testTranformIsNotNull() {
    OtherChildInPlacemtHome otherChildInPlacemtHome =
        new OtherChildInPlacemtHomeEntityBuilder().build();
    otherChildInPlacemtHome.setPlacementHome(placementHome);
    ParticipantIntakeApi participantIntakeApi =
        otherChildInPlacemtHomeTransformer.tranform(otherChildInPlacemtHome);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   * Test the legacy descriptor is set not null when OtherChildInPlacemtHome is transformed to
   * participantIntakeApi
   */
  @Test
  public void testLegacyDescriptorNotNull() {
    OtherChildInPlacemtHome otherChildInPlacemtHome =
        new OtherChildInPlacemtHomeEntityBuilder().build();
    otherChildInPlacemtHome.setPlacementHome(placementHome);
    ParticipantIntakeApi participantIntakeApi =
        otherChildInPlacemtHomeTransformer.tranform(otherChildInPlacemtHome);
    assertThat(participantIntakeApi.getLegacyDescriptor(), is(notNullValue()));
    assertThat(participantIntakeApi.getLegacyDescriptor().getTableName(),
        is(equalTo(LegacyTable.CHILD_IN_PLACEMENT_HOME.getName())));
    assertThat(participantIntakeApi.getLegacyDescriptor().getId(), is(equalTo("Ahzpb2C0Ki")));
  }

  /**
   * 
   */
  @Test
  public void testAddressIsSet() {
    OtherChildInPlacemtHome otherChildInPlacemtHome =
        new OtherChildInPlacemtHomeEntityBuilder().build();
    otherChildInPlacemtHome.setPlacementHome(placementHome);
    ParticipantIntakeApi participantIntakeApi =
        otherChildInPlacemtHomeTransformer.tranform(otherChildInPlacemtHome);
    assertThat(participantIntakeApi.getAddresses(), is(notNullValue()));
  }

  /**
   * Test service provider transform response is returned as expected.
   * 
   */
  @Test
  public void testConvertExpectdVsActual() {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("Ahzpb2C0Ki", null, lastUpdated,
        LegacyTable.CHILD_IN_PLACEMENT_HOME.getName(),
        LegacyTable.CHILD_IN_PLACEMENT_HOME.getDescription());
    LegacyDescriptor addressLegacyDescriptor = new LegacyDescriptor("ph12345678", null, lastUpdated,
        LegacyTable.PLACEMENT_HOME.getName(), LegacyTable.PLACEMENT_HOME.getDescription());

    Set<AddressIntakeApi> addresses = new HashSet<>(Arrays.asList(new AddressIntakeApi(null, null,
        "streetNumber streetName", "Sacramento", "CA", "99999", null, addressLegacyDescriptor)));
    Set<PhoneNumber> phoneNumbers = null;
    ParticipantIntakeApi expected = new ParticipantIntakeApi(null, null, null, legacyDescriptor,
        "aaa", null, "bbb", null, "male", null, null, null, new Date(), null, new LinkedList<>(),
        null, null, null, new HashSet<>(), addresses, phoneNumbers, false, false);
    OtherChildInPlacemtHome otherChildInPlacemtHome =
        new OtherChildInPlacemtHomeEntityBuilder().build();
    otherChildInPlacemtHome.setPlacementHome(placementHome);
    ParticipantIntakeApi actual =
        otherChildInPlacemtHomeTransformer.tranform(otherChildInPlacemtHome);
    actual.getLegacyDescriptor().setLastUpdated(lastUpdated);
    actual.getAddresses().stream().findFirst().get().getLegacyDescriptor()
        .setLastUpdated(lastUpdated);
    assertEquals(expected, actual);
  }

}
