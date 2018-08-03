package gov.ca.cwds.rest.api.domain.hoi;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

/**
 * Screening allegation.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("squid:S2160")
public class HOIAllegation extends ApiObjectIdentity implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("type")
  private SystemCodeDescriptor type;

  @JsonProperty("disposition")
  private SystemCodeDescriptor disposition;

  @JsonProperty("victim")
  private HOIVictim victim;

  @JsonProperty("perpetrator")
  private HOIPerpetrator perpetrator;

  @JsonProperty("legacy_descriptor")
  private LegacyDescriptor legacyDescriptor;

  /**
   * No-argument constructor
   */
  public HOIAllegation() {
    // No-argument constructor
  }

  /**
   * @param id - id
   * @param type - type
   * @param disposition - disposition
   * @param victim - victim
   * @param perpetrator - perpetrator
   * @param legacyDescriptor - legacyDescriptor
   */
  public HOIAllegation(String id, SystemCodeDescriptor type, SystemCodeDescriptor disposition,
      HOIVictim victim, HOIPerpetrator perpetrator, LegacyDescriptor legacyDescriptor) {
    super();
    this.id = id;
    this.type = type;
    this.disposition = disposition;
    this.victim = victim;
    this.perpetrator = perpetrator;
    this.legacyDescriptor = legacyDescriptor;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public SystemCodeDescriptor getType() {
    return type;
  }

  public void setType(SystemCodeDescriptor type) {
    this.type = type;
  }

  public SystemCodeDescriptor getDisposition() {
    return disposition;
  }

  public void setDisposition(SystemCodeDescriptor disposition) {
    this.disposition = disposition;
  }

  public LegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(LegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public HOIVictim getVictim() {
    return victim;
  }

  public void setVictim(HOIVictim victim) {
    this.victim = victim;
  }

  public HOIPerpetrator getPerpetrator() {
    return perpetrator;
  }

  public void setPerpetrator(HOIPerpetrator perpetrator) {
    this.perpetrator = perpetrator;
  }
}
