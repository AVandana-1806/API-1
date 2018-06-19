package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;

public class SpecialProjectTest {

  private String id = "1234567ABC";
  private String lastUpdatedId = "aab";
  private LocalDateTime localDateTime = LocalDateTime.now();
  private Boolean archiveAssociationIndicator = Boolean.FALSE;
  private String projectDescription = "special project description";
  private LocalDate endDate = null;
  private Short governmentEntityType = 1084;
  private String name = "S-CESC";
  private LocalDate startDate = LocalDate.now();
  
  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(SpecialProject.class.newInstance(), is(notNullValue()));
  }
  
  @Test
  public void testSettersGetters() throws Exception {
    SpecialProject sp = new SpecialProject();
    sp.setArcassIndicator(archiveAssociationIndicator);
    assertThat(sp.getArrchiveAssociationIndicator(), is(equalTo(archiveAssociationIndicator)));
    sp.setEndDate(endDate);
    assertThat(sp.getEndDate(), is(equalTo(endDate)));
    sp.setGovernmentEntityType(governmentEntityType);
    assertThat(sp.getGovernmentEntityType(), is(equalTo(governmentEntityType)));
    sp.setId(id);
    assertThat(sp.getId(), is(equalTo(id)));
    sp.setLastUpdateId(lastUpdatedId);
    assertThat(sp.getLastUpdateId(), is(equalTo(lastUpdatedId)));
    sp.setLastUpdateTime(localDateTime);
    assertThat(sp.getLastUpdateTime(), is(equalTo(localDateTime)));
    sp.setName(name);
    assertThat(sp.getName(), is(equalTo(name)));
    sp.setProjectDescription(projectDescription);
    assertThat(sp.getProjectDescription(), is(equalTo(projectDescription)));
    sp.setStartDate(startDate);
    assertThat(sp.getStartDate(), is(equalTo(startDate)));
    
  }
}
