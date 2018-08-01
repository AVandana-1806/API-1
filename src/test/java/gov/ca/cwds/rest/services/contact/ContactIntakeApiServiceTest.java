package gov.ca.cwds.rest.services.contact;

import gov.ca.cwds.data.ns.ContactDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.persistence.ns.ContactEntity;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactIntake;
import gov.ca.cwds.rest.services.ContactIntakeApiService;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactIntakeApiServiceTest {

  private ContactIntakeApiService service;
  private ContactDao contactDao;
  private ContactEntity entity;
  private ContactIntake contactRequest;
  private ParticipantDao participantDao;

  @Before
  public void setup(){
    contactDao = mock(ContactDao.class);
    participantDao = mock(ParticipantDao.class);
    service = new ContactIntakeApiService(contactDao, participantDao);

    entity = new ContactEntity();
    entity.setScreeningId("1");
    entity.setStartedAt(LocalDateTime.now());
    entity.setEndedAt(LocalDateTime.now());
    entity.setPurpose("purpose");
    entity.setCommunicationMethod("commMethod");
    entity.setStatus("C");
    entity.setService(1);
    entity.setLocation("Location");
    entity.setNote("Note");

    contactRequest = new ContactIntake(
            "1",
            LocalDateTime.now(),
            LocalDateTime.now(),
            "purpose",
            "commMethod",
            "C",
            1,
            "Location",
            "Note");
  }

  @Test
  public void shouldSaveRelationshipWhenCreateIsCalled(){
    when(contactDao.create(any())).thenReturn(entity);
    when(participantDao.findLegacyIdListByScreeningId(any())).thenReturn(anySet());
    service.create(contactRequest);
    verify(contactDao).create(isA(ContactEntity.class));
  }

  @Test(expected = NotImplementedException.class)
  public void shouldNotImplementFind() {
    service.find("1");
  }

  @Test(expected = NotImplementedException.class)
  public void shouldNotImplementUpdate() {
    service.update("1", contactRequest);
  }

  @Test(expected = NotImplementedException.class)
  public void shouldNotImplementDelete() {
    service.delete("1");
  }

}
