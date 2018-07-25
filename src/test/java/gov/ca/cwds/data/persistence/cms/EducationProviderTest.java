package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.util.Doofenshmirtz;

public class EducationProviderTest extends Doofenshmirtz<EducationProvider> {

  EducationProvider target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new EducationProvider();
  }

  @Test
  public void type() throws Exception {
    assertThat(EducationProvider.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCityName_A$() throws Exception {
    String actual = target.getCityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getWebAddress_A$() throws Exception {
    String actual = target.getWebAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFaxNumber_A$() throws Exception {
    Long actual = target.getFaxNumber();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_A$() throws Exception {
    String actual = target.getId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneExtensionNumber_A$() throws Exception {
    Integer actual = target.getPhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumberAsDecimal_A$() throws Exception {
    Long actual = target.getPhoneNumberAsDecimal();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumber_A$() throws Exception {
    String actual = target.getPhoneNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCodeType_A$() throws Exception {
    Short actual = target.getStateCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetName_A$() throws Exception {
    String actual = target.getStreetName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_A$() throws Exception {
    String actual = target.getStreetNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipNumber_A$() throws Exception {
    Integer actual = target.getZipNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipSuffixNumber_A$() throws Exception {
    Short actual = target.getZipSuffixNumber();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetAddress_A$() throws Exception {
    String actual = target.getStreetAddress();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCity_A$() throws Exception {
    String actual = target.getCity();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getState_A$() throws Exception {
    String actual = target.getState();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZip_A$() throws Exception {
    String actual = target.getZip();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty_A$() throws Exception {
    String actual = target.getCounty();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumberExtension_A$() throws Exception {
    String actual = target.getPhoneNumberExtension();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneType_A$() throws Exception {
    Object actual = target.getPhoneType();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneId_A$() throws Exception {
    String actual = target.getPhoneId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAddressId_A$() throws Exception {
    String actual = target.getAddressId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCd_A$() throws Exception {
    Short actual = target.getStateCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAgencyName_A$String() throws Exception {
    String agencyName = null;
    target.setAgencyName(agencyName);
  }

  @Test
  public void setCityName_A$String() throws Exception {
    String cityName = null;
    target.setCityName(cityName);
  }

  @Test
  public void setWebAddress_A$String() throws Exception {
    String webAddress = null;
    target.setWebAddress(webAddress);
  }

  @Test
  public void setFaxNumber_A$Long() throws Exception {
    Long faxNumber = null;
    target.setFaxNumber(faxNumber);
  }

  @Test
  public void setId_A$String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void setPhoneExtensionNumber_A$Integer() throws Exception {
    Integer phoneExtensionNumber = null;
    target.setPhoneExtensionNumber(phoneExtensionNumber);
  }

  @Test
  public void setPhoneNumber_A$Long() throws Exception {
    Long phoneNumber = null;
    target.setPhoneNumber(phoneNumber);
  }

  @Test
  public void setStateCodeType_A$Short() throws Exception {
    Short stateCodeType = null;
    target.setStateCodeType(stateCodeType);
  }

  @Test
  public void setStreetName_A$String() throws Exception {
    String streetName = null;
    target.setStreetName(streetName);
  }

  @Test
  public void setStreetNumber_A$String() throws Exception {
    String streetNumber = null;
    target.setStreetNumber(streetNumber);
  }

  @Test
  public void setZipNumber_A$Integer() throws Exception {
    Integer zipNumber = null;
    target.setZipNumber(zipNumber);
  }

  @Test
  public void setZipSuffixNumber_A$Short() throws Exception {
    Short zipSuffixNumber = null;
    target.setZipSuffixNumber(zipSuffixNumber);
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    int expected = 0;
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
