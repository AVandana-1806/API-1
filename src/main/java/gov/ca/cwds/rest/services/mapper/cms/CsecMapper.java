package gov.ca.cwds.rest.services.mapper.cms;

import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.rest.api.domain.Csec;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
@SuppressWarnings("squid:S1214")
public interface CsecMapper {
  CsecMapper INSTANCE = Mappers.getMapper(CsecMapper.class);

  @Mappings({
    @Mapping(target = "participantId", source = "childClient"),
    @Mapping(target = "csecCodeId", source = "sexualExploitationType.systemId"),
    @Mapping(target = "startDate", source = "startDate"),
    @Mapping(target = "endDate", source = "endDate")
  })
  Csec map(CsecHistory csec);

  @InheritInverseConfiguration
  List<Csec> toDomain(List<CsecHistory> csecEntities);

}
