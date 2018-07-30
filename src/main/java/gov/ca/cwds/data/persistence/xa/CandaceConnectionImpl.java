package gov.ca.cwds.data.persistence.xa;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
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
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.CaresStackUtils;

/**
 * JDBC connection facade. By its nature, this implementation class must implement methods on
 * Connection interface, some of which are deprecated or otherwise disturb SonarQube. Get over it.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"deprecation", "rawtypes", "findbugs:SE_BAD_FIELD",
    "squid:CallToDeprecatedMethod", "squid:RedundantThrowsDeclarationCheck",
    "findsecbugs:SQL_INJECTION_JDBC", "findsecbugs:EXTERNAL_CONFIG_CONTROL"})
public class CandaceConnectionImpl implements Connection {

  private static final Logger LOGGER = LoggerFactory.getLogger(CandaceConnectionImpl.class);

  protected final Connection con;

  public CandaceConnectionImpl(Connection con) {
    LOGGER.trace("ctor: con class: {}", con.getClass().getName());
    this.con = con;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    LOGGER.trace("unwrap");
    return con.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    LOGGER.trace("isWrapperFor");
    return con.isWrapperFor(iface);
  }

  @Override
  public Statement createStatement() throws SQLException {
    LOGGER.debug("CandaceConnectionImpl.createStatement");
    return con.createStatement();
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    LOGGER.debug("CandaceConnectionImpl.prepareStatement: sql: {}", sql);
    return con.prepareStatement(sql);
  }

  @Override
  public CallableStatement prepareCall(String sql) throws SQLException {
    LOGGER.debug("CandaceConnectionImpl.prepareCall: sql: {}", sql);
    return con.prepareCall(sql);
  }

  @Override
  public String nativeSQL(String sql) throws SQLException {
    LOGGER.debug("CandaceConnectionImpl.nativeSQL: sql: {}", sql);
    return con.nativeSQL(sql);
  }

  @Override
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    LOGGER.debug("CandaceConnectionImpl.setAutoCommit: autoCommit: {}", autoCommit);
    con.setAutoCommit(autoCommit);
  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    LOGGER.trace("CandaceConnectionImpl.getAutoCommit");
    return con.getAutoCommit();
  }

  @Override
  public void commit() throws SQLException {
    LOGGER.info("*** CandaceConnectionImpl.commit ***");
    CaresStackUtils.logStack();
    con.commit();
  }

  @Override
  public void rollback() throws SQLException {
    LOGGER.info("*** CandaceConnectionImpl.rollback ***");
    CaresStackUtils.logStack();
    con.rollback();
  }

  @Override
  public void close() throws SQLException {
    LOGGER.info("*** CandaceConnectionImpl.close ***");
    CaresStackUtils.logStack();
    con.close();
  }

  @Override
  public boolean isClosed() throws SQLException {
    final boolean ret = con.isClosed();
    LOGGER.debug("CandaceConnectionImpl.isClosed: {}", ret);
    return ret;
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    LOGGER.trace("getMetaData");
    return con.getMetaData();
  }

  @Override
  public void setReadOnly(boolean readOnly) throws SQLException {
    LOGGER.debug("CandaceConnectionImpl.setReadOnly: readOnly: {}", readOnly);
    con.setReadOnly(readOnly);
  }

  @Override
  public boolean isReadOnly() throws SQLException {
    LOGGER.trace("CandaceConnectionImpl.isReadOnly: con.isReadOnly(): {}", con.isReadOnly());
    return con.isReadOnly();
  }

  @Override
  public void setCatalog(String catalog) throws SQLException {
    con.setCatalog(catalog);
  }

  @Override
  public String getCatalog() throws SQLException {
    return con.getCatalog();
  }

  @Override
  public void setTransactionIsolation(int level) throws SQLException {
    LOGGER.debug("CandaceConnectionImpl.setTransactionIsolation: level: {}", level);
    con.setTransactionIsolation(level);
  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    final int ret = con.getTransactionIsolation();
    LOGGER.debug("CandaceConnectionImpl.getTransactionIsolation: {}", ret);
    return ret;
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return con.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    con.clearWarnings();
  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency)
      throws SQLException {
    LOGGER.trace("createStatement(int, int)");
    return con.createStatement(resultSetType, resultSetConcurrency);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    LOGGER.trace("prepareStatement(String, int, int)");
    return con.prepareStatement(sql, resultSetType, resultSetConcurrency);
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    LOGGER.trace("prepareCall(String, int, int)");
    return con.prepareCall(sql, resultSetType, resultSetConcurrency);
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    LOGGER.trace("getTypeMap");
    return con.getTypeMap();
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    LOGGER.trace("setTypeMap");
    con.setTypeMap(map);
  }

  @Override
  public void setHoldability(int holdability) throws SQLException {
    LOGGER.trace("setHoldability(int)");
    con.setHoldability(holdability);
  }

  @Override
  public int getHoldability() throws SQLException {
    final int ret = con.getHoldability();
    LOGGER.trace("getHoldability: {}", ret);
    return ret;
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    LOGGER.trace("setSavepoint");
    return con.setSavepoint();
  }

  @Override
  public Savepoint setSavepoint(String name) throws SQLException {
    LOGGER.trace("setSavepoint(String): {}", name);
    return con.setSavepoint(name);
  }

  @Override
  public void rollback(Savepoint savepoint) throws SQLException {
    LOGGER.trace("rollback(Savepoint)");
    con.rollback(savepoint);
  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    LOGGER.trace("releaseSavepoint(Savepoint)");
    con.releaseSavepoint(savepoint);
  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    LOGGER.trace("createStatement(int, int, int)");
    return con.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    LOGGER.trace("prepareStatement(String, int, int)");
    return con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    LOGGER.trace("prepareCall(String, int, int)");
    return con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    LOGGER.trace("prepareStatement(String, int)");
    return con.prepareStatement(sql, autoGeneratedKeys);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    LOGGER.trace("prepareStatement(String, int[])");
    return con.prepareStatement(sql, columnIndexes);
  }

  @Override
  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    LOGGER.trace("prepareStatement(String, String[])");
    return con.prepareStatement(sql, columnNames);
  }

  @Override
  public Clob createClob() throws SQLException {
    return con.createClob();
  }

  @Override
  public Blob createBlob() throws SQLException {
    return con.createBlob();
  }

  @Override
  public NClob createNClob() throws SQLException {
    return con.createNClob();
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    return con.createSQLXML();
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {
    return con.isValid(timeout);
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    con.setClientInfo(name, value);
  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    con.setClientInfo(properties);
  }

  @Override
  public String getClientInfo(String name) throws SQLException {
    return con.getClientInfo(name);
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    return con.getClientInfo();
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return con.createArrayOf(typeName, elements);
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return con.createStruct(typeName, attributes);
  }

  @Override
  public void setSchema(String schema) throws SQLException {
    LOGGER.trace("setSchema: schema: {}", schema);
    con.setSchema(schema);
  }

  @Override
  public String getSchema() throws SQLException {
    final String schema = con.getSchema();
    LOGGER.trace("getSchema: schema: {}", schema);
    return schema;
  }

  @Override
  public void abort(Executor executor) throws SQLException {
    LOGGER.trace("abort: executor class: {}", executor.getClass().getName());
    con.abort(executor);
  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    LOGGER.trace("setNetworkTimeout: executor class: {}, milliseconds: {}",
        executor.getClass().getName(), milliseconds);
    con.setNetworkTimeout(executor, milliseconds);
  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    return con.getNetworkTimeout();
  }

}
