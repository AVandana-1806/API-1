package gov.ca.cwds.rest.services.submit;

import static org.junit.Assert.*;

import gov.ca.cwds.rest.api.domain.Screening;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class SafetyAlertsInformationTest {
  Screening screening;
  SafetyAlertsInformation safetyAlertsInformation;

  @Before
  public void setUp(){
    screening = new Screening();
    Set<String> safetyAlerts = new HashSet();
    safetyAlerts.add("first alert");
    screening.setSafetyAlerts(safetyAlerts);

    String safetyInformation = "first safety information";
    screening.setSafetyInformation(safetyInformation);

    safetyAlertsInformation = new SafetyAlertsInformation();
  }

  @Test
  public void shouldConcatSafetyAlertsAndSafetyInformationWhenBothHaveValues(){
    String safetyAlertInfo = safetyAlertsInformation.buildSafetyAlertsInfo(screening);

    assertNotNull(safetyAlertInfo);
    assertEquals("first alert,first safety information", safetyAlertInfo);
  }

  @Test
  public void shouldReturnSafetyInformationWhenSafetyAlertsIsNullAndSafetyInformationHasAValue(){
    screening.setSafetyAlerts(null);

    String safetyAlertInfo = safetyAlertsInformation.buildSafetyAlertsInfo(screening);
    assertNotNull(safetyAlertInfo);
    assertEquals("first safety information", safetyAlertInfo);

  }

  @Test
  public void shouldReturnSafetyInformationWhenSafetyAlertsIsEmptyAndSafetyInformationHasAValue(){
    screening.setSafetyAlerts(new HashSet());

    String safetyAlertInfo = safetyAlertsInformation.buildSafetyAlertsInfo(screening);
    assertEquals("first safety information", safetyAlertInfo);
  }

  @Test
  public void shouldReturnSafetyAlertWhenNoSafetyInformationExists(){
    screening.setSafetyInformation("");
    String safetyAlertInfo = safetyAlertsInformation.buildSafetyAlertsInfo(screening);

    assertEquals("first alert", safetyAlertInfo);
  }

  @Test
  public void shouldReturnConcatenatedSafetyAlertsWhenNoSafetyInformationExists(){
    Set<String> safetyAlerts = new HashSet();
    safetyAlerts.add("first alert");
    safetyAlerts.add("second alert");
    screening.setSafetyAlerts(safetyAlerts);
    screening.setSafetyInformation("");

    String safetyAlertInfo = safetyAlertsInformation.buildSafetyAlertsInfo(screening);
    assertEquals("first alert,second alert", safetyAlertInfo);
  }

  @Test
  public void shouldReturnNullIfNoSafetyAlertOrInformationIsPresent(){
    screening.setSafetyAlerts(null);
    screening.setSafetyInformation("");

    String safetyAlertInfo = safetyAlertsInformation.buildSafetyAlertsInfo(screening);

    assertNull(safetyAlertInfo);
  }
}