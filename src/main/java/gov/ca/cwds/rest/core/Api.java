package gov.ca.cwds.rest.core;

/**
 * Constants defining resources locations in the API.
 * 
 * @author CWDS API Tea "secondaryLanguage":1255,m
 */
public final class Api {

  public static class Datasource {

    /**
     * Identifer for Postgres datasource
     */
    public static final String NS = "ns";

    /**
     * Identifer for CMS DB2 datasource
     */
    public static final String CMS = "cms";

    /**
     * Identifer for CMS DB2 replicated datasources.
     */
    public static final String CMS_REP = "rs";

    /**
     * Identifer for CMS DB2 datasource for XA transaction
     */
    public static final String XA_CMS = "xa_cms";

    /**
     * Identifer for CMS DB2 datasources for XA, replicated schema.
     */
    public static final String XA_CMS_RS = "xa_cms_rs";

    /**
     * Identifer for NS Postgres datasources for XA transactions.
     */
    public static final String XA_NS = "xa_ns";

    private Datasource() {}
  }

  public static class PathParam {

    public static final String SCREENING_ID = "screeningId";
    public static final String PARTICIPANT_ID = "participantId";
    public static final String LEGACY_ID = "legacyId";


    private PathParam() {}
  }

  public static class ResponseMessage {

    public static final String BAD_REQUEST = "Unable to process JSON";
    public static final String UNAUTHORIZED = "Not Authorized";
    public static final String NOT_FOUND = "Not found";
    public static final String NOT_ACCEPTABLE = "Accept Header not supported";
    public static final String UNPROCESSABLE_ENTITY = "Unable to validate Document";
    public static final String CONFLICT = "Conflict - already exists";

    private ResponseMessage() {}
  }

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
   * A {@code String} constant representing {@value #RESOURCE_PARTICIPANTS} API..
   */
  public static final String RESOURCE_PARTICIPANTS = "participants";

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
  public static final String DATASOURCE_CMS = Datasource.CMS;

  /**
   * Identifer for CMS DB2 datasources.
   */
  public static final String DS_CMS = Datasource.CMS;

  /**
   * Identifer for CMS DB2 replicated datasources.
   */
  public static final String DATASOURCE_CMS_REP = Datasource.CMS_REP;

  /**
   * Identifer for CMS DB2 datasources for XA, transactional schema.
   */
  public static final String DATASOURCE_XA_CMS = Datasource.XA_CMS;

  /**
   * Identifer for CMS DB2 datasources for XA, replicated schema.
   */
  public static final String DATASOURCE_XA_CMS_RS = Datasource.XA_CMS_RS;

  /**
   * Identifer for NS Postgres datasources.
   */
  public static final String DATASOURCE_NS = Datasource.NS;

  /**
   * Identifer for NS Postgres datasources for XA transactions.
   */
  public static final String DATASOURCE_XA_NS = Datasource.XA_NS;

  /**
   * Default private constructor
   */
  private Api() {
    // Default private constructor
  }

}
