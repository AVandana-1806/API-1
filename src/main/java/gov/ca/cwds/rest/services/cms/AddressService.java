package gov.ca.cwds.rest.services.cms;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.cms.AddressDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.persistence.cms.Address;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.api.domain.ScreeningToReferral;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import gov.ca.cwds.rest.business.rules.ReferralAddressDroolsConfiguration;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.security.realm.PerryAccount;


/**
 * @author CWDS API Team
 *
 * <blockquote>
 * <pre>
 * 
 * BUSINESS RULE: R - 06398 Maintain Address Phonetic Entity
 * 
 *  When a Client or Placement Home street number or street name field is added, modified, 
 *  or deleted, make the appropriate update to the corresponding Address Phonetic row. 
 *  The LIS Interface needs to update the corresponding Address Phonetic row as well.  
 *  
 * </pre>
 * </blockquote>
 */
public class AddressService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Address, gov.ca.cwds.rest.api.domain.cms.Address> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

  private AddressDao addressDao;
  private SsaName3Dao ssaname3Dao;
  private UpperCaseTables upperCaseTables;
  private Validator validator;

  @Inject
  private DroolsService droolsService;

  /**
   * 
   * @param addressDao the address DAO
   * @param ssaname3Dao the stored procedure call
   * @param upperCaseTables the address upper case
   * @param validator the validator object used to validate validatable objects
   */
  @Inject
  public AddressService(AddressDao addressDao, SsaName3Dao ssaname3Dao,
      UpperCaseTables upperCaseTables, Validator validator) {
    this.addressDao = addressDao;
    this.ssaname3Dao = ssaname3Dao;
    this.upperCaseTables = upperCaseTables;
    this.validator = validator;
  }

  @Override
  public PostedAddress create(gov.ca.cwds.rest.api.domain.cms.Address request) {
    try {
      Address managed = new Address(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()), request,
          RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());

      managed = addressDao.create(managed);
      if (managed.getId() == null) {
        throw new ServiceException("Address ID cannot be null");
      }
      ssaname3Dao.addressSsaname3("I", managed);
      upperCaseTables.createAddressUc(managed);
      return new PostedAddress(managed, true);
    } catch (EntityExistsException e) {
      LOGGER.info("Address already exists : {}", request);
      throw new ServiceException(e);
    }
  }

  /**
   * @param scr - scr
   * @param messageBuilder - messageBuilder
   * @return the AddressFromScreening
   */
  public gov.ca.cwds.rest.api.domain.Address createAddressFromScreening(ScreeningToReferral scr,
      MessageBuilder messageBuilder) {
    gov.ca.cwds.rest.api.domain.Address address = scr.getAddress();
    if (address == null || address.getZip() == null
        || StringUtils.isBlank(address.getStreetAddress())) {
      String message = "Screening address is null or empty";
      messageBuilder.addMessageAndLog(message, LOGGER);
      return address;
    }

    try {
      gov.ca.cwds.rest.api.domain.cms.Address domainAddress =
          gov.ca.cwds.rest.api.domain.cms.Address.createWithDefaults(address);

      messageBuilder.addDomainValidationError(validator.validate(domainAddress));
      final PerryAccount perryAccount = RequestExecutionContext.instance().getUserIdentity();
      droolsService.performBusinessRules(createConfiguration(), domainAddress, perryAccount);
      PostedAddress postedAddress = this.create(domainAddress);
      address.setLegacyId(postedAddress.getExistingAddressId());
      address.setLegacySourceTable(LegacyTable.ADDRESS.getName());
    } catch (Exception e) {
      String message = "Error creating address: " + e.getMessage();
      messageBuilder.addMessageAndLog(message, e, LOGGER, ErrorType.DATA_ACCESS);
    }

    return address;
  }

  private DroolsConfiguration<gov.ca.cwds.rest.api.domain.cms.Address> createConfiguration() {
    return ReferralAddressDroolsConfiguration.DATA_PROCESSING_INSTANCE;
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.Address delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Address persistedAddress = addressDao.delete(primaryKey);
    if (persistedAddress != null) {
      ssaname3Dao.deleteSsaname3("ADR_PHTT", primaryKey, "A");
      upperCaseTables.deleteAddressUc(primaryKey);
      return new gov.ca.cwds.rest.api.domain.cms.Address(persistedAddress, true);
    }
    return null;
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.Address find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.Address persistedAddress = addressDao.find(primaryKey);
    if (persistedAddress != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Address(persistedAddress, true);
    }
    return null;
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.Address update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Address request) {

    try {
      Address managed =
          new Address(primaryKey, request, RequestExecutionContext.instance().getStaffId(),
              RequestExecutionContext.instance().getRequestStartTime());
      managed = addressDao.update(managed);
      ssaname3Dao.addressSsaname3("U", managed);
      upperCaseTables.updateAddressUc(managed);
      return new gov.ca.cwds.rest.api.domain.cms.Address(managed, true);
    } catch (EntityNotFoundException e) {
      LOGGER.info("Address not found : {}", request);
      throw new ServiceException(e);
    }
  }

  /**
   * @param droolsService - droolsService
   */
  public void setDroolsService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }

}
