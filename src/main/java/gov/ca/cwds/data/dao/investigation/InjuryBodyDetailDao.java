package gov.ca.cwds.data.dao.investigation;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.InjuryBodyDetail;
import gov.ca.cwds.inject.CmsSessionFactory;

public class InjuryBodyDetailDao extends CrudsDaoImpl<InjuryBodyDetail> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public InjuryBodyDetailDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * finding list of Injury Harm Details
   * 
   * @param secondaryInjuryHarmDetailId - secondaryInjuryHarmDetailId
   * @return list of Injury Harm details
   */
  @SuppressWarnings("unchecked")
  public InjuryBodyDetail[] findInjuryBodyDetailsByInjuryHarmDetailId(
      String secondaryInjuryHarmDetailId) {
    Query<InjuryBodyDetail> query = this.grabSession().getNamedQuery(
        "gov.ca.cwds.data.dao.investigation.InjuryBodyDetail.findInjuryBodyDetailsByInjuryHarmDetailId")
        .setParameter("secondaryInjuryHarmDetailId", secondaryInjuryHarmDetailId);
    return query.list().toArray(new InjuryBodyDetail[0]);
  }


  /**
   * finding list of Injury Harm Details
   * 
   * @param allegationId - allegation id
   * @return list of Injury Harm details
   */
  @SuppressWarnings("unchecked")
  public InjuryBodyDetail[] findInjuryBodyDetailsByAllegationId(String allegationId) {
    Query<InjuryBodyDetail> query = this.grabSession().getNamedQuery(
        "gov.ca.cwds.data.dao.investigation.InjuryBodyDetail.findInjuryBodyDetailsByAllegationId")
        .setParameter("allegationId", allegationId);
    return query.list().toArray(new InjuryBodyDetail[0]);
  }

}
