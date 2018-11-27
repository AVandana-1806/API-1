package gov.ca.cwds.es.live;

import org.hibernate.jdbc.Work;

/**
 * Expose the total number of records processed to Hibernate's Work interface.
 * 
 * @author CWDS API Team
 */
public interface LiveElasticWorkTotal extends Work {

  int getTotalProcessed();

}
