package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.ns.IntakeLov;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class IntakeLovDaoTest extends Doofenshmirtz<Client> {

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
  }

  @Test
  public void type() throws Exception {
    assertThat(IntakeLovDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    IntakeLovDao target = new IntakeLovDao(sessionFactory);
    assertThat(target, notNullValue());
  }

  @Test
  public void findAll_Args__() throws Exception {
    SessionFactory sessionFactory = mock(SessionFactory.class);
    Session session = mock(Session.class);
    Transaction tx = mock(Transaction.class);
    Query query = mock(Query.class);

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.getNamedQuery(any(String.class))).thenReturn(query);
    when(session.beginTransaction()).thenReturn(tx);
    IntakeLov intakeLov = new IntakeLov(1251L, "lang_tpc", "Cambodian", "19", false, "LANG_TPC", "",
        null, "language", "Cambodian", "Cambodian");
    when(query.list()).thenReturn(Arrays.asList(intakeLov));
    when(query.setParameter(any(String.class), any(String.class))).thenReturn(query);
    IntakeLovDao target = new IntakeLovDao(sessionFactory);
    List<IntakeLov> response = target.findByLegacyMetaId("LANG_TPC");
    assertThat(response, notNullValue());
  }

}
