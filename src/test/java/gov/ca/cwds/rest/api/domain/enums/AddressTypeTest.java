package gov.ca.cwds.rest.api.domain.enums;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddressTypeTest {

  @Test
  public void homeAddressShouldHaveCorrectCode(){
    assertEquals(32, AddressType.HOME.getCode());
  }

  @Test
  public void homeAddressShouldHaveCorrectValue(){
    assertEquals("Residence", AddressType.HOME.getValue());
  }

  @Test
  public void commonAddressShouldHaveCorrectCode(){
    assertEquals(6273, AddressType.COMMON.getCode());
  }

  @Test
  public void commonAddressShouldHaveCorrectValue(){
    assertEquals("Common", AddressType.COMMON.getValue());
  }

  @Test
  public void dayCareAddressShouldHaveCorrectCode(){
    assertEquals(28, AddressType.DAY_CARE.getCode());
  }

  @Test
  public void dayCareAddressShouldHaveCorrectValue(){
    assertEquals("Day Care", AddressType.DAY_CARE.getValue());
  }

  @Test
  public void homelessAddressShouldHaveCorrectCode(){
    assertEquals(29, AddressType.HOMELESS.getCode());
  }

  @Test
  public void homelessAddressShouldHaveCorrectValue(){
    assertEquals("Homeless", AddressType.HOMELESS.getValue());
  }

  @Test
  public void otherAddressShouldHaveCorrectCode(){
    assertEquals(6272, AddressType.OTHER.getCode());
  }

  @Test
  public void otherAddressShouldHaveCorrectValue(){
    assertEquals("Other", AddressType.OTHER.getValue());
  }

  @Test
  public void penalInstitutionAddressShouldHaveCorrectCode(){
    assertEquals(30, AddressType.PENAL_INSTITUTION.getCode());
  }

  @Test
  public void penalInstitutionAddressShouldHaveCorrectValue(){
    assertEquals("Penal Institution", AddressType.PENAL_INSTITUTION.getValue());
  }

  @Test
  public void permanentMailingAddressShouldHaveCorrectCode(){
    assertEquals(31, AddressType.PERMANENT_MAILING_ADDRESS.getCode());
  }

  @Test
  public void permanentMailingAddressShouldHaveCorrectValue(){
    assertEquals("Permanent Mailing Address", AddressType.PERMANENT_MAILING_ADDRESS.getValue());
  }

  @Test
  public void residence2AddressShouldHaveCorrectCode(){
    assertEquals(6271, AddressType.RESIDENCE_2.getCode());
  }

  @Test
  public void residence2AddressShouldHaveCorrectValue(){
    assertEquals("Residence 2", AddressType.RESIDENCE_2.getValue());
  }

  @Test
  public void workAddressShouldHaveCorrectCode(){
    assertEquals(27, AddressType.WORK.getCode());
  }

  @Test
  public void workAddressShouldHaveCorrectValue(){
    assertEquals("Work", AddressType.WORK.getValue());
  }
}