package gov.ca.cwds.es.live;

import gov.ca.cwds.data.std.ApiMarker;

public interface CaresInterruptibleOperation extends ApiMarker {

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
   * Mark the retrieval step done.
   */
  void doneRetrieve();

}
