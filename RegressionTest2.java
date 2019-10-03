import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegressionTest2 {

    public static boolean debug = false;

    @Test
    public void test1001() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1001");
        gov.ca.cwds.data.persistence.ns.CsecEntity csecEntity0 = new gov.ca.cwds.data.persistence.ns.CsecEntity();
        csecEntity0.setParticipantId("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds");
        java.lang.String str3 = csecEntity0.getParticipantId();
        java.time.LocalDate localDate4 = null;
        csecEntity0.setStartDate(localDate4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str3 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds" + "'", str3.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds"));
    }

    @Test
    public void test1002() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1002");
        gov.ca.cwds.data.persistence.ns.Person person0 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity1 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity2 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person0, ethnicity1);
        java.util.Date date3 = personEthnicity2.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person4 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity5 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity6 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person4, ethnicity5);
        java.util.Date date7 = personEthnicity6.getLastUpdatedTime();
        boolean boolean8 = personEthnicity2.equals((java.lang.Object) personEthnicity6);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity12 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity2.setEthnicity(ethnicity12);
        java.util.Date date14 = personEthnicity2.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person15 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity19 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity20 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person15, ethnicity19);
        gov.ca.cwds.data.persistence.ns.Person person21 = null;
        personEthnicity20.setPerson(person21);
        gov.ca.cwds.data.persistence.ns.PersonEthnicityId personEthnicityId23 = personEthnicity20.getPrimaryKey();
        boolean boolean24 = personEthnicity2.equals((java.lang.Object) personEthnicityId23);
        gov.ca.cwds.data.persistence.ns.Person person25 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity26 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity27 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person25, ethnicity26);
        java.util.Date date28 = personEthnicity27.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person29 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity30 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity31 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person29, ethnicity30);
        java.util.Date date32 = personEthnicity31.getLastUpdatedTime();
        boolean boolean33 = personEthnicity27.equals((java.lang.Object) personEthnicity31);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity37 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity27.setEthnicity(ethnicity37);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity39 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity37);
        java.lang.String str40 = ethnicity37.getCreateUserId();
        java.lang.String str41 = ethnicity37.getLastUpdatedId();
        personEthnicityId23.setEthnicity(ethnicity37);
        gov.ca.cwds.data.persistence.ns.Person person43 = personEthnicityId23.getPerson();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date3);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date3.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date7);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date7.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean8 + "' != '" + true + "'", boolean8 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date14);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date14.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personEthnicityId23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean24 + "' != '" + false + "'", boolean24 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date28);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date28.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date32);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date32.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean33 + "' != '" + true + "'", boolean33 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str40);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str41);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(person43);
    }

    @Test
    public void test1003() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1003");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship0.setLastUpdatedId("");
        java.lang.String str3 = clientRelationship0.getSecondaryClientId();
        java.util.Date date4 = clientRelationship0.getEndDate();
        java.lang.String str5 = clientRelationship0.getAbsentParentCode();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
    }

    @Test
    public void test1004() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1004");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getRaces();
        java.lang.String str2 = participantEntity0.getApproximateAge();
        participantEntity0.setGender("yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'");
        participantEntity0.setNameSuffix("2019-09-03T15:53:13.008-0700");
        participantEntity0.setProbationYouth((java.lang.Boolean) false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
    }

    @Test
    public void test1005() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1005");
        gov.ca.cwds.rest.api.domain.Address[] addressArray7 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet8 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean9 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet8, addressArray7);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray10 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet11 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean12 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet11, phoneNumberArray10);
        gov.ca.cwds.rest.api.domain.Language[] languageArray13 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet14 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean15 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet14, languageArray13);
        gov.ca.cwds.rest.api.domain.Race[] raceArray16 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet17 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean18 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet17, raceArray16);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity21 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray22 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity21 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet23 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean24 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet23, ethnicityArray22);
        gov.ca.cwds.rest.api.domain.Person person25 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet8, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet11, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet14, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet17, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet23);
        java.lang.String str26 = person25.getMiddleName();
        java.lang.String str27 = person25.getNameSuffix();
        java.lang.String str28 = person25.getGender();
        java.lang.String str29 = person25.getNameSuffix();
        java.util.Set<gov.ca.cwds.rest.api.domain.Address> addressSet30 = person25.getAddress();
        java.util.Set<gov.ca.cwds.rest.api.domain.Address> addressSet31 = person25.getAddress();
        java.lang.String str32 = person25.getNameSuffix();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor40 = null;
        gov.ca.cwds.rest.api.domain.Address address41 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor40);
        java.lang.String str42 = address41.getCity();
        java.lang.Integer int43 = address41.getType();
        address41.setLegacyId("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        boolean boolean46 = person25.equals((java.lang.Object) address41);
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor47 = address41.getLegacyDescriptor();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray10);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean12 + "' != '" + false + "'", boolean12 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray13);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + false + "'", boolean15 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray22);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean24 + "' != '" + true + "'", boolean24 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str26 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str26.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str27 + "' != '" + "" + "'", str27.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "hi!" + "'", str28.equals("hi!"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str29 + "' != '" + "" + "'", str29.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressSet30);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressSet31);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str32 + "' != '" + "" + "'", str32.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str42 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str42.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int43 + "' != '" + 0 + "'", int43.equals(0));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean46 + "' != '" + false + "'", boolean46 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor47);
    }

    @Test
    public void test1006() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1006");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship2 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date3 = clientRelationship2.getLastUpdatedTime();
        java.lang.String str4 = clientRelationship2.getSecondaryClientId();
        gov.ca.cwds.data.persistence.ns.Person person5 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity6 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity7 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person5, ethnicity6);
        java.util.Date date8 = personEthnicity7.getLastUpdatedTime();
        java.lang.String str9 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date8);
        java.lang.String str10 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date8);
        clientRelationship2.setLastUpdatedTime(date8);
        gov.ca.cwds.data.persistence.ns.Person person18 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity19 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity20 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person18, ethnicity19);
        java.util.Date date21 = personEthnicity20.getLastUpdatedTime();
        java.lang.String str22 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date21);
        java.lang.String str23 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date21);
        gov.ca.cwds.data.persistence.ns.Person person28 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity29 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity30 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person28, ethnicity29);
        java.util.Date date31 = personEthnicity30.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person32 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity33 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity34 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person32, ethnicity33);
        java.util.Date date35 = personEthnicity34.getLastUpdatedTime();
        boolean boolean36 = personEthnicity30.equals((java.lang.Object) personEthnicity34);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity40 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity30.setEthnicity(ethnicity40);
        java.util.Date date42 = personEthnicity30.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship43 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", (java.lang.Short) (short) 1, date21, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "2019-09-03", "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", date42);
        java.lang.String str44 = gov.ca.cwds.rest.api.domain.DomainChef.cookISO8601Timestamp(date21);
        java.util.Date date45 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date21);
        java.lang.String str46 = gov.ca.cwds.rest.api.domain.DomainChef.cookTime(date21);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship47 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("2019-09-03T15:53:27.444-0700", (java.lang.Short) (short) -1, date8, "2019-09-03-15.53.43.538", "{\"absentParentCode\":\"HH:mm:ss\",\"clientRelationshipType\":0,\"endDate\":1567551225020,\"id\":\"Non-mandated Reporter\",\"lastUpdatedId\":null,\"lastUpdatedTime\":null,\"primaryClientId\":\"gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId\",\"primaryKey\":\"Non-mandated Reporter\",\"sameHomeCode\":\"\",\"secondaryClientId\":\"15:52:54\",\"startDate\":1567551225020}", "2019-09-03T15:53:25.566Z", "2019-09-03T15:53:17.793Z", date21);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date8);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date8.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "2019-09-03" + "'", str9.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str10 + "' != '" + "2019-09-03" + "'", str10.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date21);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date21.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str22 + "' != '" + "2019-09-03" + "'", str22.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str23 + "' != '" + "2019-09-03" + "'", str23.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date31);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date31.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date35);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date35.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date42);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date42.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str44 + "' != '" + "2019-09-03T15:53:48.787Z" + "'", str44.equals("2019-09-03T15:53:48.787Z"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date45);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date45.toString(), "Tue Sep 03 15:53:48 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str46 + "' != '" + "15:53:48" + "'", str46.equals("15:53:48"));
    }

    @Test
    public void test1007() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1007");
        gov.ca.cwds.data.persistence.ns.CsecEntity csecEntity0 = new gov.ca.cwds.data.persistence.ns.CsecEntity();
        java.lang.String str1 = csecEntity0.getParticipantId();
        java.lang.Integer int2 = csecEntity0.getId();
        java.io.Serializable serializable3 = csecEntity0.getPrimaryKey();
        java.lang.Integer int4 = csecEntity0.getId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(int2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(int4);
    }

    @Test
    public void test1008() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1008");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType0 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean1 = stringJsonUserType0.isMutable();
        int[] intArray2 = stringJsonUserType0.sqlTypes();
        java.lang.Object obj4 = stringJsonUserType0.deepCopy((java.lang.Object) "2019-09-03-15.53.08.216");
        gov.ca.cwds.rest.api.domain.Address[] addressArray12 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet13 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean14 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet13, addressArray12);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray15 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet16 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean17 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet16, phoneNumberArray15);
        gov.ca.cwds.rest.api.domain.Language[] languageArray18 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet19 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean20 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet19, languageArray18);
        gov.ca.cwds.rest.api.domain.Race[] raceArray21 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet22 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean23 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet22, raceArray21);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity26 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray27 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity26 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet28 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean29 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet28, ethnicityArray27);
        gov.ca.cwds.rest.api.domain.Person person30 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet13, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet16, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet19, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet22, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet28);
        java.lang.String str31 = person30.getLastName();
        java.lang.String str32 = person30.getNameSuffix();
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList33 = person30.getMessages();
        java.lang.String str34 = person30.getFirstName();
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity37 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList38 = ethnicity37.getMessages();
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity41 = new gov.ca.cwds.data.persistence.ns.Ethnicity(ethnicity37, "Anonymous Reporter", "N");
        boolean boolean42 = stringJsonUserType0.equals((java.lang.Object) str34, (java.lang.Object) "Anonymous Reporter");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean1 + "' != '" + true + "'", boolean1 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(intArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + obj4 + "' != '" + "2019-09-03-15.53.08.216" + "'", obj4.equals("2019-09-03-15.53.08.216"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + false + "'", boolean14 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean17 + "' != '" + false + "'", boolean17 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray18);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray21);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean23 + "' != '" + false + "'", boolean23 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray27);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean29 + "' != '" + true + "'", boolean29 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str31 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str31.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str32 + "' != '" + "" + "'", str32.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList33);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str34 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSSZ" + "'", str34.equals("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList38);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean42 + "' != '" + false + "'", boolean42 == false);
    }

    @Test
    public void test1009() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1009");
        gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber4 = new gov.ca.cwds.rest.api.domain.PhoneNumber((java.lang.Long) (-1L), "2019-09-03-15.53.08.285", "2019-09-03T15:53:37.021-0700", "2019-09-03T15:53:17.793Z");
    }

    @Test
    public void test1010() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1010");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        java.lang.Integer int10 = address8.getType();
        address8.setLegacyId("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter13 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.Address[] addressArray21 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet22 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean23 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet22, addressArray21);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray24 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet25 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean26 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet25, phoneNumberArray24);
        gov.ca.cwds.rest.api.domain.Language[] languageArray27 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet28 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean29 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet28, languageArray27);
        gov.ca.cwds.rest.api.domain.Race[] raceArray30 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet31 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean32 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet31, raceArray30);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity35 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray36 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity35 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet37 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean38 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet37, ethnicityArray36);
        gov.ca.cwds.rest.api.domain.Person person39 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet22, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet25, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet28, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet31, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet37);
        boolean boolean40 = hOIReporter13.equals((java.lang.Object) "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        hOIReporter13.setId("yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor50 = null;
        gov.ca.cwds.rest.api.domain.Address address51 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor50);
        java.lang.String str52 = address51.getCity();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor53 = address51.getLegacyDescriptor();
        hOIReporter13.setLegacyDescriptor(legacyDescriptor53);
        org.joda.time.DateTime dateTime55 = null;
        legacyDescriptor53.setLastUpdated(dateTime55);
        address8.setLegacyDescriptor(legacyDescriptor53);
        java.lang.Integer int58 = address8.getState();
        java.lang.String str59 = address8.getZip();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int10 + "' != '" + 0 + "'", int10.equals(0));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray21);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean23 + "' != '" + false + "'", boolean23 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray24);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean26 + "' != '" + false + "'", boolean26 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray27);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean29 + "' != '" + false + "'", boolean29 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray30);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean32 + "' != '" + false + "'", boolean32 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray36);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean38 + "' != '" + true + "'", boolean38 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean40 + "' != '" + false + "'", boolean40 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str52 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str52.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor53);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int58 + "' != '" + 100 + "'", int58.equals(100));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str59 + "' != '" + "HH:mm:ss" + "'", str59.equals("HH:mm:ss"));
    }

    @Test
    public void test1011() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1011");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean1 = participantEntity0.getProbationYouth();
        gov.ca.cwds.data.persistence.ns.CsecEntity[] csecEntityArray2 = new gov.ca.cwds.data.persistence.ns.CsecEntity[] {};
        java.util.ArrayList<gov.ca.cwds.data.persistence.ns.CsecEntity> csecEntityList3 = new java.util.ArrayList<gov.ca.cwds.data.persistence.ns.CsecEntity>();
        boolean boolean4 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.data.persistence.ns.CsecEntity>) csecEntityList3, csecEntityArray2);
        participantEntity0.setCsecs((java.util.List<gov.ca.cwds.data.persistence.ns.CsecEntity>) csecEntityList3);
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity6 = participantEntity0.getScreening();
        java.lang.String str7 = participantEntity0.getScreeningId();
        participantEntity0.setLastName("2019-09-03T15:52:59.197-0700");
        java.lang.String str10 = participantEntity0.getLegacyId();
        java.lang.Boolean boolean11 = participantEntity0.getEstimatedDob();
        java.lang.String str12 = participantEntity0.getSsn();
        participantEntity0.setApproximateAge("2019-09-03-15.53.05.703");
        participantEntity0.setNameSuffix("15:53:04");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(csecEntityArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean4 + "' != '" + false + "'", boolean4 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str10);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str12);
    }

    @Test
    public void test1012() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1012");
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity2 = new gov.ca.cwds.rest.api.domain.Ethnicity("2019-09-03-15.53.26.640", "2019-09-03T15:53:27.159-0700");
    }

    @Test
    public void test1013() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1013");
        gov.ca.cwds.rest.api.domain.Race race2 = new gov.ca.cwds.rest.api.domain.Race("_n", "");
        gov.ca.cwds.data.persistence.ns.Race race5 = new gov.ca.cwds.data.persistence.ns.Race(race2, "2019-09-03T15:53:30.502Z", "2019-09-03T15:53:27.721Z");
    }

    @Test
    public void test1014() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1014");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean1 = participantEntity0.getProbationYouth();
        participantEntity0.setLastName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.Date date4 = null;
        participantEntity0.setDateOfBirth(date4);
        participantEntity0.setId("_n");
        participantEntity0.setEthnicity("yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'");
        participantEntity0.setId("_n");
        participantEntity0.setRelatedScreeningId("15:52:59");
        participantEntity0.setLegacyId("2019-09-03-15.53.05.294");
        java.util.Date date16 = participantEntity0.getDateOfBirth();
        java.util.Date date17 = participantEntity0.getDateOfDeath();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date17);
    }

    @Test
    public void test1015() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1015");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role1 = hOIReporter0.getRole();
        java.lang.String str2 = hOIReporter0.getId();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor3 = hOIReporter0.getLegacyDescriptor();
        java.lang.String str4 = hOIReporter0.getNameSuffix();
        hOIReporter0.setId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        boolean boolean8 = hOIReporter0.equals((java.lang.Object) "2019-09-03T15:53:26.809Z");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor23 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        gov.ca.cwds.rest.api.domain.Address address24 = new gov.ca.cwds.rest.api.domain.Address("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds", "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", "15:52:59", (java.lang.Integer) 10, "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", (java.lang.Integer) 10, legacyDescriptor23);
        gov.ca.cwds.rest.api.domain.Address address25 = new gov.ca.cwds.rest.api.domain.Address("N", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "2019-09-03T15:52:58.300-0700", (java.lang.Integer) (-1), "00001", (java.lang.Integer) 100, legacyDescriptor23);
        org.joda.time.DateTime dateTime26 = null;
        legacyDescriptor23.setLastUpdated(dateTime26);
        hOIReporter0.setLegacyDescriptor(legacyDescriptor23);
        legacyDescriptor23.setUiId("");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean8 + "' != '" + false + "'", boolean8 == false);
    }

    @Test
    public void test1016() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1016");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role0 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor5 = null;
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter6 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role0, "15:52:54", "2019-09-03-15.52.55.282", "{\"id\":null,\"first_name\":null,\"last_name\":null,\"name_suffix\":null,\"role\":null,\"legacy_descriptor\":{\"legacy_id\":null,\"legacy_ui_id\":null,\"legacy_last_updated\":null,\"legacy_table_name\":null,\"legacy_table_description\":null}}", "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'", legacyDescriptor5);
        java.lang.String str7 = hOIReporter6.getFirstName();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role8 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor13 = null;
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter14 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role8, "15:52:54", "2019-09-03-15.52.55.282", "{\"id\":null,\"first_name\":null,\"last_name\":null,\"name_suffix\":null,\"role\":null,\"legacy_descriptor\":{\"legacy_id\":null,\"legacy_ui_id\":null,\"legacy_last_updated\":null,\"legacy_table_name\":null,\"legacy_table_description\":null}}", "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'", legacyDescriptor13);
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role15 = hOIReporter14.getRole();
        boolean boolean16 = hOIReporter6.equals((java.lang.Object) hOIReporter14);
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role17 = hOIReporter6.getRole();
        hOIReporter6.setFirstName("2019-09-03T15:53:09.087Z");
        java.lang.String str20 = hOIReporter6.getLastName();
        java.lang.String str21 = hOIReporter6.getId();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor22 = hOIReporter6.getLegacyDescriptor();
        org.junit.Assert.assertTrue("'" + role0 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role0.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str7 + "' != '" + "2019-09-03-15.52.55.282" + "'", str7.equals("2019-09-03-15.52.55.282"));
        org.junit.Assert.assertTrue("'" + role8 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role8.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        org.junit.Assert.assertTrue("'" + role15 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role15.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + true + "'", boolean16 == true);
        org.junit.Assert.assertTrue("'" + role17 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role17.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str20 + "' != '" + "{\"id\":null,\"first_name\":null,\"last_name\":null,\"name_suffix\":null,\"role\":null,\"legacy_descriptor\":{\"legacy_id\":null,\"legacy_ui_id\":null,\"legacy_last_updated\":null,\"legacy_table_name\":null,\"legacy_table_description\":null}}" + "'", str20.equals("{\"id\":null,\"first_name\":null,\"last_name\":null,\"name_suffix\":null,\"role\":null,\"legacy_descriptor\":{\"legacy_id\":null,\"legacy_ui_id\":null,\"legacy_last_updated\":null,\"legacy_table_name\":null,\"legacy_table_description\":null}}"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str21 + "' != '" + "15:52:54" + "'", str21.equals("15:52:54"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(legacyDescriptor22);
    }

    @Test
    public void test1017() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1017");
        gov.ca.cwds.rest.api.domain.Address[] addressArray7 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet8 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean9 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet8, addressArray7);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray10 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet11 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean12 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet11, phoneNumberArray10);
        gov.ca.cwds.rest.api.domain.Language[] languageArray13 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet14 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean15 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet14, languageArray13);
        gov.ca.cwds.rest.api.domain.Race[] raceArray16 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet17 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean18 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet17, raceArray16);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity21 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray22 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity21 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet23 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean24 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet23, ethnicityArray22);
        gov.ca.cwds.rest.api.domain.Person person25 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet8, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet11, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet14, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet17, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet23);
        java.lang.String str26 = person25.getSsn();
        gov.ca.cwds.data.persistence.ns.Person person27 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity28 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity29 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person27, ethnicity28);
        java.util.Date date30 = personEthnicity29.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person31 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity32 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity33 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person31, ethnicity32);
        java.util.Date date34 = personEthnicity33.getLastUpdatedTime();
        boolean boolean35 = personEthnicity29.equals((java.lang.Object) personEthnicity33);
        boolean boolean36 = person25.equals((java.lang.Object) boolean35);
        java.lang.String str37 = person25.getSsn();
        java.lang.String str38 = person25.getMiddleName();
        java.lang.String str39 = person25.getLastName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray10);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean12 + "' != '" + false + "'", boolean12 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray13);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + false + "'", boolean15 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray22);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean24 + "' != '" + true + "'", boolean24 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str26 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str26.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date30);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date30.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date34);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date34.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean35 + "' != '" + true + "'", boolean35 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + false + "'", boolean36 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str37 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str37.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str38 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str38.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str39 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str39.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
    }

    @Test
    public void test1018() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1018");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor0 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor0.setId("2019-09-03-15.52.55.282");
        org.joda.time.DateTime dateTime3 = null;
        legacyDescriptor0.setLastUpdated(dateTime3);
    }

    @Test
    public void test1019() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1019");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean1 = participantEntity0.getProbationYouth();
        gov.ca.cwds.data.persistence.ns.CsecEntity[] csecEntityArray2 = new gov.ca.cwds.data.persistence.ns.CsecEntity[] {};
        java.util.ArrayList<gov.ca.cwds.data.persistence.ns.CsecEntity> csecEntityList3 = new java.util.ArrayList<gov.ca.cwds.data.persistence.ns.CsecEntity>();
        boolean boolean4 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.data.persistence.ns.CsecEntity>) csecEntityList3, csecEntityArray2);
        participantEntity0.setCsecs((java.util.List<gov.ca.cwds.data.persistence.ns.CsecEntity>) csecEntityList3);
        participantEntity0.setGender("yyyy-MM-dd HH:mm:ss.SSS");
        participantEntity0.setLegacySourceTable("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        participantEntity0.setApproximateAgeUnits("{\"id\":null,\"first_name\":null,\"last_name\":null,\"name_suffix\":null,\"role\":null,\"legacy_descriptor\":{\"legacy_id\":null,\"legacy_ui_id\":null,\"legacy_last_updated\":null,\"legacy_table_name\":null,\"legacy_table_description\":null}}");
        java.lang.String str12 = participantEntity0.getGender();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(csecEntityArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean4 + "' != '" + false + "'", boolean4 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str12 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str12.equals("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void test1020() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1020");
        gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber4 = new gov.ca.cwds.rest.api.domain.PhoneNumber((java.lang.Long) 0L, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId", "2019-09-03-15.52.55.282", "_n");
        java.lang.String str5 = phoneNumber4.getExtension();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str5 + "' != '" + "2019-09-03-15.52.55.282" + "'", str5.equals("2019-09-03-15.52.55.282"));
    }

    @Test
    public void test1021() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1021");
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity3 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonEthnicity> personEthnicitySet4 = ethnicity3.getPersonEthnicity();
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity5 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity3);
        java.lang.String str6 = ethnicity5.getSubEthnicity();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personEthnicitySet4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str6 + "' != '" + "" + "'", str6.equals(""));
    }

    @Test
    public void test1022() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1022");
        gov.ca.cwds.rest.api.domain.Language language1 = new gov.ca.cwds.rest.api.domain.Language("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId");
        boolean boolean3 = language1.equals((java.lang.Object) 100);
        boolean boolean5 = language1.equals((java.lang.Object) "2019-09-03T15:53:13.008-0700");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean3 + "' != '" + false + "'", boolean3 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
    }

    @Test
    public void test1023() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1023");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role1 = hOIReporter0.getRole();
        java.lang.String str2 = hOIReporter0.getId();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor3 = hOIReporter0.getLegacyDescriptor();
        java.lang.String str4 = hOIReporter0.getNameSuffix();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role5 = hOIReporter0.getRole();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor13 = null;
        gov.ca.cwds.rest.api.domain.Address address14 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor13);
        java.lang.String str15 = address14.getCity();
        java.lang.String str16 = address14.getCity();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor17 = address14.getLegacyDescriptor();
        legacyDescriptor17.setTableName("HH:mm:ss");
        java.lang.String str20 = legacyDescriptor17.getTableDescription();
        legacyDescriptor17.setUiId("2019-09-03T15:53:35.927Z");
        hOIReporter0.setLegacyDescriptor(legacyDescriptor17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str15 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str15.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str16.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str20);
    }

    @Test
    public void test1024() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1024");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship1 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship1.setLastUpdatedId("");
        java.lang.String str4 = clientRelationship1.getSecondaryClientId();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship5 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date6 = clientRelationship5.getLastUpdatedTime();
        boolean boolean7 = clientRelationship1.equals((java.lang.Object) date6);
        java.util.Date date8 = clientRelationship1.getLastUpdatedTime();
        java.util.Date date9 = clientRelationship1.getEndDate();
        java.lang.String str10 = clientRelationship1.getLastUpdatedId();
        boolean boolean11 = safelySurrenderedBabiesEntity0.equals((java.lang.Object) clientRelationship1);
        java.io.Serializable serializable12 = safelySurrenderedBabiesEntity0.getPrimaryKey();
        java.lang.String str13 = safelySurrenderedBabiesEntity0.getParticipantId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean7 + "' != '" + false + "'", boolean7 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str10 + "' != '" + "" + "'", str10.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean11 + "' != '" + false + "'", boolean11 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str13);
    }

    @Test
    public void test1025() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1025");
        gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber4 = new gov.ca.cwds.rest.api.domain.PhoneNumber((java.lang.Long) 0L, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId", "2019-09-03-15.52.55.282", "_n");
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList5 = phoneNumber4.getMessages();
        java.lang.String str6 = phoneNumber4.getNumber();
        java.lang.Long long7 = phoneNumber4.getId();
        java.lang.Long long8 = phoneNumber4.getId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str6 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId" + "'", str6.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long7 + "' != '" + 0L + "'", long7.equals(0L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long8 + "' != '" + 0L + "'", long8.equals(0L));
    }

    @Test
    public void test1026() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1026");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds", "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", "15:52:59", (java.lang.Integer) 10, "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", (java.lang.Integer) 10, legacyDescriptor7);
        java.lang.String str9 = address8.getLegacySourceTable();
        boolean boolean10 = address8.hasMessages();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId" + "'", str9.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
    }

    @Test
    public void test1027() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1027");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        java.lang.Integer int10 = address8.getType();
        java.lang.String str11 = address8.getStreetAddress();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter12 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        boolean boolean13 = address8.equals((java.lang.Object) hOIReporter12);
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor14 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor14.setId("2019-09-03-15.52.55.282");
        hOIReporter12.setLegacyDescriptor(legacyDescriptor14);
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity18 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean19 = participantEntity18.getProbationYouth();
        participantEntity18.setLastName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        participantEntity18.setApproximateAgeUnits("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        participantEntity18.setSsn("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId");
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity26 = participantEntity18.getScreening();
        boolean boolean27 = legacyDescriptor14.equals((java.lang.Object) participantEntity18);
        legacyDescriptor14.setId("");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int10 + "' != '" + 0 + "'", int10.equals(0));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str11 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str11.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean19);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
    }

    @Test
    public void test1028() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1028");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship0.setLastUpdatedId("");
        clientRelationship0.setLastUpdatedId("2019-09-03-15.53.02.984");
    }

    @Test
    public void test1029() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1029");
        gov.ca.cwds.rest.api.domain.Language language1 = new gov.ca.cwds.rest.api.domain.Language("2019-09-03T15:53:08.733-0700");
    }

    @Test
    public void test1030() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1030");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setRaces("yyyy-MM-dd HH:mm:ss.SSS");
        participantEntity0.setEstimatedDob((java.lang.Boolean) false);
        java.lang.String str5 = participantEntity0.getEthnicity();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
    }

    @Test
    public void test1031() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1031");
        gov.ca.cwds.data.persistence.hibernate.StringArrayType stringArrayType0 = new gov.ca.cwds.data.persistence.hibernate.StringArrayType();
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity4 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        java.lang.String str5 = ethnicity4.getEthnicityType();
        java.lang.Long long6 = ethnicity4.getId();
        gov.ca.cwds.data.persistence.ns.Person person7 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity8 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity9 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person7, ethnicity8);
        java.util.Date date10 = personEthnicity9.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person11 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity12 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity13 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person11, ethnicity12);
        java.util.Date date14 = personEthnicity13.getLastUpdatedTime();
        boolean boolean15 = personEthnicity9.equals((java.lang.Object) personEthnicity13);
        gov.ca.cwds.data.persistence.ns.PersonEthnicityId personEthnicityId16 = personEthnicity13.getPrimaryKey();
        java.io.Serializable serializable17 = personEthnicityId16.getPrimaryKey();
        boolean boolean18 = stringArrayType0.equals((java.lang.Object) long6, (java.lang.Object) personEthnicityId16);
        java.lang.Class<java.lang.String[]> strArrayClass19 = stringArrayType0.returnedClass();
        java.lang.Class<java.lang.String[]> strArrayClass20 = stringArrayType0.returnedClass();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str5 + "' != '" + "hi!" + "'", str5.equals("hi!"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long6 + "' != '" + 1L + "'", long6.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date10);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date10.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date14);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date14.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + true + "'", boolean15 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personEthnicityId16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArrayClass19);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArrayClass20);
    }

    @Test
    public void test1032() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1032");
        gov.ca.cwds.rest.api.domain.Language language1 = new gov.ca.cwds.rest.api.domain.Language("");
        java.lang.String str2 = language1.getTheLanguage();
        java.lang.String str3 = language1.getTheLanguage();
        boolean boolean5 = language1.equals((java.lang.Object) "2019-09-03-15.53.11.857");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str2 + "' != '" + "" + "'", str2.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str3 + "' != '" + "" + "'", str3.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
    }

    @Test
    public void test1033() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1033");
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity2 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList3 = ethnicity2.getMessages();
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity6 = new gov.ca.cwds.data.persistence.ns.Ethnicity(ethnicity2, "Anonymous Reporter", "N");
        java.lang.String str7 = ethnicity2.getEthnicityType();
        boolean boolean9 = ethnicity2.equals((java.lang.Object) ' ');
        java.lang.String str10 = ethnicity2.getEthnicityType();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity11 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity11.setGender("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds");
        boolean boolean14 = ethnicity2.equals((java.lang.Object) "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds");
        java.lang.String str15 = ethnicity2.getSubEthnicity();
        java.lang.String str16 = ethnicity2.getSubEthnicity();
        boolean boolean18 = ethnicity2.equals((java.lang.Object) "2019-09-03-15.53.29.790");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str7 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds" + "'", str7.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str10 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds" + "'", str10.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + false + "'", boolean14 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str15 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str15.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str16.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
    }

    @Test
    public void test1034() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1034");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean1 = participantEntity0.getProbationYouth();
        java.lang.String[] strArray2 = participantEntity0.getRoles();
        java.lang.String str3 = participantEntity0.getMiddleName();
        java.util.Date date4 = participantEntity0.getDateOfBirth();
        participantEntity0.setLegacyId("{\"absentParentCode\":\"HH:mm:ss\",\"clientRelationshipType\":0,\"endDate\":1567551211852,\"id\":\"Non-mandated Reporter\",\"lastUpdatedId\":null,\"lastUpdatedTime\":null,\"primaryClientId\":\"gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId\",\"primaryKey\":\"Non-mandated Reporter\",\"sameHomeCode\":\"\",\"secondaryClientId\":\"15:52:54\",\"startDate\":1567551211852}");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date4);
    }

    @Test
    public void test1035() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1035");
        gov.ca.cwds.rest.api.domain.Race race2 = new gov.ca.cwds.rest.api.domain.Race("2019-09-03T15:53:11.506Z", "N");
        race2.setRaceType("_n");
    }

    @Test
    public void test1036() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1036");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        java.lang.Integer int10 = address8.getType();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor11 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor11.setId("2019-09-03-15.52.55.282");
        java.lang.String str14 = legacyDescriptor11.getUiId();
        legacyDescriptor11.setTableDescription("15:52:54");
        address8.setLegacyDescriptor(legacyDescriptor11);
        java.lang.String str18 = legacyDescriptor11.getTableName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int10 + "' != '" + 0 + "'", int10.equals(0));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str18);
    }

    @Test
    public void test1037() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1037");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date1 = clientRelationship0.getLastUpdatedTime();
        java.lang.String str2 = clientRelationship0.getSecondaryClientId();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship3 = null;
        boolean boolean4 = clientRelationship0.relatedTo(clientRelationship3);
        java.util.Date date5 = clientRelationship0.getStartDate();
        java.lang.String str6 = clientRelationship0.getPrimaryClientId();
        java.lang.String str7 = clientRelationship0.toString();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean4 + "' != '" + false + "'", boolean4 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
    }

    @Test
    public void test1038() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1038");
        gov.ca.cwds.data.persistence.ns.Person person3 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity4 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity5 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person3, ethnicity4);
        java.util.Date date6 = personEthnicity5.getLastUpdatedTime();
        java.lang.String str7 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date6);
        java.lang.String str8 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date6);
        gov.ca.cwds.data.persistence.ns.Person person13 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity14 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity15 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person13, ethnicity14);
        java.util.Date date16 = personEthnicity15.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person17 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity18 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity19 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person17, ethnicity18);
        java.util.Date date20 = personEthnicity19.getLastUpdatedTime();
        boolean boolean21 = personEthnicity15.equals((java.lang.Object) personEthnicity19);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity25 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity15.setEthnicity(ethnicity25);
        java.util.Date date27 = personEthnicity15.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship28 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", (java.lang.Short) (short) 1, date6, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "2019-09-03", "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", date27);
        java.lang.String str29 = gov.ca.cwds.rest.api.domain.DomainChef.cookStrictTimestamp(date27);
        gov.ca.cwds.data.persistence.ns.Person person30 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity31 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity32 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person30, ethnicity31);
        java.util.Date date33 = personEthnicity32.getLastUpdatedTime();
        java.util.Date date34 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date33);
        java.lang.String str35 = gov.ca.cwds.rest.api.domain.DomainChef.cookTime(date34);
        java.lang.String str36 = gov.ca.cwds.rest.api.domain.DomainChef.cookStrictTimestamp(date34);
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity41 = null;
        java.lang.String[] strArray46 = new java.lang.String[] { "Y", "Anonymous Reporter", "2019-09-03T15:53:03.344Z" };
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity47 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean48 = participantEntity47.getProbationYouth();
        participantEntity47.setLastName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        participantEntity47.setApproximateAgeUnits("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        java.lang.String[] strArray53 = participantEntity47.getLanguages();
        java.lang.String[] strArray54 = participantEntity47.getLanguages();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity66 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", date27, date34, "2019-09-03T15:53:03.344Z", "hi!", "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", "2019-09-03T15:53:01.223Z", screeningEntity41, "2019-09-03-15.52.58.871", strArray46, strArray54, "_n", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "2019-09-03-15.53.00.269", "yyyy-MM-dd", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", (java.lang.Boolean) true, (java.lang.Boolean) false, (java.lang.Boolean) true, "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", (java.lang.Boolean) true);
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity67 = participantEntity66.getScreening();
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity68 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship69 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship69.setLastUpdatedId("");
        java.lang.String str72 = clientRelationship69.getSecondaryClientId();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship73 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date74 = clientRelationship73.getLastUpdatedTime();
        boolean boolean75 = clientRelationship69.equals((java.lang.Object) date74);
        java.util.Date date76 = clientRelationship69.getLastUpdatedTime();
        java.util.Date date77 = clientRelationship69.getEndDate();
        java.lang.String str78 = clientRelationship69.getLastUpdatedId();
        boolean boolean79 = safelySurrenderedBabiesEntity68.equals((java.lang.Object) clientRelationship69);
        participantEntity66.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity68);
        safelySurrenderedBabiesEntity68.setRelationToChild("15:53:18");
        safelySurrenderedBabiesEntity68.setParticipantId("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date6);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date6.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str7 + "' != '" + "2019-09-03" + "'", str7.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str8 + "' != '" + "2019-09-03" + "'", str8.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date16);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date16.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date20);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date20.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + true + "'", boolean21 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date27);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date27.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str29 + "' != '" + "2019-09-03T15:53:49.499-0700" + "'", str29.equals("2019-09-03T15:53:49.499-0700"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date33);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date33.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date34);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date34.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str35 + "' != '" + "15:53:49" + "'", str35.equals("15:53:49"));
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str36 + "' != '" + "2019-09-03T15:53:49.499-0700" + "'", str36.equals("2019-09-03T15:53:49.499-0700"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray46);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean48);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray53);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray54);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity67);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str72);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date74);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean75 + "' != '" + false + "'", boolean75 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date76);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date77);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str78 + "' != '" + "" + "'", str78.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean79 + "' != '" + false + "'", boolean79 == false);
    }

    @Test
    public void test1039() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1039");
        gov.ca.cwds.data.persistence.ns.Race race3 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 100L, "2019-09-03T15:53:04.984-0700", "2019-09-03-15.53.05.294");
    }

    @Test
    public void test1040() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1040");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role1 = hOIReporter0.getRole();
        java.lang.String str2 = hOIReporter0.getId();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor3 = hOIReporter0.getLegacyDescriptor();
        java.lang.String str4 = hOIReporter0.getNameSuffix();
        hOIReporter0.setId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.lang.String str7 = hOIReporter0.toString();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
    }

    @Test
    public void test1041() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test1041");
        gov.ca.cwds.data.persistence.hibernate.StringArrayType stringArrayType0 = new gov.ca.cwds.data.persistence.hibernate.StringArrayType();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role1 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.NON_MANDATED_REPORTER;
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity2 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean3 = participantEntity2.getProbationYouth();
        participantEntity2.setLastName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        participantEntity2.setApproximateAgeUnits("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        java.lang.String[] strArray8 = participantEntity2.getLanguages();
        java.lang.Object obj9 = stringArrayType0.assemble((java.io.Serializable) role1, (java.lang.Object) strArray8);
        java.lang.Class<java.lang.String[]> strArrayClass10 = stringArrayType0.returnedClass();
        java.sql.ResultSet resultSet11 = null;
        java.lang.String[] strArray17 = new java.lang.String[] { "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "N", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipBySecondaryClientId", "2019-09-03T15:53:03.344Z", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" };
        org.hibernate.engine.spi.SharedSessionContractImplementor sharedSessionContractImplementor18 = null;
        java.lang.Object obj20 = stringArrayType0.nullSafeGet(resultSet11, strArray17, sharedSessionContractImplementor18, (java.lang.Object) 1);
        int[] intArray21 = stringArrayType0.sqlTypes();
        java.sql.ResultSet resultSet22 = null;
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity23 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean24 = participantEntity23.getProbationYouth();
        participantEntity23.setLastName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.Date date27 = null;
        participantEntity23.setDateOfBirth(date27);
        participantEntity23.setId("_n");
        participantEntity23.setEthnicity("yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'");
        participantEntity23.setId("_n");
        java.lang.String str35 = participantEntity23.getScreeningId();
        participantEntity23.setFirstName("15:53:10");
        java.lang.String[] strArray38 = participantEntity23.getLanguages();
        org.hibernate.engine.spi.SharedSessionContractImplementor sharedSessionContractImplementor39 = null;
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity40 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean41 = participantEntity40.getProbationYouth();
        participantEntity40.setLastName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        participantEntity40.setApproximateAgeUnits("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        java.lang.String str46 = participantEntity40.getEthnicity();
        java.lang.String str47 = participantEntity40.getLegacySourceTable();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity50 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.Boolean boolean51 = participantEntity50.getProbationYouth();
        gov.ca.cwds.data.persistence.ns.CsecEntity[] csecEntityArray52 = new gov.ca.cwds.data.persistence.ns.CsecEntity[] {};
        java.util.ArrayList<gov.ca.cwds.data.persistence.ns.CsecEntity> csecEntityList53 = new java.util.ArrayList<gov.ca.cwds.data.persistence.ns.CsecEntity>();
        boolean boolean54 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.data.persistence.ns.CsecEntity>) csecEntityList53, csecEntityArray52);
        participantEntity50.setCsecs((java.util.List<gov.ca.cwds.data.persistence.ns.CsecEntity>) csecEntityList53);
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity56 = participantEntity50.getScreening();
        java.lang.String str57 = participantEntity50.getScreeningId();
        java.lang.String str58 = participantEntity50.getApproximateAge();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity59 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        gov.ca.cwds.data.persistence.ns.Person person60 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity61 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity62 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person60, ethnicity61);
        java.util.Date date63 = personEthnicity62.getLastUpdatedTime();
        java.util.Date date64 = personEthnicity62.getCreateDateTime();
        java.lang.String str65 = gov.ca.cwds.rest.api.domain.DomainChef.cookISO8601Timestamp(date64);
        participantEntity59.setDateOfBirth(date64);
        participantEntity50.setDateOfDeath(date64);
        gov.ca.cwds.data.persistence.ns.Person person72 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity73 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity74 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person72, ethnicity73);
        java.util.Date date75 = personEthnicity74.getLastUpdatedTime();
        java.util.Date date76 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date75);
        java.lang.String str77 = gov.ca.cwds.rest.api.domain.DomainChef.cookTime(date76);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship78 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("yyyy-MM-dd'T'HH:mm:ss.SSSZ", (java.lang.Short) (short) -1, date64, "15:53:04", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", "{\"id\":null,\"first_name\":null,\"last_name\":null,\"name_suffix\":null,\"role\":null,\"legacy_descriptor\":{\"legacy_id\":null,\"legacy_ui_id\":null,\"legacy_last_updated\":null,\"legacy_table_name\":null,\"legacy_table_description\":null}}", "15:53:07", date76);
        participantEntity40.setDateOfDeath(date76);
        java.util.Date date80 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date76);
        java.lang.Object obj81 = stringArrayType0.nullSafeGet(resultSet22, strArray38, sharedSessionContractImplementor39, (java.lang.Object) date76);
        java.sql.PreparedStatement preparedStatement82 = null;
        gov.ca.cwds.data.persistence.ns.CsecEntity csecEntity83 = new gov.ca.cwds.data.persistence.ns.CsecEntity();
        csecEntity83.setParticipantId("Y");
        csecEntity83.setParticipantId("2019-09-03T15:53:00.960Z");
        java.lang.String str88 = csecEntity83.getParticipantId();
        org.hibernate.engine.spi.SharedSessionContractImplementor sharedSessionContractImplementor90 = null;
        // The following exception was thrown during execution in test generation
        try {
            stringArrayType0.nullSafeSet(preparedStatement82, (java.lang.Object) str88, 10, sharedSessionContractImplementor90);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        // Expected exception.
        }
        org.junit.Assert.assertTrue("'" + role1 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.NON_MANDATED_REPORTER + "'", role1.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.NON_MANDATED_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray8);
        org.junit.Assert.assertTrue("'" + obj9 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.NON_MANDATED_REPORTER + "'", obj9.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.NON_MANDATED_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArrayClass10);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(obj20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(intArray21);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean24);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str35);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray38);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean41);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str46);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str47);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean51);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(csecEntityArray52);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean54 + "' != '" + false + "'", boolean54 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity56);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str57);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str58);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date63);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date63.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date64);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date64.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str65 + "' != '" + "2019-09-03T15:53:49.582Z" + "'", str65.equals("2019-09-03T15:53:49.582Z"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date75);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date75.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date76);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date76.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str77 + "' != '" + "15:53:49" + "'", str77.equals("15:53:49"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date80);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date80.toString(), "Tue Sep 03 15:53:49 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(obj81);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str88 + "' != '" + "2019-09-03T15:53:00.960Z" + "'", str88.equals("2019-09-03T15:53:00.960Z"));
    }
}
