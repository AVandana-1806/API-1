package gov.ca.cwds.es.live;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiGroupNormalizer;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Denormalized
 */
@JsonPropertyOrder(alphabetic = true)
@Entity
@Table(name = "VW_WHATEVER")
public class TestDenormalizedEntity implements PersistentObject,
    ApiGroupNormalizer<TestNormalizedEntity>, ApiMultiplePersonAware, CmsReplicatedEntity {

  private String id;
  private String[] names;

  private EmbeddableCmsReplicatedEntity replicatedEntity = new EmbeddableCmsReplicatedEntity();

  public TestDenormalizedEntity() {
    // no-op.
  }

  public TestDenormalizedEntity(String id, String... names) {
    this.id = id;
    this.names = names;
  }

  @Override
  public Class<TestNormalizedEntity> getNormalizationClass() {
    return TestNormalizedEntity.class;
  }

  @Override
  public String getNormalizationGroupKey() {
    return id;
  }

  @Override
  public TestNormalizedEntity normalize(Map<Object, TestNormalizedEntity> map) {
    final String thisId = getNormalizationGroupKey();

    TestNormalizedEntity ret;

    if (map.containsKey(thisId)) {
      ret = map.get(thisId);
    } else {
      ret = new TestNormalizedEntity(thisId);
      map.put(thisId, ret);
    }

    if (names != null && names.length > 0) {
      for (String x : names) {
        ret.addEntry(new TestNormalizedEntry(thisId, x));
      }
    }

    return ret;
  }

  @Override
  public Serializable getPrimaryKey() {
    return "abc1234567";
  }

  @Override
  public ApiPersonAware[] getPersons() {
    final ApiPersonAware[] ret = {new TestOnlyApiPersonAware()};
    return ret;
  }

  // =======================
  // CmsReplicatedEntity:
  // =======================

  @Override
  public EmbeddableCmsReplicatedEntity getReplicatedEntity() {
    return replicatedEntity;
  }

  // =======================
  // ApiLegacyAware:
  // =======================

  @Override
  public String getLegacyId() {
    return id;
  }

  @Override
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return LiveElasticTransformer.createLegacyDescriptor(getId(),
        replicatedEntity.getReplicationDate(), LegacyTable.CLIENT);
  }

  @Override
  public String getId() {
    return id;
  }

}
