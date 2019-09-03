package gov.ca.cwds.rest.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.investigation.CmsRecordDescriptor;

public class CmsRecordUtilsTest {

  @Test
  public void shouldCreateCmsRecordDescriptor() throws Exception {
    String cmsId = "4qQmBZR00T";

    CmsRecordDescriptor descriptor =
        new CmsRecordDescriptor(cmsId, "0275-2355-1856-1000029", "CLIENT_T", "Client");
    CmsRecordDescriptor createdDescriptor =
        CmsRecordUtils.createLegacyDescriptor(cmsId, LegacyTable.CLIENT);
    assertThat(createdDescriptor, is(equalTo(descriptor)));
  }

  @Test
  public void shouldCreateNullWhenIdIsBlank() throws Exception {
    String cmsId = "";

    CmsRecordDescriptor createdDescriptor =
        CmsRecordUtils.createLegacyDescriptor(cmsId, LegacyTable.CLIENT);
    assertThat(createdDescriptor.getId(), is(equalTo(null)));
  }

  @Test
  public void shouldCreateNullTableNameWhenPhysicalTableNameNull() throws Exception {
    String cmsId = "4qQmBZR00T";
    CmsRecordDescriptor createdDescriptor = CmsRecordUtils.createLegacyDescriptor(cmsId, null);
    assertThat(createdDescriptor.getTableDescription(), is(equalTo(null)));
    assertThat(createdDescriptor.getTableName(), is(equalTo(null)));
  }

  @Test
  public void shouldCreateUiIdToIdWhenNotLength10() throws Exception {
    String cmsId = "4qQmBZR00";

    CmsRecordDescriptor createdDescriptor = CmsRecordUtils.createLegacyDescriptor(cmsId, null);
    assertThat(createdDescriptor.getUiId(), is(equalTo(cmsId)));
  }

}
