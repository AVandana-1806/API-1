package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class IdTest {

  private String identifier = "123";

  @Test
  public void type() throws Exception {
    assertThat(Id.class, notNullValue());
  }

  @Test
  public void constructorTest() throws Exception {

    Id domain = new Id(identifier);

    assertThat(domain.getIdentifier(), is(equalTo(identifier)));
  }

  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(Id.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }


}
