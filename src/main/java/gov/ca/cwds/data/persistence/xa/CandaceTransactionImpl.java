package gov.ca.cwds.data.persistence.xa;

import javax.transaction.Synchronization;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class CandaceTransactionImpl implements Transaction {

  private final Transaction txn;

  public CandaceTransactionImpl(Transaction txn) {
    this.txn = txn;
  }

  @Override
  public void begin() {
    txn.begin();
  }

  @Override
  public void commit() {
    txn.commit();
  }

  @Override
  public void rollback() {
    txn.rollback();
  }

  @Override
  public TransactionStatus getStatus() {
    return txn.getStatus();
  }

  @Override
  public void setRollbackOnly() {
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
    txn.markRollbackOnly();
  }

}
