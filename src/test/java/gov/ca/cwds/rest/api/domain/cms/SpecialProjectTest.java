package gov.ca.cwds.rest.api.domain.cms;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.rest.api.domain.DomainChef;
import io.dropwizard.validation.ConstraintViolations;
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
  private String name = "S-CSEC Referral";
  private String startDateString = "2018-05-01";
  private LocalDate startDate = LocalDate.now();
  private String id = "1234567ABC";
  
  private Validator validator;
  
  @Before
  public void setup(){
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void descriptionShouldErrorWhenNull() {
    SpecialProject sp = new SpecialProject(Boolean.TRUE, null,
        endDateString, governmentEntityType, name, startDateString);
    Set<ConstraintViolation<SpecialProject>> errors = validator.validate(sp);
    assertThat(ConstraintViolations.format(errors)).containsOnly("description may not be null");
    
  }
  
  @Test
  public void governmentEntityTypeShouldErrorWhenNull() {
    SpecialProject sp = new SpecialProject(Boolean.TRUE, description,
        endDateString, null, name, startDateString);
    Set<ConstraintViolation<SpecialProject>> errors = validator.validate(sp);
    assertThat(ConstraintViolations.format(errors)).containsOnly("governmentEntityType may not be null");
    
  }
  
  @Test
  public void nameShouldErrorWhenNull() {
    SpecialProject sp = new SpecialProject(archiveAssociationIndicator, description,
        endDateString, governmentEntityType, null, startDateString);
    Set<ConstraintViolation<SpecialProject>> errors = validator.validate(sp);
    assertThat(ConstraintViolations.format(errors)).containsOnly("name may not be null");
   
  }
  
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
