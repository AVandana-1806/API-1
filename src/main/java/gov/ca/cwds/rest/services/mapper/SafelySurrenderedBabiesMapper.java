package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.rest.api.domain.SafelySurrenderedBabies;
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

  SafelySurrenderedBabiesIntakeApi map(SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity);

  @Mapping(target = "surrenderedByName", source = "surrenderedBy")
  @Mapping(target = "braceletInfoCode", source = "parentGuardGivenBraceletId")
  @Mapping(target = "medicalQuestionaireCode", source = "parentGuardProvMedQuestion")
  @Mapping(target = "medicalQuestionaireReturnDate", source = "medQuestionaireReturnDate")
  SafelySurrenderedBabies mapToScreeningToReferral(SafelySurrenderedBabiesIntakeApi safelySurrenderedBabiesIntakeApi);

  @InheritInverseConfiguration
  SafelySurrenderedBabiesEntity map(SafelySurrenderedBabiesIntakeApi safelySurrenderedBabies);

  @InheritInverseConfiguration
  SafelySurrenderedBabiesEntity map(SafelySurrenderedBabiesIntakeApi safelySurrenderedBabies,
      @MappingTarget SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity);

}
