package gov.ca.cwds.data.persistence.xa;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.LobHelper;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionEventListener;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionBuilder;
import org.hibernate.SimpleNaturalIdLoadAccess;
import org.hibernate.Transaction;
import org.hibernate.TypeHelper;
import org.hibernate.UnknownProfileException;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.stat.SessionStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.CaresStackUtils;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Hibernate session facade that adds logging and facilitates XA transactions.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"deprecation", "rawtypes", "findbugs:SE_BAD_FIELD",
    "squid:CallToDeprecatedMethod", "squid:RedundantThrowsDeclarationCheck",
    "findsecbugs:SQL_INJECTION_JDBC"})
public class CandaceSessionImpl implements Session {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CandaceSessionImpl.class);

  protected Session session;

  protected transient CandaceTransactionImpl txn;

  public CandaceSessionImpl(Session session) {
    LOGGER.debug("CandaceSessionImpl.ctor");
    this.session = session;
  }

  /**
   * Log the stack of method calls that brought us to this point. Shows who called what and in what
   * order.
   * 
   * @param obj persistent object
   * @param methodMsg calling method
   */
  protected void logStack(Object obj, String methodMsg) {
    if (LOGGER.isTraceEnabled()) {
      if (obj instanceof PersistentObject) {
        final PersistentObject po = (PersistentObject) obj;
        LOGGER.info("CandaceSessionImpl.{}: class: {}, key: {}", methodMsg, po.getClass(),
            po.getPrimaryKey());
      } else {
        LOGGER.info("CandaceSessionImpl.{}", methodMsg);
      }
      CaresStackUtils.logStack();
    }
  }

  protected void logStack(String methodMsg) {
    logStack(null, methodMsg);
  }

  /**
   * Is this session currently participating in an XA distributed transaction?
   * 
   * @return true = in XA transaction
   */
  public boolean isXaTransaction() {
    return CandaceSessionFactoryImpl.isXaTransaction();
  }

  @Override
  public Query getNamedQuery(String queryName) {
    LOGGER.debug("getNamedQuery: {}", queryName);
    return session.getNamedQuery(queryName);
  }

  @Override
  public String getTenantIdentifier() {
    LOGGER.trace("getTenantIdentifier");
    return session.getTenantIdentifier();
  }

  @Override
  public Session getSession() {
    return session.getSession();
  }

  @Override
  public void close() throws HibernateException {
    LOGGER.info("close");
    if (session != null) {
      session.close();
      session = null; // release session references
    }
  }

  @Override
  public boolean isOpen() {
    LOGGER.trace("isOpen");
    return session.isOpen();
  }

  @Override
  public boolean isConnected() {
    LOGGER.trace("isConnected");
    return session.isConnected();
  }

  @Override
  public Transaction beginTransaction() {
    LOGGER.info("beginTransaction: XA: {}", CandaceSessionFactoryImpl.isXaTransaction());
    if (txn == null || txn.getStatus() != TransactionStatus.ACTIVE) {
      this.txn = new CandaceTransactionImpl(session.beginTransaction());
    }

    return txn;
  }

  @Override
  public Transaction getTransaction() {
    LOGGER.debug("getTransaction: XA: {}", CandaceSessionFactoryImpl.isXaTransaction());
    if (this.txn == null) {
      this.txn = new CandaceTransactionImpl(session.getTransaction());
    }
    return txn;
  }

  @Override
  public Query createNamedQuery(String name) {
    LOGGER.debug("createNamedQuery: {}", name);
    return session.createNamedQuery(name);
  }

  @Override
  public ProcedureCall getNamedProcedureCall(String name) {
    LOGGER.debug("getNamedProcedureCall: {}", name);
    return session.getNamedProcedureCall(name);
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName) {
    LOGGER.debug("createStoredProcedureCall: {}", procedureName);
    return session.createStoredProcedureCall(procedureName);
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName, Class... resultClasses) {
    return session.createStoredProcedureCall(procedureName, resultClasses);
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName,
      String... resultSetMappings) {
    return session.createStoredProcedureCall(procedureName, resultSetMappings);
  }

  @Override
  public NativeQuery createSQLQuery(String queryString) {
    LOGGER.debug("createSQLQuery: {}", queryString);
    return session.createSQLQuery(queryString);
  }

  @Override
  public void remove(Object entity) {
    LOGGER.debug("remove");
    session.remove(entity);
  }

  @Override
  public Criteria createCriteria(Class persistentClass) {
    LOGGER.debug("createCriteria: persistentClass: {}", persistentClass);
    return session.createCriteria(persistentClass);
  }

  @Override
  public NativeQuery createNativeQuery(String sqlString) {
    LOGGER.debug("createNativeQuery: {}", sqlString);
    return session.createNativeQuery(sqlString);
  }

  @Override
  public SharedSessionBuilder sessionWithOptions() {
    LOGGER.debug("sessionWithOptions");
    return session.sessionWithOptions();
  }

  @Override
  public Criteria createCriteria(Class persistentClass, String alias) {
    return session.createCriteria(persistentClass, alias);
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey) {
    LOGGER.trace("find(Class<T>,Object): entityClass: {}, primaryKey: {}", entityClass, primaryKey);
    return session.find(entityClass, primaryKey);
  }

  @Override
  public void flush() throws HibernateException {
    LOGGER.debug("***** CandaceSessionImpl.flush *****");
    logStack("flush");
    session.flush();
  }

  @Override
  public Criteria createCriteria(String entityName) {
    return session.createCriteria(entityName);
  }

  @Override
  public NativeQuery createNativeQuery(String sqlString, String resultSetMapping) {
    LOGGER.trace("createNativeQuery(String,String): sqlString: {}, resultSetMapping: {}", sqlString,
        resultSetMapping);
    return session.createNativeQuery(sqlString, resultSetMapping);
  }

  @Override
  public void setFlushMode(FlushMode flushMode) {
    session.setFlushMode(flushMode);
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
    return session.find(entityClass, primaryKey, properties);
  }

  @Override
  public Criteria createCriteria(String entityName, String alias) {
    LOGGER.trace("createCriteria(String,String): entityName: {}, resultSetMapping: {}", entityName,
        alias);
    return session.createCriteria(entityName, alias);
  }

  @Override
  public NativeQuery getNamedSQLQuery(String name) {
    LOGGER.trace("getNamedSQLQuery(String): name: {}", name);
    return session.getNamedSQLQuery(name);
  }

  @Override
  public Integer getJdbcBatchSize() {
    return session.getJdbcBatchSize();
  }

  @Override
  public FlushModeType getFlushMode() {
    return session.getFlushMode();
  }

  @Override
  public NativeQuery getNamedNativeQuery(String name) {
    return session.getNamedNativeQuery(name);
  }

  @Override
  public void setJdbcBatchSize(Integer jdbcBatchSize) {
    session.setJdbcBatchSize(jdbcBatchSize);
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
    return session.find(entityClass, primaryKey, lockMode);
  }

  @Override
  public void setHibernateFlushMode(FlushMode flushMode) {
    LOGGER.debug("setHibernateFlushMode: flushMode: {}", flushMode);
    session.setHibernateFlushMode(flushMode);
  }

  @Override
  public FlushMode getHibernateFlushMode() {
    return session.getHibernateFlushMode();
  }

  @Override
  public void setCacheMode(CacheMode cacheMode) {
    LOGGER.debug("setCacheMode: cacheMode: {}", cacheMode);
    session.setCacheMode(cacheMode);
  }

  @Override
  public CacheMode getCacheMode() {
    return session.getCacheMode();
  }

  @Override
  public SessionFactory getSessionFactory() {
    LOGGER.debug("getSessionFactory()");
    return session.getSessionFactory();
  }

  @Override
  public void cancelQuery() throws HibernateException {
    LOGGER.debug("cancelQuery()");
    session.cancelQuery();
  }

  @Override
  public boolean isDirty() throws HibernateException {
    return session.isDirty();
  }

  @Override
  public boolean isDefaultReadOnly() {
    return session.isDefaultReadOnly();
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode,
      Map<String, Object> properties) {
    return session.find(entityClass, primaryKey, lockMode, properties);
  }

  @Override
  public void setDefaultReadOnly(boolean readOnly) {
    LOGGER.debug("setDefaultReadOnly: readOnly: {}", readOnly);
    session.setDefaultReadOnly(readOnly);
  }

  @Override
  public Serializable getIdentifier(Object object) {
    return session.getIdentifier(object);
  }

  @Override
  public boolean contains(String entityName, Object object) {
    return session.contains(entityName, object);
  }

  @Override
  public void evict(Object object) {
    LOGGER.debug("evict: object hash: {}", object != null ? object.hashCode() : 0);
    logStack("evict");
    session.evict(object);
  }

  @Override
  public <T> T load(Class<T> theClass, Serializable id, LockMode lockMode) {
    return session.load(theClass, id, lockMode);
  }

  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {
    return session.getReference(entityClass, primaryKey);
  }

  @Override
  public <T> T load(Class<T> theClass, Serializable id, LockOptions lockOptions) {
    return session.load(theClass, id, lockOptions);
  }

  @Override
  public Object load(String entityName, Serializable id, LockMode lockMode) {
    return session.load(entityName, id, lockMode);
  }

  @Override
  public void setFlushMode(FlushModeType flushMode) {
    LOGGER.debug("setFlushMode: flushMode: {}", flushMode);
    session.setFlushMode(flushMode);
  }

  @Override
  public Object load(String entityName, Serializable id, LockOptions lockOptions) {
    return session.load(entityName, id, lockOptions);
  }

  @Override
  public <T> T load(Class<T> theClass, Serializable id) {
    return session.load(theClass, id);
  }

  @Override
  public Object load(String entityName, Serializable id) {
    return session.load(entityName, id);
  }

  @Override
  public void load(Object object, Serializable id) {
    session.load(object, id);
  }

  @Override
  public void replicate(Object object, ReplicationMode replicationMode) {
    session.replicate(object, replicationMode);
  }

  @Override
  public void replicate(String entityName, Object object, ReplicationMode replicationMode) {
    session.replicate(entityName, object, replicationMode);
  }

  @Override
  public Serializable save(Object object) {
    LOGGER.debug("save(object)");
    logStack(object, "save(object)");
    return session.save(object);
  }

  @Override
  public Serializable save(String entityName, Object object) {
    LOGGER.debug("save(String,Object): entityName: {}", entityName);
    logStack(object, "save(String,Object)");
    return session.save(entityName, object);
  }

  @Override
  public void saveOrUpdate(Object object) {
    LOGGER.debug("saveOrUpdate(Object)");
    logStack(object, "saveOrUpdate(Object)");
    session.saveOrUpdate(object);
  }

  @Override
  public void saveOrUpdate(String entityName, Object object) {
    LOGGER.debug("saveOrUpdate(String,Object): entityName: {}", entityName);
    logStack(object, "saveOrUpdate(String,Object)");
    session.saveOrUpdate(entityName, object);
  }

  @Override
  public void update(Object object) {
    LOGGER.debug("update(Object)");
    logStack(object, "update(Object)");
    session.update(object);
  }

  @Override
  public void update(String entityName, Object object) {
    LOGGER.debug("update(String,Object): entityName: {}", entityName);
    logStack(object, "update(String,Object)");
    session.update(entityName, object);
  }

  @Override
  public void refresh(Object entity, Map<String, Object> properties) {
    session.refresh(entity, properties);
  }

  @Override
  public void refresh(Object entity, LockModeType lockMode) {
    session.refresh(entity, lockMode);
  }

  @Override
  public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    session.refresh(entity, lockMode, properties);
  }

  @Override
  public void refresh(Object object) {
    LOGGER.trace("refresh(Object)");
    logStack("refresh(Object)");
    session.refresh(object);
  }

  @Override
  public Object merge(Object object) {
    return session.merge(object);
  }

  @Override
  public Object merge(String entityName, Object object) {
    LOGGER.debug("merge(String,Object): entityName: {}", entityName);
    logStack(object, "merge(String,Object)");
    return session.merge(entityName, object);
  }

  @Override
  public void persist(Object object) {
    session.persist(object);
  }

  @Override
  public void persist(String entityName, Object object) {
    LOGGER.debug("persist(String,Object): entityName: {}", entityName);
    logStack(object, "persist(String,Object)");
    session.persist(entityName, object);
  }

  @Override
  public void delete(Object object) {
    LOGGER.info("delete");
    session.delete(object);
  }

  @Override
  public void delete(String entityName, Object object) {
    LOGGER.debug("delete(String,Object): entityName: {}", entityName);
    logStack(object, "delete(String,Object)");
    session.delete(entityName, object);
  }

  @Override
  public void lock(Object entity, LockModeType lockMode) {
    session.lock(entity, lockMode);
  }

  @Override
  public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    session.lock(entity, lockMode, properties);
  }

  @Override
  public void lock(Object object, LockMode lockMode) {
    session.lock(object, lockMode);
  }

  @Override
  public void lock(String entityName, Object object, LockMode lockMode) {
    LOGGER.debug("lock(String,Object,LockMode): entityName: {}, lockMode: {}", entityName,
        lockMode);
    session.lock(entityName, object, lockMode);
  }

  @Override
  public void detach(Object entity) {
    LOGGER.debug("detach(Object)");
    logStack("detach(Object)");
    session.detach(entity);
  }

  @Override
  public boolean contains(Object entity) {
    return session.contains(entity);
  }

  @Override
  public LockRequest buildLockRequest(LockOptions lockOptions) {
    return session.buildLockRequest(lockOptions);
  }

  @Override
  public LockModeType getLockMode(Object entity) {
    return session.getLockMode(entity);
  }

  @Override
  public void setProperty(String propertyName, Object value) {
    session.setProperty(propertyName, value);
  }

  @Override
  public Map<String, Object> getProperties() {
    return session.getProperties();
  }

  @Override
  public void refresh(String entityName, Object object) {
    LOGGER.trace("refresh(String,Object)");
    session.refresh(entityName, object);
  }

  @Override
  public void refresh(Object object, LockMode lockMode) {
    LOGGER.trace("refresh(Object,LockMode)");
    session.refresh(object, lockMode);
  }

  @Override
  public void refresh(Object object, LockOptions lockOptions) {
    LOGGER.trace("refresh(Object,LockOptions)");
    session.refresh(object, lockOptions);
  }

  @Override
  public void refresh(String entityName, Object object, LockOptions lockOptions) {
    LOGGER.trace("refresh(String,Object,LockOptions)");
    session.refresh(entityName, object, lockOptions);
  }

  @Override
  public LockMode getCurrentLockMode(Object object) {
    LOGGER.trace("getCurrentLockMode()");
    return session.getCurrentLockMode(object);
  }

  @Override
  public Query createFilter(Object collection, String queryString) {
    LOGGER.trace("createFilter()");
    return session.createFilter(collection, queryString);
  }

  @Override
  public void clear() {
    LOGGER.debug("clear()");
    logStack("clear()");
    if (session != null) {
      final Transaction txn = session.getTransaction();
      if (txn != null && txn.isActive()) {
        session.clear();
      }
    }
  }

  @Override
  public <T> T get(Class<T> entityType, Serializable id) {
    LOGGER.debug("get(Class<T>,Serializable): entityType: {}, id: {}", entityType, id);
    return session.get(entityType, id);
  }

  @Override
  public <T> T get(Class<T> entityType, Serializable id, LockMode lockMode) {
    LOGGER.debug("get(Class<T>,Serializable,LockMode): entityType: {}, id: {}, lockMode: {}",
        entityType, id, lockMode);
    return session.get(entityType, id, lockMode);
  }

  @Override
  public Object get(String entityName, Serializable id, LockMode lockMode) {
    LOGGER.debug("get(String,Serializable,LockMode): entityName: {}, id: {}, lockMode: {}",
        entityName, id, lockMode);
    return session.get(entityName, id, lockMode);
  }

  @Override
  public <T> T get(Class<T> entityType, Serializable id, LockOptions lockOptions) {
    LOGGER.debug("get(Class<T>,Serializable,LockOptions): entityType: {}, id: {}, lockMode: {}",
        entityType, id, lockOptions);
    return session.get(entityType, id, lockOptions);
  }

  @Override
  public Object get(String entityName, Serializable id) {
    LOGGER.debug("get(String,Serializable): entityName: {}, id: {}", entityName, id);
    return session.get(entityName, id);
  }

  @Override
  public Object get(String entityName, Serializable id, LockOptions lockOptions) {
    LOGGER.debug("get(String,Serializable,LockOptions): entityName: {}, id: {}, lockOptions: {}",
        entityName, id, lockOptions);
    return session.get(entityName, id, lockOptions);
  }

  @Override
  public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
    LOGGER.debug("createNamedStoredProcedureQuery(String): name: {}", name);
    return session.createNamedStoredProcedureQuery(name);
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
    LOGGER.debug("createStoredProcedureQuery(String): procedureName: {}", procedureName);
    return session.createStoredProcedureQuery(procedureName);
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName,
      Class... resultClasses) {
    LOGGER.debug("createStoredProcedureQuery(String,Class...): procedureName: {}", procedureName);
    return session.createStoredProcedureQuery(procedureName, resultClasses);
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName,
      String... resultSetMappings) {
    LOGGER.debug("createStoredProcedureQuery(String,String...): procedureName: {}", procedureName);
    return session.createStoredProcedureQuery(procedureName, resultSetMappings);
  }

  @Override
  public String getEntityName(Object object) {
    LOGGER.debug("getEntityName(Object)");
    return session.getEntityName(object);
  }

  @Override
  public IdentifierLoadAccess byId(String entityName) {
    LOGGER.debug("byId(String): entityName: {}", entityName);
    return session.byId(entityName);
  }

  @Override
  public <T> IdentifierLoadAccess<T> byId(Class<T> entityClass) {
    LOGGER.debug("byId(Class<T>): entityName: {}", entityClass);
    return session.byId(entityClass);
  }

  @Override
  public <T> MultiIdentifierLoadAccess<T> byMultipleIds(Class<T> entityClass) {
    LOGGER.debug("byMultipleIds(Class<T>): entityName: {}", entityClass);
    return session.byMultipleIds(entityClass);
  }

  @Override
  public MultiIdentifierLoadAccess byMultipleIds(String entityName) {
    LOGGER.debug("byMultipleIds(String): entityName: {}", entityName);
    return session.byMultipleIds(entityName);
  }

  @Override
  public void joinTransaction() {
    LOGGER.debug("joinTransaction()");
    session.joinTransaction();
  }

  @Override
  public boolean isJoinedToTransaction() {
    LOGGER.trace("isJoinedToTransaction()");
    return session.isJoinedToTransaction();
  }

  @Override
  public <T> T unwrap(Class<T> cls) {
    LOGGER.trace("unwrap(Class<T>): cls: {}", cls);
    return session.unwrap(cls);
  }

  @Override
  public NaturalIdLoadAccess byNaturalId(String entityName) {
    LOGGER.debug("byNaturalId(String): entityName: {}", entityName);
    return session.byNaturalId(entityName);
  }

  @Override
  public Object getDelegate() {
    LOGGER.trace("getDelegate()");
    return session.getDelegate();
  }

  @Override
  public <T> NaturalIdLoadAccess<T> byNaturalId(Class<T> entityClass) {
    LOGGER.trace("byNaturalId(Class<T>): entityClass: {}", entityClass);
    return session.byNaturalId(entityClass);
  }

  @Override
  public SimpleNaturalIdLoadAccess bySimpleNaturalId(String entityName) {
    LOGGER.debug("bySimpleNaturalId(String): entityName: {}", entityName);
    return session.bySimpleNaturalId(entityName);
  }

  @Override
  public <T> SimpleNaturalIdLoadAccess<T> bySimpleNaturalId(Class<T> entityClass) {
    LOGGER.trace("bySimpleNaturalId(Class<T>): entityClass: {}", entityClass);
    return session.bySimpleNaturalId(entityClass);
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    LOGGER.trace("getEntityManagerFactory()");
    return session.getEntityManagerFactory();
  }

  @Override
  public Filter enableFilter(String filterName) {
    LOGGER.trace("enableFilter(String): filterName: {}", filterName);
    return session.enableFilter(filterName);
  }

  @Override
  public Filter getEnabledFilter(String filterName) {
    LOGGER.trace("getEnabledFilter(String): filterName: {}", filterName);
    return session.getEnabledFilter(filterName);
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    LOGGER.trace("getCriteriaBuilder()");
    return session.getCriteriaBuilder();
  }

  @Override
  public void disableFilter(String filterName) {
    LOGGER.trace("disableFilter(String): filterName: {}", filterName);
    session.disableFilter(filterName);
  }

  @Override
  public Metamodel getMetamodel() {
    LOGGER.trace("getMetamodel()");
    return session.getMetamodel();
  }

  @Override
  public SessionStatistics getStatistics() {
    LOGGER.trace("getStatistics()");
    return session.getStatistics();
  }

  @Override
  public boolean isReadOnly(Object entityOrProxy) {
    LOGGER.trace("isReadOnly(Object)");
    return session.isReadOnly(entityOrProxy);
  }

  @Override
  public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
    LOGGER.trace("createEntityGraph(Class<T>)");
    return session.createEntityGraph(rootType);
  }

  @Override
  public EntityGraph<?> createEntityGraph(String graphName) {
    LOGGER.trace("createEntityGraph(String): graphName: {}", graphName);
    return session.createEntityGraph(graphName);
  }

  @Override
  public EntityGraph<?> getEntityGraph(String graphName) {
    LOGGER.trace("getEntityGraph(String): graphName: {}", graphName);
    return session.getEntityGraph(graphName);
  }

  @Override
  public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
    LOGGER.trace("getEntityGraphs(Class<T>): entityClass: {}", entityClass);
    return session.getEntityGraphs(entityClass);
  }

  @Override
  public void setReadOnly(Object entityOrProxy, boolean readOnly) {
    LOGGER.trace("setReadOnly(Object,boolean): readOnly: {}", readOnly);
    session.setReadOnly(entityOrProxy, readOnly);
  }

  @Override
  public void doWork(Work work) throws HibernateException {
    LOGGER.debug("CandanceSessionImpl.doWork: work class: {}", work.getClass());
    session.doWork(work);
  }

  @Override
  public <T> T doReturningWork(ReturningWork<T> work) throws HibernateException {
    LOGGER.debug("CandanceSessionImpl.doReturningWork: work class: {}", work.getClass());
    return session.doReturningWork(work);
  }

  @Override
  public Connection disconnect() {
    LOGGER.warn("CandanceSessionImpl.disconnect");
    return session.disconnect();
  }

  @Override
  public void reconnect(Connection connection) {
    LOGGER.warn("CandanceSessionImpl.reconnect: connection: {}", connection);
    session.reconnect(connection);
  }

  @Override
  public boolean isFetchProfileEnabled(String name) throws UnknownProfileException {
    LOGGER.trace("CandanceSessionImpl.isFetchProfileEnabled: name: {}", name);
    return session.isFetchProfileEnabled(name);
  }

  @Override
  public void enableFetchProfile(String name) throws UnknownProfileException {
    LOGGER.trace("CandanceSessionImpl.enableFetchProfile: name: {}", name);
    session.enableFetchProfile(name);
  }

  @Override
  public void disableFetchProfile(String name) throws UnknownProfileException {
    LOGGER.trace("CandanceSessionImpl.disableFetchProfile: name: {}", name);
    session.disableFetchProfile(name);
  }

  @Override
  public TypeHelper getTypeHelper() {
    LOGGER.trace("CandanceSessionImpl.getTypeHelper");
    return session.getTypeHelper();
  }

  @Override
  public LobHelper getLobHelper() {
    LOGGER.trace("CandanceSessionImpl.getLobHelper");
    return session.getLobHelper();
  }

  @Override
  public void addEventListeners(SessionEventListener... listeners) {
    LOGGER.trace("CandanceSessionImpl.addEventListeners: listeners: {}", (Object[]) listeners);
    session.addEventListeners(listeners);
  }

  @Override
  public Query createQuery(String queryString) {
    LOGGER.debug("CandanceSessionImpl.createQuery(String): queryString: {}", queryString);
    return session.createQuery(queryString);
  }

  @Override
  public <T> Query<T> createQuery(String queryString, Class<T> resultType) {
    LOGGER.debug(
        "CandanceSessionImpl.createQuery(String, Class<T>): queryString: {}, resultType: {}",
        queryString, resultType);
    return session.createQuery(queryString, resultType);
  }

  @Override
  public <T> Query<T> createQuery(CriteriaQuery<T> criteriaQuery) {
    LOGGER.debug("CandanceSessionImpl.createQuery(CriteriaQuery<T>): criteriaQuery: {}",
        criteriaQuery);
    return session.createQuery(criteriaQuery);
  }

  @Override
  public Query createQuery(CriteriaUpdate updateQuery) {
    LOGGER.debug("CandanceSessionImpl.createQuery(CriteriaQuery): updateQuery: {}", updateQuery);
    return session.createQuery(updateQuery);
  }

  @Override
  public Query createQuery(CriteriaDelete deleteQuery) {
    LOGGER.debug("CandanceSessionImpl.createQuery(CriteriaQuery): deleteQuery: {}", deleteQuery);
    return session.createQuery(deleteQuery);
  }

  @Override
  public <T> Query<T> createNamedQuery(String name, Class<T> resultType) {
    LOGGER.debug("CandanceSessionImpl.createNamedQuery: name: {}, resultType: {}", name,
        resultType);
    return session.createNamedQuery(name, resultType);
  }

  /*
   * DRS: confusing and contradictory method signatures. After stripping types, this method
   * signature is the same as {@link EntityManager.createNativeQuery(String, Class)};
   * 
   * @see org.hibernate.query.QueryProducer#createNativeQuery(java.lang.String, java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public NativeQuery createNativeQuery(String sqlString, Class resultClass) {
    LOGGER.debug("CandanceSessionImpl.createNativeQuery: sqlString: {}, resultClass: {}", sqlString,
        resultClass);
    return session.createNativeQuery(sqlString, resultClass);
  }

}
