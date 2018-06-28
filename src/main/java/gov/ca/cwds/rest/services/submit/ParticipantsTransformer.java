package gov.ca.cwds.rest.services.submit;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.business.rules.CalendarEnum;

/**
 * Business layer object to transform NS {@link ParticipantIntakeApi} to {@link Participant}
 * 
 * @author CWDS API Team
 */
public class ParticipantsTransformer {

  /**
   * @param participantsIntake - participantsIntake
   * @return the participants
   */
  public Set<Participant> transform(Set<ParticipantIntakeApi> participantsIntake) {
    Set<Participant> participants = new HashSet<>();

    Boolean clientStaffPersonAdded = Boolean.FALSE;
    Boolean reporterConfidentialWaiver = Boolean.FALSE;
    String reporterEmployerName = "Employer Name";
    AddressTransformer addressTransformer = new AddressTransformer();
    RaceAndEthnicityTransformer raceAndEthnicityTransformer = new RaceAndEthnicityTransformer();

    for (ParticipantIntakeApi p : participantsIntake) {
      Set<Address> addresses = new HashSet<>();
      for (AddressIntakeApi addressIntake : p.getAddresses()) {
        addresses.add(addressTransformer.transform(addressIntake));
      }
      addresses = Collections.unmodifiableSet(addresses);
      String gender = StringUtils.isNotBlank(p.getGender())
          ? (Gender.findByNsDescription(p.getGender().toLowerCase())).getCmsDescription()
          : "";
      Long pid = Long.valueOf(p.getId());
      RaceAndEthnicity raceAndEthnicity = raceAndEthnicityTransformer.transform(p);
      String dob = DomainChef.cookDate(p.getDateOfBirth());
      String ssn = StringUtils.isNotBlank(p.getSsn()) ? p.getSsn().replaceAll("-", "") : "";

      Short primaryLanguage = 0;
      Short secondayLanguage = 0;
      if (p.getLanguages() != null && p.getLanguages().size() > 1) {
        secondayLanguage = getlanguageLegacyId(p.getLanguages().get(1));
        primaryLanguage = getlanguageLegacyId(p.getLanguages().get(0));
      } else if (p.getLanguages() != null && !p.getLanguages().isEmpty()) {
        primaryLanguage = getlanguageLegacyId(p.getLanguages().get(0));
      }

      Participant participant = new Participant(pid, p.getLegacySourceTable(), null,
          p.getLegacyDescriptor(), p.getFirstName(), p.getMiddleName(), p.getLastName(),
          p.getNameSuffix(), gender, ssn, dob, primaryLanguage, secondayLanguage,
          Integer.parseInt(p.getScreeningId()), reporterConfidentialWaiver, reporterEmployerName,
          clientStaffPersonAdded, setSensitivityIndicator(p), p.getApproximateAge(),
          setApproximateAgeUnit(p), p.getRoles(), addresses, raceAndEthnicity, p.getCsecs());
      participants.add(participant);
    }
    return participants;
  }

  private String setApproximateAgeUnit(ParticipantIntakeApi p) {
    String ageUnit = null;
    if (StringUtils.isNotBlank(p.getApproximateAgeUnits())) {
      ageUnit = CalendarEnum.lookUpByDescription(p.getApproximateAgeUnits()).getName();
    }
    return ageUnit;
  }

  private String setSensitivityIndicator(ParticipantIntakeApi p) {
    if (Boolean.TRUE.equals(p.getSensitive())) {
      return "S";
    } else if (Boolean.TRUE.equals(p.getSealed())) {
      return "R";
    }
    return "N";
  }

  private Short getlanguageLegacyId(String language) {
    return IntakeCodeCache.global().getLegacySystemCodeForIntakeCode("LANG_TPC", language);
  }

}
