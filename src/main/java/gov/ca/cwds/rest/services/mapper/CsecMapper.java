package gov.ca.cwds.rest.services.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * CWDS API Team
 */
import gov.ca.cwds.data.persistence.ns.CsecEntity;
import gov.ca.cwds.rest.api.domain.Csec;

@Mapper
@SuppressWarnings("squid:S1214")
public interface CsecMapper {
  CsecMapper INSTANCE = Mappers.getMapper(CsecMapper.class);

  @Mapping(target = "participantId", source = "participantId", ignore = true)
  CsecEntity map(Csec csec);

  @Mapping(target = "participantId", source = "participantId", ignore = true)
  Csec map(CsecEntity csecEntity);

  CsecEntity update(@MappingTarget CsecEntity csecEntity, Csec csec);

  List<Csec> toDomain(List<CsecEntity> csecEntities);

  @InheritInverseConfiguration
  List<CsecEntity> toPersistence(List<Csec> csecs);
}
