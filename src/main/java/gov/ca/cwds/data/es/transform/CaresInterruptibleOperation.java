package gov.ca.cwds.data.es.transform;

public interface CaresInterruptibleOperation {

  /**
   * Is the rocket still flying?
   * 
   * @return true if rocket has not completed
   */
  boolean isRunning();

  /**
   * Did the rocket fail?
   * 
   * @return true if the rocket has failed
   */
  boolean isFailed();

  /**
   * Mark the rocket as failed and stop the rocket.
   * 
   * <p>
   * Worker threads should stop themselves.
   * </p>
   */
  void fail();

  /**
   * Mark the rocket done. Working threads should stop themselves.
   */
  void done();

  /**
   * Mark Elasticsearch indexing complete.
   */
  void doneIndex();

  /**
   * Mark the retrieval step done.
   */
  void doneRetrieve();

  /**
   * Mark the normalize/transform step done.
   */
  void doneTransform();

}
