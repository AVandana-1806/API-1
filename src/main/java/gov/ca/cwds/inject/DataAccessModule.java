package gov.ca.cwds.inject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import gov.ca.cwds.data.cms.AddressUcDao;
import gov.ca.cwds.data.cms.AllegationDao;
import gov.ca.cwds.data.cms.AllegationPerpetratorHistoryDao;
import gov.ca.cwds.data.cms.AssignmentDao;
import gov.ca.cwds.data.cms.AssignmentUnitDao;
import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.CaseAssignmentDao;
import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.CaseLoadDao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.cms.ClientCollateralDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.ClientScpEthnicityDao;
import gov.ca.cwds.data.cms.ClientUcDao;
import gov.ca.cwds.data.cms.CmsDocReferralClientDao;
import gov.ca.cwds.data.cms.CmsDocumentDao;
import gov.ca.cwds.data.cms.CountyOwnershipDao;
import gov.ca.cwds.data.cms.CountyTriggerDao;
import gov.ca.cwds.data.cms.CrossReportDao;
import gov.ca.cwds.data.cms.CwsOfficeDao;
import gov.ca.cwds.data.cms.DrmsDocumentDao;
import gov.ca.cwds.data.cms.DrmsDocumentTemplateDao;
import gov.ca.cwds.data.cms.ExternalInterfaceDao;
import gov.ca.cwds.data.cms.GovernmentOrganizationCrossReportDao;
import gov.ca.cwds.data.cms.GovernmentOrganizationDao;
import gov.ca.cwds.data.cms.LawEnforcementDao;
import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.cms.OtherCaseReferralDrmsDocumentDao;
import gov.ca.cwds.data.cms.OtherClientNameDao;
import gov.ca.cwds.data.cms.ReferralAssignmentDao;
import gov.ca.cwds.data.cms.ReferralClientDao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectDao;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectReferralDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.cms.StateIdDao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.cms.TickleDao;
import gov.ca.cwds.data.cms.XaCmsAddressDao;
import gov.ca.cwds.data.dao.contact.ContactPartyDeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.legacy.cms.dao.SexualExploitationTypeDao;
import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.AgencyDao;
import gov.ca.cwds.data.ns.AllegationIntakeDao;
import gov.ca.cwds.data.ns.CsecDao;
import gov.ca.cwds.data.ns.EthnicityDao;
import gov.ca.cwds.data.ns.IntakeLOVCodeDao;
import gov.ca.cwds.data.ns.IntakeLovDao;
import gov.ca.cwds.data.ns.LanguageDao;
import gov.ca.cwds.data.ns.PaperTrailDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.PersonAddressDao;
import gov.ca.cwds.data.ns.PersonDao;
import gov.ca.cwds.data.ns.PersonEthnicityDao;
import gov.ca.cwds.data.ns.PersonLanguageDao;
import gov.ca.cwds.data.ns.PersonPhoneDao;
import gov.ca.cwds.data.ns.PersonRaceDao;
import gov.ca.cwds.data.ns.PhoneNumberDao;
import gov.ca.cwds.data.ns.RaceDao;
import gov.ca.cwds.data.ns.ScreeningAddressDao;
import gov.ca.cwds.data.ns.ScreeningDao;
import gov.ca.cwds.data.ns.XaNsAddressDao;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeDao;
import gov.ca.cwds.data.persistence.cms.CountyTriggerEmbeddable;
import gov.ca.cwds.data.persistence.cms.SystemCodeDaoFileImpl;
import gov.ca.cwds.data.persistence.ns.papertrail.PaperTrailInterceptor;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.ApiConfiguration;
import gov.ca.cwds.rest.ElasticUtils;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.TriggerTablesConfiguration;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.Reminders;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegation;
import gov.ca.cwds.rest.services.referentialintegrity.RIAllegationPerpetratorHistory;
import gov.ca.cwds.rest.services.referentialintegrity.RIAssignment;
import gov.ca.cwds.rest.services.referentialintegrity.RIChildClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientCollateral;
import gov.ca.cwds.rest.services.referentialintegrity.RICrossReport;
import gov.ca.cwds.rest.services.referentialintegrity.RIGovernmentOrganizationCrossReport;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferral;
import gov.ca.cwds.rest.services.referentialintegrity.RIReferralClient;
import gov.ca.cwds.rest.services.referentialintegrity.RIReporter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;

/**
 * Dependency injection (DI) setup for data access objects (DAO).
 *
 * @author CWDS API Team
 * @see ApiSessionFactoryFactory
 */
public class DataAccessModule extends AbstractModule {

  private Map<String, Client> clients;

  private final PaperTrailInterceptor paperTrailInterceptor = new PaperTrailInterceptor();

  // CMS:
  private final ImmutableList<Class<?>> cmsEntities = ImmutableList.<Class<?>>builder()
      .add(gov.ca.cwds.data.legacy.cms.entity.CsecHistory.class,
          gov.ca.cwds.data.legacy.cms.entity.Client.class,
          gov.ca.cwds.data.legacy.cms.entity.ClientOtherEthnicity.class,
          gov.ca.cwds.data.legacy.cms.entity.CountyLicenseCase.class,
          gov.ca.cwds.data.legacy.cms.entity.BackgroundCheck.class,
          gov.ca.cwds.data.legacy.cms.entity.LicensingVisit.class,
          gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome.class,
          gov.ca.cwds.data.legacy.cms.entity.OtherChildrenInPlacementHome.class,
          gov.ca.cwds.data.legacy.cms.entity.OtherPeopleScpRelationship.class,
          gov.ca.cwds.data.legacy.cms.entity.OutOfHomePlacement.class,
          gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck.class,
          gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode.class,
          gov.ca.cwds.data.legacy.cms.entity.PlacementFacilityTypeHistory.class,
          gov.ca.cwds.data.legacy.cms.entity.PlacementHome.class,
          gov.ca.cwds.data.legacy.cms.entity.PlacementHomeNotes.class,
          gov.ca.cwds.data.legacy.cms.entity.PlacementHomeProfile.class,
          gov.ca.cwds.data.legacy.cms.entity.StaffPerson.class,
          gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider.class,
          gov.ca.cwds.data.legacy.cms.entity.syscodes.County.class,
          gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType.class,
          gov.ca.cwds.data.legacy.cms.entity.syscodes.SexualExploitationType.class,
          gov.ca.cwds.data.legacy.cms.entity.syscodes.VisitType.class,
          gov.ca.cwds.data.persistence.cms.Address.class,
          gov.ca.cwds.data.persistence.cms.Allegation.class,
          gov.ca.cwds.data.persistence.cms.ClientAddress.class,
          gov.ca.cwds.data.persistence.cms.ClientCollateral.class,
          gov.ca.cwds.data.persistence.cms.Client.class,
          gov.ca.cwds.data.persistence.cms.CmsDocReferralClient.class,
          gov.ca.cwds.data.persistence.cms.CmsDocument.class,
          gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment.class,
          gov.ca.cwds.data.persistence.cms.CollateralIndividual.class,
          gov.ca.cwds.data.persistence.cms.CrossReport.class,
          gov.ca.cwds.data.persistence.cms.EducationProviderContact.class,
          gov.ca.cwds.data.persistence.cms.EducationProvider.class,
          gov.ca.cwds.data.persistence.cms.OtherAdultInPlacemtHome.class,
          gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome.class,
          gov.ca.cwds.data.persistence.cms.OtherClientName.class,
          gov.ca.cwds.data.persistence.cms.Referral.class,
          gov.ca.cwds.data.persistence.cms.ReferralClient.class,
          gov.ca.cwds.data.persistence.cms.Reporter.class,
          gov.ca.cwds.data.persistence.cms.ServiceProvider.class,
          gov.ca.cwds.data.persistence.cms.StaffPerson.class,
          gov.ca.cwds.data.persistence.cms.StateId.class,
          gov.ca.cwds.data.persistence.cms.SubstituteCareProvider.class,
          gov.ca.cwds.data.persistence.cms.LongText.class,
          gov.ca.cwds.data.persistence.cms.AllegationPerpetratorHistory.class,
          gov.ca.cwds.data.persistence.cms.ClientUc.class,
          gov.ca.cwds.data.persistence.cms.ChildClient.class,
          gov.ca.cwds.data.persistence.cms.Address.class,
          gov.ca.cwds.data.persistence.cms.ClientAddress.class,
          gov.ca.cwds.data.persistence.cms.CountyOwnership.class,
          gov.ca.cwds.data.persistence.cms.CountyTrigger.class,
          gov.ca.cwds.data.persistence.cms.CountyTriggerEmbeddable.class,
          gov.ca.cwds.data.persistence.cms.SystemCode.class,
          gov.ca.cwds.data.persistence.cms.SystemMeta.class,
          gov.ca.cwds.data.persistence.cms.DrmsDocument.class,
          gov.ca.cwds.data.persistence.cms.Assignment.class,
          gov.ca.cwds.data.persistence.cms.AssignmentUnit.class,
          gov.ca.cwds.data.persistence.cms.CwsOffice.class,
          gov.ca.cwds.data.persistence.cms.BaseAssignment.class,
          gov.ca.cwds.data.persistence.cms.ReferralAssignment.class,
          gov.ca.cwds.data.persistence.cms.CaseAssignment.class,
          gov.ca.cwds.data.persistence.cms.CmsCase.class,
          gov.ca.cwds.data.persistence.cms.Tickle.class,
          gov.ca.cwds.data.persistence.cms.ClientRelationship.class,
          gov.ca.cwds.data.persistence.cms.ClientCollateral.class,
          gov.ca.cwds.data.persistence.cms.AddressUc.class,
          gov.ca.cwds.data.persistence.cms.ExternalInterface.class,
          gov.ca.cwds.data.persistence.cms.RelationshipWrapper.class,
          gov.ca.cwds.data.persistence.cms.LawEnforcementEntity.class,
          gov.ca.cwds.data.persistence.cms.CaseLoad.class,
          gov.ca.cwds.data.persistence.cms.StaffPersonCaseLoad.class,
          gov.ca.cwds.data.persistence.cms.ClientScpEthnicity.class,
          gov.ca.cwds.data.persistence.cms.GovernmentOrganizationEntity.class,
          gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate.class,
          gov.ca.cwds.data.persistence.cms.OtherCaseReferralDrmsDocument.class,
          gov.ca.cwds.data.persistence.cms.GovernmentOrganizationCrossReport.class,
          gov.ca.cwds.data.persistence.cms.InjuryHarmDetail.class,
          gov.ca.cwds.data.persistence.cms.InjuryBodyDetail.class,
          gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity.class,
          gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity.class,
          gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity.class,
          gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity.class,
          gov.ca.cwds.data.legacy.cms.entity.SpecialProject.class,
          gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral.class,
          gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies.class,
          gov.ca.cwds.data.legacy.cms.entity.NonCWSNumber.class)
      .build();

  private final ImmutableList<Class<?>> nsEntities = ImmutableList.<Class<?>>builder().add(
      gov.ca.cwds.data.persistence.ns.Person.class, gov.ca.cwds.data.persistence.ns.Address.class,
      gov.ca.cwds.data.persistence.ns.Addresses.class,
      gov.ca.cwds.data.persistence.ns.Allegation.class,
      gov.ca.cwds.data.persistence.ns.AllegationEntity.class,
      gov.ca.cwds.data.persistence.ns.CsecEntity.class,
      gov.ca.cwds.data.persistence.ns.CrossReportEntity.class,
      gov.ca.cwds.data.persistence.ns.GovernmentAgencyEntity.class,
      gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity.class,
      gov.ca.cwds.data.persistence.ns.IntakeLov.class,
      gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity.class,
      gov.ca.cwds.data.persistence.ns.PaperTrail.class,
      gov.ca.cwds.data.persistence.ns.ParticipantEntity.class,
      gov.ca.cwds.data.persistence.ns.ParticipantAddresses.class,
      gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers.class,
      gov.ca.cwds.data.persistence.ns.PersonAddressId.class,
      gov.ca.cwds.data.persistence.ns.PersonAddress.class,
      gov.ca.cwds.data.persistence.ns.PersonPhoneId.class,
      gov.ca.cwds.data.persistence.ns.PhoneNumber.class,
      gov.ca.cwds.data.persistence.ns.PhoneNumbers.class,
      gov.ca.cwds.data.persistence.ns.PersonPhone.class,
      gov.ca.cwds.data.persistence.ns.PersonLanguageId.class,
      gov.ca.cwds.data.persistence.ns.Language.class,
      gov.ca.cwds.data.persistence.ns.PersonLanguage.class,
      gov.ca.cwds.data.persistence.ns.PersonEthnicityId.class,
      gov.ca.cwds.data.persistence.ns.PersonEthnicity.class,
      gov.ca.cwds.data.persistence.ns.Ethnicity.class,
      gov.ca.cwds.data.persistence.ns.PersonRaceId.class,
      gov.ca.cwds.data.persistence.ns.PersonRace.class, gov.ca.cwds.data.persistence.ns.Race.class,
      gov.ca.cwds.data.persistence.ns.Relationship.class,
      gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity.class,
      gov.ca.cwds.data.persistence.ns.ScreeningEntity.class,
      gov.ca.cwds.data.persistence.ns.ScreeningAddressEntity.class,
      gov.ca.cwds.data.persistence.ns.ScreeningWrapper.class).build();

  private final HibernateBundle<ApiConfiguration> cmsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(cmsEntities, new ApiSessionFactoryFactory()) {

        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getCmsDataSourceFactory();
        }

        @Override
        public String name() {
          return "cms";
        }
      };

  private final HibernateBundle<ApiConfiguration> nsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(nsEntities,
          new FerbSessionFactoryFactory<>(paperTrailInterceptor)) {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getNsDataSourceFactory();
        }

        @Override
        public String name() {
          return "ns";
        }
      };

  private final HibernateBundle<ApiConfiguration> rsHibernateBundle =
      new HibernateBundle<ApiConfiguration>(ImmutableList.of(), new ApiSessionFactoryFactory()) {
        @Override
        public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getRsDataSourceFactory();
        }

        @Override
        public String name() {
          return "rs";
        }
      };

  /**
   * XA pooled datasource factory for CMS DB2.
   */
  private final FerbHibernateBundle xaCmsHibernateBundle =
      new FerbHibernateBundle(cmsEntities, new ApiSessionFactoryFactory()) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getXaCmsDataSourceFactory();
        }

        @Override
        public String name() {
          return "xa_cms";
        }
      };

  /**
   * XA pooled datasource factory for NS PostgreSQL.
   */
  private final FerbHibernateBundle xaNsHibernateBundle =
      new FerbHibernateBundle(nsEntities, new FerbSessionFactoryFactory<>(paperTrailInterceptor)) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
          return configuration.getXaNsDataSourceFactory();
        }

        @Override
        public String name() {
          return "xa_ns";
        }
      };

  /**
   * Constructor takes the API configuration.
   *
   * @param bootstrap the ApiConfiguration
   */
  public DataAccessModule(Bootstrap<ApiConfiguration> bootstrap) {
    bootstrap.addBundle(cmsHibernateBundle);
    bootstrap.addBundle(nsHibernateBundle);
    bootstrap.addBundle(rsHibernateBundle);
    bootstrap.addBundle(xaCmsHibernateBundle);
    bootstrap.addBundle(xaNsHibernateBundle);
  }

  /**
   * {@inheritDoc}
   *
   * @see com.google.inject.AbstractModule#configure()
   */
  @Override
  protected void configure() {
    // CMS:
    // CmsReferral participants:
    bind(AllegationDao.class);
    bind(ClientDao.class);
    bind(ReferralClientDao.class);
    bind(ReferralDao.class);
    bind(ReporterDao.class);
    bind(CrossReportDao.class);
    bind(CaseDao.class);
    bind(ReferralAssignmentDao.class);
    bind(CaseAssignmentDao.class);
    bind(ClientRelationshipDao.class);
    bind(ClientCollateralDao.class);

    bind(AttorneyDao.class);
    bind(CmsDocReferralClientDao.class);
    bind(CmsDocumentDao.class);
    bind(OtherClientNameDao.class);
    bind(StaffPersonDao.class);
    bind(StateIdDao.class);
    bind(LongTextDao.class);
    bind(AllegationPerpetratorHistoryDao.class);
    bind(ClientUcDao.class);
    bind(ChildClientDao.class);
    bind(SexualExploitationTypeDao.class);
    bind(SystemCodeDao.class);
    bind(SystemMetaDao.class);
    bind(DrmsDocumentDao.class);
    bind(DrmsDocumentTemplateDao.class);
    bind(OtherCaseReferralDrmsDocumentDao.class);
    bind(AssignmentDao.class);
    bind(AssignmentUnitDao.class);
    bind(CwsOfficeDao.class);
    bind(TickleDao.class);
    bind(AddressUcDao.class);
    bind(ExternalInterfaceDao.class);
    bind(DeliveredServiceDao.class);
    bind(ContactPartyDeliveredServiceDao.class);
    bind(ReferralClientDeliveredServiceDao.class);
    bind(IndividualDeliveredServiceDao.class);
    bind(LawEnforcementDao.class);
    bind(CaseLoadDao.class);
    bind(ClientScpEthnicityDao.class);
    bind(GovernmentOrganizationDao.class);
    bind(GovernmentOrganizationCrossReportDao.class);
    bind(XaCmsAddressDao.class);
    bind(SpecialProjectDao.class);
    bind(SpecialProjectReferralDao.class);

    // NS:
    bind(AddressDao.class);
    bind(AddressesDao.class);
    bind(CsecDao.class);
    bind(XaNsAddressDao.class);

    bind(PersonDao.class);
    bind(ScreeningDao.class);
    bind(ScreeningAddressDao.class);
    bind(AgencyDao.class);
    bind(gov.ca.cwds.data.ns.CrossReportDao.class);
    bind(AllegationIntakeDao.class);
    bind(ParticipantDao.class);
    bind(PhoneNumberDao.class);
    bind(LanguageDao.class);
    bind(PersonAddressDao.class);
    bind(PersonPhoneDao.class);
    bind(PersonLanguageDao.class);
    bind(PersonEthnicityDao.class);
    bind(EthnicityDao.class);
    bind(PersonRaceDao.class);
    bind(RaceDao.class);
    bind(IntakeLOVCodeDao.class);
    bind(IntakeLovDao.class);
    bind(PaperTrailDao.class);
    bind(PaperTrailInterceptor.class);

    // Trigger Tables:
    bind(CountyOwnershipDao.class);
    bind(CountyTriggerDao.class);
    bind(NonLACountyTriggers.class);
    bind(LACountyTrigger.class);
    bind(TriggerTablesDao.class);
    bind(CountyTriggerEmbeddable.class);

    // Downstream Tables:
    bind(Reminders.class);

    // System code loader DAO:
    bind(ApiSystemCodeDao.class).to(SystemCodeDaoFileImpl.class);

    // Referential integrity:
    bind(RIClientCollateral.class);
    bind(RIChildClient.class);
    bind(RIAllegationPerpetratorHistory.class);
    bind(RIAssignment.class);
    bind(RIClientAddress.class);
    bind(RIAllegation.class);
    bind(RICrossReport.class);
    bind(RIReporter.class);
    bind(RIReferral.class);
    bind(RIReferralClient.class);
    bind(RIGovernmentOrganizationCrossReport.class);
  }

  @Provides
  @CmsSessionFactory
  public SessionFactory cmsSessionFactory() {
    return cmsHibernateBundle.getSessionFactory();
  }

  @Provides
  @NsSessionFactory
  public SessionFactory nsSessionFactory() {
    return nsHibernateBundle.getSessionFactory();
  }

  @Provides
  @CwsRsSessionFactory
  public SessionFactory rsSessionFactory() {
    return rsHibernateBundle.getSessionFactory();
  }

  @Provides
  @CmsHibernateBundle
  public HibernateBundle<ApiConfiguration> cmsHibernateBundle() {
    return cmsHibernateBundle;
  }

  @Provides
  @NsHibernateBundle
  public HibernateBundle<ApiConfiguration> nsHibernateBundle() {
    return nsHibernateBundle;
  }

  @Provides
  @CwsRsHibernateBundle
  public HibernateBundle<ApiConfiguration> rsHibernateBundle() {
    return rsHibernateBundle;
  }

  @Provides
  @XaCmsHibernateBundle
  public FerbHibernateBundle getXaCmsHibernateBundle() {
    return xaCmsHibernateBundle;
  }

  @Provides
  @XaNsSessionFactory
  public SessionFactory xaNsSessionFactory() {
    return xaNsHibernateBundle.getSessionFactory();
  }

  @Provides
  @XaCmsSessionFactory
  public SessionFactory xaCmsSessionFactory() {
    return xaCmsHibernateBundle.getSessionFactory();
  }

  @Provides
  @XaNsHibernateBundle
  public FerbHibernateBundle getXaNsHibernateBundle() {
    return xaNsHibernateBundle;
  }

  @Provides
  public Map<String, ElasticsearchConfiguration> elasticSearchConfigs(
      ApiConfiguration apiConfiguration) {
    return apiConfiguration.getElasticsearchConfigurations();
  }

  @Provides
  public TriggerTablesConfiguration triggerTablesConfiguration(ApiConfiguration apiConfiguration) {
    return apiConfiguration.getTriggerTablesConfiguration();
  }

  @Provides
  @Named("elasticsearch.daos")
  public synchronized Map<String, ElasticsearchDao> provideElasticSearchDaos(
      ApiConfiguration apiConfiguration) {
    if (clients == null) {
      provideElasticsearchClients(apiConfiguration);
    }

    Map<String, ElasticsearchDao> esDaos = new HashMap<>();
    for (Map.Entry<String, Client> esKey : clients.entrySet()) {
      ElasticsearchConfiguration config =
          apiConfiguration.getElasticsearchConfigurations().get(esKey.getKey());
      ElasticsearchDao dao = new ElasticsearchDao(esKey.getValue(), config);
      esDaos.put(esKey.getKey(), dao);
    }
    return esDaos;
  }

  @Provides
  @Named("screenings.index")
  public ElasticsearchDao provideElasticSearchDaoScreenings(
      @Named("elasticsearch.daos") Map<String, ElasticsearchDao> esDaos) {
    return esDaos.get("screeningsIndex");
  }

  private synchronized Map<String, Client> makeElasticsearchClients(
      ApiConfiguration apiConfiguration) {
    if (clients == null) {
      clients = new ConcurrentHashMap<>();
      Map<String, ElasticsearchConfiguration> esConfigs =
          apiConfiguration.getElasticsearchConfigurations();

      for (Map.Entry<String, ElasticsearchConfiguration> esConfigKey : esConfigs.entrySet()) {
        ElasticsearchConfiguration config = esConfigs.get(esConfigKey.getKey());
        TransportClient transportClient = ElasticUtils.buildElasticsearchClient(config);
        clients.put(esConfigKey.getKey(), transportClient);
      }
    }

    return clients;
  }

  @Provides
  public synchronized Map<String, Client> provideElasticsearchClients(
      ApiConfiguration apiConfiguration) {
    if (clients == null) {
      makeElasticsearchClients(apiConfiguration);
    }

    return clients;
  }

  public PaperTrailInterceptor getPaperTrailInterceptor() {
    return paperTrailInterceptor;
  }

}
