package gov.ca.cwds.es.live;

import static gov.ca.cwds.rest.api.domain.DomainChef.freshDate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.data.persistence.cms.BaseAddress;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * {@link CmsPersistentObject} that represents an Address in the replicated schema.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"findsecbugs:SQL_INJECTION_JDBC", "squid:S2095",
    "findbugs:SE_TRANSIENT_FIELD_NOT_RESTORED", "squid:S1206", "serial"})
@Entity
@Table(name = "ADDRS_T")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedAddress extends BaseAddress implements CmsReplicatedEntity {

  private EmbeddableCmsReplicatedEntity replicatedEntity = new EmbeddableCmsReplicatedEntity();

  public ReplicatedAddress() {
    super();
  }

  @Override
  public EmbeddableCmsReplicatedEntity getReplicatedEntity() {
    return replicatedEntity;
  }

  @Override
  public String getLegacyId() {
    return getId();
  }

  @Override
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return LiveElasticTransformer.createLegacyDescriptor(getId(), getLastUpdatedTime(),
        LegacyTable.ADDRESS);
  }

  @Override
  public CmsReplicationOperation getReplicationOperation() {
    return replicatedEntity.getReplicationOperation();
  }

  @Override
  public Date getReplicationDate() {
    return freshDate(replicatedEntity.getReplicationDate());
  }

  @Override
  public void setReplicationOperation(CmsReplicationOperation replicationOperation) {
    this.replicatedEntity.setReplicationOperation(replicationOperation);
  }

  @Override
  public void setReplicationDate(Date replicationDate) {
    this.replicatedEntity.setReplicationDate(freshDate(replicationDate));
  }

}
