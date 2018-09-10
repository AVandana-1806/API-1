package gov.ca.cwds.rest.services.hoi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOIReferral;
import gov.ca.cwds.rest.api.domain.hoi.HOIScreening;

class InvolvementHistoryData {

  private String screeningId = null;

  private HOIScreeningData hoiScreeningData;

  private List<HOIScreening> hoiScreenings = new ArrayList<>();

  private List<HOICase> hoiCases = new ArrayList<>();

  private List<HOIReferral> hoiReferrals = new ArrayList<>();

  InvolvementHistoryData(String screeningId) {
    this.screeningId = screeningId;
    this.hoiScreeningData = new HOIScreeningData(new HashSet<>());
  }

  InvolvementHistoryData(Collection<String> clientIds) {
    this.hoiScreeningData = new HOIScreeningData(clientIds);
  }

  String getScreeningId() {
    return screeningId;
  }

  HOIScreeningData getHoiScreeningData() {
    return hoiScreeningData;
  }

  List<HOIScreening> getHoiScreenings() {
    return hoiScreenings;
  }

  void setHoiScreenings(List<HOIScreening> hoiScreenings) {
    this.hoiScreenings = hoiScreenings;
  }

  List<HOICase> getHoiCases() {
    return hoiCases;
  }

  void setHoiCases(List<HOICase> hoiCases) {
    this.hoiCases = hoiCases;
  }

  List<HOIReferral> getHoiReferrals() {
    return hoiReferrals;
  }

  void setHoiReferrals(List<HOIReferral> hoiReferrals) {
    this.hoiReferrals = hoiReferrals;
  }

}
