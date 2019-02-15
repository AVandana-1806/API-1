package gov.ca.cwds.rest.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import gov.ca.cwds.data.legacy.cms.dao.PlacementEpisodeDao;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.apache.shiro.authz.AuthorizationException;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * @author CWDS API Team
 */
public class ClientTransformerTest {

  private ClientTransformer clientTransformer = new ClientTransformer();
  private AuthorizationService authorizationService;
  private PlacementEpisodeDao placementEpisodeDao;

  @Before
  public void setup() {
    authorizationService = mock(AuthorizationService.class);
    doThrow(AuthorizationException.class).when(authorizationService)
        .ensureClientAccessAuthorized("authorizedId");
    clientTransformer.setAuthorizationService(authorizationService);

    placementEpisodeDao = mock(PlacementEpisodeDao.class);
    clientTransformer.setPlacementEpisodeDao(placementEpisodeDao);
  }

  /**
   * Initialize intake code cache
   */
  private TestIntakeCodeCache testIntakeCodeCache = new TestIntakeCodeCache();
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  /**
   *
   */
  @Test
  public void testTranformIsNotNull() {
    Client client = new ClientEntityBuilder().build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   *
   */
  @Test
  public void testLegacyDescriptorNotNull() {
    Client client = new ClientEntityBuilder().setId("Abc0987654").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getLegacyDescriptor(), is(notNullValue()));
    assertThat(participantIntakeApi.getLegacyDescriptor().getTableName(),
        is(equalTo(LegacyTable.CLIENT.getName())));
    assertThat(participantIntakeApi.getLegacyDescriptor().getId(), is(equalTo("Abc0987654")));
  }

  /**
   *
   */
  @Test
  public void testTranformTheGender() {
    Client client = new ClientEntityBuilder().setGenderCode("F").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getGender(), is(equalTo("female")));
  }

  /**
   *
   */
  @Test
  public void testTranformPrimaryAndSecondayLanguage() {
    Client client = new ClientEntityBuilder().setPrimaryLanguageType((short) 1248)
        .setSecondaryLanguageType((short) 1253).build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getLanguages(),
        containsInAnyOrder("English", "American Sign Language"));
  }

  /**
   *
   */
  @Test
  public void testTranformSsnCorrectly() {
    Client client = new ClientEntityBuilder().setSocialSecurityNumber("345674389").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getSsn(), is(equalTo("345-67-4389")));
  }

  /**
   *
   */
  @Test
  public void testTranformSsnWhen0() {
    Client client = new ClientEntityBuilder().setSocialSecurityNumber("0").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getSsn(), is(equalTo("0")));
  }

  /**
   *
   */
  @Test
  public void testTranformSsnWhenNull() {
    Client client = new ClientEntityBuilder().setSocialSecurityNumber(null).build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getSsn(), is(equalTo("")));
  }

  /**
   *
   */
  @Test
  public void testTranformWhenOnlyPrimatyLanguageSet() {
    Client client = new ClientEntityBuilder().setPrimaryLanguageType((short) 1248)
        .setSecondaryLanguageType((short) 0).build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getLanguages(), containsInAnyOrder("American Sign Language"));
  }

  /**
   *
   */
  @Test
  public void testTranformWhenLanguages0() {
    Client client = new ClientEntityBuilder().setPrimaryLanguageType((short) 0)
        .setSecondaryLanguageType((short) 0).build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getLanguages().isEmpty(), is(equalTo(true)));
  }

  /**
   *
   */
  @Test
  public void testTranformSensitiveIndicator() {
    Client client = new ClientEntityBuilder().setSensitivityIndicator("S").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getSensitive(), is(equalTo(true)));
  }

  /**
   *
   */
  @Test
  public void testTranformSealedIndicator() {
    Client client = new ClientEntityBuilder().setSensitivityIndicator("R").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getSealed(), is(equalTo(true)));
  }

  /**
   *
   */
  @Test(expected = AuthorizationException.class)
  public void testTranformSealedIndicator1() {
    doThrow(AuthorizationException.class).when(authorizationService)
        .ensureClientAccessAuthorized(anyString());
    clientTransformer.setAuthorizationService(authorizationService);
    Client client = new ClientEntityBuilder().setSensitivityIndicator("R").build();
    clientTransformer.transform(client);
  }

  @Test
  public void testTransformEstimatedDob() {
    Client client = new ClientEntityBuilder().setEstimatedDobCode("Y").build();
    LocalDate today = LocalDate.now();

    client.setBirthDate(
        Date.from(today.minusDays(4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    ParticipantIntakeApi participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getApproximateAge(), is(equalTo("4")));
    assertThat(participantIntakeApi.getApproximateAgeUnits(), is(equalTo("days")));

    client.setBirthDate(
        Date.from(today.minusWeeks(7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getApproximateAge(), is(equalTo("7")));
    assertThat(participantIntakeApi.getApproximateAgeUnits(), is(equalTo("weeks")));

    client.setBirthDate(
        Date.from(today.minusWeeks(14).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getApproximateAge(), is(equalTo("3")));
    assertThat(participantIntakeApi.getApproximateAgeUnits(), is(equalTo("months")));

    client.setBirthDate(
        Date.from(today.minusYears(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    participantIntakeApi = clientTransformer.transform(client);
    assertThat(participantIntakeApi.getApproximateAge(), is(equalTo("5")));
    assertThat(participantIntakeApi.getApproximateAgeUnits(), is(equalTo("years")));
  }

}
