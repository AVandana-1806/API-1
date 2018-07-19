package gov.ca.cwds.data.persistence.xa;

import javax.transaction.Synchronization;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.CaresStackUtils;

/**
 * Hibernate {@link Transaction} connection facade.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"deprecation", "rawtypes", "findbugs:SE_BAD_FIELD",
    "squid:CallToDeprecatedMethod", "squid:RedundantThrowsDeclarationCheck",
    "findsecbugs:SQL_INJECTION_JDBC"})
public class CandaceTransactionImpl implements Transaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(CandaceTransactionImpl.class);

  private final Transaction txn;

  /**
   * Ctor wraps a Hibernate transaction.
   * 
   * @param txn Hibernate transaction
   */
  public CandaceTransactionImpl(Transaction txn) {
    LOGGER.debug("CandaceTransactionImpl.ctor");
    this.txn = txn;
    this.txn.setTimeout(90); // NEXT: soft-code
  }

  public boolean isXaTransaction() {
    return CandaceSessionFactoryImpl.isXaTransaction();
  }

  @Override
  public void begin() {
    LOGGER.info("CandaceTransactionImpl.begin");
    CaresStackUtils.logStack();
    txn.begin();
  }

  @Override
  public void commit() {
    LOGGER.info("CandaceTransactionImpl.commit");
    CaresStackUtils.logStack();
    //txn.commit(); // TODO: revert after implementation
  }

  @Override
  public void rollback() {
    LOGGER.info("CandaceTransactionImpl.rollback");
    CaresStackUtils.logStack();
    txn.rollback();
  }

  @Override
  public TransactionStatus getStatus() {
    LOGGER.trace("CandaceTransactionImpl.getStatus");
    return txn.getStatus();
  }

  @Override
  public void setRollbackOnly() {
    LOGGER.info("CandaceTransactionImpl.setRollbackOnly");
    txn.setRollbackOnly();
  }

  @Override
  public void registerSynchronization(Synchronization synchronization) throws HibernateException {
    LOGGER.trace("CandaceTransactionImpl.registerSynchronization");
    txn.registerSynchronization(synchronization);
  }

  @Override
  public boolean getRollbackOnly() {
    LOGGER.trace("CandaceTransactionImpl.getRollbackOnly");
    return txn.getRollbackOnly();
  }

  @Override
  public void setTimeout(int seconds) {
    LOGGER.info("CandaceTransactionImpl.setTimeout: seconds: {}", seconds);
    txn.setTimeout(seconds);
  }

  @Override
  public boolean isActive() {
    LOGGER.trace("CandaceTransactionImpl.isActive");
    return txn.isActive();
  }

  @Override
  public int getTimeout() {
    LOGGER.debug("CandaceTransactionImpl.getTimeout");
    return txn.getTimeout();
  }

  @Override
  public void markRollbackOnly() {
    LOGGER.info("CandaceTransactionImpl.markRollbackOnly");
    txn.markRollbackOnly();
  }

  @Override
  protected void finalize() throws Throwable {
    LOGGER.info("CandaceTransactionImpl.finalize");
    super.finalize();
  }

}
