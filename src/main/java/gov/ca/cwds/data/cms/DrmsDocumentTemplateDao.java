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
   * Find templates by application context id and government entity type.
   *
   * @param applicationContextType - application context type id
   * @param governmentEntityType - government entity type id.
   * @return - list of DrmsDocumentTemplates
   */
  @SuppressWarnings("unchecked")
  public DrmsDocumentTemplate[] findByApplicationContextAndGovernmentEntity(
      Short applicationContextType, Short governmentEntityType) {

    final Query<DrmsDocumentTemplate> query = this.grabSession().getNamedQuery(
        DrmsDocumentTemplate.NQ_TEMPLATES_BY_APPLICATION_CONTEXT_AND_GOVERNMENT_ENTITY);

    query.setParameter("applicationContextType", applicationContextType)
        .setParameter("govermentEntityType", governmentEntityType);
    return query.list().toArray(new DrmsDocumentTemplate[0]);
  }

}
