package gov.ca.cwds.data.persistence.xa;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.hibernate.UnitOfWorkAspect;

public class CaresUnitOfWorkAspect extends UnitOfWorkAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(CaresUnitOfWorkAspect.class);

  public CaresUnitOfWorkAspect(Map<String, SessionFactory> sessionFactories) {
    super(sessionFactories);
    LOGGER.debug("CaresUnitOfWorkAspect.ctor");
  }

  @Override
  public Session getSession() {
    return super.getSession();
  }

  @Override
  public SessionFactory getSessionFactory() {
    return super.getSessionFactory();
  }

}
