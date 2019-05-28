package gov.ca.cwds.rest.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS API Team
 */
public class DocUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocUtils.class);
  private static final String TEMPLATE_LOCATION = "cms/drmstemplates";
  private static final String DATE_FORMAT = "yyyyMMddHHmmssSSS";
  private static final String SPACE8 = "        ";

  private DocUtils() {
    // static methods only
  }

  public static byte[] createFromTemplateUseBookmarks(byte[] template,
      Map<String, String> keyValuePairs) {
    try (HWPFDocument document = new HWPFDocument(new ByteArrayInputStream(template))) {
      final Bookmarks bookmarks = document.getBookmarks();
      for (int i = 0; i < bookmarks.getBookmarksCount(); i++) {
        final Bookmark bookmark = bookmarks.getBookmark(i);
        final String value = keyValuePairs.get(bookmark.getName());
        if (value != null) {
          final Range range = new Range(bookmark.getStart(), bookmark.getEnd(), document);
          // Bookmark text should contain bookmark name in square brackets. Problems replacing text
          // in empty bookmarks
          range.replaceText("[" + bookmark.getName() + "]", value);
        }
      }

      final ByteArrayOutputStream out = new ByteArrayOutputStream(template.length);
      document.write(out);
      return out.toByteArray();
    } catch (Exception e) {
      LOGGER.warn("ERROR PROCESSING TEMPLATE: ", e);
      return template;
    }
  }

  public static String generateDocHandle(Date date, String docAuth) {
    final SecureRandom random = new SecureRandom();
    final String ds = (new SimpleDateFormat(DATE_FORMAT)).format(date);

    // TODO: find the next available document sequence number instead of randomly generating one.
    return ds.substring(14, 16) // hundreds of seconds
        .concat(ds.substring(12, 14)) // seconds
        .concat(ds.substring(10, 12)) // minutes
        .concat(ds.substring(8, 10)) // hour (24 hr format)
        .concat(ds.substring(6, 8)) // Day
        .concat(ds.substring(4, 6)) // Month
        .concat(ds.substring(2, 4)) // Year year
        .concat(ds.substring(0, 2)) // Year century
        .concat("*").concat(docAuth.concat(SPACE8).substring(0, 8)) // User Id (8 characters)
        .concat(StringUtils.leftPad(String.valueOf(random.nextInt(99999)), 5, "0"));
  }

  public static String loadTemplateBase64(String docName) {
    try {
      return DatatypeConverter.printBase64Binary(IOUtils.toByteArray(
          DocUtils.class.getClassLoader().getResourceAsStream(TEMPLATE_LOCATION + "/" + docName)));
    } catch (Exception e) {
      LOGGER.error("ERROR LOADING TEMPLATE {} FROM RESOURCES: {}", docName, e);
      return "";
    }
  }

}
