package gov.ca.cwds.rest.services.screeningparticipant;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.submit.Gender;

/**
 * Business layer object to transform a {@link OtherChildInPlacemtHome} to an
 * {@link ParticipantIntakeApi}
 * 
 * @author CWDS API Team
 *
 */
public class OtherChildInPlacemtHomeTransformer
    implements ParticipantMapper<OtherChildInPlacemtHome> {

  @Override
  public ParticipantIntakeApi tranform(OtherChildInPlacemtHome otherChildInPlacemtHome) {

    LegacyDescriptor otherChildLegacyDescriptor =
        new LegacyDescriptor(otherChildInPlacemtHome.getId(), null,
            new DateTime(otherChildInPlacemtHome.getLastUpdatedTime()),
            LegacyTable.CHILD_IN_PLACEMENT_HOME.getName(),
            LegacyTable.CHILD_IN_PLACEMENT_HOME.getDescription());

    String firstName = StringUtils.isNotBlank(otherChildInPlacemtHome.getName())
        ? getFirstName(otherChildInPlacemtHome)
        : null;
    String lastName = StringUtils.isNotBlank(otherChildInPlacemtHome.getName())
        ? getLastName(otherChildInPlacemtHome)
        : otherChildInPlacemtHome.getName();
    String gender =
        StringUtils.isNotBlank(otherChildInPlacemtHome.getGenderCode())
            ? (Gender.findByCmsDescription(otherChildInPlacemtHome.getGenderCode().toUpperCase()))
                .getNsDescription()
            : null;

    PlacementHome placementHome = otherChildInPlacemtHome.getPlacementHome();
    String streetAddress = placementHome.getStreetNo() + " " + placementHome.getStreetNm();
    String state =
        IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(placementHome.getStateCode());

    LegacyDescriptor placemtHomeLegacyDescriptor = new LegacyDescriptor(
        placementHome.getIdentifier(), null,
        new org.joda.time.DateTime(
            placementHome.getLastUpdateTime().withNano(0).format(DateTimeFormatter.ISO_DATE_TIME)),
        LegacyTable.PLACEMENT_HOME.getName(), LegacyTable.PLACEMENT_HOME.getDescription());

    Set<AddressIntakeApi> addresses = new HashSet<>(
        Arrays.asList(new AddressIntakeApi(null, null, streetAddress, placementHome.getCityNm(),
            state, getZip(placementHome), null, placemtHomeLegacyDescriptor)));
    addresses = Collections.unmodifiableSet(addresses);

    return new ParticipantIntakeApi(null, null, null, otherChildLegacyDescriptor, firstName, null,
        lastName, null, gender, null, null, null, otherChildInPlacemtHome.getBirthDate(),
        otherChildInPlacemtHome.getDeathDate(), new LinkedList<>(), null, null, null,
        new HashSet<>(), addresses, null, Boolean.FALSE, Boolean.FALSE);
  }

  private String getFirstName(OtherChildInPlacemtHome otherChildInPlacemtHome) {
    String fullName = otherChildInPlacemtHome.getName();
    if (!fullName.contains(" ")) {
      return null;
    }
    return StringUtils.split(fullName, " ", 2)[0];
  }

  private String getLastName(OtherChildInPlacemtHome otherChildInPlacemtHome) {
    String fullName = otherChildInPlacemtHome.getName();
    if (!fullName.contains(" ")) {
      return fullName;
    }
    return StringUtils.split(fullName, " ", 2)[1];
  }

  private String getZip(PlacementHome placementHome) {
    return placementHome.getZipNo();
    /**
     * This line can be added once the referrals started accepting zip suffix
     * 
     * if (placementHome.getZipSfxNo() != null) { return placementHome.getZipNo() + "-" +
     * placementHome.getZipSfxNo(); } return zip;
     */
  }
}
