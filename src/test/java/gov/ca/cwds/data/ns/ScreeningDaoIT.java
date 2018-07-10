package gov.ca.cwds.data.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.persistence.ns.ScreeningWrapper;

public class ScreeningDaoIT {

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private static ScreeningDao screeningDao;
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeClass
  public static void beforeClass() {
    sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
    screeningDao = new ScreeningDao(sessionFactory);
  }

  @AfterClass
  public static void afterClass() {
    sessionFactory.close();
  }

  @Before
  public void setup() throws Exception {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
  }

  @After
  public void teardown() throws Exception {
    session.getTransaction().rollback();
  }

  @Test
  public void testThatFindScreeningsByUserIdQueryExist() throws Exception {

    Query<ScreeningWrapper> query = session
        .getNamedQuery("gov.ca.cwds.data.persistence.ns.ScreeningWrapper.findScreeningsOfUser");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testThatFindScreeningsByClientIdsExist() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.data.persistence.ns.ScreeningEntity.findScreeningsByClientIds");
    assertThat(query, is(notNullValue()));
  }

  @Test
  public void testThatFindScreeningByUserIdExist() throws Exception {
    Query query = session
        .getNamedQuery("gov.ca.cwds.data.persistence.ns.ScreeningWrapper.findScreeningsOfUser");
    assertThat(query, is(notNullValue()));
  }

  public void testFind() throws Exception {
    // TODO Auto-generated method stub

  }

  public void testFindEntityNotFoundException() throws Exception {
    // TODO Auto-generated method stub

  }

  public void testCreate() throws Exception {
    // TODO Auto-generated method stub

  }

  public void testCreateExistingEntityException() throws Exception {
    // TODO Auto-generated method stub

  }

  public void testDelete() throws Exception {
    // TODO Auto-generated method stub

  }

  public void testDeleteEntityNotFoundException() throws Exception {
    // TODO Auto-generated method stub

  }

  public void testUpdate() throws Exception {
    // TODO Auto-generated method stub

  }

  public void testUpdateEntityNotFoundException() throws Exception {
    // TODO Auto-generated method stub

  }

}
