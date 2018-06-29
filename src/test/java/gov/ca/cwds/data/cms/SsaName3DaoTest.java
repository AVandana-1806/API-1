package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.OtherClientName;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProvider;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class SsaName3DaoTest extends Doofenshmirtz<Client> {

  SsaName3Dao target;
  Query<Client> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new SsaName3Dao(sessionFactory);
    q = queryInator();

    when(proc.getOutputParameterValue("RETSTATUS")).thenReturn("0");
    when(proc.getOutputParameterValue("RETMESSAG")).thenReturn("hello world");
  }

  @Test
  public void type() throws Exception {
    assertThat(SsaName3Dao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void addressSsaname3_A$String$Address() throws Exception {
    String crudOperation = "I";
    Address address = new Address();
    target.addressSsaname3(crudOperation, address);
  }

  @Test
  public void clientSsaname3_A$String$Client() throws Exception {
    String crudOperation = "I";
    Client client = new Client();
    target.clientSsaname3(crudOperation, client);
  }

  @Test
  public void otherClientSsaname3_A$String$OtherClientName() throws Exception {
    String crudOperation = "I";
    OtherClientName otherClientName = new OtherClientName();
    target.otherClientSsaname3(crudOperation, otherClientName);
  }

  @Test
  public void subCareProviderSsaname3_A$String$SubstituteCareProvider() throws Exception {
    String crudOperation = "I";
    SubstituteCareProvider substituteCareProvider = new SubstituteCareProvider();
    target.subCareProviderSsaname3(crudOperation, substituteCareProvider);
  }

  @Test
  public void deleteSsaname3_A$String$String$String() throws Exception {
    String phttTable = null;
    String primaryKey = null;
    String nameCode = null;
    target.deleteSsaname3(phttTable, primaryKey, nameCode);
  }

  @Test
  public void callStoredProc_A$String$String$String$String$String$String$String$String$String$Short$Date$String()
      throws Exception {
    String tableName = null;
    String crudOper = null;
    String identifier = null;
    String nameCd = null;
    String firstName = null;
    String middleName = null;
    String lastName = null;
    String streettNumber = null;
    String streetName = null;
    Short gvrEntc = null;
    Date updateTimeStamp = new Date();
    String updateId = null;
    target.callStoredProc(tableName, crudOper, identifier, nameCd, firstName, middleName, lastName,
        streettNumber, streetName, gvrEntc, updateTimeStamp, updateId);
  }

}

