package gov.ca.cwds.data.es.transform;

public class SimpleCaresInterruptibleImpl implements CaresInterruptibleOperation {

  /**
   * Completion flag for fatal errors.
   * <p>
   * Volatile guarantees that changes to this flag become visible other threads immediately. In
   * other words, threads don't cache a copy of this variable in their local memory for performance.
   * </p>
   */
  private volatile boolean fatalError = false;

  /**
   * Completion flag for data retrieval.
   */
  private volatile boolean doneRetrieve = false;

  @Override
  public boolean isRunning() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isFailed() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void fail() {
    // TODO Auto-generated method stub

  }

  @Override
  public void done() {
    // TODO Auto-generated method stub

  }

  @Override
  public void doneRetrieve() {
    // TODO Auto-generated method stub

  }

}
