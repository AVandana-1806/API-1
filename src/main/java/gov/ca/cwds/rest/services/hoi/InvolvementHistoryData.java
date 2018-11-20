package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;

/**
 * This class looks important but lacks any Javadoc comment, so it has one now. :-)
 * 
 * @author CWDS API Team
 */
public class InvolvementHistoryData {

  private String screeningId = null;

  private HOIScreeningData hoiScreeningData;

  private List<HOIScreening> hoiScreenings = new ArrayList<>();

  private List<HOICase> hoiCases = new ArrayList<>();

  private List<HOIReferral> hoiReferrals = new ArrayList<>();

  public InvolvementHistoryData(String screeningId) {
    this.screeningId = screeningId;
    this.hoiScreeningData = new HOIScreeningData(new HashSet<>());
  }

  public InvolvementHistoryData(Collection<String> clientIds) {
    this.hoiScreeningData = new HOIScreeningData(clientIds);
  }

  public String getScreeningId() {
    return screeningId;
  }

  public HOIScreeningData getHoiScreeningData() {
    return hoiScreeningData;
  }

  public List<HOIScreening> getHoiScreenings() {
    return hoiScreenings;
  }

  public void setHoiScreenings(List<HOIScreening> hoiScreenings) {
    this.hoiScreenings = hoiScreenings;
  }

  public List<HOICase> getHoiCases() {
    return hoiCases;
  }

  public void setHoiCases(List<HOICase> hoiCases) {
    this.hoiCases = hoiCases;
  }

  public List<HOIReferral> getHoiReferrals() {
    return hoiReferrals;
  }

  public void setHoiReferrals(List<HOIReferral> hoiReferrals) {
    this.hoiReferrals = hoiReferrals;
  }

}
