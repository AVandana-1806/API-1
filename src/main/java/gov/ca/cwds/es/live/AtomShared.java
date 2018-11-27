package gov.ca.cwds.es.live;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.std.ApiMarker;

/**
 * Shared features of all Elasticsearch indexer rockets.
 * 
 * @author CWDS API Team
 * @see NeutronThreadUtils
 */
public interface AtomShared extends ApiMarker {

  /**
   * Make logger available to interfaces.
   * 
   * @return SLF4J logger
   */
  Logger getLogger();

  /**
   * Elasticsearch operations.
   * 
   * @return Elasticsearch DAO
   */
  ElasticsearchDao getEsDao();

  /**
   * Jackson ObjectMapper suitable for Elasticsearch document operations.
   * 
   * @return Jackson ObjectMapper
   */
  ObjectMapper getMapper();

  /**
   * Common method sets the thread's name.
   * 
   * @param title title of thread
   */
  default void nameThread(final String title) {
    // N/A in application servers. In batch processes, name the thread.
  }

}
