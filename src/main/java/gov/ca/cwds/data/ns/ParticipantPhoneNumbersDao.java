package gov.ca.cwds.data.ns;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Participant Phone Numbers DAO
 *
 * @author Intake Team 4
 */
public class ParticipantPhoneNumbersDao extends CrudsDaoImpl<ParticipantPhoneNumbers> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ParticipantPhoneNumbersDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param participantId - participantId
   * @return the ParticipantPhoneNumbers
   */
  @SuppressWarnings("unchecked")
  public Set<ParticipantPhoneNumbers> findByParticipantId(String participantId) {
    final Query<ParticipantPhoneNumbers> query = this.grabSession()
        .getNamedQuery(ParticipantPhoneNumbers.PARTICIPANT_PHONE_NUMBERS_BY_PARTICIPANT_ID)
        .setParameter(ParticipantPhoneNumbers.PARAM_PARTICIPANT_ID, participantId);
    return new HashSet<>(query.getResultList());
  }

  @Override
  public ParticipantPhoneNumbers create(ParticipantPhoneNumbers object) {
    object.setCreatedAt(new Date());
    object.setUpdatedAt(object.getCreatedAt());
    return super.create(object);
  }

  @Override
  public ParticipantPhoneNumbers update(ParticipantPhoneNumbers object) {
    object.setUpdatedAt(new Date());
    return super.update(object);
  }

}
