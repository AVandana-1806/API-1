package gov.ca.cwds.es.live;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;

/**
 * Test DAO.
 */
public class TestNormalizedEntityDao extends BaseDaoImpl<TestNormalizedEntity> {

  @Inject
  public TestNormalizedEntityDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
