package gov.ca.cwds.rest.services.screening.participant;

import org.apache.commons.lang3.NotImplementedException;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link AddressIntakeApi}
 *
 * @author CWDS API team
 */
public class AddressIntakeApiService
    implements TypedCrudsService<String, AddressIntakeApi, AddressIntakeApi> {

  private AddressesDao addressesDao;
  private LegacyDescriptorDao legacyDescriptorDao;

  /**
   * Constructor
   *
   * @param addressesDao {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Address}
   *        objects.
   * @param legacyDescriptorDao - {@link Dao} for {@link LegacyDescriptorDao}
   */
  @Inject
  public AddressIntakeApiService(AddressesDao addressesDao,
      LegacyDescriptorDao legacyDescriptorDao) {
    this.addressesDao = addressesDao;
    this.legacyDescriptorDao = legacyDescriptorDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.TypedCrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public AddressIntakeApi create(AddressIntakeApi request) {
    AddressIntakeApi address = request;
    gov.ca.cwds.data.persistence.ns.Addresses managed =
        new gov.ca.cwds.data.persistence.ns.Addresses(address);
    managed = addressesDao.create(managed);
    AddressIntakeApi addressPosted = new AddressIntakeApi(managed);
    addressPosted
        .setLegacyDescriptor(saveLegacyDescriptor(address.getLegacyDescriptor(), managed.getId()));
    return addressPosted;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.TypedCrudsService#delete(java.io.Serializable)
   */
  @Override
  public AddressIntakeApi delete(String primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.TypedCrudsService#find(java.io.Serializable)
   */
  @Override
  public AddressIntakeApi find(String primaryKey) {
    gov.ca.cwds.data.persistence.ns.Addresses persistedAddress = addressesDao.find(primaryKey);
    if (persistedAddress == null) {
      return null;
    }
    AddressIntakeApi addressIntakeApi = new AddressIntakeApi(persistedAddress);
    // Get it's legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity =
        legacyDescriptorDao.findAddressLegacyDescriptor(persistedAddress.getId());
    if (legacyDescriptorEntity != null) {
      addressIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
    }
    return addressIntakeApi;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.TypedCrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public AddressIntakeApi update(String primaryKey, AddressIntakeApi request) {
    throw new NotImplementedException("Update is not implemented");
  }

  LegacyDescriptor saveLegacyDescriptor(LegacyDescriptor legacyDescriptor, String describableId) {
    if (legacyDescriptor == null || describableId == null) {
      return null;
    }
    // Save legacy descriptor entity
    LegacyDescriptorEntity legacyDescriptorEntity = new LegacyDescriptorEntity(legacyDescriptor,
        LegacyDescriptorEntity.DESCRIBABLE_TYPE_ADDRESS, Long.valueOf(describableId));
    legacyDescriptorEntity = legacyDescriptorDao.create(legacyDescriptorEntity);
    return new LegacyDescriptor(legacyDescriptorEntity);
  }
}
