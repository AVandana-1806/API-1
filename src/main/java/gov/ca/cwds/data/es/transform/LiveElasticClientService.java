package gov.ca.cwds.data.es.transform;

import static gov.ca.cwds.rest.core.Api.DS_CMS;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.FlushMode;
import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.rest.resources.SimpleResourceService;
import gov.ca.cwds.rest.services.TypedCrudsService;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Business service for live Elasticsearch client.
 * 
 * @author CWDS API Team
 */
public class LiveElasticClientService
    extends SimpleResourceService<String[], LiveElasticClientRequest, LiveElasticClientResponse>
    implements TypedCrudsService<String[], LiveElasticClientRequest, LiveElasticClientResponse> {

  private ClientRelationshipDao dao;

  /**
   * Constructor
   */
  @Inject
  public LiveElasticClientService(ClientRelationshipDao dao, ObjectMapper mapper) {
    this.dao = dao;
    ElasticTransformer.setMapper(mapper);
  }

  @Override
  protected LiveElasticClientResponse handleRequest(LiveElasticClientRequest req) {
    return handleFind(req.getClientIds().toArray(new String[0]));
  }

  @UnitOfWork(value = DS_CMS, readOnly = true, transactional = false, flushMode = FlushMode.MANUAL)
  @Override
  protected LiveElasticClientResponse handleFind(String[] keys) {
    final SimpleCaresInterruptibleImpl interruptible = new SimpleCaresInterruptibleImpl();
    final LiveElasticClientHandler handler = new LiveElasticClientHandler(interruptible, keys);
    List<ElasticSearchPerson> results = new ArrayList<>();

    try (final Session session = dao.grabSession()) {
      final Connection con = LiveElasticJdbcHelper.prepConnection(session);
      handler.handleMain(con);
      results = handler.handleJdbcDone();
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "LIVE CLIENT HANDLER FAILED! {}", e.getMessage(), e);
    }

    return new LiveElasticClientResponse(results);
  }

  @Override
  public LiveElasticClientResponse create(LiveElasticClientRequest you) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public LiveElasticClientResponse delete(String[] found) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public LiveElasticClientResponse update(String[] secret, LiveElasticClientRequest message) {
    throw new NotImplementedException("update not implemented");
  }

}
