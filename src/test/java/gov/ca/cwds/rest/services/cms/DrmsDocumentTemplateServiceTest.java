package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.DrmsDocumentTemplateDao;
import gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class DrmsDocumentTemplateServiceTest extends Doofenshmirtz<DrmsDocumentTemplate> {

  DrmsDocumentTemplateDao drmsDocumentTemplateDao;
  CmsDocumentService cmsDocumentService;
  DrmsDocumentTemplateService target;
  Query<DrmsDocumentTemplate> q;

  DrmsDocumentTemplate doc;
  DrmsDocumentTemplate[] docs;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    drmsDocumentTemplateDao = mock(DrmsDocumentTemplateDao.class);
    cmsDocumentService = mock(CmsDocumentService.class);
    target = new DrmsDocumentTemplateService(drmsDocumentTemplateDao, cmsDocumentService);
    q = queryInator();

    doc = new DrmsDocumentTemplate(DEFAULT_CLIENT_ID, (short) 2205, "wx_", (short) 1095, "cscpfam ",
        "N", (short) 1253, new Date(), "CARES Cares!", "L");
    docs = new DrmsDocumentTemplate[1];
    docs[0] = doc;

    when(drmsDocumentTemplateDao.findByApplicationContextAndGovernmentEntity(any(Short.class),
        any(Short.class))).thenReturn(docs);
    when(drmsDocumentTemplateDao.find(any(DrmsDocumentTemplate.class))).thenReturn(doc);
    when(drmsDocumentTemplateDao.find(any(String.class))).thenReturn(doc);
    when(drmsDocumentTemplateDao
        .create(any(gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate.class))).thenReturn(doc);
    when(drmsDocumentTemplateDao.getSessionFactory()).thenReturn(sessionFactoryImplementor);
    when(cmsDocumentService.create(any())).thenReturn(readJsonDoc());
  }

  protected gov.ca.cwds.rest.api.domain.cms.CmsDocument readJsonDoc() throws Exception {
    return MAPPER.readValue(fixture("fixtures/domain/cms/CmsDocument/valid/valid.json"),
        gov.ca.cwds.rest.api.domain.cms.CmsDocument.class);
  }

  @Test
  public void type() throws Exception {
    assertThat(DrmsDocumentTemplateService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findScreenerNarrativeTemplateNs_A$Short() throws Exception {
    Short govermentEntity = (short) 10;
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate actual =
        target.findScreenerNarrativeTemplateNs(govermentEntity);
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = NotImplementedException.class)
  public void find_A$String() throws Exception {
    String s = DEFAULT_CLIENT_ID;
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate actual = target.find(s);
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = NotImplementedException.class)
  public void delete_A$String() throws Exception {
    String s = null;
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate actual = target.delete(s);
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void create_A$DrmsDocumentTemplate() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate request =
        mock(gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate.class);
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate actual = target.create(request);
    // gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate expected = null;
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = NotImplementedException.class)
  public void update_A$String$DrmsDocumentTemplate() throws Exception {
    String s = DEFAULT_CLIENT_ID;
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate request =
        mock(gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate.class);
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate actual = target.update(s, request);
    gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
