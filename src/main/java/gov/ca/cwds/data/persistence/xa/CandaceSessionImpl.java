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
import org.hibernate.stat.SessionStatistics;

public class CandaceSessionImpl implements Session {

  private static final long serialVersionUID = 1L;

  @Override
  public SharedSessionBuilder sessionWithOptions() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void flush() throws HibernateException {
    // TODO Auto-generated method stub

  }

  @Override
  public void setFlushMode(FlushMode flushMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public FlushModeType getFlushMode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setHibernateFlushMode(FlushMode flushMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public FlushMode getHibernateFlushMode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setCacheMode(CacheMode cacheMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public CacheMode getCacheMode() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SessionFactory getSessionFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void cancelQuery() throws HibernateException {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isDirty() throws HibernateException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isDefaultReadOnly() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setDefaultReadOnly(boolean readOnly) {
    // TODO Auto-generated method stub

  }

  @Override
  public Serializable getIdentifier(Object object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean contains(String entityName, Object object) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void evict(Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public <T> T load(Class<T> theClass, Serializable id, LockMode lockMode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T load(Class<T> theClass, Serializable id, LockOptions lockOptions) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object load(String entityName, Serializable id, LockMode lockMode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object load(String entityName, Serializable id, LockOptions lockOptions) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T load(Class<T> theClass, Serializable id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object load(String entityName, Serializable id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void load(Object object, Serializable id) {
    // TODO Auto-generated method stub

  }

  @Override
  public void replicate(Object object, ReplicationMode replicationMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void replicate(String entityName, Object object, ReplicationMode replicationMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public Serializable save(Object object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Serializable save(String entityName, Object object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void saveOrUpdate(Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void saveOrUpdate(String entityName, Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void update(Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void update(String entityName, Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public Object merge(Object object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object merge(String entityName, Object object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void persist(Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void persist(String entityName, Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete(Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete(String entityName, Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void lock(Object object, LockMode lockMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void lock(String entityName, Object object, LockMode lockMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public LockRequest buildLockRequest(LockOptions lockOptions) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void refresh(Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh(String entityName, Object object) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh(Object object, LockMode lockMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh(Object object, LockOptions lockOptions) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh(String entityName, Object object, LockOptions lockOptions) {
    // TODO Auto-generated method stub

  }

  @Override
  public LockMode getCurrentLockMode(Object object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Query createFilter(Object collection, String queryString) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub

  }

  @Override
  public <T> T get(Class<T> entityType, Serializable id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T get(Class<T> entityType, Serializable id, LockMode lockMode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T get(Class<T> entityType, Serializable id, LockOptions lockOptions) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object get(String entityName, Serializable id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object get(String entityName, Serializable id, LockMode lockMode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object get(String entityName, Serializable id, LockOptions lockOptions) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getEntityName(Object object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IdentifierLoadAccess byId(String entityName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> MultiIdentifierLoadAccess<T> byMultipleIds(Class<T> entityClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MultiIdentifierLoadAccess byMultipleIds(String entityName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> IdentifierLoadAccess<T> byId(Class<T> entityClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public NaturalIdLoadAccess byNaturalId(String entityName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> NaturalIdLoadAccess<T> byNaturalId(Class<T> entityClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SimpleNaturalIdLoadAccess bySimpleNaturalId(String entityName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> SimpleNaturalIdLoadAccess<T> bySimpleNaturalId(Class<T> entityClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Filter enableFilter(String filterName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Filter getEnabledFilter(String filterName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void disableFilter(String filterName) {
    // TODO Auto-generated method stub

  }

  @Override
  public SessionStatistics getStatistics() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isReadOnly(Object entityOrProxy) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setReadOnly(Object entityOrProxy, boolean readOnly) {
    // TODO Auto-generated method stub

  }

  @Override
  public void doWork(Work work) throws HibernateException {
    // TODO Auto-generated method stub

  }

  @Override
  public <T> T doReturningWork(ReturningWork<T> work) throws HibernateException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Connection disconnect() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void reconnect(Connection connection) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isFetchProfileEnabled(String name) throws UnknownProfileException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void enableFetchProfile(String name) throws UnknownProfileException {
    // TODO Auto-generated method stub

  }

  @Override
  public void disableFetchProfile(String name) throws UnknownProfileException {
    // TODO Auto-generated method stub

  }

  @Override
  public TypeHelper getTypeHelper() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LobHelper getLobHelper() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addEventListeners(SessionEventListener... listeners) {
    // TODO Auto-generated method stub

  }

  @Override
  public Query createQuery(String queryString) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> Query<T> createQuery(String queryString, Class<T> resultType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> Query<T> createQuery(CriteriaQuery<T> criteriaQuery) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Query createQuery(CriteriaUpdate updateQuery) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Query createQuery(CriteriaDelete deleteQuery) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> Query<T> createNamedQuery(String name, Class<T> resultType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTenantIdentifier() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void close() throws HibernateException {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isOpen() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isConnected() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Transaction beginTransaction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Transaction getTransaction() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProcedureCall getNamedProcedureCall(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName, Class... resultClasses) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ProcedureCall createStoredProcedureCall(String procedureName,
      String... resultSetMappings) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Criteria createCriteria(Class persistentClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Criteria createCriteria(Class persistentClass, String alias) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Criteria createCriteria(String entityName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Criteria createCriteria(String entityName, String alias) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer getJdbcBatchSize() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setJdbcBatchSize(Integer jdbcBatchSize) {
    // TODO Auto-generated method stub

  }

  @Override
  public Session getSession() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void remove(Object entity) {
    // TODO Auto-generated method stub

  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode,
      Map<String, Object> properties) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setFlushMode(FlushModeType flushMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void lock(Object entity, LockModeType lockMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh(Object entity, Map<String, Object> properties) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh(Object entity, LockModeType lockMode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    // TODO Auto-generated method stub

  }

  @Override
  public void detach(Object entity) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean contains(Object entity) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public LockModeType getLockMode(Object entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setProperty(String propertyName, Object value) {
    // TODO Auto-generated method stub

  }

  @Override
  public Map<String, Object> getProperties() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName,
      Class... resultClasses) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName,
      String... resultSetMappings) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void joinTransaction() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isJoinedToTransaction() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> cls) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object getDelegate() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Metamodel getMetamodel() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public EntityGraph<?> createEntityGraph(String graphName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public EntityGraph<?> getEntityGraph(String graphName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Query getNamedQuery(String queryName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Query createNamedQuery(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public NativeQuery createNativeQuery(String sqlString) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public NativeQuery createNativeQuery(String sqlString, String resultSetMapping) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public NativeQuery getNamedNativeQuery(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  // public <R> NativeQuery<R> createNativeQuery(String sqlString, Class<R> resultClass) {
  public NativeQuery createNativeQuery(String sqlString, Class resultClass) {
    // TODO Auto-generated method stub
    return null;
  }

}
