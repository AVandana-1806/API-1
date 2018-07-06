package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.SsaName3Dao;
import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.rules.TriggerTablesDao;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;
import gov.ca.cwds.rest.api.domain.cms.PostedClient;
import gov.ca.cwds.rest.business.rules.ExternalInterfaceTables;
import gov.ca.cwds.rest.business.rules.NonLACountyTriggers;
import gov.ca.cwds.rest.business.rules.R04966NamesMustHaveAtLeastOneAlphaChar;
import gov.ca.cwds.rest.business.rules.UpperCaseTables;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Business layer object to work on {@link Client}.
 * 
 * @author CWDS API Team
 */
public class ClientService implements
    TypedCrudsService<String, gov.ca.cwds.rest.api.domain.cms.Client, gov.ca.cwds.rest.api.domain.cms.Client> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

  private ClientDao clientDao;
  private StaffPersonDao staffpersonDao;
  private TriggerTablesDao triggerTablesDao;
  private NonLACountyTriggers nonLaCountyTriggers;
  private SsaName3Dao ssaname3Dao;
  private UpperCaseTables upperCaseTables;
  private ExternalInterfaceTables externalInterfaceTables;

  /**
   * Constructor
   * 
   * @param clientDao The {@link Dao} handling {@link gov.ca.cwds.data.persistence.cms.Client}
   *        objects.
   * @param staffpersonDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.StaffPerson} objects
   * @param triggerTablesDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.rules.TriggerTablesDao} objects
   * @param nonLaCountyTriggers The {@link Dao} handling
   *        {@link gov.ca.cwds.rest.business.rules.NonLACountyTriggers} objects
   * @param ssaname3Dao dao the stored procedure call
   * @param upperCaseTables the downstream tables
   * @param externalInterfaceTables the external interface table
   */
  @Inject
  public ClientService(ClientDao clientDao, StaffPersonDao staffpersonDao,
      TriggerTablesDao triggerTablesDao, NonLACountyTriggers nonLaCountyTriggers,
      SsaName3Dao ssaname3Dao, UpperCaseTables upperCaseTables,
      ExternalInterfaceTables externalInterfaceTables) {
    this.clientDao = clientDao;
    this.staffpersonDao = staffpersonDao;
    this.triggerTablesDao = triggerTablesDao;
    this.nonLaCountyTriggers = nonLaCountyTriggers;
    this.ssaname3Dao = ssaname3Dao;
    this.upperCaseTables = upperCaseTables;
    this.externalInterfaceTables = externalInterfaceTables;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Client find(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Client persistedClient = clientDao.find(primaryKey);
    if (persistedClient != null) {
      return new gov.ca.cwds.rest.api.domain.cms.Client(persistedClient, true);
    }
    return null;
  }

  /**
   * This method is the representation of postedCmsReferral to find the existing client
   * 
   * @param primaryKey client key
   * @return {@link PostedClient} if found else null
   */
  public PostedClient findInboundId(Serializable primaryKey) {
    gov.ca.cwds.data.persistence.cms.Client persistedClient = clientDao.find(primaryKey);
    if (persistedClient != null) {
      return new PostedClient(persistedClient, true);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Client delete(String primaryKey) {
    gov.ca.cwds.data.persistence.cms.Client persistedClient = clientDao.delete(primaryKey);
    if (persistedClient != null) {
      ssaname3Dao.deleteSsaname3(LegacyTable.CLIENT_PHONETIC.getName(), primaryKey, "C");
      upperCaseTables.deleteClientUc(primaryKey);
      externalInterfaceTables.createExtInterForDelete(primaryKey, LegacyTable.CLIENT.getName());
      return new gov.ca.cwds.rest.api.domain.cms.Client(persistedClient, true);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedClient create(gov.ca.cwds.rest.api.domain.cms.Client request) {
    gov.ca.cwds.rest.api.domain.cms.Client client = request;

    try {
      Client managed = new Client(
          CmsKeyIdGenerator.getNextValue(RequestExecutionContext.instance().getStaffId()), client,
          RequestExecutionContext.instance().getStaffId(),
          RequestExecutionContext.instance().getRequestStartTime());
      validateByRuleR04966(managed);
      managed = clientDao.create(managed);

      // checking the staffPerson county code
      final StaffPerson staffperson = staffpersonDao.find(managed.getLastUpdatedId());
      createDownStreamEntity(managed, staffperson);

      return new PostedClient(managed, false);
    } catch (EntityExistsException e) {
      LOGGER.info("Client already exists : {}", client);
      throw new ServiceException(e);
    }

  }

  private void createDownStreamEntity(Client managed, StaffPerson staffperson) {
    if (staffperson != null
        && !(triggerTablesDao.getLaCountySpecificCode().equals(staffperson.getCountyCode()))) {
      nonLaCountyTriggers.createClientCountyTrigger(managed);
    }
    ssaname3Dao.clientSsaname3("I", managed);
    upperCaseTables.createClientUc(managed);
    externalInterfaceTables.createExtInterClient(managed, "N");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public gov.ca.cwds.rest.api.domain.cms.Client update(String primaryKey,
      gov.ca.cwds.rest.api.domain.cms.Client request) {
    gov.ca.cwds.rest.api.domain.cms.Client client = request;

    gov.ca.cwds.rest.api.domain.cms.Client savedEntity;
    try {
      // DRS: HOT-2176: isolate "possible non-threadsafe access to session".
      final RequestExecutionContext ctx = RequestExecutionContext.instance();
      final Client existingClient = clientDao.find(primaryKey);
      Client managed = existingClient;
      managed.setLastUpdatedId(ctx.getStaffId());
      managed.setLastUpdatedTime(ctx.getRequestStartTime());

      // Client managed =
      // new Client(primaryKey, client, RequestExecutionContext.instance().getStaffId(),
      // RequestExecutionContext.instance().getRequestStartTime());

      validateByRuleR04966(existingClient);

      // managed.setClientAddress(existingClient.getClientAddress());
      managed = clientDao.update(managed);
      savedEntity = new gov.ca.cwds.rest.api.domain.cms.Client(managed, true);
      ssaname3Dao.clientSsaname3("U", managed);
      upperCaseTables.updateClientUc(managed);
      externalInterfaceTables.createExtInterClient(managed, "C");
    } catch (EntityNotFoundException e) {
      LOGGER.info("client not found : {}", client);
      throw new ServiceException(e);
    }

    return savedEntity;
  }

  private void validateByRuleR04966(Client managed) {
    if (!new R04966NamesMustHaveAtLeastOneAlphaChar(managed).isValid()) {
      throw new ServiceException("Client must have at least one alpha character "
          + "in at least one of the name fields (first, middle, last)");
    }
  }

}
