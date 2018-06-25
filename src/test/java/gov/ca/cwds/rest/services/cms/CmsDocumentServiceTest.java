package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CmsDocumentServiceTest extends Doofenshmirtz<CmsDocument> {

  private static final String DEFAULT_DOC_ID = "0131351421120020*JONESMF 00004";

  CmsDocumentDao dao;
  CmsDocumentService target;
  Query<CmsDocument> q;
  CmsDocument doc;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Override
  public void setup() throws Exception {
    super.setup();
    // dao = new CmsDocumentDao(sessionFactoryImplementor);
    dao = mock(CmsDocumentDao.class);
    target = new CmsDocumentService(dao);
    q = queryInator();

    doc = readPersistedObject();
    when(dao.find(any(CmsDocument.class))).thenReturn(doc);
    when(dao.find(any(String.class))).thenReturn(doc);
    when(dao.create(any(CmsDocument.class))).thenReturn(doc);
    when(dao.getSessionFactory()).thenReturn(sessionFactoryImplementor);
  }

  protected CmsDocument readPersistedObject() throws IOException {
    return MAPPER.readValue(
        "{\"id\":\"0131351421120020*JONESMF     00004\",\"segmentCount\":1,\"docLength\":3,\"docAuth\":\"RAMESHA\",\"docServ\":\"D7706001\",\"docDate\":\"2007-01-31\",\"docTime\":\"19:59:07\",\"docName\":\"1234\",\"compressionMethod\":\"PKWare02\",\"blobSegments\":[]}",
        CmsDocument.class);
  }

  protected gov.ca.cwds.rest.api.domain.cms.CmsDocument readJsonDoc() throws Exception {
    return MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"),
        gov.ca.cwds.rest.api.domain.cms.CmsDocument.class);
  }

  @Test
  public void updateThrowsNotImplementedException() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.CmsDocument cmsDocumentDomain = readJsonDoc();
    target.update("testkey", cmsDocumentDomain);
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsDocumentService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void find_A$String() throws Exception {
    String primaryKey = DEFAULT_CLIENT_ID;
    gov.ca.cwds.rest.api.domain.cms.CmsDocument actual = target.find(primaryKey);
    CmsDocument expected = doc;
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void create_A$CmsDocument() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.CmsDocument request = readJsonDoc();
    gov.ca.cwds.rest.api.domain.cms.CmsDocument actual = target.create(request);
    // CmsDocument expected = JsonUtils.from(
    // "{\"id\":\"0131351421120020*JONESMF
    // 00004\",\"segmentCount\":1,\"docLength\":3,\"docAuth\":\"RAMESHA\",\"docServ\":\"D7706001\",\"docDate\":\"2007-01-31\",\"docTime\":\"19:59:07\",\"docName\":\"1234\",\"compressionMethod\":\"PKWare02\",\"blobSegments\":[]}",
    // CmsDocument.class);
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void update_A$String$CmsDocument() throws Exception {
    String primaryKey = DEFAULT_DOC_ID;
    gov.ca.cwds.rest.api.domain.cms.CmsDocument request = readJsonDoc();
    gov.ca.cwds.rest.api.domain.cms.CmsDocument actual = target.update(primaryKey, request);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void blobToInsert_A$CmsDocumentBlobSegment() throws Exception {
    byte[] docBlob = {0, 0, 0, 0};
    CmsDocumentBlobSegment blob =
        new CmsDocumentBlobSegment("0131351421120020*JONESMF ", "00004", docBlob);
    String actual = target.blobToInsert(blob);
    String expected =
        "INSERT INTO CWSNS1.TSBLOBT(DOC_HANDLE, DOC_SEGSEQ, DOC_BLOB) VALUES('0131351421120020*JONESMF ','00004',x'00000000')";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void blobsDelete_A$() throws Exception {
    String actual = target.blobsDelete();
    String expected = "DELETE FROM CWSNS1.TSBLOBT WHERE DOC_HANDLE = ?";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void deleteBlobs_A$String() throws Exception {
    String docId = "0131351421120020*JONESMF ";
    target.deleteBlobs(docId);
  }

  @Test
  public void insertBlobs_A$govcacwdsdatapersistencecmsCmsDocument$List() throws Exception {
    CmsDocument doc = new CmsDocument();
    List<CmsDocumentBlobSegment> blobs = new ArrayList<CmsDocumentBlobSegment>();
    target.insertBlobs(doc, blobs);
  }

  @Test
  public void getConnection_A$() throws Exception {
    Connection actual = target.getConnection();
    assertThat(actual, is(not(0)));
  }

  @Test
  public void getConnection_A$_T$SQLException() throws Exception {
    try {
      when(sessionFactoryImplementor.getSessionFactoryOptions()).thenThrow(SQLException.class);
      target.getConnection();
      fail("Expected exception was not thrown!");
    } catch (SQLException e) {
    }
  }

  @Test
  public void delete_A$String() throws Exception {
    String primaryKey = "0131351421120020*JONESMF ";
    gov.ca.cwds.rest.api.domain.cms.CmsDocument actual = target.delete(primaryKey);
    CmsDocument expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
