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
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.persistence.cms.SystemCode;
import gov.ca.cwds.inject.DataAccessModuleTest.TestDataAccessModule;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.CachingIntakeCodeService;
import gov.ca.cwds.rest.services.cms.GovernmentOrganizationService;
import gov.ca.cwds.rest.services.cms.SystemCodeService;
import gov.ca.cwds.rest.util.Doofenshmirtz;
import io.dropwizard.setup.Bootstrap;

public class ServicesModuleTest extends Doofenshmirtz<SystemCode> {

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

  IntakeLovDao intakeLovDao;
  SystemCodeService systemCodeService;
  CachingIntakeCodeService intakeLovService;
  ServicesModule target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    intakeLovDao = mock(IntakeLovDao.class);
    systemCodeService = new SystemCodeService(systemCodeDao, systemMetaDao);
    intakeLovService = new CachingIntakeCodeService(intakeLovDao);
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
  public void provideCmsSystemCodeSerializer_A$SystemCodeCache() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.SystemCodeCache systemCodeCache =
        mock(gov.ca.cwds.rest.api.domain.cms.SystemCodeCache.class);
    CmsSystemCodeSerializer actual = target.provideCmsSystemCodeSerializer(systemCodeCache);
    assertThat(actual, is(notNullValue()));
  }

}
