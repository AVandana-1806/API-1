package gov.ca.cwds.rest.services.screeningparticipant;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;

import com.google.inject.Inject;
import gov.ca.cwds.data.legacy.cms.dao.PlacementEpisodeDao;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.IntakeLovType;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import gov.ca.cwds.rest.services.submit.Gender;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * @author CWDS API Team
 */
public class ClientTransformer implements ParticipantMapper<Client> {

  @Inject
  private AuthorizationService authorizationService;

  @Inject
  private PlacementEpisodeDao placementEpisodeDao;

  @Override
  public ParticipantIntakeApi tranform(Client client) {
    // Ensure Client are authorized
    String clientId = client.getId();
    authorizationService.ensureClientAccessAuthorized(clientId);
    IntakeRaceAndEthnicityConverter intakeRaceAndEthnicityConverter =
        new IntakeRaceAndEthnicityConverter();

    IntakeAddressConverter intakeAddressConverter = new IntakeAddressConverter();
    List<PlacementEpisode> placementEpisodes = placementEpisodeDao.findByClientId(clientId);
    List<AddressIntakeApi> addresses =
        intakeAddressConverter.getPlacementHomeAddresses(placementEpisodes);

    LegacyDescriptor legacyDescriptor =
        new LegacyDescriptor(clientId, null, new DateTime(client.getLastUpdatedTime()),
            LegacyTable.CLIENT.getName(), LegacyTable.CLIENT.getDescription());
    String gender = StringUtils.isNotBlank(client.getGenderCode())
        ? (Gender.findByCmsDescription(client.getGenderCode().toUpperCase())).getNsDescription()
        : null;

    List<String> languages = getLanguages(client);
    String races = intakeRaceAndEthnicityConverter.createRace(client);
    String hispanic = intakeRaceAndEthnicityConverter.createHispanic(client);
    addresses.addAll(intakeAddressConverter.convert(client));

    IntakePhoneConverter intakePhoneConverter = new IntakePhoneConverter();
    List<PhoneNumber> phones = new ArrayList<>();
    phones.addAll(intakePhoneConverter.getPlacementHomePhones(placementEpisodes));
    phones.addAll(intakePhoneConverter.convert(client));

    Date clientDob = client.getBirthDate();
    String approxAge = null;
    String approxAgeUnits = null;
    if (clientDob != null && "Y".equals(client.getEstimatedDobCode())) {
      String approxAgeAndUnits = calcApproximateAgeAndUnits(clientDob);
      int idx = approxAgeAndUnits.indexOf(':');
      approxAge = approxAgeAndUnits.substring(0, idx);
      approxAgeUnits = approxAgeAndUnits.substring(idx + 1);
      clientDob = null;
    }

    return new ParticipantIntakeApi(null, null, null, legacyDescriptor, client.getFirstName(),
        client.getMiddleName(), client.getLastName(), client.getNameSuffix(), gender, approxAge,
        approxAgeUnits, convertSSN(client), clientDob, client.getDeathDate(), languages,
        races, hispanic, null, new HashSet<>(), addresses, phones,
        getSealedIndicator(client), getSensitivieIndicator(client));
  }

  /*
   * This code is temporary and will be removed after resolution of an issue that is mentioned in this comment:
   * https://osi-cwds.atlassian.net/browse/HOT-2447?focusedCommentId=105060&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel#comment-105060
   *
   * So no rules were requested from PO about how exactly the estimated DOB should be converted to Approximate Age and Units.
   * This coding is done based on developer's gut feeling as a temporary solution.
   */
  private String calcApproximateAgeAndUnits(Date dob) {
    LocalDate localDob = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate localToday = LocalDate.now();

    long days = DAYS.between(localDob, localToday);
    if (days < 7) {
      return String.valueOf(days) + ":days";
    }

    long weeks = WEEKS.between(localDob, localToday);
    if (weeks < 8 && weeks % 4 != 0) {
      return String.valueOf(weeks) + ":weeks";
    }

    long months = MONTHS.between(localDob, localToday);
    if (months < 12) {
      return String.valueOf(months) + ":months";
    }

    long years = YEARS.between(localDob, localToday);
    return String.valueOf(years) + ":years";
  }

  private String convertSSN(Client client) {
    String ssn = client.getSocialSecurityNumber();
    if ((!"0".equals(ssn)) && StringUtils.isNotBlank(ssn)) {
      StringBuilder builder = new StringBuilder(client.getSocialSecurityNumber());
      builder.insert(3, "-");
      builder.insert(6, "-");
      return builder.toString();
    }
    return ssn;
  }

  private List<String> getLanguages(Client client) {
    List<String> languages = new LinkedList<>();
    if (client.getPrimaryLanguageType() == 0) {
      return languages;
    } else if (client.getPrimaryLanguageType() != 0 && client.getSecondaryLanguageType() != 0) {
      return new LinkedList<>(Arrays.asList(
          IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(client.getPrimaryLanguageType(),
              IntakeLovType.LANGUAGE.getValue()),
          IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(
              client.getSecondaryLanguageType(), IntakeLovType.LANGUAGE.getValue())));
    } else if (client.getPrimaryLanguageType() != 0 && client.getSecondaryLanguageType() == 0) {
      return new LinkedList<>(
          Collections.singletonList(IntakeCodeCache.global().getIntakeCodeForLegacySystemCode(
              client.getPrimaryLanguageType(), IntakeLovType.LANGUAGE.getValue())));
    }
    return languages;
  }

  private Boolean getSealedIndicator(Client client) {
    return "R".equals(client.getSensitivityIndicator());
  }

  private Boolean getSensitivieIndicator(Client client) {
    return "S".equals(client.getSensitivityIndicator());
  }

  /**
   * @param authorizationService - authorizationService
   */
  void setAuthorizationService(AuthorizationService authorizationService) {
    this.authorizationService = authorizationService;
  }

  /**
   * @param placementEpisodeDao - placementEpisodeDao
   */
  void setPlacementEpisodeDao(PlacementEpisodeDao placementEpisodeDao) {
    this.placementEpisodeDao = placementEpisodeDao;
  }
}
