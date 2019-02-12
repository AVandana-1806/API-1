package gov.ca.cwds.rest.services.screeningparticipant;

import gov.ca.cwds.data.legacy.cms.entity.OutOfHomePlacement;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.enums.AddressType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Intake Address Converter transforms the legacy {@link Address} into a intake {@link
 * AddressIntakeApi}, From legacy find the recent open address residence and placement home for the
 * client.
 *
 * @author CWDS API Team
 */
public class IntakePhoneConverter extends IntakeConverter {

  private static final Short RESIDENCE = AddressType.HOME.getCode();
  private static final Short RESIDENCE_2 = AddressType.RESIDENCE_2.getCode();
  private static final Short BUSINESS = AddressType.WORK.getCode();

  /**
   * @param client - client
   * @return the phoneNumbers
   */
  public List<PhoneNumber> convert(Client client) {
    List<PhoneNumber> phones = new ArrayList<>();
    if (client.getClientAddress() != null) {
      List<ClientAddress> clientAddressList = convertPersonalData(client);
      clientAddressList.forEach(clientAddress -> phones.addAll(getPhones(clientAddress)));
    }
    return phones;
  }

  /**
   * @param placementEpisodes - placementEpisodes
   * @return the client placementHome phoneNumbers
   */
  public List<PhoneNumber> getPlacementHomePhones(
      List<PlacementEpisode> placementEpisodes) {
    List<PhoneNumber> phones = new ArrayList<>();

    LocalDate now = LocalDate.now();
    placementEpisodes.stream().forEach(placementEpisode -> {
      List<OutOfHomePlacement> outOfHomePlacements = placementEpisode.getOutOfHomePlacements()
          .stream()
          .filter(
              outOfHomePlacement -> ObjectUtils.compare(outOfHomePlacement.getStartDt(), now) <= 0
                  && ObjectUtils.compare(outOfHomePlacement.getEndDt(), now, true) >= 0)
          .collect(Collectors.toList());

      for (OutOfHomePlacement outOfHomePlacement : outOfHomePlacements) {
        phones.addAll(getPhones(outOfHomePlacement.getPlacementHome()));
      }
    });
    return phones;
  }

  private PhoneNumber toPhoneNumber(String phoneNumber, String phoneExtension,
      PhoneType phoneType) {
    return phoneExtension == null || phoneExtension.isEmpty() || "0".equals(phoneExtension.trim())
        ? new PhoneNumber(phoneNumber.trim(), phoneType.name())
        : new PhoneNumber(phoneNumber.trim(), phoneExtension.trim(), phoneType.name());
  }

  List<PhoneNumber> getPhones(PlacementHome placementHome) {
    List<PhoneNumber> phones = new ArrayList<>();
    if (!(placementHome == null || placementHome.getPrmTelNo() == null || placementHome
        .getPrmTelNo().isEmpty())) {
      phones.add(
          toPhoneNumber(placementHome.getPrmTelNo(), placementHome.getPrmExtNo(), PhoneType.Home));
    }
    return phones;
  }

  List<PhoneNumber> getPhones(ClientAddress clientAddress) {
    Address address = clientAddress.getAddresses();
    final List<PhoneNumber> ret = new ArrayList<>();

    if (isNumberPopulated(address.getPrimaryNumber())) {
      PhoneType phoneType = PhoneType.Emergency;
      if (RESIDENCE.equals(clientAddress.getAddressType())
          || RESIDENCE_2.equals(clientAddress.getAddressType())) {
        phoneType = PhoneType.Home;
      } else if (BUSINESS.equals(clientAddress.getAddressType())) {
        phoneType = PhoneType.Work;
      }
      ret.add(toPhoneNumber(String.valueOf(address.getPrimaryNumber()),
          isNumberPopulated(address.getPrimaryExtension()) ? address.getPrimaryExtension()
              .toString() : null,
          phoneType));
    }
    if (isNumberPopulated(address.getMessageNumber())) {
      ret.add(toPhoneNumber(String.valueOf(address.getMessageNumber()),
          isNumberPopulated(address.getMessageExtension()) ? address.getMessageExtension()
              .toString() : null,
          PhoneType.Cell));
    }
    if (isNumberPopulated(address.getEmergencyNumber())) {
      ret.add(toPhoneNumber(String.valueOf(address.getEmergencyNumber()),
          isNumberPopulated(address.getEmergencyExtension()) ? address.getEmergencyExtension()
              .toString()
              : null,
          PhoneType.Emergency));
    }
    return ret;
  }


  private boolean isNumberPopulated(Number n) {
    return n != null && n.intValue() != 0;
  }
}
