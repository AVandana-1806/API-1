package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.fixture.AddressEntityBuilder;
import gov.ca.cwds.fixture.ClientAddressEntityBuilder;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.enums.AddressType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS API Team
 */
public class IntakePhoneConverterTest {

  Set<ClientAddress> clientAddresses;
  Set<ClientAddress> allClientAddresses;
  ClientAddress sacramentoHomeAddress;
  private IntakePhoneConverter phoneConverter = new IntakePhoneConverter();
  private Client client;
  /**
   * Initialize intake code cache. Tests rely on cache having AddressType Values pre populated.
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  @Before
  public void setup() {
    Address address = new AddressEntityBuilder().setCity("Sacramento")
        .setPrimaryNumber(7778889999L)
        .setMessageNumber(8889990000L)
        .setEmergencyNumber(10101010101L)
        .build();

    sacramentoHomeAddress = new ClientAddressEntityBuilder()
        .setId("SacramentoId")
        .setAddressType(AddressType.HOME.getCode())
        .setEffEndDt(null)
        .setAddresses(address)
        .buildClientAddress();
    clientAddresses = new HashSet<>(Arrays.asList(sacramentoHomeAddress));

    allClientAddresses = buildAllClientAddresses();

    client = new ClientEntityBuilder().setClientAddress(clientAddresses).build();
  }

  private Set<ClientAddress> buildAllClientAddresses() {
    Set<ClientAddress> addresses = new HashSet<ClientAddress>();

    addresses.add(sacramentoHomeAddress);

    addresses.add(createClientAddress("SanFranciscoId", "San Francisco", AddressType.HOME.getCode(),
        1112223333L, 22233344444L, 0L));
    addresses.add(
        createClientAddress("ConcordId", "Concord", AddressType.COMMON.getCode(), 3334445555L, 0L,
            4445556666L));
    addresses.add(
        createClientAddress("RocklinId", "Rocklin", AddressType.RESIDENCE_2.getCode(), 5556667777L,
            0L, 0L));
    addresses.add(
        createClientAddress("WestCovinaId", "West Covina", AddressType.WORK.getCode(), 66677788888L,
            0L, 0L));

    return addresses;
  }

  private ClientAddress createClientAddress(String id, String city, short addrType,
      Long primaryNumber, Long messageNumber, Long emergencyNumber) {
    Address sanFranciscoHomeAddress = new AddressEntityBuilder()
        .setCity(city)
        .setPrimaryNumber(primaryNumber)
        .setMessageNumber(messageNumber)
        .setEmergencyNumber(emergencyNumber)
        .build();
    return new ClientAddressEntityBuilder()
        .setId(id)
        .setAddressType(addrType)
        .setEffEndDt(null)
        .setAddresses(sanFranciscoHomeAddress)
        .buildClientAddress();
  }

  @Test
  public void testConvertIsNotNull() {
    Client client = new ClientEntityBuilder().build();
    List<PhoneNumber> phoneNumbers = phoneConverter.convert(client);
    assertThat(phoneNumbers, is(notNullValue()));
  }

  @Test
  public void shouldContainAllPhones() {
    client.setClientAddress(allClientAddresses);
    List<PhoneNumber> phoneNumbers = phoneConverter.convert(client);

    assertEquals(9, phoneNumbers.size());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void shouldContainThreePhones() {
    List<PhoneNumber> phoneNumbers = phoneConverter.convert(client);

    assertEquals(3, phoneNumbers.size());
    PhoneNumber phoneNumber = phoneNumbers.get(0);
    assertEquals(phoneNumber.getNumber(), "7778889999");
    assertEquals(phoneNumber.getType(), "Home");
    phoneNumber = phoneNumbers.get(1);
    assertEquals(phoneNumber.getNumber(), "8889990000");
    assertEquals(phoneNumber.getType(), "Cell");
    phoneNumber = phoneNumbers.get(2);
    assertEquals(phoneNumber.getNumber(), "10101010101");
    assertEquals(phoneNumber.getType(), "Other");
  }

  @Test
  public void shouldContainAllPhonesWhenClientHasMultipleAddresses() {
    client.setClientAddress(allClientAddresses);
    List<PhoneNumber> phoneNumbers = phoneConverter.convert(client);

    List homePhones = collectPhoneTypes(phoneNumbers, "Home");
    assertEquals(3, homePhones.size());
  }


  @Test
  public void shouldContainCellNumbesWhenClientHasMessageNumbers() {
    client.setClientAddress(allClientAddresses);
    List<PhoneNumber> phoneNumbers = phoneConverter.convert(client);

    List cells = collectPhoneTypes(phoneNumbers, "Cell");
    assertEquals(2, cells.size());
  }

  @Test
  public void shouldContainWorkNumbersWhenClientHasBusinessAddressWithPhone() {
    client.setClientAddress(allClientAddresses);
    List<PhoneNumber> phoneNumbers = phoneConverter.convert(client);

    List work = collectPhoneTypes(phoneNumbers, "Work");
    assertEquals(1, work.size());
  }

  @Test
  public void shouldContainOtherNumbes() {
    client.setClientAddress(allClientAddresses);
    List<PhoneNumber> phoneNumbers = phoneConverter.convert(client);

    List other = collectPhoneTypes(phoneNumbers, "Other");
    assertEquals(3, other.size());
  }


  private List collectPhoneTypes(List<PhoneNumber> phoneNumbers, String typeCode) {
    return phoneNumbers.stream()
        .map(PhoneNumber::getType)
        .filter(type -> typeCode.equals(type))
        .collect(Collectors.toList());
  }
}
