package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.flywaydb.core.internal.util.FileCopyUtils;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.util.jni.LZWCompressionTest;
import gov.ca.cwds.rest.util.jni.LZWEncoder;
import gov.ca.cwds.rest.util.jni.PKCompressionTest;
import gov.ca.cwds.utils.JsonUtils;

public class CmsDocumentDaoTest extends LZWCompressionTest {

  CmsDocument doc;
  CmsDocumentDao target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setUpBeforeTest();

    lzw = mock(LZWEncoder.class);
    target = new CmsDocumentDao(sessionFactory);
    doc = new CmsDocument();

    new TestingRequestExecutionContext("0X5");
    RequestExecutionContext.instance();
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsDocumentDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void compressPK_Args__CmsDocument__String() throws Exception {
    final String src = PKCompressionTest.class.getResource(PKCompressionTest.ZIP_B64_3).getPath();
    final String base64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
    List<CmsDocumentBlobSegment> actual = target.compressPK(doc, base64);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void decompressDoc_Args__CmsDocument() throws Exception {
    final String src = PKCompressionTest.class.getResource(PKCompressionTest.ZIP_B64_3).getPath();
    final String base64 = FileCopyUtils.copyToString(new FileReader(new File(src))).trim();
    final List<CmsDocumentBlobSegment> blobs = target.compressPK(doc, base64);
    doc.setBlobSegments(new HashSet<>(blobs));
    String actual = target.decompressDoc(doc);
    assertThat(actual, is(notNullValue()));
  }

  // @Test
  // public void decompressPK_Args__CmsDocument() throws Exception {
  // String actual = target.decompressPK(doc);
  // String expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  // @Test
  // public void decompressLZW_Args__CmsDocument() throws Exception {
  // if (this.inst == null || !LZWEncoder.isClassloaded()) {
  // // Build platform does not yet support this test.
  // return;
  // }

  //
  // final String good = LZWCompressionTest.class.getResource(GOOD_DOC).getPath();
  //
  // String actual = target.decompressLZW(doc);
  // String expected = null;
  // assertThat(actual, is(equalTo(expected)));
  // }

  @Test
  public void compressPK_A$CmsDocument$String() throws Exception {
    final String base64 = getClass().getResource(PKCompressionTest.ZIP_DOC_1).getPath();
    List<CmsDocumentBlobSegment> actual = target.compressPK(doc, base64);

    final CmsDocumentBlobSegment expected = JsonUtils.from(
        "{\"docHandle\":null,\"segmentSequence\":\"0001\",\"docBlob\":\"++stt+783U0Za9v/aKzz1sw8vj9rxv48zf99FfuX+d/v2q0LAA==\",\"primaryKey\":{\"columns\":[\" \",\"0001\"]}}",
        CmsDocumentBlobSegment.class);
    assertThat(actual.get(0), is(equalTo(expected)));
  }

  @Test
  public void compressPlain_A$CmsDocument$String() throws Exception {
    final String base64 = getClass().getResource(PKCompressionTest.ZIP_DOC_1).getPath();
    List<CmsDocumentBlobSegment> actual = target.compressPlain(doc, base64);
    List<CmsDocumentBlobSegment> expected = new ArrayList<>();
    expected.add(JsonUtils.from(
        "{\"docHandle\":null,\"segmentSequence\":\"0001\",\"docBlob\":\"++stt+783U0Za9v/aKzz1sw8vj9rxv48zf99FfuX+d/v2q0LAA==\",\"primaryKey\":{\"columns\":[\" \",\"0001\"]}}",
        CmsDocumentBlobSegment.class));
    assertThat(actual, is(notNullValue()));
    // assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void decompressDoc_A$CmsDocument() throws Exception {
    String actual = target.decompressDoc(doc);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void compressDoc_A$CmsDocument$String() throws Exception {
    final String base64 = FileCopyUtils
        .copyToString(new FileReader(new File(getClass().getResource(GOOD_DOC).getPath()))).trim();
    List<CmsDocumentBlobSegment> actual = target.compressDoc(doc, base64);
    List<CmsDocumentBlobSegment> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void decompressPlain_A$CmsDocument() throws Exception {
    String actual = target.decompressPlain(doc);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void decompressLZW_A$CmsDocument() throws Exception {
    LZWCompressionTest.class.getResource(GOOD_DOC).getPath();
    String actual = target.decompressLZW(doc);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void blobSegmentsToFile_A$CmsDocument$File() throws Exception {
    File src = mock(File.class);
    target.blobSegmentsToFile(doc, src);
  }

  @Test
  public void compressLZW_A$CmsDocument$String() throws Exception {
    String base64 = null;
    List<CmsDocumentBlobSegment> actual = target.compressLZW(doc, base64);
    List<CmsDocumentBlobSegment> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void errorDecompressing_A$Exception() throws Exception {
    Exception e = new IllegalStateException("test");
    target.errorDecompressing(e);
  }

  @Test
  public void errorCompressing_A$Exception() throws Exception {
    Exception e = new IllegalStateException("test");
    target.errorCompressing(e);
  }

}
