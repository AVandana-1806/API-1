package gov.ca.cwds.data.cms;

import gov.ca.cwds.rest.util.FerbDateUtils;
import java.util.Date;

import javax.persistence.ParameterMode;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.OtherClientName;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Hibernate DAO for DB2 stored procedure that calls soundex SSA Name3 and saves to the client
 * phonetic tables .
 * 
 * @author CWDS API Team
 * @see CmsSessionFactory
 * @see SessionFactory
 */
public class SsaName3Dao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SsaName3Dao.class);

  private final static String TABLE_NAME = "TABLENAME";
  private final static String CRUD_FUNCTION = "CRUDFUNCT";
  private final static String IDENTIFIER = "IDENTIFIER";
  private final static String NAME_CODE = "NAMECODE";
  private final static String FIRST_NAME = "FIRSTNAME";
  private final static String MIDDLE_NAME = "MIDDLENAME";
  private final static String LAST_NAME = "LASTNAME";
  private final static String STREET_NUMBER = "STREETNUM";
  private final static String STREET_NAME = "STREETNAME";
  private final static String GOVERNMENT_ENTITY_CODE = "GVRENTC";
  private final static String LAST_UPDATE_TIMESTAMP = "LASTUPDTM";
  private final static String LAST_UPDATE_ID = "LASTUPDID";
  private final static String RETURN_STATUS = "RETSTATUS";
  private final static String RETURN_MESSAGE = "RETMESSAG";

  private SessionFactory sessionFactory;
  private short s = 0;

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public SsaName3Dao(@CmsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * @param crudOperation I/U/D
   * @param address address
   */
  public void addressSsaname3(String crudOperation, Address address) {
    SsaName3 ssaName3 = new SsaName3();
    ssaName3.setTableName(LegacyTable.ADDRESS_PHONETIC.getName());
    ssaName3.setCrudFunction(crudOperation);
    ssaName3.setIdentifier(address.getId());
    ssaName3.setNameCode("A");
    ssaName3.setStreetNumber(address.getStreetNumber());
    ssaName3.setStreetName(address.getStreetName());
    ssaName3.setGovernmentEntityCode(s);
    ssaName3.setUpdateTimestamp(address.getLastUpdatedTime());
    ssaName3.setUpdateId(address.getLastUpdatedId());

    callStoredProc(ssaName3);
  }

  /**
   * @param crudOperation I/U/D
   * @param client client
   */
  public void clientSsaname3(String crudOperation, Client client) {
    SsaName3 ssaName3 = new SsaName3();
    ssaName3.setTableName(LegacyTable.CLIENT_PHONETIC.getName());
    ssaName3.setCrudFunction(crudOperation);
    ssaName3.setIdentifier(client.getId());
    ssaName3.setNameCode("C");
    ssaName3.setFirstName(client.getFirstName());
    ssaName3.setMiddleName(client.getMiddleName());
    ssaName3.setLastName(client.getLastName());
    ssaName3.setGovernmentEntityCode(s);
    ssaName3.setUpdateTimestamp(client.getLastUpdatedTime());
    ssaName3.setUpdateId(client.getLastUpdatedId());

    callStoredProc(ssaName3);
  }

  /**
   * @param crudOperation I/U/D
   * @param otherClientName other client name object
   */
  public void otherClientSsaname3(String crudOperation, OtherClientName otherClientName) {
    SsaName3 ssaName3 = new SsaName3();
    ssaName3.setTableName(LegacyTable.CLIENT_PHONETIC.getName());
    ssaName3.setCrudFunction(crudOperation);
    ssaName3.setIdentifier(otherClientName.getThirdId());
    ssaName3.setNameCode("N");
    ssaName3.setFirstName(otherClientName.getFirstName());
    ssaName3.setMiddleName(otherClientName.getMiddleName());
    ssaName3.setLastName(otherClientName.getLastName());
    ssaName3.setGovernmentEntityCode(s);
    ssaName3.setUpdateTimestamp(otherClientName.getLastUpdatedTime());
    ssaName3.setUpdateId(otherClientName.getLastUpdatedId());

    callStoredProc(ssaName3);
  }

  /**
   * @param crudOperation I/U/D
   * @param substituteCareProvider care provider
   */
  public void subCareProviderSsaname3(String crudOperation,
      SubstituteCareProvider substituteCareProvider) {
    SsaName3 ssaName3 = new SsaName3();
    ssaName3.setTableName(LegacyTable.SUBSTITUTE_CARE_PROVIDER_PHONETIC.getName());
    ssaName3.setCrudFunction(crudOperation);
    ssaName3.setIdentifier(substituteCareProvider.getId());
    ssaName3.setFirstName(substituteCareProvider.getFirstName());
    ssaName3.setMiddleName(substituteCareProvider.getMiddleName());
    ssaName3.setLastName(substituteCareProvider.getLastName());
    ssaName3.setGovernmentEntityCode(s);
    ssaName3.setUpdateTimestamp(substituteCareProvider.getLastUpdatedTime());
    ssaName3.setUpdateId(substituteCareProvider.getLastUpdatedId());

    callStoredProc(ssaName3);
  }

  /**
   * @param phttTable phonetic table name
   * @param primaryKey primary key
   * @param nameCode defines type of entity
   */

  public void deleteSsaname3(String phttTable, String primaryKey, String nameCode) {
    SsaName3 ssaName3 = new SsaName3();
    ssaName3.setTableName(phttTable);
    ssaName3.setCrudFunction("D");
    ssaName3.setIdentifier(primaryKey);
    ssaName3.setNameCode(nameCode);
    ssaName3.setGovernmentEntityCode(s);

    callStoredProc(ssaName3);
  }

  /**
   * Call DB2 stored procedure SPSSANAME3 to insert soundex records for client search. Story
   * #146481759.
   * 
   * @param ssaName3 parameters carrier
   */
  protected void callStoredProc(SsaName3 ssaName3) {
    final Session session = sessionFactory.getCurrentSession();
    final String STORED_PROC_NAME = "SPSSANAME3";
    final String schema =
        (String) session.getSessionFactory().getProperties().get("hibernate.default_schema");

    try {
      ProcedureCall q = session.createStoredProcedureCall(schema + "." + STORED_PROC_NAME);

      q.registerStoredProcedureParameter(TABLE_NAME, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(CRUD_FUNCTION, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(IDENTIFIER, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(NAME_CODE, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(FIRST_NAME, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(MIDDLE_NAME, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(LAST_NAME, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(STREET_NUMBER, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(STREET_NAME, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(GOVERNMENT_ENTITY_CODE, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(LAST_UPDATE_TIMESTAMP, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(LAST_UPDATE_ID, String.class, ParameterMode.IN);
      q.registerStoredProcedureParameter(RETURN_STATUS, String.class, ParameterMode.OUT);
      q.registerStoredProcedureParameter(RETURN_MESSAGE, String.class, ParameterMode.OUT);

      q.setParameter(TABLE_NAME, ssaName3.getTableName());
      q.setParameter(CRUD_FUNCTION, ssaName3.getCrudFunction());
      q.setParameter(IDENTIFIER, ssaName3.getIdentifier());
      q.setParameter(NAME_CODE, ssaName3.getNameCode());
      q.setParameter(FIRST_NAME, ssaName3.getFirstName());
      q.setParameter(MIDDLE_NAME, ssaName3.getMiddleName());
      q.setParameter(LAST_NAME, ssaName3.getLastName());
      q.setParameter(STREET_NUMBER, ssaName3.getStreetNumber());
      q.setParameter(STREET_NAME, ssaName3.getStreetName());
      q.setParameter(GOVERNMENT_ENTITY_CODE, String.valueOf(ssaName3.getGovernmentEntityCode()));
      q.setParameter(LAST_UPDATE_TIMESTAMP, DomainChef.cookTimestamp(ssaName3.getUpdateTimestamp()));
      q.setParameter(LAST_UPDATE_ID, ssaName3.getUpdateId());

      q.execute();

      final String returnStatus = (String) q.getOutputParameterValue(RETURN_STATUS);
      final String returnMessage = (String) q.getOutputParameterValue(RETURN_MESSAGE);
      int returnCode = Integer.parseInt(returnStatus);

      LOGGER.info("storeProcReturnStatus: {}, storeProcreturnMessage: {}", returnStatus,
          returnMessage);
      /*
       * return code: 0=successful, 1=keys not generated, 2=Invalid parameters sent to stored
       * procedure 3=SQL failed, 4=Call to SSANAME3 DLL failed
       */
      if (returnCode != 0 && returnCode != 1) {
        String msg =
            "Stored Procedure " + STORED_PROC_NAME + " returned with ERROR: " + returnMessage;
        LOGGER.error(msg);
        throw new DaoException(msg);
      }

    } catch (DaoException h) {
      throw new DaoException("Call to Stored Procedure " + STORED_PROC_NAME + " failed - " + h, h);
    }
  }

  static class SsaName3 {
    private String tableName = StringUtils.SPACE;
    private String crudFunction = StringUtils.SPACE;
    private String identifier = StringUtils.SPACE;
    private String nameCode = StringUtils.SPACE;
    private String firstName = StringUtils.SPACE;
    private String middleName = StringUtils.SPACE;
    private String lastName = StringUtils.SPACE;
    private String streetNumber = StringUtils.SPACE;
    private String streetName = StringUtils.SPACE;
    private Short governmentEntityCode;
    private Date updateTimestamp;
    private String updateId = StringUtils.SPACE;

    public String getTableName() {
      return tableName;
    }

    public void setTableName(String tableName) {
      this.tableName = tableName;
    }

    public String getCrudFunction() {
      return crudFunction;
    }

    public void setCrudFunction(String crudFunction) {
      this.crudFunction = crudFunction;
    }

    public String getIdentifier() {
      return identifier;
    }

    public void setIdentifier(String identifier) {
      this.identifier = identifier;
    }

    public String getNameCode() {
      return nameCode;
    }

    public void setNameCode(String nameCode) {
      this.nameCode = nameCode;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getMiddleName() {
      return middleName;
    }

    public void setMiddleName(String middleName) {
      this.middleName = middleName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getStreetNumber() {
      return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
      this.streetNumber = streetNumber;
    }

    public String getStreetName() {
      return streetName;
    }

    public void setStreetName(String streetName) {
      this.streetName = streetName;
    }

    public Short getGovernmentEntityCode() {
      return governmentEntityCode;
    }

    public void setGovernmentEntityCode(Short governmentEntityCode) {
      this.governmentEntityCode = governmentEntityCode;
    }

    public Date getUpdateTimestamp() {
      return FerbDateUtils.freshDate(updateTimestamp);
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
      this.updateTimestamp = FerbDateUtils.freshDate(updateTimestamp);
    }

    public String getUpdateId() {
      return updateId;
    }

    public void setUpdateId(String updateId) {
      this.updateId = updateId;
    }
  }

}
