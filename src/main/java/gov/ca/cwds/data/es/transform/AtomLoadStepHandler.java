package gov.ca.cwds.data.es.transform;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Interface defines load steps, primarily for initial (full) mode. In last change mode, the range
 * is arbitrary (like 'a' - 'b'), since range only applies to initial mode.
 * 
 * @author CWDS API Team
 * @param <N> persistent, normalized type
 */
public interface AtomLoadStepHandler<N extends PersistentObject> {

  /**
   * Execute arbitrary JDBC as needed on the same connection.
   * 
   * @param con database connection
   * @throws SQLException on database error
   */
  default void handleMainJdbc(final Connection con)
      throws SQLException {
    // Default is no-op.
  }

  /**
   * Begin step, before initial load key range processing begins. Allocate resources. Default
   * implementation is no-op.
   */
  default void handleStart() {
    // Default is no-op.
  }

  /**
   * Terminal step, after after initial load key range processing completes. De-allocate resources.
   * Default implementation is no-op.
   */
  default void handleFinish() {
    // Default is no-op.
  }

  /**
   * Intermediate step, after {@link Connection#commit()} and before
   * {@link #handleFinish()}. Process data, such as normalization. Default implementation
   * is no-op.
   */
  default void handleJdbcDone() {
    // Default is no-op.
  }

  /**
   * Return the handler's results.
   * 
   * @return clean, normalized results
   */
  default List<N> getResults() {
    return new ArrayList<>();
  }

  /**
   * Retrieve records from Hibernate, JDBC, or whatever.
   * 
   * @param lastRunDate last successful run date-time
   * @param deletionResults sensitive records to delete, if any
   * @return list of normalized records, ready for Elasticsearch
   */
  default List<N> fetchLastRunNormalizedResults(final Date lastRunDate,
      final Set<String> deletionResults) {
    return new ArrayList<>();
  }

}
