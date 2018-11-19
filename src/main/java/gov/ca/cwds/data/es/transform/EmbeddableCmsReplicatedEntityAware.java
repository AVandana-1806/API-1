package gov.ca.cwds.data.es.transform;

/**
 * Simple interface returns a {@link EmbeddableCmsReplicatedEntity}.
 * 
 * @see EmbeddableCmsReplicatedEntity
 * @author CWDS API Team
 */
@FunctionalInterface
public interface EmbeddableCmsReplicatedEntityAware {

  EmbeddableCmsReplicatedEntity getReplicatedEntity();

}
