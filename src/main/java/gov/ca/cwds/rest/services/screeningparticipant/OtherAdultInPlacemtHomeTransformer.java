package gov.ca.cwds.rest.services.screeningparticipant;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.IntakeLovType;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Business layer object to transform a {@link OtherAdultInPlacemtHome} to an
 * {@link ParticipantIntakeApi}
 * 
 * @author CWDS API Team
 *
 */
public class OtherAdultInPlacemtHomeTransformer
    implements ParticipantMapper<OtherAdultInPlacemtHome> {

  @Override
  public ParticipantIntakeApi tranform(OtherAdultInPlacemtHome otherAdultInPlacemtHome) {

    LegacyDescriptor otherAdultLegacyDescriptor =
        new LegacyDescriptor(otherAdultInPlacemtHome.getId(), null,
            new DateTime(otherAdultInPlacemtHome.getLastUpdatedTime()),
            LegacyTable.ADULT_IN_PLACEMENT_HOME.getName(),
            LegacyTable.ADULT_IN_PLACEMENT_HOME.getDescription());

    String firstName = StringUtils.isNotBlank(otherAdultInPlacemtHome.getName())
        ? getFirstName(otherAdultInPlacemtHome)
        : null;
    String lastName = StringUtils.isNotBlank(otherAdultInPlacemtHome.getName())
        ? getLastName(otherAdultInPlacemtHome)
        : otherAdultInPlacemtHome.getName();

    PlacementHome placementHome = otherAdultInPlacemtHome.getPlacementHome();
    String streetAddress = placementHome.getStreetNo() + " " + placementHome.getStreetNm();
    String state = IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(
        placementHome.getStateCode(), IntakeLovType.US_STATE.getValue());

    LegacyDescriptor placemtHomeLegacyDescriptor = new LegacyDescriptor(
        placementHome.getIdentifier(), null,
        new org.joda.time.DateTime(
            placementHome.getLastUpdateTime().withNano(0).format(DateTimeFormatter.ISO_DATE_TIME)),
        LegacyTable.PLACEMENT_HOME.getName(), LegacyTable.PLACEMENT_HOME.getDescription());

    List<AddressIntakeApi> addresses = Collections
        .singletonList(new AddressIntakeApi(null, null, streetAddress, placementHome.getCityNm(),
            state, getZip(placementHome), null, placemtHomeLegacyDescriptor));

    return new ParticipantIntakeApi(null, null, null, otherAdultLegacyDescriptor, firstName, null,
        lastName, null, otherAdultInPlacemtHome.getGenderCode(), null, null, null,
        otherAdultInPlacemtHome.getBirthDate(), otherAdultInPlacemtHome.getDeathDate(),
        new LinkedList<>(), null, null, null, new HashSet<>(), addresses, null, Boolean.FALSE,
        Boolean.FALSE);
  }

  private String getFirstName(OtherAdultInPlacemtHome otherAdultInPlacemtHome) {
    String fullName = otherAdultInPlacemtHome.getName();
    if (!fullName.contains(" ")) {
      return null;
    }
    return StringUtils.split(fullName, " ", 2)[0];
  }

  private String getLastName(OtherAdultInPlacemtHome otherAdultInPlacemtHome) {
    String fullName = otherAdultInPlacemtHome.getName();
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
