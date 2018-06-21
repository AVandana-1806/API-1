package gov.ca.cwds.inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.inject.DataAccessModuleTest.TestDataAccessModule;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.IntakeLovService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;
import gov.ca.cwds.rest.services.cms.SystemCodeService;
import io.dropwizard.setup.Bootstrap;

public class ServicesModuleTest {

  public static class TestServicesModule extends AbstractModule {

    private ServicesModule servicesModule;
    private MappingModule mappingModule = new MappingModule();

    @Override
    protected void configure() {
      install(servicesModule);
      install(mappingModule);
    }

    public ServicesModule getServicesModule() {
      return servicesModule;
    }

    public void setServicesModule(ServicesModule servicesModule) {
      this.servicesModule = servicesModule;
    }

  }

  SystemCodeDao systemCodeDao;
  SystemMetaDao systemMetaDao;
  IntakeLovDao intakeLovDao;
  SystemCodeService systemCodeService;
  IntakeLovService intakeLovService;
  ServicesModule target;

  @Before
  public void setup() throws Exception {
    systemCodeDao = mock(SystemCodeDao.class);
    systemMetaDao = mock(SystemMetaDao.class);
    intakeLovDao = mock(IntakeLovDao.class);
    systemCodeService = new SystemCodeService(systemCodeDao, systemMetaDao);
    intakeLovService = new IntakeLovService(intakeLovDao);
    target = new ServicesModule();
  }

  @Test
  public void type() throws Exception {
    assertThat(ServicesModule.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void configure_A$() {
    Bootstrap<ApiConfiguration> bootstrap = mock(Bootstrap.class);
    DataAccessModule dataAccessModule = new DataAccessModule(bootstrap);

    final TestDataAccessModule module1 = new TestDataAccessModule();
    module1.setDataAccessModule(dataAccessModule);

    final TestServicesModule module2 = new TestServicesModule();
    module2.setServicesModule(target);

    final Injector injector = Guice.createInjector(module1, module2);
  }

  @Test
  public void provideGovernmentOrganizationService_A$GovernmentOrganizationDao$LawEnforcementDao()
      throws Exception {
    GovernmentOrganizationDao governmentOrganizationDao = mock(GovernmentOrganizationDao.class);
    LawEnforcementDao lawEnforcementDao = mock(LawEnforcementDao.class);
    GovernmentOrganizationService actual =
        target.provideGovernmentOrganizationService(governmentOrganizationDao, lawEnforcementDao);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void provideValidator_A$() throws Exception {
    Validator actual = target.provideValidator();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void provideMessageBuilder_A$() throws Exception {
    MessageBuilder actual = target.provideMessageBuilder();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void provideSystemCodeService_A$SystemCodeDao$SystemMetaDao() throws Exception {
    SystemCodeService actual = target.provideSystemCodeService(systemCodeDao, systemMetaDao, null);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void provideIntakeCodeService_A$IntakeLovDao() throws Exception {
    IntakeLovService actual = target.provideIntakeLovService(intakeLovDao);
    assertThat(actual, is(notNullValue()));
  }

  // @Test
  // public void provideSystemCodeCache_A$SystemCodeService() throws Exception {
  // gov.ca.cwds.rest.api.domain.cms.SystemCodeCache actual =
  // target.provideSystemCodeCache(systemCodeService);
  // assertThat(actual, is(notNullValue()));
  // }

  @Test
  public void provideCmsSystemCodeSerializer_A$SystemCodeCache() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.SystemCodeCache systemCodeCache =
        mock(gov.ca.cwds.rest.api.domain.cms.SystemCodeCache.class);
    CmsSystemCodeSerializer actual = target.provideCmsSystemCodeSerializer(systemCodeCache);
    assertThat(actual, is(notNullValue()));
  }

}
