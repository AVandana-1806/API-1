import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegressionTest3 {

    public static boolean debug = false;

    @Test
    public void test1501() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1501");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter10 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role11 = hOIReporter10.getRole();
        java.lang.String str12 = hOIReporter10.getId();
        boolean boolean13 = address8.equals((java.lang.Object) hOIReporter10);
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor14 = hOIReporter10.getLegacyDescriptor();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor15 = hOIReporter10.getLegacyDescriptor();
        hOIReporter10.setId("2019-09-03-15.43.59.198");
        java.lang.String str18 = hOIReporter10.getFirstName();
        gov.ca.cwds.data.persistence.ns.Race race22 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        java.lang.String str23 = race22.getSubRaceType();
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonRace> personRaceSet24 = race22.getPersonRace();
        boolean boolean25 = hOIReporter10.equals((java.lang.Object) personRaceSet24);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str18);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str23 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSSZ" + "'", str23.equals("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personRaceSet24);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + false + "'", boolean25 == false);
    }

    @Test
    public void test1502() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1502");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType0 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        gov.ca.cwds.data.persistence.ns.Race race4 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType5 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean6 = stringJsonUserType5.isMutable();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship8 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date9 = clientRelationship8.getLastUpdatedTime();
        boolean boolean10 = stringJsonUserType5.equals((java.lang.Object) "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", (java.lang.Object) clientRelationship8);
        gov.ca.cwds.data.persistence.ns.Race race11 = new gov.ca.cwds.data.persistence.ns.Race();
        gov.ca.cwds.rest.api.domain.Race race12 = new gov.ca.cwds.rest.api.domain.Race(race11);
        gov.ca.cwds.rest.api.domain.Address[] addressArray20 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet21 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean22 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet21, addressArray20);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray23 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet24 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean25 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet24, phoneNumberArray23);
        gov.ca.cwds.rest.api.domain.Language[] languageArray26 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet27 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean28 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet27, languageArray26);
        gov.ca.cwds.rest.api.domain.Race[] raceArray29 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet30 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean31 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet30, raceArray29);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity34 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray35 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity34 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet36 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean37 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet36, ethnicityArray35);
        gov.ca.cwds.rest.api.domain.Person person38 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet21, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet24, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet27, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet30, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet36);
        java.lang.Object obj39 = stringJsonUserType5.assemble((java.io.Serializable) race12, (java.lang.Object) "");
        java.lang.Class class40 = stringJsonUserType5.returnedClass();
        java.lang.Object obj41 = stringJsonUserType0.assemble((java.io.Serializable) 0L, (java.lang.Object) class40);
        gov.ca.cwds.data.persistence.ns.Person person42 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity43 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity44 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person42, ethnicity43);
        java.util.Date date45 = personEthnicity44.getLastUpdatedTime();
        java.lang.Object obj46 = stringJsonUserType0.deepCopy((java.lang.Object) personEthnicity44);
        gov.ca.cwds.data.persistence.ns.Person person47 = null;
        personEthnicity44.setPerson(person47);
        java.util.Date date49 = personEthnicity44.getCreateDateTime();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + true + "'", boolean6 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean22 + "' != '" + false + "'", boolean22 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + false + "'", boolean25 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean28 + "' != '" + false + "'", boolean28 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean31 + "' != '" + false + "'", boolean31 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray35);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean37 + "' != '" + true + "'", boolean37 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj39);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(class40);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + obj41 + "' != '" + 0L + "'", obj41.equals(0L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date45);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date45.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj46);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date49);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date49.toString(), "Tue Sep 03 15:44:15 PDT 2019");
    }

    @Test
    public void test1503() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1503");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role0 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        java.lang.String str1 = role0.toString();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor6 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor6.setId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.lang.String str9 = legacyDescriptor6.getUiId();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter10 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role0, "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s_z", "yyyy-MM-dd-HH.mm.ss.SSS", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", legacyDescriptor6);
        org.joda.time.DateTime dateTime17 = null;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor20 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor("_anonymous _reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", dateTime17, "yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter21 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role0, "yyyy-MM-dd-HH.mm.ss.SSS", "hi!", "15:43:22", "", legacyDescriptor20);
        java.lang.String str22 = hOIReporter21.getLastName();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor23 = hOIReporter21.getLegacyDescriptor();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity24 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str25 = participantEntity24.getLegacyId();
        java.lang.String[] strArray26 = participantEntity24.getLanguages();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity27 = participantEntity24.getScreeningEntity();
        boolean boolean28 = hOIReporter21.equals((java.lang.Object) participantEntity24);
        participantEntity24.setApproximateAgeUnits("2019-09-03-15.43.41.671");
        java.lang.String str31 = participantEntity24.getFirstName();
        participantEntity24.setNameSuffix("");
        org.junit.Assert.assertTrue("'" + role0 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role0.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str1 + "' != '" + "Anonymous Reporter" + "'", str1.equals("Anonymous Reporter"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str22 + "' != '" + "15:43:22" + "'", str22.equals("15:43:22"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str25);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity27);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean28 + "' != '" + false + "'", boolean28 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str31);
    }

    @Test
    public void test1504() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1504");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        hOIReporter0.setLastName("15:43:19");
        java.lang.String str3 = hOIReporter0.getFirstName();
        java.lang.String str4 = hOIReporter0.getFirstName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
    }

    @Test
    public void test1505() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1505");
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity2 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList3 = ethnicity2.getMessages();
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity6 = new gov.ca.cwds.data.persistence.ns.Ethnicity(ethnicity2, "2019-09-03T15:43:21.461-0700", "15:43:19");
        java.lang.String str7 = ethnicity2.getSubEthnicity();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str7 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str7.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    @Test
    public void test1506() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1506");
        gov.ca.cwds.rest.api.domain.Language language1 = new gov.ca.cwds.rest.api.domain.Language("2019-09-03-15.43.38.179");
        java.lang.String str2 = language1.getTheLanguage();
        gov.ca.cwds.data.persistence.ns.Person person3 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity7 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity8 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person3, ethnicity7);
        gov.ca.cwds.rest.api.domain.Address[] addressArray16 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet17 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean18 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet17, addressArray16);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray19 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet20 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean21 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet20, phoneNumberArray19);
        gov.ca.cwds.rest.api.domain.Language[] languageArray22 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet23 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean24 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet23, languageArray22);
        gov.ca.cwds.rest.api.domain.Race[] raceArray25 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet26 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean27 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet26, raceArray25);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity30 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray31 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity30 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet32 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean33 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet32, ethnicityArray31);
        gov.ca.cwds.rest.api.domain.Person person34 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet17, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet20, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet23, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet26, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet32);
        java.lang.String str35 = person34.getSsn();
        java.lang.String str36 = person34.getNameSuffix();
        java.lang.String str37 = person34.getLastName();
        java.util.Set<gov.ca.cwds.rest.api.domain.Race> raceSet38 = person34.getRace();
        boolean boolean39 = personEthnicity8.equals((java.lang.Object) raceSet38);
        gov.ca.cwds.data.persistence.ns.Person person40 = null;
        personEthnicity8.setPerson(person40);
        boolean boolean42 = language1.equals((java.lang.Object) personEthnicity8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str2 + "' != '" + "2019-09-03-15.43.38.179" + "'", str2.equals("2019-09-03-15.43.38.179"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray19);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray22);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean24 + "' != '" + false + "'", boolean24 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray25);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray31);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean33 + "' != '" + true + "'", boolean33 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str35 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str35.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str36 + "' != '" + "" + "'", str36.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str37 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str37.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceSet38);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean39 + "' != '" + false + "'", boolean39 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean42 + "' != '" + false + "'", boolean42 == false);
    }

    @Test
    public void test1507() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1507");
        gov.ca.cwds.data.persistence.ns.CsecEntity csecEntity0 = new gov.ca.cwds.data.persistence.ns.CsecEntity();
        gov.ca.cwds.rest.api.domain.Address[] addressArray8 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet9 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean10 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet9, addressArray8);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray11 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet12 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean13 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, phoneNumberArray11);
        gov.ca.cwds.rest.api.domain.Language[] languageArray14 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet15 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet15, languageArray14);
        gov.ca.cwds.rest.api.domain.Race[] raceArray17 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet18 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean19 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet18, raceArray17);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity22 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray23 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity22 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet24 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean25 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24, ethnicityArray23);
        gov.ca.cwds.rest.api.domain.Person person26 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet9, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet15, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet18, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24);
        java.lang.String str27 = person26.getSsn();
        gov.ca.cwds.data.persistence.ns.Person person28 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity29 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity30 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person28, ethnicity29);
        java.util.Date date31 = personEthnicity30.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person32 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity33 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity34 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person32, ethnicity33);
        java.util.Date date35 = personEthnicity34.getLastUpdatedTime();
        boolean boolean36 = personEthnicity30.equals((java.lang.Object) personEthnicity34);
        boolean boolean37 = person26.equals((java.lang.Object) boolean36);
        gov.ca.cwds.rest.api.domain.error.ErrorMessage errorMessage38 = null;
        person26.addMessage(errorMessage38);
        java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet40 = person26.getPhoneNumber();
        java.lang.String str41 = person26.getGender();
        java.lang.String str42 = person26.getLastName();
        java.lang.String str43 = person26.getMiddleName();
        boolean boolean44 = csecEntity0.equals((java.lang.Object) person26);
        java.time.LocalDate localDate45 = null;
        csecEntity0.setStartDate(localDate45);
        java.time.LocalDate localDate47 = null;
        csecEntity0.setStartDate(localDate47);
        java.lang.String str49 = csecEntity0.getParticipantId();
        java.io.Serializable serializable50 = csecEntity0.getPrimaryKey();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + true + "'", boolean25 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str27 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str27.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date31);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date31.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date35);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date35.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean37 + "' != '" + false + "'", boolean37 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberSet40);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str41 + "' != '" + "hi!" + "'", str41.equals("hi!"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str42 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str42.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str43 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str43.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean44 + "' != '" + false + "'", boolean44 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str49);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable50);
    }

    @Test
    public void test1508() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1508");
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
        java.lang.String str27 = person25.getNameSuffix();
        java.lang.String str28 = person25.getLastName();
        java.util.Set<gov.ca.cwds.rest.api.domain.Language> languageSet29 = person25.getLanguage();
        boolean boolean31 = person25.equals((java.lang.Object) "15:43:38");
        gov.ca.cwds.rest.api.domain.error.ErrorMessage errorMessage32 = null;
        person25.addMessage(errorMessage32);
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
        org.junit.Assert.assertTrue("'" + str27 + "' != '" + "" + "'", str27.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str28.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageSet29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean31 + "' != '" + false + "'", boolean31 == false);
    }

    @Test
    public void test1509() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1509");
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
        gov.ca.cwds.data.persistence.ns.PersonEthnicityId personEthnicityId14 = personEthnicity2.getPrimaryKey();
        java.io.Serializable serializable15 = personEthnicityId14.getPrimaryKey();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship16 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship16.setLastUpdatedId("");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship19 = null;
        boolean boolean20 = clientRelationship16.relatedTo(clientRelationship19);
        java.lang.String str21 = clientRelationship16.getAbsentParentCode();
        boolean boolean22 = personEthnicityId14.equals((java.lang.Object) clientRelationship16);
        gov.ca.cwds.rest.api.domain.Address[] addressArray30 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet31 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean32 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet31, addressArray30);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray33 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet34 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean35 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet34, phoneNumberArray33);
        gov.ca.cwds.rest.api.domain.Language[] languageArray36 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet37 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean38 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet37, languageArray36);
        gov.ca.cwds.rest.api.domain.Race[] raceArray39 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet40 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean41 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet40, raceArray39);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity44 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray45 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity44 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet46 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean47 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet46, ethnicityArray45);
        gov.ca.cwds.rest.api.domain.Person person48 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet31, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet34, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet37, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet40, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet46);
        java.lang.String str49 = person48.getSsn();
        gov.ca.cwds.data.persistence.ns.Person person50 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity51 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity52 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person50, ethnicity51);
        java.util.Date date53 = personEthnicity52.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person54 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity55 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity56 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person54, ethnicity55);
        java.util.Date date57 = personEthnicity56.getLastUpdatedTime();
        boolean boolean58 = personEthnicity52.equals((java.lang.Object) personEthnicity56);
        boolean boolean59 = person48.equals((java.lang.Object) boolean58);
        gov.ca.cwds.rest.api.domain.error.ErrorMessage errorMessage60 = null;
        person48.addMessage(errorMessage60);
        java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet62 = person48.getPhoneNumber();
        java.util.Set<gov.ca.cwds.rest.api.domain.Race> raceSet63 = person48.getRace();
        java.util.Set<gov.ca.cwds.rest.api.domain.Race> raceSet64 = person48.getRace();
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity65 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str66 = safelySurrenderedBabiesEntity65.toString();
        java.util.Date date67 = safelySurrenderedBabiesEntity65.getMedQuestionaireReturnDate();
        java.lang.String str68 = safelySurrenderedBabiesEntity65.getRelationToChild();
        java.lang.String str69 = safelySurrenderedBabiesEntity65.getComments();
        boolean boolean70 = person48.equals((java.lang.Object) safelySurrenderedBabiesEntity65);
        java.util.Set<gov.ca.cwds.rest.api.domain.Address> addressSet71 = person48.getAddress();
        boolean boolean72 = personEthnicityId14.equals((java.lang.Object) addressSet71);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date3);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date3.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date7);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date7.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean8 + "' != '" + true + "'", boolean8 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personEthnicityId14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str21);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean22 + "' != '" + false + "'", boolean22 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray30);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean32 + "' != '" + false + "'", boolean32 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray33);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean35 + "' != '" + false + "'", boolean35 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray36);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean38 + "' != '" + false + "'", boolean38 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray39);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean41 + "' != '" + false + "'", boolean41 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray45);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean47 + "' != '" + true + "'", boolean47 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str49 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str49.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date53);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date53.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date57);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date57.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean58 + "' != '" + true + "'", boolean58 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean59 + "' != '" + false + "'", boolean59 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberSet62);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceSet63);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceSet64);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date67);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str68);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str69);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean70 + "' != '" + false + "'", boolean70 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressSet71);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean72 + "' != '" + false + "'", boolean72 == false);
    }

    @Test
    public void test1510() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1510");
        gov.ca.cwds.data.persistence.ns.Person person0 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity1 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity2 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person0, ethnicity1);
        java.util.Date date3 = personEthnicity2.getLastUpdatedTime();
        java.util.Date date4 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date3);
        java.lang.String str5 = gov.ca.cwds.rest.api.domain.DomainChef.cookTimestamp(date3);
        java.lang.String str6 = gov.ca.cwds.rest.api.domain.DomainChef.cookTime(date3);
        gov.ca.cwds.data.persistence.ns.Person person9 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity13 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity14 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person9, ethnicity13);
        java.lang.String str15 = personEthnicity14.getLastUpdatedId();
        java.util.Date date16 = personEthnicity14.getCreateDateTime();
        gov.ca.cwds.data.persistence.ns.Person person21 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity22 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity23 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person21, ethnicity22);
        java.util.Date date24 = personEthnicity23.getLastUpdatedTime();
        java.util.Date date25 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date24);
        java.lang.String str26 = gov.ca.cwds.rest.api.domain.DomainChef.cookTimestamp(date24);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship27 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", (java.lang.Short) (short) 100, date16, "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId", "yyyy-MM-dd HH:mm:ss.SSS", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds", date24);
        java.util.Date date28 = clientRelationship27.getStartDate();
        java.util.Date date29 = gov.ca.cwds.rest.api.domain.DomainChef.concatenateDateAndTime(date3, date28);
        java.util.Date date30 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date3);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date3.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date4);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date4.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str5 + "' != '" + "2019-09-03-15.44.15.785" + "'", str5.equals("2019-09-03-15.44.15.785"));
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str6 + "' != '" + "15:44:15" + "'", str6.equals("15:44:15"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date16);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date16.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date24);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date24.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date25);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date25.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str26 + "' != '" + "2019-09-03-15.44.15.785" + "'", str26.equals("2019-09-03-15.44.15.785"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date28);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date28.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date29);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date29.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date30);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date30.toString(), "Tue Sep 03 15:44:15 PDT 2019");
    }

    @Test
    public void test1511() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1511");
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
        java.lang.String str27 = person25.getSsn();
        java.lang.String str28 = person25.getSsn();
        java.lang.String str29 = person25.getLastName();
        java.lang.String str30 = person25.getBirthDate();
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
        org.junit.Assert.assertTrue("'" + str27 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str27.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str28.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str29 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str29.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str30 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId" + "'", str30.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId"));
    }

    @Test
    public void test1512() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1512");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship0.setLastUpdatedId("");
        java.util.Date date3 = clientRelationship0.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship4 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship4.setLastUpdatedId("");
        java.util.Date date7 = clientRelationship4.getLastUpdatedTime();
        java.lang.String str8 = clientRelationship4.getSecondaryClientId();
        boolean boolean9 = clientRelationship0.relatedTo(clientRelationship4);
        java.util.Date date10 = clientRelationship4.getStartDate();
        clientRelationship4.setLastUpdatedId("2019-09-03-15.43.38.932");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship13 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.lang.String str14 = clientRelationship13.getSameHomeCode();
        java.lang.String str15 = clientRelationship13.getSecondaryClientId();
        boolean boolean16 = clientRelationship4.relatedTo(clientRelationship13);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship17 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date18 = clientRelationship17.getLastUpdatedTime();
        java.lang.String str19 = clientRelationship17.getSecondaryClientId();
        java.lang.String str20 = clientRelationship17.getId();
        boolean boolean21 = clientRelationship13.relatedTo(clientRelationship17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date10);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str14 + "' != '" + "" + "'", str14.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date18);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str19);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
    }

    @Test
    public void test1513() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1513");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship0.setLastUpdatedId("");
        java.util.Date date3 = clientRelationship0.getStartDate();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter4 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        hOIReporter4.setLastName("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId");
        boolean boolean7 = clientRelationship0.equals((java.lang.Object) hOIReporter4);
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor15 = null;
        gov.ca.cwds.rest.api.domain.Address address16 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor15);
        java.lang.String str17 = address16.getCity();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter18 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role19 = hOIReporter18.getRole();
        java.lang.String str20 = hOIReporter18.getId();
        boolean boolean21 = address16.equals((java.lang.Object) hOIReporter18);
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor22 = hOIReporter18.getLegacyDescriptor();
        org.joda.time.DateTime dateTime23 = legacyDescriptor22.getLastUpdated();
        hOIReporter4.setLegacyDescriptor(legacyDescriptor22);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean7 + "' != '" + false + "'", boolean7 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str17 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str17.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role19);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor22);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(dateTime23);
    }

    @Test
    public void test1514() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1514");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        java.lang.Boolean boolean3 = participantEntity0.getSealed();
        participantEntity0.setSensitive((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.Person person6 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity7 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity8 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person6, ethnicity7);
        java.util.Date date9 = personEthnicity8.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person10 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity11 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity12 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person10, ethnicity11);
        java.util.Date date13 = personEthnicity12.getLastUpdatedTime();
        boolean boolean14 = personEthnicity8.equals((java.lang.Object) personEthnicity12);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity18 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity8.setEthnicity(ethnicity18);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity20 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity18);
        java.lang.Long long21 = ethnicity18.getPrimaryKey();
        boolean boolean22 = participantEntity0.equals((java.lang.Object) ethnicity18);
        participantEntity0.setProbationYouth((java.lang.Boolean) false);
        java.lang.String str25 = participantEntity0.getApproximateAgeUnits();
        java.lang.String[] strArray26 = participantEntity0.getRoles();
        java.util.Date date27 = participantEntity0.getDateOfBirth();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date9);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date9.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date13);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date13.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + true + "'", boolean14 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long21 + "' != '" + 1L + "'", long21.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean22 + "' != '" + false + "'", boolean22 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str25);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date27);
    }

    @Test
    public void test1515() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1515");
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
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList27 = person25.getMessages();
        person25.doSomething();
        java.lang.String str29 = person25.getLastName();
        java.lang.String str30 = person25.getGender();
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
        org.junit.Assert.assertNotNull(errorMessageList27);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str29 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str29.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str30 + "' != '" + "hi!" + "'", str30.equals("hi!"));
    }

    @Test
    public void test1516() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1516");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        java.lang.Boolean boolean3 = participantEntity0.getSealed();
        participantEntity0.setSensitive((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.Person person6 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity7 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity8 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person6, ethnicity7);
        java.util.Date date9 = personEthnicity8.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person10 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity11 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity12 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person10, ethnicity11);
        java.util.Date date13 = personEthnicity12.getLastUpdatedTime();
        boolean boolean14 = personEthnicity8.equals((java.lang.Object) personEthnicity12);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity18 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity8.setEthnicity(ethnicity18);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity20 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity18);
        java.lang.Long long21 = ethnicity18.getPrimaryKey();
        boolean boolean22 = participantEntity0.equals((java.lang.Object) ethnicity18);
        participantEntity0.setProbationYouth((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity25 = participantEntity0.getScreening();
        java.lang.String str26 = participantEntity0.getNameSuffix();
        participantEntity0.setGender("2019-09-03T15:43:36.386-0700");
        java.lang.String str29 = participantEntity0.getScreeningId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date9);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date9.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date13);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date13.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + true + "'", boolean14 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long21 + "' != '" + 1L + "'", long21.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean22 + "' != '" + false + "'", boolean22 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity25);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str29);
    }

    @Test
    public void test1517() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1517");
        gov.ca.cwds.data.persistence.ns.Race race3 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "15:43:46", "15:43:44");
        java.lang.Long long4 = race3.getPrimaryKey();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long4 + "' != '" + 0L + "'", long4.equals(0L));
    }

    @Test
    public void test1518() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1518");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        java.lang.String str3 = participantEntity0.getRelatedScreeningId();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity4 = null;
        participantEntity0.setScreeningEntity(screeningEntity4);
        java.lang.String str6 = participantEntity0.getLastName();
        participantEntity0.setRaces("2019-09-03-15.43.58.634");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
    }

    @Test
    public void test1519() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1519");
        gov.ca.cwds.data.persistence.ns.Race race3 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 1L, "15:43:27", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId");
    }

    @Test
    public void test1520() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1520");
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
        java.lang.String str27 = person25.getNameSuffix();
        java.lang.String str28 = person25.getLastName();
        java.util.Set<gov.ca.cwds.rest.api.domain.Language> languageSet29 = person25.getLanguage();
        java.lang.String str30 = person25.getNameSuffix();
        java.lang.String str31 = person25.getNameSuffix();
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
        org.junit.Assert.assertTrue("'" + str27 + "' != '" + "" + "'", str27.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str28.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageSet29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str30 + "' != '" + "" + "'", str30.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str31 + "' != '" + "" + "'", str31.equals(""));
    }

    @Test
    public void test1521() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1521");
        gov.ca.cwds.data.persistence.ns.Person person0 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity4 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity5 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person0, ethnicity4);
        gov.ca.cwds.data.persistence.ns.Person person6 = null;
        personEthnicity5.setPerson(person6);
    }

    @Test
    public void test1522() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1522");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str1 = safelySurrenderedBabiesEntity0.toString();
        java.lang.String str2 = safelySurrenderedBabiesEntity0.getSurrenderedBy();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity3 = safelySurrenderedBabiesEntity0.getParticipantEntity();
        java.lang.String str4 = safelySurrenderedBabiesEntity0.getParentGuardGivenBraceletId();
        java.util.Date date5 = safelySurrenderedBabiesEntity0.getMedQuestionaireReturnDate();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(participantEntity3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date5);
    }

    @Test
    public void test1523() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1523");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str1 = safelySurrenderedBabiesEntity0.toString();
        java.lang.String str2 = safelySurrenderedBabiesEntity0.getSurrenderedBy();
        java.lang.String str3 = safelySurrenderedBabiesEntity0.getBraceletId();
        safelySurrenderedBabiesEntity0.setParentGuardGivenBraceletId("");
        java.lang.String str6 = safelySurrenderedBabiesEntity0.getParentGuardProvMedQuestion();
        gov.ca.cwds.data.persistence.ns.Person person7 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity8 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity9 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person7, ethnicity8);
        java.util.Date date10 = personEthnicity9.getLastUpdatedTime();
        java.util.Date date11 = personEthnicity9.getCreateDateTime();
        java.util.Date date12 = personEthnicity9.getCreateDateTime();
        safelySurrenderedBabiesEntity0.setMedQuestionaireReturnDate(date12);
        java.lang.String str14 = safelySurrenderedBabiesEntity0.getRelationToChild();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship17 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship17.setLastUpdatedId("");
        java.util.Date date20 = clientRelationship17.getLastUpdatedTime();
        java.lang.String str21 = clientRelationship17.getSameHomeCode();
        gov.ca.cwds.data.persistence.ns.Person person24 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity25 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity26 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person24, ethnicity25);
        java.util.Date date27 = personEthnicity26.getLastUpdatedTime();
        java.lang.String str28 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date27);
        java.lang.String str29 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date27);
        gov.ca.cwds.data.persistence.ns.Person person34 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity35 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity36 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person34, ethnicity35);
        java.util.Date date37 = personEthnicity36.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person38 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity39 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity40 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person38, ethnicity39);
        java.util.Date date41 = personEthnicity40.getLastUpdatedTime();
        boolean boolean42 = personEthnicity36.equals((java.lang.Object) personEthnicity40);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity46 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity36.setEthnicity(ethnicity46);
        java.util.Date date48 = personEthnicity36.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship49 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Short) (short) 1, date27, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "hi!", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", date48);
        clientRelationship17.setLastUpdatedTime(date27);
        gov.ca.cwds.data.persistence.ns.Person person55 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity56 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity57 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person55, ethnicity56);
        java.util.Date date58 = personEthnicity57.getLastUpdatedTime();
        java.util.Date date59 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date58);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship60 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("2019-09-03T15:43:21.432-0700", (java.lang.Short) (short) 100, date27, "_anonymous _reporter", "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'", "15:43:23", "2019-09-03-15.43.20.156", date59);
        safelySurrenderedBabiesEntity0.setMedQuestionaireReturnDate(date59);
        java.lang.String str62 = safelySurrenderedBabiesEntity0.getParticipantId();
        java.io.Serializable serializable63 = safelySurrenderedBabiesEntity0.getPrimaryKey();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date10);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date10.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date11);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date11.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date12);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date12.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str21 + "' != '" + "" + "'", str21.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date27);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date27.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "2019-09-03" + "'", str28.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str29 + "' != '" + "2019-09-03" + "'", str29.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date37);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date37.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date41);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date41.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean42 + "' != '" + true + "'", boolean42 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date48);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date48.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date58);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date58.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date59);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date59.toString(), "Tue Sep 03 15:44:15 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str62);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable63);
    }

    @Test
    public void test1524() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1524");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship0.setLastUpdatedId("");
        clientRelationship0.setLastUpdatedId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship5 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship5.setLastUpdatedId("");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship8 = null;
        boolean boolean9 = clientRelationship5.relatedTo(clientRelationship8);
        java.util.Date date10 = clientRelationship5.getStartDate();
        boolean boolean11 = clientRelationship0.relatedTo(clientRelationship5);
        gov.ca.cwds.data.persistence.ns.Person person12 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity13 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity14 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person12, ethnicity13);
        java.util.Date date15 = personEthnicity14.getLastUpdatedTime();
        java.util.Date date16 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date15);
        java.lang.String str17 = gov.ca.cwds.rest.api.domain.DomainChef.cookTimestamp(date15);
        java.lang.String str18 = gov.ca.cwds.rest.api.domain.DomainChef.cookTime(date15);
        boolean boolean19 = clientRelationship0.equals((java.lang.Object) date15);
        java.lang.String str20 = clientRelationship0.getId();
        gov.ca.cwds.data.persistence.ns.Person person21 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity25 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity26 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person21, ethnicity25);
        gov.ca.cwds.rest.api.domain.Address[] addressArray34 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet35 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet35, addressArray34);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray37 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet38 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean39 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet38, phoneNumberArray37);
        gov.ca.cwds.rest.api.domain.Language[] languageArray40 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet41 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean42 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet41, languageArray40);
        gov.ca.cwds.rest.api.domain.Race[] raceArray43 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet44 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean45 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet44, raceArray43);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity48 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray49 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity48 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet50 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean51 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet50, ethnicityArray49);
        gov.ca.cwds.rest.api.domain.Person person52 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet35, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet38, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet41, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet44, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet50);
        java.lang.String str53 = person52.getSsn();
        java.lang.String str54 = person52.getNameSuffix();
        java.lang.String str55 = person52.getLastName();
        java.util.Set<gov.ca.cwds.rest.api.domain.Race> raceSet56 = person52.getRace();
        boolean boolean57 = personEthnicity26.equals((java.lang.Object) raceSet56);
        java.util.Date date58 = personEthnicity26.getCreateDateTime();
        clientRelationship0.setLastUpdatedTime(date58);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date10);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean11 + "' != '" + false + "'", boolean11 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date15);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date15.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date16);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date16.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str17 + "' != '" + "2019-09-03-15.44.16.023" + "'", str17.equals("2019-09-03-15.44.16.023"));
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str18 + "' != '" + "15:44:16" + "'", str18.equals("15:44:16"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray34);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + false + "'", boolean36 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray37);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean39 + "' != '" + false + "'", boolean39 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray40);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean42 + "' != '" + false + "'", boolean42 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray43);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean45 + "' != '" + false + "'", boolean45 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray49);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean51 + "' != '" + true + "'", boolean51 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str53 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str53.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str54 + "' != '" + "" + "'", str54.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str55 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str55.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceSet56);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean57 + "' != '" + false + "'", boolean57 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date58);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date58.toString(), "Tue Sep 03 15:44:16 PDT 2019");
    }

    @Test
    public void test1525() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1525");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        java.lang.Boolean boolean3 = participantEntity0.getSealed();
        java.lang.String str4 = participantEntity0.getRelatedScreeningId();
        java.lang.Boolean boolean5 = participantEntity0.getSealed();
        java.lang.String str6 = participantEntity0.getMiddleName();
        java.lang.String str7 = participantEntity0.getSsn();
        java.lang.String str8 = participantEntity0.getRelatedScreeningId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str8);
    }

    @Test
    public void test1526() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1526");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        participantEntity0.setScreeningId("15:43:19");
        java.lang.String str5 = participantEntity0.getMiddleName();
        java.lang.String str6 = participantEntity0.getRelatedScreeningId();
        participantEntity0.setLegacySourceTable("2019-09-03-15.44.10.245");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
    }

    @Test
    public void test1527() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1527");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType0 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean1 = stringJsonUserType0.isMutable();
        int[] intArray2 = stringJsonUserType0.sqlTypes();
        java.lang.Class class3 = stringJsonUserType0.returnedClass();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean1 + "' != '" + true + "'", boolean1 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(intArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(class3);
    }

    @Test
    public void test1528() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1528");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        hOIReporter0.setLastName("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId");
        java.lang.String str3 = hOIReporter0.getId();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor4 = hOIReporter0.getLegacyDescriptor();
        hOIReporter0.setLastName("2019-09-03-15.44.13.436");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor4);
    }

    @Test
    public void test1529() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1529");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role1 = hOIReporter0.getRole();
        java.lang.String str2 = hOIReporter0.getId();
        java.lang.String str3 = hOIReporter0.getId();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role4 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        java.lang.String str5 = role4.toString();
        hOIReporter0.setRole(role4);
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role7 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor12 = null;
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter13 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role7, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "Anonymous Reporter", legacyDescriptor12);
        java.lang.String str14 = hOIReporter13.getFirstName();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role16 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.fromString("Anonymous Reporter");
        hOIReporter13.setRole(role16);
        hOIReporter0.setRole(role16);
        java.lang.String str19 = hOIReporter0.getFirstName();
        hOIReporter0.setFirstName("2019-09-03-15.44.06.865");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        org.junit.Assert.assertTrue("'" + role4 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role4.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str5 + "' != '" + "Anonymous Reporter" + "'", str5.equals("Anonymous Reporter"));
        org.junit.Assert.assertTrue("'" + role7 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role7.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str14 + "' != '" + "Anonymous Reporter" + "'", str14.equals("Anonymous Reporter"));
        org.junit.Assert.assertTrue("'" + role16 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role16.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str19);
    }

    @Test
    public void test1530() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1530");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        participantEntity0.setScreeningId("15:43:19");
        java.lang.String str5 = participantEntity0.getRaces();
        participantEntity0.setId("00100");
        participantEntity0.setEstimatedDob((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship10 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date11 = clientRelationship10.getLastUpdatedTime();
        java.lang.String str12 = clientRelationship10.getSecondaryClientId();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship13 = null;
        boolean boolean14 = clientRelationship10.relatedTo(clientRelationship13);
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity15 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str16 = participantEntity15.getLegacyId();
        java.lang.String[] strArray17 = participantEntity15.getLanguages();
        participantEntity15.setScreeningId("15:43:19");
        java.lang.String str20 = participantEntity15.getLegacySourceTable();
        participantEntity15.setSealed((java.lang.Boolean) false);
        participantEntity15.setLegacyId("2019-09-03-15.43.40.464");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity25 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str26 = safelySurrenderedBabiesEntity25.toString();
        java.lang.String str27 = safelySurrenderedBabiesEntity25.toString();
        java.lang.String str28 = safelySurrenderedBabiesEntity25.getParticipantId();
        safelySurrenderedBabiesEntity25.setRelationToChild("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.lang.String str31 = safelySurrenderedBabiesEntity25.getRelationToChild();
        participantEntity15.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity25);
        safelySurrenderedBabiesEntity25.setParticipantId("2019-09-03T15:43:41.574-0700");
        boolean boolean35 = clientRelationship10.equals((java.lang.Object) safelySurrenderedBabiesEntity25);
        java.util.Date date36 = safelySurrenderedBabiesEntity25.getMedQuestionaireReturnDate();
        participantEntity0.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity25);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + false + "'", boolean14 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str31 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds" + "'", str31.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean35 + "' != '" + false + "'", boolean35 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date36);
    }

    @Test
    public void test1531() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1531");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role0 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor5 = null;
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter6 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role0, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "Anonymous Reporter", legacyDescriptor5);
        java.lang.String str7 = role0.toString();
        java.lang.String str8 = role0.getDescription();
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
        hOIReporter13.setFirstName("15:43:22");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor43 = hOIReporter13.getLegacyDescriptor();
        legacyDescriptor43.setTableName("2019-09-03-15.43.29.362");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter46 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role0, "15:44:12", "Self Reported", "15:44:05", "yyyy-MM-dd", legacyDescriptor43);
        org.junit.Assert.assertTrue("'" + role0 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role0.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str7 + "' != '" + "Anonymous Reporter" + "'", str7.equals("Anonymous Reporter"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str8 + "' != '" + "Anonymous Reporter" + "'", str8.equals("Anonymous Reporter"));
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
        org.junit.Assert.assertNotNull(legacyDescriptor43);
    }

    @Test
    public void test1532() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1532");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity3 = participantEntity0.getScreeningEntity();
        java.util.Date date4 = participantEntity0.getDateOfBirth();
        participantEntity0.setFirstName("15:43:44");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date4);
    }

    @Test
    public void test1533() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1533");
        gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber3 = new gov.ca.cwds.rest.api.domain.PhoneNumber("2019-09-03-15.43.56.336", "", "2019-09-03-15.44.04.622");
    }

    @Test
    public void test1534() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1534");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        java.lang.Boolean boolean3 = participantEntity0.getSealed();
        participantEntity0.setLegacySourceTable("2019-09-03T15:43:21.432-0700");
        java.lang.Boolean boolean6 = participantEntity0.getSealed();
        java.lang.Boolean boolean7 = participantEntity0.getEstimatedDob();
        java.lang.String str8 = participantEntity0.getSsn();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity9 = participantEntity0.getScreeningEntity();
        participantEntity0.setEstimatedDob((java.lang.Boolean) true);
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity12 = participantEntity0.getScreeningEntity();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity12);
    }

    @Test
    public void test1535() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1535");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        address8.setLegacySourceTable("Anonymous Reporter");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor12 = address8.getLegacyDescriptor();
        java.lang.String str13 = address8.getCity();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor14 = address8.getLegacyDescriptor();
        java.lang.Class<?> wildcardClass15 = address8.getClass();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str13 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str13.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(wildcardClass15);
    }

    @Test
    public void test1536() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1536");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType0 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor8 = null;
        gov.ca.cwds.rest.api.domain.Address address9 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor8);
        java.lang.String str10 = address9.getCity();
        address9.setLegacyId("");
        java.lang.String str13 = address9.getStreetAddress();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship14 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date15 = clientRelationship14.getLastUpdatedTime();
        java.lang.String str16 = clientRelationship14.getSecondaryClientId();
        java.lang.String str17 = clientRelationship14.getLastUpdatedId();
        gov.ca.cwds.rest.api.domain.Address[] addressArray25 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet26 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean27 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet26, addressArray25);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray28 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet29 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean30 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet29, phoneNumberArray28);
        gov.ca.cwds.rest.api.domain.Language[] languageArray31 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet32 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean33 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet32, languageArray31);
        gov.ca.cwds.rest.api.domain.Race[] raceArray34 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet35 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet35, raceArray34);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity39 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray40 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity39 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet41 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean42 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet41, ethnicityArray40);
        gov.ca.cwds.rest.api.domain.Person person43 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet26, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet29, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet32, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet35, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet41);
        java.lang.String str44 = person43.getSsn();
        java.lang.String str45 = person43.getNameSuffix();
        java.lang.String str46 = person43.getLastName();
        java.lang.Object obj47 = stringJsonUserType0.replace((java.lang.Object) address9, (java.lang.Object) str17, (java.lang.Object) str46);
        java.lang.Integer int48 = address9.getState();
        java.lang.String str49 = address9.getLegacyId();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor50 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor50.setId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.lang.String str53 = legacyDescriptor50.getUiId();
        legacyDescriptor50.setTableName("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId");
        address9.setLegacyDescriptor(legacyDescriptor50);
        java.lang.String str57 = address9.getLegacyId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str10 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str10.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str13 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str13.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray25);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean30 + "' != '" + false + "'", boolean30 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray31);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean33 + "' != '" + false + "'", boolean33 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray34);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + false + "'", boolean36 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray40);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean42 + "' != '" + true + "'", boolean42 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str44 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str44.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str45 + "' != '" + "" + "'", str45.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str46 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str46.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj47);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int48 + "' != '" + 100 + "'", int48.equals(100));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str49 + "' != '" + "" + "'", str49.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str53);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str57 + "' != '" + "" + "'", str57.equals(""));
    }

    @Test
    public void test1537() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1537");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        java.lang.Boolean boolean3 = participantEntity0.getSealed();
        participantEntity0.setLegacySourceTable("hi!");
        java.lang.String str6 = participantEntity0.getNameSuffix();
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity7 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str8 = safelySurrenderedBabiesEntity7.toString();
        java.util.Date date9 = safelySurrenderedBabiesEntity7.getMedQuestionaireReturnDate();
        java.lang.String str10 = safelySurrenderedBabiesEntity7.getRelationToChild();
        java.lang.String str11 = safelySurrenderedBabiesEntity7.getComments();
        participantEntity0.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity7);
        java.lang.String str13 = safelySurrenderedBabiesEntity7.getParentGuardGivenBraceletId();
        java.lang.String str14 = safelySurrenderedBabiesEntity7.toString();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str10);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str13);
    }

    @Test
    public void test1538() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1538");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.data.persistence.ns.Race race4 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        gov.ca.cwds.rest.api.domain.Race race5 = new gov.ca.cwds.rest.api.domain.Race(race4);
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonRace> personRaceSet6 = race4.getPersonRace();
        boolean boolean7 = hOIReporter0.equals((java.lang.Object) race4);
        hOIReporter0.setFirstName("2019-09-03-15.43.34.753");
        hOIReporter0.setId("Anonymous Reporter");
        java.lang.String str12 = hOIReporter0.getFirstName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personRaceSet6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean7 + "' != '" + false + "'", boolean7 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str12 + "' != '" + "2019-09-03-15.43.34.753" + "'", str12.equals("2019-09-03-15.43.34.753"));
    }

    @Test
    public void test1539() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1539");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity1 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity1.setLegacyId("N");
        java.lang.Boolean boolean4 = participantEntity1.getSealed();
        participantEntity1.setSensitive((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.Person person7 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity8 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity9 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person7, ethnicity8);
        java.util.Date date10 = personEthnicity9.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person11 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity12 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity13 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person11, ethnicity12);
        java.util.Date date14 = personEthnicity13.getLastUpdatedTime();
        boolean boolean15 = personEthnicity9.equals((java.lang.Object) personEthnicity13);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity19 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity9.setEthnicity(ethnicity19);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity21 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity19);
        java.lang.Long long22 = ethnicity19.getPrimaryKey();
        boolean boolean23 = participantEntity1.equals((java.lang.Object) ethnicity19);
        java.util.Date date24 = participantEntity1.getDateOfBirth();
        safelySurrenderedBabiesEntity0.setParticipantEntity(participantEntity1);
        participantEntity1.setEthnicity("");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date10);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date10.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date14);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date14.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + true + "'", boolean15 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long22 + "' != '" + 1L + "'", long22.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean23 + "' != '" + false + "'", boolean23 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date24);
    }

    @Test
    public void test1540() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1540");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.Address[] addressArray8 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet9 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean10 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet9, addressArray8);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray11 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet12 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean13 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, phoneNumberArray11);
        gov.ca.cwds.rest.api.domain.Language[] languageArray14 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet15 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet15, languageArray14);
        gov.ca.cwds.rest.api.domain.Race[] raceArray17 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet18 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean19 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet18, raceArray17);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity22 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray23 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity22 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet24 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean25 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24, ethnicityArray23);
        gov.ca.cwds.rest.api.domain.Person person26 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet9, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet15, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet18, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24);
        boolean boolean27 = hOIReporter0.equals((java.lang.Object) "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role28 = hOIReporter0.getRole();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role29 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor34 = null;
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter35 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role29, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "Anonymous Reporter", legacyDescriptor34);
        java.lang.String str36 = hOIReporter35.getFirstName();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role38 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.fromString("Anonymous Reporter");
        hOIReporter35.setRole(role38);
        hOIReporter0.setRole(role38);
        java.lang.String str41 = hOIReporter0.getLastName();
        hOIReporter0.setNameSuffix("Anonymous Reporter");
        hOIReporter0.setFirstName("15:43:49");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + true + "'", boolean25 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role28);
        org.junit.Assert.assertTrue("'" + role29 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role29.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str36 + "' != '" + "Anonymous Reporter" + "'", str36.equals("Anonymous Reporter"));
        org.junit.Assert.assertTrue("'" + role38 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role38.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str41);
    }

    @Test
    public void test1541() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1541");
        java.util.Date date1 = gov.ca.cwds.rest.api.domain.DomainChef.uncookDateString("2019-09-03-15.43.33.826");
        java.lang.String str2 = gov.ca.cwds.rest.api.domain.DomainChef.cookTime(date1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertEquals(date1.toString(), "Tue Sep 03 00:00:00 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str2 + "' != '" + "00:00:00" + "'", str2.equals("00:00:00"));
    }

    @Test
    public void test1542() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1542");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        java.lang.Boolean boolean3 = participantEntity0.getSealed();
        participantEntity0.setSensitive((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.Person person6 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity7 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity8 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person6, ethnicity7);
        java.util.Date date9 = personEthnicity8.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person10 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity11 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity12 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person10, ethnicity11);
        java.util.Date date13 = personEthnicity12.getLastUpdatedTime();
        boolean boolean14 = personEthnicity8.equals((java.lang.Object) personEthnicity12);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity18 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity8.setEthnicity(ethnicity18);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity20 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity18);
        java.lang.Long long21 = ethnicity18.getPrimaryKey();
        boolean boolean22 = participantEntity0.equals((java.lang.Object) ethnicity18);
        participantEntity0.setProbationYouth((java.lang.Boolean) false);
        java.lang.String str25 = participantEntity0.getApproximateAgeUnits();
        java.lang.String[] strArray26 = participantEntity0.getRoles();
        java.lang.String str27 = participantEntity0.getId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date9);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date9.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date13);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date13.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + true + "'", boolean14 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long21 + "' != '" + 1L + "'", long21.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean22 + "' != '" + false + "'", boolean22 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str25);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str27);
    }

    @Test
    public void test1543() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1543");
        gov.ca.cwds.data.persistence.ns.Race race3 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "2019-09-03-15.43.31.965", "");
        java.lang.String str4 = race3.getRaceType();
        java.lang.String str5 = race3.getLastUpdatedId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str4 + "' != '" + "2019-09-03-15.43.31.965" + "'", str4.equals("2019-09-03-15.43.31.965"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
    }

    @Test
    public void test1544() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1544");
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
        person25.doSomething();
        gov.ca.cwds.rest.api.domain.Address[] addressArray46 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet47 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean48 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet47, addressArray46);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray49 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet50 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean51 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet50, phoneNumberArray49);
        gov.ca.cwds.rest.api.domain.Language[] languageArray52 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet53 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean54 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet53, languageArray52);
        gov.ca.cwds.rest.api.domain.Race[] raceArray55 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet56 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean57 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet56, raceArray55);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity60 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray61 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity60 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet62 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean63 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet62, ethnicityArray61);
        gov.ca.cwds.rest.api.domain.Person person64 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet47, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet50, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet53, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet56, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet62);
        java.lang.String str65 = person64.getMiddleName();
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList66 = person64.getMessages();
        person25.setMessages(errorMessageList66);
        gov.ca.cwds.data.persistence.ns.Person person68 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity69 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity70 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person68, ethnicity69);
        java.util.Date date71 = personEthnicity70.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person72 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity73 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity74 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person72, ethnicity73);
        java.util.Date date75 = personEthnicity74.getLastUpdatedTime();
        boolean boolean76 = personEthnicity70.equals((java.lang.Object) personEthnicity74);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity80 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity70.setEthnicity(ethnicity80);
        java.util.Date date82 = personEthnicity70.getLastUpdatedTime();
        boolean boolean83 = person25.equals((java.lang.Object) date82);
        java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet84 = person25.getPhoneNumber();
        java.lang.String str85 = person25.getLastName();
        java.util.Set<gov.ca.cwds.rest.api.domain.Address> addressSet86 = person25.getAddress();
        person25.doSomething();
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
// flaky:         org.junit.Assert.assertEquals(date30.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date34);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date34.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean35 + "' != '" + true + "'", boolean35 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + false + "'", boolean36 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str37 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str37.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray46);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean48 + "' != '" + false + "'", boolean48 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray49);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean51 + "' != '" + false + "'", boolean51 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray52);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean54 + "' != '" + false + "'", boolean54 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray55);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean57 + "' != '" + false + "'", boolean57 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray61);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean63 + "' != '" + true + "'", boolean63 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str65 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str65.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList66);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date71);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date71.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date75);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date75.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean76 + "' != '" + true + "'", boolean76 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date82);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date82.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean83 + "' != '" + false + "'", boolean83 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberSet84);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str85 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str85.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressSet86);
    }

    @Test
    public void test1545() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1545");
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
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity16 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity17 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person15, ethnicity16);
        java.util.Date date18 = personEthnicity17.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person19 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity20 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity21 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person19, ethnicity20);
        java.util.Date date22 = personEthnicity21.getLastUpdatedTime();
        boolean boolean23 = personEthnicity17.equals((java.lang.Object) personEthnicity21);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity27 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonEthnicity> personEthnicitySet28 = ethnicity27.getPersonEthnicity();
        java.lang.String str29 = ethnicity27.getSubEthnicity();
        personEthnicity21.setEthnicity(ethnicity27);
        java.lang.Long long31 = ethnicity27.getId();
        java.lang.String str32 = ethnicity27.getEthnicityType();
        java.lang.Long long33 = ethnicity27.getId();
        personEthnicity2.setEthnicity(ethnicity27);
        java.util.Date date35 = personEthnicity2.getCreateDateTime();
        gov.ca.cwds.data.persistence.ns.PersonEthnicityId personEthnicityId36 = personEthnicity2.getPrimaryKey();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date3);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date3.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date7);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date7.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean8 + "' != '" + true + "'", boolean8 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date14);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date14.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date18);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date18.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date22);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date22.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean23 + "' != '" + true + "'", boolean23 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personEthnicitySet28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str29 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds" + "'", str29.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long31 + "' != '" + 0L + "'", long31.equals(0L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str32 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str32.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long33 + "' != '" + 0L + "'", long33.equals(0L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date35);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date35.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personEthnicityId36);
    }

    @Test
    public void test1546() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1546");
        gov.ca.cwds.data.persistence.ns.CsecEntity csecEntity0 = new gov.ca.cwds.data.persistence.ns.CsecEntity();
        java.time.LocalDate localDate1 = csecEntity0.getStartDate();
        java.lang.String str2 = csecEntity0.getCsecCodeId();
        csecEntity0.setId((java.lang.Integer) 1);
        java.lang.String str5 = csecEntity0.getParticipantId();
        gov.ca.cwds.rest.api.domain.Address[] addressArray13 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet14 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean15 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet14, addressArray13);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray16 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet17 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean18 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet17, phoneNumberArray16);
        gov.ca.cwds.rest.api.domain.Language[] languageArray19 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet20 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean21 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet20, languageArray19);
        gov.ca.cwds.rest.api.domain.Race[] raceArray22 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet23 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean24 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet23, raceArray22);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity27 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray28 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity27 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet29 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean30 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet29, ethnicityArray28);
        gov.ca.cwds.rest.api.domain.Person person31 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet14, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet17, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet20, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet23, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet29);
        java.lang.String str32 = person31.getSsn();
        gov.ca.cwds.data.persistence.ns.Person person33 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity34 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity35 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person33, ethnicity34);
        java.util.Date date36 = personEthnicity35.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person37 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity38 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity39 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person37, ethnicity38);
        java.util.Date date40 = personEthnicity39.getLastUpdatedTime();
        boolean boolean41 = personEthnicity35.equals((java.lang.Object) personEthnicity39);
        boolean boolean42 = person31.equals((java.lang.Object) boolean41);
        java.lang.String str43 = person31.getLastName();
        boolean boolean44 = csecEntity0.equals((java.lang.Object) str43);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(localDate1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray13);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + false + "'", boolean15 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray19);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray22);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean24 + "' != '" + false + "'", boolean24 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean30 + "' != '" + true + "'", boolean30 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str32 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str32.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date36);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date36.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date40);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date40.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean41 + "' != '" + true + "'", boolean41 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean42 + "' != '" + false + "'", boolean42 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str43 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str43.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean44 + "' != '" + false + "'", boolean44 == false);
    }

    @Test
    public void test1547() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1547");
        gov.ca.cwds.rest.api.domain.Race race2 = new gov.ca.cwds.rest.api.domain.Race("15:43:19", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType3 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor11 = null;
        gov.ca.cwds.rest.api.domain.Address address12 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor11);
        java.lang.String str13 = address12.getCity();
        address12.setLegacyId("");
        java.lang.String str16 = address12.getStreetAddress();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship17 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date18 = clientRelationship17.getLastUpdatedTime();
        java.lang.String str19 = clientRelationship17.getSecondaryClientId();
        java.lang.String str20 = clientRelationship17.getLastUpdatedId();
        gov.ca.cwds.rest.api.domain.Address[] addressArray28 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet29 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean30 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet29, addressArray28);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray31 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet32 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean33 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet32, phoneNumberArray31);
        gov.ca.cwds.rest.api.domain.Language[] languageArray34 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet35 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean36 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet35, languageArray34);
        gov.ca.cwds.rest.api.domain.Race[] raceArray37 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet38 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean39 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet38, raceArray37);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity42 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray43 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity42 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet44 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean45 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet44, ethnicityArray43);
        gov.ca.cwds.rest.api.domain.Person person46 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet29, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet32, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet35, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet38, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet44);
        java.lang.String str47 = person46.getSsn();
        java.lang.String str48 = person46.getNameSuffix();
        java.lang.String str49 = person46.getLastName();
        java.lang.Object obj50 = stringJsonUserType3.replace((java.lang.Object) address12, (java.lang.Object) str20, (java.lang.Object) str49);
        java.lang.Integer int51 = address12.getState();
        address12.setLegacySourceTable("2019-09-03-15.43.20.156");
        boolean boolean54 = race2.equals((java.lang.Object) "2019-09-03-15.43.20.156");
        java.lang.String str55 = race2.getSubRaceType();
        race2.setSubRaceType("2019-09-03-15.43.47.184");
        gov.ca.cwds.data.persistence.ns.Race race60 = new gov.ca.cwds.data.persistence.ns.Race(race2, "15:43:32", "2019-09-03-15.43.45.125");
        java.lang.String str61 = race2.getRaceType();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str13 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str13.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str16.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date18);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str19);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean30 + "' != '" + false + "'", boolean30 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray31);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean33 + "' != '" + false + "'", boolean33 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray34);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + false + "'", boolean36 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray37);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean39 + "' != '" + false + "'", boolean39 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray43);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean45 + "' != '" + true + "'", boolean45 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str47 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str47.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str48 + "' != '" + "" + "'", str48.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str49 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str49.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj50);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int51 + "' != '" + 100 + "'", int51.equals(100));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean54 + "' != '" + false + "'", boolean54 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str55 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds" + "'", str55.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str61 + "' != '" + "15:43:19" + "'", str61.equals("15:43:19"));
    }

    @Test
    public void test1548() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1548");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        java.lang.String str3 = participantEntity0.getRelatedScreeningId();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity4 = null;
        participantEntity0.setScreeningEntity(screeningEntity4);
        java.lang.String str6 = participantEntity0.getLastName();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity7 = null;
        participantEntity0.setScreeningEntity(screeningEntity7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
    }

    @Test
    public void test1549() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1549");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        participantEntity0.setLegacySourceTable("N");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity5 = participantEntity0.getSafelySurrenderedBabies();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(safelySurrenderedBabiesEntity5);
    }

    @Test
    public void test1550() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1550");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.Address[] addressArray8 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet9 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean10 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet9, addressArray8);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray11 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet12 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean13 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, phoneNumberArray11);
        gov.ca.cwds.rest.api.domain.Language[] languageArray14 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet15 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet15, languageArray14);
        gov.ca.cwds.rest.api.domain.Race[] raceArray17 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet18 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean19 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet18, raceArray17);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity22 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray23 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity22 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet24 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean25 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24, ethnicityArray23);
        gov.ca.cwds.rest.api.domain.Person person26 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet9, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet15, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet18, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24);
        boolean boolean27 = hOIReporter0.equals((java.lang.Object) "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        java.lang.String str28 = hOIReporter0.getLastName();
        hOIReporter0.setLastName("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.lang.String str31 = hOIReporter0.getNameSuffix();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role32 = hOIReporter0.getRole();
        java.lang.String str33 = hOIReporter0.getFirstName();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity34 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity34.setLegacyId("N");
        java.lang.Boolean boolean37 = participantEntity34.getSealed();
        participantEntity34.setSensitive((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.Person person40 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity41 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity42 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person40, ethnicity41);
        java.util.Date date43 = personEthnicity42.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person44 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity45 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity46 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person44, ethnicity45);
        java.util.Date date47 = personEthnicity46.getLastUpdatedTime();
        boolean boolean48 = personEthnicity42.equals((java.lang.Object) personEthnicity46);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity52 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity42.setEthnicity(ethnicity52);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity54 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity52);
        java.lang.Long long55 = ethnicity52.getPrimaryKey();
        boolean boolean56 = participantEntity34.equals((java.lang.Object) ethnicity52);
        participantEntity34.setProbationYouth((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity59 = participantEntity34.getScreening();
        java.lang.String str60 = participantEntity34.getNameSuffix();
        participantEntity34.setLegacySourceTable("2019-09-03-15.43.31.965");
        java.lang.String str63 = participantEntity34.toString();
        participantEntity34.setEstimatedDob((java.lang.Boolean) false);
        java.util.Date date66 = participantEntity34.getDateOfBirth();
        boolean boolean67 = hOIReporter0.equals((java.lang.Object) participantEntity34);
        participantEntity34.setSensitive((java.lang.Boolean) true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + true + "'", boolean25 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str31);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role32);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str33);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean37);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date43);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date43.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date47);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date47.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean48 + "' != '" + true + "'", boolean48 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long55 + "' != '" + 1L + "'", long55.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean56 + "' != '" + false + "'", boolean56 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity59);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str60);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date66);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean67 + "' != '" + false + "'", boolean67 == false);
    }

    @Test
    public void test1551() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1551");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        java.lang.Boolean boolean3 = participantEntity0.getSealed();
        participantEntity0.setSensitive((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.Person person6 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity7 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity8 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person6, ethnicity7);
        java.util.Date date9 = personEthnicity8.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person10 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity11 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity12 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person10, ethnicity11);
        java.util.Date date13 = personEthnicity12.getLastUpdatedTime();
        boolean boolean14 = personEthnicity8.equals((java.lang.Object) personEthnicity12);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity18 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity8.setEthnicity(ethnicity18);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity20 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity18);
        java.lang.Long long21 = ethnicity18.getPrimaryKey();
        boolean boolean22 = participantEntity0.equals((java.lang.Object) ethnicity18);
        java.util.Date date23 = participantEntity0.getDateOfBirth();
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity24 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str25 = safelySurrenderedBabiesEntity24.toString();
        java.lang.String str26 = safelySurrenderedBabiesEntity24.toString();
        gov.ca.cwds.data.persistence.ns.Person person27 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity28 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity29 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person27, ethnicity28);
        java.util.Date date30 = personEthnicity29.getLastUpdatedTime();
        java.util.Date date31 = personEthnicity29.getCreateDateTime();
        safelySurrenderedBabiesEntity24.setMedQuestionaireReturnDate(date31);
        gov.ca.cwds.data.persistence.ns.Person person33 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity34 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity35 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person33, ethnicity34);
        java.util.Date date36 = personEthnicity35.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person37 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity38 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity39 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person37, ethnicity38);
        java.util.Date date40 = personEthnicity39.getLastUpdatedTime();
        boolean boolean41 = personEthnicity35.equals((java.lang.Object) personEthnicity39);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity45 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity35.setEthnicity(ethnicity45);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity47 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity45);
        java.lang.Long long48 = ethnicity45.getPrimaryKey();
        boolean boolean49 = safelySurrenderedBabiesEntity24.equals((java.lang.Object) ethnicity45);
        participantEntity0.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity24);
        java.lang.String str51 = participantEntity0.getSsn();
        participantEntity0.setEthnicity("2019-09-03T15:43:40.181-0700");
        participantEntity0.setEstimatedDob((java.lang.Boolean) false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date9);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date9.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date13);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date13.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + true + "'", boolean14 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long21 + "' != '" + 1L + "'", long21.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean22 + "' != '" + false + "'", boolean22 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date30);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date30.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date31);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date31.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date36);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date36.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date40);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date40.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean41 + "' != '" + true + "'", boolean41 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long48 + "' != '" + 1L + "'", long48.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean49 + "' != '" + false + "'", boolean49 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str51);
    }

    @Test
    public void test1552() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1552");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType0 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean1 = stringJsonUserType0.isMutable();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship3 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date4 = clientRelationship3.getLastUpdatedTime();
        boolean boolean5 = stringJsonUserType0.equals((java.lang.Object) "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", (java.lang.Object) clientRelationship3);
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity6 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity6.setLegacyId("N");
        java.lang.Boolean boolean9 = participantEntity6.getSealed();
        participantEntity6.setSensitive((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.Person person12 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity13 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity14 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person12, ethnicity13);
        java.util.Date date15 = personEthnicity14.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person16 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity17 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity18 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person16, ethnicity17);
        java.util.Date date19 = personEthnicity18.getLastUpdatedTime();
        boolean boolean20 = personEthnicity14.equals((java.lang.Object) personEthnicity18);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity24 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity14.setEthnicity(ethnicity24);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity26 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity24);
        java.lang.Long long27 = ethnicity24.getPrimaryKey();
        boolean boolean28 = participantEntity6.equals((java.lang.Object) ethnicity24);
        java.util.Date date29 = participantEntity6.getDateOfBirth();
        participantEntity6.setEthnicity("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId");
        java.lang.String str32 = participantEntity6.getLastName();
        java.lang.Boolean boolean33 = participantEntity6.getSealed();
        java.util.Date date34 = participantEntity6.getDateOfDeath();
        java.lang.String str35 = participantEntity6.getRelatedScreeningId();
        java.lang.Object obj36 = stringJsonUserType0.deepCopy((java.lang.Object) str35);
        java.lang.Class class37 = stringJsonUserType0.returnedClass();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean1 + "' != '" + true + "'", boolean1 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date15);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date15.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date19);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date19.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean20 + "' != '" + true + "'", boolean20 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long27 + "' != '" + 1L + "'", long27.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean28 + "' != '" + false + "'", boolean28 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str32);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean33);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date34);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str35);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(obj36);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(class37);
    }

    @Test
    public void test1553() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1553");
        gov.ca.cwds.data.persistence.ns.Person person2 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity3 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity4 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person2, ethnicity3);
        java.util.Date date5 = personEthnicity4.getLastUpdatedTime();
        java.util.Date date6 = personEthnicity4.getCreateDateTime();
        java.lang.String str7 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date6);
        gov.ca.cwds.data.persistence.ns.Person person14 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity15 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity16 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person14, ethnicity15);
        java.util.Date date17 = personEthnicity16.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person23 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity24 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity25 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person23, ethnicity24);
        java.util.Date date26 = personEthnicity25.getLastUpdatedTime();
        java.util.Date date27 = personEthnicity25.getCreateDateTime();
        java.lang.String str28 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date27);
        gov.ca.cwds.data.persistence.ns.Person person31 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity32 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity33 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person31, ethnicity32);
        java.util.Date date34 = personEthnicity33.getLastUpdatedTime();
        java.lang.String str35 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date34);
        gov.ca.cwds.data.persistence.ns.Person person42 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity43 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity44 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person42, ethnicity43);
        java.util.Date date45 = personEthnicity44.getLastUpdatedTime();
        java.lang.String str46 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date45);
        java.lang.String str47 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date45);
        gov.ca.cwds.data.persistence.ns.Person person52 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity53 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity54 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person52, ethnicity53);
        java.util.Date date55 = personEthnicity54.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person56 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity57 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity58 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person56, ethnicity57);
        java.util.Date date59 = personEthnicity58.getLastUpdatedTime();
        boolean boolean60 = personEthnicity54.equals((java.lang.Object) personEthnicity58);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity64 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity54.setEthnicity(ethnicity64);
        java.util.Date date66 = personEthnicity54.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship67 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Short) (short) 1, date45, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "hi!", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", date66);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship68 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("2019-09-03", (java.lang.Short) (short) 100, date34, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s_z", "", "2019-09-03-15.43.22.185", date45);
        java.util.Date date69 = clientRelationship68.getStartDate();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity74 = null;
        java.lang.String[] strArray77 = new java.lang.String[] { "15:43:23" };
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity78 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str79 = participantEntity78.getLegacyId();
        java.lang.String[] strArray80 = participantEntity78.getLanguages();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity92 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity("2019-09-03-15.43.22.185", date27, date69, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd-HH.mm.ss.SSS", "yyyy-MM-dd-HH.mm.ss.SSS", "_anonymous _reporter", screeningEntity74, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", strArray77, strArray80, "2019-09-03-15.43.20.156", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "2019-09-03", "2019-09-03", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId", (java.lang.Boolean) false, (java.lang.Boolean) false, (java.lang.Boolean) false, "N", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship93 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Short) (short) 100, date17, "2019-09-03-15.43.22.185", "15:43:19", "2019-09-03-15.43.23.340", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", date27);
        java.lang.String str94 = gov.ca.cwds.rest.api.domain.DomainChef.cookStrictTimestamp(date17);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship95 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("2019-09-03T15:43:49.597Z", (java.lang.Short) (short) 0, date6, "2019-09-03-15.43.41.499", "15:43:57", "2019-09-03-15.43.44.879", "2019-09-03", date17);
        java.lang.String str96 = gov.ca.cwds.rest.api.domain.DomainChef.cookISO8601Timestamp(date6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date5);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date5.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date6);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date6.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str7 + "' != '" + "2019-09-03" + "'", str7.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date17);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date17.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date26);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date26.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date27);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date27.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "2019-09-03" + "'", str28.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date34);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date34.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str35 + "' != '" + "2019-09-03" + "'", str35.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date45);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date45.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str46 + "' != '" + "2019-09-03" + "'", str46.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str47 + "' != '" + "2019-09-03" + "'", str47.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date55);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date55.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date59);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date59.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean60 + "' != '" + true + "'", boolean60 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date66);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date66.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date69);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date69.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray77);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str79);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray80);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str94 + "' != '" + "2019-09-03T15:44:16.543-0700" + "'", str94.equals("2019-09-03T15:44:16.543-0700"));
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str96 + "' != '" + "2019-09-03T15:44:16.543Z" + "'", str96.equals("2019-09-03T15:44:16.543Z"));
    }

    @Test
    public void test1554() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1554");
        gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber3 = new gov.ca.cwds.rest.api.domain.PhoneNumber((java.lang.Long) (-1L), "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "15:43:57");
    }

    @Test
    public void test1555() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1555");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType0 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean1 = stringJsonUserType0.isMutable();
        int[] intArray2 = stringJsonUserType0.sqlTypes();
        gov.ca.cwds.data.persistence.ns.Race race7 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        gov.ca.cwds.rest.api.domain.Race race8 = new gov.ca.cwds.rest.api.domain.Race(race7);
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonRace> personRaceSet9 = race7.getPersonRace();
        boolean boolean10 = stringJsonUserType0.equals((java.lang.Object) 0.0f, (java.lang.Object) race7);
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType11 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean12 = stringJsonUserType11.isMutable();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship14 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date15 = clientRelationship14.getLastUpdatedTime();
        boolean boolean16 = stringJsonUserType11.equals((java.lang.Object) "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", (java.lang.Object) clientRelationship14);
        gov.ca.cwds.data.persistence.ns.Race race17 = new gov.ca.cwds.data.persistence.ns.Race();
        gov.ca.cwds.rest.api.domain.Race race18 = new gov.ca.cwds.rest.api.domain.Race(race17);
        gov.ca.cwds.rest.api.domain.Address[] addressArray26 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet27 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean28 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet27, addressArray26);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray29 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet30 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean31 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet30, phoneNumberArray29);
        gov.ca.cwds.rest.api.domain.Language[] languageArray32 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet33 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean34 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet33, languageArray32);
        gov.ca.cwds.rest.api.domain.Race[] raceArray35 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet36 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean37 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet36, raceArray35);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity40 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray41 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity40 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet42 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean43 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet42, ethnicityArray41);
        gov.ca.cwds.rest.api.domain.Person person44 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet27, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet30, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet33, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet36, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet42);
        java.lang.Object obj45 = stringJsonUserType11.assemble((java.io.Serializable) race18, (java.lang.Object) "");
        java.lang.Class class46 = stringJsonUserType11.returnedClass();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity47 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str48 = participantEntity47.getLegacyId();
        java.lang.String[] strArray49 = participantEntity47.getLanguages();
        participantEntity47.setScreeningId("15:43:19");
        java.lang.String str52 = participantEntity47.getLegacySourceTable();
        java.lang.String str53 = participantEntity47.getLastName();
        java.lang.String str54 = participantEntity47.getScreeningId();
        gov.ca.cwds.data.persistence.ns.Person person55 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity56 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity57 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person55, ethnicity56);
        java.util.Date date58 = personEthnicity57.getLastUpdatedTime();
        java.lang.String str59 = personEthnicity57.getCreateUserId();
        java.lang.Object obj60 = stringJsonUserType0.replace((java.lang.Object) stringJsonUserType11, (java.lang.Object) participantEntity47, (java.lang.Object) str59);
        java.util.List<gov.ca.cwds.data.persistence.ns.CsecEntity> csecEntityList61 = participantEntity47.getCsecs();
        participantEntity47.setEstimatedDob((java.lang.Boolean) true);
        java.lang.String str64 = participantEntity47.getEthnicity();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean1 + "' != '" + true + "'", boolean1 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(intArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personRaceSet9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean12 + "' != '" + true + "'", boolean12 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean28 + "' != '" + false + "'", boolean28 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean31 + "' != '" + false + "'", boolean31 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray32);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean34 + "' != '" + false + "'", boolean34 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray35);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean37 + "' != '" + false + "'", boolean37 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray41);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean43 + "' != '" + true + "'", boolean43 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj45);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(class46);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str48);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray49);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str52);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str53);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str54 + "' != '" + "15:43:19" + "'", str54.equals("15:43:19"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date58);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date58.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str59);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj60);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(csecEntityList61);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str64);
    }

    @Test
    public void test1556() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1556");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        participantEntity0.setScreeningId("15:43:19");
        java.lang.String str5 = participantEntity0.getLegacySourceTable();
        java.lang.String str6 = participantEntity0.getLastName();
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity7 = participantEntity0.getSafelySurrenderedBabies();
        java.lang.String str8 = participantEntity0.getLegacyId();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity9 = participantEntity0.getScreeningEntity();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(safelySurrenderedBabiesEntity7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity9);
    }

    @Test
    public void test1557() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1557");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str1 = safelySurrenderedBabiesEntity0.toString();
        java.lang.String str2 = safelySurrenderedBabiesEntity0.toString();
        java.lang.String str3 = safelySurrenderedBabiesEntity0.getParticipantId();
        java.lang.String str4 = safelySurrenderedBabiesEntity0.getParentGuardProvMedQuestion();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity5 = safelySurrenderedBabiesEntity0.getParticipantEntity();
        java.io.Serializable serializable6 = safelySurrenderedBabiesEntity0.getPrimaryKey();
        safelySurrenderedBabiesEntity0.setParentGuardProvMedQuestion("15:43:52");
        safelySurrenderedBabiesEntity0.setBraceletId("15:43:46");
        safelySurrenderedBabiesEntity0.setComments("2019-09-03-15.43.50.331");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(participantEntity5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable6);
    }

    @Test
    public void test1558() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1558");
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
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity14 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity12);
        java.lang.Long long15 = ethnicity12.getPrimaryKey();
        java.lang.String str16 = ethnicity12.getEthnicityType();
        java.lang.Long long17 = ethnicity12.getPrimaryKey();
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonEthnicity> personEthnicitySet18 = ethnicity12.getPersonEthnicity();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date3);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date3.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date7);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date7.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean8 + "' != '" + true + "'", boolean8 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long15 + "' != '" + 1L + "'", long15.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "hi!" + "'", str16.equals("hi!"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long17 + "' != '" + 1L + "'", long17.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personEthnicitySet18);
    }

    @Test
    public void test1559() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1559");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship0.setLastUpdatedId("");
        java.util.Date date3 = clientRelationship0.getLastUpdatedTime();
        java.lang.String str4 = clientRelationship0.getSecondaryClientId();
        java.lang.String str5 = clientRelationship0.getPrimaryKey();
        java.util.Date date6 = clientRelationship0.getStartDate();
        java.lang.Short short7 = clientRelationship0.getClientRelationshipType();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(short7);
    }

    @Test
    public void test1560() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1560");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.lang.String str1 = clientRelationship0.getSameHomeCode();
        java.lang.String str2 = clientRelationship0.getLastUpdatedId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str1 + "' != '" + "" + "'", str1.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
    }

    @Test
    public void test1561() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1561");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role0 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        java.lang.String str1 = role0.toString();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor6 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor6.setId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.lang.String str9 = legacyDescriptor6.getUiId();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter10 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role0, "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s_z", "yyyy-MM-dd-HH.mm.ss.SSS", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", legacyDescriptor6);
        org.joda.time.DateTime dateTime17 = null;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor20 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor("2019-09-03T15:43:21.461-0700", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId", dateTime17, "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s_z", "_anonymous _reporter");
        java.lang.String str21 = legacyDescriptor20.getId();
        legacyDescriptor20.setId("yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s_z");
        legacyDescriptor20.setId("2019-09-03-15.43.46.413");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter26 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role0, "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", "2019-09-03-15.44.10.308", "2019-09-03T15:43:45.125Z", "2019-09-03T15:43:45.125Z", legacyDescriptor20);
        org.junit.Assert.assertTrue("'" + role0 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role0.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str1 + "' != '" + "Anonymous Reporter" + "'", str1.equals("Anonymous Reporter"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str21 + "' != '" + "2019-09-03T15:43:21.461-0700" + "'", str21.equals("2019-09-03T15:43:21.461-0700"));
    }

    @Test
    public void test1562() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1562");
        gov.ca.cwds.data.persistence.ns.Person person0 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity1 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity2 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person0, ethnicity1);
        java.util.Date date3 = personEthnicity2.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person4 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity5 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity6 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person4, ethnicity5);
        java.util.Date date7 = personEthnicity6.getLastUpdatedTime();
        boolean boolean8 = personEthnicity2.equals((java.lang.Object) personEthnicity6);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity12 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonEthnicity> personEthnicitySet13 = ethnicity12.getPersonEthnicity();
        java.lang.String str14 = ethnicity12.getSubEthnicity();
        personEthnicity6.setEthnicity(ethnicity12);
        java.lang.Long long16 = ethnicity12.getId();
        java.lang.String str17 = ethnicity12.getEthnicityType();
        java.lang.Long long18 = ethnicity12.getPrimaryKey();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date3);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date3.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date7);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date7.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean8 + "' != '" + true + "'", boolean8 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personEthnicitySet13);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str14 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds" + "'", str14.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long16 + "' != '" + 0L + "'", long16.equals(0L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str17 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str17.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long18 + "' != '" + 0L + "'", long18.equals(0L));
    }

    @Test
    public void test1563() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1563");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor14 = null;
        gov.ca.cwds.rest.api.domain.Address address15 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor14);
        java.lang.String str16 = address15.getCity();
        address15.setLegacySourceTable("2019-09-03-15.43.20.156");
        java.lang.Integer int19 = address15.getState();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor20 = address15.getLegacyDescriptor();
        java.lang.String str21 = address15.getStreetAddress();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor22 = address15.getLegacyDescriptor();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor23 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor23.setTableName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        org.joda.time.DateTime dateTime26 = null;
        legacyDescriptor23.setLastUpdated(dateTime26);
        address15.setLegacyDescriptor(legacyDescriptor23);
        gov.ca.cwds.rest.api.domain.Address address29 = new gov.ca.cwds.rest.api.domain.Address("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId", "2019-09-03-15.43.25.655", "2019-09-03-15.43.40.772", "2019-09-03T15:43:41.574-0700", (java.lang.Integer) 1, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", (java.lang.Integer) (-1), legacyDescriptor23);
        legacyDescriptor23.setUiId("15:43:23");
        legacyDescriptor23.setId("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str16.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int19 + "' != '" + 100 + "'", int19.equals(100));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor20);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str21 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str21.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor22);
    }

    @Test
    public void test1564() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1564");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship0.setLastUpdatedId("");
        java.util.Date date3 = clientRelationship0.getLastUpdatedTime();
        java.lang.Short short4 = clientRelationship0.getClientRelationshipType();
        java.lang.String str5 = clientRelationship0.getSameHomeCode();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship6 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship6.setLastUpdatedId("");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship9 = null;
        boolean boolean10 = clientRelationship6.relatedTo(clientRelationship9);
        java.util.Date date11 = clientRelationship6.getEndDate();
        java.lang.Short short12 = clientRelationship6.getClientRelationshipType();
        java.lang.String str13 = clientRelationship6.getPrimaryKey();
        boolean boolean14 = clientRelationship0.relatedTo(clientRelationship6);
        java.lang.String str15 = clientRelationship6.getSecondaryClientId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(short4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str5 + "' != '" + "" + "'", str5.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(short12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str13);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + false + "'", boolean14 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str15);
    }

    @Test
    public void test1565() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1565");
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
        gov.ca.cwds.rest.api.domain.error.ErrorMessage errorMessage37 = null;
        person25.addMessage(errorMessage37);
        java.util.Set<gov.ca.cwds.rest.api.domain.Address> addressSet39 = person25.getAddress();
        java.lang.String str40 = person25.getLastName();
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
// flaky:         org.junit.Assert.assertEquals(date30.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date34);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date34.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean35 + "' != '" + true + "'", boolean35 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + false + "'", boolean36 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressSet39);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str40 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str40.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
    }

    @Test
    public void test1566() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1566");
        gov.ca.cwds.rest.api.domain.Language language1 = new gov.ca.cwds.rest.api.domain.Language("2019-09-03-15.43.46.008");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity2 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str3 = participantEntity2.getLastName();
        java.lang.String str4 = participantEntity2.getId();
        participantEntity2.setFirstName("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId");
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity7 = null;
        participantEntity2.setScreeningEntity(screeningEntity7);
        java.lang.Boolean boolean9 = participantEntity2.getSensitive();
        boolean boolean10 = language1.equals((java.lang.Object) participantEntity2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
    }

    @Test
    public void test1567() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1567");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity3 = participantEntity0.getScreeningEntity();
        java.util.Date date4 = participantEntity0.getDateOfBirth();
        participantEntity0.setSensitive((java.lang.Boolean) true);
        gov.ca.cwds.data.persistence.ns.Person person7 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity11 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity12 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person7, ethnicity11);
        java.lang.String str13 = personEthnicity12.getLastUpdatedId();
        java.util.Date date14 = personEthnicity12.getCreateDateTime();
        participantEntity0.setDateOfDeath(date14);
        java.lang.String str16 = participantEntity0.getScreeningId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str13);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date14);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date14.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str16);
    }

    @Test
    public void test1568() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1568");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.Address[] addressArray8 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet9 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean10 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet9, addressArray8);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray11 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet12 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean13 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, phoneNumberArray11);
        gov.ca.cwds.rest.api.domain.Language[] languageArray14 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet15 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet15, languageArray14);
        gov.ca.cwds.rest.api.domain.Race[] raceArray17 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet18 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean19 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet18, raceArray17);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity22 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray23 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity22 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet24 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean25 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24, ethnicityArray23);
        gov.ca.cwds.rest.api.domain.Person person26 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet9, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet15, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet18, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24);
        boolean boolean27 = hOIReporter0.equals((java.lang.Object) "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        java.lang.String str28 = hOIReporter0.getNameSuffix();
        java.lang.String str29 = hOIReporter0.getId();
        java.lang.String str30 = hOIReporter0.getFirstName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + true + "'", boolean25 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str30);
    }

    @Test
    public void test1569() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1569");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity3 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str4 = safelySurrenderedBabiesEntity3.toString();
        java.util.Date date5 = safelySurrenderedBabiesEntity3.getMedQuestionaireReturnDate();
        java.lang.String str6 = safelySurrenderedBabiesEntity3.getRelationToChild();
        java.lang.String str7 = safelySurrenderedBabiesEntity3.getComments();
        participantEntity0.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity3);
        participantEntity0.setId("2019-09-03");
        java.lang.String[] strArray11 = participantEntity0.getLanguages();
        java.lang.Boolean boolean12 = participantEntity0.getSealed();
        participantEntity0.setSsn("15:43:38");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean12);
    }

    @Test
    public void test1570() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1570");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship0.setLastUpdatedId("");
        clientRelationship0.setLastUpdatedId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship5 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship5.setLastUpdatedId("");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship8 = null;
        boolean boolean9 = clientRelationship5.relatedTo(clientRelationship8);
        java.util.Date date10 = clientRelationship5.getStartDate();
        boolean boolean11 = clientRelationship0.relatedTo(clientRelationship5);
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity12 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str13 = safelySurrenderedBabiesEntity12.toString();
        java.lang.String str14 = safelySurrenderedBabiesEntity12.toString();
        gov.ca.cwds.data.persistence.ns.Person person15 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity16 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity17 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person15, ethnicity16);
        java.util.Date date18 = personEthnicity17.getLastUpdatedTime();
        java.util.Date date19 = personEthnicity17.getCreateDateTime();
        safelySurrenderedBabiesEntity12.setMedQuestionaireReturnDate(date19);
        gov.ca.cwds.data.persistence.ns.Person person21 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity22 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity23 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person21, ethnicity22);
        java.util.Date date24 = personEthnicity23.getLastUpdatedTime();
        java.util.Date date25 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date24);
        java.lang.String str26 = gov.ca.cwds.rest.api.domain.DomainChef.cookTimestamp(date24);
        java.lang.String str27 = gov.ca.cwds.rest.api.domain.DomainChef.cookTime(date24);
        java.lang.String str28 = gov.ca.cwds.rest.api.domain.DomainChef.cookTime(date24);
        java.util.Date date29 = gov.ca.cwds.rest.api.domain.DomainChef.concatenateDateAndTime(date19, date24);
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity30 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str31 = safelySurrenderedBabiesEntity30.toString();
        java.lang.String str32 = safelySurrenderedBabiesEntity30.toString();
        java.io.Serializable serializable33 = safelySurrenderedBabiesEntity30.getPrimaryKey();
        safelySurrenderedBabiesEntity30.setParentGuardGivenBraceletId("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId");
        gov.ca.cwds.data.persistence.ns.Person person38 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity39 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity40 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person38, ethnicity39);
        java.util.Date date41 = personEthnicity40.getLastUpdatedTime();
        java.lang.String str42 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date41);
        java.lang.String str43 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date41);
        gov.ca.cwds.data.persistence.ns.Person person48 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity49 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity50 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person48, ethnicity49);
        java.util.Date date51 = personEthnicity50.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person52 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity53 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity54 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person52, ethnicity53);
        java.util.Date date55 = personEthnicity54.getLastUpdatedTime();
        boolean boolean56 = personEthnicity50.equals((java.lang.Object) personEthnicity54);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity60 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity50.setEthnicity(ethnicity60);
        java.util.Date date62 = personEthnicity50.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship63 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Short) (short) 1, date41, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "hi!", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", date62);
        safelySurrenderedBabiesEntity30.setMedQuestionaireReturnDate(date62);
        java.lang.String str65 = safelySurrenderedBabiesEntity30.getParentGuardProvMedQuestion();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity66 = safelySurrenderedBabiesEntity30.getParticipantEntity();
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity67 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str68 = safelySurrenderedBabiesEntity67.toString();
        java.lang.String str69 = safelySurrenderedBabiesEntity67.toString();
        java.lang.String str70 = safelySurrenderedBabiesEntity67.getParticipantId();
        java.lang.String str71 = safelySurrenderedBabiesEntity67.getSurrenderedBy();
        safelySurrenderedBabiesEntity67.setBraceletId("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        gov.ca.cwds.data.persistence.ns.Person person76 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity80 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity81 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person76, ethnicity80);
        java.lang.String str82 = personEthnicity81.getLastUpdatedId();
        java.util.Date date83 = personEthnicity81.getCreateDateTime();
        gov.ca.cwds.data.persistence.ns.Person person88 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity89 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity90 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person88, ethnicity89);
        java.util.Date date91 = personEthnicity90.getLastUpdatedTime();
        java.util.Date date92 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date91);
        java.lang.String str93 = gov.ca.cwds.rest.api.domain.DomainChef.cookTimestamp(date91);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship94 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", (java.lang.Short) (short) 100, date83, "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId", "yyyy-MM-dd HH:mm:ss.SSS", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIds", date91);
        safelySurrenderedBabiesEntity67.setMedQuestionaireReturnDate(date83);
        safelySurrenderedBabiesEntity30.setMedQuestionaireReturnDate(date83);
        java.util.Date date97 = gov.ca.cwds.rest.api.domain.DomainChef.concatenateDateAndTime(date29, date83);
        clientRelationship5.setLastUpdatedTime(date29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date10);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean11 + "' != '" + false + "'", boolean11 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date18);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date18.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date19);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date19.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date24);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date24.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date25);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date25.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str26 + "' != '" + "2019-09-03-15.44.16.861" + "'", str26.equals("2019-09-03-15.44.16.861"));
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str27 + "' != '" + "15:44:16" + "'", str27.equals("15:44:16"));
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str28 + "' != '" + "15:44:16" + "'", str28.equals("15:44:16"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date29);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date29.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable33);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date41);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date41.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str42 + "' != '" + "2019-09-03" + "'", str42.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str43 + "' != '" + "2019-09-03" + "'", str43.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date51);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date51.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date55);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date55.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean56 + "' != '" + true + "'", boolean56 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date62);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date62.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str65);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(participantEntity66);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str70);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str71);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str82);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date83);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date83.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date91);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date91.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date92);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date92.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str93 + "' != '" + "2019-09-03-15.44.16.862" + "'", str93.equals("2019-09-03-15.44.16.862"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date97);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date97.toString(), "Tue Sep 03 15:44:16 PDT 2019");
    }

    @Test
    public void test1571() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1571");
        gov.ca.cwds.data.persistence.ns.PersonEthnicityId personEthnicityId0 = new gov.ca.cwds.data.persistence.ns.PersonEthnicityId();
        gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber4 = new gov.ca.cwds.rest.api.domain.PhoneNumber((java.lang.Long) 1L, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId");
        boolean boolean5 = personEthnicityId0.equals((java.lang.Object) phoneNumber4);
        gov.ca.cwds.data.persistence.ns.Person person6 = null;
        personEthnicityId0.setPerson(person6);
        java.io.Serializable serializable8 = personEthnicityId0.getPrimaryKey();
        java.io.Serializable serializable9 = personEthnicityId0.getPrimaryKey();
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity10 = personEthnicityId0.getEthnicity();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(ethnicity10);
    }

    @Test
    public void test1572() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1572");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLastName();
        participantEntity0.setSealed((java.lang.Boolean) true);
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity4 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str5 = safelySurrenderedBabiesEntity4.toString();
        java.util.Date date6 = safelySurrenderedBabiesEntity4.getMedQuestionaireReturnDate();
        participantEntity0.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity4);
        participantEntity0.setId("2019-09-03-15.44.12.344");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date6);
    }

    @Test
    public void test1573() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1573");
        gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber3 = new gov.ca.cwds.rest.api.domain.PhoneNumber("2019-09-03-15.43.48.886", "2019-09-03-15.43.48.832", "");
    }

    @Test
    public void test1574() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1574");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String str2 = participantEntity0.getLastName();
        participantEntity0.setEthnicity("15:43:29");
        java.lang.String str5 = participantEntity0.getNameSuffix();
        participantEntity0.setRelatedScreeningId("2019-09-03-15.43.55.532");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
    }

    @Test
    public void test1575() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1575");
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType0 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean1 = stringJsonUserType0.isMutable();
        int[] intArray2 = stringJsonUserType0.sqlTypes();
        gov.ca.cwds.data.persistence.ns.Race race7 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        gov.ca.cwds.rest.api.domain.Race race8 = new gov.ca.cwds.rest.api.domain.Race(race7);
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonRace> personRaceSet9 = race7.getPersonRace();
        boolean boolean10 = stringJsonUserType0.equals((java.lang.Object) 0.0f, (java.lang.Object) race7);
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType11 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean12 = stringJsonUserType11.isMutable();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship14 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date15 = clientRelationship14.getLastUpdatedTime();
        boolean boolean16 = stringJsonUserType11.equals((java.lang.Object) "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", (java.lang.Object) clientRelationship14);
        gov.ca.cwds.data.persistence.ns.Race race17 = new gov.ca.cwds.data.persistence.ns.Race();
        gov.ca.cwds.rest.api.domain.Race race18 = new gov.ca.cwds.rest.api.domain.Race(race17);
        gov.ca.cwds.rest.api.domain.Address[] addressArray26 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet27 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean28 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet27, addressArray26);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray29 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet30 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean31 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet30, phoneNumberArray29);
        gov.ca.cwds.rest.api.domain.Language[] languageArray32 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet33 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean34 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet33, languageArray32);
        gov.ca.cwds.rest.api.domain.Race[] raceArray35 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet36 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean37 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet36, raceArray35);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity40 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray41 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity40 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet42 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean43 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet42, ethnicityArray41);
        gov.ca.cwds.rest.api.domain.Person person44 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet27, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet30, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet33, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet36, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet42);
        java.lang.Object obj45 = stringJsonUserType11.assemble((java.io.Serializable) race18, (java.lang.Object) "");
        java.lang.Class class46 = stringJsonUserType11.returnedClass();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity47 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str48 = participantEntity47.getLegacyId();
        java.lang.String[] strArray49 = participantEntity47.getLanguages();
        participantEntity47.setScreeningId("15:43:19");
        java.lang.String str52 = participantEntity47.getLegacySourceTable();
        java.lang.String str53 = participantEntity47.getLastName();
        java.lang.String str54 = participantEntity47.getScreeningId();
        gov.ca.cwds.data.persistence.ns.Person person55 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity56 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity57 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person55, ethnicity56);
        java.util.Date date58 = personEthnicity57.getLastUpdatedTime();
        java.lang.String str59 = personEthnicity57.getCreateUserId();
        java.lang.Object obj60 = stringJsonUserType0.replace((java.lang.Object) stringJsonUserType11, (java.lang.Object) participantEntity47, (java.lang.Object) str59);
        java.lang.String str61 = participantEntity47.getFirstName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean1 + "' != '" + true + "'", boolean1 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(intArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personRaceSet9);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean12 + "' != '" + true + "'", boolean12 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean28 + "' != '" + false + "'", boolean28 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean31 + "' != '" + false + "'", boolean31 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray32);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean34 + "' != '" + false + "'", boolean34 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray35);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean37 + "' != '" + false + "'", boolean37 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray41);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean43 + "' != '" + true + "'", boolean43 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj45);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(class46);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str48);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray49);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str52);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str53);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str54 + "' != '" + "15:43:19" + "'", str54.equals("15:43:19"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date58);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date58.toString(), "Tue Sep 03 15:44:16 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str59);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj60);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str61);
    }

    @Test
    public void test1576() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1576");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLastName();
        participantEntity0.setSealed((java.lang.Boolean) false);
        java.util.List<gov.ca.cwds.data.persistence.ns.CsecEntity> csecEntityList4 = participantEntity0.getCsecs();
        participantEntity0.setSsn("15:43:29");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(csecEntityList4);
    }

    @Test
    public void test1577() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1577");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str1 = safelySurrenderedBabiesEntity0.toString();
        java.lang.String str2 = safelySurrenderedBabiesEntity0.toString();
        java.lang.String str3 = safelySurrenderedBabiesEntity0.getParticipantId();
        java.lang.String str4 = safelySurrenderedBabiesEntity0.getSurrenderedBy();
        safelySurrenderedBabiesEntity0.setBraceletId("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        java.util.Date date7 = safelySurrenderedBabiesEntity0.getMedQuestionaireReturnDate();
        java.io.Serializable serializable8 = safelySurrenderedBabiesEntity0.getPrimaryKey();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable8);
    }

    @Test
    public void test1578() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1578");
        gov.ca.cwds.data.persistence.ns.Person person1 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity2 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity3 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person1, ethnicity2);
        java.util.Date date4 = personEthnicity3.getLastUpdatedTime();
        java.util.Date date5 = personEthnicity3.getCreateDateTime();
        java.lang.String str6 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date5);
        gov.ca.cwds.data.persistence.ns.Person person9 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity10 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity11 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person9, ethnicity10);
        java.util.Date date12 = personEthnicity11.getLastUpdatedTime();
        java.lang.String str13 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date12);
        gov.ca.cwds.data.persistence.ns.Person person20 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity21 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity22 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person20, ethnicity21);
        java.util.Date date23 = personEthnicity22.getLastUpdatedTime();
        java.lang.String str24 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date23);
        java.lang.String str25 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date23);
        gov.ca.cwds.data.persistence.ns.Person person30 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity31 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity32 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person30, ethnicity31);
        java.util.Date date33 = personEthnicity32.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person34 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity35 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity36 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person34, ethnicity35);
        java.util.Date date37 = personEthnicity36.getLastUpdatedTime();
        boolean boolean38 = personEthnicity32.equals((java.lang.Object) personEthnicity36);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity42 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity32.setEthnicity(ethnicity42);
        java.util.Date date44 = personEthnicity32.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship45 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Short) (short) 1, date23, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "hi!", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", date44);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship46 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("2019-09-03", (java.lang.Short) (short) 100, date12, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s_z", "", "2019-09-03-15.43.22.185", date23);
        java.util.Date date47 = clientRelationship46.getStartDate();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity52 = null;
        java.lang.String[] strArray55 = new java.lang.String[] { "15:43:23" };
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity56 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str57 = participantEntity56.getLegacyId();
        java.lang.String[] strArray58 = participantEntity56.getLanguages();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity70 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity("2019-09-03-15.43.22.185", date5, date47, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd-HH.mm.ss.SSS", "yyyy-MM-dd-HH.mm.ss.SSS", "_anonymous _reporter", screeningEntity52, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", strArray55, strArray58, "2019-09-03-15.43.20.156", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "2019-09-03", "2019-09-03", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipByPrimaryClientId", (java.lang.Boolean) false, (java.lang.Boolean) false, (java.lang.Boolean) false, "N", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Boolean) false);
        java.lang.String str71 = participantEntity70.getEthnicity();
        java.lang.String str72 = participantEntity70.getLastName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date4);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date4.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date5);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date5.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str6 + "' != '" + "2019-09-03" + "'", str6.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date12);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date12.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str13 + "' != '" + "2019-09-03" + "'", str13.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date23);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date23.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str24 + "' != '" + "2019-09-03" + "'", str24.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str25 + "' != '" + "2019-09-03" + "'", str25.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date33);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date33.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date37);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date37.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean38 + "' != '" + true + "'", boolean38 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date44);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date44.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date47);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date47.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray55);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str57);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray58);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str71 + "' != '" + "2019-09-03" + "'", str71.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str72 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str72.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
    }

    @Test
    public void test1579() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1579");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        participantEntity0.setScreeningId("15:43:19");
        java.lang.String str5 = participantEntity0.getLegacySourceTable();
        participantEntity0.setSealed((java.lang.Boolean) false);
        participantEntity0.setLegacyId("2019-09-03-15.43.40.464");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity10 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str11 = safelySurrenderedBabiesEntity10.toString();
        java.lang.String str12 = safelySurrenderedBabiesEntity10.toString();
        java.lang.String str13 = safelySurrenderedBabiesEntity10.getParticipantId();
        safelySurrenderedBabiesEntity10.setRelationToChild("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.lang.String str16 = safelySurrenderedBabiesEntity10.getRelationToChild();
        participantEntity0.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity10);
        safelySurrenderedBabiesEntity10.setBraceletId("2019-09-03T15:43:53.331-0700");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str13);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds" + "'", str16.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds"));
    }

    @Test
    public void test1580() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1580");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship4 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        clientRelationship4.setLastUpdatedId("");
        java.util.Date date7 = clientRelationship4.getLastUpdatedTime();
        java.lang.String str8 = clientRelationship4.getSameHomeCode();
        gov.ca.cwds.data.persistence.ns.Person person11 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity12 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity13 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person11, ethnicity12);
        java.util.Date date14 = personEthnicity13.getLastUpdatedTime();
        java.lang.String str15 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date14);
        java.lang.String str16 = gov.ca.cwds.rest.api.domain.DomainChef.cookDate(date14);
        gov.ca.cwds.data.persistence.ns.Person person21 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity22 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity23 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person21, ethnicity22);
        java.util.Date date24 = personEthnicity23.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person25 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity26 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity27 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person25, ethnicity26);
        java.util.Date date28 = personEthnicity27.getLastUpdatedTime();
        boolean boolean29 = personEthnicity23.equals((java.lang.Object) personEthnicity27);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity33 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity23.setEthnicity(ethnicity33);
        java.util.Date date35 = personEthnicity23.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship36 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Short) (short) 1, date14, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "hi!", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", date35);
        clientRelationship4.setLastUpdatedTime(date14);
        gov.ca.cwds.data.persistence.ns.Person person42 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity43 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity44 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person42, ethnicity43);
        java.util.Date date45 = personEthnicity44.getLastUpdatedTime();
        java.util.Date date46 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date45);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship47 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("2019-09-03T15:43:21.432-0700", (java.lang.Short) (short) 100, date14, "_anonymous _reporter", "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'", "15:43:23", "2019-09-03-15.43.20.156", date46);
        java.lang.String str48 = clientRelationship47.getPrimaryClientId();
        java.util.Date date49 = clientRelationship47.getLastUpdatedTime();
        java.util.Date date50 = clientRelationship47.getStartDate();
        gov.ca.cwds.data.persistence.hibernate.StringJsonUserType stringJsonUserType55 = new gov.ca.cwds.data.persistence.hibernate.StringJsonUserType();
        boolean boolean56 = stringJsonUserType55.isMutable();
        int[] intArray57 = stringJsonUserType55.sqlTypes();
        gov.ca.cwds.data.persistence.ns.Race race61 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        gov.ca.cwds.rest.api.domain.Race race62 = new gov.ca.cwds.rest.api.domain.Race(race61);
        java.lang.String str63 = race62.getRaceType();
        race62.setRaceType("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.util.Date date66 = null;
        gov.ca.cwds.data.persistence.ns.Person person67 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity68 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity69 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person67, ethnicity68);
        java.util.Date date70 = personEthnicity69.getLastUpdatedTime();
        java.util.Date date71 = gov.ca.cwds.rest.api.domain.DomainChef.freshDate(date70);
        java.lang.String str72 = gov.ca.cwds.rest.api.domain.DomainChef.cookTimestamp(date70);
        java.util.Date date73 = null; // flaky: gov.ca.cwds.rest.api.domain.DomainChef.concatenateDateAndTime(date66, date70);
        gov.ca.cwds.data.persistence.ns.Race race77 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        java.lang.Long long78 = race77.getPrimaryKey();
        java.lang.String str79 = race77.getRaceType();
        java.lang.String str80 = race77.getLastUpdatedId();
        java.lang.Object obj81 = stringJsonUserType55.replace((java.lang.Object) race62, (java.lang.Object) date70, (java.lang.Object) str80);
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship82 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("2019-09-03-15.43.33.003", (java.lang.Short) (short) 1, date50, "2019-09-03-15.43.45.455", "2019-09-03-15.43.46.008", "2019-09-03-15.43.46.322", "15:43:44", date70);
        gov.ca.cwds.data.persistence.ns.Person person83 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity87 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity88 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person83, ethnicity87);
        java.lang.String str89 = personEthnicity88.getLastUpdatedId();
        java.util.Date date90 = personEthnicity88.getCreateDateTime();
        java.lang.String str91 = gov.ca.cwds.rest.api.domain.DomainChef.cookISO8601Timestamp(date90);
        java.util.Date date92 = gov.ca.cwds.rest.api.domain.DomainChef.concatenateDateAndTime(date70, date90);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str8 + "' != '" + "" + "'", str8.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date14);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date14.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str15 + "' != '" + "2019-09-03" + "'", str15.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "2019-09-03" + "'", str16.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date24);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date24.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date28);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date28.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean29 + "' != '" + true + "'", boolean29 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date35);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date35.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date45);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date45.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date46);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date46.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str48 + "' != '" + "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'" + "'", str48.equals("yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s'_z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date49);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date50);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date50.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean56 + "' != '" + true + "'", boolean56 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(intArray57);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str63 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str63.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date70);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date70.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date71);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date71.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str72 + "' != '" + "2019-09-03-15.44.17.052" + "'", str72.equals("2019-09-03-15.44.17.052"));
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertNotNull(date73);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date73.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long78 + "' != '" + 0L + "'", long78.equals(0L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str79 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str79.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str80);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(obj81);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str89);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date90);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date90.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertTrue("'" + str91 + "' != '" + "2019-09-03T15:44:17.052Z" + "'", str91.equals("2019-09-03T15:44:17.052Z"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date92);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date92.toString(), "Tue Sep 03 15:44:17 PDT 2019");
    }

    @Test
    public void test1581() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1581");
        gov.ca.cwds.data.persistence.ns.CsecEntity csecEntity0 = new gov.ca.cwds.data.persistence.ns.CsecEntity();
        gov.ca.cwds.rest.api.domain.Address[] addressArray8 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet9 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean10 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet9, addressArray8);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray11 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet12 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean13 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, phoneNumberArray11);
        gov.ca.cwds.rest.api.domain.Language[] languageArray14 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet15 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet15, languageArray14);
        gov.ca.cwds.rest.api.domain.Race[] raceArray17 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet18 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean19 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet18, raceArray17);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity22 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray23 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity22 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet24 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean25 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24, ethnicityArray23);
        gov.ca.cwds.rest.api.domain.Person person26 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet9, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet15, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet18, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24);
        java.lang.String str27 = person26.getSsn();
        gov.ca.cwds.data.persistence.ns.Person person28 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity29 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity30 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person28, ethnicity29);
        java.util.Date date31 = personEthnicity30.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person32 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity33 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity34 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person32, ethnicity33);
        java.util.Date date35 = personEthnicity34.getLastUpdatedTime();
        boolean boolean36 = personEthnicity30.equals((java.lang.Object) personEthnicity34);
        boolean boolean37 = person26.equals((java.lang.Object) boolean36);
        gov.ca.cwds.rest.api.domain.error.ErrorMessage errorMessage38 = null;
        person26.addMessage(errorMessage38);
        java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet40 = person26.getPhoneNumber();
        java.lang.String str41 = person26.getGender();
        java.lang.String str42 = person26.getLastName();
        java.lang.String str43 = person26.getMiddleName();
        boolean boolean44 = csecEntity0.equals((java.lang.Object) person26);
        csecEntity0.setParticipantId("15:43:29");
        java.time.LocalDate localDate47 = csecEntity0.getEndDate();
        java.time.LocalDate localDate48 = csecEntity0.getStartDate();
        java.lang.String str49 = csecEntity0.getParticipantId();
        java.time.LocalDate localDate50 = csecEntity0.getEndDate();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + true + "'", boolean25 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str27 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str27.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date31);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date31.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date35);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date35.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean37 + "' != '" + false + "'", boolean37 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberSet40);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str41 + "' != '" + "hi!" + "'", str41.equals("hi!"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str42 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str42.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str43 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str43.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean44 + "' != '" + false + "'", boolean44 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(localDate47);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(localDate48);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str49 + "' != '" + "15:43:29" + "'", str49.equals("15:43:29"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(localDate50);
    }

    @Test
    public void test1582() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1582");
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity2 = new gov.ca.cwds.rest.api.domain.Ethnicity("2019-09-03-15.44.05.350", "15:43:59");
    }

    @Test
    public void test1583() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1583");
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
        java.lang.String str27 = person25.getNameSuffix();
        java.lang.String str28 = person25.getLastName();
        java.util.Set<gov.ca.cwds.rest.api.domain.Language> languageSet29 = person25.getLanguage();
        java.lang.String str30 = person25.getBirthDate();
        person25.doSomething();
        java.lang.String str32 = person25.getGender();
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
        org.junit.Assert.assertTrue("'" + str27 + "' != '" + "" + "'", str27.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str28.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageSet29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str30 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId" + "'", str30.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str32 + "' != '" + "hi!" + "'", str32.equals("hi!"));
    }

    @Test
    public void test1584() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1584");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity3 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str4 = safelySurrenderedBabiesEntity3.toString();
        java.util.Date date5 = safelySurrenderedBabiesEntity3.getMedQuestionaireReturnDate();
        java.lang.String str6 = safelySurrenderedBabiesEntity3.getRelationToChild();
        java.lang.String str7 = safelySurrenderedBabiesEntity3.getComments();
        participantEntity0.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity3);
        safelySurrenderedBabiesEntity3.setRelationToChild("2019-09-03-15.43.22.185");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity11 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity11.setLegacyId("N");
        java.lang.Boolean boolean14 = participantEntity11.getSealed();
        java.lang.String str15 = participantEntity11.getFirstName();
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
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship43 = new gov.ca.cwds.data.persistence.cms.ClientRelationship("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", (java.lang.Short) (short) 1, date21, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "hi!", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsByPrimaryClientIds", date42);
        participantEntity11.setDateOfDeath(date21);
        safelySurrenderedBabiesEntity3.setMedQuestionaireReturnDate(date21);
        gov.ca.cwds.rest.api.domain.PhoneNumber phoneNumber49 = new gov.ca.cwds.rest.api.domain.PhoneNumber("2019-09-03-15.43.27.309", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "2019-09-03-15.43.23.340");
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity52 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList53 = ethnicity52.getMessages();
        java.lang.String str54 = ethnicity52.getEthnicityType();
        boolean boolean55 = phoneNumber49.equals((java.lang.Object) ethnicity52);
        boolean boolean56 = safelySurrenderedBabiesEntity3.equals((java.lang.Object) phoneNumber49);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str7);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date21);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date21.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str22 + "' != '" + "2019-09-03" + "'", str22.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str23 + "' != '" + "2019-09-03" + "'", str23.equals("2019-09-03"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date31);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date31.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date35);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date35.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + true + "'", boolean36 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date42);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date42.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList53);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str54 + "' != '" + "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds" + "'", str54.equals("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean55 + "' != '" + false + "'", boolean55 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean56 + "' != '" + false + "'", boolean56 == false);
    }

    @Test
    public void test1585() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1585");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        participantEntity0.setScreeningId("15:43:19");
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity5 = participantEntity0.getScreeningEntity();
        java.lang.Boolean boolean6 = participantEntity0.getEstimatedDob();
        participantEntity0.setFirstName("2019-09-03-15.43.25.655");
        java.lang.String str9 = participantEntity0.getPrimaryKey();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str9);
    }

    @Test
    public void test1586() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1586");
        gov.ca.cwds.data.persistence.ns.Person person0 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity4 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        java.lang.String str5 = ethnicity4.getEthnicityType();
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity6 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person0, ethnicity4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str5 + "' != '" + "hi!" + "'", str5.equals("hi!"));
    }

    @Test
    public void test1587() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1587");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor21 = null;
        gov.ca.cwds.rest.api.domain.Address address22 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor21);
        java.lang.String str23 = address22.getCity();
        address22.setLegacySourceTable("2019-09-03-15.43.20.156");
        java.lang.Integer int26 = address22.getState();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor27 = address22.getLegacyDescriptor();
        java.lang.String str28 = address22.getStreetAddress();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor29 = address22.getLegacyDescriptor();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor30 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor30.setTableName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        org.joda.time.DateTime dateTime33 = null;
        legacyDescriptor30.setLastUpdated(dateTime33);
        address22.setLegacyDescriptor(legacyDescriptor30);
        gov.ca.cwds.rest.api.domain.Address address36 = new gov.ca.cwds.rest.api.domain.Address("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId", "2019-09-03-15.43.25.655", "2019-09-03-15.43.40.772", "2019-09-03T15:43:41.574-0700", (java.lang.Integer) 1, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", (java.lang.Integer) (-1), legacyDescriptor30);
        legacyDescriptor30.setUiId("15:43:23");
        gov.ca.cwds.rest.api.domain.Address address39 = new gov.ca.cwds.rest.api.domain.Address("2019-09-03-15.43.29.362", "2019-09-03-15.44.05.633", "2019-09-03-15.43.34.753", "2019-09-03_t15:43:36.968-0700", (java.lang.Integer) 0, "", (java.lang.Integer) 100, legacyDescriptor30);
        org.joda.time.DateTime dateTime40 = legacyDescriptor30.getLastUpdated();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str23 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str23.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int26 + "' != '" + 100 + "'", int26.equals(100));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor27);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str28.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor29);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(dateTime40);
    }

    @Test
    public void test1588() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1588");
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
        gov.ca.cwds.data.persistence.ns.CsecEntity csecEntity26 = new gov.ca.cwds.data.persistence.ns.CsecEntity();
        java.time.LocalDate localDate27 = csecEntity26.getStartDate();
        java.time.LocalDate localDate28 = csecEntity26.getEndDate();
        boolean boolean29 = person25.equals((java.lang.Object) localDate28);
        java.lang.String str30 = person25.getGender();
        gov.ca.cwds.rest.api.domain.Address[] addressArray38 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet39 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean40 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet39, addressArray38);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray41 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet42 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean43 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet42, phoneNumberArray41);
        gov.ca.cwds.rest.api.domain.Language[] languageArray44 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet45 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean46 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet45, languageArray44);
        gov.ca.cwds.rest.api.domain.Race[] raceArray47 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet48 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean49 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet48, raceArray47);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity52 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray53 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity52 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet54 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean55 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet54, ethnicityArray53);
        gov.ca.cwds.rest.api.domain.Person person56 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet39, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet42, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet45, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet48, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet54);
        java.lang.String str57 = person56.getSsn();
        gov.ca.cwds.data.persistence.ns.Person person58 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity59 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity60 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person58, ethnicity59);
        java.util.Date date61 = personEthnicity60.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person62 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity63 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity64 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person62, ethnicity63);
        java.util.Date date65 = personEthnicity64.getLastUpdatedTime();
        boolean boolean66 = personEthnicity60.equals((java.lang.Object) personEthnicity64);
        boolean boolean67 = person56.equals((java.lang.Object) boolean66);
        java.lang.String str68 = person56.getLastName();
        java.lang.String str69 = person56.getLastName();
        java.util.Set<gov.ca.cwds.rest.api.domain.Language> languageSet70 = person56.getLanguage();
        java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet71 = person56.getEthnicity();
        boolean boolean72 = person25.equals((java.lang.Object) person56);
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
        org.junit.Assert.assertNull(localDate27);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(localDate28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean29 + "' != '" + false + "'", boolean29 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str30 + "' != '" + "hi!" + "'", str30.equals("hi!"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray38);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean40 + "' != '" + false + "'", boolean40 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray41);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean43 + "' != '" + false + "'", boolean43 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray44);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean46 + "' != '" + false + "'", boolean46 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray47);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean49 + "' != '" + false + "'", boolean49 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray53);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean55 + "' != '" + true + "'", boolean55 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str57 + "' != '" + "yyyy-MM-dd-HH.mm.ss.SSS" + "'", str57.equals("yyyy-MM-dd-HH.mm.ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date61);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date61.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date65);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date65.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean66 + "' != '" + true + "'", boolean66 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean67 + "' != '" + false + "'", boolean67 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str68 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str68.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str69 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId" + "'", str69.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageSet70);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicitySet71);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean72 + "' != '" + true + "'", boolean72 == true);
    }

    @Test
    public void test1589() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1589");
        gov.ca.cwds.data.persistence.cms.ClientRelationship clientRelationship0 = new gov.ca.cwds.data.persistence.cms.ClientRelationship();
        java.util.Date date1 = clientRelationship0.getLastUpdatedTime();
        java.lang.String str2 = clientRelationship0.getSecondaryClientId();
        java.lang.String str3 = clientRelationship0.getLastUpdatedId();
        java.lang.String str4 = clientRelationship0.getSameHomeCode();
        java.util.Date date5 = clientRelationship0.getStartDate();
        java.lang.String str6 = clientRelationship0.getPrimaryKey();
        java.util.Date date7 = clientRelationship0.getStartDate();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str4 + "' != '" + "" + "'", str4.equals(""));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date7);
    }

    @Test
    public void test1590() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1590");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        java.lang.Integer int10 = address8.getType();
        java.lang.Integer int11 = address8.getState();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter12 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        hOIReporter12.setLastName("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId");
        java.lang.String str15 = hOIReporter12.getId();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor16 = hOIReporter12.getLegacyDescriptor();
        address8.setLegacyDescriptor(legacyDescriptor16);
        java.lang.String str18 = address8.getCity();
        java.lang.String str19 = address8.getStreetAddress();
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList20 = address8.getMessages();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int10 + "' != '" + 0 + "'", int10.equals(0));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int11 + "' != '" + 100 + "'", int11.equals(100));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str15);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str18 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str18.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str19 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str19.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList20);
    }

    @Test
    public void test1591() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1591");
        gov.ca.cwds.data.persistence.ns.Race race3 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) (-1L), "Non-mandated Reporter", "");
    }

    @Test
    public void test1592() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1592");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str1 = safelySurrenderedBabiesEntity0.toString();
        java.lang.String str2 = safelySurrenderedBabiesEntity0.toString();
        java.io.Serializable serializable3 = safelySurrenderedBabiesEntity0.getPrimaryKey();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity4 = safelySurrenderedBabiesEntity0.getParticipantEntity();
        java.lang.String str5 = safelySurrenderedBabiesEntity0.getSurrenderedBy();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity6 = safelySurrenderedBabiesEntity0.getParticipantEntity();
        java.io.Serializable serializable7 = safelySurrenderedBabiesEntity0.getPrimaryKey();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(participantEntity4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(participantEntity6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(serializable7);
    }

    @Test
    public void test1593() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1593");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor21 = null;
        gov.ca.cwds.rest.api.domain.Address address22 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor21);
        java.lang.String str23 = address22.getCity();
        address22.setLegacySourceTable("2019-09-03-15.43.20.156");
        java.lang.Integer int26 = address22.getState();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor27 = address22.getLegacyDescriptor();
        java.lang.String str28 = address22.getStreetAddress();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor29 = address22.getLegacyDescriptor();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor30 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor30.setTableName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        org.joda.time.DateTime dateTime33 = null;
        legacyDescriptor30.setLastUpdated(dateTime33);
        address22.setLegacyDescriptor(legacyDescriptor30);
        gov.ca.cwds.rest.api.domain.Address address36 = new gov.ca.cwds.rest.api.domain.Address("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findLegacyIdListByScreeningId", "2019-09-03-15.43.25.655", "2019-09-03-15.43.40.772", "2019-09-03T15:43:41.574-0700", (java.lang.Integer) 1, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", (java.lang.Integer) (-1), legacyDescriptor30);
        legacyDescriptor30.setUiId("15:43:23");
        gov.ca.cwds.rest.api.domain.Address address39 = new gov.ca.cwds.rest.api.domain.Address("2019-09-03-15.43.29.362", "2019-09-03-15.44.05.633", "2019-09-03-15.43.34.753", "2019-09-03_t15:43:36.968-0700", (java.lang.Integer) 0, "", (java.lang.Integer) 100, legacyDescriptor30);
        address39.setLegacyId("2019-09-03-15.43.23.340");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str23 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str23.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + int26 + "' != '" + 100 + "'", int26.equals(100));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor27);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str28 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str28.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor29);
    }

    @Test
    public void test1594() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1594");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity1 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity1.setLegacyId("N");
        java.lang.Boolean boolean4 = participantEntity1.getSealed();
        participantEntity1.setSensitive((java.lang.Boolean) false);
        gov.ca.cwds.data.persistence.ns.Person person7 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity8 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity9 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person7, ethnicity8);
        java.util.Date date10 = personEthnicity9.getLastUpdatedTime();
        gov.ca.cwds.data.persistence.ns.Person person11 = null;
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity12 = null;
        gov.ca.cwds.data.persistence.ns.PersonEthnicity personEthnicity13 = new gov.ca.cwds.data.persistence.ns.PersonEthnicity(person11, ethnicity12);
        java.util.Date date14 = personEthnicity13.getLastUpdatedTime();
        boolean boolean15 = personEthnicity9.equals((java.lang.Object) personEthnicity13);
        gov.ca.cwds.data.persistence.ns.Ethnicity ethnicity19 = new gov.ca.cwds.data.persistence.ns.Ethnicity((java.lang.Long) 1L, "hi!", "");
        personEthnicity9.setEthnicity(ethnicity19);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity21 = new gov.ca.cwds.rest.api.domain.Ethnicity(ethnicity19);
        java.lang.Long long22 = ethnicity19.getPrimaryKey();
        boolean boolean23 = participantEntity1.equals((java.lang.Object) ethnicity19);
        java.util.Date date24 = participantEntity1.getDateOfBirth();
        safelySurrenderedBabiesEntity0.setParticipantEntity(participantEntity1);
        participantEntity1.setLastName("2019-09-03T15:44:05.983-0700");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(boolean4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date10);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date10.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(date14);
        // Regression assertion (captures the current behavior of the code)
// flaky:         org.junit.Assert.assertEquals(date14.toString(), "Tue Sep 03 15:44:17 PDT 2019");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + true + "'", boolean15 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + long22 + "' != '" + 1L + "'", long22.equals(1L));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean23 + "' != '" + false + "'", boolean23 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date24);
    }

    @Test
    public void test1595() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1595");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.Address[] addressArray8 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet9 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean10 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet9, addressArray8);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray11 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet12 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean13 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, phoneNumberArray11);
        gov.ca.cwds.rest.api.domain.Language[] languageArray14 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet15 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet15, languageArray14);
        gov.ca.cwds.rest.api.domain.Race[] raceArray17 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet18 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean19 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet18, raceArray17);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity22 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray23 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity22 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet24 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean25 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24, ethnicityArray23);
        gov.ca.cwds.rest.api.domain.Person person26 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet9, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet15, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet18, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24);
        boolean boolean27 = hOIReporter0.equals((java.lang.Object) "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role28 = hOIReporter0.getRole();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role29 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor34 = null;
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter35 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role29, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "Anonymous Reporter", legacyDescriptor34);
        java.lang.String str36 = hOIReporter35.getFirstName();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role38 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.fromString("Anonymous Reporter");
        hOIReporter35.setRole(role38);
        hOIReporter0.setRole(role38);
        java.lang.String str41 = role38.toString();
        java.lang.String str42 = role38.getDescription();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + true + "'", boolean25 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role28);
        org.junit.Assert.assertTrue("'" + role29 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role29.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str36 + "' != '" + "Anonymous Reporter" + "'", str36.equals("Anonymous Reporter"));
        org.junit.Assert.assertTrue("'" + role38 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role38.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str41 + "' != '" + "Anonymous Reporter" + "'", str41.equals("Anonymous Reporter"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str42 + "' != '" + "Anonymous Reporter" + "'", str42.equals("Anonymous Reporter"));
    }

    @Test
    public void test1596() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1596");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter10 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role11 = hOIReporter10.getRole();
        java.lang.String str12 = hOIReporter10.getId();
        boolean boolean13 = address8.equals((java.lang.Object) hOIReporter10);
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor21 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor21.setId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        gov.ca.cwds.rest.api.domain.Address address24 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "Anonymous Reporter", "gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", (java.lang.Integer) (-1), "15:43:22", (java.lang.Integer) 1, legacyDescriptor21);
        hOIReporter10.setLegacyDescriptor(legacyDescriptor21);
        java.lang.String str26 = hOIReporter10.getLastName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str26);
    }

    @Test
    public void test1597() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1597");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor7.setTableName("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        org.joda.time.DateTime dateTime10 = null;
        legacyDescriptor7.setLastUpdated(dateTime10);
        java.lang.String str12 = legacyDescriptor7.getTableName();
        legacyDescriptor7.setTableName("yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s_z");
        gov.ca.cwds.rest.api.domain.Address address15 = new gov.ca.cwds.rest.api.domain.Address("2019-09-03-15.44.05.152", "15:43:19", "15:43:22", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByRelatedScreeningIdAndParticipantId", (java.lang.Integer) 0, "2019-09-03-15.43.44.429", (java.lang.Integer) 100, legacyDescriptor7);
        java.lang.String str16 = address15.getLegacyId();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str12 + "' != '" + "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" + "'", str12.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "15:43:19" + "'", str16.equals("15:43:19"));
    }

    @Test
    public void test1598() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1598");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        participantEntity0.setSealed((java.lang.Boolean) true);
        java.lang.String str5 = participantEntity0.getLegacyId();
        participantEntity0.setSensitive((java.lang.Boolean) true);
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity8 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity8.setLegacyId("N");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity11 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str12 = safelySurrenderedBabiesEntity11.toString();
        java.util.Date date13 = safelySurrenderedBabiesEntity11.getMedQuestionaireReturnDate();
        java.lang.String str14 = safelySurrenderedBabiesEntity11.getRelationToChild();
        java.lang.String str15 = safelySurrenderedBabiesEntity11.getComments();
        participantEntity8.setSafelySurrenderedBabies(safelySurrenderedBabiesEntity11);
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role17 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        java.lang.String str18 = role17.toString();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor23 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor();
        legacyDescriptor23.setId("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds");
        java.lang.String str26 = legacyDescriptor23.getUiId();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter27 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role17, "yyyy-_m_m-dd'_t'_h_h:mm:ss._s_s_s_z", "yyyy-MM-dd-HH.mm.ss.SSS", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", legacyDescriptor23);
        org.joda.time.DateTime dateTime34 = null;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor37 = new gov.ca.cwds.rest.api.domain.LegacyDescriptor("_anonymous _reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByParticipantsId", dateTime34, "yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter38 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role17, "yyyy-MM-dd-HH.mm.ss.SSS", "hi!", "15:43:22", "", legacyDescriptor37);
        java.lang.String str39 = hOIReporter38.getLastName();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor40 = hOIReporter38.getLegacyDescriptor();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity41 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str42 = participantEntity41.getLegacyId();
        java.lang.String[] strArray43 = participantEntity41.getLanguages();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity44 = participantEntity41.getScreeningEntity();
        boolean boolean45 = hOIReporter38.equals((java.lang.Object) participantEntity41);
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity46 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str47 = participantEntity46.getLegacyId();
        java.lang.String[] strArray48 = participantEntity46.getLanguages();
        participantEntity41.setLanguages(strArray48);
        participantEntity8.setLanguages(strArray48);
        participantEntity0.setLanguages(strArray48);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date13);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str15);
        org.junit.Assert.assertTrue("'" + role17 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role17.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str18 + "' != '" + "Anonymous Reporter" + "'", str18.equals("Anonymous Reporter"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str39 + "' != '" + "15:43:22" + "'", str39.equals("15:43:22"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor40);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str42);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray43);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity44);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean45 + "' != '" + false + "'", boolean45 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str47);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray48);
    }

    @Test
    public void test1599() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1599");
        gov.ca.cwds.rest.api.domain.Race race2 = new gov.ca.cwds.rest.api.domain.Race("2019-09-03-15.44.04.278", "15:44:12");
    }

    @Test
    public void test1600() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1600");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.rest.api.domain.Address[] addressArray8 = new gov.ca.cwds.rest.api.domain.Address[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address> addressSet9 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Address>();
        boolean boolean10 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Address>) addressSet9, addressArray8);
        gov.ca.cwds.rest.api.domain.PhoneNumber[] phoneNumberArray11 = new gov.ca.cwds.rest.api.domain.PhoneNumber[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber> phoneNumberSet12 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.PhoneNumber>();
        boolean boolean13 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, phoneNumberArray11);
        gov.ca.cwds.rest.api.domain.Language[] languageArray14 = new gov.ca.cwds.rest.api.domain.Language[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language> languageSet15 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Language>();
        boolean boolean16 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Language>) languageSet15, languageArray14);
        gov.ca.cwds.rest.api.domain.Race[] raceArray17 = new gov.ca.cwds.rest.api.domain.Race[] {};
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race> raceSet18 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Race>();
        boolean boolean19 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Race>) raceSet18, raceArray17);
        gov.ca.cwds.rest.api.domain.Ethnicity ethnicity22 = new gov.ca.cwds.rest.api.domain.Ethnicity("gov.ca.cwds.data.persistence.cms.ClientRelationship.findClientRelationshipsBySecondaryClientIds", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gov.ca.cwds.rest.api.domain.Ethnicity[] ethnicityArray23 = new gov.ca.cwds.rest.api.domain.Ethnicity[] { ethnicity22 };
        java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity> ethnicitySet24 = new java.util.LinkedHashSet<gov.ca.cwds.rest.api.domain.Ethnicity>();
        boolean boolean25 = java.util.Collections.addAll((java.util.Collection<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24, ethnicityArray23);
        gov.ca.cwds.rest.api.domain.Person person26 = new gov.ca.cwds.rest.api.domain.Person("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "", "hi!", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId", "yyyy-MM-dd-HH.mm.ss.SSS", (java.util.Set<gov.ca.cwds.rest.api.domain.Address>) addressSet9, (java.util.Set<gov.ca.cwds.rest.api.domain.PhoneNumber>) phoneNumberSet12, (java.util.Set<gov.ca.cwds.rest.api.domain.Language>) languageSet15, (java.util.Set<gov.ca.cwds.rest.api.domain.Race>) raceSet18, (java.util.Set<gov.ca.cwds.rest.api.domain.Ethnicity>) ethnicitySet24);
        boolean boolean27 = hOIReporter0.equals((java.lang.Object) "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findByScreeningIdAndLegacyId");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role28 = hOIReporter0.getRole();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role29 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor34 = null;
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter35 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role29, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "Anonymous Reporter", legacyDescriptor34);
        java.lang.String str36 = hOIReporter35.getFirstName();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role38 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.fromString("Anonymous Reporter");
        hOIReporter35.setRole(role38);
        hOIReporter0.setRole(role38);
        java.lang.String str41 = hOIReporter0.getLastName();
        hOIReporter0.setNameSuffix("Anonymous Reporter");
        java.lang.String str44 = hOIReporter0.getLastName();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(addressArray8);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(phoneNumberArray11);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean13 + "' != '" + false + "'", boolean13 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(languageArray14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(raceArray17);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(ethnicityArray23);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + true + "'", boolean25 == true);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(role28);
        org.junit.Assert.assertTrue("'" + role29 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role29.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str36 + "' != '" + "Anonymous Reporter" + "'", str36.equals("Anonymous Reporter"));
        org.junit.Assert.assertTrue("'" + role38 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role38.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str41);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str44);
    }

    @Test
    public void test1601() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1601");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        address8.setLegacySourceTable("Anonymous Reporter");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor12 = address8.getLegacyDescriptor();
        java.lang.String str13 = address8.getCity();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor14 = address8.getLegacyDescriptor();
        java.lang.String str15 = address8.getZip();
        java.lang.String str16 = address8.getStreetAddress();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str13 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str13.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str15 + "' != '" + "HH:mm:ss" + "'", str15.equals("HH:mm:ss"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str16 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str16.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
    }

    @Test
    public void test1602() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1602");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter0 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        hOIReporter0.setLastName("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId");
        java.lang.String str3 = hOIReporter0.getFirstName();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor4 = hOIReporter0.getLegacyDescriptor();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role6 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.fromString("Anonymous Reporter");
        hOIReporter0.setRole(role6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor4);
        org.junit.Assert.assertTrue("'" + role6 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role6.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
    }

    @Test
    public void test1603() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1603");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        java.lang.String str1 = participantEntity0.getLegacyId();
        java.lang.String[] strArray2 = participantEntity0.getLanguages();
        participantEntity0.setScreeningId("15:43:19");
        java.lang.String str5 = participantEntity0.getLegacySourceTable();
        java.util.Date date6 = participantEntity0.getDateOfDeath();
        participantEntity0.setEthnicity("2019-09-03-15.43.29.883");
        participantEntity0.setRaces("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.lang.String str11 = participantEntity0.getGender();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str1);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(strArray2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(date6);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str11);
    }

    @Test
    public void test1604() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1604");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        address8.setLegacySourceTable("Anonymous Reporter");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor12 = address8.getLegacyDescriptor();
        java.lang.String str13 = address8.getLegacySourceTable();
        java.lang.String str14 = address8.getZip();
        gov.ca.cwds.rest.api.domain.error.ErrorMessage errorMessage15 = null;
        address8.addMessage(errorMessage15);
        java.util.ArrayList<gov.ca.cwds.rest.api.domain.error.ErrorMessage> errorMessageList17 = address8.getMessages();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str13 + "' != '" + "Anonymous Reporter" + "'", str13.equals("Anonymous Reporter"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str14 + "' != '" + "HH:mm:ss" + "'", str14.equals("HH:mm:ss"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(errorMessageList17);
    }

    @Test
    public void test1605() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1605");
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity0 = new gov.ca.cwds.data.persistence.ns.ParticipantEntity();
        participantEntity0.setLegacyId("N");
        java.lang.String str3 = participantEntity0.getScreeningId();
        java.lang.String str4 = participantEntity0.getPrimaryKey();
        gov.ca.cwds.data.persistence.ns.ScreeningEntity screeningEntity5 = participantEntity0.getScreeningEntity();
        participantEntity0.setRelatedScreeningId("2019-09-03-15.44.05.418");
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(screeningEntity5);
    }

    @Test
    public void test1606() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1606");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role0 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER;
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor5 = null;
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter6 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role0, "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "Anonymous Reporter", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantByRelatedScreeningIdAndLegacyId", "Anonymous Reporter", legacyDescriptor5);
        java.lang.String str7 = hOIReporter6.getFirstName();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role9 = gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.fromString("Anonymous Reporter");
        hOIReporter6.setRole(role9);
        java.lang.String str11 = hOIReporter6.toString();
        java.lang.String str12 = hOIReporter6.getId();
        hOIReporter6.setLastName("15:43:27");
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role role15 = hOIReporter6.getRole();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter20 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter();
        gov.ca.cwds.data.persistence.ns.Race race24 = new gov.ca.cwds.data.persistence.ns.Race((java.lang.Long) 0L, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        gov.ca.cwds.rest.api.domain.Race race25 = new gov.ca.cwds.rest.api.domain.Race(race24);
        java.util.Set<gov.ca.cwds.data.persistence.ns.PersonRace> personRaceSet26 = race24.getPersonRace();
        boolean boolean27 = hOIReporter20.equals((java.lang.Object) race24);
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor28 = hOIReporter20.getLegacyDescriptor();
        java.lang.String str29 = legacyDescriptor28.getTableName();
        gov.ca.cwds.rest.api.domain.hoi.HOIReporter hOIReporter30 = new gov.ca.cwds.rest.api.domain.hoi.HOIReporter(role15, "2019-09-03T15:44:14.248-0700", "", "2019-09-03-15.44.01.357", "HH:mm:ss", legacyDescriptor28);
        org.junit.Assert.assertTrue("'" + role0 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role0.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str7 + "' != '" + "Anonymous Reporter" + "'", str7.equals("Anonymous Reporter"));
        org.junit.Assert.assertTrue("'" + role9 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role9.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str12 + "' != '" + "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId" + "'", str12.equals("gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId"));
        org.junit.Assert.assertTrue("'" + role15 + "' != '" + gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER + "'", role15.equals(gov.ca.cwds.rest.api.domain.hoi.HOIReporter.Role.ANONYMOUS_REPORTER));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(personRaceSet26);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor28);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str29);
    }

    @Test
    public void test1607() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1607");
        gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity0 = new gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity();
        java.lang.String str1 = safelySurrenderedBabiesEntity0.toString();
        java.lang.String str2 = safelySurrenderedBabiesEntity0.getSurrenderedBy();
        gov.ca.cwds.data.persistence.ns.ParticipantEntity participantEntity3 = safelySurrenderedBabiesEntity0.getParticipantEntity();
        java.lang.String str4 = safelySurrenderedBabiesEntity0.getRelationToChild();
        java.lang.String str5 = safelySurrenderedBabiesEntity0.getParentGuardProvMedQuestion();
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str2);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(participantEntity3);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str4);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNull(str5);
    }

    @Test
    public void test1608() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest3.test1608");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor7 = null;
        gov.ca.cwds.rest.api.domain.Address address8 = new gov.ca.cwds.rest.api.domain.Address("yyyy-MM-dd-HH.mm.ss.SSS", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.deleteParticipantsByRelatedScreeningId", "gov.ca.cwds.data.persistence.ns.ParticipantEntity.findParticipantsByRelatedScreeningId", "yyyy-MM-dd HH:mm:ss.SSS", (java.lang.Integer) 100, "HH:mm:ss", (java.lang.Integer) 0, legacyDescriptor7);
        java.lang.String str9 = address8.getCity();
        address8.setLegacySourceTable("Anonymous Reporter");
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor12 = address8.getLegacyDescriptor();
        java.lang.String str13 = address8.getCity();
        gov.ca.cwds.rest.api.domain.LegacyDescriptor legacyDescriptor14 = address8.getLegacyDescriptor();
        java.lang.String str15 = address8.getZip();
        gov.ca.cwds.rest.api.domain.error.ErrorMessage errorMessage16 = null;
        address8.addMessage(errorMessage16);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str9 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str9.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor12);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str13 + "' != '" + "yyyy-MM-dd HH:mm:ss.SSS" + "'", str13.equals("yyyy-MM-dd HH:mm:ss.SSS"));
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertNotNull(legacyDescriptor14);
        // Regression assertion (captures the current behavior of the code)
        org.junit.Assert.assertTrue("'" + str15 + "' != '" + "HH:mm:ss" + "'", str15.equals("HH:mm:ss"));
    }
}
