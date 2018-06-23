package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.Test;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SpecialProjectTest {

  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
  
  private Boolean archiveAssociationIndicator = Boolean.FALSE;
  private String description = "special project description";
  private String endDateString = "2018-05-30";
  private LocalDate endDate = null;
  private Short governmentEntityType = 1084;
  private String name = "S-CESC";
  private String startDateString = "2018-05-01";
  private LocalDate startDate = LocalDate.now();
  private String id = "1234567ABC";
  
  @Test
  public void testConstructor() throws Exception {
    SpecialProject sp = new SpecialProject(archiveAssociationIndicator, description,
        endDateString, governmentEntityType, name, startDateString);
    assertThat(sp.getArchiveAssociationIndicator(), is(equalTo(archiveAssociationIndicator)));
    assertThat(sp.getDescription(), is(equalTo(description)));
    assertThat(sp.getEndDate(), is(equalTo(endDateString)));
    assertThat(sp.getGovernmentEntityType(), is(equalTo(governmentEntityType)));
    assertThat(sp.getName(), is(equalTo(name)));
    assertThat(sp.getStartDate(), is(equalTo(startDateString)));
  }
  
  @Test
  public void testConstructorUsingPersistentObject() throws Exception {
    gov.ca.cwds.data.legacy.cms.entity.SpecialProject persistent = 
        new gov.ca.cwds.data.legacy.cms.entity.SpecialProject(archiveAssociationIndicator, 
            description, endDate, governmentEntityType, id, name, startDate);
    
    SpecialProject sp = new SpecialProject(persistent);
    assertThat(sp.getArchiveAssociationIndicator(), is(equalTo(persistent.getArrchiveAssociationIndicator())));
    assertThat(sp.getDescription(), is(equalTo(persistent.getProjectDescription())));
    assertThat(sp.getEndDate(), is(equalTo(cookLocalDate(persistent.getEndDate()))));
    assertThat(sp.getGovernmentEntityType(), is(equalTo(persistent.getGovernmentEntityType())));
    assertThat(sp.getName(), is(equalTo(persistent.getName())));
    assertThat(sp.getStartDate(), is(equalTo(cookLocalDate(persistent.getStartDate()))));
  }
  
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SpecialProject.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

  private String cookLocalDate(LocalDate date) {
    if (date != null) {
      String newDate = date.format(formatter);
      return newDate;
    }
    return null;
  }

}
