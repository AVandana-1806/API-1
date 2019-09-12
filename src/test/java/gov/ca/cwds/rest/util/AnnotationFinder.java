package gov.ca.cwds.rest.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.legacy.cms.entity.LongText;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.rest.api.domain.cms.PostedClientRelationship;
import gov.ca.cwds.rest.resources.ClientRelationshipResource;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * Convenience methods to find annotations by class and method. s *
 * <p>
 * Note that class annotations are not normally inherited by subclasses.
 * </p>
 * 
 * @author CWDS API Team
 */
public class AnnotationFinder {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationFinder.class);

  private final Map<Class<?>, String> entityTables = new ConcurrentHashMap<>();

  /**
   * Find table name from annotation {@link Table}.
   * 
   * @param klazz class to check
   * @return table name. Empty value means no table name found.
   */
  public String findTableName(Class<?> klazz) {
    String ret = null;

    if (entityTables.containsKey(klazz)) {
      ret = entityTables.get(klazz);
    } else {
      final Table table = findClassAnnotation(klazz, Table.class);
      ret = table != null ? table.name() : "";
      LOGGER.info("table: class: {}, table name: \"{}\"", klazz, ret);
      entityTables.put(klazz, ret);
    }

    return ret;
  }

  public <A extends Annotation> A findClassAnnotation(Class<?> klazz, Class<A> ann) {
    final A[] annos = klazz.getAnnotationsByType(ann);
    return annos != null && annos.length > 0 ? annos[0] : null;
  }

  public <A extends Annotation> A findMethodAnnotation(Class<?> klazz, Class<A> ann,
      Method method) {
    return method.getAnnotation(ann);
  }

  public <A extends Annotation> A findMethodAnnotation(Class<?> klazz, Class<A> ann,
      String methodName) {
    Method method = null;
    for (Method m : klazz.getMethods()) {
      if (m.getName().equals(methodName)) {
        method = m;
        break;
      }
    }

    return method != null ? method.getAnnotation(ann) : null;
  }

  public static void main(String[] args) throws Exception {
    final AnnotationFinder annoFinder = new AnnotationFinder();

    // Find table name by persistence class:
    final Class<?>[] klazzes = {Client.class, Allegation.class, ClientAddress.class, LongText.class,
        gov.ca.cwds.data.persistence.cms.LongText.class, Address.class,
        gov.ca.cwds.data.legacy.cms.entity.Address.class,
        gov.ca.cwds.data.persistence.ns.Address.class, String.class, ClientCollateral.class,
        PostedClientRelationship.class};
    for (Class<?> klazz : klazzes) {
      annoFinder.findTableName(klazz);
    }

    // Find data source from @UnitOfWork:
    final UnitOfWork uow =
        annoFinder.findMethodAnnotation(ClientRelationshipResource.class, UnitOfWork.class, "get");
    LOGGER.info("data source: \"{}\"", uow.value());
  }

}
