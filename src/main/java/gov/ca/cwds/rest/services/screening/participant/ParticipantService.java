package gov.ca.cwds.rest.services.screening.participant;

import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.services.relationship.RelationshipFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.ns.AddressesDao;
import gov.ca.cwds.data.ns.AllegationDao;
import gov.ca.cwds.data.ns.CsecDao;
import gov.ca.cwds.data.ns.LegacyDescriptorDao;
import gov.ca.cwds.data.ns.ParticipantAddressesDao;
import gov.ca.cwds.data.ns.ParticipantDao;
import gov.ca.cwds.data.ns.ParticipantPhoneNumbersDao;
import gov.ca.cwds.data.ns.PhoneNumbersDao;
import gov.ca.cwds.data.ns.SafelySurrenderedBabiesDao;
import gov.ca.cwds.data.persistence.ns.Addresses;
import gov.ca.cwds.data.persistence.ns.AllegationEntity;
import gov.ca.cwds.data.persistence.ns.CsecEntity;
import gov.ca.cwds.data.persistence.ns.LegacyDescriptorEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantAddresses;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.data.persistence.ns.ParticipantPhoneNumbers;
import gov.ca.cwds.data.persistence.ns.PhoneNumbers;
import gov.ca.cwds.data.persistence.ns.SafelySurrenderedBabiesEntity;
import gov.ca.cwds.rest.api.domain.AddressIntakeApi;
import gov.ca.cwds.rest.api.domain.Csec;
import gov.ca.cwds.rest.api.domain.LegacyDescriptor;
import gov.ca.cwds.rest.api.domain.ParticipantIntakeApi;
import gov.ca.cwds.rest.api.domain.PhoneNumber;
import gov.ca.cwds.rest.api.domain.SafelySurrenderedBabiesIntakeApi;
import gov.ca.cwds.rest.resources.parameter.ParticipantResourceParameters;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.services.TypedCrudsService;
import gov.ca.cwds.rest.services.mapper.CsecMapper;
import gov.ca.cwds.rest.services.mapper.SafelySurrenderedBabiesMapper;

/**
 * Business layer object to work on {@link ParticipantIntakeApi}
 *
 * @author CWDS API Team
 */
public class ParticipantService implements
    TypedCrudsService<ParticipantResourceParameters, ParticipantIntakeApi, ParticipantIntakeApi> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantService.class);

  @Inject
  private ParticipantDao participantDao;

  @Inject
  private AllegationDao allegationDao;

  @Inject
  private LegacyDescriptorDao legacyDescriptorDao;

  @Inject
  private AddressesDao addressesDao;

  @Inject
  private ParticipantAddressesDao participantAddressesDao;

  @Inject
  private AddressService addressService;

  @Inject
  private PhoneNumbersDao phoneNumbersDao;

  @Inject
  private ParticipantPhoneNumbersDao participantPhoneNumbersDao;

  @Inject
  private CsecDao csecDao;

  @Inject
  private CsecMapper csecMapper;

  @Inject
  private SafelySurrenderedBabiesDao safelySurrenderedBabiesDao;

  @Inject
  private SafelySurrenderedBabiesMapper safelySurrenderedBabiesMapper;

  @Inject
  private ParticipantTransformer participantTransformer;

  @Inject
  private RelationshipFacade relationshipFacade;

  @Inject
  private ClientDao clientDao;

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#find(Serializable)
   */
  @Override
  public ParticipantIntakeApi find(ParticipantResourceParameters resourceParameters) {
    if (resourceParameters == null) {
      throw new ServiceException("NULL argument for FIND participant");
    }

    String screeningId = resourceParameters.getScreeningId();
    String participantId = resourceParameters.getParticipantId();
    if (!ObjectUtils.allNotNull(screeningId, participantId)) {
      throw new ServiceException("NULL screening/participant id for FIND participant");
    }

    ParticipantEntity participantEntity =
        participantDao.findByScreeningIdAndParticipantId(screeningId, participantId);
    if (participantEntity == null) {
      return null;
    }
    ParticipantIntakeApi participantIntakeApi = new ParticipantIntakeApi(participantEntity);

    List<CsecEntity> csecEntities = csecDao.findByParticipantId(participantId);
    participantIntakeApi.setCsecs(csecMapper.toDomain(csecEntities));
    participantIntakeApi.setSafelySurenderedBabies(
        safelySurrenderedBabiesMapper.map(participantEntity.getSafelySurrenderedBabies()));

    // Get it's legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity =
        legacyDescriptorDao.findParticipantLegacyDescriptor(participantEntity.getId());
    if (legacyDescriptorEntity != null) {
      participantIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
    }

    // Get it's Addresses and PhoneNumbers
    for (Addresses addresses : addressesDao.findByParticipant(participantEntity.getId())) {
      AddressIntakeApi addressIntakeApi = new AddressIntakeApi(addresses);
      participantIntakeApi.getAddresses().add(addressIntakeApi);

      // Get it's legacy descriptor
      legacyDescriptorEntity = legacyDescriptorDao.findAddressLegacyDescriptor(addresses.getId());
      if (legacyDescriptorEntity != null) {
        addressIntakeApi.setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntity));
      }

    }
    for (PhoneNumbers phoneNumbers : phoneNumbersDao.findByParticipant(participantEntity.getId())) {
      participantIntakeApi.getPhoneNumbers().add(new PhoneNumber(phoneNumbers));
    }

    return participantIntakeApi;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#delete(Serializable)
   */
  @Override
  public ParticipantIntakeApi delete(ParticipantResourceParameters resourceParameters) {
    if (resourceParameters == null) {
      throw new ServiceException("NULL argument for DELETE participant");
    }

    String screeningId = resourceParameters.getScreeningId();
    String participantId = resourceParameters.getParticipantId();
    if (!ObjectUtils.allNotNull(screeningId, participantId)) {
      throw new ServiceException("NULL screening/participant id for DELETE participant");
    }

    ParticipantEntity participantEntity =
        participantDao.findByRelatedScreeningAndParticipantId(screeningId, participantId);
    if (participantEntity == null) {
      return null;
    }

    // Delete allegations for this participant where he is a Victim
    allegationDao.deleteByIdList(allegationDao.findByVictimId(participantId).stream()
        .map(AllegationEntity::getId).collect(Collectors.toList()));

    // Update allegations -  clear reference to this participant where he is a Perpetrator
    allegationDao.findByPerpetratorId(participantId).stream()
        .forEach(allegation -> {
          allegation.setPerpetratorId(null);
          allegationDao.update(allegation);
        });

    // Delete Participant Addresses & PhoneNumbers
    participantAddressesDao.findByParticipantId(participantId).forEach(
        participantAddresses -> participantAddressesDao.delete(participantAddresses.getId()));

    participantPhoneNumbersDao.findByParticipantId(participantId)
        .forEach(participantPhoneNumbers -> participantPhoneNumbersDao
            .delete(participantPhoneNumbers.getId()));

    // Delete legacy descriptor
    LegacyDescriptorEntity legacyDescriptorEntity =
        legacyDescriptorDao.findParticipantLegacyDescriptor(participantId);
    if (legacyDescriptorEntity != null) {
      legacyDescriptorDao.delete(legacyDescriptorEntity.getId());
    }

    relationshipFacade.deleteRelationshipsAndRelatedParticipants(participantId, screeningId);
    participantDao.delete(participantId);

    return new ParticipantIntakeApi(participantEntity);
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ParticipantIntakeApi create(ParticipantIntakeApi participant) {
    if (participant == null) {
      throw new ServiceException("NULL argument for CREATE participant");
    }

    // validate if participant already exist but didn't attach
    ParticipantIntakeApi participantIntakeApi = getExistingParticipant(participant);
    if (participantIntakeApi != null) {
      return participantIntakeApi;
    }

    return persistParticipantObjectInNS(
        participantTransformer.prepareParticipantObject(participant));
  }

  private ParticipantIntakeApi getExistingParticipant(ParticipantIntakeApi participant) {
    ParticipantEntity existing = getExistingParticipant(
        participant.getScreeningId(),
        participant.getLegacyDescriptor());
    if (existing != null) {
      existing = enrichExistingParticipantWithScreeningId(
          participant.getScreeningId(),
          existing);
      participantDao.update(existing);
      return new ParticipantIntakeApi(existing);
    }
    return null;
  }

  public ParticipantIntakeApi persistParticipantObjectInNS(ParticipantIntakeApi participant) {

    setLegacyIdAndTable(participant);
    enrichEstimatedDob(participant);

    ParticipantEntity participantEntityManaged =
        participantDao.create(new ParticipantEntity(participant));

    createOrUpdateCsecs(participant, participantEntityManaged);
    createOrUpdateSafelySurrenderedBabies(participant, participantEntityManaged);

    // Create Participant Addresses & PhoneNumbers
    List<AddressIntakeApi> addressIntakeApiList =
        createParticipantAddresses(participant.getAddresses(), participantEntityManaged);

    List<PhoneNumber> phoneList =
        createParticipantPhoneNumbers(participant.getPhoneNumbers(), participantEntityManaged);

    ParticipantIntakeApi participantIntakeApiPosted =
        new ParticipantIntakeApi(participantEntityManaged);
    participantIntakeApiPosted.addAddresses(addressIntakeApiList);
    participantIntakeApiPosted.addPhoneNumbers(phoneList);
    participantIntakeApiPosted.setCsecs(participant.getCsecs());

    SafelySurrenderedBabiesIntakeApi createdSsb =
        safelySurrenderedBabiesMapper.map(participantEntityManaged.getSafelySurrenderedBabies());
    participantIntakeApiPosted.setSafelySurenderedBabies(createdSsb);

    // Save legacy descriptor entity
    if (participant.getLegacyDescriptor() != null) {
      LegacyDescriptorEntity legacyDescriptorEntityManaged =
          legacyDescriptorDao.create(new LegacyDescriptorEntity(participant.getLegacyDescriptor(),
              LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT,
              Long.valueOf(participantEntityManaged.getId())));
      participantIntakeApiPosted
          .setLegacyDescriptor(new LegacyDescriptor(legacyDescriptorEntityManaged));
    }
    return participantIntakeApiPosted;
  }

  private void enrichEstimatedDob(ParticipantIntakeApi participant) {
    participant.setEstimatedDob(getDobCode(participant.getLegacyDescriptor()));
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.rest.services.CrudsService#update(Serializable, gov.ca.cwds.rest.api.Request)
   */
  @Override
  public ParticipantIntakeApi update(ParticipantResourceParameters resourceParameters,
      ParticipantIntakeApi participant) {
    if (!ObjectUtils.allNotNull(resourceParameters, participant)) {
      throw new ServiceException("NULL argument for UPDATE participant");
    }

    setLegacyIdAndTable(participant);
    String screeningId = resourceParameters.getScreeningId();
    String participantId = resourceParameters.getParticipantId();
    if (!ObjectUtils.allNotNull(screeningId, participantId)) {
      throw new ServiceException("NULL screening/participant id for UPDATE participant");
    }

    participant.setScreeningId(resourceParameters.getScreeningId());
    participant.setId(participantId);

    ParticipantEntity participantEntityManaged = participantDao
        .findByScreeningIdAndParticipantId(resourceParameters.getScreeningId(), participantId);

    if (participantEntityManaged == null) {
      LOGGER.info("participant not found : {}", participant);
      throw new ServiceException(new EntityNotFoundException(
          "Entity ParticipantEntity with id = [" + participantId + "] was not found."));
    }
    participantEntityManaged =
        participantDao.update(participantEntityManaged.updateFrom(participant));

    createOrUpdateCsecs(participant, participantEntityManaged);
    createOrUpdateSafelySurrenderedBabies(participant, participantEntityManaged);

    // Update Participant Addresses & PhoneNumbers
    List<AddressIntakeApi> addressIntakeApiList =
        updateParticipantAddresses(participant.getAddresses(), participantEntityManaged);

    List<PhoneNumber> phoneList =
        updateParticipantPhoneNumbers(participant.getPhoneNumbers(), participantEntityManaged);

    LegacyDescriptorEntity foundDescriptor =
        legacyDescriptorDao.findParticipantLegacyDescriptor(participantId);
    LegacyDescriptor discriptor =
        foundDescriptor == null ? new LegacyDescriptor() : new LegacyDescriptor(foundDescriptor);

    ParticipantIntakeApi participantIntakeApiPosted =
        new ParticipantIntakeApi(participantEntityManaged);
    participantIntakeApiPosted.setLegacyDescriptor(discriptor);
    participantIntakeApiPosted.addAddresses(addressIntakeApiList);
    participantIntakeApiPosted.addPhoneNumbers(phoneList);
    participantIntakeApiPosted.setCsecs(participant.getCsecs());
    participantIntakeApiPosted.setSafelySurenderedBabies(
        safelySurrenderedBabiesMapper.map(participantEntityManaged.getSafelySurrenderedBabies()));
    return participantIntakeApiPosted;
  }

  private ParticipantEntity getExistingParticipant(String screeningId,
      LegacyDescriptor legacyDescriptor) {
    String legacyId = "";
    if (legacyDescriptor != null && StringUtils.isNoneEmpty(legacyDescriptor.getId())) {
      legacyId = legacyDescriptor.getId();
    }
    return participantDao
        .findByRelatedScreeningIdAndLegacyId(screeningId,
            legacyId);
  }


  private ParticipantEntity enrichExistingParticipantWithScreeningId(String screeningId,
      ParticipantEntity existingParticipant) {
    existingParticipant.setScreeningId(screeningId);
    return existingParticipant;
  }

  private void setLegacyIdAndTable(ParticipantIntakeApi participant) {
    if (participant.getLegacyId() == null && participant.getLegacyDescriptor() != null) {
      participant.setLegacyId(participant.getLegacyDescriptor().getId());
      participant.setLegacySourceTable(participant.getLegacyDescriptor().getTableName());
    }
  }

  private List<AddressIntakeApi> createParticipantAddresses(
      List<AddressIntakeApi> addressIntakeApiList, ParticipantEntity participantEntityManaged) {
    List<AddressIntakeApi> addressIntakeApiListPosted = new ArrayList<>();
    addressIntakeApiList.stream().map(this::createAddresses).forEach(addressesWrapper -> {
      addressIntakeApiListPosted.add(addressesWrapper.addressIntakeApi);
      participantAddressesDao
          .create(new ParticipantAddresses(participantEntityManaged, addressesWrapper.addresses));
    });
    return addressIntakeApiListPosted;
  }

  private List<AddressIntakeApi> updateParticipantAddresses(
      List<AddressIntakeApi> addressIntakeApiList, ParticipantEntity participantEntityManaged) {
    List<AddressIntakeApi> addressIntakeApiListPosted = new ArrayList<>();

    Map<String, ParticipantAddresses> participantAddressesOldMap = new HashMap<>();
    participantAddressesDao.findByParticipantId(participantEntityManaged.getId())
        .forEach(participantAddresses -> participantAddressesOldMap
            .put(participantAddresses.getAddress().getId(), participantAddresses));

    addressIntakeApiList.stream().map(this::createAddresses).forEach(addressesWrapper -> {
      addressIntakeApiListPosted.add(addressesWrapper.addressIntakeApi);

      // See if we had this ParticipantAddresses entity before. Otherwise create
      Addresses addressesEntityManaged = addressesWrapper.addresses;
      ParticipantAddresses participantAddresses =
          participantAddressesOldMap.get(addressesEntityManaged.getId());
      if (participantAddresses != null) {
        // Remove from the Old map
        participantAddressesOldMap.remove(addressesEntityManaged.getId());
        participantAddressesDao.update(participantAddresses);
      } else {
        participantAddressesDao
            .create(new ParticipantAddresses(participantEntityManaged, addressesEntityManaged));
      }
    });

    // Delete old ones that are not in the new.
    participantAddressesOldMap.values().forEach(
        participantAddresses -> participantAddressesDao.delete(participantAddresses.getId()));

    return addressIntakeApiListPosted;
  }

  private void createOrUpdateCsecs(ParticipantIntakeApi participantIntakeApi,
      ParticipantEntity participantEntityManaged) {

    String participantId = participantEntityManaged.getId();
    List<CsecEntity> managedCsecEntities = csecDao.findByParticipantId(participantId);

    List<CsecEntity> toDeleteList = new ArrayList<>();
    List<CsecEntity> toUpdateList = new ArrayList<>();
    List<CsecEntity> toCreateList = new ArrayList<>();
    List<Csec> csecs = new ArrayList<>(participantIntakeApi.getCsecs());

    for (CsecEntity managedCsecEntity : managedCsecEntities) {
      if (csecs.stream()
          .noneMatch(c -> String.valueOf(managedCsecEntity.getId()).equals(c.getId()))) {
        toDeleteList.add(managedCsecEntity);
      }
      csecs.stream().filter(c -> String.valueOf(managedCsecEntity.getId()).equals(c.getId()))
          .findFirst().ifPresent(c -> toUpdateList
          .add(csecMapper.update(managedCsecEntity, csecs.remove(csecs.indexOf(c)))));
    }

    toCreateList.addAll(csecMapper
        .toPersistence(csecs.stream().filter(c -> c.getId() == null).collect(Collectors.toList())));

    for (CsecEntity csecEntity : toDeleteList) {
      csecDao.delete(csecEntity.getId());
    }

    List<CsecEntity> csecEntities = new ArrayList<>();
    for (CsecEntity csecEntity : toUpdateList) {
      csecEntity.setParticipantId(participantId);
      // "update" is not working here due to XA transaction implementation
      csecEntities.add(csecDao.merge(csecEntity));
    }
    for (CsecEntity csecEntity : toCreateList) {
      csecEntity.setParticipantId(participantId);
      csecEntities.add(csecDao.create(csecEntity));
    }

    participantIntakeApi.setCsecs(csecMapper.toDomain(csecEntities));
  }

  private void createOrUpdateSafelySurrenderedBabies(ParticipantIntakeApi participantIntakeApi,
      ParticipantEntity participantEntityManaged) {

    SafelySurrenderedBabiesIntakeApi safelySurenderedBabies =
        participantIntakeApi.getSafelySurenderedBabies();

    if (safelySurenderedBabies != null) {
      String participantId = participantEntityManaged.getId();

      SafelySurrenderedBabiesEntity existingSafelySurrenderedBabiesEntity =
          safelySurrenderedBabiesDao.find(participantId);
      SafelySurrenderedBabiesEntity createdOrUpdatedSafelySurrenderedBabiesEntity = null;

      if (existingSafelySurrenderedBabiesEntity == null) {
        SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity =
            safelySurrenderedBabiesMapper.map(safelySurenderedBabies);
        safelySurrenderedBabiesEntity.setParticipantId(participantId);
        createdOrUpdatedSafelySurrenderedBabiesEntity =
            safelySurrenderedBabiesDao.create(safelySurrenderedBabiesEntity);
        safelySurenderedBabies.setParticipantId(
            String.valueOf(createdOrUpdatedSafelySurrenderedBabiesEntity.getParticipantId()));
      } else {
        SafelySurrenderedBabiesEntity safelySurrenderedBabiesEntity = safelySurrenderedBabiesMapper
            .map(safelySurenderedBabies, existingSafelySurrenderedBabiesEntity);
        safelySurrenderedBabiesEntity.setParticipantId(participantId);
        createdOrUpdatedSafelySurrenderedBabiesEntity =
            safelySurrenderedBabiesDao.update(safelySurrenderedBabiesEntity);
      }
      participantEntityManaged
          .setSafelySurrenderedBabies(createdOrUpdatedSafelySurrenderedBabiesEntity);
    }

  }

  public List<ParticipantEntity> getByScreeningId(String screeningId) {
    return participantDao.getByScreeningId(screeningId);
  }

  private static class AddressesWrapper {

    private AddressIntakeApi addressIntakeApi;
    private Addresses addresses;

    AddressesWrapper(AddressIntakeApi addressIntakeApi, Addresses addresses) {
      this.addressIntakeApi = addressIntakeApi;
      this.addresses = addresses;
    }
  }

  private AddressesWrapper createAddresses(AddressIntakeApi addressIntakeApi) {
    AddressIntakeApi workingAddressIntakeApi = addressIntakeApi;
    Addresses addressesEntityManaged =
        workingAddressIntakeApi.getId() == null ? null : addressesDao.find(workingAddressIntakeApi.getId());
    if (addressesEntityManaged == null
        || !workingAddressIntakeApi.equals(new AddressIntakeApi(addressesEntityManaged))) {
      // Create only those that don't exist or differs (were changed) from existing ones
      workingAddressIntakeApi.setId(null);
      addressesEntityManaged = addressesDao.create(new Addresses(workingAddressIntakeApi));
      LegacyDescriptor legacyDescriptor = workingAddressIntakeApi.getLegacyDescriptor();
      workingAddressIntakeApi = new AddressIntakeApi(addressesEntityManaged);
      workingAddressIntakeApi.setLegacyDescriptor(addressService
          .saveLegacyDescriptor(legacyDescriptor, addressesEntityManaged.getId()));
    }
    return new AddressesWrapper(workingAddressIntakeApi, addressesEntityManaged);
  }

  private List<PhoneNumber> createParticipantPhoneNumbers(List<PhoneNumber> phoneList,
      ParticipantEntity participantEntityManaged) {
    List<PhoneNumber> phoneListPosted = new ArrayList<>();

    for (PhoneNumber phoneNumber : phoneList) {

      PhoneNumbers phoneNumbersEntityManaged = phoneNumber.getId() == null ? null
          : phoneNumbersDao.find(String.valueOf(phoneNumber.getId()));

      if (phoneNumbersEntityManaged == null
          || !phoneNumber.equals(new PhoneNumber(phoneNumbersEntityManaged))) {
        // Create only those that don't exist or differs (were changed) from existing ones
        phoneNumber.setId(null);
        phoneNumbersEntityManaged = phoneNumbersDao.create(new PhoneNumbers(phoneNumber));
        phoneNumber = new PhoneNumber(phoneNumbersEntityManaged);
      }
      phoneListPosted.add(phoneNumber);

      ParticipantPhoneNumbers participantPhoneNumbers =
          new ParticipantPhoneNumbers(participantEntityManaged, phoneNumbersEntityManaged);
      participantPhoneNumbers.setCreatedAt(new Date());
      participantPhoneNumbers.setUpdatedAt(participantPhoneNumbers.getCreatedAt());
      participantPhoneNumbersDao.create(participantPhoneNumbers);

    }
    return phoneListPosted;
  }

  private List<PhoneNumber> updateParticipantPhoneNumbers(List<PhoneNumber> phoneList,
      ParticipantEntity participantEntityManaged) {
    List<PhoneNumber> phoneListPosted = new ArrayList<>();

    Map<String, ParticipantPhoneNumbers> participantPhoneNumbersOldMap = new HashMap<>();
    participantPhoneNumbersDao.findByParticipantId(participantEntityManaged.getId())
        .forEach(participantPhoneNumbers -> participantPhoneNumbersOldMap
            .put(participantPhoneNumbers.getPhoneNumber().getId(), participantPhoneNumbers));

    for (PhoneNumber phoneNumber : phoneList) {

      PhoneNumbers phoneNumbersEntityManaged = phoneNumber.getId() == null ? null
          : phoneNumbersDao.find(String.valueOf(phoneNumber.getId()));

      if (phoneNumbersEntityManaged == null
          || !phoneNumber.equals(new PhoneNumber(phoneNumbersEntityManaged))) {
        // Create only those that don't exist or differs (were changed) from existing ones
        phoneNumber.setId(null);
        phoneNumbersEntityManaged = phoneNumbersDao.create(new PhoneNumbers(phoneNumber));
        phoneNumber = new PhoneNumber(phoneNumbersEntityManaged);
      }

      phoneListPosted.add(phoneNumber);

      // See if we had this ParticipantPhoneNumber entity before. Otherwise create
      ParticipantPhoneNumbers participantPhoneNumbers =
          participantPhoneNumbersOldMap.get(phoneNumbersEntityManaged.getId());
      if (participantPhoneNumbers != null) {
        // Remove from the Old map
        participantPhoneNumbersOldMap.remove(phoneNumbersEntityManaged.getId());
        participantPhoneNumbersDao.update(participantPhoneNumbers);
      } else {
        participantPhoneNumbersDao.create(
            new ParticipantPhoneNumbers(participantEntityManaged, phoneNumbersEntityManaged));
      }
    }
    // Delete old ones that are not in the new.
    participantPhoneNumbersOldMap.values()
        .forEach(participantPhoneNumbers -> participantPhoneNumbersDao
            .delete(participantPhoneNumbers.getId()));

    return phoneListPosted;
  }

  void setCsecMapper(CsecMapper csecMapper) {
    this.csecMapper = csecMapper;
  }

  void setSafelySurrenderedBabiesMapper(
      SafelySurrenderedBabiesMapper safelySurrenderedBabiesMapper) {
    this.safelySurrenderedBabiesMapper = safelySurrenderedBabiesMapper;
  }

  protected Boolean getDobCode(LegacyDescriptor legacyDescriptor) {
    String dobCode = null;
    if (legacyDescriptor != null) {
      Client client = clientDao.findClientsByIds(Arrays.asList(legacyDescriptor.getId()))
              .get(legacyDescriptor.getId());
      if (client != null) {
        dobCode = client.getEstimatedDobCode();
      }
    }
    return BooleanUtils.toBooleanObject(dobCode);
  }

  void setParticipantDao(ParticipantDao participantDao) {
    this.participantDao = participantDao;
  }

  public void setClientDao(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  public void setParticipantTransformer(ParticipantTransformer participantTransformer){
    this.participantTransformer = participantTransformer;
  }

  public void setRelationshipFacade(
      RelationshipFacade relationshipFacade) {
    this.relationshipFacade = relationshipFacade;
  }
}
