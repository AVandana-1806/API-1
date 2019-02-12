package gov.ca.cwds.rest.services.screeningparticipant;

public enum PhoneType {
  HOME("Home"),
  WORK("Work"),
  CELL("Cell"),
  EMERGENCY("Emergency");

  private String label;

  PhoneType(String label) {
    this.label = label;
  }


  @Override
  public String toString() {
    return label;
  }
}
