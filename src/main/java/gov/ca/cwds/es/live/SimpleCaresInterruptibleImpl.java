package gov.ca.cwds.es.live;

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
  private volatile boolean done = false;

  @Override
  public boolean isRunning() {
    return !done && !fatalError;
  }

  @Override
  public boolean isFailed() {
    return fatalError;
  }

  @Override
  public void fail() {
    fatalError = true;
  }

  @Override
  public void done() {
    done = true;
  }

  @Override
  public void doneRetrieve() {
    done = true;
  }

}
