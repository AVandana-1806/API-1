package gov.ca.cwds.rest.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePerson;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonResponse;
import gov.ca.cwds.rest.api.domain.es.ESPerson;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;
import gov.ca.cwds.rest.resources.SimpleResourceService;


/**
 * Business service for Intake Person Auto-complete.
 * 
 * @author CWDS API Team
 */
public class AutoCompletePersonService
    extends SimpleResourceService<String, AutoCompletePersonRequest, AutoCompletePersonResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AutoCompletePersonService.class);

  private ElasticsearchDao elasticsearchDao;

  /**
   * Constructor
   * 
   * @param elasticsearchDao the ElasticSearch DAO
   */
  @Inject
  public AutoCompletePersonService(ElasticsearchDao elasticsearchDao) {
    this.elasticsearchDao = elasticsearchDao;
  }

  @Override
  protected AutoCompletePersonResponse handleRequest(AutoCompletePersonRequest req) {

    final ElasticSearchPerson[] hits =
        this.elasticsearchDao.autoCompletePerson(req.getSearchCriteria());

    List<AutoCompletePerson> list = new ArrayList<>(hits.length);
    if (hits.length > 0) {
      // Convert ElasticSearchPerson to AutoCompletePerson.
      for (ElasticSearchPerson hit : hits) {
        LOGGER.info(hit.toString());
        list.add(new AutoCompletePerson(hit));
      }

    } else {
      LOGGER.debug("No records found");
    }

    return new AutoCompletePersonResponse(list.toArray(new AutoCompletePerson[0]));
  }

  /**
   * Search on the first non-null, non-whitespace term.
   * 
   * @param firstName first name to search, if any
   * @param lastName last name to search, if any
   * @param birthDate birth date to search, if any
   * @return array of matching {@link ESPerson}
   * @throws Exception due to I/O error, unknown host, etc.
   */
  public ESPerson[] queryPersonOr(String firstName, String lastName, String birthDate)
      throws Exception {

    // Parse inputs.
    ESSearchRequest req = new ESSearchRequest();
    String field = "";
    String value = "";
    if (!StringUtils.isBlank(firstName)) {
      field = ESPerson.ESColumn.FIRST_NAME.getCol();
      value = firstName;
    } else if (!StringUtils.isBlank(lastName)) {
      field = ESPerson.ESColumn.LAST_NAME.getCol();
      value = lastName;
    } else if (!StringUtils.isBlank(birthDate)) {
      field = ESPerson.ESColumn.BIRTH_DATE.getCol();
      value = birthDate;
    }

    // Build search request.
    req.getRoot().addElem(new ESSearchRequest.ESFieldSearchEntry(field, value));

    // Search.
    final SearchHit[] hits = this.elasticsearchDao.queryPersonOr(req);
    final ESPerson[] ret = new ESPerson[hits.length];

    // Prep results.
    int ctr = -1;
    for (SearchHit hit : hits) {
      ret[++ctr] = ESPerson.makeESPerson(hit);
    }

    return ret;
  }

  @Override
  protected AutoCompletePersonResponse handleFind(String arg0) {
    // No-op for now.
    return null;
  }

}
