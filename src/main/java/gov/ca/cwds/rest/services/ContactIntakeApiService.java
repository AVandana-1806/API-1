package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.ns.ContactDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.persistence.ns.ContactEntity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.contact.PostedContactIntake;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactIntake;
import gov.ca.cwds.rest.services.mapper.ContactIntakeMapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Set;

public class ContactIntakeApiService implements TypedCrudsService<String, ContactIntake, Response>{

  @Inject
  private ContactDao contactDao;

  @Inject
  private ParticipantDao participantDao;

  public ContactIntakeApiService(ContactDao contactDao, ParticipantDao participantDao) {
    super();
    this.contactDao = contactDao;
    this.participantDao = participantDao;
  }

  @Override
  public Response create(ContactIntake request) {
    ContactEntity entity = ContactIntakeMapper.INSTANCE.map(request);
    PostedContactIntake response = ContactIntakeMapper.INSTANCE.map(contactDao.create(entity));
    Set<String> participantIds = participantDao.findLegacyIdListByScreeningId(request.getScreeningId());
    response.setPeopleIds(participantIds);
    return response;
  }

  public ContactIntakeApiService() {
    super();
  }

  @Override
  public Response find(String s) {
    throw new NotImplementedException("Find is not implemented");
  }

  @Override
  public Response delete(String s) {
    throw new NotImplementedException("Delete is not implemented");
  }

  @Override
  public Response update(String s, ContactIntake contactIntake) {
    throw new NotImplementedException("Update is not implemented");
  }
}
