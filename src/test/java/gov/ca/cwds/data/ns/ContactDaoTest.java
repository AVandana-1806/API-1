package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDateTime;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.persistence.ns.ContactEntity;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class ContactDaoTest extends Doofenshmirtz<ContactEntity> {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ContactDao contactDao;


  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    contactDao = new ContactDao(sessionFactory);
  }

  @Test
  public void testCreate() {
    ContactEntity entity = new ContactEntity("1", LocalDateTime.now(), LocalDateTime.now(),
        "purpose", "commMethod", "C", 1, "Location", "Note");
    ContactEntity created = contactDao.create(entity);
    assertThat(created, is(entity));
  }

  @Test
  public void testFindAllNamedQueryExist() throws Exception {
    Query query =
        session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.ContactEntity.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testFindEntityNotFoundException() throws Exception {
    Integer id = 99;
    ContactEntity found = contactDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ContactEntity updated = contactDao.delete(99);
    assertThat(updated, is(nullValue()));
  }

}
