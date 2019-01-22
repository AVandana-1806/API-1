package gov.ca.cwds.rest.services.screeningparticipant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.ServiceException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transforms the Legacy race and ethnicity values for an existing people into valid {@link
 * ParticipantIntakeApi} race and hispanic.
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
   * @param client - client
   * @return the intake race
   */
  public String createRace(Client client) {
    List<IntakeRace> intakeRaceList = new ArrayList<>();
    List<Short> systemIds = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    String stringRace = null;

    systemIds.add(client.getPrimaryEthnicityType());
    client.getClientScpEthnicities().forEach(race -> systemIds.add(race.getEthnicity()));

    for (Short id : systemIds) {
      final gov.ca.cwds.rest.api.domain.cms.SystemCode systemCode =
          SystemCodeCache.global().getSystemCode(id);
      if (systemCode != null && !(HISPANIC_CODE_OTHER_ID.equals(systemCode.getOtherCd())
          && !CARIBBEAN_RACE_CODE.equals(id))) {
        String shortDescrption = systemCode.getShortDescription();
        if (StringUtils.isNotBlank(shortDescrption)) {
          String race =
              IntakeCodeConverter.IntakeRaceCode.findByLegacyValue(shortDescrption).getRace();
          String raceDetail =
              IntakeCodeConverter.IntakeRaceCode.findByLegacyValue(shortDescrption).getRaceDetail();
          intakeRaceList.add(new IntakeRace(race, raceDetail));
        }
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

  /**
   * @param client - client
   * @return the intake hispanic
   */
  public String createHispanic(Client client) {
    List<IntakeEthnicity> intakeHispanicList = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    String stringHispanic = null;
    List<Short> systemIds = new ArrayList<>();
    systemIds.add(client.getPrimaryEthnicityType());
    client.getClientScpEthnicities().forEach(race -> systemIds.add(race.getEthnicity()));
    List<String> hispanicDetails = new ArrayList<>();
    for (Short id : systemIds) {
      final gov.ca.cwds.rest.api.domain.cms.SystemCode systemCode =
          SystemCodeCache.global().getSystemCode(id);
      if (systemCode != null && (HISPANIC_CODE_OTHER_ID.equals(systemCode.getOtherCd())
          && (!CARIBBEAN_RACE_CODE.equals(id)))) {
        hispanicDetails.add(systemCode.getShortDescription());
      }
    }
    intakeHispanicList
        .add(new IntakeEthnicity(translateHispanicOriginCodes(client.getHispanicOriginCode()),
            hispanicDetails));
    try {
      stringHispanic = mapper.writeValueAsString(intakeHispanicList);
    } catch (JsonProcessingException e) {
      LOGGER.error("Unable to build the Ethnicity json", e);
      throw new ServiceException(e);
    }
    return stringHispanic;
  }

  private String translateHispanicOriginCodes(String code) {
    if (YES.equals(code)) {
      return "Yes";
    } else if (NO.equals(code)) {
      return "No";
    } else if (UNKNOWN.equals(code)) {
      return "Unknown";
    } else if (ABANDONED.equals(code)) {
      return "Abandoned";
    } else if (DECLINED_TO_ANSWER.equals(code)) {
      return "Declined to answer";
    } else {
      return null;
    }
  }
}
