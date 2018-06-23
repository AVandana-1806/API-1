package gov.ca.cwds.data.persistence.xa;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.EntityType;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.StatelessSessionBuilder;
import org.hibernate.TypeHelper;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

import gov.ca.cwds.inject.FerbHibernateBundle;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import io.dropwizard.hibernate.HibernateBundle;

/**
 * Ferb {@link SessionFactory} facade.
 * <a href="http://phineasandferb.wikia.com/wiki/Candace_Flynn">Candace</a> is the emotionally
 * volatile sister of Phineas and Ferb. She always tries -- and fails -- to prove her brothers'
 * involvement in grand activities.
 * 
 * <p>
 * <a href="https://www.youtube.com/watch?v=0ZzN83mWpUo">More Phineas, less Candace.</a>
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"deprecation", "rawtypes"}) // SessionFactory method signatures
public class CandaceSessionFactoryImpl implements SessionFactory {

  private static final long serialVersionUID = 1L;

  private SessionFactory normSessionFactory;
  private SessionFactory xaSessionFactory;

  private HibernateBundle<ApiConfiguration> hibernateBundle;
  private FerbHibernateBundle xaHibernateBundle;

  public CandaceSessionFactoryImpl(SessionFactory normSessionFactory,
      SessionFactory xaSessionFactory) {
    super();
    this.normSessionFactory = normSessionFactory;
    this.xaSessionFactory = xaSessionFactory;
  }

  public CandaceSessionFactoryImpl(HibernateBundle<ApiConfiguration> hibernateBundle,
      FerbHibernateBundle xaHibernateBundle) {
    super();
    this.hibernateBundle = hibernateBundle;
    this.xaHibernateBundle = xaHibernateBundle;
  }

  /**
   * Determine which type of transaction to use.
   * 
   * @return smart {@link SessionFactory}
   */
  protected SessionFactory pick() {
    if (normSessionFactory == null) {
      init();
    }
    return RequestExecutionContext.instance().isXaTransaction() ? xaSessionFactory
        : normSessionFactory;
  }

  protected synchronized void init() {
    if (normSessionFactory == null || xaSessionFactory == null) {
      this.normSessionFactory = hibernateBundle.getSessionFactory();
      this.xaSessionFactory = xaHibernateBundle.getSessionFactory();
    }
  }

  // ==================================
  // SessionFactory delegates:
  // ==================================

  @Override
  public SessionFactoryImplementor getSessionFactory() {
    return pick().getSessionFactory();
  }

  @Override
  public Reference getReference() throws NamingException {
    return pick().getReference();
  }

  @Override
  public EntityManager createEntityManager() {
    return pick().createEntityManager();
  }

  @Override
  public <T> List<EntityGraph<? super T>> findEntityGraphsByType(Class<T> entityClass) {
    return pick().findEntityGraphsByType(entityClass);
  }

  @Override
  public SessionFactoryOptions getSessionFactoryOptions() {
    return pick().getSessionFactoryOptions();
  }

  @Override
  public EntityManager createEntityManager(Map map) {
    return pick().createEntityManager(map);
  }

  @Override
  public SessionBuilder withOptions() {
    return pick().withOptions();
  }

  @Override
  public Session openSession() throws HibernateException {
    return pick().openSession();
  }

  @Override
  public EntityType getEntityTypeByName(String entityName) {
    return pick().getEntityTypeByName(entityName);
  }

  @Override
  public EntityManager createEntityManager(SynchronizationType synchronizationType) {
    return pick().createEntityManager(synchronizationType);
  }

  @Override
  public Session getCurrentSession() throws HibernateException {
    return pick().getCurrentSession();
  }

  @Override
  public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
    return pick().createEntityManager(synchronizationType, map);
  }

  @Override
  public StatelessSessionBuilder withStatelessOptions() {
    return pick().withStatelessOptions();
  }

  @Override
  public StatelessSession openStatelessSession() {
    return pick().openStatelessSession();
  }

  @Override
  public StatelessSession openStatelessSession(Connection connection) {
    return pick().openStatelessSession(connection);
  }

  @Override
  public Statistics getStatistics() {
    return pick().getStatistics();
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    return pick().getCriteriaBuilder();
  }

  @Override
  public org.hibernate.Metamodel getMetamodel() {
    return pick().getMetamodel();
  }

  @Override
  public boolean isClosed() {
    return pick().isClosed();
  }

  @Override
  public boolean isOpen() {
    return pick().isOpen();
  }

  @Override
  public Cache getCache() {
    return pick().getCache();
  }

  @Override
  public Set getDefinedFilterNames() {
    return pick().getDefinedFilterNames();
  }

  @Override
  public void close() {
    pick().close();
  }

  @Override
  public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
    return pick().getFilterDefinition(filterName);
  }

  @Override
  public boolean containsFetchProfileDefinition(String name) {
    return pick().containsFetchProfileDefinition(name);
  }

  @Override
  public Map<String, Object> getProperties() {
    return pick().getProperties();
  }

  @Override
  public TypeHelper getTypeHelper() {
    return pick().getTypeHelper();
  }

  @Override
  public ClassMetadata getClassMetadata(Class entityClass) {
    return pick().getClassMetadata(entityClass);
  }

  @Override
  public PersistenceUnitUtil getPersistenceUnitUtil() {
    return pick().getPersistenceUnitUtil();
  }

  @Override
  public ClassMetadata getClassMetadata(String entityName) {
    return pick().getClassMetadata(entityName);
  }

  @Override
  public void addNamedQuery(String name, Query query) {
    pick().addNamedQuery(name, query);
  }

  @Override
  public CollectionMetadata getCollectionMetadata(String roleName) {
    return pick().getCollectionMetadata(roleName);
  }

  @Override
  public Map<String, ClassMetadata> getAllClassMetadata() {
    return pick().getAllClassMetadata();
  }

  @Override
  public <T> T unwrap(Class<T> cls) {
    return pick().unwrap(cls);
  }

  @Override
  public Map getAllCollectionMetadata() {
    return pick().getAllCollectionMetadata();
  }

  @Override
  public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
    pick().addNamedEntityGraph(graphName, entityGraph);
  }

}