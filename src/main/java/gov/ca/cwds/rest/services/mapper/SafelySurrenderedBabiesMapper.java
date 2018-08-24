package gov.ca.cwds.rest.services.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity;
import gov.ca.cwds.rest.api.domain.SafelySurrenderedBabiesIntakeApi;

@Mapper
@SuppressWarnings("squid:S1214")
public interface SafelySurrenderedBabiesMapper {
  SafelySurrenderedBabiesMapper INSTANCE = Mappers.getMapper(SafelySurrenderedBabiesMapper.class);

  @Mapping(target = "parentGuardGivenBraceletId",
      expression = "java(safelySurrenderedBabiesEntity.getParentGuardGivenBraceletId() == \"U\"? null : safelySurrenderedBabiesEntity.getParentGuardGivenBraceletId())")
  @Mapping(target = "parentGuardProvMedQuestion",
      expression = "java(safelySurrenderedBabiesEntity.getParentGuardProvMedQuestion() == \"U\"? null : safelySurrenderedBabiesEntity.getParentGuardProvMedQuestion())")
  @Mapping(target = "relationToChild",
      expression = "java(safelySurrenderedBabiesEntity.getRelationToChild() == \"\"? null : safelySurrenderedBabiesEntity.getRelationToChild())")
  SafelySurrenderedBabiesIntakeApi map(SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity);

  @InheritInverseConfiguration
  @Mapping(target = "parentGuardGivenBraceletId", defaultValue = "U")
  @Mapping(target = "parentGuardProvMedQuestion", defaultValue = "U")
  @Mapping(target = "relationToChild", defaultValue = "")
  SafelySurrenderedBabiesEntity map(SafelySurrenderedBabiesIntakeApi safelySurrenderedBabies);

  @InheritInverseConfiguration
  @Mapping(target = "parentGuardGivenBraceletId", defaultValue = "U")
  @Mapping(target = "parentGuardProvMedQuestion", defaultValue = "U")
  @Mapping(target = "relationToChild", defaultValue = "")
  SafelySurrenderedBabiesEntity map(SafelySurrenderedBabiesIntakeApi safelySurrenderedBabies,
      @MappingTarget SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity);

}
