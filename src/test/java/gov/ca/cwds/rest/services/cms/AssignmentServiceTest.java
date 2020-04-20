package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.fixture.AssignmentEntityBuilder;
import gov.ca.cwds.fixture.AssignmentResourceBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.PostedAssignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AssignmentServiceTest {

  private AssignmentService assignmentService;
  private AssignmentDao assignmentDao;
  private NonLACountyTriggers nonLACountyTriggers;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private Validator validator;
  private ExternalInterfaceTables externalInterfaceTables;
  private CountyOwnershipDao countyOwnershipDao;
  private ReferralDao referralDao;
  private CaseLoadDao caseLoadDao;
  private Date lastUpdatedTime = new Date();
  private AssignmentUnitDao assignmentUnitDao;
  private CwsOfficeDao cwsOfficeDao;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("0X5");
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    assignmentDao = mock(AssignmentDao.class);
    nonLACountyTriggers = mock(NonLACountyTriggers.class);
    staffpersonDao = mock(StaffPersonDao.class);
    triggerTablesDao = mock(TriggerTablesDao.class);
    externalInterfaceTables = mock(ExternalInterfaceTables.class);
    countyOwnershipDao = mock(CountyOwnershipDao.class);
    referralDao = mock(ReferralDao.class);
    ReferralClientDao referralClientDao = mock(ReferralClientDao.class);
    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    caseLoadDao = mock(CaseLoadDao.class);
    assignmentUnitDao = mock(AssignmentUnitDao.class);
    cwsOfficeDao = mock(CwsOfficeDao.class);
    nonLACountyTriggers =
        new NonLACountyTriggers(countyOwnershipDao, referralDao, referralClientDao);
    assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers, staffpersonDao,
        triggerTablesDao, validator, externalInterfaceTables, caseLoadDao, referralDao,
        assignmentUnitDao, cwsOfficeDao, messageBuilder);
  }

  // find test
  @Test
  public void assignmentServiceFindReturnsCorrectEntity() {
    String id = "SlCAr46088";
    Assignment expected = new AssignmentResourceBuilder().buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, expected, "0XA", lastUpdatedTime);

    when(assignmentDao.find(id)).thenReturn(assignment);
    Assignment found = assignmentService.find(id);
    assertThat(found, is(expected));
  }


  @Test
  public void assignmentServiceFindReturnsNullWhenNotFound() {
    Response found = assignmentService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  // delete test
  @Test
  public void assignmentServiceDeleteDelegatesToCrudsService() {
    assignmentService.delete("ABC2345678");
    verify(assignmentDao, times(1)).delete("ABC2345678");
  }

  @Test
  public void assignmentServiceDeleteReturnsNullWhenNotFound() {
    Response found = assignmentService.delete("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  @Test
  public void assignmentServiceDeleteReturnsNotNull() {
    String id = "AabekZX00F";
    Assignment expected = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, expected, "0XA", lastUpdatedTime);

    when(assignmentDao.delete(id)).thenReturn(assignment);
    Assignment found = assignmentService.delete(id);
    assertThat(found, is(expected));
  }

  // update test
  @Test
  public void assignmentServiceUpdateReturnsCorrectEntity() {
    String id = "SlCAr46088";
    Assignment expected =
        new AssignmentResourceBuilder().setCountySpecificCode("45").buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment assignment =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, expected, "ABC", lastUpdatedTime);

    when(assignmentDao.find("ABC1234567")).thenReturn(assignment);
    when(assignmentDao.update(any())).thenReturn(assignment);

    Object retval = assignmentService.update("ABC1234567", expected);
    assertEquals(retval.getClass(), Assignment.class);
  }

  @Test
  public void assignmentServiceUpdateThrowsExceptionWhenNotFound() {
    try {
      Assignment assignmentRequest =
          new AssignmentResourceBuilder().setCountySpecificCode("45").buildAssignment();

      when(assignmentDao.update(any())).thenThrow(EntityNotFoundException.class);

      assignmentService.update("ZZZZZZZZZZ", assignmentRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  // create test
  @Test
  public void assignmentServiceCreateReturnsPostedClass() {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC",
            lastUpdatedTime);

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    Response response = assignmentService.create(request);
    assertEquals(response.getClass(), PostedAssignment.class);
  }

  @Test
  public void assignmentServiceCreateReturnsNonNull() {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC",
            lastUpdatedTime);

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    PostedAssignment postedAssignment = assignmentService.create(request);
    assertThat(postedAssignment, is(notNullValue()));
  }

  @Test
  public void assignmentServiceCreateReturnsCorrectEntity() {
    String id = "5rVkB8c088";
    Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment(id, domainAssignment, "ABC",
            lastUpdatedTime);

    PostedAssignment request = new PostedAssignment(toCreate);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);

    PostedAssignment expected = new PostedAssignment(toCreate);
    PostedAssignment returned = assignmentService.create(request);
    assertThat(returned, is(expected));
  }

  @Test
  public void assignmentServiceCreateThrowsEntityExistsException() {
    try {
      Assignment assignmentRequest = new AssignmentResourceBuilder().buildAssignment();

      when(assignmentDao.create(any())).thenThrow(EntityExistsException.class);

      assignmentService.create(assignmentRequest);
    } catch (Exception e) {
      assertEquals(e.getClass(), ServiceException.class);
    }
  }

  @Test
  public void assignmentServiceCreateNullIDError() {
    try {
      Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();

      gov.ca.cwds.data.persistence.cms.Assignment toCreate =
          new gov.ca.cwds.data.persistence.cms.Assignment(null, domainAssignment, "ABC",
              lastUpdatedTime);

      when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
          .thenReturn(toCreate);

      new PostedAssignment(toCreate);
    } catch (ServiceException e) {
      assertEquals("Assignment ID cannot be blank", e.getMessage());
    }

  }

  @Test
  public void assignmentServiceCreateBlankIDError() {
    try {
      Assignment domainAssignment = new AssignmentResourceBuilder().buildAssignment();
      gov.ca.cwds.data.persistence.cms.Assignment toCreate =
          new gov.ca.cwds.data.persistence.cms.Assignment("   ", domainAssignment, "ABC",
              lastUpdatedTime);

      when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
          .thenReturn(toCreate);

      new PostedAssignment(toCreate);
    } catch (ServiceException e) {
      assertEquals("Assignment ID cannot be blank", e.getMessage());
    }
  }

  /**
   * Test for checking the new Assignment Id generated and length is 10
   *
   * @throws Exception - exception
   */
  @Test
  public void assignmentServiceCreateReturnsGeneratedId() {
    Assignment domainAssignment = new AssignmentResourceBuilder().setStartDate("2017-01-01")
        .setStartTime("16:01:01").buildAssignment();

    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenAnswer(invocation -> invocation.getArguments()[0]);

    PostedAssignment returned = assignmentService.create(domainAssignment);
    assertEquals(returned.getId().length(), 10);
    PostedAssignment newReturned = assignmentService.create(domainAssignment);
    Assert.assertNotEquals(returned.getId(), newReturned.getId());
  }

  @Test
  public void testCreateNonLACountyTriggerForAssignment() {
    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "q1p",
            lastUpdatedTime);

    Referral domainReferral = new ReferralResourceBuilder().setCountySpecificCode("00").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234568", domainReferral, "0X5");

    Assignment request = new Assignment(toCreate);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("20");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.find(any(String.class))).thenReturn(referral);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);
    assignmentService.create(request);

    verify(countyOwnershipDao, times(1)).create(any());
  }

  @Test
  public void testNotCreateNonLACountyTriggerForAssignment() {
    Assignment assignmentDomain = new AssignmentResourceBuilder().buildAssignment();
    gov.ca.cwds.data.persistence.cms.Assignment toCreate =
        new gov.ca.cwds.data.persistence.cms.Assignment("ABC1234567", assignmentDomain, "BTr",
            lastUpdatedTime);

    Referral domainReferral = new ReferralResourceBuilder().setCountySpecificCode("00").build();
    gov.ca.cwds.data.persistence.cms.Referral referral =
        new gov.ca.cwds.data.persistence.cms.Referral("ABC1234568", domainReferral, "0X5");

    Assignment request = new Assignment(toCreate);

    when(triggerTablesDao.getLaCountySpecificCode()).thenReturn("19");

    StaffPerson staffPerson = new StaffPerson("BTr", null, "External Interface",
        "external interface", "SCXCIN7", " ", "", BigDecimal.valueOf(9165672100L), 0, null, "    ",
        "N", "MIZN02k00E", "  ", "    ", "19", "N", "3XPCP92q38", null);

    when(staffpersonDao.find(any(String.class))).thenReturn(staffPerson);
    when(referralDao.find(any(gov.ca.cwds.data.persistence.cms.Referral.class)))
        .thenReturn(referral);
    when(assignmentDao.create(any(gov.ca.cwds.data.persistence.cms.Assignment.class)))
        .thenReturn(toCreate);
    assignmentService.create(request);

    verify(countyOwnershipDao, times(0)).create(any());
  }

  @Test
  public void testR06560RuleValid() {
    gov.ca.cwds.data.persistence.cms.Assignment assignment = new AssignmentEntityBuilder()
        .setEstablishedForCode("R").setTypeOfAssignmentCode("P").setFkCaseLoad("-1").build();
    assignmentService.executeR06560Rule(assignment);
  }

  @Test
  public void testR06560RuleInvalid() {
    gov.ca.cwds.data.persistence.cms.Assignment assignment = new AssignmentEntityBuilder()
        .setEstablishedForCode("R").setTypeOfAssignmentCode("P").setFkCaseLoad(null).build();
    AssignmentService assignmentService = new AssignmentService(assignmentDao, nonLACountyTriggers,
        staffpersonDao, triggerTablesDao, validator, externalInterfaceTables, caseLoadDao,
        referralDao, assignmentUnitDao, cwsOfficeDao, new MessageBuilder());
    try {
      assignmentService.executeR06560Rule(assignment);
      fail();
    } catch (BusinessValidationException e) {
      assertEquals("R - 06560 Caseload Required For First Primary Asg is failed",
          e.getValidationDetailsList().iterator().next().getUserMessage());
    }
  }

  @Test
  public void testFirstAssignmentWithEarlierStartDate() {
    Set<gov.ca.cwds.data.persistence.cms.Assignment> assignmentSet = new HashSet<>();
    assignmentSet.add(new AssignmentEntityBuilder().setId("1").setStartDate("2017-01-11")
        .setStartTime("10:11:02").setEndDate("2017-01-12").setEndTime("14:00:00").build());
    assignmentSet.add(new AssignmentEntityBuilder().setId("2").setStartDate("2015-06-10")
        .setStartTime("10:11:02").setEndDate("2015-06-11").setEndTime("14:00:00").build());
    assignmentSet.add(new AssignmentEntityBuilder().setId("3").setStartDate("2015-04-11")
        .setStartTime("10:11:02").setEndDate("2015-04-12").setEndTime("14:00:00").build());
    when(assignmentDao.findAssignmentsByReferralId(any(String.class))).thenReturn(assignmentSet);

    gov.ca.cwds.data.persistence.cms.Assignment firstAssignment =
        assignmentService.findReferralFirstAssignment("111");
    assertNotNull(firstAssignment);
    assertEquals("3", firstAssignment.getId());
  }

  @Test
  public void testFirstAssignmentWithSameStartDateEarlierStartTime() {
    Set<gov.ca.cwds.data.persistence.cms.Assignment> assignmentSet = new HashSet<>();
    assignmentSet.add(new AssignmentEntityBuilder().setId("1").setStartDate("2017-01-11")
        .setStartTime("10:11:02").setEndDate("2017-01-12").setEndTime("14:00:00").build());
    assignmentSet.add(new AssignmentEntityBuilder().setId("2").setStartDate("2017-01-11")
        .setStartTime("09:56:02").setEndDate("2017-01-12").setEndTime("15:00:00").build());
    when(assignmentDao.findAssignmentsByReferralId(any(String.class))).thenReturn(assignmentSet);

    gov.ca.cwds.data.persistence.cms.Assignment firstAssignment =
        assignmentService.findReferralFirstAssignment("111");
    assertNotNull(firstAssignment);
    assertEquals("2", firstAssignment.getId());
  }
}
