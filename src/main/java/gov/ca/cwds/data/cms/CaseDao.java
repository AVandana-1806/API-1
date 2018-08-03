package gov.ca.cwds.data.cms;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link CmsCase}.
 * 
 * @author CWDS API Team
 */
public class CaseDao extends CrudsDaoImpl<CmsCase> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CaseDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find by Client Ids
   * 
   * @param clientIds - the client Ids
   * @return map with all the cases for all the clients
   */
  public Map<String, CmsCase> findByClientIds(Collection<String> clientIds) {
    @SuppressWarnings("unchecked")
    final Query<CmsCase> query = this.grabSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.CmsCase.findByClientIds");
    query.setParameterList("clientIds", clientIds, StringType.INSTANCE);
    return query.list().stream().collect(Collectors.toMap(CmsCase::getId, c -> c));
  }

  /**
   * Find all related cases by Victim Client Id
   * 
   * @param clientId - the victim client Id
   * @return all cases for all related client including given client id
   */
  public CmsCase[] findAllRelatedByVictimClientId(String clientId) {
    @SuppressWarnings("unchecked")
    final NativeQuery<CmsCase> query = this.grabSession().getNamedNativeQuery(
        "gov.ca.cwds.data.persistence.cms.CmsCase.findAllRelatedByVictimClientId");
    query.setParameter("clientId", clientId, StringType.INSTANCE);
    return query.list().toArray(new CmsCase[0]);
  }

}
