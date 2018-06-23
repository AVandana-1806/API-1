package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.EducationProvider;
import gov.ca.cwds.data.persistence.cms.EducationProviderContact;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Business layer object to transform a {@link EducationProviderContact} to an
 * {@link ParticipantIntakeApi}
 * 
 * @author CWDS API Team
 *
 */
public class EducationProviderContactTransformer
    implements ParticipantMapper<EducationProviderContact> {

  @Override
  public ParticipantIntakeApi tranform(EducationProviderContact educationProviderContact) {

    LegacyDescriptor educationProviderContactLegacyDescriptor =
        new LegacyDescriptor(educationProviderContact.getId(), null,
            new DateTime(educationProviderContact.getLastUpdatedTime()),
            LegacyTable.EDUCATION_PROVIDER_CONTACT.getName(),
            LegacyTable.EDUCATION_PROVIDER_CONTACT.getDescription());

    String firstName = educationProviderContact.getFirstName();
    String lastName = educationProviderContact.getLastName();
    String middleName = educationProviderContact.getMiddleName();
    String suffixTitle = educationProviderContact.getNameSuffix();
    String ssn = educationProviderContact.getSsn();
    String sensitivityIndicator = educationProviderContact.getSensitivityIndicator() != null
        ? educationProviderContact.getSensitivityIndicator()
        : "";

    EducationProvider educationProvider = educationProviderContact.getEducationProvider();
    String streetAddress =
        educationProvider.getStreetNumber() + " " + educationProvider.getStreetName();
    String state =
        IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(educationProvider.getStateCd());

    LegacyDescriptor educationProviderLegacyDescriptor = new LegacyDescriptor(
        educationProvider.getId(), null,
        new org.joda.time.DateTime(educationProvider.getLastUpdatedTime()),
        LegacyTable.EDUCATION_PROVIDER.getName(), LegacyTable.EDUCATION_PROVIDER.getDescription());

    Set<AddressIntakeApi> addresses = new HashSet<>(Arrays
        .asList(new AddressIntakeApi(null, null, streetAddress, educationProvider.getCityName(),
            state, getZip(educationProvider), null, educationProviderLegacyDescriptor)));
    addresses = Collections.unmodifiableSet(addresses);

    Set<PhoneNumber> phoneNumbers = new HashSet<>(
        Arrays.asList(new PhoneNumber(null, educationProviderContact.getPhoneNumber(), null)));


    return new ParticipantIntakeApi(null, null, null, educationProviderContactLegacyDescriptor,
        firstName, middleName, lastName, suffixTitle, educationProviderContact.getGender(), null,
        null, null, educationProviderContact.getBirthDate(), new LinkedList<>(), null, null, ssn,
        new HashSet<>(), addresses, phoneNumbers, "R".equals(sensitivityIndicator),
        "S".equals(sensitivityIndicator));
  }

  private String getZip(EducationProvider educationProvider) {
    String zip = educationProvider.getZipNumber().toString();
    if (educationProvider.getZipSuffixNumber() != null) {
      return educationProvider.getZipNumber() + "-" + educationProvider.getZipSuffixNumber();
    }
    return zip;
  }

}
