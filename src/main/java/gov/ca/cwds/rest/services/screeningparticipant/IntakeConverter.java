package gov.ca.cwds.rest.services.screeningparticipant;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class IntakeConverter {

  protected List<ClientAddress> convertPersonalData(Client client) {
    Set<ClientAddress> clientAddresses = client.getClientAddress().stream()
        .filter(clientAddress -> clientAddress.getEffEndDt() == null)
        .collect(Collectors.toSet());
    Comparator<ClientAddress> clientAddressComparator = (ClientAddress c1, ClientAddress c2) -> c2
        .getLastUpdatedTime().compareTo(c1.getLastUpdatedTime());
    List<ClientAddress> clientAddressList = new ArrayList<>(clientAddresses);
    Collections.sort(clientAddressList, clientAddressComparator);
    return clientAddressList;
  }
}
