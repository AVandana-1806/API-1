package gov.ca.cwds.data.cms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static final Logger LOGGER = LoggerFactory.getLogger(StaffPersonDao.class);

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
   * @param ids Set of StaffPerson id's
   * @return map where key is a StaffPerson id and value is a StaffPerson itself
   */
  @SuppressWarnings("unchecked")
  public Map<String, StaffPerson> findByIds(Collection<String> ids) {
    if (ids == null || ids.isEmpty()) {
      return new HashMap<>();
    }
    LOGGER.info("StaffPersonDao.findByIds: ids: {}", ids);
    final Query<StaffPerson> query = this.grabSession()
        .getNamedQuery(constructNamedQueryName("findByIds")).setParameter("ids", ids);
    CaresQueryAccelerator.readOnlyQuery(query);
    return query.list().stream().collect(Collectors.toMap(StaffPerson::getId, s -> s));
  }

}
