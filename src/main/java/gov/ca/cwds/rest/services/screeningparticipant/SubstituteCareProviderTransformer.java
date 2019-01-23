package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.IntakeLovType;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Business layer object to transform a {@link SubstituteCareProvider} to an
 * {@link ParticipantIntakeApi}
 * 
 * @author CWDS API Team
 *
 */
public class SubstituteCareProviderTransformer
    implements ParticipantMapper<SubstituteCareProvider> {

  @Override
  public ParticipantIntakeApi tranform(SubstituteCareProvider substituteCareProvider) {

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(substituteCareProvider.getId(), null,
        new DateTime(substituteCareProvider.getLastUpdatedTime()),
        LegacyTable.SUBSTITUTE_CARE_PROVIDER.getName(),
        LegacyTable.SUBSTITUTE_CARE_PROVIDER.getDescription());

    String state = IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(
        substituteCareProvider.getStateCodeType(), IntakeLovType.US_STATE.getValue());
    String streetAddress =
        substituteCareProvider.getStreetNumber() + " " + substituteCareProvider.getStreetName();
    List<AddressIntakeApi> addresses = Collections.singletonList(
        new AddressIntakeApi(null, null, streetAddress, substituteCareProvider.getCityName(), state,
            getZip(substituteCareProvider), null, legacyDescriptor));
    String sensitivityIndicator = substituteCareProvider.getSensitivityIndicator() != null
        ? substituteCareProvider.getSensitivityIndicator()
        : "";

    Set<PhoneNumber> phoneNumbers = new HashSet<>(Arrays.asList(
        new PhoneNumber(substituteCareProvider.getAdditionalPhoneNumber().toString(), null)));

    return new ParticipantIntakeApi(null, null, null, legacyDescriptor,
        substituteCareProvider.getFirstName(), substituteCareProvider.getMiddleName(),
        substituteCareProvider.getLastName(), substituteCareProvider.getSuffixTitleDescription(),
        null, null, null, substituteCareProvider.getSsn(), substituteCareProvider.getBirthDate(),
        substituteCareProvider.getDeathDate(), new LinkedList<>(), null, null, null,
        new HashSet<>(), addresses, phoneNumbers, "R".equals(sensitivityIndicator),
        "S".equals(sensitivityIndicator));
  }

  private String getZip(SubstituteCareProvider substituteCareProvider) {
    return substituteCareProvider.getZipNumber().toString();
    /**
     * This line can be added once the referrals started accepting zip suffix
     * 
     * if (substituteCareProvider.getZipSuffixNumber() != null) { return
     * substituteCareProvider.getZipNumber() + "-" + substituteCareProvider.getZipSuffixNumber(); }
     */
  }

}
