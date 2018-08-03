package gov.ca.cwds.data.cms;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link Client}.
 *
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class ClientDao extends BaseDaoImpl<Client> {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find Clients by id's
   *
   * @param ids Set of Client id'ss
   * @return map where key is a Client id and value is a Client itself
   */
  public Map<String, Client> findClientsByIds(Collection<String> ids) {
    @SuppressWarnings("unchecked")
    final Query<Client> query = this.grabSession()
        .getNamedQuery(constructNamedQueryName("findByIds")).setParameter("ids", ids);
    return query.list().stream().collect(Collectors.toMap(BaseClient::getId, c -> c));
  }
}
