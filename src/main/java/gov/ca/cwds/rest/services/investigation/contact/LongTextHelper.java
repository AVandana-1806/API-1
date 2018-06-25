package gov.ca.cwds.rest.services.investigation.contact;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.rest.api.domain.cms.LongText;
import gov.ca.cwds.rest.api.domain.cms.PostedLongText;
import gov.ca.cwds.rest.services.cms.LongTextService;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class LongTextHelper {

  private LongTextService longTextService;

  /**
   * @param longTextService the {@link gov.ca.cwds.rest.services.Service} handling
   *        {@link gov.ca.cwds.data.persistence.cms.LongText} objects
   */
  @Inject
  public LongTextHelper(LongTextService longTextService) {
    this.longTextService = longTextService;
  }

  /**
   * Find from the Long Text table the text value of the Detail Text
   * 
   * @param detailText the Detail Text
   * @return text value of the Detail Text
   */
  public String getLongText(String detailText) {
    LongText detail = new LongText();
    if (detailText != null) {
      detail = longTextService.find(detailText);
    }
    return (Optional.of(detail)).map(LongText::getTextDescription).orElse(null);
  }

  /**
   * Creates Long Text Entity and returns the identifier
   * 
   * @param countySpecificCode the county of the referral
   * @param textDescription the text
   * @return identifier of the posted LongText entity
   */
  public String createLongText(String textDescription, String countySpecificCode) {
    gov.ca.cwds.rest.api.domain.cms.LongText longText =
        new gov.ca.cwds.rest.api.domain.cms.LongText(countySpecificCode, textDescription);
    PostedLongText postedLongText = longTextService.create(longText);

    return postedLongText.getId();
  }

  /**
   * Update Long Text Entity Or Create a new entity and returns the identifier
   * 
   * @param longTextId the longText identifier
   * @param countySpecificCode the county of the referral
   * @param note the text description
   * @return identifier of the posted LongText entity
   * 
   */
  public String updateLongText(String longTextId, String note, String countySpecificCode) {
    if (StringUtils.isBlank(longTextId)) {
      return handleEmptyLongTextId(longTextId, note, countySpecificCode);
    } else {
      return handleExistingLongTextId(longTextId, note, countySpecificCode);
    }
  }

  private String handleExistingLongTextId(String longTextId, String note,
      String countySpecificCode) {
    if (StringUtils.isBlank(note)) {
      longTextService.delete(longTextId);
      return null;
    }

    gov.ca.cwds.rest.api.domain.cms.LongText longText =
        new gov.ca.cwds.rest.api.domain.cms.LongText(countySpecificCode, note);
    longTextService.update(longTextId, longText);
    return longTextId;
  }

  private String handleEmptyLongTextId(String longTextId, String note, String countySpecificCode) {
    if (!StringUtils.isBlank(note)) {
      return createLongText(note, countySpecificCode);
    }
    return longTextId;
  }

}
