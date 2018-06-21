package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.ns.AddressDao;
import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.persistence.xa.XAUnitOfWork;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.AddressUtils;
import gov.ca.cwds.rest.api.domain.PostedAddress;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.resources.AddressResource;

/**
 * Business layer object to work on Postgres (NS) {@link Address}.
 * 
 * @author CWDS API Team
 */
public class AddressService implements CrudsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

  private AddressDao addressDao;
  private AddressesDao nsAddressDao;
  private gov.ca.cwds.data.cms.AddressDao cmsAddressDao;

  /**
   * Constructor
   * 
   * @param addressDao {@link Dao} handling {@link gov.ca.cwds.data.persistence.ns.Address} objects.
   * @param xaNsAddressDao {@link Dao} for XA (two-phase commit) transactions for PostgreSQL
   * @param xaCmsAddressDao {@link Dao} for XA (two-phase commit) transactions for CWS/CMS DB2
   */
  @Inject
  public AddressService(AddressDao addressDao, AddressesDao xaNsAddressDao,
      gov.ca.cwds.data.cms.AddressDao xaCmsAddressDao) {
    this.addressDao = addressDao;
    this.nsAddressDao = xaNsAddressDao;
    this.cmsAddressDao = xaCmsAddressDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Address find(Serializable primaryKey) {
    assert primaryKey instanceof Long;

    gov.ca.cwds.data.persistence.ns.Address persistedAddress = addressDao.find(primaryKey);
    if (persistedAddress != null) {
      return new Address(persistedAddress);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedAddress create(Request request) {
    assert request instanceof Address;

    final Address address = (Address) request;
    gov.ca.cwds.data.persistence.ns.Address managed =
        new gov.ca.cwds.data.persistence.ns.Address(address, null, null);

    managed = addressDao.create(managed);
    return new PostedAddress(managed);
  }

  /**
   * {@inheritDoc}
   * 
   * <p>
   * Update NS and CMS with XA transaction. See INT-1592.
   * </p>
   * 
   * <p>
   * Note that the only transaction annotation is on {@link AddressResource#update(long, Address)},
   * and transactions are managed auto-magically.
   * </p>
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @XAUnitOfWork
  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    assert request instanceof Address;

    final String strNsId = String.valueOf((long) primaryKey);
    final Address reqAddr = (Address) request;
    final RequestExecutionContext ctx = RequestExecutionContext.instance();
    final String staffId = ctx.getStaffId();

    // ==================
    // PostgreSQL:
    // ==================

    LOGGER.info("XA for Postgres");
    // Proof of concept only. Don't bother parsing raw street addresses.
    final gov.ca.cwds.data.persistence.ns.Addresses nsAddr = nsAddressDao.find(strNsId);
    nsAddr.setZip(reqAddr.getZip());
    nsAddr.setCity(reqAddr.getCity());
    nsAddr.setLegacyId(reqAddr.getLegacyId());

    if (StringUtils.isNotEmpty(reqAddr.getLegacySourceTable())) {
      nsAddr.setLegacySourceTable(reqAddr.getLegacySourceTable().trim().toUpperCase());
    } else {
      nsAddr.setLegacySourceTable("ADDRS_T");
    }

    final gov.ca.cwds.data.persistence.ns.Addresses ret = nsAddressDao.update(nsAddr);

    // ==================
    // DB2:
    // ==================

    LOGGER.info("XA for DB2");
    final gov.ca.cwds.data.persistence.cms.Address cmsAddr =
        cmsAddressDao.find(nsAddr.getLegacyId());
    cmsAddr.setAddressDescription(reqAddr.getStreetAddress());
    cmsAddr.setCity(reqAddr.getCity());
    cmsAddr.setZip(AddressUtils.defaultIfBlank(reqAddr.getZip()));
    cmsAddr.setLastUpdatedId(staffId);
    cmsAddr.setLastUpdatedTime(ctx.getRequestStartTime());
    cmsAddressDao.update(cmsAddr);

    ret.setLegacyId(reqAddr.getLegacyId());
    ret.setLegacySourceTable(reqAddr.getLegacySourceTable());

    // Return results.
    final PostedAddress result = new PostedAddress(ret);
    result.setLegacyDescriptor(reqAddr.getLegacyDescriptor());
    result.getLegacyDescriptor().setId(reqAddr.getLegacyId());
    return result;
  }

}
