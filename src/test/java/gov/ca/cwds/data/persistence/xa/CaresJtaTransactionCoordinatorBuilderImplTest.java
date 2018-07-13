package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CaresJtaTransactionCoordinatorBuilderImplTest extends Doofenshmirtz<ClientAddress> {

  CaresJtaTransactionCoordinatorBuilderImpl target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new CaresJtaTransactionCoordinatorBuilderImpl();
  }

  @Test
  public void type() throws Exception {
    assertThat(CaresJtaTransactionCoordinatorBuilderImpl.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CaresJtaTransactionCoordinatorBuilderImpl target =
        new CaresJtaTransactionCoordinatorBuilderImpl();
    assertThat(target, notNullValue());
  }

  // @Test
  // public void buildTransactionCoordinator_A$TransactionCoordinatorOwner$Object() throws Exception
  // {
  // TransactionCoordinatorOwner owner = mock(TransactionCoordinatorOwner.class);
  // TransactionCoordinatorBuilder.Options options =
  // mock(TransactionCoordinatorBuilder.Options.class);
  // TransactionCoordinator actual = target.buildTransactionCoordinator(owner, options);
  // TransactionCoordinator expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  @Test
  public void isJta_A$() throws Exception {
    boolean actual = target.isJta();
    boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDefaultConnectionHandlingMode_A$() throws Exception {
    PhysicalConnectionHandlingMode actual = target.getDefaultConnectionHandlingMode();
    PhysicalConnectionHandlingMode expected =
        PhysicalConnectionHandlingMode.DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION;
    assertThat(actual, is(equalTo(expected)));
  }

  // @Test
  // public void buildDdlTransactionIsolator_A$JdbcContext() throws Exception {
  // JdbcContext jdbcContext = mock(JdbcContext.class);
  // DdlTransactionIsolator actual = target.buildDdlTransactionIsolator(jdbcContext);
  // DdlTransactionIsolator expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

}
