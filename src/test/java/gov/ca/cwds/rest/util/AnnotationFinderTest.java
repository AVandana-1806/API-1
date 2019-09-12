package gov.ca.cwds.rest.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.ClientCollateral;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.data.persistence.ns.Address;
import gov.ca.cwds.rest.api.domain.cms.PostedClientRelationship;
import gov.ca.cwds.rest.resources.ClientRelationshipResource;
import io.dropwizard.hibernate.UnitOfWork;

public class AnnotationFinderTest extends Doofenshmirtz<String> {

  AnnotationFinder target;

  @Override
  public void setup() throws Exception {
    super.setup();
    target = new AnnotationFinder();
  }

  @Test
  public void type() throws Exception {
    assertThat(AnnotationFinder.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findTableName_A$Class() throws Exception {
    final List<Pair<Class<?>, String>> pairs = new ArrayList<>();
    pairs.add(Pair.<Class<?>, String>of(Client.class, "CLIENT_T"));
    pairs.add(Pair.<Class<?>, String>of(Allegation.class, "ALLGTN_T"));
    pairs.add(Pair.<Class<?>, String>of(ClientAddress.class, "CL_ADDRT"));
    pairs.add(Pair.<Class<?>, String>of(LongText.class, "LONG_TXT"));
    pairs.add(Pair.<Class<?>, String>of(Address.class, "addresses"));
    pairs.add(Pair.<Class<?>, String>of(ClientCollateral.class, "CLN_COLT"));
    pairs.add(Pair.<Class<?>, String>of(PostedClientRelationship.class, ""));
    pairs.add(Pair.<Class<?>, String>of(String.class, ""));

    for (Pair<Class<?>, String> p : pairs) {
      final String actual = target.findTableName(p.getKey());
      assertThat(actual, is(equalTo(p.getValue())));
    }
  }

  @Test
  public void findClassAnnotation_A$Class$Class() throws Exception {
    final Table actual = target.findClassAnnotation(Client.class, Table.class);
    final String expected = "CLIENT_T";
    assertThat(actual.name(), is(equalTo(expected)));
  }

  @Test
  public void findMethodAnnotation_A$Class$Class$String() throws Exception {
    // Find data source from @UnitOfWork:
    final UnitOfWork actual =
        target.findMethodAnnotation(ClientRelationshipResource.class, UnitOfWork.class, "get");
    System.out.println("data source: \"" + actual.value() + "\"");

    final String expected = "cms";
    assertThat(actual.value(), is(equalTo(expected)));
  }

}
