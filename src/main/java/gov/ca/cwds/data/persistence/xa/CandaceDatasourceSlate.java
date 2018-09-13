package gov.ca.cwds.data.persistence.xa;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.inject.CwsRsSessionFactory;
import gov.ca.cwds.inject.NsSessionFactory;

/**
 * Hibernate session factory slate references all Ferb data sources in one convenient place.
 * 
 * @author CWDS API Team
 */
public class CandaceDatasourceSlate implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private SessionFactory cmsSessionFactory;
  private SessionFactory nsSessionFactory;
  private SessionFactory cmsRsSessionFactory;

  @Inject
  public CandaceDatasourceSlate(@CmsSessionFactory SessionFactory cmsSessionFactory,
      @NsSessionFactory SessionFactory nsSessionFactory,
      @CwsRsSessionFactory SessionFactory cmsRsSessionFactory) {
    this.cmsSessionFactory = cmsSessionFactory;
    this.cmsRsSessionFactory = cmsRsSessionFactory;
    this.nsSessionFactory = nsSessionFactory;
  }

  public SessionFactory getCmsSessionFactory() {
    return cmsSessionFactory;
  }

  public SessionFactory getNsSessionFactory() {
    return nsSessionFactory;
  }

  public SessionFactory getCmsRsSessionFactory() {
    return cmsRsSessionFactory;
  }

}
