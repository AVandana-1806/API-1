package gov.ca.cwds.rest.services.cms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientAddressDao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.ClientAddress;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.Participant;
import gov.ca.cwds.rest.api.domain.cms.Address;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedAddress;
import gov.ca.cwds.rest.api.domain.comparator.DateTimeComparator;
import gov.ca.cwds.rest.api.domain.comparator.DateTimeComparatorInterface;
import gov.ca.cwds.rest.business.rules.LACountyTrigger;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.messages.MessageBuilder;
import gov.ca.cwds.rest.services.LegacyDefaultValues;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.referentialintegrity.RIClientAddress;

/**
 * Business layer object to work on {@link ClientAddress}
 * 
 * @author CWDS API Team
 */
public class ClientAddressService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.ClientAddress, gov.ca.cwds.rest.api.domain.cms.ClientAddress> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientAddressService.class);

  private ClientAddressDao clientAddressDao;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private LACountyTrigger laCountyTrigger;
  private NonLACountyTriggers nonLaTriggers;
  private RIClientAddress riClientAddress;
  private Validator validator;
  private AddressService addressService;

  /**
   * Constructor
   * 
   * @param clientAddressDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.ClientAddress} objects.
   * @param laCountyTrigger The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.LACountyTrigger} objects
   * @param triggerTablesDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.rules.TriggerTablesDao} objects
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects
   * @param nonLaTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects.
   * @param riClientAddress - riClientAddress
   * @param validator - validator
   * @param addressService - addressService
   */
  @Inject
  public ClientAddressService(ClientAddressDao clientAddressDao, StaffPersonDao staffpersonDao,
      TriggerTablesDao triggerTablesDao, LACountyTrigger laCountyTrigger,
      NonLACountyTriggers nonLaTriggers, RIClientAddress riClientAddress, Validator validator,
      AddressService addressService) {
    this.clientAddressDao = clientAddressDao;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
    this.laCountyTrigger = laCountyTrigger;
    this.nonLaTriggers = nonLaTriggers;
    this.riClientAddress = riClientAddress;
    this.validator = validator;
    this.addressService = addressService;
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientAddress find(String primaryKey) {

    gov.ca.cwds.data.persistence.cms.ClientAddress persistedClientAddress =
        clientAddressDao.find(primaryKey);
    if (persistedClientAddress != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(persistedClientAddress, true);
    }
    return null;
  }

  /**
   * @param address - address
   * @param clientParticipant - clientParticipant
   * @return the address Found
   */
  public List<Response> findByAddressAndClient(gov.ca.cwds.rest.api.domain.Address address,
      Participant clientParticipant) {
    List<gov.ca.cwds.data.persistence.cms.ClientAddress> persistedClientAddresses = clientAddressDao
        .findByAddressAndClient(getAddressLegacyId(address), clientParticipant.getLegacyId());
    if (persistedClientAddresses != null && !persistedClientAddresses.isEmpty()) {
      ArrayList<Response> foundClientAddresses = new ArrayList<>();
      for (ClientAddress clientAddress : persistedClientAddresses) {
        foundClientAddresses
            .add(new gov.ca.cwds.rest.api.domain.cms.ClientAddress(clientAddress, true));

      }
      return foundClientAddresses;
    }
    return Collections.emptyList();
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientAddress delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.ClientAddress persistedClientAddress =
        clientAddressDao.delete(primaryKey);
    if (persistedClientAddress != null) {
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(persistedClientAddress, true);
    }
    return null;
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientAddress create(
      gov.ca.cwds.rest.api.domain.cms.ClientAddress request) {

    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress = request;
    try {
      ClientAddress managedClientAddress;
      managedClientAddress = new ClientAddress(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()),
          clientAddress, RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());

      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managedClientAddress.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createClientAddressCountyTrigger(managedClientAddress);
      } else {
        nonLaTriggers.createAndUpdateClientAddressCoutyOwnership(managedClientAddress);
      }
      managedClientAddress = clientAddressDao.create(managedClientAddress);
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(managedClientAddress, false);
    } catch (EntityExistsException e) {
      LOGGER.info("ClientAddress already exists : {}", clientAddress);
      throw new ServiceException(e);
    }
  }

  @Override
  public gov.ca.cwds.rest.api.domain.cms.ClientAddress update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.ClientAddress request) {
    gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress = request;

    try {
      ClientAddress managed = new ClientAddress(primaryKey, clientAddress,
          RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      // checking the staffPerson county code
      StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      if (staffperson != null
          && (triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
        laCountyTrigger.createClientAddressCountyTrigger(managed);
      }
      managed = clientAddressDao.update(managed);
      return new gov.ca.cwds.rest.api.domain.cms.ClientAddress(managed, true);
    } catch (EntityNotFoundException e) {
      LOGGER.info("ClientAddress not found : {}", clientAddress);
      throw new ServiceException(e);
    }
  }

  /**
   * @param clientParticipant - clientParticipant
   * @param referralId - referralId
   * @param clientId - clientId
   * @param messageBuilder - messageBuilder
   * @return the savedClientAddress
   */
  public Participant saveClientAddress(Participant clientParticipant, String referralId,
      String clientId, MessageBuilder messageBuilder) {

    String addressId = "";
    Set<gov.ca.cwds.rest.api.domain.Address> addresses;
    addresses = clientParticipant.getAddresses();

    if (addresses == null) {
      return null;
    }

    for (gov.ca.cwds.rest.api.domain.Address address : addresses) {

      Address domainAddress = Address.createWithDefaults(address);
      messageBuilder.addDomainValidationError(validator.validate(domainAddress));
      
      if (StringUtils.isBlank(getAddressLegacyId(address))) {
        /*
         * Since legacy address id is not provided, we consider this is a new address.
         */
        addressId = createNewAddress(address, domainAddress);
        
        /*
         * Create client address entry for given referral.
         */
        if (hasAddress(messageBuilder, addressId) && hasClient(clientId, messageBuilder)) {
          Short addressType = address.getType() != null ? address.getType().shortValue()
              : LegacyDefaultValues.DEFAULT_ADDRESS_TYPE;
          gov.ca.cwds.rest.api.domain.cms.ClientAddress clientAddress =
              new gov.ca.cwds.rest.api.domain.cms.ClientAddress(addressType, "", "", "", addressId,
                  clientId, "", referralId);

          messageBuilder.addDomainValidationError(validator.validate(clientAddress));
          create(clientAddress);
          messageBuilder.addDomainValidationError(validator.validate(clientAddress));
          address.setLegacySourceTable(LegacyTable.ADDRESS.getName());
          address.setLegacyId(addressId);
        }        
      } else {
        //
        // At this time, we don't update addresses (September 17, 2018)
        //
        //addressId = updateExistingAddress(messageBuilder, addressId, address, domainAddress);              
      }      
    }

    return clientParticipant;
  }

  private boolean hasClient(String clientId, MessageBuilder messageBuilder) {
    if (StringUtils.isBlank(clientId)) {
      String message = " CLIENT/IDENTIFIER is required for CLIENT_ADDRESS ";
      ServiceException se = new ServiceException(message);
      messageBuilder.addMessageAndLog(message, se, LOGGER);
      return false;
    }
    return true;
  }

  private boolean hasAddress(MessageBuilder messageBuilder, String addressId) {
    if (StringUtils.isBlank(addressId)) {
      String message = " ADDRESS/IDENTIFIER is required for CLIENT_ADDRESS table ";
      ServiceException se = new ServiceException(message);
      messageBuilder.addMessageAndLog(message, se, LOGGER);
      return false;
    }
    return true;
  }

  private String updateExistingAddress(MessageBuilder messageBuilder, String addressId,
      gov.ca.cwds.rest.api.domain.Address address, Address domainAddress) {
    Address foundAddress = this.addressService.find(getAddressLegacyId(address));
    if (foundAddress != null) {
      addressId = updateAddress(messageBuilder, addressId, address, domainAddress, foundAddress);
    } else {
      String message = " Legacy Id on Address does not correspond to an existing CMS/CWS Address ";
      ServiceException se = new ServiceException(message);
      messageBuilder.addMessageAndLog(message, se, LOGGER);
    }
    return addressId;
  }

  private String updateAddress(MessageBuilder messageBuilder, String addressId,
      gov.ca.cwds.rest.api.domain.Address address, Address domainAddress, Address foundAddress) {
    boolean okToUpdate = okToUpdateAddress(address, foundAddress);
    if (okToUpdate) {
      addressId = updateAddress(messageBuilder, address, domainAddress);
    } else {
      String message =
          String.format("Unable to Update %s %s Address. Address was previously modified",
              address.getStreetAddress(), address.getCity());
      messageBuilder.addMessageAndLog(message, LOGGER);
    }
    return addressId;
  }

  private boolean okToUpdateAddress(gov.ca.cwds.rest.api.domain.Address address,
      Address foundAddress) {
    DateTimeComparatorInterface comparator = new DateTimeComparator();
    return comparator.compare(address.getLegacyDescriptor().getLastUpdated(),
        foundAddress.getLastUpdatedTime());
  }

  private String createNewAddress(gov.ca.cwds.rest.api.domain.Address address,
      Address domainAddress) {
    String addressId;
    PostedAddress postedAddress = this.addressService.create(domainAddress);
    addressId = postedAddress.getExistingAddressId();
    address.getLegacyDescriptor().setLastUpdated(postedAddress.getLastUpdatedTime());
    return addressId;
  }

  private String updateAddress(MessageBuilder messageBuilder,
      gov.ca.cwds.rest.api.domain.Address address, Address domainAddress) {
    String addressId = getAddressLegacyId(address);
    Address savedAddress = this.addressService.update(addressId, domainAddress);
    if (savedAddress != null) {
      address.getLegacyDescriptor().setLastUpdated(savedAddress.getLastUpdatedTime());
    } else {
      String message = "Unable to save Client Address";
      messageBuilder.addMessageAndLog(message, LOGGER);
    }
    return addressId;
  }

  public RIClientAddress getRiClientAddress() {
    return riClientAddress;
  }

  private String getAddressLegacyId(gov.ca.cwds.rest.api.domain.Address address) {
    String addressLegacyId = address.getLegacyId();

    if (StringUtils.isBlank(addressLegacyId)) {
      LegacyDescriptor addressLegacyDescriptor = address.getLegacyDescriptor();
      addressLegacyId = addressLegacyDescriptor != null ? addressLegacyDescriptor.getId() : null;
    }
    return addressLegacyId;
  }

}
