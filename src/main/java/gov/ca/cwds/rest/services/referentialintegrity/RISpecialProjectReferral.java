package gov.ca.cwds.rest.services.referentialintegrity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import gov.ca.cwds.data.ApiHibernateInterceptor;
import gov.ca.cwds.data.ApiReferentialCheck;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectDao;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * Verifies that a special project referral points to a valid special project for the county.
 * 
* <p>
 * Validate any other constraints or business rules here before committing a transaction to the
 * database.
 * </p>
 * 
 * <p>
 * Enforce foreign key constraints using "normal" Hibernate mechanisms, such as this typical FK:
 * </p>
 * <blockquote>
 * 
 *  
* <pre>
 * &#64;OneToOne(optional = false)
 * &#64;JoinColumn(name = "FKREFERL_T", nullable = true, updatable = false, insertable = false)
 * private Referral referral;
 * 
 * &#64;OneToOne(optional = false)
 * &#64;JoinColumn(name = "FKSPC_PRJT", nullable = true, updatable = false, insertable = false)
 * private SpecialProject specialProject;
 * 
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see ApiHibernateInterceptor
 */
public class RISpecialProjectReferral implements ApiReferentialCheck<SpecialProjectReferral> {
  
  private static final String REFERRAL_ID_INVALID_ERROR = 
      "SpecialProjectReferral => Referral with given Identifier is not present in database";
  private static final String SPECIAL_PROJECT_ID_INVALID_ERROR =
      "SpecialProjectReferral => Special Project with given Identifier is no present in database";
  
  /**
   * Default.
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(RISpecialProjectReferral.class);

  private transient ReferralDao referralDao;
  private transient SpecialProjectDao specialProjectDao;

  /**
   * Constructor
   * 
   * @param referralDao - referralDao
   * @param specialProjectDao - specialProjectDao
   */
  @Inject
  public RISpecialProjectReferral(final ReferralDao referralDao, SpecialProjectDao specialProjectDao) {
    this.referralDao = referralDao;
    this.specialProjectDao = specialProjectDao;
    ApiHibernateInterceptor.addHandler(SpecialProjectReferral.class, specialProjectReferral -> apply((SpecialProjectReferral) specialProjectReferral));
  }
  
  @Override
  public Boolean apply(SpecialProjectReferral specialProjectReferral) {
    String referralId = specialProjectReferral.getReferralId();
    String specialProjectId = specialProjectReferral.getSpecialProjectId();
    
    LOGGER.debug("RI: SpecialProjectReferral");
    if (referralDao.find(referralId) == null) {
      throw new ReferentialIntegrityException(REFERRAL_ID_INVALID_ERROR);
    } else {
      if (StringUtils.isNoneBlank(specialProjectId) && specialProjectDao.find(specialProjectId) == null) {
        throw new ReferentialIntegrityException(SPECIAL_PROJECT_ID_INVALID_ERROR);
      }
    }
    return Boolean.TRUE;
  }

}
