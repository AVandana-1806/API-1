package gov.ca.cwds.data.persistence.xa;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockOptions;
import org.hibernate.query.Query;

/**
 * Make Hibernate queries read-only.
 * 
 * @author CWDS API Team
 */
public class CaresQueryAccelerator {

  /**
   * Make a Hibernate query read-only.
   * 
   * @param q query to make read-only
   * @see #optimizeQuery(Query)
   */
  public static void readOnlyQuery(Query<?> q) {
    optimizeQuery(q);
    q.setReadOnly(true);
  }

  /**
   * Optimize a Hibernate query for batch performance. Disable Hibernate caching, set flush mode to
   * manual, and set fetch size to {@code NeutronIntegerDefaults.FETCH_SIZE}.
   * 
   * @param q query to optimize
   */
  public static void optimizeQuery(Query<?> q) {
    q.setCacheable(true);
    q.setCacheMode(CacheMode.NORMAL);
    q.setFetchSize(100);
    q.setHibernateFlushMode(FlushMode.MANUAL);
    q.setTimeout(90); // 90 seconds tops
    q.setLockOptions(LockOptions.READ);
  }

}
