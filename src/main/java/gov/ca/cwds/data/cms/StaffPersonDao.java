package gov.ca.cwds.data.cms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.xa.CaresQueryAccelerator;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link StaffPerson}.
 * 
 * @author CWDS API Team
 */
public class StaffPersonDao extends BaseDaoImpl<StaffPerson> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public StaffPersonDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find StaffPersons by id's
   *
   * @param ids Set of StaffPerson id-s
   * @return map where key is a StaffPerson id and value is a StaffPerson itself
   */
  public Map<String, StaffPerson> findByIds(Collection<String> ids) {
    if (ids == null || ids.isEmpty()) {
      return new HashMap<>();
    }
    @SuppressWarnings("unchecked")
    final Query<StaffPerson> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findByIds")).setParameter("ids", ids);
    CaresQueryAccelerator.readOnlyQuery(query);
    return query.list().stream().collect(Collectors.toMap(StaffPerson::getId, s -> s));
  }

}
