package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

import gov.ca.cwds.fixture.AddressResourceBuilder;

@SuppressWarnings("javadoc")
public class AddressTest {

  private Long id = (long) 12345;
  private String streetAddress = "123 first street";
  private String city = "surf city";
  private String state = "CA";
  private String zip = "98765";
  private String type = "home";

  private String staffId = "0X5";

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Address.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testConstructor() throws Exception {
    Address address = new Address(id, streetAddress, city, state, zip, type);
    assertThat(address.getId(), is(equalTo(id)));
    assertThat(address.getPrimaryKey(), is(equalTo(id)));
    assertThat(address.getStreetAddress(), is(equalTo(streetAddress)));
    assertThat(address.getCity(), is(equalTo(city)));
    assertThat(address.getState(), is(equalTo(state)));
    assertThat(address.getZip(), is(equalTo(zip)));
    assertThat(address.getType(), is(equalTo(type)));
    assertThat(address.getPersonAddress().isEmpty(), is(Boolean.TRUE));
  }

  @Test
  public void testConstructorDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.Address domainAddress =
        new AddressResourceBuilder().createAddress();
    Address address = new Address(domainAddress, staffId, staffId);
    assertThat(address.getStreetAddress(), is(equalTo(domainAddress.getStreetAddress())));
    assertThat(address.getCity(), is(equalTo(domainAddress.getCity())));
    assertThat(address.getState(), is(equalTo(domainAddress.getState().toString())));
    assertThat(address.getZip(), is(equalTo(domainAddress.getZip())));
    assertThat(address.getType(), is(equalTo(domainAddress.getType().toString())));
  }

  @Test
  public void testConstructorWithNullValues() throws Exception {
    gov.ca.cwds.rest.api.domain.Address domainAddress =
        new AddressResourceBuilder().setState(null).setType(null).createAddress();
    Address address = new Address(domainAddress, staffId, staffId);
    assertThat(address.getState(), is(equalTo("0")));
    assertThat(address.getType(), is(equalTo("0")));
  }
}
