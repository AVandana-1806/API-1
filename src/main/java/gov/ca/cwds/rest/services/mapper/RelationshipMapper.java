package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.enums.SameHomeStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@FunctionalInterface
@Mapper(imports = {
    SameHomeStatus.class
})
@SuppressWarnings("squid:S1214")
public interface RelationshipMapper {
  RelationshipMapper INSTANCE = Mappers.getMapper(RelationshipMapper.class);

  @Mapping(expression = "java(SameHomeStatus.fromValue(relationship.getSameHomeStatus()))", target = "sameHomeStatus")
  ScreeningRelationship map(Relationship relationship);
}
