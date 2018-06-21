package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Intake Address Converter transforms the legacy {@link Address} into a intake
 * {@link AddressIntakeApi}, From legacy find the recent open address residence for the client.
 * 
 * @author CWDS API Team
 *
 */
public class IntakeAddressConverter {

  private static final Short RESIDENCE = 32;

  /**
   * @param client - client
   * @return the addressIntakeApi
   */
  public List<AddressIntakeApi> convert(Client client) {
    List<AddressIntakeApi> addresses = new ArrayList<>();
    if (client.getClientAddress() != null) {
      Set<ClientAddress> clientAddresses = client.getClientAddress().stream()
          .filter(clientAddress -> clientAddress.getEffEndDt() == null)
          .filter(clientAddress -> RESIDENCE.equals(clientAddress.getAddressType()))
          .collect(Collectors.toSet());
      Comparator<ClientAddress> clientAddressComparator = (ClientAddress c1, ClientAddress c2) -> c2
          .getLastUpdatedTime().compareTo(c1.getLastUpdatedTime());
      List<ClientAddress> clientAddressList = new ArrayList<>(clientAddresses);
      Collections.sort(clientAddressList, clientAddressComparator);
      clientAddressList.forEach(clientAddress -> addresses.add(convertToAddress(clientAddress)));
    }
    return addresses;
  }

  private AddressIntakeApi convertToAddress(ClientAddress clientAddress) {
    Address address = clientAddress.getAddresses();
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(address.getId(), null, new DateTime(address.getLastUpdatedTime()),
            LegacyTable.ADDRESS.getName(), LegacyTable.ADDRESS.getDescription());
    String streetAddress = address.getStreetNumber() + " " + address.getStreetName();
    String state = IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(address.getStateCd());
    String type =
        IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(clientAddress.getAddressType());
    return new AddressIntakeApi(null, null, streetAddress, address.getCity(), state,
        getZip(address), type, legacyDescriptor);
  }

  private String getZip(Address address) {
    String zip = address.getZip();
    if (address.getZip4() != null) {
      return address.getZip() + "-" + address.getZip4();
    }
    return zip;
  }
}
