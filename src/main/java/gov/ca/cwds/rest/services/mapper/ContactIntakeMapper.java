package gov.ca.cwds.rest.services.mapper;

import gov.ca.cwds.data.persistence.ns.ContactEntity;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactIntake;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactIntakeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@SuppressWarnings("squid:S1214")
public interface ContactIntakeMapper {

  ContactIntakeMapper INSTANCE = Mappers.getMapper(ContactIntakeMapper.class);

  ContactIntake map(ContactEntity contactEntity);

  ContactEntity map(ContactIntakeRequest contactIntakeRequest);
}
