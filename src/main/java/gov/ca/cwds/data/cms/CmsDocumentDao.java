package gov.ca.cwds.data.cms;

import static java.lang.Math.min;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.ByteArrayBuffer;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.util.jni.CmsPKCompressor;
import gov.ca.cwds.rest.util.jni.LZWEncoder;

/**
 * Data Access Object (DAO) for legacy, compressed CMS documents.
 * 
 * @author CWDS API Team
 */
public class CmsDocumentDao extends BaseDaoImpl<CmsDocument> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentDao.class);

  private static final String COMPRESSION_TYPE_LZW = "01";
  private static final String COMPRESSION_TYPE_PK = "02";
  private static final String COMPRESSION_TYPE_PLAIN = "00";

  public static final String COMPRESSION_TYPE_LZW_FULL = "CWSCMP01";
  public static final String COMPRESSION_TYPE_PK_FULL = "PKWare02";
  public static final String COMPRESSION_TYPE_PLAIN_FULL = "PLAIN_00";
  public static final int BLOB_SEGMENT_LENGTH = 4000;

  /**
   * Constructor.
   * 
   * @param sessionFactory Hibernate session factory
   */
  @Inject
  public CmsDocumentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Compress document blob segments with PKWare.
   * 
   * @param doc the document record
   * @param base64 base64 encoded bytes
   * @return new blobs in order
   */
  protected List<CmsDocumentBlobSegment> compressPK(final CmsDocument doc, String base64) {
    final List<CmsDocumentBlobSegment> blobs = new ArrayList<>();
    byte[] bytes = DatatypeConverter.parseBase64Binary(base64);

    try {
      final byte[] compressed = new CmsPKCompressor().compressBytes(bytes);

      int i = 0;
      int segmentStart = 0;
      while (segmentStart < compressed.length) {
        final String sequence = StringUtils.leftPad(String.valueOf(++i), 4, '0');
        final int segmentLength = min(compressed.length - segmentStart, BLOB_SEGMENT_LENGTH);
        blobs.add(new CmsDocumentBlobSegment(doc.getId(), sequence,
            Arrays.copyOfRange(compressed, segmentStart, segmentStart + segmentLength)));
        segmentStart += segmentLength;
      }

      doc.setCompressionMethod(COMPRESSION_TYPE_PK_FULL);
      doc.setDocLength((long) bytes.length);
      doc.setSegmentCount((short) i);

      final RequestExecutionContext ctx = RequestExecutionContext.instance();
      doc.setLastUpdatedTime(ctx.getRequestStartTime());
      doc.setLastUpdatedId(StringUtils.isNotBlank(ctx.getStaffId()) ? ctx.getStaffId() : "0x5");

    } catch (Exception e) {
      LOGGER.error("ERROR COMPRESSING PK! {}", e.getMessage());
      throw new ServiceException("ERROR COMPRESSING PK! " + e.getMessage(), e);
    }

    return blobs;
  }

  /**
   * No compressed document blob segments.
   *
   * @param doc the document record
   * @param base64 base64 encoded bytes
   * @return new blobs in order
   */
  private List<CmsDocumentBlobSegment> compressPLAIN(final CmsDocument doc, String base64) {
    final List<CmsDocumentBlobSegment> blobs = new ArrayList<>();
    try {
      final byte[] plain = DatatypeConverter.parseBase64Binary(base64);

      int i = 0;
      int segmentStart = 0;
      while (segmentStart < plain.length) {
        final String sequence = StringUtils.leftPad(String.valueOf(++i), 4, '0');
        final int segmentLength = min(plain.length - segmentStart, BLOB_SEGMENT_LENGTH);
        blobs.add(new CmsDocumentBlobSegment(doc.getId(), sequence,
            Arrays.copyOfRange(plain, segmentStart, segmentStart + segmentLength)));
        segmentStart += segmentLength;
      }

      doc.setCompressionMethod(COMPRESSION_TYPE_PLAIN_FULL);
      doc.setDocLength((long) plain.length);
      doc.setSegmentCount((short) i);

      final RequestExecutionContext ctx = RequestExecutionContext.instance();
      doc.setLastUpdatedTime(ctx.getRequestStartTime());
      doc.setLastUpdatedId(StringUtils.isNotBlank(ctx.getStaffId()) ? ctx.getStaffId() : "0x5");

    } catch (Exception e) {
      LOGGER.error("ERROR COMPRESSING PLAIN! {}", e.getMessage());
      throw new ServiceException("ERROR COMPRESSING PLAIN! " + e.getMessage(), e);
    }

    return blobs;
  }

  /**
   * Decompress (inflate) a document by determining the compression type, assembling blob segments,
   * and calling appropriate library.
   * 
   * @param doc LZW or PK archive to decompress
   * @return base64-encoded String of decompressed document
   */
  public String decompressDoc(CmsDocument doc) {
    String retval = "";

    if (doc.getCompressionMethod().endsWith(COMPRESSION_TYPE_LZW)) {
      LZWEncoder lzw = new LZWEncoder();
      if (!lzw.didLibraryLoad()) {
        LOGGER.warn("LZW compression not enabled!");
      } else {
        retval = decompressLZW(doc);
      }
    } else if (doc.getCompressionMethod().endsWith(COMPRESSION_TYPE_PK)) {
      retval = decompressPK(doc);
    } else if (doc.getCompressionMethod().endsWith(COMPRESSION_TYPE_PLAIN)) {
      retval = decompressPLAIN(doc);
    } else {
      LOGGER.error("UNSUPPORTED COMPRESSION METHOD! {}", doc.getCompressionMethod());
    }

    return retval;
  }

  /**
   * Compress (deflate) a document by determining the compression type, calling appropriate library
   * and splitting on blob segments.
   *
   *
   * @param doc document to compress
   * @param base64 base64 encoded bytes
   * @return list of blob segments of compressed document
   */
  public List<CmsDocumentBlobSegment> compressDoc(CmsDocument doc, String base64) {
    List<CmsDocumentBlobSegment> retval = null;

    if (doc.getCompressionMethod().endsWith(COMPRESSION_TYPE_LZW)) {
      LZWEncoder lzw = new LZWEncoder();
      if (!lzw.didLibraryLoad()) {
        LOGGER.warn("LZW compression not enabled!");
      } else {
        retval = compressLZW(doc, base64);
      }
    } else if (doc.getCompressionMethod().endsWith(COMPRESSION_TYPE_PK)) {
      retval = compressPK(doc, base64);
    } else if (doc.getCompressionMethod().endsWith(COMPRESSION_TYPE_PLAIN)) {
      retval = compressPLAIN(doc, base64);
    } else {
      LOGGER.error("UNSUPPORTED COMPRESSION METHOD! {}", doc.getCompressionMethod());
    }

    return retval;
  }

  /**
   * Decompress (inflate) a PKWare-compressed document by assembling blob segments and calling Java
   * PKWare SDK.
   * 
   * <p>
   * DB2 SQL returns blob segments as hexadecimal using the DB2 {@code blob()} function.
   * </p>
   * 
   * @param doc PK archive to decompress
   * @return base64-encoded String of decompressed document
   */
  private String decompressPK(CmsDocument doc) {
    String retval = "";

    try {
      final ByteArrayBuffer buf = new ByteArrayBuffer(doc.getDocLength().intValue());
      for (CmsDocumentBlobSegment seg : doc.getBlobSegments()) {
        buf.append(seg.getDocBlob(), 0, seg.getDocBlob().length);
      }

      final byte[] bytes = new CmsPKCompressor().decompressBytes(buf.buffer());
      LOGGER.debug("DAO: bytes len={}", bytes.length);
      retval = DatatypeConverter.printBase64Binary(bytes);
    } catch (Exception e) {
      LOGGER.error("ERROR DECOMPRESSING PK! {}", e.getMessage());
      throw new ServiceException("ERROR DECOMPRESSING PK! " + e.getMessage(), e);
    }

    return retval;
  }


  /**
   * Decompress (inflate) a non-compressed document by assembling blob segments
   *
   * <p>
   * DB2 SQL returns blob segments as hexadecimal using the DB2 {@code blob()} function.
   * </p>
   *
   * @param doc PLAIN archive to decompress
   * @return base64-encoded String of decompressed document
   */
  private String decompressPLAIN(CmsDocument doc) {
    String retval = "";

    try {
      final ByteArrayBuffer buf = new ByteArrayBuffer(doc.getDocLength().intValue());
      for (CmsDocumentBlobSegment seg : doc.getBlobSegments()) {
        buf.append(seg.getDocBlob(), 0, seg.getDocBlob().length);
      }

      final byte[] bytes = buf.buffer();
      LOGGER.debug("DAO: bytes len={}", bytes.length);
      retval = DatatypeConverter.printBase64Binary(bytes);
    } catch (Exception e) {
      LOGGER.error("ERROR DECOMPRESSING PLAIN! {}", e.getMessage());
      throw new ServiceException("ERROR DECOMPRESSING PLAINE! " + e.getMessage(), e);
    }

    return retval;
  }

  /**
   * Decompress (inflate) an LZW-compressed document by assembling blob segments and calling native
   * library.
   * 
   * <p>
   * OPTION: Trap std::exception in shared library and return error code. The LZW library currently
   * returns a blank when decompression fails, for safety, since unhandled C++ exceptions kill the
   * JVM.
   * </p>
   * 
   * <p>
   * For security reasons, remove temporary documents immediately. OPTION: pass bytes to C++ library
   * instead of file names.
   * </p>
   * 
   * @param doc LZW archive to decompress
   * @return base64-encoded String of decompressed document
   */
  @SuppressFBWarnings("PATH_TRAVERSAL_IN") // There is no path traversal here
  private String decompressLZW(CmsDocument doc) {
    String retval = "";

    File src = null;
    File tgt = null;
    try {
      src = File.createTempFile("src", ".lzw");
      src.deleteOnExit();
      tgt = File.createTempFile("tgt", ".doc");
      tgt.deleteOnExit();
    } catch (IOException e) {
      errorDecompressing(e);
    }

    try {
      blobSegmentsToFile(doc, src);
      // DECOMPRESS!
      final LZWEncoder lzw = new LZWEncoder();
      lzw.fileCopyUncompress(src.getAbsolutePath(), tgt.getAbsolutePath());
      retval =
          DatatypeConverter.printBase64Binary(Files.readAllBytes(Paths.get(tgt.getAbsolutePath())));

      final boolean srcDeletedSuccessfully = src.delete();
      if (!srcDeletedSuccessfully) {
        LOGGER.warn("Unable to delete compressed file {}", src.getAbsolutePath());
      }

      final boolean tgtDeletedSuccessfully = tgt.delete();
      if (!tgtDeletedSuccessfully) {
        LOGGER.warn("Unable to delete doc file {}", tgt.getAbsolutePath());
      }
    } catch (Exception e) {
      errorDecompressing(e);
    }
    return retval;
  }

  private void blobSegmentsToFile(CmsDocument doc, File src) {
    try (FileOutputStream fos = new FileOutputStream(src);) {
      for (CmsDocumentBlobSegment seg : doc.getBlobSegments()) {
        final byte[] bytes = seg.getDocBlob();
        fos.write(bytes);
      }
      fos.flush();
    } catch (IOException e) {
      errorDecompressing(e);
    }
  }


  /**
   * Compress (deflate) a document into LZW-compressed by calling native library and split on blob
   * segments
   *
   * <p>
   * OPTION: Trap std::exception in shared library and return error code. The LZW library currently
   * returns a blank when decompression fails, for safety, since unhandled C++ exceptions kill the
   * JVM.
   * </p>
   *
   * <p>
   * For security reasons, remove temporary documents immediately. OPTION: pass bytes to C++ library
   * instead of file names.
   * </p>
   *
   * @param doc LZW archive to decompress
   * @return base64-encoded String of decompressed document
   */
  @SuppressFBWarnings("PATH_TRAVERSAL_IN") // There is no path traversal here
  private List<CmsDocumentBlobSegment> compressLZW(CmsDocument doc, String base64) {

    final List<CmsDocumentBlobSegment> blobs = new ArrayList<>();

    File src = null;
    File tgt = null;
    try {
      src = File.createTempFile("src", ".doc");
      src.deleteOnExit();
      tgt = File.createTempFile("tgt", ".lzw");
      tgt.deleteOnExit();
    } catch (IOException e) {
      errorCompressing(e);
    }

    try (FileOutputStream fos = new FileOutputStream(src);) {
      final byte[] bytes = DatatypeConverter.parseBase64Binary(base64.trim());
      fos.write(bytes, 0, bytes.length);
      fos.flush();
      // COMPRESS!
      final LZWEncoder lzw = new LZWEncoder();
      lzw.fileCopyCompress(src.getAbsolutePath(), tgt.getAbsolutePath());

      final byte[] compressed = Files.readAllBytes(Paths.get(tgt.getAbsolutePath()));
      final boolean srcDeletedSuccessfully = src.delete();
      if (!srcDeletedSuccessfully) {
        LOGGER.warn("Unable to delete compressed file {}", src.getAbsolutePath());
      }

      final boolean tgtDeletedSuccessfully = tgt.delete();
      if (!tgtDeletedSuccessfully) {
        LOGGER.warn("Unable to delete doc file {}", tgt.getAbsolutePath());
      }

      int i = 0;
      int segmentStart = 0;
      while (segmentStart < compressed.length) {
        final String sequence = StringUtils.leftPad(String.valueOf(++i), 4, '0');
        final int segmentLength = min(compressed.length - segmentStart, BLOB_SEGMENT_LENGTH);
        blobs.add(new CmsDocumentBlobSegment(doc.getId(), sequence,
            Arrays.copyOfRange(compressed, segmentStart, segmentStart + segmentLength)));
        segmentStart += segmentLength;
      }

      doc.setCompressionMethod(COMPRESSION_TYPE_LZW_FULL);
      doc.setDocLength((long) bytes.length);
      doc.setSegmentCount((short) i);

      final RequestExecutionContext ctx = RequestExecutionContext.instance();
      doc.setLastUpdatedTime(ctx.getRequestStartTime());
      doc.setLastUpdatedId(StringUtils.isNotBlank(ctx.getStaffId()) ? ctx.getStaffId() : "0x5");

    } catch (IOException e) {
      errorCompressing(e);
    }

    return blobs;
  }

  private void errorDecompressing(Exception e) {
    LOGGER.error("ERROR DECOMPRESSING LZW! {}", e.getMessage(), e);
    throw new ServiceException("ERROR DECOMPRESSING LZW! " + e.getMessage(), e);
  }

  private void errorCompressing(Exception e) {
    LOGGER.error("ERROR COMPRESSING LZW! {}", e.getMessage());
    throw new ServiceException("ERROR COMPRESSING LZW! " + e.getMessage(), e);
  }

}
