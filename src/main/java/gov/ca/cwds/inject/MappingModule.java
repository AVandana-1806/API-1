package gov.ca.cwds.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

import gov.ca.cwds.cms.data.access.mapper.ClientMapper;
import gov.ca.cwds.cms.data.access.mapper.ClientMapperImpl;
import gov.ca.cwds.rest.services.mapper.AddressMapper;
import gov.ca.cwds.rest.services.mapper.AgencyMapper;
import gov.ca.cwds.rest.services.mapper.AllegationMapper;
import gov.ca.cwds.rest.services.mapper.AllegationTypeMapper;
import gov.ca.cwds.rest.services.mapper.ContactIntakeMapper;
import gov.ca.cwds.rest.services.mapper.CrossReportMapper;
import gov.ca.cwds.rest.services.mapper.CsecMapper;
import gov.ca.cwds.rest.services.mapper.LegacyDescriptorMapper;
import gov.ca.cwds.rest.services.mapper.RelationshipMapper;
import gov.ca.cwds.rest.services.mapper.SafelySurrenderedBabiesMapper;
import gov.ca.cwds.rest.services.mapper.ScreeningMapper;

/**
 * CWDS API Team
 */
public class MappingModule extends AbstractModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessModule.class);

  @Override
  protected void configure() {
    LOGGER.warn("MappingModule: configure");
    bind(AddressMapper.class).to(AddressMapper.INSTANCE.getClass()).asEagerSingleton();
    bind(AgencyMapper.class).to(AgencyMapper.INSTANCE.getClass()).asEagerSingleton();
    bind(AllegationMapper.class).to(AllegationMapper.INSTANCE.getClass()).asEagerSingleton();
    bind(AllegationTypeMapper.class).to(AllegationTypeMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(ClientMapper.class).to(ClientMapperImpl.INSTANCE.getClass()).asEagerSingleton();
    bind(ContactIntakeMapper.class).to(ContactIntakeMapper.INSTANCE.getClass()).asEagerSingleton();
    bind(CsecMapper.class).to(CsecMapper.INSTANCE.getClass()).asEagerSingleton();
    bind(CrossReportMapper.class).to(CrossReportMapper.INSTANCE.getClass()).asEagerSingleton();
    bind(LegacyDescriptorMapper.class).to(LegacyDescriptorMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(RelationshipMapper.class).to(RelationshipMapper.INSTANCE.getClass()).asEagerSingleton();
    bind(SafelySurrenderedBabiesMapper.class).to(SafelySurrenderedBabiesMapper.INSTANCE.getClass())
        .asEagerSingleton();
    bind(ScreeningMapper.class).to(ScreeningMapper.INSTANCE.getClass()).asEagerSingleton();
  }

}
