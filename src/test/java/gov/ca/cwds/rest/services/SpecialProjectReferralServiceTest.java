package gov.ca.cwds.rest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.legacy.cms.dao.NonCWSNumberDao;
import gov.ca.cwds.data.legacy.cms.dao.SafelySurrenderedBabiesDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectReferralDao;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.fixture.SafelySurrenderedBabiesBuilder;
import gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.cms.SpecialProjectReferralService;
import gov.ca.cwds.rest.services.referentialintegrity.RISpecialProjectReferral;

/**
 * Unit tests for SpecialProjectReferralService.
 * 
 * @author CWDS TPT Team
 *
 */
public class SpecialProjectReferralServiceTest {

  private SpecialProjectReferralService specialProjectReferralService;
  private SpecialProjectDao specialProjectDao;
  private SpecialProjectReferralDao specialProjectReferralDao;
  private SafelySurrenderedBabiesDao safelySurrenderedBabiesDao;
  private RISpecialProjectReferral riSpecialProjectReferral;
  private NonCWSNumberDao nonCWSNumberDao;
  private ReferralDao referralDao;
  private DroolsService droolsService;
  private Validator validator;

  @Before
  public void setup() {
    specialProjectDao = mock(SpecialProjectDao.class);
    specialProjectReferralDao = mock(SpecialProjectReferralDao.class);
    safelySurrenderedBabiesDao = mock(SafelySurrenderedBabiesDao.class);
    nonCWSNumberDao = mock(NonCWSNumberDao.class);
    riSpecialProjectReferral = mock(RISpecialProjectReferral.class);
    droolsService = mock(DroolsService.class);
    validator = mock(Validator.class);

    List<SpecialProject> ssbSpecialProjects = new ArrayList<>();
    SpecialProject specialProject = new SpecialProject();
    specialProject.setId("ssb-sp-id");
    ssbSpecialProjects.add(specialProject);

    SpecialProjectReferral ssbSpecialProjectReferral = new SpecialProjectReferral();
    ssbSpecialProjectReferral.setId("ssb-spr-id");

    when(specialProjectDao.findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity(any()))
        .thenReturn(ssbSpecialProjects);
    when(specialProjectReferralDao.create(any())).thenReturn(ssbSpecialProjectReferral);
    when(safelySurrenderedBabiesDao.create(any())).thenReturn(null);
    when(nonCWSNumberDao.create(any())).thenReturn(null);
    when(droolsService.performBusinessRules(any(), any())).thenReturn(null);

    specialProjectReferralService = new SpecialProjectReferralService();
    specialProjectReferralService.setNonCWSNumberDao(nonCWSNumberDao);
    specialProjectReferralService.setSafelySurrenderedBabiesDao(safelySurrenderedBabiesDao);
    specialProjectReferralService.setSpecialProjectDao(specialProjectDao);
    specialProjectReferralService.setSpecialProjectReferralDao(specialProjectReferralDao);
    specialProjectReferralService.setRiSpecialProjectReferral(riSpecialProjectReferral);

    new TestingRequestExecutionContext("0X5");
  }

  @Test
  public void testProcessSafelySurrenderedBabies() {
    LocalDateTime now = LocalDateTime.now();
    SafelySurrenderedBabies ssb = new SafelySurrenderedBabiesBuilder().build();
    specialProjectReferralService.processSafelySurrenderedBabies("clientId", "referralId",
        now.toLocalDate(), now.toLocalTime(), ssb);
  }

  @Test(expected = ServiceException.class)
  public void testProcessSafelySurrenderedBabies_SpecialprojectMissing() {
    when(specialProjectDao.findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity(any()))
        .thenReturn(new ArrayList<>());

    LocalDateTime now = LocalDateTime.now();
    SafelySurrenderedBabies ssb = new SafelySurrenderedBabiesBuilder().build();
    specialProjectReferralService.processSafelySurrenderedBabies("clientId", "referralId",
        now.toLocalDate(), now.toLocalTime(), ssb);
  }

  @Test
  public void shouldReturnRiSpecialProjectReferral() {
    referralDao = mock(ReferralDao.class);
    RISpecialProjectReferral ri = new RISpecialProjectReferral(referralDao, specialProjectDao);
    specialProjectReferralService.setRiSpecialProjectReferral(ri);
    assertThat(specialProjectReferralService.getRiSpecialProjectReferral(), is(ri));
  }
}
