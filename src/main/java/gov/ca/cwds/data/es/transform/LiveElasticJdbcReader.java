package gov.ca.cwds.data.es.transform;

import java.sql.ResultSet;
import java.sql.SQLException;

import gov.ca.cwds.data.persistence.PersistentObject;

@FunctionalInterface
public interface LiveElasticJdbcReader<T extends PersistentObject> {

  /**
   * Read a record or records and return a populated object of type T.
   * 
   * @param rs ResultSet to read from
   * @return the specified type
   * @throws SQLException database or network error
   */
  T read(final ResultSet rs) throws SQLException;

}
