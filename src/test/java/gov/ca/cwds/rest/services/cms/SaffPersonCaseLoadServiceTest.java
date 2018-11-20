package gov.ca.cwds.rest.services.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.cms.StaffPersonCaseLoadDao;
import gov.ca.cwds.fixture.StaffPersonCaseLoadEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad;

/**
 * @author CWDS API Team
 *
 */
public class SaffPersonCaseLoadServiceTest {

  private StaffPersonCaseLoadService staffPersonCaseLoadService;
  private StaffPersonCaseLoadDao staffPersonCaseLoadDao;

  /**
   * @throws Exception - Exception
   */
  @Before
  public void setup() throws Exception {
    staffPersonCaseLoadDao = mock(StaffPersonCaseLoadDao.class);
    staffPersonCaseLoadService = mock(StaffPersonCaseLoadService.class);
    staffPersonCaseLoadService = new StaffPersonCaseLoadService(staffPersonCaseLoadDao);
  }

  /**
   * @throws Exception
   */
  @Test
  public void testFindReturnsCorrectEntity() throws Exception {
    gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad allegation =
        new StaffPersonCaseLoadEntityBuilder().build();
    when(staffPersonCaseLoadDao.find("MLazRFR00E")).thenReturn(allegation);
    StaffPersonCaseLoad found = staffPersonCaseLoadService.find("MLazRFR00E");
    assertThat(found, is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void findReturnsNullWhenNotFound() throws Exception {
    Response found = staffPersonCaseLoadService.find("ABC1234567");
    assertThat(found, is(nullValue()));
  }

  /**
   * 
   */
  @Test(expected = NotImplementedException.class)
  public void notImplementDelete() {
    staffPersonCaseLoadService.delete("something");
  }

  /**
   * 
   */
  @Test(expected = NotImplementedException.class)
  public void notImplementedCreate() {
    staffPersonCaseLoadService.create(new StaffPersonCaseLoad());
  }

  /**
   * 
   */
  @Test(expected = NotImplementedException.class)
  public void shouldNotImplementUpdate() {
    staffPersonCaseLoadService.update("something", new StaffPersonCaseLoad());
  }

}
