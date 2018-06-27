package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.fixture.CollateralIndividualEntityBuilder;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * @author CWDS API Team
 *
 */
public class CollateralIndividualTranformerTest {

  private CollateralIndividualTranformer collateralIndividualTranformer =
      new CollateralIndividualTranformer();

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private static DateTime lastUpdated = new DateTime("2018-06-11T11:47:07.524-07:00");

  /**
   * Test when the collateralIndividual transform to participantIntakeApi is not null
   */
  @Test
  public void testTranformIsNotNull() {
    CollateralIndividual collateralIndividual = new CollateralIndividualEntityBuilder().build();
    ParticipantIntakeApi participantIntakeApi =
        collateralIndividualTranformer.tranform(collateralIndividual);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   * Test the legacy descriptor is set not null when collateralIndividual is transformed to
   * participantIntakeApi
   */
  @Test
  public void testLegacyDescriptorNotNull() {
    CollateralIndividual collateralIndividual = new CollateralIndividualEntityBuilder().build();
    ParticipantIntakeApi participantIntakeApi =
        collateralIndividualTranformer.tranform(collateralIndividual);
    assertThat(participantIntakeApi.getLegacyDescriptor(), is(notNullValue()));
    assertThat(participantIntakeApi.getLegacyDescriptor().getTableName(),
        is(equalTo(LegacyTable.COLLATERAL_INDIVIDUAL.getName())));
    assertThat(participantIntakeApi.getLegacyDescriptor().getId(), is(equalTo("AarHGUP0Ki")));
  }

  /**
   * 
   */
  @Test
  public void testAddressIsSet() {
    CollateralIndividual collateralIndividual = new CollateralIndividualEntityBuilder().build();
    ParticipantIntakeApi participantIntakeApi =
        collateralIndividualTranformer.tranform(collateralIndividual);
    assertThat(participantIntakeApi.getAddresses(), is(notNullValue()));
  }

  /**
   * Test report transform response is returned as per expected.
   * 
   * @throws JsonProcessingException
   * 
   */
  @Test
  public void testConvertExpectdVsActual() throws JsonProcessingException {
    LegacyDescriptor legacyDescriptor = new LegacyDescriptor("AarHGUP0Ki", null, lastUpdated,
        LegacyTable.COLLATERAL_INDIVIDUAL.getName(),
        LegacyTable.COLLATERAL_INDIVIDUAL.getDescription());
    Set<AddressIntakeApi> addresses = new HashSet<>(Arrays.asList(new AddressIntakeApi(null, null,
        "2751 West River", "Sacramento", "CA", "95833", null, legacyDescriptor)));
    Set<PhoneNumber> phoneNumbers = new HashSet<>(Arrays.asList(new PhoneNumber(null, "1", null)));
    ParticipantIntakeApi expected =
        new ParticipantIntakeApi(null, null, null, legacyDescriptor, "firstName", "middleName",
            "lastName", "Jr", "male", null, null, null, new Date(), new Date(), new LinkedList<>(),
            null, null, null, new HashSet<>(), addresses, phoneNumbers, false, false);
    CollateralIndividual collateralIndividual = new CollateralIndividualEntityBuilder().build();
    ParticipantIntakeApi actual = collateralIndividualTranformer.tranform(collateralIndividual);
    actual.getLegacyDescriptor().setLastUpdated(lastUpdated);
    assertThat(expected.getFirstName(), is(equalTo(actual.getFirstName())));
    assertThat(expected.getGender(), is(equalTo(actual.getGender())));
    assertThat(expected.getAddresses(), is(equalTo(actual.getAddresses())));
    assertThat(expected.getPhoneNumbers(), is(equalTo(actual.getPhoneNumbers())));
  }

}
