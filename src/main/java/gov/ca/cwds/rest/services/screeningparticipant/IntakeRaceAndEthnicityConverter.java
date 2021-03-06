package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.ArrayList;
import java.util.List;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.screeningparticipant.IntakeCodeConverter.IntakeRaceCode;

/**
 * Transforms the Legacy race and ethnicity values for clients into valid
 * {@link ParticipantIntakeApi}, including category races, like "white" and "hispanic."
 *
 * @author CWDS API Team
 */
public class IntakeRaceAndEthnicityConverter {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(IntakeRaceAndEthnicityConverter.class);

  private static final String HISPANIC_CODE_OTHER_ID = "02";
  private static final Short CARIBBEAN_RACE_CODE = 3162;
  private static final String YES = "Y";
  private static final String DECLINED_TO_ANSWER = "D";
  private static final String ABANDONED = "Z";
  private static final String UNKNOWN = "U";
  private static final String NO = "N";

  /**
   * Look up ethnicity by client id.
   * 
   * @param client - client
   * @return the intake race
   */
  public String createRace(Client client) {
    final List<IntakeRace> intakeRaceList = new ArrayList<>();
    final List<Short> systemIds = new ArrayList<>();
    final ObjectMapper mapper = new ObjectMapper();
    String stringRace = null;

    systemIds.add(client.getPrimaryEthnicityType());
    client.getClientScpEthnicities().forEach(race -> systemIds.add(race.getEthnicity()));

    for (Short id : systemIds) {
      final gov.ca.cwds.rest.api.domain.cms.SystemCode systemCode =
          SystemCodeCache.global().getSystemCode(id);
      if (systemCode != null && !(HISPANIC_CODE_OTHER_ID.equals(systemCode.getOtherCd())
          && !CARIBBEAN_RACE_CODE.equals(id))) {
        processRaceList(intakeRaceList, systemCode);
      }
    }

    try {
      stringRace = mapper.writeValueAsString(intakeRaceList);
    } catch (JsonProcessingException e) {
      LOGGER.error("Unable to build the race json", e);
      throw new ServiceException(e);
    }

    return stringRace;
  }

  private void processRaceList(List<IntakeRace> intakeRaceList, SystemCode systemCode) {
    final String shortDescrption = systemCode.getShortDescription();

    if (StringUtils.isNotBlank(shortDescrption)) {
      // SNAP-1004: NPE on "Other Race Unknown"
      final IntakeRaceCode raceCd =
          IntakeRaceCode.findByLegacyValue(shortDescrption);
      if (raceCd != null) {
        intakeRaceList.add(new IntakeRace(raceCd.getRace(), raceCd.getRaceDetail()));
      }
    }
  }

  /**
   * @param client - client
   * @return the intake hispanic
   */
  public String createHispanic(Client client) {
    final List<IntakeEthnicity> intakeHispanicList = new ArrayList<>();
    final ObjectMapper mapper = new ObjectMapper();
    String stringHispanic = null;

    final List<Short> systemIds = new ArrayList<>();
    systemIds.add(client.getPrimaryEthnicityType());
    client.getClientScpEthnicities().forEach(race -> systemIds.add(race.getEthnicity()));
    final List<String> hispanicDetails = new ArrayList<>();

    for (Short id : systemIds) {
      final gov.ca.cwds.rest.api.domain.cms.SystemCode systemCode =
          SystemCodeCache.global().getSystemCode(id);
      if (systemCode != null && (HISPANIC_CODE_OTHER_ID.equals(systemCode.getOtherCd())
          && (!CARIBBEAN_RACE_CODE.equals(id)))) {
        hispanicDetails.add(systemCode.getShortDescription());
      }
    }

    try {
      intakeHispanicList.add(new IntakeEthnicity(
          translateHispanicOriginCodes(client.getHispanicOriginCode()), hispanicDetails));
      stringHispanic = mapper.writeValueAsString(intakeHispanicList);
    } catch (JsonProcessingException e) {
      LOGGER.error("Unable to build the Ethnicity json", e);
      throw new ServiceException(e);
    }

    return stringHispanic;
  }

  private String translateHispanicOriginCodes(String code) {
    if (StringUtils.isEmpty(code)) {
      return null;
    }

    switch (code) {
      case UNKNOWN:
      case ABANDONED:
        return "Unknown";
      case YES:
        return "Yes";
      case NO:
        return "No";
      case DECLINED_TO_ANSWER:
        return "Declined to answer";
      default:
        return null;
    }
  }

}
