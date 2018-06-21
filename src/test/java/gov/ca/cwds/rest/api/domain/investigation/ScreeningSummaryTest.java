package gov.ca.cwds.rest.api.domain.investigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

@SuppressWarnings("javadoc")
public class ScreeningSummaryTest {

  private String additionalInformation = "there was excessive evidence of abuse";
  private String decision = "promoteToReferral";
  private String decisionDetail = "immediate";
  private String name = "henderson screening";
  private Set<String> safetyAlerts = validSafetyAletrs();
  private Set<SimpleAllegation> allegations = new HashSet<>();
  private Date startedAt = DomainChef.uncookStrictTimestampString("2017-09-01T16:48:05.457-0000");
  private String safetyInformation = "the animal at residence is a lion";
  private String id = "1";
  private Set<SimpleAllegation> simpleAllegations = new HashSet<>();
  private String[] allegationType = new String[2];


  private Set<String> validSafetyAletrs() {
    Set<String> safetyAlerts = new HashSet<String>();
    safetyAlerts.add("dangerous animal on premises");
    safetyAlerts.add("firearms in home");
    safetyAlerts.add("severe mental health status");
    return safetyAlerts;
  }

  @Before
  public void setup() {
    allegationType[0] = "allegation 1";
    allegationType[1] = "allegation 2";
    gov.ca.cwds.data.persistence.ns.Allegation allegation =
        new gov.ca.cwds.data.persistence.ns.Allegation("1234567ABC", "screening id", "perpetrator",
            "victim", "10-30-2017", "10-30-2017", allegationType);

    SimpleAllegation simpleAllegation = new SimpleAllegation(allegation);

    simpleAllegations.add(simpleAllegation);
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(ScreeningSummary.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    ScreeningSummary domain = new ScreeningSummary(id, name, decision, decisionDetail, safetyAlerts,
        safetyInformation, additionalInformation, startedAt, simpleAllegations);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getName(), is(equalTo(name)));
    assertThat(domain.getDecision(), is(equalTo(decision)));
    assertThat(domain.getDecisionDetail(), is(equalTo(decisionDetail)));
    assertThat(domain.getSafetyAlerts(), is(equalTo(safetyAlerts)));
    assertThat(domain.getSafetyInformation(), is(equalTo(safetyInformation)));
    assertThat(domain.getAdditionalInformation(), is(equalTo(additionalInformation)));
    assertThat(domain.getStartedAt(), is(equalTo(startedAt)));
    assertThat(domain.getAllegations(), is(equalTo(simpleAllegations)));
  }

  @Test
  public void testConstructorUsingScreening()
      throws JsonParseException, JsonMappingException, IOException {

    String referrence = "screeing referrence";
    LocalDateTime endedAt = LocalDateTime.now();
    String incidentCounty = "20";
    LocalDate incidentDate = LocalDate.now();
    String locationType = "";
    String communicationMethod = "1234";
    String name = "screening name";
    String responseTime = "2345";
    String screeningDecision = "3456";
    LocalDateTime startedAt = LocalDateTime.now();
    String narrative = "screening narrative";
    Address screeningAddress = new Address();
    Set<ParticipantEntity> participantEntities = new HashSet<>();
    ParticipantEntity participantEntity = new ParticipantEntity();
    participantEntities.add(participantEntity);
    ScreeningEntity screeningEntity =
        new ScreeningEntity(null, referrence, startedAt, endedAt, incidentCounty, incidentDate,
            locationType, communicationMethod, name, responseTime, screeningDecision, null,
            narrative, screeningAddress, null, participantEntities, "ssb", "Open", null);

    ScreeningSummary screeningSummary = new ScreeningSummary(screeningEntity, simpleAllegations);
    assertThat(screeningSummary.getName(), is(equalTo(screeningEntity.getName())));
    assertThat(screeningSummary.getDecision(), is(equalTo(screeningEntity.getScreeningDecision())));
    assertThat(screeningSummary.getAdditionalInformation(),
        is(equalTo(screeningEntity.getAdditionalInformation())));
    assertThat(screeningSummary.getDecisionDetail(),
        is(equalTo(screeningEntity.getScreeningDecisionDetail())));
    assertThat(screeningSummary.getId(), is(equalTo(screeningEntity.getId())));
    assertThat(screeningSummary.getSafetyInformation(),
        is(equalTo(screeningEntity.getSafetyInformation())));
    assertThat(new java.sql.Timestamp(screeningSummary.getStartedAt().getTime()).toLocalDateTime(),
        is(equalTo(screeningEntity.getStartedAt())));
    assertThat(screeningSummary.getAllegations(), is(equalTo(simpleAllegations)));
  }

  @Test
  public void testNullSafetyAlerts() {
    Set<String> safetyAlerts = new HashSet<>();
    // pass null for safety alerts to constructor
    ScreeningSummary domain = new ScreeningSummary(id, name, decision, decisionDetail, null,
        safetyInformation, additionalInformation, startedAt, null);
    assertThat(domain.getSafetyAlerts(), is(equalTo(safetyAlerts)));
  }

  @Test
  public void testNullAllegations() {
    Set<SimpleAllegation> simpleAllegations = new HashSet<>();
    // pass null for allegations to constructor
    ScreeningSummary domain = new ScreeningSummary(id, name, decision, decisionDetail, null,
        safetyInformation, additionalInformation, startedAt, null);
    assertThat(domain.getAllegations(), is(equalTo(simpleAllegations)));
  }

}
