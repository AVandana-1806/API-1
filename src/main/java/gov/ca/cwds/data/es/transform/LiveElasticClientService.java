package gov.ca.cwds.data.es.transform;

import static gov.ca.cwds.rest.core.Api.DS_CMS;

import java.sql.Connection;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.FlushMode;
import org.hibernate.Session;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
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
  public LiveElasticClientService(ClientRelationshipDao dao) {
    this.dao = dao;
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
    final Pair<String, String> dummyRange = Pair.<String, String>of("a", "b");

    try (final Session session = dao.grabSession()) {
      final Connection con = LiveElasticJdbcHelper.prepConnection(session);
      handler.handleMainJdbc(con);
      handler.handleJdbcDone();
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "LiveElasticClientHandler FAILED! {}", e.getMessage(), e);
    }

    return new LiveElasticClientResponse("placeholder");
  }

  @Override
  public LiveElasticClientResponse create(LiveElasticClientRequest arg0) {
    throw new NotImplementedException("create not implemented");
  }

  @Override
  public LiveElasticClientResponse delete(String[] arg0) {
    throw new NotImplementedException("delete not implemented");
  }

  @Override
  public LiveElasticClientResponse update(String[] arg0, LiveElasticClientRequest arg1) {
    throw new NotImplementedException("update not implemented");
  }

}
