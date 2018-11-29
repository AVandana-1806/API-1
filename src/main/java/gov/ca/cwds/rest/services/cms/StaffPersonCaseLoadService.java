package gov.ca.cwds.rest.services.cms;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.StaffPersonCaseLoadDao;
import gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link StaffPersonCaseLoad}
 * 
 * @author CWDS API Team
 */
public class StaffPersonCaseLoadService
    implements TypedCrudsService<String, StaffPersonCaseLoad, StaffPersonCaseLoad> {

  private StaffPersonCaseLoadDao staffPersonCaseLoadDao;

  /**
   * 
   * @param staffPersonCaseLoadDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad} objects.
   */
  @Inject
  public StaffPersonCaseLoadService(StaffPersonCaseLoadDao staffPersonCaseLoadDao) {
    this.staffPersonCaseLoadDao = staffPersonCaseLoadDao;
  }

  @Override
  public StaffPersonCaseLoad create(StaffPersonCaseLoad primaryKey) {
    throw new NotImplementedException("Create is not implemented");
  }

  @Override
  public StaffPersonCaseLoad delete(String primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public StaffPersonCaseLoad find(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad persistedStaffPersonCaseLoad =
        staffPersonCaseLoadDao.find(primaryKey);
    if (persistedStaffPersonCaseLoad != null) {
      return new gov.ca.cwds.rest.api.domain.cms.StaffPersonCaseLoad(persistedStaffPersonCaseLoad);
    }
    return null;
  }

  @Override
  public StaffPersonCaseLoad update(String primaryKey, StaffPersonCaseLoad request) {
    throw new NotImplementedException("Update is not implemented");
  }

}
