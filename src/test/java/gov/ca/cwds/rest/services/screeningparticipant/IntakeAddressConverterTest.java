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
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
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
 *
 */
public class IntakeAddressConverterTest {

  private IntakeAddressConverter addressConverter = new IntakeAddressConverter();
  private Client client;
  Set<ClientAddress> clientAddresses;
  Set<ClientAddress> allClientAddresses;
  ClientAddress sacramentoHomeAddress;

  /**
   * Initialize intake code cache. Tests rely on cache having AddressType Values pre populated.
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();

  @Before
  public void setup(){
    Address address = new AddressEntityBuilder().setCity("Sacramento").build();

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

  private Set<ClientAddress> buildAllClientAddresses(){
    Set<ClientAddress> addresses = new HashSet<ClientAddress>();

    addresses.add(sacramentoHomeAddress);

    addresses.add(createClientAddress("SanFranciscoId", "San Francisco", AddressType.HOME.getCode()));
    addresses.add(createClientAddress("ConcordId", "Concord", AddressType.COMMON.getCode()));
    addresses.add(createClientAddress("DailyCityId", "Daily City", AddressType.DAY_CARE.getCode()));
    addresses.add(createClientAddress("HumboltId", "Humbolt", AddressType.HOMELESS.getCode()));
    addresses.add(createClientAddress("OlemaId", "Olema", AddressType.OTHER.getCode()));
    addresses.add(createClientAddress("PetalumaId", "Petaluma", AddressType.PENAL_INSTITUTION.getCode()));
    addresses.add(createClientAddress("PengroveId", "Pengrove", AddressType.PERMANENT_MAILING_ADDRESS.getCode()));
    addresses.add(createClientAddress("RocklinId", "Rocklin", AddressType.RESIDENCE_2.getCode() ));
    addresses.add(createClientAddress("WestCovinaId", "West Covina", AddressType.WORK.getCode()));

    return addresses;
  }

  private ClientAddress createClientAddress(String id, String city, short addrType){
    Address sanFranciscoHomeAddress = new AddressEntityBuilder()
        .setCity(city)
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
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);
    assertThat(addressIntakeApis, is(notNullValue()));
  }

  @Test
  public void shouldContainAllClientAddresses() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    assertEquals(allClientAddresses.size(), addressIntakeApis.size());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void shouldContainASingleHomeAddressWhenClientOnlyHasOneHomeAddress() {
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    assertEquals(1, addressIntakeApis.size());
    AddressIntakeApi actualAddress = addressIntakeApis.get(0);
    assertEquals(actualAddress.getCity(), "Sacramento");
    assertEquals(actualAddress.getType(), "Residence");
  }

  @Test
  public void shouldContainAllHomeAddressesWhenClientOnlyHasMultipleHomeAddresses() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.HOME.getValue());
    assertEquals(2, homeAddresses.size());
  }

  @Test
  public void shouldContainDayCareAddressesWhenClientHasDayCare() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.DAY_CARE.getValue());
    assertEquals(1, homeAddresses.size());
  }

  @Test
  public void shouldContainCommonAddressesWhenClientHasCommonAddress() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.COMMON.getValue());
    assertEquals(1, homeAddresses.size());
  }

  @Test
  public void shouldContainHomelessAddressesWhenClientHasHomelessAddress() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.HOMELESS.getValue());
    assertEquals(1, homeAddresses.size());
  }

  @Test
  public void shouldContainOtherAddressesWhenClientHasOther() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.OTHER.getValue());
    assertEquals(1, homeAddresses.size());
  }

  @Test
  public void shouldContainPenalInstitutionAddressesWhenClientHasPenalInstitutionAddress() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.PENAL_INSTITUTION.getValue());
    assertEquals(1, homeAddresses.size());
  }

  @Test
  public void shouldContainPermanentAddressesWhenClientHasPermanentAddress() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.PERMANENT_MAILING_ADDRESS.getValue());
    assertEquals(1, homeAddresses.size());
  }

  @Test
  public void shouldContainResidence2AddressesWhenClientHasResidence2ddress() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.RESIDENCE_2.getValue());
    assertEquals(1, homeAddresses.size());
  }

  @Test
  public void shouldContainWorkAddressesWhenClientHasWork() {
    client.setClientAddress(allClientAddresses);
    List<AddressIntakeApi> addressIntakeApis = addressConverter.convert(client);

    List homeAddresses = collectAddressTypes(addressIntakeApis,  AddressType.WORK.getValue());
    assertEquals(1, homeAddresses.size());
  }


  private List collectAddressTypes(List<AddressIntakeApi> addresses, String typeCode){
    return addresses.stream()
        .map(AddressIntakeApi::getType)
        .filter(type -> typeCode.equals(type))
        .collect(Collectors.toList());
  }
}
