package gov.ca.cwds.rest.services.investigation.contact;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gov.ca.cwds.rest.api.domain.DomainObject;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ReferralDao;
import gov.ca.cwds.data.dao.contact.ContactPartyDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.contact.ContactPartyDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.IndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.investigation.contact.Contact;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactList;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.business.rules.R05904ContactStartedAt;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link DeliveredServiceEntity} and related entities
 * {@link IndividualDeliveredServiceEntity}, {@link ReferralClientDeliveredServiceEntity},
 * {@link ContactPartyDeliveredServiceEntity}, {@link gov.ca.cwds.data.persistence.cms.LongText}
 * 
 * @author CWDS API Team
 */
public class ContactService implements TypedCrudsService<String, ContactReferralRequest, Response> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

  private DeliveredService deliveredService;
  private ReferralClientDeliveredService referralClientDeliveredService;
  private DeliveredToIndividualService deliveredToIndividualService;
  private ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao;
  private ReferralDao referralDao;

  /**
   * @param deliveredService the {@link gov.ca.cwds.rest.services.Service} handling
   *        {@link DeliveredServiceEntity} objects
   * @param referralClientDeliveredService the {@link gov.ca.cwds.rest.services.Service} handling
   *        {@link ReferralClientDeliveredServiceEntity} objects
   * @param contactPartyDeliveredServiceDao the {@link Dao} handling
   *        {@link ContactPartyDeliveredServiceEntity} objects
   * @param deliveredToIndividualService the deliveredToIndividualService
   * @param referralDao the {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Referral}
   *        objects
   */
  @Inject
  public ContactService(DeliveredService deliveredService,
      ReferralClientDeliveredService referralClientDeliveredService,
      DeliveredToIndividualService deliveredToIndividualService,
      ContactPartyDeliveredServiceDao contactPartyDeliveredServiceDao, ReferralDao referralDao) {
    this.deliveredService = deliveredService;
    this.referralClientDeliveredService = referralClientDeliveredService;
    this.contactPartyDeliveredServiceDao = contactPartyDeliveredServiceDao;
    this.deliveredToIndividualService = deliveredToIndividualService;
    this.referralDao = referralDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(String primaryKey) {
    if (!primaryKey.contains(":")) {
      return findAllContactsForTheReferral(primaryKey);
    } else {
      String contactId = retrieveContactId(primaryKey);
      return contactId != null ? findSingleContact(contactId) : null;
    }
  }

  private Contact findSingleContact(String contactId) {
    final DeliveredServiceEntity deliveredServiceEntity = deliveredService.find(contactId);
    if (deliveredServiceEntity == null) {
      return null;
    }
    LastUpdatedBy lastUpdatedBy =
        deliveredService.getTheLastUpdatedByStaffPerson(deliveredServiceEntity);
    String note = deliveredService.combineDetailTextAndContinuation(deliveredServiceEntity);

    Map<String, IndividualDeliveredService> peopleInIndividualDeliveredService =
        deliveredToIndividualService.getPeopleInIndividualDeliveredService(deliveredServiceEntity);

    return new Contact(deliveredServiceEntity, lastUpdatedBy, note,
        peopleInIndividualDeliveredService);
  }

  /**
   * finding contacts by referral id
   * 
   * @param referralId - referral/investigation id
   * @return - list of referral contacts
   */
  public ContactList findAllContactsForTheReferral(String referralId) {
    final Set<Contact> contacts = new HashSet<>();
    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServiceEntities =
        referralClientDeliveredService.findByReferralId(referralId.trim());
    for (ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity : referralClientDeliveredServiceEntities) {
      Contact contact = this.findSingleContact(referralClientDeliveredServiceEntity
          .getReferralClientDeliveredServiceEmbeddable().getDeliveredServiceId());
      contacts.add(contact);
    }
    return new ContactList(contacts);
  }

  /**
   * Extract Contact Id from the primary key and return it if it is valid for the Referral Id
   * 
   * @param primaryKey the combination of referralId and contactId
   * @return The contactId
   */
  private String retrieveContactId(String primaryKey) {
    String[] identifiers = primaryKey.split(":");
    String referralId = identifiers[0].trim();
    String contactId = identifiers[1].trim();
    return isContactIdValid(referralId, contactId) ? contactId : null;
  }

  /**
   * Check if Contact Id is valid for the Referral Id
   * 
   * @param referralId the Referral Id
   * @param contactId the Contact Id
   */
  private boolean isContactIdValid(String referralId, String contactId) {

    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServiceEntities =
        referralClientDeliveredService.findByReferralId(referralId);
    if (referralClientDeliveredServiceEntities.length == 0) {
      return false;
    }
    for (ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity : referralClientDeliveredServiceEntities) {
      if (referralClientDeliveredServiceEntity.getReferralClientDeliveredServiceEmbeddable()
          .getDeliveredServiceId().equals(contactId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * <pre>
   * Create a Contact ... this includes the following,
   * creates two records in LongText creates a record in DeliveredService
   * creates a record in ReferralClientDeliveredService for each Child Client associated with the Referral
   * create a record in IndividualDeliveredService for each Person in persons 
   * create a record in ContactPartyDeliveredService
   * </pre>
   * 
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response create(ContactReferralRequest request) {
    validateCurrentUserStaffId(RequestExecutionContext.instance().getStaffId());
    ContactRequest contactRequest = request.getContactRequest();
    Referral referral = validateReferral(request);
    validateContactStartDate(contactRequest.getStartedAt(), referral.getReceivedDate(),
        referral.getReceivedTime());
    String referralId = referral.getId();
    String countySpecificCode = referral.getCountySpecificCode();
    String deliveredServiceId = deliveredService.create(request, countySpecificCode);
    Integer serviceContactType = Integer.valueOf(contactRequest.getPurpose());
    referralClientDeliveredService.addOnBehalfOfClients(deliveredServiceId, referralId,
        countySpecificCode);
    deliveredToIndividualService.addPeopleToIndividualDeliveredService(deliveredServiceId,
        contactRequest, countySpecificCode);

    contactPartyDeliveredServiceDao.create(new ContactPartyDeliveredServiceEntity(
        CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
        serviceContactType.shortValue(), countySpecificCode, deliveredServiceId,
        RequestExecutionContext.instance().getStaffId(),
        RequestExecutionContext.instance().getRequestStartTime()));

    return this.find(referralId + ":" + deliveredServiceId);
  }

  private void validateCurrentUserStaffId(String staffId) {
    if (StringUtils.isBlank(staffId)) {
      LOGGER.error(
          "The Logged In User Staff Id is not Provided. We cannot create or update without this Identifier : {}",
          staffId);
      throw new ServiceException(
          "The Logged In User Staff Id is not Provided. We cannot create or update without this Identifier");
    }
  }

  /**
   * Validates that there exists a Referral for the given identifier and returns the Referral
   * 
   * @param request the ContactReferralRequest
   * @return the Referral
   */
  Referral validateReferral(ContactReferralRequest request) {
    String referralId = request.getReferralId();
    Referral referral = referralDao.find(referralId);

    if (referral == null) {
      LOGGER.error("ReferralId is not Valid : {}", referralId);
      throw new ServiceException("ReferralId is not Valid");
    }

    return referral;
  }

  /**
   * <pre>
   * Update a Contact ... this includes the following,
   * update the two records in LongText and update the record in DeliveredService
   * creates a record in ReferralClientDeliveredService for each new Child Client associated with the Referral
   * delete records in ReferralClientDeliveredService for each Child Client removed from association with the Referral
   * create a record in IndividualDeliveredService for each new Person in persons 
   * delete a record in IndividualDeliveredService for each Person removed from persons 
   * update a record in ContactPartyDeliveredService
   * </pre>
   * 
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(String primaryKey, ContactReferralRequest request) {
    validateCurrentUserStaffId(RequestExecutionContext.instance().getStaffId());
    ContactRequest contactRequest = request.getContactRequest();
    Referral referral = validateReferral(request);
    validateContactStartDate(contactRequest.getStartedAt(), referral.getReceivedDate(),
        referral.getReceivedTime());
    String referralId = referral.getId();
    String countySpecificCode = referral.getCountySpecificCode();
    deliveredService.update(primaryKey, request, countySpecificCode);
    Integer serviceContactType = Integer.valueOf(contactRequest.getPurpose());
    String deliveredServiceId = primaryKey;
    referralClientDeliveredService.updateOnBehalfOfClients(deliveredServiceId, referralId,
        countySpecificCode);
    deliveredToIndividualService.updatePeopleToIndividualDeliveredService(deliveredServiceId,
        contactRequest, countySpecificCode);
    ContactPartyDeliveredServiceEntity entity =
        contactPartyDeliveredServiceDao.findByDeliveredServiceId(primaryKey);
    contactPartyDeliveredServiceDao.update(new ContactPartyDeliveredServiceEntity(
        entity.getPrimaryKey(), serviceContactType.shortValue(), countySpecificCode,
        deliveredServiceId, RequestExecutionContext.instance().getStaffId(),
        RequestExecutionContext.instance().getRequestStartTime()));

    return this.find(referralId + ":" + deliveredServiceId);
  }

  private void validateContactStartDate(String contactStartedAt, Date referralReceivedDate,
      Date referralReceivedTime) {
    Date referralReceivedDateTime;
    referralReceivedDateTime =
        DomainChef.concatenateDateAndTime(referralReceivedDate, referralReceivedTime);
    Date contactStartedAtDateTime = DateTimeFormat.forPattern(DomainObject.TIMESTAMP_ISO8601_FORMAT)
            .parseDateTime(contactStartedAt.trim()).toDate();
    if (!new R05904ContactStartedAt(contactStartedAtDateTime, referralReceivedDateTime).isValid()) {
      throw new ServiceException(
          "Contact Started At is the same or before the Referral Received Date");
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Contact delete(String primaryKey) {
    throw new NotImplementedException("delete not implemented");
  }

}
