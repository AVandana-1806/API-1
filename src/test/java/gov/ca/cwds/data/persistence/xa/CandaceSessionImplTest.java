package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CandaceSessionImplTest extends Doofenshmirtz<ClientAddress> {

  CandaceSessionImpl target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new CandaceSessionImpl(session);
  }

  @Test
  public void type() throws Exception {
    assertThat(CandaceSessionImpl.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void isXaTransaction_A$() throws Exception {
    boolean actual = target.isXaTransaction();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamedQuery_A$String() throws Exception {
    String queryName = null;
    Query actual = target.getNamedQuery(queryName);
    Query expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getTenantIdentifier_A$() throws Exception {
    String actual = target.getTenantIdentifier();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSession_A$() throws Exception {
    Session actual = target.getSession();
    Session expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void close_A$() throws Exception {
    target.close();
  }

  @Test(expected = HibernateException.class)
  public void close_A$_T$HibernateException() throws Exception {
    doThrow(HibernateException.class).when(session).close();
    target.close();
  }

  @Test
  public void isOpen_A$() throws Exception {
    boolean actual = target.isOpen();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void isConnected_A$() throws Exception {
    boolean actual = target.isConnected();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void beginTransaction_A$() throws Exception {
    Transaction actual = target.beginTransaction();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getTransaction_A$() throws Exception {
    final Transaction actual = target.getTransaction();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void createNamedQuery_A$String() throws Exception {
    String name = null;
    Query actual = target.createNamedQuery(name);
    Query expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamedProcedureCall_A$String() throws Exception {
    String name = null;
    ProcedureCall actual = target.getNamedProcedureCall(name);
    ProcedureCall expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createStoredProcedureCall_A$String() throws Exception {
    String procedureName = null;
    ProcedureCall actual = target.createStoredProcedureCall(procedureName);
    ProcedureCall expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createStoredProcedureCall_A$String$ClassArray() throws Exception {
    String procedureName = null;
    Class[] resultClasses = new Class[] {};
    ProcedureCall actual = target.createStoredProcedureCall(procedureName, resultClasses);
    ProcedureCall expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createStoredProcedureCall_A$String$StringArray() throws Exception {
    String procedureName = null;
    String[] resultSetMappings = new String[] {};
    ProcedureCall actual = target.createStoredProcedureCall(procedureName, resultSetMappings);
    ProcedureCall expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createSQLQuery_A$String() throws Exception {
    String queryString = null;
    NativeQuery actual = target.createSQLQuery(queryString);
    NativeQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void remove_A$Object() throws Exception {
    Object entity = null;
    target.remove(entity);
  }

  @Test
  public void createCriteria_A$Class() throws Exception {
    Class persistentClass = null;
    Criteria actual = target.createCriteria(persistentClass);
    Criteria expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createNativeQuery_A$String() throws Exception {
    String sqlString = null;
    NativeQuery actual = target.createNativeQuery(sqlString);
    NativeQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void sessionWithOptions_A$() throws Exception {
    SharedSessionBuilder actual = target.sessionWithOptions();
    SharedSessionBuilder expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createCriteria_A$Class$String() throws Exception {
    Class persistentClass = null;
    String alias = null;
    Criteria actual = target.createCriteria(persistentClass, alias);
    Criteria expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void find_A$Class$Object() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    Object primaryKey = null;
    Object actual = target.find(entityClass, primaryKey);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void flush_A$() throws Exception {
    target.flush();
  }

  @Test(expected = HibernateException.class)
  public void flush_A$_T$HibernateException() throws Exception {
    doThrow(HibernateException.class).when(session).flush();
    target.flush();
  }

  @Test
  public void createCriteria_A$String() throws Exception {
    String entityName = null;
    Criteria actual = target.createCriteria(entityName);
    Criteria expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createNativeQuery_A$String$String() throws Exception {
    String sqlString = null;
    String resultSetMapping = null;
    NativeQuery actual = target.createNativeQuery(sqlString, resultSetMapping);
    NativeQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFlushMode_A$FlushMode() throws Exception {
    FlushMode flushMode = FlushMode.MANUAL;
    target.setFlushMode(flushMode);
  }

  @Test
  public void find_A$Class$Object$Map() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    Object primaryKey = null;
    Map<String, Object> properties = new HashMap<String, Object>();
    Object actual = target.find(entityClass, primaryKey, properties);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createCriteria_A$String$String() throws Exception {
    String entityName = null;
    String alias = null;
    Criteria actual = target.createCriteria(entityName, alias);
    Criteria expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamedSQLQuery_A$String() throws Exception {
    String name = null;
    NativeQuery actual = target.getNamedSQLQuery(name);
    NativeQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getJdbcBatchSize_A$() throws Exception {
    Integer actual = target.getJdbcBatchSize();
    Integer expected = 0;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFlushMode_A$() throws Exception {
    FlushModeType actual = target.getFlushMode();
    FlushModeType expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamedNativeQuery_A$String() throws Exception {
    String name = null;
    NativeQuery actual = target.getNamedNativeQuery(name);
    NativeQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setJdbcBatchSize_A$Integer() throws Exception {
    Integer jdbcBatchSize = null;
    target.setJdbcBatchSize(jdbcBatchSize);
  }

  @Test
  public void find_A$Class$Object$LockModeType() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    Object primaryKey = null;
    LockModeType lockMode = LockModeType.READ;
    Object actual = target.find(entityClass, primaryKey, lockMode);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setHibernateFlushMode_A$FlushMode() throws Exception {
    FlushMode flushMode = FlushMode.MANUAL;
    target.setHibernateFlushMode(flushMode);
  }

  @Test
  public void getHibernateFlushMode_A$() throws Exception {
    FlushMode actual = target.getHibernateFlushMode();
    FlushMode expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCacheMode_A$CacheMode() throws Exception {
    CacheMode cacheMode = CacheMode.NORMAL;
    target.setCacheMode(cacheMode);
  }

  @Test
  public void getCacheMode_A$() throws Exception {
    CacheMode actual = target.getCacheMode();
    CacheMode expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSessionFactory_A$() throws Exception {
    SessionFactory actual = target.getSessionFactory();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void cancelQuery_A$() throws Exception {
    target.cancelQuery();
  }

  @Test(expected = HibernateException.class)
  public void cancelQuery_A$_T$HibernateException() throws Exception {
    doThrow(HibernateException.class).when(session).cancelQuery();
    target.cancelQuery();
  }

  @Test
  public void isDirty_A$() throws Exception {
    boolean actual = target.isDirty();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = HibernateException.class)
  public void isDirty_A$_T$HibernateException() throws Exception {
    doThrow(HibernateException.class).when(session).isDirty();
    target.isDirty();
  }

  @Test
  public void isDefaultReadOnly_A$() throws Exception {
    boolean actual = target.isDefaultReadOnly();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void find_A$Class$Object$LockModeType$Map() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    Object primaryKey = null;
    LockModeType lockMode = LockModeType.READ;
    Map<String, Object> properties = new HashMap<String, Object>();
    Object actual = target.find(entityClass, primaryKey, lockMode, properties);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDefaultReadOnly_A$boolean() throws Exception {
    boolean readOnly = false;
    target.setDefaultReadOnly(readOnly);
  }

  @Test
  public void getIdentifier_A$Object() throws Exception {
    Object object = null;
    Serializable actual = target.getIdentifier(object);
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void contains_A$String$Object() throws Exception {
    String entityName = null;
    Object object = null;
    boolean actual = target.contains(entityName, object);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void evict_A$Object() throws Exception {
    Object object = null;
    target.evict(object);
  }

  @Test
  public void load_A$Class$Serializable$LockMode() throws Exception {
    final Class<ClientAddress> theClass = ClientAddress.class;
    Serializable id = DEFAULT_CLIENT_ID;
    LockMode lockMode = LockMode.READ;
    Object actual = target.load(theClass, id, lockMode);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReference_A$Class$Object() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    Object primaryKey = null;
    Object actual = target.getReference(entityClass, primaryKey);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void load_A$Class$Serializable$LockOptions() throws Exception {
    final Class<ClientAddress> theClass = ClientAddress.class;
    Serializable id = DEFAULT_CLIENT_ID;
    LockOptions lockOptions = LockOptions.NONE;
    Object actual = target.load(theClass, id, lockOptions);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void load_A$String$Serializable$LockMode() throws Exception {
    String entityName = null;
    Serializable id = DEFAULT_CLIENT_ID;
    LockMode lockMode = LockMode.READ;
    Object actual = target.load(entityName, id, lockMode);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFlushMode_A$FlushModeType() throws Exception {
    FlushModeType flushMode = FlushModeType.AUTO;
    target.setFlushMode(flushMode);
  }

  @Test
  public void load_A$String$Serializable$LockOptions() throws Exception {
    String entityName = null;
    Serializable id = DEFAULT_CLIENT_ID;
    LockOptions lockOptions = mock(LockOptions.class);
    Object actual = target.load(entityName, id, lockOptions);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void lock_A$Object$LockModeType() throws Exception {
    Object entity = null;
    LockModeType lockMode = LockModeType.READ;
    target.lock(entity, lockMode);
  }

  @Test
  public void load_A$Class$Serializable() throws Exception {
    final Class<ClientAddress> theClass = ClientAddress.class;
    Serializable id = DEFAULT_CLIENT_ID;
    Object actual = target.load(theClass, id);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void load_A$String$Serializable() throws Exception {
    String entityName = null;
    Serializable id = DEFAULT_CLIENT_ID;
    Object actual = target.load(entityName, id);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void load_A$Object$Serializable() throws Exception {
    Object object = null;
    Serializable id = DEFAULT_CLIENT_ID;
    target.load(object, id);
  }

  @Test
  public void lock_A$Object$LockModeType$Map() throws Exception {
    Object entity = null;
    LockModeType lockMode = LockModeType.READ;
    Map<String, Object> properties = new HashMap<String, Object>();
    target.lock(entity, lockMode, properties);
  }

  @Test
  public void replicate_A$Object$ReplicationMode() throws Exception {
    Object object = null;
    ReplicationMode replicationMode = mock(ReplicationMode.class);
    target.replicate(object, replicationMode);
  }

  @Test
  public void replicate_A$String$Object$ReplicationMode() throws Exception {
    String entityName = null;
    Object object = null;
    ReplicationMode replicationMode = mock(ReplicationMode.class);
    target.replicate(entityName, object, replicationMode);
  }

  @Test
  public void save_A$Object() throws Exception {
    Object object = null;
    Serializable actual = target.save(object);
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void save_A$String$Object() throws Exception {
    String entityName = null;
    Object object = null;
    Serializable actual = target.save(entityName, object);
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void saveOrUpdate_A$Object() throws Exception {
    Object object = null;
    target.saveOrUpdate(object);
  }

  @Test
  public void saveOrUpdate_A$String$Object() throws Exception {
    String entityName = null;
    Object object = null;
    target.saveOrUpdate(entityName, object);
  }

  @Test
  public void refresh_A$Object$Map() throws Exception {
    Object entity = null;
    Map<String, Object> properties = new HashMap<String, Object>();
    target.refresh(entity, properties);
  }

  @Test
  public void update_A$Object() throws Exception {
    Object object = null;
    target.update(object);
  }

  @Test
  public void update_A$String$Object() throws Exception {
    String entityName = null;
    Object object = null;
    target.update(entityName, object);
  }

  @Test
  public void refresh_A$Object$LockModeType() throws Exception {
    Object entity = null;
    LockModeType lockMode = LockModeType.READ;
    target.refresh(entity, lockMode);
  }

  @Test
  public void merge_A$Object() throws Exception {
    Object object = null;
    Object actual = target.merge(object);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void merge_A$String$Object() throws Exception {
    String entityName = null;
    Object object = null;
    Object actual = target.merge(entityName, object);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void persist_A$Object() throws Exception {
    Object object = null;
    target.persist(object);
  }

  @Test
  public void refresh_A$Object$LockModeType$Map() throws Exception {
    Object entity = null;
    LockModeType lockMode = LockModeType.READ;
    Map<String, Object> properties = new HashMap<String, Object>();
    target.refresh(entity, lockMode, properties);
  }

  @Test
  public void persist_A$String$Object() throws Exception {
    String entityName = null;
    Object object = null;
    target.persist(entityName, object);
  }

  @Test
  public void delete_A$Object() throws Exception {
    Object object = null;
    target.delete(object);
  }

  @Test
  public void delete_A$String$Object() throws Exception {
    String entityName = null;
    Object object = null;
    target.delete(entityName, object);
  }

  @Test
  public void lock_A$Object$LockMode() throws Exception {
    Object object = null;
    LockMode lockMode = LockMode.READ;
    target.lock(object, lockMode);
  }

  @Test
  public void lock_A$String$Object$LockMode() throws Exception {
    String entityName = null;
    Object object = null;
    LockMode lockMode = LockMode.READ;
    target.lock(entityName, object, lockMode);
  }

  @Test
  public void detach_A$Object() throws Exception {
    Object entity = null;
    target.detach(entity);
  }

  @Test
  public void contains_A$Object() throws Exception {
    Object entity = null;
    boolean actual = target.contains(entity);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void buildLockRequest_A$LockOptions() throws Exception {
    LockOptions lockOptions = mock(LockOptions.class);
    Object actual = target.buildLockRequest(lockOptions);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLockMode_A$Object() throws Exception {
    Object entity = null;
    LockModeType actual = target.getLockMode(entity);
    LockModeType expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void refresh_A$Object() throws Exception {
    Object object = null;
    target.refresh(object);
  }

  @Test
  public void setProperty_A$String$Object() throws Exception {
    String propertyName = null;
    Object value = null;
    target.setProperty(propertyName, value);
  }

  @Test
  public void getProperties_A$() throws Exception {
    Map<String, Object> actual = target.getProperties();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void refresh_A$String$Object() throws Exception {
    String entityName = null;
    Object object = null;
    target.refresh(entityName, object);
  }

  @Test
  public void refresh_A$Object$LockMode() throws Exception {
    Object object = null;
    LockMode lockMode = LockMode.READ;
    target.refresh(object, lockMode);
  }

  @Test
  public void refresh_A$Object$LockOptions() throws Exception {
    Object object = null;
    LockOptions lockOptions = mock(LockOptions.class);
    target.refresh(object, lockOptions);
  }

  @Test
  public void refresh_A$String$Object$LockOptions() throws Exception {
    String entityName = null;
    Object object = null;
    LockOptions lockOptions = mock(LockOptions.class);
    target.refresh(entityName, object, lockOptions);
  }

  @Test
  public void getCurrentLockMode_A$Object() throws Exception {
    Object object = null;
    LockMode actual = target.getCurrentLockMode(object);
    LockMode expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createFilter_A$Object$String() throws Exception {
    Object collection = null;
    String queryString = null;
    Query actual = target.createFilter(collection, queryString);
    Query expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void clear_A$() throws Exception {
    target.clear();
  }

  @Test
  public void get_A$Class$Serializable() throws Exception {
    final Class<ClientAddress> entityType = ClientAddress.class;
    Serializable id = DEFAULT_CLIENT_ID;
    Object actual = target.get(entityType, id);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void get_A$Class$Serializable$LockMode() throws Exception {
    final Class<ClientAddress> entityType = ClientAddress.class;
    Serializable id = DEFAULT_CLIENT_ID;
    LockMode lockMode = LockMode.READ;
    Object actual = target.get(entityType, id, lockMode);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void get_A$Class$Serializable$LockOptions() throws Exception {
    final Class<ClientAddress> entityType = ClientAddress.class;
    Serializable id = DEFAULT_CLIENT_ID;
    LockOptions lockOptions = mock(LockOptions.class);
    Object actual = target.get(entityType, id, lockOptions);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createNamedStoredProcedureQuery_A$String() throws Exception {
    String name = null;
    StoredProcedureQuery actual = target.createNamedStoredProcedureQuery(name);
    StoredProcedureQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void get_A$String$Serializable() throws Exception {
    String entityName = null;
    Serializable id = DEFAULT_CLIENT_ID;
    Object actual = target.get(entityName, id);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createStoredProcedureQuery_A$String() throws Exception {
    String procedureName = null;
    StoredProcedureQuery actual = target.createStoredProcedureQuery(procedureName);
    StoredProcedureQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void get_A$String$Serializable$LockMode() throws Exception {
    String entityName = null;
    Serializable id = DEFAULT_CLIENT_ID;
    LockMode lockMode = LockMode.READ;
    Object actual = target.get(entityName, id, lockMode);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createStoredProcedureQuery_A$String$ClassArray() throws Exception {
    String procedureName = null;
    Class[] resultClasses = new Class[] {};
    StoredProcedureQuery actual = target.createStoredProcedureQuery(procedureName, resultClasses);
    StoredProcedureQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void get_A$String$Serializable$LockOptions() throws Exception {
    String entityName = null;
    Serializable id = DEFAULT_CLIENT_ID;
    LockOptions lockOptions = mock(LockOptions.class);
    Object actual = target.get(entityName, id, lockOptions);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createStoredProcedureQuery_A$String$StringArray() throws Exception {
    String procedureName = null;
    String[] resultSetMappings = new String[] {};
    StoredProcedureQuery actual =
        target.createStoredProcedureQuery(procedureName, resultSetMappings);
    StoredProcedureQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEntityName_A$Object() throws Exception {
    Object object = null;
    String actual = target.getEntityName(object);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void byId_A$String() throws Exception {
    String entityName = null;
    IdentifierLoadAccess actual = target.byId(entityName);
    IdentifierLoadAccess expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void byMultipleIds_A$Class() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    MultiIdentifierLoadAccess<ClientAddress> actual = target.byMultipleIds(entityClass);
    MultiIdentifierLoadAccess<ClientAddress> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void joinTransaction_A$() throws Exception {
    target.joinTransaction();
  }

  @Test
  public void byMultipleIds_A$String() throws Exception {
    String entityName = null;
    MultiIdentifierLoadAccess actual = target.byMultipleIds(entityName);
    MultiIdentifierLoadAccess expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void isJoinedToTransaction_A$() throws Exception {
    boolean actual = target.isJoinedToTransaction();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void byId_A$Class() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    IdentifierLoadAccess<ClientAddress> actual = target.byId(entityClass);
    IdentifierLoadAccess<ClientAddress> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void unwrap_A$Class() throws Exception {
    final Class<ClientAddress> cls = ClientAddress.class;
    Object actual = target.unwrap(cls);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void byNaturalId_A$String() throws Exception {
    String entityName = null;
    NaturalIdLoadAccess actual = target.byNaturalId(entityName);
    NaturalIdLoadAccess expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDelegate_A$() throws Exception {
    Object actual = target.getDelegate();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void byNaturalId_A$Class() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    NaturalIdLoadAccess<ClientAddress> actual = target.byNaturalId(entityClass);
    NaturalIdLoadAccess<ClientAddress> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void bySimpleNaturalId_A$String() throws Exception {
    String entityName = null;
    SimpleNaturalIdLoadAccess actual = target.bySimpleNaturalId(entityName);
    SimpleNaturalIdLoadAccess expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void bySimpleNaturalId_A$Class() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    SimpleNaturalIdLoadAccess<ClientAddress> actual = target.bySimpleNaturalId(entityClass);
    SimpleNaturalIdLoadAccess<ClientAddress> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEntityManagerFactory_A$() throws Exception {
    EntityManagerFactory actual = target.getEntityManagerFactory();
    EntityManagerFactory expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void enableFilter_A$String() throws Exception {
    String filterName = null;
    Filter actual = target.enableFilter(filterName);
    Filter expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEnabledFilter_A$String() throws Exception {
    String filterName = null;
    Filter actual = target.getEnabledFilter(filterName);
    Filter expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCriteriaBuilder_A$() throws Exception {
    CriteriaBuilder actual = target.getCriteriaBuilder();
    CriteriaBuilder expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void disableFilter_A$String() throws Exception {
    String filterName = null;
    target.disableFilter(filterName);
  }

  @Test
  public void getMetamodel_A$() throws Exception {
    Metamodel actual = target.getMetamodel();
    Metamodel expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStatistics_A$() throws Exception {
    SessionStatistics actual = target.getStatistics();
    SessionStatistics expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void isReadOnly_A$Object() throws Exception {
    Object entityOrProxy = null;
    boolean actual = target.isReadOnly(entityOrProxy);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createEntityGraph_A$Class() throws Exception {
    Class<ClientAddress> rootType = ClientAddress.class;
    EntityGraph<ClientAddress> actual = target.createEntityGraph(rootType);
    EntityGraph<ClientAddress> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReadOnly_A$Object$boolean() throws Exception {
    Object entityOrProxy = null;
    boolean readOnly = false;
    target.setReadOnly(entityOrProxy, readOnly);
  }

  @Test
  public void getEntityGraphs_A$Class() throws Exception {
    final Class<ClientAddress> entityClass = ClientAddress.class;
    Object actual = target.getEntityGraphs(entityClass);
    Object expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void doWork_A$Work() throws Exception {
    Work work = mock(Work.class);
    target.doWork(work);
  }

  @Test(expected = HibernateException.class)
  public void doWork_A$Work_T$HibernateException() throws Exception {
    Work work = mock(Work.class);
    doThrow(HibernateException.class).when(work).execute(any(Connection.class));
    target.doWork(work);
  }

  @Test
  public void doReturningWork_A$ReturningWork() throws Exception {
    ReturningWork<Object> work = mock(ReturningWork.class);
    Object actual = target.doReturningWork(work);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = HibernateException.class)
  public void doReturningWork_A$ReturningWork_T$HibernateException() throws Exception {
    final ReturningWork<Object> work = mock(ReturningWork.class);
    doThrow(HibernateException.class).when(session).doReturningWork(any());
    target.doReturningWork(work);
  }

  @Test
  public void disconnect_A$() throws Exception {
    Connection actual = target.disconnect();
    Connection expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void reconnect_A$Connection() throws Exception {
    target.reconnect(con);
  }

  @Test
  public void isFetchProfileEnabled_A$String() throws Exception {
    String name = null;
    boolean actual = target.isFetchProfileEnabled(name);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = UnknownProfileException.class)
  public void isFetchProfileEnabled_A$String_T$UnknownProfileException() throws Exception {
    String name = null;
    doThrow(UnknownProfileException.class).when(session).isFetchProfileEnabled(any());
    target.isFetchProfileEnabled(name);
  }

  @Test
  public void enableFetchProfile_A$String() throws Exception {
    String name = null;
    target.enableFetchProfile(name);
  }

  @Test(expected = UnknownProfileException.class)
  public void enableFetchProfile_A$String_T$UnknownProfileException() throws Exception {
    String name = null;
    doThrow(UnknownProfileException.class).when(session).enableFetchProfile(any());
    target.enableFetchProfile(name);
  }

  @Test
  public void disableFetchProfile_A$String() throws Exception {
    String name = null;
    target.disableFetchProfile(name);
  }

  @Test(expected = UnknownProfileException.class)
  public void disableFetchProfile_A$String_T$UnknownProfileException() throws Exception {
    String name = null;
    doThrow(UnknownProfileException.class).when(session).disableFetchProfile(any());
    target.disableFetchProfile(name);
  }

  @Test
  public void getTypeHelper_A$() throws Exception {
    TypeHelper actual = target.getTypeHelper();
    TypeHelper expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLobHelper_A$() throws Exception {
    LobHelper actual = target.getLobHelper();
    LobHelper expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void addEventListeners_A$SessionEventListenerArray() throws Exception {
    SessionEventListener[] listeners = new SessionEventListener[] {};
    target.addEventListeners(listeners);
  }

  @Test
  public void createQuery_A$String() throws Exception {
    String queryString = null;
    Query actual = target.createQuery(queryString);
    Query expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createQuery_A$String$Class() throws Exception {
    String queryString = null;
    Class<ClientAddress> resultType = ClientAddress.class;
    Query<ClientAddress> actual = target.createQuery(queryString, resultType);
    Query<ClientAddress> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createQuery_A$CriteriaQuery() throws Exception {
    CriteriaQuery<Object> criteriaQuery = mock(CriteriaQuery.class);
    Query<Object> actual = target.createQuery(criteriaQuery);
    Query<Object> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createQuery_A$CriteriaUpdate() throws Exception {
    CriteriaUpdate updateQuery = mock(CriteriaUpdate.class);
    Query actual = target.createQuery(updateQuery);
    Query expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createQuery_A$CriteriaDelete() throws Exception {
    CriteriaDelete deleteQuery = mock(CriteriaDelete.class);
    Query actual = target.createQuery(deleteQuery);
    Query expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createNamedQuery_A$String$Class() throws Exception {
    String name = null;
    Class<ClientAddress> resultType = ClientAddress.class;
    Query<ClientAddress> actual = target.createNamedQuery(name, resultType);
    Query<ClientAddress> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createNativeQuery_A$String$Class() throws Exception {
    String sqlString = null;
    Class resultClass = null;
    NativeQuery actual = target.createNativeQuery(sqlString, resultClass);
    NativeQuery expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
