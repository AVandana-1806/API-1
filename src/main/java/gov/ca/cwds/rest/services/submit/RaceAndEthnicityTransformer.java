package gov.ca.cwds.rest.services.submit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.RaceAndEthnicity;
import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.screeningparticipant.IntakeCodeConverter;
import gov.ca.cwds.rest.services.screeningparticipant.IntakeEthnicity;
import gov.ca.cwds.rest.services.screeningparticipant.IntakeRace;

/**
 * Transforms the Intake race and ethnicity from the screening into a legacy supported values
 * {@link RaceAndEthnicity} for an valid participants.
 * 
 * @author CWDS API Team
 *
 */
public class RaceAndEthnicityTransformer {
  private static final Logger LOGGER = LoggerFactory.getLogger(RaceAndEthnicityTransformer.class);

  private static final String DECLINED_TO_ANSWER = "D";
  private static final String UNKNOWN = "U";
  private static final String NO = "N";
  private static final String YES = "Y";
  private static final String DEFAULT_VALUE = "X";
  private static final short UNABLE_TO_DETERMINE = (short) 6351; // Intake calls as 'Abandoned'

  /**
   * 
   */
  public RaceAndEthnicityTransformer() {
    // no-opt
  }

  /**
   * @param participantsIntake - participantsIntake
   * @return the raceAndEthnicity
   */
  public RaceAndEthnicity transform(ParticipantIntakeApi participantsIntake) {
    List<IntakeRace> intakeRace = null;
    IntakeEthnicity intakeEthnicity = null;
    RaceAndEthnicity raceAndEthnicity = new RaceAndEthnicity();
    List<Short> raceCodes = new ArrayList<>();
    List<Short> hispanicCodes = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    if (participantsIntake != null) {
      try {
        intakeRace = raceJsonBuilder(participantsIntake, mapper);
        intakeEthnicity = ethnicityJsonBuilder(participantsIntake, mapper);
      } catch (IOException e) {
        LOGGER.error("Unable to parse the race and Ethnicity", e);
        throw new ServiceException(e);
      }
      if (intakeRace != null) {
        buildRace(intakeRace, raceAndEthnicity, raceCodes);
      }
      if (intakeEthnicity != null) {
        buildEthnicity(intakeEthnicity, raceAndEthnicity, hispanicCodes);
      }
    }
    return raceAndEthnicity;
  }

  private List<IntakeRace> raceJsonBuilder(ParticipantIntakeApi participantsIntake,
      ObjectMapper mapper) throws IOException {
    List<IntakeRace> intakeRace = null;
    if (StringUtils.isNotBlank(participantsIntake.getRaces())) {
      intakeRace =
          mapper.readValue(participantsIntake.getRaces(), new TypeReference<List<IntakeRace>>() {});
    }
    return intakeRace;
  }

  private IntakeEthnicity ethnicityJsonBuilder(ParticipantIntakeApi participantsIntake,
       ObjectMapper mapper) throws IOException {
    IntakeEthnicity intakeEthnicity = null;
    if (StringUtils.isNotBlank(participantsIntake.getEthnicity())) {
      intakeEthnicity = mapper.readValue(participantsIntake.getEthnicity(), IntakeEthnicity.class);
    }
    return intakeEthnicity;
  }

  private void buildRace(List<IntakeRace> intakeRaces, RaceAndEthnicity raceAndEthnicity,
      List<Short> raceCodes) {
    for (IntakeRace intakeRace : intakeRaces) {
      raceCodes.add(getLegacySystemCodeForRace(SystemCodeCategoryId.ETHNICITY, intakeRace));
    }
    raceAndEthnicity.setRaceCode(raceCodes);
    if (raceCodes.contains(UNABLE_TO_DETERMINE)) {
      raceAndEthnicity.setUnableToDetermineCode("A");
    }
  }

  private void buildEthnicity(IntakeEthnicity intakeEthnicity, RaceAndEthnicity raceAndEthnicity,
      List<Short> hispanicCodes) {
    if (StringUtils.isBlank(intakeEthnicity.getHispanicLatinoOrigin())) {
      raceAndEthnicity.setHispanicOriginCode(DEFAULT_VALUE);
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Yes")) {
      setHispanicCodeForYes(intakeEthnicity, raceAndEthnicity, hispanicCodes);
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("No")) {
      raceAndEthnicity.setHispanicOriginCode(NO);
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Unknown")) {
      raceAndEthnicity.setHispanicOriginCode(UNKNOWN);
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Abandoned")) {
      setHispanicOriginForAbandoned(raceAndEthnicity, hispanicCodes);
      raceAndEthnicity.setHispanicUnableToDetermineCode("A");
    } else if (intakeEthnicity.getHispanicLatinoOrigin().contains("Declined to answer")) {
      raceAndEthnicity.setHispanicOriginCode(DECLINED_TO_ANSWER);
    }
  }

  private void setHispanicCodeForYes(IntakeEthnicity intakeEthnicity,
      RaceAndEthnicity raceAndEthnicity, List<Short> hispanicCodes) {
    raceAndEthnicity.setHispanicOriginCode(YES);
    if (!intakeEthnicity.getEthnicityDetail().isEmpty()) {
      hispanicCodes.add(SystemCodeCache.global().getSystemCodeId(
          intakeEthnicity.getEthnicityDetail().get(0), SystemCodeCategoryId.ETHNICITY));
      raceAndEthnicity.setHispanicCode(hispanicCodes);
    }
  }

  private void setHispanicOriginForAbandoned(RaceAndEthnicity raceAndEthnicity,
      List<Short> hispanicCodes) {
    hispanicCodes.add(UNABLE_TO_DETERMINE);
    raceAndEthnicity.setHispanicCode(hispanicCodes);
    raceAndEthnicity.setHispanicOriginCode("Z");
  }

  private Short getLegacySystemCodeForRace(String metaId, IntakeRace intakeRace) {
    Short sysId = null;
    IntakeCodeConverter.IntakeRaceCode intakeCodeCode =
        (intakeRace != null) ? IntakeCodeConverter.IntakeRaceCode.lookUpByIntakeRace(intakeRace)
            : null;
    if (intakeCodeCode != null && StringUtils.isNotBlank(intakeCodeCode.getLegacyValue())
        && StringUtils.isNotBlank(metaId)) {
      sysId = SystemCodeCache.global().getSystemCodeId(intakeCodeCode.getLegacyValue(), metaId);
    }
    return sysId;
  }

}
