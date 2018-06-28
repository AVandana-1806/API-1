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
public class CandaceTransactionImpl implements Transaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(CandaceTransactionImpl.class);

  private final Transaction txn;

  public CandaceTransactionImpl(Transaction txn) {
    this.txn = txn;
  }

  public boolean isXaTransaction() {
    return CandaceSessionFactoryImpl.isXaTransaction();
  }

  @Override
  public void begin() {
    LOGGER.info("\n\t****** CandaceTransactionImpl.begin! ****** \n");
    CaresStackUtils.logStack();
    txn.begin();
  }

  @Override
  public void commit() {
    LOGGER.info("\n\t****** CandaceTransactionImpl.commit! ****** \n");
    CaresStackUtils.logStack();
    txn.commit();
  }

  @Override
  public void rollback() {
    LOGGER.warn("\n\t****** CandaceTransactionImpl.rollback! ****** \n");
    CaresStackUtils.logStack();
    txn.rollback();
  }

  @Override
  public TransactionStatus getStatus() {
    return txn.getStatus();
  }

  @Override
  public void setRollbackOnly() {
    LOGGER.warn("\n\t****** CandaceTransactionImpl.setRollbackOnly! ****** \n");
    txn.setRollbackOnly();
  }

  @Override
  public void registerSynchronization(Synchronization synchronization) throws HibernateException {
    txn.registerSynchronization(synchronization);
  }

  @Override
  public boolean getRollbackOnly() {
    return txn.getRollbackOnly();
  }

  @Override
  public void setTimeout(int seconds) {
    txn.setTimeout(seconds);
  }

  @Override
  public boolean isActive() {
    return txn.isActive();
  }

  @Override
  public int getTimeout() {
    return txn.getTimeout();
  }

  @Override
  public void markRollbackOnly() {
    LOGGER.warn("\n\t****** CandaceTransactionImpl.markRollbackOnly! ****** \n");
    txn.markRollbackOnly();
  }

}
