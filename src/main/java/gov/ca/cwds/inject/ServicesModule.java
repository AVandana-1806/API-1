package gov.ca.cwds.inject;

import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.provideHibernateStatistics;
import static gov.ca.cwds.inject.FerbHibernateBundle.CMS_BUNDLE_TAG;
import static gov.ca.cwds.inject.FerbHibernateBundle.NS_BUNDLE_TAG;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

import gov.ca.cwds.cms.data.access.service.impl.CsecHistoryService;
import gov.ca.cwds.data.CaresStackUtils;
import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.dao.cms.BaseAuthorizationDao;
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.xa.CandaceSessionImpl;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAspect;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWorkAwareProxyFactory;
import gov.ca.cwds.data.persistence.xa.XaCmsRsHibernateBundle;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.SystemCodeCacheConfiguration;
import gov.ca.cwds.rest.api.domain.IntakeCodeCache;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContext.Parameter;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.AddressService;
import gov.ca.cwds.rest.services.CachingIntakeCodeService;
import gov.ca.cwds.rest.services.ContactIntakeApiService;
import gov.ca.cwds.rest.services.IntakeLovService;
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
import gov.ca.cwds.rest.services.screeningparticipant.ClientTransformer;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantDaoFactoryImpl;
import gov.ca.cwds.rest.services.screeningparticipant.ParticipantMapperFactoryImpl;
import gov.ca.cwds.rest.services.screeningparticipant.ScreeningParticipantService;
import gov.ca.cwds.rest.services.submit.ScreeningSubmitService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.hibernate.UnitOfWorkAspect;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

/**
 * Identifies all CWDS API business layer (aka, service) classes available for dependency injection
 * (aka, DI) by Google Guice.
 * 
 * @author CWDS API Team
 */
public class ServicesModule extends AbstractModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServicesModule.class);

  /**
   * AOP method interceptor manages database transactions outside of DropWizard resource classes.
   * 
   * @author CWDS API Team
   */
  public static class UnitOfWorkInterceptor implements org.aopalliance.intercept.MethodInterceptor {

    UnitOfWorkAwareProxyFactory proxyFactory;

    @Inject
    @CmsSessionFactory
    SessionFactory cmsSessionFactory;

    @Inject
    @CwsRsSessionFactory
    SessionFactory rsSessionFactory;

    @Inject
    @NsSessionFactory
    SessionFactory nsSessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
      final Method m = mi.getMethod();
      final RequestExecutionContext ctx = RequestExecutionContext.instance();
      LOGGER.info("Unit of work interceptor: class: {}, method: {}", m.getDeclaringClass(),
          m.getName());

      // If already in an XA transaction, skip this @UnitOfWork.
      if (ctx != null && RequestExecutionContext.instance().isXaTransaction()) {
        LOGGER.warn("******* XA TRANSACTION: SKIP @UnitOfWork! class: {}, method: {}******* ",
            m.getDeclaringClass(), m.getName());
        return mi.proceed();
      }

      // Use our wrapped session factories.
      final UnitOfWork annotation = mi.getMethod().getAnnotation(UnitOfWork.class);
      final String name = annotation.value().trim();
      SessionFactory currentSessionFactory;

      switch (name) {
        case Api.DS_CMS:
          proxyFactory = UnitOfWorkModule.getUnitOfWorkProxyFactory(Api.DS_CMS, cmsSessionFactory);
          currentSessionFactory = cmsSessionFactory;
          break;

        case Api.DATASOURCE_CMS_REP:
          proxyFactory =
              UnitOfWorkModule.getUnitOfWorkProxyFactory(Api.DATASOURCE_CMS_REP, rsSessionFactory);
          currentSessionFactory = rsSessionFactory;
          break;

        case Api.DS_NS:
          proxyFactory = UnitOfWorkModule.getUnitOfWorkProxyFactory(Api.DS_NS, nsSessionFactory);
          currentSessionFactory = nsSessionFactory;
          break;

        default:
          throw new IllegalStateException("Unknown datasource! " + annotation.value());
      }

      // AOP:
      final UnitOfWorkAspect aspect =
          proxyFactory.newAspect(ImmutableMap.of(name, currentSessionFactory));

      try {
        // Not XA, so clear XA flags.
        BaseAuthorizationDao.clearXaMode();
        RequestExecutionContext.instance().put(Parameter.XA_TRANSACTION, Boolean.FALSE);

        aspect.beforeStart(annotation);
        clearHibernateStatistics(annotation.value());
        final Object result = mi.proceed();
        collectAndProvideHibernateStatistics(annotation.value());
        aspect.afterEnd();
        return result;
      } catch (Exception e) {
        LOGGER.error("UNIT OF WORK FAILED! {}", e.getMessage(), e);
        aspect.onError();
        throw e;
      } finally {
        aspect.onFinish();
      }
    }

    protected void clearHibernateStatistics(String bundleTag) {
      if (CMS_BUNDLE_TAG.equals(bundleTag)) {
        cmsSessionFactory.getStatistics().clear();
      } else if (NS_BUNDLE_TAG.equals(bundleTag)) {
        nsSessionFactory.getStatistics().clear();
      }
    }

    protected void collectAndProvideHibernateStatistics(String bundleTag) {
      if (CMS_BUNDLE_TAG.equals(bundleTag)) {
        provideHibernateStatistics(bundleTag, cmsSessionFactory.getStatistics());
      } else if (NS_BUNDLE_TAG.equals(bundleTag)) {
        provideHibernateStatistics(bundleTag, nsSessionFactory.getStatistics());
      }
    }

  }

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

  /**
   * Construct an interceptor to stack traces for any injected class.
   * 
   * <blockquote>
   * 
   * <pre>
   * final PhineasMethodLoggerInterceptor daoInterceptor = new PhineasMethodLoggerInterceptor();
   * bindInterceptor(Matchers.subclassesOf(CrudsDao.class), Matchers.any(), daoInterceptor);
   * requestInjection(daoInterceptor);
   * </pre>
   * 
   * </blockquote>
   * 
   * @author CWDS API Team
   */
  public static class PhineasMethodLoggerInterceptor
      implements org.aopalliance.intercept.MethodInterceptor {

    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
      try {
        final Method m = mi.getMethod();
        LOGGER.info("stack for method call: class: {}, method: {}", m.getDeclaringClass(),
            m.getName());
        CaresStackUtils.logStack();

        LOGGER.info("Phineas interceptor: before method: {}", m);
        final Object result = mi.proceed();
        LOGGER.info("Phineas interceptor: after  method: {}", m);
        return result;
      } catch (Exception e) {
        LOGGER.error("Phineas interceptor: ERROR PRINTING STACK TRACE! {}", e.getMessage(), e);
        throw e;
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
    bind(gov.ca.cwds.rest.services.cms.AddressService.class);
    bind(gov.ca.cwds.rest.services.StaffPersonService.class);

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
    bind(ScreeningParticipantService.class);
    bind(ScreeningRelationshipService.class);
    bind(ScreeningService.class);
    bind(ScreeningSubmitService.class);
    bind(ScreeningToReferral.class);
    bind(SpecialProjectReferralService.class);
    bind(StaffPersonIdRetriever.class);
    bind(StaffPersonService.class);
    bind(TickleService.class);

    // Enable AOP for DropWizard @UnitOfWork.
    final UnitOfWorkInterceptor interceptor = new UnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(UnitOfWork.class), interceptor);
    requestInjection(interceptor);

    // Enable AOP for Ferb @XAUnitOfWork.
    final XAUnitOfWorkInterceptor xaInterceptor = new XAUnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(XAUnitOfWork.class), xaInterceptor);
    requestInjection(xaInterceptor);

    // No Hibernate managed transactions when using XA.
    final Properties p = new Properties();
    p.setProperty("managed", "N");
    Names.bindProperties(binder(), p);

    // @Singleton does not work with DropWizard Guice.
    bind(GovernmentOrganizationService.class).toProvider(GovtOrgSvcProvider.class);
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
   * @param systemCodeCache - systemCodeCache
   * @return the CmsSystemCodeSerializer
   */
  @Provides
  public CmsSystemCodeSerializer provideCmsSystemCodeSerializer(SystemCodeCache systemCodeCache) {
    LOGGER.debug("provide syscode serializer");
    return new CmsSystemCodeSerializer(systemCodeCache);
  }

  // ==========================
  // Code caches:
  // ==========================

  /**
   * @param systemCodeDao - systemCodeDao
   * @param systemMetaDao - systemMetaDao
   * @param config Ferb API configuration
   * @return the systemCodes
   */
  @Provides
  // @Singleton
  public synchronized SystemCodeService provideSystemCodeService(SystemCodeDao systemCodeDao,
      SystemMetaDao systemMetaDao, ApiConfiguration config) {
    LOGGER.debug("provide syscode service");
    SystemCodeService ret;

    boolean preLoad = true; // default is true
    long secondsToRefreshCache = 365L * 24 * 60 * 60; // default is 365 days

    final SystemCodeCacheConfiguration systemCodeCacheConfig =
        config != null ? config.getSystemCodeCacheConfiguration() : null;
    if (systemCodeCacheConfig != null) {
      preLoad = systemCodeCacheConfig.getPreLoad(preLoad);
      secondsToRefreshCache = systemCodeCacheConfig.getRefreshAfter(secondsToRefreshCache);
    }

    try (final Session session = new CandaceSessionImpl(systemCodeDao.grabSession())) {
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

  /**
   * @param systemCodeService - systemCodeService
   * @return the SystemCodeCache
   */
  @Provides
  // @Singleton
  public SystemCodeCache provideSystemCodeCache(SystemCodeService systemCodeService) {
    LOGGER.debug("provide syscode cache");
    final SystemCodeCache systemCodeCache = (SystemCodeCache) systemCodeService;
    systemCodeCache.register();
    return systemCodeCache;
  }

  /**
   * @param intakeLovDao - intakeLovDao
   * @param config - config
   * @return the IntakeCode
   */
  @Provides
  // @Singleton
  public IntakeLovService provideIntakeLovService(IntakeLovDao intakeLovDao,
      ApiConfiguration config) {
    LOGGER.debug("provide intakeCode service");

    boolean preLoad = true; // default is true
    long secondsToRefreshCache = 365L * 24 * 60 * 60; // default is 365 days

    final SystemCodeCacheConfiguration intakeCodeCacheConfig =
        config != null ? config.getIntakeCodeCacheConfiguration() : null;
    if (intakeCodeCacheConfig != null) {
      preLoad = intakeCodeCacheConfig.getPreLoad(preLoad);
      secondsToRefreshCache = intakeCodeCacheConfig.getRefreshAfter(secondsToRefreshCache);
    }

    return new CachingIntakeCodeService(intakeLovDao, secondsToRefreshCache, preLoad);
  }

  /**
   * @param intakeLovService - intakeLovService
   * @return IntakeCodeCache
   */
  @Provides
  public IntakeCodeCache provideIntakeLovCodeCache(IntakeLovService intakeLovService) {
    LOGGER.debug("provide intakeCode cache");

    if (intakeCodeCache == null) {
      makeIntakeLovCodeCache(intakeLovService);
    }

    return intakeCodeCache;
  }

  protected synchronized IntakeCodeCache makeIntakeLovCodeCache(IntakeLovService intakeLovService) {
    if (intakeCodeCache == null) {
      final IntakeCodeCache intakeCodeCache = (IntakeCodeCache) intakeLovService;
      intakeCodeCache.register();
    }
    return intakeCodeCache;
  }

}
