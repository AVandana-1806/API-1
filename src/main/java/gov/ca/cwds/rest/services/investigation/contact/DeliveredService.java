package gov.ca.cwds.rest.services.investigation.contact;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.dao.contact.DeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.LastUpdatedBy;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactReferralRequest;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class DeliveredService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveredService.class);

  private DeliveredServiceDao deliveredServiceDao;
  private StaffPersonDao staffPersonDao;
  private LongTextHelper longTextHelper;

  /**
   * @param deliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity} objects
   * @param staffPersonDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects.
   * @param longTextHelper the helper class handling
   *        {@link gov.ca.cwds.data.persistence.cms.LongText} objects
   * 
   */
  @Inject
  public DeliveredService(DeliveredServiceDao deliveredServiceDao, StaffPersonDao staffPersonDao,
      LongTextHelper longTextHelper) {
    this.deliveredServiceDao = deliveredServiceDao;
    this.staffPersonDao = staffPersonDao;
    this.longTextHelper = longTextHelper;
  }

  /**
   * Find the Staff Person who last updated the deliveredServiceEntity persistence object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return The Staff Person who last updated the persistence object
   */
  protected LastUpdatedBy getTheLastUpdatedByStaffPerson(
      DeliveredServiceEntity deliveredServiceEntity) {
    String lastUpdatedByStaffPersonId = deliveredServiceEntity.getLastUpdatedId();
    if (StringUtils.isNotBlank(lastUpdatedByStaffPersonId)) {
      StaffPerson lastUpdtatedId = staffPersonDao.find(lastUpdatedByStaffPersonId);
      if (lastUpdtatedId != null) {
        return new LastUpdatedBy(lastUpdtatedId);
      }
    }
    return new LastUpdatedBy();
  }

  /**
   * Combine DetailText And DetailTextContinuation from the deliveredServiceEntity persistence
   * object
   * 
   * @param deliveredServiceEntity The persistence object
   * @return the combined DetailText And DetailTextContinuation
   */
  String combineDetailTextAndContinuation(DeliveredServiceEntity deliveredServiceEntity) {
    String detailText = longTextHelper.getLongText(deliveredServiceEntity.getDetailText());

    String detailTextContinuation =
        longTextHelper.getLongText(deliveredServiceEntity.getDetailTextContinuation());
    if (detailText == null && detailTextContinuation == null) {
      return null;
    }
    detailText = detailText != null ? detailText : "";
    detailTextContinuation = detailTextContinuation != null ? detailTextContinuation : "";
    detailText = detailText.startsWith("Part")
        ? detailText.substring(detailText.indexOf(':') + 2, detailText.length())
        : detailText;
    return detailText + detailTextContinuation;
  }

  public DeliveredServiceEntity find(String contactId) {
    return deliveredServiceDao.find(contactId);
  }

  /**
   * Creates a record in DeliveredService and returns the unique identifier
   * 
   * @param request the request
   * @param countySpecificCode the county of the referral
   * @return the DeliveredServiceDomain
   */
  public String create(ContactReferralRequest request, String countySpecificCode) {

    List<DeliveredServiceDomain> deliveredServiceDomains =
        constructDeliveredServiceDomainForCreate(request, countySpecificCode);
    String primaryDeliveredServiceId = null;

    try {

      for (DeliveredServiceDomain deliveredServiceDomain : deliveredServiceDomains) {

        gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity persistedDeliveredService =
            new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(
                CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
                deliveredServiceDomain, RequestExecutionContext.instance().getStaffId(),
                RequestExecutionContext.instance().getRequestStartTime());
        persistedDeliveredService = deliveredServiceDao.create(persistedDeliveredService);
        if (StringUtils.isBlank(primaryDeliveredServiceId)) {
          primaryDeliveredServiceId = persistedDeliveredService.getId();

        }
      }
    } catch (EntityExistsException e) {
      LOGGER.info("Internal Error : deliveredServiceEntity couldn't create it for Referral id : {}",
          request.getReferralId());
      throw new ServiceException(e);
    }

    return primaryDeliveredServiceId;
  }

  /**
   * Construct the DeliveredService object for create
   *
   * @param request the request
   * @param countySpecificCode
   * @return the DeliveredServiceDomain
   */
  private List<DeliveredServiceDomain> constructDeliveredServiceDomainForCreate(
      ContactReferralRequest request, String countySpecificCode) {
    List<DeliveredServiceDomain> deliveredServiceDomains = new ArrayList<>();
    DeliveredServiceDomain deliveredServiceDomain = null;
    ContactRequest contactRequest = request.getContactRequest();
    String note = null;
    String noteContinuation = null;
    // note is not blank
    int partCount = 0;
    if (StringUtils.isNoneBlank(contactRequest.getNote())) {
      List<String> splitNotes = this.extractLongText(contactRequest.getNote());
      int index = -1;
      while (index < splitNotes.size()) {
        note = this.getNextText(++index, splitNotes);
        noteContinuation = this.getNextText(++index, splitNotes);
        if (note != null) {
          ++partCount;
          deliveredServiceDomain = createDeliveredServiceDomain(countySpecificCode, contactRequest,
              "Part " + partCount + ": " + note, noteContinuation);
          deliveredServiceDomains.add(deliveredServiceDomain);
        }

      }

    } else {
      deliveredServiceDomain =
          createDeliveredServiceDomain(countySpecificCode, contactRequest, note, noteContinuation);
      deliveredServiceDomains.add(deliveredServiceDomain);
    }
    return deliveredServiceDomains;

  }

  private DeliveredServiceDomain createDeliveredServiceDomain(String countySpecificCode,
      ContactRequest contactRequest, String note, String noteContinuation) {
    Integer serviceContactType = Integer.valueOf(contactRequest.getPurpose());
    String endDate = getDateStringFromDateTime(contactRequest.getEndedAt());
    String endTime = getTimeStringFromDateTime(contactRequest.getEndedAt());
    String startDate = getDateStringFromDateTime(contactRequest.getStartedAt());
    String startTime = getTimeStringFromDateTime(contactRequest.getStartedAt());

    String longTextId =
        (StringUtils.isNotBlank(note)) ? longTextHelper.createLongText(note, countySpecificCode)
            : null;
    String longTextContinuationId = (StringUtils.isNotBlank(noteContinuation))
        ? longTextHelper.createLongText(noteContinuation, countySpecificCode)
        : null;

    return DeliveredServiceDomain.createWithDefaultsForFieldsNotPopulatedByUI(
        Integer.valueOf(contactRequest.getCommunicationMethod()),
        Integer.valueOf(contactRequest.getLocation()), countySpecificCode, longTextId,
        longTextContinuationId, endDate, endTime, serviceContactType, startDate, startTime,
        contactRequest.getStatus());
  }

  /**
   * Update a record in DeliveredService and return the DeliveredServiceDomain object
   *
   * @param contactId the Delivered Service Identifier
   * @param request the request
   * @param countySpecificCode the county of the referral
   * @return the DeliveredServiceDomain
   */
  public DeliveredServiceDomain update(String contactId, ContactReferralRequest request,
      String countySpecificCode) {

    DeliveredServiceDomain deliveredServiceDomain =
        constructDeliveredServiceDomainForUpdate(contactId, request, countySpecificCode);

    try {
      gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity persistedDeliveredService =
          new gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity(contactId,
              deliveredServiceDomain, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      persistedDeliveredService = deliveredServiceDao.update(persistedDeliveredService);
      return new gov.ca.cwds.rest.api.contact.DeliveredServiceDomain(persistedDeliveredService);

    } catch (Exception e) {
      LOGGER.info("deliveredServiceEntity Exception ", e);
      throw new ServiceException(e);
    }

  }

  /**
   * Construct the DeliveredService object for update
   *
   * @param contactId the Delivered Service Identifier
   * @param request the request
   * @param countySpecificCode
   * @return the DeliveredServiceDomain
   */
  private DeliveredServiceDomain constructDeliveredServiceDomainForUpdate(String contactId,
      ContactReferralRequest request, String countySpecificCode) {
    ContactRequest contactRequest = request.getContactRequest();
    Integer serviceContactType = Integer.valueOf(contactRequest.getPurpose());
    String endDate = getDateStringFromDateTime(contactRequest.getEndedAt());
    String endTime = getTimeStringFromDateTime(contactRequest.getEndedAt());
    String startDate = getDateStringFromDateTime(contactRequest.getStartedAt());
    String startTime = getTimeStringFromDateTime(contactRequest.getStartedAt());
    String note = getNoteFromRequest(contactRequest.getNote());
    String noteContinuation = getNoteContinuationFromRequest(contactRequest.getNote());

    DeliveredServiceEntity deliveredServiceEntity = deliveredServiceDao.find(contactId);

    String longTextId = longTextHelper.updateLongText(deliveredServiceEntity.getDetailText(), note,
        countySpecificCode);

    String longTextContinuationId = longTextHelper.updateLongText(
        deliveredServiceEntity.getDetailTextContinuation(), noteContinuation, countySpecificCode);

    return DeliveredServiceDomain.updateWithDeliveredServiceEntityValuesForFieldsNotPopulatedByUI(
        deliveredServiceEntity, Integer.valueOf(contactRequest.getCommunicationMethod()),
        Integer.valueOf(contactRequest.getLocation()), countySpecificCode, longTextId,
        longTextContinuationId, endDate, endTime, serviceContactType, startDate, startTime,
        contactRequest.getStatus());
  }

  private String getNoteFromRequest(String note) {
    return (StringUtils.isNotBlank(note) && note.length() > 4000) ? note.substring(0, 3999) : note;
  }

  private String getNoteContinuationFromRequest(String note) {
    return (StringUtils.isNotBlank(note) && note.length() > 4000) ? note.substring(4000) : null;
  }

  private String getTimeStringFromDateTime(String dateTime) {
    String trimmedDateTime = StringUtils.isNotBlank(dateTime) ? dateTime.trim() : null;
    return StringUtils.isNotBlank(trimmedDateTime)
        ? DomainChef.cookTime(Date.from(Instant.parse(trimmedDateTime)))
        : null;
  }

  private String getDateStringFromDateTime(String dateTime) {
    String trimmedDateTime = StringUtils.isNotBlank(dateTime) ? dateTime.trim() : null;
    return StringUtils.isNotBlank(trimmedDateTime)
        ? DomainChef.cookDate(Date.from(Instant.parse(trimmedDateTime)))
        : null;
  }

  /**
   * 
   * @param longNote - complete full long text which comes from UI.
   * @return list of split text
   */
  private List<String> extractLongText(String longNote) {
    List<String> lengthTexts = new ArrayList<>();
    String note = longNote;

    int beginIndex = 0;
    int count = 3800;
    int endIndex = count;
    String tempText = null;
    String longText = null;
    while (beginIndex < note.length()) {

      tempText = getTextCertainRange(note, beginIndex, endIndex);
      endIndex = reDetermineEndIndexBasedRule(tempText, endIndex, count, note);
      longText = getTextCertainRange(note, beginIndex, endIndex);
      lengthTexts.add(longText);
      if (endIndex < note.length()) {
        note = getTextCertainRange(note, endIndex + 1, note.length());
        beginIndex = 0;
        endIndex = count;
        endIndex = endIndex > note.length() ? note.length() : endIndex;
      } else {
        beginIndex = note.length();

      }
    }
    return lengthTexts;
  }

  /**
   * getting specified string based on certain limit
   * 
   * @param note - complete full long text which comes from UI.
   * @param beginIndex - begin index
   * @param endIndex - end index
   * @return temp text based on certain range
   */
  private String getTextCertainRange(String note, int beginIndex, int endIndex) {
    // changing endIndex if full long text is smaller than estimated count.
    int inRangeEndIndex = endIndex;
    inRangeEndIndex = (note.length() < endIndex) ? note.length() : endIndex;
    return note.substring(beginIndex, inRangeEndIndex);
  }

  /**
   * determining end index count based on last period(.) or space (" ") or can fit entire text.
   * 
   * @param temptext - temp text to create longText
   * @param endIndex - end of index
   * @param count - count of chars to be stored
   * @param note - complete full long text which comes from UI.
   * @return - end index
   */
  private int reDetermineEndIndexBasedRule(String temptext, int endIndex, int count, String note) {
    int tempEndIndex = 0;
    // it can fit entire text - no need to split by last period (.) or space(" ")
    if (note.length() < count) {
      return endIndex;
    }
    // if period (.) found, then consider that position as end point
    if (temptext.lastIndexOf('.') > -1) {
      tempEndIndex = temptext.lastIndexOf('.');
    }
    // if space (" ") found, then consider that position as end point
    else if (temptext.lastIndexOf(' ') > -1) {
      tempEndIndex = temptext.lastIndexOf(' ');
    }

    return tempEndIndex > 0 ? tempEndIndex : endIndex;
  }

  /**
   * fetching content from list
   * 
   * @param index - index to fetch the content
   * @param splitNotes - list of split notes
   * @return note based on index count
   */
  private String getNextText(int index, List<String> splitNotes) {
    return (splitNotes.size() > index) ? splitNotes.get(index) : null;
  }

}
