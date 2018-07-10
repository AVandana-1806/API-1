package gov.ca.cwds.data.persistence.xa;

import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.transaction.backend.jta.internal.JtaTransactionCoordinatorBuilderImpl;
import org.hibernate.resource.transaction.spi.DdlTransactionIsolator;
import org.hibernate.resource.transaction.spi.TransactionCoordinator;
import org.hibernate.resource.transaction.spi.TransactionCoordinatorOwner;
import org.hibernate.tool.schema.internal.exec.JdbcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Monitor JTA managed connections for XA. Changes the default connection handling mode to
 * {@link PhysicalConnectionHandlingMode#DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION}.
 * 
 * @author CWDS API Team
 */
public class CaresJtaTransactionCoordinatorBuilderImpl
    extends JtaTransactionCoordinatorBuilderImpl {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CaresJtaTransactionCoordinatorBuilderImpl.class);

  @Override
  public TransactionCoordinator buildTransactionCoordinator(TransactionCoordinatorOwner owner,
      Options options) {
    LOGGER.debug("CaresJtaTransactionCoordinatorBuilderImpl.buildTransactionCoordinator");
    return super.buildTransactionCoordinator(owner, options);
  }

  @Override
  public boolean isJta() {
    LOGGER.debug("CaresJtaTransactionCoordinatorBuilderImpl.isJta");
    return super.isJta();
  }

  @Override
  public PhysicalConnectionHandlingMode getDefaultConnectionHandlingMode() {
    LOGGER.debug("CaresJtaTransactionCoordinatorBuilderImpl.getDefaultConnectionHandlingMode");
    return PhysicalConnectionHandlingMode.DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION;
  }

  @Override
  public DdlTransactionIsolator buildDdlTransactionIsolator(JdbcContext jdbcContext) {
    LOGGER.debug("CaresJtaTransactionCoordinatorBuilderImpl.buildDdlTransactionIsolator");
    return super.buildDdlTransactionIsolator(jdbcContext);
  }

}
