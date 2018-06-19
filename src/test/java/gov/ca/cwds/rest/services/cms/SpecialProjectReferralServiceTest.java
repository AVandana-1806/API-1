package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectReferralDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.fixture.CsecBuilder;
import gov.ca.cwds.fixture.SpecialProjectEntityBuilder;
import gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.referentialintegrity.RISpecialProjectReferral;

public class SpecialProjectReferralServiceTest {

  private SpecialProjectReferralService  specialProjectReferralService;
  
  private SpecialProjectReferralDao specialProjectReferralDao;
  private SpecialProjectDao specialProjectDao;
  private RISpecialProjectReferral riSpecialProjectReferral;
  
  private Validator validator;

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Before
  public void setup() {
    new TestingRequestExecutionContext("0X5");
    RequestExecutionContext.instance();
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    specialProjectReferralDao = mock(SpecialProjectReferralDao.class);    
    specialProjectDao = mock(SpecialProjectDao.class);
    
    riSpecialProjectReferral = mock(RISpecialProjectReferral.class);
    
    specialProjectReferralService = new SpecialProjectReferralService(specialProjectReferralDao,
        specialProjectDao, riSpecialProjectReferral, validator);    
  }
  
  @Test
  public void shouldReturnSpecialProjectReferralObjectWhenCreate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
    
    SpecialProjectReferral sprEntity = new SpecialProjectReferral();
    sprEntity.setCountySpecificCode(sprDomain.getCountySpecificCode());
    sprEntity.setId("9876543ABC");
    sprEntity.setLastUpdateId("aab");
    sprEntity.setLastUpdateTime(LocalDateTime.now());
    sprEntity.setPartEndDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationEndDate()));
    sprEntity.setPartStartDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationStartDate()));
    sprEntity.setReferralId(sprDomain.getReferralId());
    sprEntity.setSpecialProjectId(sprDomain.getSpecialProjectId());
    sprEntity.setSsbIndicator(sprDomain.getSafelySurrenderedBabiesIndicator());
    
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprPosted = specialProjectReferralService.create(sprDomain);
    
    assertThat(sprPosted.getClass(), is(gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral.class));
  }

  @Test
  public void shouldReturnPostedSpecialProjectReferralWhenSaveCsecSpecialProjectReferral() throws Exception {
    List<Csec> csecs = new ArrayList<Csec>();
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    csecs.add(csec);
    
    String referralId = "0987654ABC";
    String incidentCounty = "34";
    MessageBuilder messageBuilder = new MessageBuilder();
    
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .build();
    List<SpecialProject> specialProjects = new ArrayList();
    specialProjects.add(specialProject);
    
    when(specialProjectDao.findSpecialProjectByGovernmentEntityAndName(any(String.class), any(Short.class)))
    .thenReturn(specialProjects);

    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
    SpecialProjectReferral sprEntity = new SpecialProjectReferral();
    sprEntity.setCountySpecificCode(sprDomain.getCountySpecificCode());
    sprEntity.setId("9876543ABC");
    sprEntity.setLastUpdateId("aab");
    sprEntity.setLastUpdateTime(LocalDateTime.now());
    sprEntity.setPartEndDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationEndDate()));
    sprEntity.setPartStartDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationStartDate()));
    sprEntity.setReferralId(sprDomain.getReferralId());
    sprEntity.setSpecialProjectId(sprDomain.getSpecialProjectId());
    sprEntity.setSsbIndicator(sprDomain.getSafelySurrenderedBabiesIndicator());
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);

    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprPosted = specialProjectReferralService
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted.getClass(), is(gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral.class));    
  }
  
  @Test 
  public void shouldReturnNullWhenSpecialProjectDoesNotExistsOnSave() throws Exception {
    List<Csec> csecs = new ArrayList<Csec>();
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    csecs.add(csec);
    
    String referralId = "0987654ABC";
    String incidentCounty = "34";
    LocalDate endDate = LocalDate.now();
    MessageBuilder messageBuilder = new MessageBuilder();
    
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .setEndDate(endDate)
        .build();        
    List<SpecialProject> specialProjects = new ArrayList();
    specialProjects.add(specialProject);    
    when(specialProjectDao.findSpecialProjectByGovernmentEntityAndName(any(String.class), any(Short.class)))
    .thenReturn(specialProjects);
    
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
    SpecialProjectReferral sprEntity = new SpecialProjectReferral();
    sprEntity.setCountySpecificCode(sprDomain.getCountySpecificCode());
    sprEntity.setId("9876543ABC");
    sprEntity.setLastUpdateId("aab");
    sprEntity.setLastUpdateTime(LocalDateTime.now());
    sprEntity.setPartEndDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationEndDate()));
    sprEntity.setPartStartDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationStartDate()));
    sprEntity.setReferralId(sprDomain.getReferralId());
    sprEntity.setSpecialProjectId(sprDomain.getSpecialProjectId());
    sprEntity.setSsbIndicator(sprDomain.getSafelySurrenderedBabiesIndicator());
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);

    // specialProjectReferralDao.create should not be called
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprPosted = specialProjectReferralService
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted, is(nullValue()));    
 }
  
  @Test
  public void shouldReturnNullWhenSpecialProjectReferralAlreadyExist() throws Exception {
    List<Csec> csecs = new ArrayList<Csec>();
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    csecs.add(csec);
    
    String referralId = "9876543ABC";
    String specialProjectId = "0987654ABC";
    String incidentCounty = "34";
    MessageBuilder messageBuilder = new MessageBuilder();

    // mock the special project find dao
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .setId(specialProjectId)
        .build();
    List<SpecialProject> specialProjects = new ArrayList<SpecialProject>();
    specialProjects.add(specialProject);
    when(specialProjectDao.findSpecialProjectByGovernmentEntityAndName(any(String.class), any(Short.class)))
    .thenReturn(specialProjects);

    // mock the special project referral dao (findSpecialProjectByReferralIdAndSpecialProjectId)
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = 
        new SpecialProjectReferralResourceBuilder()
        .setSpecialProjectId(specialProjectId)
        .setReferralId(referralId)
        .build();
    SpecialProjectReferral sprEntity = new SpecialProjectReferral();
    sprEntity.setCountySpecificCode(sprDomain.getCountySpecificCode());
    sprEntity.setId("9876543ABC");
    sprEntity.setLastUpdateId("aab");
    sprEntity.setLastUpdateTime(LocalDateTime.now());
    sprEntity.setPartEndDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationEndDate()));
    sprEntity.setPartStartDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationStartDate()));
    sprEntity.setReferralId(sprDomain.getReferralId());
    sprEntity.setSpecialProjectId(sprDomain.getSpecialProjectId());
    sprEntity.setSsbIndicator(sprDomain.getSafelySurrenderedBabiesIndicator());
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);

    List<SpecialProjectReferral> specialProjectReferrals = new ArrayList();
    specialProjectReferrals.add(sprEntity);    
    when(specialProjectReferralDao
        .findSpecialProjectReferralsByReferralIdAndSpecialProjectId(referralId, specialProjectId))
        .thenReturn(specialProjectReferrals);

    // specialProjectReferralDao.create should not be called
    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprPosted = specialProjectReferralService
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted, is(nullValue()));    

  }
  
  @Test
  public void shouldReturnNullWhenInvalidGovernmentEntityType() throws Exception {
    List<Csec> csecs = new ArrayList<Csec>();
    Csec csec = new CsecBuilder().createCsec();
    csec.setId("S-CSEC Referral");
    csecs.add(csec);
    
    String referralId = "0987654ABC";
    String incidentCounty = "ZZ";
    MessageBuilder messageBuilder = new MessageBuilder();
    
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .build();
    List<SpecialProject> specialProjects = new ArrayList<SpecialProject>();
    specialProjects.add(specialProject);    
    when(specialProjectDao.findSpecialProjectByGovernmentEntityAndName(any(String.class), any(Short.class)))
    .thenReturn(specialProjects);

    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
    SpecialProjectReferral sprEntity = new SpecialProjectReferral();
    sprEntity.setCountySpecificCode(sprDomain.getCountySpecificCode());
    sprEntity.setId("9876543ABC");
    sprEntity.setLastUpdateId("aab");
    sprEntity.setLastUpdateTime(LocalDateTime.now());
    sprEntity.setPartEndDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationEndDate()));
    sprEntity.setPartStartDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationStartDate()));
    sprEntity.setReferralId(sprDomain.getReferralId());
    sprEntity.setSpecialProjectId(sprDomain.getSpecialProjectId());
    sprEntity.setSsbIndicator(sprDomain.getSafelySurrenderedBabiesIndicator());
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);

    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprPosted = specialProjectReferralService
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted, is(nullValue()));    
    
  }
  
  @Test
  public void shouldRetrunNullWhenCSECDataNotProvided() throws Exception {
    List<Csec> csecs = new ArrayList();

    String referralId = "0987654ABC";
    String incidentCounty = "34";
    MessageBuilder messageBuilder = new MessageBuilder();
    
    SpecialProject specialProject = new SpecialProjectEntityBuilder()
        .setName("test")
        .build();
    List<SpecialProject> specialProjects = new ArrayList();
    specialProjects.add(specialProject);
    
    when(specialProjectDao.findSpecialProjectByGovernmentEntityAndName(any(String.class), any(Short.class)))
    .thenReturn(specialProjects);

    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
    SpecialProjectReferral sprEntity = new SpecialProjectReferral();
    sprEntity.setCountySpecificCode(sprDomain.getCountySpecificCode());
    sprEntity.setId("9876543ABC");
    sprEntity.setLastUpdateId("aab");
    sprEntity.setLastUpdateTime(LocalDateTime.now());
    sprEntity.setPartEndDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationEndDate()));
    sprEntity.setPartStartDate(DomainChef.uncookLocalDateString(sprDomain.getParticipationStartDate()));
    sprEntity.setReferralId(sprDomain.getReferralId());
    sprEntity.setSpecialProjectId(sprDomain.getSpecialProjectId());
    sprEntity.setSsbIndicator(sprDomain.getSafelySurrenderedBabiesIndicator());
    when(specialProjectReferralDao.create(any(SpecialProjectReferral.class))).thenReturn(sprEntity);

    gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprPosted = specialProjectReferralService
        .saveCsecSpecialProjectReferral(csecs, referralId, incidentCounty, messageBuilder);
    assertThat(sprPosted, is(nullValue()));    
    
  }
  
  @Test
  public void testFind() {
    try {
      specialProjectReferralService.find("abc");
      fail("Expected exception was not thrown");
    } catch (Exception e) {
    }
  }

  @Test
  public void testDelete() {
    try {
      specialProjectReferralService.delete("abc");
      fail("Expected exception was not thrown");
    } catch (Exception e) {
    }
  }

  @Test
  public void testUpdate() {
    try {
      String primaryKey = "1234567ABC";
      gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral sprDomain = new SpecialProjectReferralResourceBuilder().build();
      
      specialProjectReferralService.update(primaryKey, sprDomain);
      fail("Expected exception was not thrown");
    } catch (Exception e) {
    }
  }
}
