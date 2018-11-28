package gov.ca.cwds.es.live;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticSearchPersonAddress;
import gov.ca.cwds.data.es.ElasticSearchPersonLanguage;
import gov.ca.cwds.data.es.ElasticSearchPersonPhone;
import gov.ca.cwds.data.es.ElasticSearchPersonScreening;
import gov.ca.cwds.data.es.ElasticSearchRaceAndEthnicity;
import gov.ca.cwds.data.es.ElasticSearchSystemCode;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.es.SimpleAddress;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ElasticTransformerTest extends Doofenshmirtz<TestDenormalizedEntity> {

  TestNormalizedEntityDao dao;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    dao = new TestNormalizedEntityDao(sessionFactory);
  }

  @Test
  public void type() throws Exception {
    assertThat(LiveElasticTransformer.class, notNullValue());
  }

  @Test
  public void handleLanguage_Args__ApiPersonAware() throws Exception {
    final ApiPersonAware p = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    final List<ElasticSearchPersonLanguage> actual = LiveElasticTransformer.buildLanguage(p);
    final List<ElasticSearchPersonLanguage> expected = new ArrayList<>();
    expected.add(new ElasticSearchPersonLanguage("1249", null, false));
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void handlePhone_Args__ApiPersonAware() throws Exception {
    final ApiPersonAware p = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    final List<ElasticSearchPersonPhone> actual = LiveElasticTransformer.buildPhone(p);
    final List<ElasticSearchPersonPhone> expected = null;
    assertThat(actual, notNullValue());
  }

  @Test
  public void handleAddress_Args__ApiPersonAware() throws Exception {
    final TestOnlyApiPersonAware p = new TestOnlyApiPersonAware();
    final SimpleAddress addr =
        new SimpleAddress("Provo", "Utah", "UT", "206 Hinckley Center", "84602");
    p.addAddress(addr);

    final List<ElasticSearchPersonAddress> actual = LiveElasticTransformer.buildAddress(p);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void handleScreening_Args__ApiPersonAware() throws Exception {
    final ApiPersonAware p = new TestOnlyApiPersonAware();
    final List<ElasticSearchPersonScreening> actual = LiveElasticTransformer.buildScreening(p);
    final List<ElasticSearchPersonScreening> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void buildElasticSearchPersonDoc_Args__ObjectMapper__ApiPersonAware() throws Exception {
    final TestNormalizedEntity p = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    final ElasticSearchPerson actual =
        LiveElasticTransformer.buildElasticSearchPersonDoc(MAPPER, p);
    // ElasticSearchPerson expected = mapper.readValue(
    // this.getClass().getResourceAsStream("/fixtures/ElasticTransformerTestFixture.json"),
    // ElasticSearchPerson.class);
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void buildElasticSearchPersonDoc_Args__ObjectMapper__ApiPersonAware_T__JsonProcessingException()
      throws Exception {
    final ApiPersonAware p = mock(ApiPersonAware.class);
    doThrow(new IllegalArgumentException("whatever")).when(p).getPrimaryKey();
    LiveElasticTransformer.buildElasticSearchPersonDoc(MAPPER, p);
  }

  @Test
  public void handleLegacyDescriptor_Args__ApiPersonAware() throws Exception {
    final ApiPersonAware p = new TestOnlyApiPersonAware();
    final ElasticSearchLegacyDescriptor actual = LiveElasticTransformer.buildLegacyDescriptor(p);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void jsonify_Args__Object() throws Exception {
    final TestNormalizedEntity t = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    final String actual = LiveElasticTransformer.jsonify(t);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void determineId_Args__ApiLegacyAware__ElasticSearchPerson() throws Exception {
    final ApiLegacyAware l = mock(ApiLegacyAware.class);
    final ElasticSearchPerson esp = mock(ElasticSearchPerson.class);
    final String actual = LiveElasticTransformer.determineId(l, esp);
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void determineId_Args__CmsReplicatedEntity__ElasticSearchPerson() throws Exception {
    final CmsReplicatedEntity l =
        new TestDenormalizedEntity(DEFAULT_CLIENT_ID, "dave", "jey", "mariam", "jim");
    final ElasticSearchPerson esp = new ElasticSearchPerson();
    final String actual = LiveElasticTransformer.determineId(l, esp);
    final String expected = DEFAULT_CLIENT_ID;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void buildElasticSearchPersons_Args__PersistentObject() throws Exception {
    final TestNormalizedEntity p = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    p.addEntry(new TestNormalizedEntry("xyz1234567", "crap"));
    final ElasticSearchPerson[] actual = LiveElasticTransformer.buildElasticSearchPersons(p);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void buildElasticSearchPerson_Args__ApiPersonAware() throws Exception {
    final ApiPersonAware p = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    final ElasticSearchPerson actual = LiveElasticTransformer.buildElasticSearchPerson(p);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void createLegacyDescriptor_Args__String__Date__LegacyTable() throws Exception {
    final String legacyId = DEFAULT_CLIENT_ID;
    final Date legacyLastUpdated = new Date();
    final LegacyTable legacyTable = LegacyTable.CLIENT;
    final ElasticSearchLegacyDescriptor actual =
        LiveElasticTransformer.createLegacyDescriptor(legacyId, legacyLastUpdated, legacyTable);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void handleRaceEthnicity_Args__ApiPersonAware() throws Exception {
    final ApiPersonAware p = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    final ElasticSearchRaceAndEthnicity actual = LiveElasticTransformer.buildRaceEthnicity(p);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void handleClientCounty_Args__ApiPersonAware() throws Exception {
    final ApiPersonAware p = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    final List<ElasticSearchSystemCode> actual = LiveElasticTransformer.buildClientCounties(p);

    List<ElasticSearchSystemCode> expected = new ArrayList<>();
    final ElasticSearchSystemCode expectedCounty = new ElasticSearchSystemCode();
    expectedCounty.setId("1115");
    expected.add(expectedCounty);

    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void buildElasticSearchPersonDoc_Args__ApiPersonAware() throws Exception {
    final ApiPersonAware p = new TestNormalizedEntity(DEFAULT_CLIENT_ID);
    final ElasticSearchPerson actual = LiveElasticTransformer.buildElasticSearchPersonDoc(p);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getMapper_Args__() throws Exception {
    final ObjectMapper actual = LiveElasticTransformer.getMapper();
    final ObjectMapper expected = MAPPER;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMapper_Args__ObjectMapper() throws Exception {
    final ObjectMapper mapper = mock(ObjectMapper.class);
    LiveElasticTransformer.setMapper(mapper);
  }

}
