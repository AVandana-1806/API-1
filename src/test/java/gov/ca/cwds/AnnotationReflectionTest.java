package gov.ca.cwds;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javax.persistence.Table;

import gov.ca.cwds.data.persistence.cms.Client;

public class AnnotationReflectionTest {

  public static void main(String[] args) throws Exception {
    check(Class2.class.getMethod("num", new Class[0]));

    findTableName(Client.class);
  }

  public static String findTableName(Class<?> klazz) {
    final Table table = Client.class.getDeclaredAnnotation(Table.class);
    return table.name();
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
