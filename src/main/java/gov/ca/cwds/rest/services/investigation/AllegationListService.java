package gov.ca.cwds.rest.services.investigation;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.dao.investigation.AllegationsDao;
import gov.ca.cwds.fixture.investigation.AllegationListEntityBuilder;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.AllegationList;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on Investigation People
 * 
 * @author CWDS API Team
 */
public class AllegationListService implements TypedCrudsService<String, AllegationList, Response> {

  private AllegationsDao allegationDao;
  private AllegationList validAllegations = new AllegationListEntityBuilder().build();

  /**
   * @param allegationDao {@link Dao} handling
   *        {@link gov.ca.cwds.rest.api.domain.investigation.Allegation} objects
   */
  @Inject
  public AllegationListService(AllegationsDao allegationDao) {
    super();
    this.allegationDao = allegationDao;
  }

  @Override
  public Response find(String primaryKey) {
    return validAllegations;
  }

  @Override
  public Response create(AllegationList request) {
    return validAllegations;
  }

  @Override
  public Response delete(String primaryKey) {
    return validAllegations;
  }

  @Override
  public Response update(String primaryKey, AllegationList request) {
    return validAllegations;
  }
}