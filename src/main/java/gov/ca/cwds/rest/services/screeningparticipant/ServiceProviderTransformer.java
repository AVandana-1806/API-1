package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.cms.ServiceProvider;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Business layer object to transform a {@link ServiceProvider } to an {@link ParticipantIntakeApi}
 * 
 * @author CWDS API Team
 *
 */
public class ServiceProviderTransformer implements ParticipantMapper {

  @Override
  public ParticipantIntakeApi tranform(CmsPersistentObject object) {
    ServiceProvider serviceProvider = (ServiceProvider) object;

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(serviceProvider.getId(), null,
        new DateTime(serviceProvider.getLastUpdatedTime()), LegacyTable.SERVICE_PROVIDER.getName(),
        LegacyTable.SERVICE_PROVIDER.getDescription());

    String state = IntakeCodeCache.global()
        .getIntakeCodeForLegacySystemCode(serviceProvider.getStateCodeType());
    String streetAddress =
        serviceProvider.getStreetNumber() + " " + serviceProvider.getStreetName();
    String zip = serviceProvider.getZipNumber() + "-" + serviceProvider.getZipSuffixNumber();
    Set<AddressIntakeApi> addresses = new HashSet<>(Arrays.asList(new AddressIntakeApi(null, null,
        streetAddress, serviceProvider.getCity(), state, zip, null, legacyDescriptor)));
    addresses = Collections.unmodifiableSet(addresses);
    String phoneType =
        serviceProvider.getPhoneType() != null ? serviceProvider.getPhoneType().name() : null;
    Set<PhoneNumber> phoneNumbers = new HashSet<>(
        Arrays.asList(new PhoneNumber(null, serviceProvider.getPhoneNumber(), phoneType)));
    String sensitivityIndicator = serviceProvider.getSensitivityIndicator() != null
        ? serviceProvider.getSensitivityIndicator()
        : "";

    return new ParticipantIntakeApi(null, null, null, legacyDescriptor,
        serviceProvider.getFirstName(), serviceProvider.getMiddleName(),
        serviceProvider.getLastName(), serviceProvider.getSuffixTitleDescription(), null, null,
        null, null, serviceProvider.getBirthDate(), new LinkedList<>(), null, null, null,
        new HashSet<>(), addresses, phoneNumbers, "R".equals(sensitivityIndicator),
        "S".equals(sensitivityIndicator));
  }

}
