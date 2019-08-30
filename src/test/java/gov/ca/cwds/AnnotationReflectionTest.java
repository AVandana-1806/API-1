package gov.ca.cwds;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.legacy.cms.entity.LongText;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;

public class AnnotationReflectionTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationReflectionTest.class);

  public static void main(String[] args) throws Exception {
    check(Class2.class.getMethod("num", new Class[0]));

    findTableName(Client.class);
    findTableName(Allegation.class);
    findTableName(ClientAddress.class);
    findTableName(LongText.class);
    findTableName(gov.ca.cwds.data.persistence.cms.LongText.class);
    findTableName(Address.class);
    findTableName(gov.ca.cwds.data.legacy.cms.entity.Address.class);
    findTableName(gov.ca.cwds.data.persistence.ns.Address.class);
  }

  public static String findTableName(Class<?> klazz) {
    final Table table = klazz.getDeclaredAnnotation(Table.class);
    final String ret = table != null ? table.name() : null;
    LOGGER.info("table: class: {}, name: \"{}\"", klazz, ret);
    return ret;
  }

  public static void check(Method invokedMethod) {
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
