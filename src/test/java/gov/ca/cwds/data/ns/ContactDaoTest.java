package gov.ca.cwds.data.ns;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.ns.ContactEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class ContactDaoTest implements DaoTestTemplate {

  /**
   *
   */
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static ContactDao contactDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @SuppressWarnings("javadoc")
  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
    contactDao = new ContactDao(sessionFactory);
  }

  @SuppressWarnings("javadoc")
  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Override
  @Before
  public void setup() {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @Override
  @After
  public void teardown() {
    session.getTransaction().rollback();
  }

  @Override
  @Test
  public void testCreate() {
    ContactEntity entity = new ContactEntity(
            "1",
            LocalDateTime.now(),
            LocalDateTime.now(),
            "purpose",
            "commMethod",
            "C",
            1,
            "Location",
            "Note");
    ContactEntity created = contactDao.create(entity);
    assertThat(created, is(entity));
  }

  @Override
  @Test
  public void testFindAllNamedQueryExist() throws Exception {
    Query query = session.getNamedQuery("gov.ca.cwds.rest.api.persistence.ns.ContactEntity.findAll");
    assertThat(query, is(notNullValue()));
  }

  @Override
  public void testFindAllReturnsCorrectList() throws Exception {
    // TODO Auto-generated method stub
  }

  @Override
  public void testFind() throws Exception {
    // TODO Auto-generated method stub
  }

  @Override
  @Test
  public void testFindEntityNotFoundException() throws Exception {
    Integer id = 99;
    ContactEntity found = contactDao.find(id);
    assertThat(found, is(nullValue()));
  }

  @Override
  public void testCreateExistingEntityException() throws Exception {
    // TODO Auto-generated method stub
  }

  @Override
  public void testDelete() throws Exception {
      // TODO Auto-generated method stub
  }

  @Override
  @Test
  public void testDeleteEntityNotFoundException() throws Exception {
    ContactEntity updated = contactDao.delete(99);
    assertThat(updated, is(nullValue()));
  }

  @Override
  public void testUpdate() throws Exception {
    // TODO Auto-generated method stub
  }

  @Override
  public void testUpdateEntityNotFoundException() throws Exception {
    // TODO Auto-generated method stub
  }
}
