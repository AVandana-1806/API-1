package gov.ca.cwds.data.persistence.xa;

import java.io.Serializable;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.CaresStackUtils;
import gov.ca.cwds.inject.FerbHibernateBundle;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContextCallback;
import gov.ca.cwds.rest.filters.RequestExecutionContextRegistry;
import io.dropwizard.hibernate.HibernateBundle;

/**
 * Ferb smart {@link SessionFactory} facade, which chooses between XA and non-XA SessionFactory
 * implementations. <a href="http://phineasandferb.wikia.com/wiki/Candace_Flynn">Candace</a> is the
 * emotionally volatile sister of Phineas and Ferb. She always tries -- and fails -- to prove her
 * brothers' involvement in grand activities.
 * 
 * <p>
 * <a href="https://www.youtube.com/watch?v=0ZzN83mWpUo">More Phineas, less Candace.</a>
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"deprecation", "rawtypes", "findbugs:SE_BAD_FIELD",
    "squid:CallToDeprecatedMethod", "squid:RedundantThrowsDeclarationCheck",
    "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED"})
public class CandaceSessionFactoryImpl implements SessionFactory, RequestExecutionContextCallback {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CandaceSessionFactoryImpl.class);

  private String sessionFactoryName;
  private SessionFactory normSessionFactory;
  private SessionFactory xaSessionFactory;

  // Only works for the same datasource, for which this class is a facade.
  private transient ThreadLocal<CandaceSessionImpl> local = new ThreadLocal<>();

  private transient HibernateBundle<ApiConfiguration> hibernateBundle;
  private transient FerbHibernateBundle xaHibernateBundle;

  public CandaceSessionFactoryImpl(String sessionFactoryName, SessionFactory normSessionFactory,
      SessionFactory xaSessionFactory) {
    super();
    this.sessionFactoryName = makeSessionFactoryName(sessionFactoryName);
    this.normSessionFactory = normSessionFactory;
    this.xaSessionFactory = xaSessionFactory;
  }

  public CandaceSessionFactoryImpl(HibernateBundle<ApiConfiguration> hibernateBundle,
      FerbHibernateBundle xaHibernateBundle) {
    super();
    this.sessionFactoryName = makeSessionFactoryName(xaHibernateBundle.name());
    this.hibernateBundle = hibernateBundle;
    this.xaHibernateBundle = xaHibernateBundle;
  }

  /**
   * Is this request currently in an XA transaction?
   * 
   * @return true = request is using XA
   */
  public static boolean isXaTransaction() {
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    return ctx != null && ctx.isXaTransaction();
  }

  /**
   * Pick a transaction type.
   * 
   * @return smart {@link SessionFactory}
   */
  protected SessionFactory pick() {
    if (normSessionFactory == null || xaSessionFactory == null) {
      init();
    }

    return isXaTransaction() ? xaSessionFactory : normSessionFactory;
  }

  protected synchronized void init() {
    if (normSessionFactory == null || xaSessionFactory == null) {
      this.normSessionFactory = hibernateBundle.getSessionFactory();
      this.xaSessionFactory = xaHibernateBundle.getSessionFactory();

      // Notify this instance upon request start and end.
      RequestExecutionContextRegistry.registerCallback(this);
    }
  }

  // ==================================
  // RequestExecutionContextCallback:
  // ==================================

  protected final String makeSessionFactoryName(String name) {
    return "candace_session_factory_" + name;
  }

  @Override
  public Serializable key() {
    return sessionFactoryName;
  }

  @Override
  public void startRequest(RequestExecutionContext ctx) {
    LOGGER.debug("CandaceSessionFactoryImpl.startRequest");
    local.set(null); // clear the current thread
  }

  @Override
  public void endRequest(RequestExecutionContext ctx) {
    LOGGER.debug("CandaceSessionFactoryImpl.endRequest");
    local.set(null); // clear the current thread
  }

  // ==================================
  // SessionFactory delegates:
  // ==================================

  @Override
  public SessionFactoryImplementor getSessionFactory() {
    LOGGER.trace("CandaceSessionFactoryImpl.getSessionFactory");
    return pick().getSessionFactory();
  }

  @Override
  public Reference getReference() throws NamingException {
    LOGGER.trace("CandaceSessionFactoryImpl.getReference");
    return pick().getReference();
  }

  @Override
  public EntityManager createEntityManager() {
    LOGGER.trace("CandaceSessionFactoryImpl.createEntityManager");
    return pick().createEntityManager();
  }

  @Override
  public <T> List<EntityGraph<? super T>> findEntityGraphsByType(Class<T> entityClass) {
    LOGGER.trace("CandaceSessionFactoryImpl.findEntityGraphsByType: entityClass: {}",
        entityClass.getName());
    return pick().findEntityGraphsByType(entityClass);
  }

  @Override
  public SessionFactoryOptions getSessionFactoryOptions() {
    LOGGER.info("CandaceSessionFactoryImpl.getSessionFactoryOptions");
    return pick().getSessionFactoryOptions();
  }

  @Override
  public EntityManager createEntityManager(Map map) {
    LOGGER.info("CandaceSessionFactoryImpl.createEntityManager");
    return pick().createEntityManager(map);
  }

  @Override
  public SessionBuilder withOptions() {
    LOGGER.info("CandaceSessionFactoryImpl.withOptions");
    return pick().withOptions();
  }

  @Override
  public Session openSession() throws HibernateException {
    LOGGER.debug("CandaceSessionFactoryImpl.openSession");

    CandaceSessionImpl session = local.get();
    if (session == null) {
      LOGGER.info("CandaceSessionFactoryImpl.openSession: opening a new session");
      session = new CandaceSessionImpl(pick().openSession());
      local.set(session);
    }

    return session;
  }

  @Override
  public EntityType getEntityTypeByName(String entityName) {
    LOGGER.info("CandaceSessionFactoryImpl.getEntityTypeByName");
    return pick().getEntityTypeByName(entityName);
  }

  @Override
  public EntityManager createEntityManager(SynchronizationType synchronizationType) {
    return pick().createEntityManager(synchronizationType);
  }

  @Override
  public Session getCurrentSession() throws HibernateException {
    LOGGER.debug("CandaceSessionFactoryImpl.getCurrentSession");

    Session session = local.get();
    if (session == null) {
      LOGGER.info("CandaceSessionFactoryImpl.getCurrentSession: opening a new session");
      session = openSession();
    }

    return session;
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
    LOGGER.info("CandaceSessionFactoryImpl.openStatelessSession");
    return pick().openStatelessSession();
  }

  @Override
  public StatelessSession openStatelessSession(Connection connection) {
    LOGGER.info("CandaceSessionFactoryImpl.openStatelessSession(con)");
    return pick().openStatelessSession(connection);
  }

  @Override
  public Statistics getStatistics() {
    // IDEA: separate and store statistics by request.
    LOGGER.debug("CandaceSessionFactoryImpl.getStatistics");
    return pick().getStatistics();
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    return pick().getCriteriaBuilder();
  }

  @Override
  public org.hibernate.Metamodel getMetamodel() {
    LOGGER.trace("CandaceSessionFactoryImpl.getMetamodel");
    return pick().getMetamodel();
  }

  @Override
  public boolean isClosed() {
    final boolean ret = pick().isClosed();
    LOGGER.trace("CandaceSessionFactoryImpl.isClosed: {}", ret);
    return ret;
  }

  @Override
  public boolean isOpen() {
    final boolean ret = pick().isOpen();
    LOGGER.trace("CandaceSessionFactoryImpl.isOpen: {}", ret);
    return ret;
  }

  @Override
  public Cache getCache() {
    LOGGER.trace("CandaceSessionFactoryImpl.getCache");
    return pick().getCache();
  }

  @Override
  public Set getDefinedFilterNames() {
    LOGGER.trace("CandaceSessionFactoryImpl.getDefinedFilterNames");
    return pick().getDefinedFilterNames();
  }

  @Override
  public void close() {
    LOGGER.info("******** CandaceSessionFactoryImpl.close ********");
    local.set(null);
    pick().close();
    CaresStackUtils.logStack();
  }

  @Override
  public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
    LOGGER.trace("CandaceSessionFactoryImpl.getFilterDefinition: filterName: {}", filterName);
    return pick().getFilterDefinition(filterName);
  }

  @Override
  public boolean containsFetchProfileDefinition(String name) {
    LOGGER.trace("CandaceSessionFactoryImpl.containsFetchProfileDefinition: name: {}", name);
    return pick().containsFetchProfileDefinition(name);
  }

  @Override
  public Map<String, Object> getProperties() {
    LOGGER.trace("CandaceSessionFactoryImpl.getProperties");
    return pick().getProperties();
  }

  @Override
  public TypeHelper getTypeHelper() {
    LOGGER.trace("CandaceSessionFactoryImpl.getTypeHelper");
    return pick().getTypeHelper();
  }

  @Override
  public ClassMetadata getClassMetadata(Class entityClass) {
    LOGGER.trace("CandaceSessionFactoryImpl.getClassMetadata: entityClass: {}",
        entityClass.getName());
    return pick().getClassMetadata(entityClass);
  }

  @Override
  public PersistenceUnitUtil getPersistenceUnitUtil() {
    LOGGER.trace("CandaceSessionFactoryImpl.getPersistenceUnitUtil");
    return pick().getPersistenceUnitUtil();
  }

  @Override
  public ClassMetadata getClassMetadata(String entityName) {
    LOGGER.trace("CandaceSessionFactoryImpl.getClassMetadata: entityName: {}", entityName);
    return pick().getClassMetadata(entityName);
  }

  @Override
  public void addNamedQuery(String name, Query query) {
    LOGGER.trace("CandaceSessionFactoryImpl.addNamedQuery: name: {}", name);
    pick().addNamedQuery(name, query);
  }

  @Override
  public CollectionMetadata getCollectionMetadata(String roleName) {
    LOGGER.trace("CandaceSessionFactoryImpl.getCollectionMetadata: roleName: {}", roleName);
    return pick().getCollectionMetadata(roleName);
  }

  @Override
  public Map<String, ClassMetadata> getAllClassMetadata() {
    LOGGER.trace("CandaceSessionFactoryImpl.getAllClassMetadata");
    return pick().getAllClassMetadata();
  }

  @Override
  public <T> T unwrap(Class<T> cls) {
    LOGGER.trace("CandaceSessionFactoryImpl.unwrap: cls: {}", cls.getName());
    return pick().unwrap(cls);
  }

  @Override
  public Map getAllCollectionMetadata() {
    LOGGER.trace("CandaceSessionFactoryImpl.getAllCollectionMetadata");
    return pick().getAllCollectionMetadata();
  }

  @Override
  public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
    LOGGER.trace("CandaceSessionFactoryImpl.addNamedEntityGraph: graphName: {}", graphName);
    pick().addNamedEntityGraph(graphName, entityGraph);
  }

  public String getSessionFactoryName() {
    LOGGER.info("CandaceSessionFactoryImpl.getSessionFactoryName: sessionFactoryName: {}",
        sessionFactoryName);
    return sessionFactoryName;
  }

}
