package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import gov.ca.cwds.data.persistence.ns.Addresses;

public class AddressIntakeApiTest {

  String id = "23456";
  String legacySourceTable = "ADDRESS_T";
  String legacyId = "1234567ABC";
  String streetAddress = "742 Evergreen Terrace";
  String city = "Springfield";
  String state = "CA";
  String zip = "93838";
  String type = "school";
  LegacyDescriptor legacyDescriptor;
  List<PhoneNumber> phones = Arrays.asList(new PhoneNumber("1112223333","Home"));
  String newId = "654321";
  String newLegacySourceTable = "CLIENT_T";
  String newLegacyId = "2345678ABC";
  String newStreetAddress = "742 Blue Place";

  @Test
  public void testConstructor() throws Exception {
    AddressIntakeApi domain = new AddressIntakeApi(legacySourceTable, legacyId, streetAddress, city, state, zip, type,
        legacyDescriptor);
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
    assertThat(domain.getLegacyId(), is(equalTo(legacyId)));
    assertThat(domain.getStreetAddress(), is(equalTo(streetAddress)));
    assertThat(domain.getCity(), is(equalTo(city)));
    assertThat(domain.getState(), is(equalTo(state)));
    assertThat(domain.getZip(), is(equalTo(zip)));
    assertThat(domain.getType(), is(equalTo(type)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
    
    domain = new AddressIntakeApi(legacySourceTable, legacyId, streetAddress, city, state, zip, type,
        legacyDescriptor, phones);
    assertThat(domain.getLegacySourceTable(), is(equalTo(legacySourceTable)));
    assertThat(domain.getLegacyId(), is(equalTo(legacyId)));
    assertThat(domain.getStreetAddress(), is(equalTo(streetAddress)));
    assertThat(domain.getCity(), is(equalTo(city)));
    assertThat(domain.getState(), is(equalTo(state)));
    assertThat(domain.getZip(), is(equalTo(zip)));
    assertThat(domain.getType(), is(equalTo(type)));
    assertThat(domain.getLegacyDescriptor(), is(equalTo(legacyDescriptor)));
    assertThat(domain.getPhoneNumbers(), is(equalTo(phones)));

  }
  
  @Test
  public void testConstructorUsingPersistentObject() throws Exception {
    Addresses persistent =
        new Addresses(id, streetAddress, city, state, zip, type, legacyId, legacySourceTable);
    AddressIntakeApi domain = new AddressIntakeApi(persistent);
    assertThat(domain.getLegacySourceTable(), is(equalTo(persistent.getLegacySourceTable())));
    assertThat(domain.getLegacyId(), is(equalTo(persistent.getLegacyId())));
    assertThat(domain.getStreetAddress(), is(equalTo(persistent.getStreetAddress())));
    assertThat(domain.getCity(), is(equalTo(persistent.getCity())));
    assertThat(domain.getState(), is(equalTo(persistent.getState())));
    assertThat(domain.getZip(), is(equalTo(persistent.getZip())));
    assertThat(domain.getType(), is(equalTo(persistent.getType())));
    assertThat(domain.getId(), is(equalTo(persistent.getId())));
    
  }

  @Test
  public void testSetters() throws Exception {
    AddressIntakeApi domain = new AddressIntakeApi(legacySourceTable, legacyId, streetAddress, city, state, zip, type,
        legacyDescriptor);
    domain.setId(newId);
    assertThat(domain.getId(), is(equalTo(newId)));
    domain.setLegacySourceTable(newLegacySourceTable);
    assertThat(domain.getLegacySourceTable(), is(equalTo(newLegacySourceTable)));
    domain.setLegacyId(newLegacyId);
    assertThat(domain.getLegacyId(), is(equalTo(newLegacyId)));
  }
  
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    AddressIntakeApi addresses = new AddressIntakeApi(legacySourceTable, legacyId, streetAddress, city, state, zip, type,
        legacyDescriptor);
    assertTrue(addresses.equals(addresses));
  }
}
