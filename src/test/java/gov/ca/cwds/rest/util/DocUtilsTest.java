package gov.ca.cwds.rest.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Date;

import org.junit.Test;

public class DocUtilsTest {

  @Test
  public void shouldGenerateDocHandle() throws Exception {
    Date docDate = new Date();
    String docAuth = "ABCDEFGH";

    String dochandle = DocUtils.generateDocHandle(docDate, docAuth);
    assertThat(dochandle, is(not(equalTo(null))));
  }

}
