package gov.ca.cwds.rest.core;

/**
 * Constants defining resources locations in the API.
 * 
 * @author CWDS API Tea "secondaryLanguage":1255,m
 */
public final class Api {

  /**
   * A {@code String} constant representing {@value #RESOURCE_ADDRESSES} API..
   */
  public static final String RESOURCE_ADDRESSES = "addresses";

  /**
   * A {@code String} constant representing {@value #RESOURCE_APPLICATION} API..
   */
  public static final String RESOURCE_APPLICATION = "application";

  /**
   * A {@code String} constant representing {@value #RESOURCE_CASE_HISTORY_OF_INVOLVEMENT} API..
   */
  public static final String RESOURCE_CASE_HISTORY_OF_INVOLVEMENT = "hoi_cases";

  /**
   * A {@code String} constant representing {@value #RESOURCE_CLIENT} API..
   */
  public static final String RESOURCE_CLIENT = "clients";

  /**
   * A {@code String} constant representing {@value #RESOURCE_AUTHORIZE} API..
   */
  public static final String RESOURCE_AUTHORIZE = "authorize";

  /**
   * A {@code String} constant representing {@value #RESOURCE_CLIENT_COLLATERALS} API..
   */
  public static final String RESOURCE_CLIENT_COLLATERALS = "client_collaterals";

  /**
   * A {@code String} constant representing {@value #RESOURCE_CMS_DOCUMENT} API.
   */
  public static final String RESOURCE_CMS_DOCUMENT = "cmsdocument";

  /**
   * A {@code String} constant representing {@value #RESOURCE_CMS_DOC_REFRRAL_CLIENT} API.
   */
  public static final String RESOURCE_CMS_DOC_REFRRAL_CLIENT = "cmsdocreferralclient";

  /**
   * A {@code String} constant representing {@value #RESOURCE_CMSNSREFERRAL} API.
   */
  public static final String RESOURCE_CMSNSREFERRAL = "cms_ns_referrals";

  /**
   * A {@code String} constant representing {@value #RESOURCE_CMS_UI_IDENTIFIER} API..
   */
  public static final String RESOURCE_CMS_UI_IDENTIFIER = "cms_ui_id";

  /**
   * A {@code String} constant representing {@value #RESOURCE_DELIVERY_SERVICE} API.
   */
  public static final String RESOURCE_DELIVERY_SERVICE = "contacts";

  /**
   * A {@code String} constant representing {@value #RESOURCE_INTAKE_CONTACTS} API..
   */
  public static final String RESOURCE_INTAKE_CONTACTS = "intake/contacts";

  /**
   * A {@code String} constant representing {@value #RESOURCE_GOVERNMENT_ORG} API.
   */
  public static final String RESOURCE_GOVERNMENT_ORG = "cross_report_agency";

  /**
   * A {@code String} constant representing {@value #RESOURCE_HOI_SCREENINGS} API..
   */
  public static final String RESOURCE_HOI_SCREENINGS = "hoi_screenings";

  /**
   * A {@code String} constant representing {@value #RESOURCE_INVESTIGATIONS} API.
   */
  public static final String RESOURCE_INVESTIGATIONS = "investigations";

  /**
   * A {@code String} constant representing {@value #RESOURCE_INTAKE_LOV} API.
   */
  public static final String RESOURCE_INTAKE_LOV = "lov";

  /**
   * A {@code String} constant representing {@value #RESOURCE_LOV} API..
   */
  public static final String RESOURCE_LOV = "__oldlov";

  /**
   * A {@code String} constant representing {@value #RESOURCE_PARTICIPANTS_INTAKE_API} API..
   */
  public static final String RESOURCE_PARTICIPANTS_INTAKE_API = "participants";

  /**
   * A {@code String} constant representing {@value #RESOURCE_PEOPLE} API..
   */
  public static final String RESOURCE_PEOPLE = "people";

  /**
   * A {@code String} constant representing {@value #RESOURCE_REFERRALS} API..
   */
  public static final String RESOURCE_REFERRALS = "referrals";

  /**
   * A {@code String} constant representing {@value #RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT} API..
   */
  public static final String RESOURCE_REFERRAL_HISTORY_OF_INVOLVEMENT = "hoi_referrals";

  /**
   * A {@code String} constant representing {@value #RESOURCE_SCREENINGS} API..
   */
  public static final String RESOURCE_SCREENINGS = "screenings";

  /**
   * A {@code String} constant representing {@value #RESOURCE_INTAKE_SCREENINGS} API..
   */
  public static final String RESOURCE_INTAKE_SCREENINGS = "intake/screenings";

  /**
   * A {@code String} constant representing {@value #RESOURCE_STAFF_PERSONS} API.
   */
  public static final String RESOURCE_STAFF_PERSONS = "staffpersons";

  /**
   * A {@code String} constant representing {@value #SCREENING_RELATIONSHIPS} API.
   */
  public static final String SCREENING_RELATIONSHIPS = "screening_relationships";

  /**
   * Identifer for CMS DB2 datasources.
   */
  public static final String DATASOURCE_CMS = "cms";

  /**
   * Identifer for CMS DB2 datasources for XA transactions.
   */
  public static final String DATASOURCE_XA_CMS = "xa_cms";

  /**
   * Default private constructor
   */
  private Api() {
    // Default private constructor
  }

}
