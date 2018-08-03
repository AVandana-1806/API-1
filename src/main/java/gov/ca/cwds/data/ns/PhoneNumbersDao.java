package gov.ca.cwds.data.ns;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * PhoneNumber DAO
 *
 * @author Intake Team 4
 */
public class PhoneNumbersDao extends CrudsDaoImpl<PhoneNumbers> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public PhoneNumbersDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param participantId - participantId
   * @return the PhoneNumbers
   */
  public List<PhoneNumbers> findByParticipant(String participantId) {
    final Query<PhoneNumbers> query =
        this.grabSession().getNamedQuery(PhoneNumbers.FIND_BY_PARTICIPANT_ID)
            .setParameter(PhoneNumbers.PARAM_PARTICIPANT_ID, participantId);
    return query.getResultList();
  }
}
