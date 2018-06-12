package gov.ca.cwds.rest.api.services.screeningparticipant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import org.junit.Test;

import gov.ca.cwds.data.cms.TestIntakeCodeCache;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.services.screeningparticipant.ClientTransformer;

/**
 * @author CWDS API Team
 *
 */
public class ClientTransformerTest {

  private ClientTransformer clientTransformer = new ClientTransformer();

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
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
    assertThat(participantIntakeApi, is(notNullValue()));
  }

  /**
   * 
   */
  @Test
  public void testLegacyDescriptorNotNull() {
    Client client = new ClientEntityBuilder().setId("Abc0987654").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
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
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
    assertThat(participantIntakeApi.getGender(), is(equalTo("female")));
  }

  /**
   * 
   */
  @Test
  public void testTranformPrimaryAndSecondayLanguage() {
    Client client = new ClientEntityBuilder().setPrimaryLanguageType((short) 1248)
        .setSecondaryLanguageType((short) 1253).build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
    assertThat(participantIntakeApi.getLanguages(),
        containsInAnyOrder("English", "American Sign Language"));
  }

  /**
   * 
   */
  @Test
  public void testTranformWhenOnlyPrimatyLanguageSet() {
    Client client = new ClientEntityBuilder().setPrimaryLanguageType((short) 1248)
        .setSecondaryLanguageType((short) 0).build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
    assertThat(participantIntakeApi.getLanguages(), containsInAnyOrder("American Sign Language"));
  }

  /**
   * 
   */
  @Test
  public void testTranformWhenLanguages0() {
    Client client = new ClientEntityBuilder().setPrimaryLanguageType((short) 0)
        .setSecondaryLanguageType((short) 0).build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
    assertThat(participantIntakeApi.getLanguages().isEmpty(), is(equalTo(true)));
  }

  /**
   * 
   */
  @Test
  public void testTranformSensitiveIndicator() {
    Client client = new ClientEntityBuilder().setSensitivityIndicator("S").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
    assertThat(participantIntakeApi.getSensitive(), is(equalTo(true)));
  }

  /**
   * 
   */
  @Test
  public void testTranformSealedIndicator() {
    Client client = new ClientEntityBuilder().setSensitivityIndicator("R").build();
    ParticipantIntakeApi participantIntakeApi = clientTransformer.tranform(client);
    assertThat(participantIntakeApi.getSealed(), is(equalTo(true)));
  }

}
