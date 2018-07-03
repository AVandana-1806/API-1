package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.IntakeLovEntry;
import gov.ca.cwds.rest.api.domain.IntakeLovResponse;
import gov.ca.cwds.rest.resources.SimpleResourceService;

/**
 * Business service for Intake LOV listings.
 * 
 * @author CWDS API Team
 */
public class IntakeLovService
    extends SimpleResourceService<String, IntakeLovEntry, IntakeLovResponse> implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private transient IntakeLovDao intakeLovDao;

  protected IntakeLovService() {}

  /**
   * Constructor
   * 
   * @param intakeLovDao main DAO
   */
  @Inject
  public IntakeLovService(IntakeLovDao intakeLovDao) {
    this.intakeLovDao = intakeLovDao;
  }

  @Override
  protected IntakeLovResponse handleRequest(IntakeLovEntry req) {
    return new IntakeLovResponse(
        intakeLovDao.findAll().stream().map(IntakeLovEntry::new).collect(Collectors.toList()));
  }

  @Override
  protected IntakeLovResponse handleFind(String searchForThis) {
    try {
      return handleRequest(new IntakeLovEntry());
    } catch (Exception e) {
      throw new ServiceException("ERROR handling \"find\"", e);
    }
  }

  /**
   * @return All intake LOV's
   */
  protected List<IntakeLov> loadAll() {
    return intakeLovDao.findAll();
  }

  /**
   * @param legacyCategoryId - legacyCategoryId
   * @return the intake code in the value based on the category id
   */
  protected List<IntakeLov> loadAllLegacyMetaIds(Serializable legacyCategoryId) {
    return intakeLovDao.findByLegacyMetaId(legacyCategoryId.toString());
  }

  protected IntakeLov loadLegacySystemCode(Serializable systemCodeId) {
    if (systemCodeId == null) {
      return null;
    }
    return intakeLovDao.findByLegacySystemCodeId((Number) systemCodeId);
  }

}
