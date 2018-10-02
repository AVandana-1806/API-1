package gov.ca.cwds.inject;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

import gov.ca.cwds.cms.data.access.service.impl.CsecHistoryService;
import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.xa.CandaceSessionImpl;
import gov.ca.cwds.data.persistence.xa.CaresMethodInterceptor;
import gov.ca.cwds.data.persistence.xa.CaresUnitOfWorkInterceptor;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAspect;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAwareProxyFactory;
import gov.ca.cwds.data.persistence.xa.XaCmsRsHibernateBundle;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.SystemCodeCacheConfiguration;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.CachingIntakeCodeService;
import gov.ca.cwds.rest.services.ContactIntakeApiService;
import gov.ca.cwds.rest.services.PersonService;
import gov.ca.cwds.rest.services.ScreeningRelationshipService;
import gov.ca.cwds.rest.services.ScreeningService;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import gov.ca.cwds.rest.services.cms.AllegationService;
import gov.ca.cwds.rest.services.cms.AssignmentService;
import gov.ca.cwds.rest.services.cms.CachingSystemCodeService;
import gov.ca.cwds.rest.services.cms.ClientCollateralService;
import gov.ca.cwds.rest.services.cms.ClientRelationshipService;
import gov.ca.cwds.rest.services.cms.CmsDocReferralClientService;
import gov.ca.cwds.rest.services.cms.CmsDocumentService;
import gov.ca.cwds.rest.services.cms.CmsNSReferralService;
import gov.ca.cwds.rest.services.cms.CmsReferralService;
import gov.ca.cwds.rest.services.cms.CrossReportService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentService;
import gov.ca.cwds.rest.services.cms.DrmsDocumentTemplateService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationCrossReportService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;
import gov.ca.cwds.rest.services.cms.LegacyKeyService;
import gov.ca.cwds.rest.services.cms.OtherCaseReferralDrmsDocumentService;
import gov.ca.cwds.rest.services.cms.ReferralClientService;
import gov.ca.cwds.rest.services.cms.ReferralService;
import gov.ca.cwds.rest.services.cms.ReporterService;
import gov.ca.cwds.rest.services.cms.SpecialProjectReferralService;
import gov.ca.cwds.rest.services.cms.StaffPersonIdRetriever;
import gov.ca.cwds.rest.services.cms.StaffPersonService;
import gov.ca.cwds.rest.services.cms.SystemCodeService;
import gov.ca.cwds.rest.services.cms.TickleService;
import gov.ca.cwds.rest.services.contact.DeliveredService;
import gov.ca.cwds.rest.services.hoi.HOICaseService;
import gov.ca.cwds.rest.services.hoi.HOIReferralService;
import gov.ca.cwds.rest.services.hoi.InvolvementHistoryService;
import gov.ca.cwds.rest.services.investigation.contact.ContactService;
import gov.ca.cwds.rest.services.investigation.contact.DeliveredToIndividualService;
import gov.ca.cwds.rest.services.relationship.RelationshipFacade;
import gov.ca.cwds.rest.services.relationship.RelationshipFacadeLegacyAndNewDB;
import gov.ca.cwds.rest.services.screeningparticipant.ClientTransformer;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantDaoFactoryImpl;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantMapperFactoryImpl;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Identifies all CWDS API business layer (aka, service) classes available for dependency injection
 * (aka, DI) by Google Guice.
 *
 * @author CWDS API Team
 */
public class ServicesModule extends AbstractModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServicesModule.class);

  /**
   * AOP method interception for Ferb annotation {@link XAUnitOfWork}. Automatically manages
   * Hibernate sessions and XA transactions.
   *
   * <p>
   * NEXT: switch *all* data sources to XA and change all resources to use {@link XAUnitOfWork}
   * instead of {@link UnitOfWork}.
   * </p>
   *
   * @author CWDS API Team
   * @see XAUnitOfWorkAspect
   */
  public static class XAUnitOfWorkInterceptor
      implements org.aopalliance.intercept.MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(XAUnitOfWorkInterceptor.class);

    XAUnitOfWorkAwareProxyFactory proxyFactory;

    @Inject
    @XaCmsHibernateBundle
    FerbHibernateBundle xaCmsHibernateBundle;

    @Inject
    @XaCmsRsHibernateBundle
    FerbHibernateBundle xaCmsRsHibernateBundle;

    @Inject
    @XaNsHibernateBundle
    FerbHibernateBundle xaNsHibernateBundle;

    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
      LOGGER.info("XAUnitOfWorkInterceptor: intercept!");
      proxyFactory = UnitOfWorkModule.getXAUnitOfWorkProxyFactory(xaCmsHibernateBundle,
          xaNsHibernateBundle, xaCmsRsHibernateBundle);
      final XAUnitOfWorkAspect aspect = proxyFactory.newAspect();
      try {
        LOGGER.debug("XAUnitOfWorkInterceptor: Before XA annotation");
        final Method method = mi.getMethod();
        aspect.beforeStart(method, method.getAnnotation(XAUnitOfWork.class));
        final Object result = mi.proceed();
        aspect.afterEnd();
        LOGGER.debug("XAUnitOfWorkInterceptor: After XA annotation");
        return result;
      } catch (Exception e) {
        LOGGER.error("XAUnitOfWorkInterceptor: XA UNIT OF WORK FAILED! {}", e.getMessage(), e);
        aspect.onError();
        throw e;
      } finally {
        LOGGER.info("XAUnitOfWorkInterceptor: Finish XA");
        aspect.onFinish();
      }
    }

  }

  private IntakeCodeCache intakeCodeCache;
  private SystemCodeCache systemCodeCache;

  /**
   * Default, no-op constructor.
   */
  public ServicesModule() {
    // Default, no-op.
  }

  @Override
  protected void configure() {
    LOGGER.info("configure: point 1");
    bind(AddressService.class);
    bind(AllegationService.class);
    bind(AssignmentService.class);
    bind(AuthorizationService.class);
    bind(ClientCollateralService.class);
    bind(ClientRelationshipService.class);
    bind(ClientTransformer.class);
    bind(CmsDocReferralClientService.class);
    bind(CmsDocumentService.class);
    bind(CmsNSReferralService.class);
    bind(CmsReferralService.class);
    bind(ContactIntakeApiService.class);
    bind(ContactService.class);
    bind(CrossReportService.class);
    bind(CsecHistoryService.class);
    bind(DeliveredService.class);
    bind(DeliveredToIndividualService.class);
    bind(DrmsDocumentService.class);
    bind(DrmsDocumentTemplateService.class);
    bind(gov.ca.cwds.rest.services.cms.AddressService.class);
    bind(gov.ca.cwds.rest.services.StaffPersonService.class);
    bind(GovernmentOrganizationCrossReportService.class);
    bind(HOICaseService.class);
    bind(HOIReferralService.class);
    bind(InvolvementHistoryService.class);
    bind(LegacyKeyService.class);
    bind(OtherCaseReferralDrmsDocumentService.class);
    bind(ParticipantDaoFactoryImpl.class);
    bind(ParticipantMapperFactoryImpl.class);
    bind(PersonService.class);
    bind(ReferralClientService.class);
    bind(ReferralService.class);
    bind(ReporterService.class);
    bind(ScreeningRelationshipService.class);
    bind(ScreeningService.class);
    bind(ScreeningToReferral.class);
    bind(SpecialProjectReferralService.class);
    bind(StaffPersonIdRetriever.class);
    bind(StaffPersonService.class);
    bind(TickleService.class);
    bind(DroolsService.class);
    bind(RelationshipFacade.class).to(RelationshipFacadeLegacyAndNewDB.class);

    LOGGER.info("configure: point 2");

    // Enable AOP for DropWizard @UnitOfWork.
    final CaresUnitOfWorkInterceptor interceptor = new CaresUnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(UnitOfWork.class), interceptor);
    requestInjection(interceptor);

    // Enable AOP for Ferb @XAUnitOfWork.
    final XAUnitOfWorkInterceptor xaInterceptor = new XAUnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(XAUnitOfWork.class), xaInterceptor);
    requestInjection(xaInterceptor);

    // Monitor DAO activity.
    final CaresMethodInterceptor daoInterceptor = new CaresMethodInterceptor();
    bindInterceptor(Matchers.subclassesOf(CrudsDao.class), Matchers.any(), daoInterceptor);
    requestInjection(daoInterceptor);

    // No Hibernate managed transactions when using XA.
    final Properties p = new Properties();
    p.setProperty("managed", "N");
    Names.bindProperties(binder(), p);

    // @Singleton does not work with DropWizard Guice.
    bind(GovernmentOrganizationService.class).toProvider(GovtOrgSvcProvider.class);
    LOGGER.info("configure: done");
  }

  /**
   * @param governmentOrganizationDao - governmentOrganizationDao
   * @param lawEnforcementDao - lawEnforcementDao
   * @return the cross report agencies
   */
  public GovernmentOrganizationService provideGovernmentOrganizationService(
      GovernmentOrganizationDao governmentOrganizationDao, LawEnforcementDao lawEnforcementDao) {
    return new GovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
  }

  @Provides
  Validator provideValidator() {
    return Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Provides
  MessageBuilder provideMessageBuilder() {
    return new MessageBuilder();
  }

  /**
   * Provides SystemCodeCache.
   *
   * @param systemCodeDao system code DAO
   * @param systemMetaDao system meta (category) DAO
   * @param config Ferb API configuration
   * @return SystemCodeCache initialized CMS system code cache
   */
  @Provides
  public synchronized SystemCodeCache provideSystemCodeCache(SystemCodeDao systemCodeDao,
      SystemMetaDao systemMetaDao, ApiConfiguration config) {
    LOGGER.info("provide syscode cache");
    if (systemCodeCache == null) {
      SystemCodeService systemCodeService =
          createSystemCodeService(systemCodeDao, systemMetaDao, config);
      systemCodeCache = (SystemCodeCache) systemCodeService;
      systemCodeCache.register();
    }
    return systemCodeCache;
  }

  /**
   * Provides IntakeCodeCache.
   *
   * @param intakeLovDao Intake list of values (LOV) DAO
   * @return IntakeCodeCache initialized Intake LOV code cache
   */
  @Provides
  public synchronized IntakeCodeCache provideIntakeLovCodeCache(IntakeLovDao intakeLovDao) {
    LOGGER.info("provide intakeCode cache");
    if (intakeCodeCache == null) {
      CachingIntakeCodeService intakeLovService = new CachingIntakeCodeService(intakeLovDao);
      intakeCodeCache = intakeLovService;
      intakeCodeCache.register();
    }
    return intakeCodeCache;
  }

  /**
   * @param systemCodeCache - systemCodeCache
   * @return the CmsSystemCodeSerializer
   */
  @Provides
  public CmsSystemCodeSerializer provideCmsSystemCodeSerializer(SystemCodeCache systemCodeCache) {
    LOGGER.info("provide syscode serializer");
    return new CmsSystemCodeSerializer(systemCodeCache);
  }

  //////////////////////////////////////////////////////////////
  // Private
  //////////////////////////////////////////////////////////////

  private SystemCodeService createSystemCodeService(SystemCodeDao systemCodeDao,
      SystemMetaDao systemMetaDao, ApiConfiguration config) {
    LOGGER.info("createSystemCodeService");
    SystemCodeService ret;

    boolean preLoad = true; // default is true
    long secondsToRefreshCache = 365L * 24 * 60 * 60; // default is 365 days

    final SystemCodeCacheConfiguration systemCodeCacheConfig =
        config != null ? config.getSystemCodeCacheConfiguration() : null;
    if (systemCodeCacheConfig != null) {
      preLoad = systemCodeCacheConfig.getPreLoad(preLoad);
      secondsToRefreshCache = systemCodeCacheConfig.getRefreshAfter(secondsToRefreshCache);
    }

    try (final Session session = new CandaceSessionImpl(systemCodeDao)) {
      LOGGER.info("Load code cache: preLoad: {}, secondsToRefreshCache: {}", preLoad,
          secondsToRefreshCache);
      final Transaction txn = session.beginTransaction();
      ret = new CachingSystemCodeService(systemCodeDao, systemMetaDao, secondsToRefreshCache,
          preLoad);
      txn.commit();
    } catch (Exception e) {
      LOGGER.error("ERROR LOADING SYSTEM CODE CACHE! {}", e.getMessage(), e);
      throw e;
    }

    return ret;
  }
}
