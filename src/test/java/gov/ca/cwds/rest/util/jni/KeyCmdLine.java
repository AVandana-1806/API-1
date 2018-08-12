package gov.ca.cwds.rest.util.jni;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.util.jni.KeyJNI.KeyDetail;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Command line tool to call the Java CWDS key generation library.
 * 
 * <h3>Command line arguments:</h3>
 * <h4>Compose/generate a key:</h4> <blockquote>
 * 
 * <pre>
 * {@code java -cp bin gov.ca.cwds.rest.util.jni.KeyCmdLine -c 0X5}.
 * </pre>
 * 
 * </blockquote>
 * 
 * <h4>Decompose a key:</h4> <blockquote>
 * 
 * <pre>
 * {@code java -cp bin gov.ca.cwds.rest.util.jni.KeyCmdLine -d OpHh4Kr0X5}.
 * </pre>
 * 
 * </blockquote>
 * 
 * <h4>JVM arguments (both cases):</h4> <blockquote>
 * 
 * <pre>
 * {@code -ea -Dcwds.jni.force=Y -Djava.library.path=.:lib/}.
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 */
public class KeyCmdLine {

  /**
   * Decode a key into its constituent parts, timestamp and staff id.
   * 
   * @param key key to decode.
   */
  protected void decode(String key) {
    final KeyDetail kd = new KeyDetail();
    new KeyJNI().decomposeKey(key, kd);
    System.out.println("Java:\nkey=" + kd.key + "\nstaffId=" + kd.staffId + "\nUITimestamp="
        + kd.UITimestamp + "\nPTimestamp=" + kd.PTimestamp);
  }

  protected void encode(String staffId, String numKeysToMake, String fileNm)
      throws FileNotFoundException {
    final KeyJNI jni = new KeyJNI();
    final int iterations = Integer.parseInt(numKeysToMake);

    try (final PrintStream out = StringUtils.isBlank(fileNm) ? System.out
        : new PrintStream(new BufferedOutputStream(new FileOutputStream(fileNm.trim())))) {
      for (int i = 0; i < iterations; i++) {
        final String key = jni.generateKey(staffId);
        out.println(key);
      }
    }
  }

  /**
   * Main method. See class javadoc for details.
   * 
   * @param args command line
   */
  public static void main(String[] args) throws Exception {
    final OptionParser parser = new OptionParser("dcf::s:i:k:");
    final OptionSet options = parser.parse(args);

    final String staffId = options.has("s") ? (String) options.valueOf("s") : "0x5";
    final String numKeysToMake = options.has("i") ? (String) options.valueOf("i") : "1";
    final String fileNm = options.has("f") ? (String) options.valueOf("f") : null;
    final String key = options.has("k") ? (String) options.valueOf("k") : null;

    final boolean decompose = options.has("d");

    if (decompose) { // Decompose key.
      new KeyCmdLine().decode(key);
    } else { // Compose key.
      new KeyCmdLine().encode(staffId, numKeysToMake, fileNm);
    }
  }

}
