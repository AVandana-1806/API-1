package gov.ca.cwds.fixture;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gov.ca.cwds.rest.api.domain.AllegationIntake;

/**
 * @author CWDS API Team
 *
 */
public class AllegationIntakeResourceBuilder {

  String id = "1";
  String legacyId = "";
  String legacySourceTable = "";
  String nonProtectingParent = "U";
  Set<String> types = Stream.of("General neglect").collect(Collectors.toSet());;
  String county = "99";
  long victimPersonId = 5432l;

  /**
   * @return the AllegationIntake
   */
  public AllegationIntake build() {
    AllegationIntake allegation = new AllegationIntake();
    allegation.setId(id);
    allegation.setLegacyId(legacyId);
    allegation.setLegacySourceTable(legacySourceTable);
    allegation.setNonProtectingParent(nonProtectingParent);
    allegation.setTypes(types);
    allegation.setCounty(county);
    allegation.setVictimPersonId(victimPersonId);
    return allegation;
  }

  /**
   * @param id - id
   * @return the id
   */
  public AllegationIntakeResourceBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public AllegationIntakeResourceBuilder setLegacyId(String legacyId) {
    this.legacyId = legacyId;
    return this;
  }

  public AllegationIntakeResourceBuilder setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
    return this;
  }

  public AllegationIntakeResourceBuilder setNonProtectingParent(String nonProtectingParent) {
    this.nonProtectingParent = nonProtectingParent;
    return this;
  }

  public AllegationIntakeResourceBuilder setTypes(Set<String> types) {
    this.types = types;
    return this;
  }

  public AllegationIntakeResourceBuilder setCounty(String county) {
    this.county = county;
    return this;
  }

  public AllegationIntakeResourceBuilder setVictimPersonId(Long victimPersonId) {
    this.victimPersonId = victimPersonId;
    return this;
  }

}
