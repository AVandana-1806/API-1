package gov.ca.cwds.inject;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.xa.WorkFerbUserInfo;

public class CaresDB2DatasourceConnectionProviderImpl extends DatasourceConnectionProviderImpl {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CaresDB2DatasourceConnectionProviderImpl.class);

  private static final long serialVersionUID = 1L;

  @Override
  public Connection getConnection() throws SQLException {
    LOGGER.info("CaresDB2DatasourceConnectionProviderImpl.getConnection");
    final Connection con = super.getConnection();
    new WorkFerbUserInfo().execute(con);
    return con;
  }

}
