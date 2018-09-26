package gov.ca.cwds.data.ns;

import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.DELETE_PARTICIPANTS_BY_RELATED_SCREENING_ID;
import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_BY_RELATED_SCREENING_ID_AND_PARTICIPANT_ID;
import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_BY_SCREENING_ID_AND_LEGACY_ID;
import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_PARTICIPANTS_BY_RELATED_SCREENING_ID;
import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_PARTICIPANTS_BY_SCREENING_IDS;
import static gov.ca.cwds.data.persistence.ns.ParticipantEntity.FIND_PARTICIPANT_BY_RELATED_SCREENING_ID_AND_LEGACY_ID;
import static gov.ca.cwds.data.persistence.xa.CaresQueryAccelerator.readOnlyQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.rest.core.Api.PathParam;

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
   * Find Legacy Id's by screeningId
   *
   * @param screeningId screeningId
   * @return Set of Legacy Id's
   */
  public Set<String> findLegacyIdListByScreeningId(String screeningId) {
    @SuppressWarnings("unchecked") final Query<String> query =
        grabSession().getNamedQuery(ParticipantEntity.FIND_LEGACY_ID_LIST_BY_SCREENING_ID)
            .setParameter(PathParam.SCREENING_ID, screeningId);
    readOnlyQuery(query);
    return new HashSet<>(query.list());
  }

  /**
   * @param screeningIds Set of Screening ID's
   * @return map where key is a Screening ID and value is a Set of Participant Entities bound to the
   * screening
   */
  public Map<String, Set<ParticipantEntity>> findByScreeningIds(Set<String> screeningIds) {
    if (screeningIds == null || screeningIds.isEmpty()) {
      return new HashMap<>();
    }
    @SuppressWarnings("unchecked") final Query<ParticipantEntity> query =
        grabSession().getNamedQuery(FIND_PARTICIPANTS_BY_SCREENING_IDS).setParameter("screeningIds",
            screeningIds);
    readOnlyQuery(query);

    final Map<String, Set<ParticipantEntity>> result = new HashMap<>(screeningIds.size());
    for (ParticipantEntity participantEntity : query.list()) {
      final String screeningId = participantEntity.getScreeningId();
      if (!result.containsKey(screeningId)) {
        result.put(screeningId, new HashSet<>());
      }
      result.get(screeningId).add(participantEntity);
    }
    return result;
  }

  public List<ParticipantEntity> getByScreeningId(String screeningId) {
    @SuppressWarnings("unchecked") final Query<ParticipantEntity> query =
        grabSession().getNamedQuery(constructNamedQueryName("findByScreeningId"))
            .setParameter(PathParam.SCREENING_ID, screeningId);
    readOnlyQuery(query);
    return query.list();
  }

  public List<ParticipantEntity> findByIds(Set<String> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return new ArrayList<>();
    }

    final Query<ParticipantEntity> query =
        grabSession().getNamedQuery(constructNamedQueryName("findByParticipantsId"))
            .setParameter(PathParam.PARTICIPANT_IDS, ids);
    readOnlyQuery(query);
    return query.list();
  }

  public ParticipantEntity findByScreeningIdAndParticipantId(String screeningId,
      String participantId) {
    @SuppressWarnings("unchecked") final Query<ParticipantEntity> query = this.grabSession()
        .getNamedQuery(constructNamedQueryName("findByScreeningIdAndParticipantId"))
        .setParameter(PathParam.SCREENING_ID, screeningId)
        .setParameter(PathParam.PARTICIPANT_ID, participantId);
    readOnlyQuery(query);
    return query.uniqueResult();
  }

  public ParticipantEntity findByRelatedScreeningAndParticipantId(String screeningId,
      String participantId) {
    @SuppressWarnings("unchecked") final Query<ParticipantEntity> query = this.grabSession()
        .getNamedQuery(FIND_BY_RELATED_SCREENING_ID_AND_PARTICIPANT_ID)
        .setParameter(PathParam.RELATED_SCREENING_ID, screeningId)
        .setParameter(PathParam.PARTICIPANT_ID, participantId);
    readOnlyQuery(query);
    return query.uniqueResult();
  }

  public ParticipantEntity findByScreeningIdAndLegacyId(String screeningId, String legacyId) {
    @SuppressWarnings("unchecked") final Query<ParticipantEntity> query =
        this.grabSession().getNamedQuery(FIND_BY_SCREENING_ID_AND_LEGACY_ID)
            .setParameter(PathParam.SCREENING_ID, screeningId)
            .setParameter(PathParam.LEGACY_ID, legacyId);
    readOnlyQuery(query);
    return query.uniqueResult();
  }

  /**
   * @param screeningId screening id
   * @param legacyId client legacy identifier
   * @return Participant that has been created in scope of screening (related_screening_id) but has
   * not been attached
   */
  public ParticipantEntity findByRelatedScreeningIdAndLegacyId(String screeningId,
      String legacyId) {
    @SuppressWarnings("unchecked") final Query<ParticipantEntity> query = this.grabSession()
        .getNamedQuery(FIND_PARTICIPANT_BY_RELATED_SCREENING_ID_AND_LEGACY_ID)
        .setParameter(PathParam.RELATED_SCREENING_ID, screeningId)
        .setParameter(PathParam.LEGACY_ID, legacyId);
    readOnlyQuery(query);
    return query.uniqueResult();
  }

  /**
   *
   * @param relatedScreeningId screening id where Participant has been added
   * @return Participant that has been created in scope of screening (related_screening_id)
   */
  public Set<ParticipantEntity> findByRelatedScreeningId(String relatedScreeningId) {
    if (StringUtils.isEmpty(relatedScreeningId)) {
      return new HashSet<>();
    }

    @SuppressWarnings("unchecked") final Query<ParticipantEntity> query =
        grabSession().getNamedQuery(FIND_PARTICIPANTS_BY_RELATED_SCREENING_ID)
            .setParameter(PathParam.RELATED_SCREENING_ID, relatedScreeningId);
    readOnlyQuery(query);
    return Collections.unmodifiableSet(new HashSet<>(query.list()));
  }

  /**
   * Delete all participants where screening_id is null
   * @param relatedScreeningId relatedScreeningId
   */
  public void deleteParticipantsByRelatedScreningIdWithNullableScreeningId(String relatedScreeningId) {
    if (StringUtils.isEmpty(relatedScreeningId)) {
      return;
    }

    @SuppressWarnings("unchecked") final Query<ParticipantEntity> query =
        grabSession().getNamedQuery(DELETE_PARTICIPANTS_BY_RELATED_SCREENING_ID)
            .setParameter(PathParam.RELATED_SCREENING_ID, relatedScreeningId);
    query.executeUpdate();
  }

}
