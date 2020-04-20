package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;

/**
 * @author CWDS API Team
 */
public class AccessLimitationTest {

  private LimitedAccessType limitedAccessCode = LimitedAccessType.NONE;
  private Date limitedAccessDate = new Date();
  private String limitedAccessDescription = "Some Limited Descrption";
  private SystemCodeDescriptor limitedAccessGovernmentEntity =
      new SystemCodeDescriptor((short) 3225, "Descrption");

  @Test
  public void type() throws Exception {
    assertThat(AccessLimitation.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    AccessLimitation target = new AccessLimitation();
    assertThat(target, notNullValue());
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    AccessLimitation empty = new AccessLimitation();
    assertEquals(empty.getClass(), AccessLimitation.class);
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    AccessLimitation domain = new AccessLimitation(limitedAccessCode, limitedAccessDate,
        limitedAccessDescription, limitedAccessGovernmentEntity);

    assertThat(domain.getLimitedAccessCode(), is(equalTo(limitedAccessCode)));
    assertThat(domain.getLimitedAccessDate(), is(equalTo(limitedAccessDate)));
    assertThat(domain.getLimitedAccessDescription(), is(equalTo(limitedAccessDescription)));
    assertThat(domain.getLimitedAccessGovernmentEntity(),
        is(equalTo(limitedAccessGovernmentEntity)));
  }

}
