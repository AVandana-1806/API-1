package gov.ca.cwds;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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

public class AnnotationFinder {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationFinder.class);

  private final Map<Class<?>, String> entityTables = new ConcurrentHashMap<>();

  public static void main(String[] args) throws Exception {
    final AnnotationFinder scanner = new AnnotationFinder();

    // Scan method:
    scanner.check(Class2.class.getMethod("num", new Class[0]));

    // Scan class:
    final Class<?>[] klazzes = {Client.class, Allegation.class, ClientAddress.class, LongText.class,
        gov.ca.cwds.data.persistence.cms.LongText.class, Address.class,
        gov.ca.cwds.data.legacy.cms.entity.Address.class,
        gov.ca.cwds.data.persistence.ns.Address.class, String.class};
    for (Class<?> klazz : klazzes) {
      scanner.findTableName(klazz);
    }

  }

  public String findTableName(Class<?> klazz) {
    String ret = null;

    if (entityTables.containsKey(klazz)) {
      ret = entityTables.get(klazz);
    } else {
      final Table table = klazz.getDeclaredAnnotation(Table.class);
      ret = table != null ? table.name() : "NOT AN ENTITY";
      LOGGER.info("table: class: {}, table name: \"{}\"", klazz, ret);
      entityTables.put(klazz, ret);
    }

    return ret;
  }

  public void check(Method invokedMethod) {
    Class<?> type = invokedMethod.getDeclaringClass();
    while (type != null) {
      for (Annotation annotation : type.getDeclaredAnnotations()) {
        System.out.println(annotation.toString());
      }
      type = type.getSuperclass();
    }
  }
}


@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Annot1 {
  int num();
}


@Annot1(num = 5)
class Class1 {
  public int num() {
    return 1;
  }
}


@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Annot2 {
  String text();
}


@Annot2(text = "ttt")
class Class2 extends Class1 {
  @Override
  public int num() {
    return super.num() + 1;
  }
}
