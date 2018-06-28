package gov.ca.cwds.data.persistence.xa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CandaceConnectionImplTest extends Doofenshmirtz<Client> {

  CandaceConnectionImpl target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new CandaceConnectionImpl(con);
  }

  @Test
  public void type() throws Exception {
    assertThat(CandaceConnectionImpl.class, notNullValue());
  }

  @Test
  public void unwrap_A$Class() throws Exception {
    Class<?> iface = Client.class;
    Object actual = target.unwrap(iface);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void unwrap_A$Class_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).unwrap(any());
    Class<Client> iface = Client.class;
    target.unwrap(iface);
  }

  @Test
  public void isWrapperFor_A$Class() throws Exception {
    Class<?> iface = Client.class;
    boolean actual = target.isWrapperFor(iface);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void isWrapperFor_A$Class_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).isWrapperFor(any());
    Class<?> iface = Client.class;
    target.isWrapperFor(iface);
  }

  @Test
  public void createStatement_A$() throws Exception {
    Statement actual = target.createStatement();
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = SQLException.class)
  public void createStatement_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).createStatement();
    target.createStatement();
  }

  @Test
  public void prepareStatement_A$String() throws Exception {
    String sql = null;
    PreparedStatement actual = target.prepareStatement(sql);
    PreparedStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void prepareStatement_A$String_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).prepareStatement(any());
    String sql = null;
    target.prepareStatement(sql);
  }

  @Test
  public void prepareCall_A$String() throws Exception {
    String sql = null;
    CallableStatement actual = target.prepareCall(sql);
    CallableStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void prepareCall_A$String_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).prepareCall(any());
    String sql = null;
    target.prepareCall(sql);
  }

  @Test
  public void nativeSQL_A$String() throws Exception {
    String sql = null;
    String actual = target.nativeSQL(sql);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void nativeSQL_A$String_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).nativeSQL(any());
    String sql = null;
    target.nativeSQL(sql);
  }

  @Test
  public void setAutoCommit_A$boolean() throws Exception {
    boolean autoCommit = false;
    target.setAutoCommit(autoCommit);
  }

  @Test(expected = SQLException.class)
  public void setAutoCommit_A$boolean_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).setAutoCommit(any(Boolean.class));
    boolean autoCommit = false;
    target.setAutoCommit(autoCommit);
  }

  @Test
  public void getAutoCommit_A$() throws Exception {
    boolean actual = target.getAutoCommit();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getAutoCommit_A$_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).getAutoCommit();
    target.getAutoCommit();
  }

  @Test
  public void commit_A$() throws Exception {
    target.commit();
  }

  @Test(expected = SQLException.class)
  public void commit_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).commit();
    target.commit();
  }

  @Test
  public void rollback_A$() throws Exception {
    target.rollback();
  }

  @Test(expected = SQLException.class)
  public void rollback_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).rollback();
    target.rollback();
  }

  @Test
  public void close_A$() throws Exception {
    target.close();
  }

  @Test(expected = SQLException.class)
  public void close_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).close();
    target.close();
  }

  @Test
  public void isClosed_A$() throws Exception {
    boolean actual = target.isClosed();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void isClosed_A$_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).isClosed();
    target.isClosed();
  }

  @Test
  public void getMetaData_A$() throws Exception {
    DatabaseMetaData actual = target.getMetaData();
    DatabaseMetaData expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getMetaData_A$_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).getMetaData();
    target.getMetaData();
  }

  @Test
  public void setReadOnly_A$boolean() throws Exception {
    boolean readOnly = false;
    target.setReadOnly(readOnly);
  }

  @Test(expected = SQLException.class)
  public void setReadOnly_A$boolean_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).setReadOnly(any(Boolean.class));
    boolean readOnly = false;
    target.setReadOnly(readOnly);
  }

  @Test
  public void isReadOnly_A$() throws Exception {
    boolean actual = target.isReadOnly();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void isReadOnly_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).isReadOnly();
    target.isReadOnly();
  }

  @Test
  public void setCatalog_A$String() throws Exception {
    String catalog = null;
    target.setCatalog(catalog);
  }

  @Test(expected = SQLException.class)
  public void setCatalog_A$String_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).setCatalog(any());
    String catalog = null;
    target.setCatalog(catalog);
  }

  @Test
  public void getCatalog_A$() throws Exception {
    when(con.getCatalog()).thenReturn("CWSNS4");
    String actual = target.getCatalog();
    String expected = "CWSNS4";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getCatalog_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).getCatalog();
    target.getCatalog();
  }

  @Test
  public void setTransactionIsolation_A$int() throws Exception {
    int level = 0;
    target.setTransactionIsolation(level);
  }

  @Test(expected = SQLException.class)
  public void setTransactionIsolation_A$int_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).setTransactionIsolation(any(Integer.class));
    int level = 0;
    target.setTransactionIsolation(level);
  }

  @Test
  public void getTransactionIsolation_A$() throws Exception {
    int actual = target.getTransactionIsolation();
    int expected = 0;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getTransactionIsolation_A$_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).getTransactionIsolation();
    target.getTransactionIsolation();
  }

  @Test
  public void getWarnings_A$() throws Exception {
    SQLWarning actual = target.getWarnings();
    SQLWarning expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getWarnings_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).getWarnings();
    target.getWarnings();
  }

  @Test
  public void clearWarnings_A$() throws Exception {
    target.clearWarnings();
  }

  @Test(expected = SQLException.class)
  public void clearWarnings_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).clearWarnings();
    target.clearWarnings();
  }

  @Test
  public void createStatement_A$int$int() throws Exception {
    int resultSetType = 0;
    int resultSetConcurrency = 0;
    Statement actual = target.createStatement(resultSetType, resultSetConcurrency);
    Statement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void prepareStatement_A$String$int$int() throws Exception {
    String sql = null;
    int resultSetType = 0;
    int resultSetConcurrency = 0;
    PreparedStatement actual = target.prepareStatement(sql, resultSetType, resultSetConcurrency);
    PreparedStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void prepareCall_A$String$int$int() throws Exception {
    String sql = null;
    int resultSetType = 0;
    int resultSetConcurrency = 0;
    CallableStatement actual = target.prepareCall(sql, resultSetType, resultSetConcurrency);
    CallableStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setTypeMap_A$Map() throws Exception {
    Map map = new HashMap();
    target.setTypeMap(map);
  }

  @Test(expected = SQLException.class)
  public void setTypeMap_A$Map_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).setTypeMap(any());
    Map map = new HashMap();
    target.setTypeMap(map);
  }

  @Test
  public void setHoldability_A$int() throws Exception {
    int holdability = 0;
    target.setHoldability(holdability);
  }

  @Test(expected = SQLException.class)
  public void setHoldability_A$int_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).setHoldability(any(Integer.class));
    int holdability = 0;
    target.setHoldability(holdability);
  }

  @Test
  public void getHoldability_A$() throws Exception {
    int actual = target.getHoldability();
    int expected = 0;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getHoldability_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).getHoldability();
    target.getHoldability();
  }

  @Test
  public void setSavepoint_A$() throws Exception {
    Savepoint actual = target.setSavepoint();
    Savepoint expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void setSavepoint_A$_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).setSavepoint();
    target.setSavepoint();
  }

  @Test
  public void setSavepoint_A$String() throws Exception {
    String name = null;
    Savepoint actual = target.setSavepoint(name);
    Savepoint expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void rollback_A$Savepoint() throws Exception {
    Savepoint savepoint = mock(Savepoint.class);
    target.rollback(savepoint);
  }

  @Test
  public void releaseSavepoint_A$Savepoint() throws Exception {
    Savepoint savepoint = mock(Savepoint.class);
    target.releaseSavepoint(savepoint);
  }

  @Test(expected = SQLException.class)
  public void releaseSavepoint_A$Savepoint_T$SQLException() throws Exception {
    Savepoint savepoint = mock(Savepoint.class);
    doThrow(new SQLException()).when(con).releaseSavepoint(any(Savepoint.class));
    target.releaseSavepoint(savepoint);
  }

  @Test
  public void createStatement_A$int$int$int() throws Exception {
    int resultSetType = 0;
    int resultSetConcurrency = 0;
    int resultSetHoldability = 0;
    Statement actual =
        target.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    Statement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void prepareStatement_A$String$int$int$int() throws Exception {
    String sql = null;
    int resultSetType = 0;
    int resultSetConcurrency = 0;
    int resultSetHoldability = 0;
    PreparedStatement actual =
        target.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    PreparedStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void prepareCall_A$String$int$int$int() throws Exception {
    String sql = null;
    int resultSetType = 0;
    int resultSetConcurrency = 0;
    int resultSetHoldability = 0;
    CallableStatement actual =
        target.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    CallableStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void prepareStatement_A$String$int() throws Exception {
    String sql = null;
    int autoGeneratedKeys = 0;
    PreparedStatement actual = target.prepareStatement(sql, autoGeneratedKeys);
    PreparedStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void prepareStatement_A$String$intArray() throws Exception {
    String sql = null;
    int[] columnIndexes = new int[] {};
    PreparedStatement actual = target.prepareStatement(sql, columnIndexes);
    PreparedStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void prepareStatement_A$String$StringArray() throws Exception {
    String sql = null;
    String[] columnNames = new String[] {};
    PreparedStatement actual = target.prepareStatement(sql, columnNames);
    PreparedStatement expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createClob_A$() throws Exception {
    Clob actual = target.createClob();
    Clob expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void createClob_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).createClob();
    target.createClob();
  }

  @Test
  public void createBlob_A$() throws Exception {
    Blob actual = target.createBlob();
    Blob expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void createBlob_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).createBlob();
    target.createBlob();
  }

  @Test
  public void createNClob_A$() throws Exception {
    NClob actual = target.createNClob();
    NClob expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void createNClob_A$_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).createNClob();
    target.createNClob();
  }

  @Test
  public void createSQLXML_A$() throws Exception {
    SQLXML actual = target.createSQLXML();
    SQLXML expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void createSQLXML_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).createSQLXML();
    target.createSQLXML();
  }

  @Test
  public void isValid_A$int() throws Exception {
    int timeout = 0;
    boolean actual = target.isValid(timeout);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void isValid_A$int_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).isValid(any(Integer.class));
    int timeout = 0;
    target.isValid(timeout);
  }

  @Test
  public void setClientInfo_A$String$String() throws Exception {
    String name = null;
    String value = null;
    target.setClientInfo(name, value);
  }

  @Test(expected = SQLClientInfoException.class)
  public void setClientInfo_A$String$String_T$SQLClientInfoException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).setClientInfo(any(), any());
    String name = null;
    String value = null;
    target.setClientInfo(name, value);
  }

  @Test
  public void setClientInfo_A$Properties() throws Exception {
    Properties properties = mock(Properties.class);
    target.setClientInfo(properties);
  }

  @Test
  public void getClientInfo_A$String() throws Exception {
    String name = null;
    String actual = target.getClientInfo(name);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getClientInfo_A$String_T$SQLException() throws Exception {
    doThrow(new SQLClientInfoException()).when(con).getClientInfo(any());
    String name = null;
    target.getClientInfo(name);
  }

  @Test
  public void getClientInfo_A$() throws Exception {
    Properties actual = target.getClientInfo();
    Properties expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void createArrayOf_A$String$ObjectArray() throws Exception {
    String typeName = null;
    Object[] elements = new Object[] {};
    Array actual = target.createArrayOf(typeName, elements);
    Array expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void createArrayOf_A$String$ObjectArray_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).createArrayOf(any(), any());
    String typeName = null;
    Object[] elements = new Object[] {};
    target.createArrayOf(typeName, elements);
  }

  @Test
  public void createStruct_A$String$ObjectArray() throws Exception {
    String typeName = null;
    Object[] attributes = new Object[] {};
    Struct actual = target.createStruct(typeName, attributes);
    Struct expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void createStruct_A$String$ObjectArray_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).createStruct(any(), any());
    String typeName = null;
    Object[] attributes = new Object[] {};
    target.createStruct(typeName, attributes);
  }

  @Test
  public void setSchema_A$String() throws Exception {
    String schema = null;
    target.setSchema(schema);
  }

  @Test(expected = SQLException.class)
  public void setSchema_A$String_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).setSchema(any());
    String schema = null;
    target.setSchema(schema);
  }

  @Test
  public void getSchema_A$() throws Exception {
    String actual = target.getSchema();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getSchema_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).getSchema();
    target.getSchema();
  }

  @Test
  public void abort_A$Executor() throws Exception {
    Executor executor = mock(Executor.class);
    target.abort(executor);
  }

  @Test(expected = SQLException.class)
  public void abort_A$Executor_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).abort(any(Executor.class));
    Executor executor = mock(Executor.class);
    target.abort(executor);
  }

  @Test
  public void setNetworkTimeout_A$Executor$int() throws Exception {
    Executor executor = mock(Executor.class);
    int milliseconds = 0;
    target.setNetworkTimeout(executor, milliseconds);
  }

  @Test(expected = SQLException.class)
  public void setNetworkTimeout_A$Executor$int_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).setNetworkTimeout(any(Executor.class),
        any(Integer.class));
    Executor executor = mock(Executor.class);
    int milliseconds = 0;
    target.setNetworkTimeout(executor, milliseconds);
  }

  @Test
  public void getNetworkTimeout_A$() throws Exception {
    int actual = target.getNetworkTimeout();
    int expected = 0;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void getNetworkTimeout_A$_T$SQLException() throws Exception {
    doThrow(new SQLException()).when(con).getNetworkTimeout();
    target.getNetworkTimeout();
  }

}
