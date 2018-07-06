package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.ns.RelationshipDao;
import gov.ca.cwds.data.persistence.ns.Relationship;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.ScreeningRelationship;
import gov.ca.cwds.rest.api.domain.enums.SameHomeStatus;

import java.io.Serializable;
import java.util.Date;

import gov.ca.cwds.rest.services.investigation.ClientsRelationshipsService;
import gov.ca.cwds.rest.services.mapper.RelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreeningRelationshipService implements CrudsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningRelationshipService.class);
    private RelationshipDao relationshipDao;
    private RelationshipMapper relationshipMapper;
    private ClientsRelationshipsService clientsRelationshipsService;

    @Inject
    public ScreeningRelationshipService(RelationshipDao relationshipDao,
                                        RelationshipMapper relationshipMapper,
                                        ClientsRelationshipsService clientsRelationshipsService) {
        super();
        this.relationshipDao = relationshipDao;
        this.relationshipMapper = relationshipMapper;
        this.clientsRelationshipsService = clientsRelationshipsService;
    }

    @Override
    public Response find(Serializable serializable) {
        assert serializable instanceof String;
        Relationship entity = relationshipDao.find(serializable);
        if (entity != null) {
            return relationshipMapper.map(entity);
        }
        return null;
    }

    @Override
    public Response delete(Serializable serializable) {
        return null;
    }

    @Override
    public Response create(Request request) {
        ScreeningRelationship relationship = (ScreeningRelationship) request;
        Relationship entity = new Relationship(null, relationship.getClientId(),
                relationship.getRelativeId(), relationship.getRelationshipType(),
                new Date(), new Date(), relationship.isAbsentParentIndicator(),
                SameHomeStatus.toValue(relationship.getSameHomeStatus()), relationship.getLegacyId(), relationship.getStartDate(), relationship.getEndDate());
        entity = relationshipDao.create(entity);
        relationship.setId(entity.getId());
        LOGGER.debug("saved relationship {}", relationship);
        return relationship;
    }

    @Override
    public Response update(Serializable serializable, Request request) {
        assert serializable instanceof String;
        ScreeningRelationship relationship = (ScreeningRelationship) request;
        Relationship entity = relationshipDao.find(serializable);
        if (entity == null) {
            return null;
        }

        entity.setRelativeId(relationship.getRelativeId());
        entity.setRelationshipType(relationship.getRelationshipType());
        entity.setUpdatedAt(new Date());
        entity.setAbsentParentIndicator(relationship.isAbsentParentIndicator());
        entity.setSameHomeStatus(SameHomeStatus.toValue(relationship.getSameHomeStatus()));
        relationshipDao.update(entity);
        LOGGER.debug("updated relationship {}", relationship);

        relationship.setId(entity.getId());
        return relationship;
    }
}
