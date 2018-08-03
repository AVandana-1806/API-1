package gov.ca.cwds.data.ns;

import static gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.DESCRIBABLE_TYPE_ADDRESS;
import static gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Address DAO
 *
 * @author Intake Team 4
 */
public class LegacyDescriptorDao extends CrudsDaoImpl<LegacyDescriptorEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public LegacyDescriptorDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find LegacyDescriptorEntity by participantId
   *
   * @param participantId participantId
   * @return LegacyDescriptorEntity
   */
  public LegacyDescriptorEntity findParticipantLegacyDescriptor(String participantId) {
    return findLegacyDescriptor(participantId, DESCRIBABLE_TYPE_PARTICIPANT);
  }

  /**
   * Find LegacyDescriptorEntity by participantId
   *
   * @param addressId address id
   * @return LegacyDescriptorEntity
   */
  public LegacyDescriptorEntity findAddressLegacyDescriptor(String addressId) {
    return findLegacyDescriptor(addressId, DESCRIBABLE_TYPE_ADDRESS);
  }

  /**
   * Find all Legacy Descriptor Entities for given participant Id-s
   *
   * @param participantIds participant Id-s
   * @return map where key is a participant Id and value is a LegacyDescriptorEntity
   */
  public Map<String, LegacyDescriptorEntity> findParticipantLegacyDescriptors(
      Set<String> participantIds) {
    return findLegacyDescriptors(participantIds, DESCRIBABLE_TYPE_PARTICIPANT);
  }

  private LegacyDescriptorEntity findLegacyDescriptor(String describableId,
      String describableType) {
    if (describableId == null) {
      return null;
    }
    @SuppressWarnings("unchecked")
    final Query<LegacyDescriptorEntity> query =
        this.grabSession().getNamedQuery(LegacyDescriptorEntity.FIND_BY_DESCRIBABLE_ID_AND_TYPE)
            .setParameter("describableId", Long.valueOf(describableId))
            .setParameter("describableType", describableType);
    List<LegacyDescriptorEntity> entityList = query.getResultList();
    return entityList.isEmpty() ? null : entityList.get(0);
  }

  private Map<String, LegacyDescriptorEntity> findLegacyDescriptors(Set<String> describableIds,
      String describableType) {
    if (describableIds == null || describableIds.isEmpty()) {
      return new HashMap<>();
    }
    Set<Long> longDescribableIds =
        describableIds.stream().map(Long::valueOf).collect(Collectors.toSet());
    @SuppressWarnings("unchecked")
    final Query<LegacyDescriptorEntity> query =
        this.grabSession().getNamedQuery(LegacyDescriptorEntity.FIND_BY_DESCRIBABLE_IDS_AND_TYPE)
            .setParameter("describableIds", longDescribableIds)
            .setParameter("describableType", describableType);
    return query.list().stream()
        .collect(Collectors.toMap(d -> String.valueOf(d.getDescribableId().longValue()), d -> d));
  }
}
