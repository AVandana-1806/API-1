package gov.ca.cwds.rest.services.screeningparticipant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Transforms the Legacy race and ethnicity values for an existing people into valid
 * {@link ParticipantIntakeApi} race and hispanic.
 * 
 * @author CWDS API Team
 *
 */
public class IntakeRaceAndEthnicityConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(IntakeRaceAndEthnicityConverter.class);

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
    List<IntakeRaceAndEthnicity.IntakeRace> intakeRaceList = new ArrayList<>();
    List<Short> systemIds = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    String stringRace = null;

    systemIds.add(client.getPrimaryEthnicityType());
    client.getClientScpEthnicities().forEach(race -> systemIds.add(race.getEthnicity()));

    for (Short id : systemIds) {
      final gov.ca.cwds.rest.api.domain.cms.SystemCode systemCode =
          SystemCodeCache.global().getSystemCode(id);
      if (systemCode != null && !(HISPANIC_CODE_OTHER_ID.equals(systemCode.getOtherCd())
          && (!CARIBBEAN_RACE_CODE.equals(id)))) {
        String shortDescrption = systemCode.getShortDescription();
        if (StringUtils.isNotBlank(shortDescrption)) {
          String race = IntakeCodeConverter.IntakeRaceCode.findByLegacyValue(shortDescrption)
              .getRace();
          String raceDetail = IntakeCodeConverter.IntakeRaceCode
              .findByLegacyValue(shortDescrption).getRaceDetail();
          intakeRaceList.add(new IntakeRaceAndEthnicity.IntakeRace(race, raceDetail));
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
    List<IntakeRaceAndEthnicity.IntakeEthnicity> intakeHispanicList = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    String stringHispanic = null;

    final gov.ca.cwds.rest.api.domain.cms.SystemCode systemCode =
        SystemCodeCache.global().getSystemCode(client.getPrimaryEthnicityType());
    if (systemCode != null && HISPANIC_CODE_OTHER_ID.equals(systemCode.getOtherCd())
        && (!CARIBBEAN_RACE_CODE.equals(client.getPrimaryEthnicityType()))) {
      if (YES.equals(client.getHispanicOriginCode())) {
        intakeHispanicList.add(new IntakeRaceAndEthnicity.IntakeEthnicity("Yes",
            Arrays.asList(systemCode.getShortDescription())));
      }
    } else {
      buildOtherHispanicCodes(client, intakeHispanicList);
    }
    try {
      stringHispanic = mapper.writeValueAsString(intakeHispanicList);
    } catch (JsonProcessingException e) {
      LOGGER.error("Unable to build the Ethnicity json", e);
      throw new ServiceException(e);
    }
    return stringHispanic;
  }

  private void buildOtherHispanicCodes(Client client,
      List<IntakeRaceAndEthnicity.IntakeEthnicity> intakeHispanicList) {
    if (client.getPrimaryEthnicityType() == 0 && YES.equals(client.getHispanicOriginCode())) {
      intakeHispanicList.add(new IntakeRaceAndEthnicity.IntakeEthnicity("Yes", Arrays.asList()));
    } else if (NO.equals(client.getHispanicOriginCode())) {
      intakeHispanicList.add(new IntakeRaceAndEthnicity.IntakeEthnicity("No", Arrays.asList()));
    } else if (UNKNOWN.equals(client.getHispanicOriginCode())) {
      intakeHispanicList
          .add(new IntakeRaceAndEthnicity.IntakeEthnicity("Unknown", Arrays.asList()));
    } else if (ABANDONED.equals(client.getHispanicOriginCode())) {
      intakeHispanicList
          .add(new IntakeRaceAndEthnicity.IntakeEthnicity("Abandoned", Arrays.asList()));
    } else if (DECLINED_TO_ANSWER.equals(client.getHispanicOriginCode())) {
      intakeHispanicList
          .add(new IntakeRaceAndEthnicity.IntakeEthnicity("Declined to answer", Arrays.asList()));
    } else {
      intakeHispanicList.add(new IntakeRaceAndEthnicity.IntakeEthnicity(null, Arrays.asList()));
    }
  }
}
