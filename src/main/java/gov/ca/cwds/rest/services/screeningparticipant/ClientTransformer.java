package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.submit.Gender;

/**
 * @author CWDS API Team
 *
 */
public class ClientTransformer implements ParticipantMapper {

  @Override
  public ParticipantIntakeApi tranform(CmsPersistentObject object) {
    Client client = (Client) object;
    RaceAndEthnicityConverter raceAndEthnicityConverter = new RaceAndEthnicityConverter();
    AddressConverter addressConverter = new AddressConverter();
    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(client.getId(), null, new DateTime(client.getLastUpdatedTime()),
            LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());

    String gender = StringUtils.isNotBlank(client.getGenderCode())
        ? (Gender.findByCmsDescription(client.getGenderCode().toUpperCase())).getNsDescription()
        : null;

    List<String> languages = setLanguages(client);
    String races = raceAndEthnicityConverter.createRace(client);
    String hispanic = raceAndEthnicityConverter.createHispanic(client);
    Set<AddressIntakeApi> addresses = new HashSet<>(addressConverter.convert(client));
    addresses = Collections.unmodifiableSet(addresses);

    return new ParticipantIntakeApi(null, LegacyTable.CLIENT.getName(), client.getId(),
        legacyDescriptor, client.getFirstName(), client.getMiddleName(), client.getLastName(),
        client.getNameSuffix(), gender, null, null, setSsn(client), client.getBirthDate(),
        languages, races, hispanic, null, new HashSet<>(), addresses, null,
        setSealedIndicator(client), setSensitivieIndicator(client));
  }

  private String setSsn(Client client) {
    String ssn = client.getSocialSecurityNumber();
    if (StringUtils.isNotBlank(ssn) && ssn != "0") {
      StringBuilder builder = new StringBuilder(client.getSocialSecurityNumber());
      builder.insert(3, "-");
      builder.insert(6, "-");
      return builder.toString();
    }
    return ssn;
  }

  private List<String> setLanguages(Client client) {
    List<String> languages = new LinkedList<>();
    if (client.getPrimaryLanguageType() == 0) {
      return languages;
    } else if (client.getPrimaryLanguageType() != 0 && client.getSecondaryLanguageType() != 0) {
      return new LinkedList<>(Arrays.asList(
          IntakeCodeCache.global()
              .getIntakeCodeForLegacySystemCode(client.getPrimaryLanguageType()),
          IntakeCodeCache.global()
              .getIntakeCodeForLegacySystemCode(client.getSecondaryLanguageType())));
    } else if (client.getPrimaryLanguageType() != 0 && client.getSecondaryLanguageType() == 0) {
      return new LinkedList<>(Arrays.asList(IntakeCodeCache.global()
          .getIntakeCodeForLegacySystemCode(client.getPrimaryLanguageType())));
    }
    return languages;
  }

  private Boolean setSealedIndicator(Client client) {
    Boolean sealed = Boolean.FALSE;
    if ("R".equals(client.getSensitivityIndicator())) {
      return Boolean.TRUE;
    }
    return sealed;
  }

  private Boolean setSensitivieIndicator(Client client) {
    Boolean sensitive = Boolean.FALSE;
    if ("S".equals(client.getSensitivityIndicator())) {
      return Boolean.TRUE;
    }
    return sensitive;
  }

}
