package gov.ca.cwds.data.cms;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link DrmsDocumentTemplateDao}.
 *
 * @author CWDS API Team
 */
public class DrmsDocumentTemplateDao extends CrudsDaoImpl<DrmsDocumentTemplate> {

  public static final Short APPLICATION_CONTEXT_REFERRAL_DOCUMENTS = 82;
  public static final Short APPLICATION_CONTEXT_OTHER = 3075;
  public static final Short GOVERMENT_ENTITY_SYSTEM = 0;
  public static final Short LANGUAGE_ENGLISH = 1253;
  public static final String SCREENERNARRATIVE = "Screener Narrative";
  public static final String SCREENERNARRATIVE_NS = "Screener Narrative (NS)";

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public DrmsDocumentTemplateDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * finding templates based on application context id and government entity type
   *
   * @param applicationContextType - application context type id
   * @param govermentEntityType - government entity type id.
   * @return - list of DrmsDocumentTemplates
   */
  @SuppressWarnings("unchecked")
  public DrmsDocumentTemplate[] findByApplicationContextAndGovermentEntity(
      Short applicationContextType, Short govermentEntityType) {

    final Query<DrmsDocumentTemplate> query =
        this.getSessionFactory().getCurrentSession().getNamedQuery(
            DrmsDocumentTemplate.NQ_TEMPLATES_BY_APPLICATION_CONTEXT_AND_GOVERNMENT_ENTITY);

    query.setParameter("applicationContextType", applicationContextType)
        .setParameter("govermentEntityType", govermentEntityType);
    return query.list().toArray(new DrmsDocumentTemplate[0]);
  }

}
