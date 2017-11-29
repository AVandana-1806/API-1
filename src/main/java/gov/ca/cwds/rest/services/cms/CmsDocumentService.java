package gov.ca.cwds.rest.services.cms;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.rest.api.domain.cms.CmsDocument;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link CmsDocument}.
 * 
 * @author CWDS API Team
 */
public class CmsDocumentService implements TypedCrudsService<String, CmsDocument, CmsDocument> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsDocumentService.class);

  private CmsDocumentDao dao;

  /**
   * Constructor.
   * 
   * @param dao The {@link Dao} handling {@link CmsDocumentDao} objects.
   */
  @Inject
  public CmsDocumentService(CmsDocumentDao dao) {
    this.dao = dao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public CmsDocument find(String primaryKey) {
    LOGGER.debug("primaryKey={}", primaryKey);
    CmsDocument retval = null;
    String base64Doc;

    gov.ca.cwds.data.persistence.cms.CmsDocument doc = dao.find(primaryKey);
    if (doc != null) {
      doc.setCompressionMethod(
          doc.getCompressionMethod() != null ? doc.getCompressionMethod().trim() : "");
      doc.setDocAuth(doc.getDocAuth() != null ? doc.getDocAuth().trim() : "");
      doc.setDocName(doc.getDocName() != null ? doc.getDocName().trim() : "");
      doc.setDocServ(doc.getDocServ() != null ? doc.getDocServ().trim() : "");

      base64Doc = dao.decompressDoc(doc);
      retval = new CmsDocument(doc);
      retval.setBase64Blob(base64Doc);
    } else {
      LOGGER.warn("EMPTY document!");
    }

    return retval;
  }

  /**
   * Update binary document.
   * 
   * @param primaryKey primary key
   * @param request domain document
   */
  @Override
  public CmsDocument update(String primaryKey, CmsDocument request) {
    LOGGER.debug("primaryKey={}", primaryKey);

    gov.ca.cwds.data.persistence.cms.CmsDocument doc = dao.find(primaryKey);
    if (doc != null) {
      if (StringUtils.isNotBlank(request.getDocAuth())) {
        doc.setDocAuth(request.getDocAuth().trim());
      }
      if (StringUtils.isNotBlank(request.getDocName())) {
        doc.setDocName(request.getDocName().trim());
      }
      if (StringUtils.isNotBlank(request.getDocServ())) {
        doc.setDocServ(request.getDocServ().trim());
      }

      doc = dao.compressPK(doc, request.getBase64Blob().trim());
      gov.ca.cwds.data.persistence.cms.CmsDocument managed =
          new gov.ca.cwds.data.persistence.cms.CmsDocument(doc);
      try {
        dao.update(managed);
      } catch (Exception e) {
        LOGGER.error("HUH??? {}", e.getMessage(), e);
      }

      request.setCompressionMethod(managed.getCompressionMethod());
      request.setDocAuth(managed.getDocAuth());
      request.setDocName(managed.getDocName());
      request.setDocLength(managed.getDocLength());
      request.setSegmentCount(managed.getSegmentCount());
    } else {
      LOGGER.warn("EMPTY document! {}", primaryKey);
    }

    return request;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public CmsDocument delete(String primaryKey) {
    throw new NotImplementedException("DELETE NOT IMPLEMENTED!");
  }

  /**
   * <p>
   * <strong>NOT YET IMPLEMENTED!</strong>
   * </p>
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public CmsDocument create(CmsDocument request) {
    throw new NotImplementedException("CREATE NOT IMPLEMENTED!");
  }

}
