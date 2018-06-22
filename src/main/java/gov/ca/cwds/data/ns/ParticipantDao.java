package gov.ca.cwds.data.ns;

import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_PARTICIPANTS_BY_SCREENING_IDS;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.rest.core.Api.PathParam;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Participant DAO in PostgreSQL.
 *
 * @author CWDS API Team
 */
public class ParticipantDao extends BaseDaoImpl<ParticipantEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ParticipantDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find Legacy Id-s by screeningId
   *
   * @param screeningId screeningId
   * @return Set of Legacy Id-s
   */
  public Set<String> findLegacyIdListByScreeningId(String screeningId) {
    @SuppressWarnings("unchecked")
    final Query<String> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(ParticipantEntity.FIND_LEGACY_ID_LIST_BY_SCREENING_ID)
        .setParameter(PathParam.SCREENING_ID, screeningId);
    return new HashSet<>(query.list());
  }

  /**
   * @param screeningIds Set of Screening ID-s
   * @return map where key is a Screening ID and value is a Set of Participant Entities bound to the
   * screening
   */
  public Map<String, Set<ParticipantEntity>> findByScreeningIds(Set<String> screeningIds) {
    if (screeningIds == null || screeningIds.isEmpty()) {
      return new HashMap<>();
    }
    @SuppressWarnings("unchecked")
    final Query<ParticipantEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(FIND_PARTICIPANTS_BY_SCREENING_IDS)
        .setParameter("screeningIds", screeningIds);
    Map<String, Set<ParticipantEntity>> result = new HashMap<>(screeningIds.size());
    for (ParticipantEntity participantEntity : query.list()) {
      String screeningId = participantEntity.getScreeningId();
      if (!result.containsKey(screeningId)) {
        result.put(screeningId, new HashSet<>());
      }
      result.get(screeningId).add(participantEntity);
    }
    return result;
  }

  public List<ParticipantEntity> getByScreeningId(String screeningId) {
    @SuppressWarnings("unchecked")
    final Query<ParticipantEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findByScreeningId"))
        .setParameter(PathParam.SCREENING_ID, screeningId);
    return query.list();
  }

  public ParticipantEntity findByScreeningIdAndParticipantId(String screeningId, String participantId) {
    @SuppressWarnings("unchecked")
    final Query<ParticipantEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findByScreeningIdAndParticipantId"))
        .setParameter(PathParam.SCREENING_ID, screeningId)
        .setParameter(PathParam.PARTICIPANT_ID, participantId);
    return query.uniqueResult();
  }
}
