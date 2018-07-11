package gov.ca.cwds.rest.business.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.data.persistence.cms.AssignmentUnit;
import gov.ca.cwds.data.persistence.cms.CaseLoad;
import gov.ca.cwds.data.persistence.cms.CwsOffice;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.fixture.AssignmentEntityBuilder;
import gov.ca.cwds.fixture.AssignmentUnitEntityBuilder;
import gov.ca.cwds.fixture.CaseLoadEntityBuilder;
import gov.ca.cwds.fixture.CwsOfficeEntityBuilder;
import gov.ca.cwds.fixture.ReferralEntityBuilder;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * CWDS API Team
 */
public class R01054PrimaryAssignmentAddingTest {

  private ReferralDao referralDao;
  private CaseLoadDao caseLoadDao;
  private AssignmentUnitDao assignmentUnitDao;
  private CwsOfficeDao cwsOfficeDao;
  private SessionFactory sessionFactory;
  private Session session;

  @Before
  public void setUp() {
    referralDao = mock(ReferralDao.class);
    caseLoadDao = mock(CaseLoadDao.class);
    assignmentUnitDao = mock(AssignmentUnitDao.class);
    cwsOfficeDao = mock(CwsOfficeDao.class);
    sessionFactory = mock(SessionFactory.class);
    session = mock(Session.class);
  }

  @Test
  public void testReferralAssignment() {
    short governmentEntityType = (short) -1;

    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P")
        .setEstablishedForCode("R").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    AssignmentUnit assignmentUnit = new AssignmentUnitEntityBuilder().build();
    CwsOffice cwsOffice =
        new CwsOfficeEntityBuilder().setGovernmentEntityType(governmentEntityType).build();
    Referral referral = new ReferralEntityBuilder().build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(assignmentUnit);
    when(cwsOfficeDao.find(anyString())).thenReturn(cwsOffice);
    when(referralDao.find(anyString())).thenReturn(referral);
    when(referralDao.grabSession()).thenReturn(session);
    when(sessionFactory.getCurrentSession()).thenReturn(session);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    rule.execute();

    assertEquals((int) governmentEntityType, (int) referral.getGovtEntityType());
    verify(session, times(1)).merge(any());
  }

  @Test
  public void testReferralAssignmentNoReferralFound() {
    short governmentEntityType = (short) -1;

    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P")
        .setEstablishedForCode("R").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    AssignmentUnit assignmentUnit = new AssignmentUnitEntityBuilder().build();
    CwsOffice cwsOffice =
        new CwsOfficeEntityBuilder().setGovernmentEntityType(governmentEntityType).build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(assignmentUnit);
    when(cwsOfficeDao.find(anyString())).thenReturn(cwsOffice);
    when(referralDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch (ServiceException e) {
      assertEquals("Cannot find referral for assignment: SlCAr46088", e.getMessage());
    }
    verify(session, times(0)).merge(any());
  }


  @Test
  public void testNoCwsOfficeFound() {
    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P")
        .setEstablishedForCode("R").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();
    AssignmentUnit assignmentUnit = new AssignmentUnitEntityBuilder().build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(assignmentUnit);
    when(cwsOfficeDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch (ServiceException e) {
      assertEquals("Cannot find cwsOffice for assignmentUnit: assignmentUnitId", e.getMessage());
    }
  }

  @Test
  public void testAssignmentUnitFound() {
    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("P")
        .setEstablishedForCode("R").build();
    CaseLoad caseLoad = new CaseLoadEntityBuilder().build();

    when(caseLoadDao.find(anyString())).thenReturn(caseLoad);
    when(assignmentUnitDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch (ServiceException e) {
      assertEquals("Cannot find assignmentUnit for caseLoad: ABC1234567", e.getMessage());
    }
    verify(session, times(0)).merge(any());
  }

  @Test
  public void testCaseLoadFound() {
    Assignment assignment = new AssignmentEntityBuilder().setEstablishedForCode("R").build();
    when(caseLoadDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    try {
      rule.execute();
      fail();
    } catch (ServiceException e) {
      assertEquals("Cannot find caseLoad for assignment: SlCAr46088", e.getMessage());
    }
    verify(session, times(0)).merge(any());
  }

  @Test
  public void testNoPrimaryAssignment() {
    Assignment assignment = new AssignmentEntityBuilder().setTypeOfAssignmentCode("N").build();
    when(caseLoadDao.find(anyString())).thenReturn(null);

    R01054PrimaryAssignmentAdding rule = new R01054PrimaryAssignmentAdding(assignment, referralDao,
        caseLoadDao, assignmentUnitDao, cwsOfficeDao);
    rule.execute();

    verify(session, times(0)).merge(any());
  }
}
