package gov.ca.cwds.data.dao.investigation;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.InjuryHarmDetail;
import gov.ca.cwds.inject.CmsSessionFactory;

public class InjuryHarmDetailDao extends CrudsDaoImpl<InjuryHarmDetail> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public InjuryHarmDetailDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * finding list of Injury Harm Details
   * 
   * @param allegationId - allegation id
   * @return list of Injury Harm details
   */
  @SuppressWarnings("unchecked")
  public InjuryHarmDetail[] findInjuryHarmDetailsByAllegationId(String allegationId) {
    Query<InjuryHarmDetail> query = this.grabSession().getNamedQuery(
        "gov.ca.cwds.data.dao.investigation.InjuryHarmDetail.findInjuryHarmDetailsByAllegationId")
        .setParameter("allegationId", allegationId);
    return query.list().toArray(new InjuryHarmDetail[0]);
  }

}
