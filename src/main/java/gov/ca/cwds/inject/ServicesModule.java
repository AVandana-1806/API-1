package gov.ca.cwds.inject;

import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.provideHibernateStatistics;
import static gov.ca.cwds.inject.FerbHibernateBundle.CMS_BUNDLE_TAG;
import static gov.ca.cwds.inject.FerbHibernateBundle.NS_BUNDLE_TAG;

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
import io.dropwizard.hibernate.HibernateBundle;
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
    @CmsHibernateBundle
    HibernateBundle<ApiConfiguration> cmsHibernateBundle;

    @Inject
    @NsHibernateBundle
    HibernateBundle<ApiConfiguration> nsHibernateBundle;

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation mi) throws Throwable {
      final RequestExecutionContext ctx = RequestExecutionContext.instance();
      if (ctx != null && RequestExecutionContext.instance().isXaTransaction()) {
        final Method m = mi.getMethod();
        LOGGER.warn("******* XA TRANSACTION: IGNORE @UnitOfWork. class: {}, method: {}******* ",
            m.getDeclaringClass(), m.getName());
        return mi.proceed();
      }

      proxyFactory =
          UnitOfWorkModule.getUnitOfWorkProxyFactory(cmsHibernateBundle, nsHibernateBundle);
      final UnitOfWorkAspect aspect = proxyFactory.newAspect();
      try {
        BaseAuthorizationDao.clearXaMode();
        final UnitOfWork unitOfWorkAnnotation = mi.getMethod().getAnnotation(UnitOfWork.class);
        aspect.beforeStart(unitOfWorkAnnotation);
        clearHibernateStatistics(unitOfWorkAnnotation.value());
        final Object result = mi.proceed();
        collectAndProvideHibernateStatistics(unitOfWorkAnnotation.value());
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
        cmsHibernateBundle.getSessionFactory().getStatistics().clear();
      } else if (NS_BUNDLE_TAG.equals(bundleTag)) {
        nsHibernateBundle.getSessionFactory().getStatistics().clear();
      }
    }

    protected void collectAndProvideHibernateStatistics(String bundleTag) {
      if (CMS_BUNDLE_TAG.equals(bundleTag)) {
        provideHibernateStatistics(bundleTag,
            cmsHibernateBundle.getSessionFactory().getStatistics());
      } else if (NS_BUNDLE_TAG.equals(bundleTag)) {
        provideHibernateStatistics(bundleTag,
            nsHibernateBundle.getSessionFactory().getStatistics());
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
      BaseAuthorizationDao.setXaMode(true);
      final RequestExecutionContext ctx = RequestExecutionContext.instance();
      if (ctx != null) {
        ctx.put(Parameter.XA_TRANSACTION, Boolean.TRUE);
      }

      proxyFactory = UnitOfWorkModule.getXAUnitOfWorkProxyFactory(xaCmsHibernateBundle,
          xaNsHibernateBundle, xaCmsRsHibernateBundle);
      final XAUnitOfWorkAspect aspect = proxyFactory.newAspect();
      try {
        LOGGER.info("XAUnitOfWorkInterceptor: Before XA annotation");
        BaseAuthorizationDao.setXaMode(true);
        final Method method = mi.getMethod();
        aspect.beforeStart(method, method.getAnnotation(XAUnitOfWork.class));
        final Object result = mi.proceed();
        aspect.afterEnd();
        LOGGER.info("XAUnitOfWorkInterceptor: After XA annotation");
        return result;
      } catch (Exception e) {
        LOGGER.error("XAUnitOfWorkInterceptor: BOOM! {}", e.getMessage(), e);
        aspect.onError();
        throw e;
      } finally {
        aspect.onFinish();
        BaseAuthorizationDao.clearXaMode();
      }
    }

  }

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
    bind(ClientCollateralService.class);
    bind(ClientRelationshipService.class);
    bind(CmsDocReferralClientService.class);
    bind(CmsDocumentService.class);
    bind(CmsNSReferralService.class);
    bind(CmsReferralService.class);
    bind(ContactService.class);
    bind(CrossReportService.class);
    bind(DeliveredService.class);
    bind(DeliveredToIndividualService.class);
    bind(DrmsDocumentService.class);
    bind(DrmsDocumentTemplateService.class);
    bind(OtherCaseReferralDrmsDocumentService.class);
    bind(GovernmentOrganizationCrossReportService.class);
    bind(LegacyKeyService.class);
    bind(PersonService.class);
    bind(ReferralClientService.class);
    bind(ReferralService.class);
    bind(ReporterService.class);
    bind(ScreeningService.class);
    bind(ScreeningSubmitService.class);
    bind(ScreeningToReferral.class);
    bind(StaffPersonIdRetriever.class);
    bind(StaffPersonService.class);
    bind(TickleService.class);
    bind(HOIReferralService.class);
    bind(InvolvementHistoryService.class);
    bind(HOICaseService.class);
    bind(AuthorizationService.class);
    bind(ScreeningRelationshipService.class);
    bind(CsecHistoryService.class);
    bind(ScreeningParticipantService.class);
    bind(ParticipantDaoFactoryImpl.class);
    bind(ParticipantMapperFactoryImpl.class);
    bind(SpecialProjectReferralService.class);
    bind(ClientTransformer.class);
    bind(ContactIntakeApiService.class);

    // Enable AOP for DropWizard @UnitOfWork.
    final UnitOfWorkInterceptor interceptor = new UnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(UnitOfWork.class), interceptor);
    requestInjection(interceptor);

    // Enable AOP for Ferb @XAUnitOfWork.
    final XAUnitOfWorkInterceptor xaInterceptor = new XAUnitOfWorkInterceptor();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(XAUnitOfWork.class), xaInterceptor);
    requestInjection(xaInterceptor);

    // DRS: uncomment to log stack traces for DAO's.
    // final PhineasMethodLoggerInterceptor daoInterceptor = new PhineasMethodLoggerInterceptor();
    // bindInterceptor(Matchers.subclassesOf(CrudsDao.class), Matchers.any(), daoInterceptor);
    // requestInjection(daoInterceptor);

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
   * @param systemCodeDao - systemCodeDao
   * @param systemMetaDao - systemMetaDao
   * @param config Ferb API configuration
   * @return the systemCodes
   */
  @Provides
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
    final IntakeCodeCache intakeCodeCache = (IntakeCodeCache) intakeLovService;
    intakeCodeCache.register();
    return intakeCodeCache;
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

}
