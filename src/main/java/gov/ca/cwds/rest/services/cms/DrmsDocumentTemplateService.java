package gov.ca.cwds.rest.services.cms;

import static gov.ca.cwds.data.cms.CmsDocumentDao.COMPRESSION_TYPE_PK_FULL;
import static gov.ca.cwds.data.cms.DrmsDocumentTemplateDao.APPLICATION_CONTEXT_OTHER;
import static gov.ca.cwds.data.cms.DrmsDocumentTemplateDao.GOVERMENT_ENTITY_SYSTEM;
import static gov.ca.cwds.data.cms.DrmsDocumentTemplateDao.LANGUAGE_ENGLISH;
import static gov.ca.cwds.data.cms.DrmsDocumentTemplateDao.SCREENERNARRATIVE_NS;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityExistsException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.DrmsDocumentTemplateDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.util.DocUtils;

/**
 * Business layer object to work on {@link DrmsDocumentTemplate}.
 *
 * @author CWDS API Team
 */
public class DrmsDocumentTemplateService
    implements TypedCrudsService<String, DrmsDocumentTemplate, DrmsDocumentTemplate> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DrmsDocumentTemplateService.class);

  public static final String TEMPLATE_DOC_NAME_SCREENER_NARRATIVE_NS = "INALG_NS.DOC";

  private DrmsDocumentTemplateDao drmsDocumentTemplateDao;
  private CmsDocumentService cmsDocumentService;

  /**
   * @param drmsDocumentTemplateDao - drmsDocumentTemplateDao
   * @param cmsDocumentService - cmsDocumentService
   */
  @Inject
  public DrmsDocumentTemplateService(DrmsDocumentTemplateDao drmsDocumentTemplateDao,
      CmsDocumentService cmsDocumentService) {
    this.drmsDocumentTemplateDao = drmsDocumentTemplateDao;
    this.cmsDocumentService = cmsDocumentService;
  }

  /**
   * @param governmentEntity - government entity
   * @return the document template
   */
  public DrmsDocumentTemplate findScreenerNarrativeTemplateNs(Short governmentEntity) {
    gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate template = null;
    gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate[] templates = drmsDocumentTemplateDao
        .findByApplicationContextAndGovernmentEntity(APPLICATION_CONTEXT_OTHER, governmentEntity);
    for (gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate doc : templates) {
      // TO1DO For now can't use DOT files as templates with POI. So only _NS files are considered,
      // which will be a DOC files
      if (SCREENERNARRATIVE_NS.equals(doc.getTitleName().trim())
          && doc.getDocumentDOSFilePrefixName().toUpperCase().endsWith("_NS")) {
        template = doc;
        if ((GOVERMENT_ENTITY_SYSTEM.equals(governmentEntity)
            && GOVERMENT_ENTITY_SYSTEM.equals(doc.getGovermentEntityType()))
            || (!GOVERMENT_ENTITY_SYSTEM.equals(governmentEntity)
                && !GOVERMENT_ENTITY_SYSTEM.equals(doc.getGovermentEntityType()))) {
          // Asked for system and found system or asked for not system and found not system use it
          // right away. If both exists then not system takes priority
          break;
        }
      }
    }

    if (template == null) {
      LOGGER.warn("NO (_NS) SCREENER NARRATIVE TEMPLATE FOUND. CREATING ONE.");
      final String base64blob =
          DocUtils.loadTemplateBase64(TEMPLATE_DOC_NAME_SCREENER_NARRATIVE_NS);
      if (base64blob.isEmpty()) {
        LOGGER.warn("CAN'T LOAD (_NS) SCREENER NARRATIVE TEMPLATE.");
        return null;
      }

      final Date now = new Date();
      final String docAuth = "CWDS_NS";
      final String docServ = "AUTOCRTD";
      final String docDate = new SimpleDateFormat(DomainObject.DATE_FORMAT).format(now);
      final String docTime = new SimpleDateFormat(DomainObject.TIME_FORMAT).format(now);
      final Short segments = 1;
      final Long docLength = 1L;

      CmsDocument cmsDocument = new CmsDocument(DocUtils.generateDocHandle(now, docAuth), segments,
          docLength, docAuth, docServ, docDate, docTime, TEMPLATE_DOC_NAME_SCREENER_NARRATIVE_NS,
          COMPRESSION_TYPE_PK_FULL, base64blob);
      cmsDocument = cmsDocumentService.create(cmsDocument);

      template = new gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
          APPLICATION_CONTEXT_OTHER,
          FilenameUtils.getBaseName(TEMPLATE_DOC_NAME_SCREENER_NARRATIVE_NS),
          GOVERMENT_ENTITY_SYSTEM, cmsDocument.getId(), "N", LANGUAGE_ENGLISH, now,
          SCREENERNARRATIVE_NS, "N");

      template = drmsDocumentTemplateDao.create(template);
    }

    return new DrmsDocumentTemplate(template);
  }

  @Override
  public DrmsDocumentTemplate find(String s) {
    throw new NotImplementedException("FIND NOT IMPLEMENTED");
  }

  @Override
  public DrmsDocumentTemplate delete(String s) {
    throw new NotImplementedException("DELETE NOT IMPLEMENTED");
  }

  @Override
  public DrmsDocumentTemplate create(DrmsDocumentTemplate request) {
    DrmsDocumentTemplate drmsDocumentTemplate = request;

    try {
      gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate managed =
          new gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate(drmsDocumentTemplate,
              RequestExecutionContext.instance().getRequestStartTime());
      managed = drmsDocumentTemplateDao.create(managed);
      return new DrmsDocumentTemplate(managed);
    } catch (EntityExistsException e) {
      LOGGER.info("drmsDocumentTemplate already exists : {}", drmsDocumentTemplate);
      throw new ServiceException(e);
    }
  }

  @Override
  public DrmsDocumentTemplate update(String s, DrmsDocumentTemplate request) {
    throw new NotImplementedException("UPDATE NOT IMPLEMENTED");
  }

}
