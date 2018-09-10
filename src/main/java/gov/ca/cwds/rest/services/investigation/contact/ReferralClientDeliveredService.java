package gov.ca.cwds.rest.services.investigation.contact;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ChildClientDao;
import gov.ca.cwds.data.dao.contact.ReferralClientDeliveredServiceDao;
import gov.ca.cwds.data.persistence.cms.ChildClient;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Business layer object to work on {@link DeliveredServiceEntity}
 * 
 * @author CWDS API Team
 */
public class ReferralClientDeliveredService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ReferralClientDeliveredService.class);

  private String currentUserStaffId = RequestExecutionContext.instance().getStaffId();
  private Date currentRequestStartTime = RequestExecutionContext.instance().getRequestStartTime();
  private ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao;
  private ChildClientDao childClientDao;

  /**
   * @param referralClientDeliveredServiceDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.contact.ReferralClientDeliveredServiceEntity}
   *        objects
   * @param childClientDao the {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ChildClient} objects
   */
  @Inject
  public ReferralClientDeliveredService(
      ReferralClientDeliveredServiceDao referralClientDeliveredServiceDao,
      ChildClientDao childClientDao) {
    this.referralClientDeliveredServiceDao = referralClientDeliveredServiceDao;
    this.childClientDao = childClientDao;
  }

  /**
   * Check if Contact Id is valid for the Referral Id
   * 
   * @param referralId the Referral Id
   * @param contactId the Contact Id
   * 
   */
  protected void checkContactIdValidForGivenReferralId(String referralId, String contactId) {

    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServiceEntities =
        findReferralClientDeliveredServiceEntities(referralId);

    for (ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity : referralClientDeliveredServiceEntities) {
      if (referralClientDeliveredServiceEntity.getReferralClientDeliveredServiceEmbeddable()
          .getDeliveredServiceId().equals(contactId)) {
        return;
      }
    }
    throw new ServiceException("ContactId is not Valid For the Given ReferralId");
  }

  private ReferralClientDeliveredServiceEntity[] findReferralClientDeliveredServiceEntities(
      String referralId) {
    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServiceEntities =
        referralClientDeliveredServiceDao.findByReferralId(referralId);
    if (referralClientDeliveredServiceEntities.length == 0) {
      LOGGER.info("There are no Contacts For the Given ReferralId : {}", referralId);
      throw new ServiceException("There are no Contacts For the Given ReferralId");
    }
    return referralClientDeliveredServiceEntities;
  }

  /**
   * Adds a record to ReferralClientDeliveredService for each victim child client associated with
   * the Referral
   * 
   * @param deliveredServiceId the identifier for the DeliveredService
   * @param referralId the identifier for the Referral
   * @param countySpecificCode the county of the referral
   */
  protected void addOnBehalfOfClients(String deliveredServiceId, String referralId,
      String countySpecificCode) {
    List<ChildClient> victimClients = childClientDao.findVictimClients(referralId);
    if (victimClients != null && !victimClients.isEmpty()) {
      addReferralClientDeliveredServiceEntities(victimClients, deliveredServiceId, referralId,
          countySpecificCode);
    } else {
      throw new ServiceException(
          "An  On Behalf Of Client for the referral could not be found. At least one On Behalf Of Client should exist");
    }
  }

  private void addReferralClientDeliveredServiceEntities(List<ChildClient> victimClients,
      String deliveredServiceId, String referralId, String countySpecificCode) {
    for (ChildClient childClient : victimClients) {
      ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity =
          new ReferralClientDeliveredServiceEntity(deliveredServiceId, referralId,
              childClient.getPrimaryKey(), countySpecificCode, currentUserStaffId,
              currentRequestStartTime);
      if (referralClientDeliveredServiceDao
          .find(referralClientDeliveredServiceEntity.getPrimaryKey()) == null) {
        referralClientDeliveredServiceDao.create(referralClientDeliveredServiceEntity);
      }
    }

  }

  public ReferralClientDeliveredServiceEntity[] findByReferralId(String referralId) {
    return referralClientDeliveredServiceDao.findByReferralId(referralId);
  }

  /**
   * Updates a record to ReferralClientDeliveredService for each victim child client associated with
   * the Referral
   * 
   * @param deliveredServiceId the identifier for the DeliveredService
   * @param referralId the identifier for the Referral
   * @param countySpecificCode the county of the referral
   */
  public void updateOnBehalfOfClients(String deliveredServiceId, String referralId,
      String countySpecificCode) {
    ReferralClientDeliveredServiceEntity[] referralClientDeliveredServices =
        referralClientDeliveredServiceDao.findByReferralId(referralId);

    List<ChildClient> victimClients = childClientDao.findVictimClients(referralId);
    if (victimClients != null && !victimClients.isEmpty()) {
      for (ChildClient childClient : victimClients) {
        if (notExistsReferralClientDeliveredServiceEntity(referralClientDeliveredServices,
            childClient.getPrimaryKey())) {
          ReferralClientDeliveredServiceEntity referralClientDeliveredServiceEntity =
              new ReferralClientDeliveredServiceEntity(deliveredServiceId, referralId,
                  childClient.getPrimaryKey(), countySpecificCode, currentUserStaffId,
                  currentRequestStartTime);
          referralClientDeliveredServiceDao.create(referralClientDeliveredServiceEntity);
        }
      }
    } else {
      throw new ServiceException(
          "An  On Behalf Of Client for the referral could not be found. At least one On Behalf Of Client should exist");
    }
  }

  private Boolean notExistsReferralClientDeliveredServiceEntity(
      ReferralClientDeliveredServiceEntity[] referralClientDeliveredServices,
      String childClientId) {

    for (ReferralClientDeliveredServiceEntity referralClientDeliveredService : referralClientDeliveredServices) {
      if (referralClientDeliveredService.getPrimaryKey().getClientId().equals(childClientId)) {
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
