package gov.ca.cwds.rest.util.jni;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class LZWCompressionTest extends Doofenshmirtz<CmsDocument> {

  protected static final String TEST_BASE = "/jni/lzw/";
  protected static final String GOOD_LZW = TEST_BASE + "good.lzw";
  protected static final String GOOD_LZW_BASE64 = TEST_BASE + "good.b64";
  protected static final String GOOD_DOC = TEST_BASE + "good.doc";

  protected LZWEncoder lzw;

  @Before
  public void setUpBeforeTest() throws Exception {
    super.setup();
    this.lzw = new LZWEncoder();
  }

  // ===================
  // DECOMPRESS:
  // ===================

  @Test
  public void testDecompressGood() {
    if (this.lzw == null || !LZWEncoder.isClassloaded()) {
      // Build platform does not yet support this test.
      return;
    }

    // TODO: verify that temp files are deleted!
    try {
      final String src = LZWCompressionTest.class.getResource(GOOD_LZW).getPath();
      final String good = LZWCompressionTest.class.getResource(GOOD_DOC).getPath();

      final File tgt = File.createTempFile("tgt", ".lzw");
      tgt.deleteOnExit();

      lzw.fileCopyUncompress(src, tgt.getAbsolutePath());

      final String chkTgt = CWDSCompressionUtils.checksum(tgt);
      final String chkGood = CWDSCompressionUtils.checksum(new File(good));

      assertTrue("LZW decompression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

  // ===================
  // COMPRESS:
  // ===================

  @Test
  public void testCompressGood() {
    if (this.lzw == null || !LZWEncoder.isClassloaded()) {
      // Build platform does not yet support this test.
      return;
    }

    try {
      final String src = LZWCompressionTest.class.getResource(GOOD_DOC).getPath();
      final String good = LZWCompressionTest.class.getResource(GOOD_LZW).getPath();

      final File tgt = File.createTempFile("tgt", ".doc");
      tgt.deleteOnExit();

      lzw.fileCopyCompress(src, tgt.getAbsolutePath());

      final String chkTgt = CWDSCompressionUtils.checksum(tgt);
      final String chkGood = CWDSCompressionUtils.checksum(new File(good));

      assertTrue("LZW compression failed", chkTgt.equals(chkGood));
    } catch (Exception e) {
      fail("Exception: " + e.getMessage());
    }
  }

}
