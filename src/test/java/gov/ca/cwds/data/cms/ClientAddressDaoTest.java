package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public abstract class ClientAddressDaoTest extends Doofenshmirtz<ClientAddress> {

  ClientAddressDao target;
  Query<ClientAddress> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new ClientAddressDao(sessionFactory);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(ClientAddressDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findByAddressAndClient_A$String$String() throws Exception {
    String addressId = DEFAULT_CLIENT_ID;
    String clientId = DEFAULT_CLIENT_ID;
    List<ClientAddress> actual = target.findByAddressAndClient(addressId, clientId);
    List<ClientAddress> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = ServiceException.class)
  public void findByAddressAndClient_explode() throws Exception {
    when(sessionFactory.getCurrentSession()).thenThrow(SQLException.class);
    String addressId = DEFAULT_CLIENT_ID;
    String clientId = DEFAULT_CLIENT_ID;
    List<ClientAddress> actual = target.findByAddressAndClient(addressId, clientId);
    List<ClientAddress> expected = new ArrayList<>();
    assertFalse(true);
    assertThat(actual, is(equalTo(expected)));
  }

}
