package gov.ca.cwds.data.ns;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import gov.ca.cwds.data.junit.template.DaoTestTemplate;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.Relationship;

import java.util.Date;

import gov.ca.cwds.data.persistence.ns.ScreeningEntity;
import gov.ca.cwds.fixture.ParticipantEntityBuilder;
import gov.ca.cwds.fixture.ScreeningEntityBuilder;
import org.apache.commons.lang3.NotImplementedException;
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

public class RelationshipDaoIT implements DaoTestTemplate {

    private static RelationshipDao relationshipDao;
    private static SessionFactory sessionFactory;
    private static ParticipantDao participantDao;
    private static ScreeningDao screeningDao;
    private static String screeningId;
    private Session session;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("javadoc")
    @BeforeClass
    public static void beforeClass() {
        System.out.println(System.getenv("DB_NS_JDBC_URL"));
        System.out.println(System.getenv("DB_NS_PASSWORD"));
        sessionFactory = new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
        relationshipDao = new RelationshipDao(sessionFactory);
        participantDao = new ParticipantDao(sessionFactory);
        screeningDao = new ScreeningDao(sessionFactory);
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
        ScreeningEntity screeningEntity =new ScreeningEntityBuilder().build();
        screeningEntity = screeningDao.create(screeningEntity);
        screeningId = screeningEntity.getId();
        ParticipantEntity participant1 = new ParticipantEntityBuilder().setScreeningEntity(screeningEntity).build();
        ParticipantEntity participant2 = new ParticipantEntityBuilder().setScreeningEntity(screeningEntity).build();
        participant1 = participantDao.create(participant1);
        participant2 = participantDao.create(participant2);
        Relationship existingRelationship = new Relationship(null, participant1.getId(), participant2.getId(), 190,
                new Date(), new Date(), true, false, "111111", new Date(), new Date());
    }

    @Override
    @After
    public void teardown() {
        session.getTransaction().rollback();
    }

    @Override
    public void testFindAllNamedQueryExist() throws Exception {
        throw new NotImplementedException(
                "Test not implemented until Entity implements find all method");
    }

    @Override
    public void testFindAllReturnsCorrectList() throws Exception {
        throw new NotImplementedException(
                "Test not implemented until Entity implements find all method");
    }

    @Override
    public void testFind() throws Exception {
        throw new NotImplementedException("Test not implemented until Entity implements find method");
    }

    @Override
    public void testFindEntityNotFoundException() throws Exception {
        throw new NotImplementedException("Test not implemented until Entity implements find method");
    }

    @Test
    @Override
    public void testCreate() throws Exception {
        Relationship relationship = new Relationship(null, "ClientId", "RelationId", 190, new Date(),
                new Date(), true, false, "11111", new Date(),  new Date());
        Relationship created = relationshipDao.create(relationship);
        assertThat(created, is(relationship));
        assertNotNull(created.getId());
    }

    @Override
    public void testCreateExistingEntityException() throws Exception {
        throw new NotImplementedException("Test not implemented ");
    }

    @Override
    public void testDelete() throws Exception {
        throw new NotImplementedException("Test not implemented until Entity implements delete method");
    }

    @Override
    public void testDeleteEntityNotFoundException() throws Exception {
        throw new NotImplementedException("Test not implemented until Entity implements delete method");
    }

    @Override
    public void testUpdate() throws Exception {
        throw new NotImplementedException("Test not implemented until Entity implements update method");
    }

    @Override
    public void testUpdateEntityNotFoundException() throws Exception {
        throw new NotImplementedException("Test not implemented until Entity implements update method");
    }
}

