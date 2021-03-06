package gov.ca.cwds.es.live;

import static gov.ca.cwds.data.persistence.cms.CmsPersistentObject.CMS_ID_LEN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.data.es.ElasticSearchPerson;
import gov.ca.cwds.data.es.ElasticSearchPersonAddress;
import gov.ca.cwds.data.es.ElasticSearchPersonAka;
import gov.ca.cwds.data.es.ElasticSearchPersonCsec;
import gov.ca.cwds.data.es.ElasticSearchPersonLanguage;
import gov.ca.cwds.data.es.ElasticSearchPersonPhone;
import gov.ca.cwds.data.es.ElasticSearchPersonScreening;
import gov.ca.cwds.data.es.ElasticSearchRaceAndEthnicity;
import gov.ca.cwds.data.es.ElasticSearchSafetyAlert;
import gov.ca.cwds.data.es.ElasticSearchSystemCode;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiLanguageAware;
import gov.ca.cwds.data.std.ApiMultipleLanguagesAware;
import gov.ca.cwds.data.std.ApiMultiplePhonesAware;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * Methods to transform {@link ApiPersonAware} into {@link ElasticSearchPerson}.
 * 
 * @author CWDS API Team
 */
public final class LiveElasticTransformer {

  private static final Logger LOGGER = LoggerFactory.getLogger(LiveElasticTransformer.class);

  private static ObjectMapper mapper;

  private LiveElasticTransformer() {
    // Static methods, don't instantiate.
  }

  /**
   * Serialize object to JSON.
   * 
   * @param obj object to serialize
   * @return JSON for this screening
   */
  public static String jsonify(Object obj) {
    String ret = "";
    try {
      ret = mapper.writeValueAsString(obj);
    } catch (Exception e) { // NOSONAR
      // HACK: shouldn't swallow the exception, but don't blow up a whole batch for one bad record.
      // Functional lambda doesn't allow checked exceptions, only runtime exceptions.
      // But then SonarQube complains about switching from checked to runtime.
      // Damned if you do, damned if you don't.
      LOGGER.warn("ERROR SERIALIZING OBJECT {} TO JSON", obj);
    }

    return ret;
  }

  protected static String determineId(final ApiLegacyAware l, final ElasticSearchPerson esp) {
    String id;
    final String legacyId = StringUtils.trimToEmpty(l.getLegacyId());
    final boolean hasLegacyId = legacyId.length() == CMS_ID_LEN;

    if (hasLegacyId) {
      id = legacyId;
      esp.setLegacyId(id);
    } else {
      id = esp.getId();
    }

    return id;
  }

  protected static String determineId(CmsReplicatedEntity l, final ElasticSearchPerson esp) {
    final String id = l.getPrimaryKey().toString();
    esp.setLegacyId(id);
    return id;
  }

  /**
   * Handle both {@link ApiMultiplePersonAware} and {@link ApiPersonAware} implementations of type
   * T.
   * 
   * @param p instance of type T
   * @return array of person documents
   */
  public static ElasticSearchPerson[] buildElasticSearchPersons(final PersistentObject p) {
    ElasticSearchPerson[] ret;

    if (p instanceof ApiMultiplePersonAware) {
      final ApiPersonAware[] persons = ((ApiMultiplePersonAware) p).getPersons();
      ret = new ElasticSearchPerson[persons.length];
      int i = 0;
      for (ApiPersonAware px : persons) {
        ret[i++] = LiveElasticTransformer.buildElasticSearchPersonDoc(px);
      }
    } else {
      ret = new ElasticSearchPerson[] {buildElasticSearchPerson((ApiPersonAware) p)};
    }

    return ret;
  }

  /**
   * Produce an ElasticSearchPerson suitable as an Elasticsearch person document.
   * 
   * @param p ApiPersonAware persistence object
   * @return populated ElasticSearchPerson
   */
  public static ElasticSearchPerson buildElasticSearchPerson(ApiPersonAware p) {
    return LiveElasticTransformer.buildElasticSearchPersonDoc(p);
  }

  /**
   * Create legacy descriptor.
   * 
   * <p>
   * Legacy ID should always be 10 characters long, otherwise we can not parse it to get UI ID. For
   * example, LegacyTable.STFPERST is usually 3 characters long.
   * </p>
   * 
   * @param legacyId Legacy ID
   * @param legacyLastUpdated Legacy last updated time stamp
   * @param legacyTable Legacy table
   * @return Legacy descriptor
   */
  public static ElasticSearchLegacyDescriptor createLegacyDescriptor(String legacyId,
      Date legacyLastUpdated, LegacyTable legacyTable) {
    final ElasticSearchLegacyDescriptor ret = new ElasticSearchLegacyDescriptor();

    if (!StringUtils.isBlank(legacyId)) {
      final String cleanLegacyId = legacyId.trim(); // data errors in CHAR(10) keys.
      ret.setLegacyId(cleanLegacyId);
      ret.setLegacyLastUpdated(DomainChef.cookStrictTimestamp(legacyLastUpdated));

      if (cleanLegacyId.length() == CMS_ID_LEN) {
        ret.setLegacyUiId(CmsKeyIdGenerator.getUIIdentifierFromKey(cleanLegacyId));
      } else {
        ret.setLegacyUiId(cleanLegacyId);
      }

      if (legacyTable != null) {
        ret.setLegacyTableName(legacyTable.getName());
        ret.setLegacyTableDescription(legacyTable.getDescription());
      }
    }

    return ret;
  }

  protected static ElasticSearchPersonLanguage buildLanguageEntry(ApiLanguageAware p) {
    final ApiLanguageAware lx = p;
    final Integer languageId = lx.getLanguageSysId();
    return new ElasticSearchPersonLanguage(languageId.toString(),
        SystemCodeCache.global().getSystemCodeShortDescription(languageId), lx.getPrimary());
  }

  protected static List<ElasticSearchPersonLanguage> buildLanguage(ApiPersonAware p) {
    List<ElasticSearchPersonLanguage> ret = null;

    if (p instanceof ApiMultipleLanguagesAware) {
      final ApiMultipleLanguagesAware mlx = (ApiMultipleLanguagesAware) p;
      ret = new ArrayList<>(mlx.getLanguages().length);
      for (ApiLanguageAware lx : mlx.getLanguages()) {
        ret.add(buildLanguageEntry(lx));
      }
    } else if (p instanceof ApiLanguageAware) {
      ret = new ArrayList<>();
      ret.add(buildLanguageEntry((ApiLanguageAware) p));
    }

    return ret;
  }

  protected static List<ElasticSearchPersonPhone> buildPhone(ApiPersonAware p) {
    List<ElasticSearchPersonPhone> ret = null;
    if (p instanceof ApiMultiplePhonesAware) {
      final Map<String, ElasticSearchPersonPhone> uniquePhones = new HashMap<>();
      final ApiMultiplePhonesAware mphx = (ApiMultiplePhonesAware) p;
      for (ApiPhoneAware phx : mphx.getPhones()) {
        uniquePhones.putIfAbsent(phx.getPhoneNumber(), new ElasticSearchPersonPhone(phx));
      }
      ret = new ArrayList<>(uniquePhones.values());
    } else if (p instanceof ApiPhoneAware) {
      ret = new ArrayList<>();
      ret.add(new ElasticSearchPersonPhone((ApiPhoneAware) p));
    }

    return ret;
  }

  protected static List<ElasticSearchPersonAddress> buildAddress(ApiPersonAware p) {
    List<ElasticSearchPersonAddress> ret = null;

    if (p instanceof ApiMultipleClientAddressAware) {
      ret = ((ApiMultipleClientAddressAware) p).getElasticSearchPersonAddresses();
    } else if (p instanceof ApiAddressAware) {
      ret = new ArrayList<>();
      final ElasticSearchPersonAddress esAddress =
          new ElasticSearchPersonAddress((ApiAddressAware) p);
      if (p instanceof ApiLegacyAware) {
        esAddress.setLegacyDescriptor(((ApiLegacyAware) p).getLegacyDescriptor());
      }
      ret.add(esAddress);
    }

    return ret;
  }

  protected static List<ElasticSearchPersonScreening> buildScreening(ApiPersonAware p) {
    List<ElasticSearchPersonScreening> ret = null;
    if (p instanceof ApiScreeningAware) {
      final ApiScreeningAware screener = ((ApiScreeningAware) p);
      ret = new ArrayList<>(screener.getEsScreenings().length);
      for (ElasticSearchPersonScreening scr : screener.getEsScreenings()) {
        ret.add(scr);
      }
    }
    return ret;
  }

  protected static ElasticSearchLegacyDescriptor buildLegacyDescriptor(ApiPersonAware p) {
    ElasticSearchLegacyDescriptor ret = null;
    if (p instanceof ApiLegacyAware) {
      ret = ((ApiLegacyAware) p).getLegacyDescriptor();
    }
    return ret;
  }

  protected static ElasticSearchRaceAndEthnicity buildRaceEthnicity(ApiPersonAware p) {
    ElasticSearchRaceAndEthnicity ret = null;
    if (p instanceof ApiClientRaceAndEthnicityAware) {
      final ApiClientRaceAndEthnicityAware raceAware = (ApiClientRaceAndEthnicityAware) p;
      ret = raceAware.getRaceAndEthnicity();
    }
    return ret;
  }

  protected static List<ElasticSearchSystemCode> buildClientCounties(ApiPersonAware p) {
    List<ElasticSearchSystemCode> ret = new ArrayList<>();
    if (p instanceof ApiClientCountyAware) {
      final ApiClientCountyAware countyAware = (ApiClientCountyAware) p;
      ret = countyAware.getClientCounties();
    }
    return ret;
  }

  protected static List<ElasticSearchSafetyAlert> buildSafetyAlerts(ApiPersonAware p) {
    List<ElasticSearchSafetyAlert> ret = null;
    if (p instanceof ApiClientSafetyAlertsAware) {
      final ApiClientSafetyAlertsAware alertsAware = (ApiClientSafetyAlertsAware) p;
      final List<ElasticSearchSafetyAlert> safetyAlerts = alertsAware.getClientSafetyAlerts();
      if (safetyAlerts != null && !safetyAlerts.isEmpty()) {
        ret = safetyAlerts;
      }
    }
    return ret;
  }

  protected static List<ElasticSearchPersonAka> buildAkas(ApiPersonAware p) {
    List<ElasticSearchPersonAka> ret = null;
    if (p instanceof ApiOtherClientNamesAware) {
      final ApiOtherClientNamesAware akasAware = (ApiOtherClientNamesAware) p;
      final List<ElasticSearchPersonAka> clientAkas = akasAware.getOtherClientNames();
      if (clientAkas != null && !clientAkas.isEmpty()) {
        ret = clientAkas;
      }
    }
    return ret;
  }

  protected static List<ElasticSearchPersonCsec> buildCsecs(ApiPersonAware p) {
    List<ElasticSearchPersonCsec> ret = null;
    if (p instanceof ReplicatedClient) {
      ReplicatedClient replicatedClient = (ReplicatedClient) p;
      final Map<String, ElasticSearchPersonCsec> clientCsecs = replicatedClient.getCsecs();
      if (clientCsecs != null && !clientCsecs.isEmpty()) {
        ret = new ArrayList<>(clientCsecs.values());
      }
    }
    return ret;
  }

  protected static String buildOpenCaseId(ApiPersonAware p) {
    String ret = null;
    if (p instanceof ApiClientCaseAware) {
      final ApiClientCaseAware caseAware = (ApiClientCaseAware) p;
      ret = caseAware.getOpenCaseId();
    }
    return ret;
  }

  protected static String buildOpenCaseResponsibleAgencyCode(ApiPersonAware p) {
    String ret = null;
    if (p instanceof ApiClientCaseAware) {
      final ApiClientCaseAware caseAware = (ApiClientCaseAware) p;
      ret = caseAware.getOpenCaseResponsibleAgencyCode();
    }
    return ret;
  }

  /**
   * Produce an ElasticSearchPerson objects suitable for an Elasticsearch person document.
   * 
   * @param p ApiPersonAware persistence object
   * @return populated ElasticSearchPerson
   */
  public static ElasticSearchPerson buildElasticSearchPersonDoc(ApiPersonAware p) {
    try {
      return buildElasticSearchPersonDoc(mapper, p);
    } catch (Exception e) {
      throw CaresLog.runtime(LOGGER, e, "FAILED TO JSONIFY! {}", e.getMessage(), e);
    }
  }

  /**
   * Produce an ElasticSearchPerson objects suitable for an Elasticsearch person document.
   * 
   * @param mapper Jackson ObjectMapper
   * @param p ApiPersonAware persistence object
   * @return populated ElasticSearchPerson
   * @throws JsonProcessingException if unable to serialize JSON
   */
  @SuppressWarnings("deprecation")
  public static ElasticSearchPerson buildElasticSearchPersonDoc(final ObjectMapper mapper,
      ApiPersonAware p) throws JsonProcessingException {
    ElasticSearchPerson ret;
    Serializable primaryKey = p.getPrimaryKey();

    if (primaryKey == null) {
      LOGGER.debug("NULL PRIMARY KEY: {}", p);
      primaryKey = "MISSING_ID";
    }

    // Write persistence object to Elasticsearch Person document.
    ret = new ElasticSearchPerson(primaryKey.toString(), // id
        p.getFirstName(), // first name
        p.getLastName(), // last name
        p.getMiddleName(), // middle name
        p.getNameSuffix(), // name suffix
        p.getGender(), // gender
        DomainChef.cookDate(p.getBirthDate()), // birth date
        p.getSsn(), // SSN
        p.getClass().getName(), // type
        mapper.writeValueAsString(p), // source
        null, // omit highlights
        buildAddress(p), buildPhone(p), buildLanguage(p), buildScreening(p));

    // Legacy descriptor
    ret.setLegacyDescriptor(buildLegacyDescriptor(p));

    // Sealed and sensitive.
    ret.setSensitivityIndicator(p.getSensitivityIndicator());

    // Set client counties
    final List<ElasticSearchSystemCode> clientCounties = buildClientCounties(p);
    ret.setClientCounties(clientCounties);
    // Added only for backward compatibility.
    if (!clientCounties.isEmpty()) {
      ret.setClientCounty(clientCounties.get(0));
    }

    // Set race/ethnicity
    ret.setCleintRace(buildRaceEthnicity(p));

    // Index number
    ret.setIndexNumber(
        StringUtils.isBlank(p.getClientIndexNumber()) ? null : p.getClientIndexNumber());

    // AKAs
    ret.setAkas(buildAkas(p));

    // CSECs
    ret.setCsecs(buildCsecs(p));

    // Safety alerts
    ret.setSafetyAlerts(buildSafetyAlerts(p));

    // Open case id
    ret.setOpenCaseId(buildOpenCaseId(p));

    // Open case responsible agency code
    ret.setOpenCaseResponsibleAgencyCode(buildOpenCaseResponsibleAgencyCode(p));

    // Death date
    ret.setDateOfDeath(DomainChef.cookDate(p.getDeathDate()));

    // Update time stamp
    ret.setIndexUpdateTime(DomainChef.cookStrictTimestamp(new Date()));
    ret.setSource("");

    return ret;
  }

  public static synchronized ObjectMapper getMapper() {
    return mapper;
  }

  public static synchronized void setMapper(final ObjectMapper mapper) {
    if (LiveElasticTransformer.mapper == null) {
      LiveElasticTransformer.mapper = mapper;
    }
  }

}
