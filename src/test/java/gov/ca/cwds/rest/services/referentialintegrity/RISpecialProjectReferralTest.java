package gov.ca.cwds.rest.services.referentialintegrity;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectDao;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.fixture.SpecialProjectEntityBuilder;
import gov.ca.cwds.fixture.SpecialProjectReferralEntityBuilder;
import gov.ca.cwds.fixture.SpecialProjectReferralResourceBuilder;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

import gov.ca.cwds.rest.api.domain.cms.SpecialProjectReferral;

public class RISpecialProjectReferralTest {

  private SpecialProjectDao specialProjectDao;
  private ReferralDao referralDao;
  
  @Before
  public void setup() {
    specialProjectDao = mock(SpecialProjectDao.class);
    referralDao = mock(ReferralDao.class);
  }
  
  @Test
  public void instantiation() throws Exception {
    RISpecialProjectReferral target = new RISpecialProjectReferral(referralDao, specialProjectDao);
    assertThat(target, notNullValue());
  }
  
  @Test(expected = ReferentialIntegrityException.class)
  public void shouldFailWhenReferralIsNotFound() throws Exception {
    // special project referral to check referential integrity
    gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral entity = new SpecialProjectReferralEntityBuilder().build();
    
    SpecialProjectReferral domain = new SpecialProjectReferralResourceBuilder().build();
    
    // mock the special project dao
    SpecialProject specialProject = new SpecialProjectEntityBuilder().build();
    when(specialProjectDao.find(any(String.class))).thenReturn(specialProject);
    
    // mock the referral
    when(referralDao.find(any(String.class))).thenReturn(null);
    
    RISpecialProjectReferral target = new RISpecialProjectReferral(referralDao, specialProjectDao);
    target.apply(entity);
  }
  
  @Test(expected = ReferentialIntegrityException.class)
  public void shouldFailWhenSpecailProjectIsNotFound() throws Exception {
    // special project referral to check referential integrity
    String lastUpdatedId = "0X5";
    LocalDateTime lastUpdatedTime = LocalDateTime.now();
    gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral entity = new SpecialProjectReferralEntityBuilder()
        .build();
    entity.setLastUpdateId(lastUpdatedId);
    entity.setLastUpdateTime(lastUpdatedTime);

    // mock the special project
    when(specialProjectDao.find(any(String.class))).thenReturn(null);
    
    // mock the referral
    Referral referral = new ReferralEntityBuilder().build();
    when(referralDao.find(any(String.class))).thenReturn(referral);
    
    RISpecialProjectReferral target = new RISpecialProjectReferral(referralDao, specialProjectDao);
    target.apply(entity);
  }
  
  @Test
  public void shouldPassWhenRI() throws Exception {
    // special project referral to check referential integrity
    String lastUpdatedId = "0X5";
    LocalDateTime lastUpdatedTime = LocalDateTime.now();
    SpecialProjectReferral domain = new SpecialProjectReferralResourceBuilder().build();
    gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral entity = new SpecialProjectReferralEntityBuilder()
        .build();
    entity.setLastUpdateId(lastUpdatedId);
    entity.setLastUpdateTime(lastUpdatedTime);
    
    // mock the special project
    SpecialProject specialProject = new SpecialProjectEntityBuilder().build();
    when(specialProjectDao.find(any(String.class))).thenReturn(specialProject);

    // mock the referral
    Referral referral = new ReferralEntityBuilder().build();
    when(referralDao.find(any(String.class))).thenReturn(referral);
    
    RISpecialProjectReferral target = new RISpecialProjectReferral(referralDao, specialProjectDao);
    Boolean result = target.apply(entity);
    assertThat(result, is(equalTo(true)));
    
  }
}
