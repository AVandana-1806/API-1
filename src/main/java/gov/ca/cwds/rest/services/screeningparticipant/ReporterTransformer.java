package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.Reporter;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.IntakeLovType;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Business layer object to transform a {@link Reporter} to an {@link ParticipantIntakeApi}
 * 
 * @author CWDS API Team
 *
 */
public class ReporterTransformer implements ParticipantMapper<Reporter> {

  @Override
  public ParticipantIntakeApi tranform(Reporter reporter) {

    LegacyDescriptor legacyDescriptor = new LegacyDescriptor(reporter.getReferralId(), null,
        new DateTime(reporter.getLastUpdatedTime()), LegacyTable.REPORTER.getName(),
        LegacyTable.REPORTER.getDescription());

    String state = IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(
        reporter.getStateCodeType(), IntakeLovType.US_STATE.getValue());
    String streetAddress = reporter.getStreetNumber() + " " + reporter.getStreetName();
    List<AddressIntakeApi> addresses = Collections.singletonList(new AddressIntakeApi(null, null,
        streetAddress, reporter.getCity(), state, getZip(reporter), null, legacyDescriptor));
    Set<PhoneNumber> phoneNumbers = new HashSet<>(
        Arrays.asList(new PhoneNumber(null, reporter.getPrimaryPhoneNumber().toString(), null)));

    return new ParticipantIntakeApi(null, null, null, legacyDescriptor, reporter.getFirstName(),
        reporter.getMiddleInitialName(), reporter.getLastName(),
        reporter.getSuffixTitleDescription(), null, null, null, null, null, null,
        new LinkedList<>(), null, null, null, new HashSet<>(), addresses, phoneNumbers,
        Boolean.FALSE, Boolean.FALSE);
  }

  private String getZip(Reporter reporter) {
    return reporter.getZipNumber().toString();
    /**
     * This line can be added once the referrals started accepting zip suffix
     * 
     * if (reporter.getZipSuffixNumber() != null) { return reporter.getZipNumber() + "-" +
     * reporter.getZipSuffixNumber(); }
     */
  }

}
