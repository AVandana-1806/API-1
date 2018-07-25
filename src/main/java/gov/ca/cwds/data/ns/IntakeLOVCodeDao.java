package gov.ca.cwds.data.ns;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * @author CWDS API team
 */
public class IntakeLOVCodeDao extends BaseDaoImpl<IntakeLOVCodeEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public IntakeLOVCodeDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find map of IntakeLOVCodeEntity by Set of intake codes.
   *
   * @param intakeCodes Set of intake codes
   * @return map where key is an intake code and value is an IntakeLOVCodeEntity
   */
  @SuppressWarnings("unchecked")
  public Map<String, IntakeLOVCodeEntity> findIntakeLOVCodesByIntakeCodes(Set<String> intakeCodes) {
    if (intakeCodes == null || intakeCodes.isEmpty()) {
      return new HashMap<>();
    }
    final Query<IntakeLOVCodeEntity> query =
        this.grabSession().getNamedQuery(constructNamedQueryName("findIntakeLOVCodesByIntakeCodes"))
            .setReadOnly(true).setCacheable(false).setHibernateFlushMode(FlushMode.MANUAL)
            .setParameter("intakeCodes", intakeCodes);
    return query.list().stream()
        .collect(Collectors.toMap(IntakeLOVCodeEntity::getIntakeCode, c -> c));
  }

}
