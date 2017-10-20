package gov.ca.cwds.rest.services.investigation.contact;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.cms.AttorneyDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.CollateralIndividualDao;
import gov.ca.cwds.data.cms.ReporterDao;
import gov.ca.cwds.data.cms.ServiceProviderDao;
import gov.ca.cwds.data.cms.SubstituteCareProviderDao;
import gov.ca.cwds.data.dao.contact.IndividualDeliveredServiceDao;
import gov.ca.cwds.data.persistence.contact.DeliveredServiceEntity;
import gov.ca.cwds.data.persistence.contact.IndividualDeliveredServiceEntity;
import gov.ca.cwds.data.std.ApiPersonAware;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.api.domain.PostedIndividualDeliveredService;
import gov.ca.cwds.rest.api.domain.investigation.contact.ContactRequest;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Business layer object
 * 
 * @author CWDS API Team
 */
public class DeliveredToIndividualService {

  private EnumMap<Code, BaseDaoImpl<? extends ApiPersonAware>> codeToDaoImplememterMap;
  private IndividualDeliveredServiceDao individualDeliveredServiceDao;
  private String currentUserStaffId = RequestExecutionContext.instance().getStaffId();
  private Date currentRequestStartTime = RequestExecutionContext.instance().getRequestStartTime();


  /**
   * Constructor
   * 
   * @param clientDao the Client Dao
   * @param attorneyDao the Attorney Dao
   * @param collateralIndividualDao the CollateralIndividual Dao
   * @param serviceProviderDao the ServiceProvider Dao
   * @param substituteCareProviderDao the SubstituteCareProvider Dao
   * @param reporterDao the Reporter Dao
   * @param individualDeliveredServiceDao the IndividualDeliveredService Dao
   */
  @Inject
  public DeliveredToIndividualService(ClientDao clientDao, AttorneyDao attorneyDao,
      CollateralIndividualDao collateralIndividualDao, ServiceProviderDao serviceProviderDao,
      SubstituteCareProviderDao substituteCareProviderDao, ReporterDao reporterDao,
      IndividualDeliveredServiceDao individualDeliveredServiceDao) {
    super();
    this.codeToDaoImplememterMap =
        deliveredToIndividualCodeToDaoImplementerMap(clientDao, attorneyDao,
            collateralIndividualDao, serviceProviderDao, substituteCareProviderDao, reporterDao);
    this.individualDeliveredServiceDao = individualDeliveredServiceDao;
  }

  /**
   * EnumMap that links each DeliveredToIndividualCode to the corresponding Dao
   * 
   * @param clientDao the clientDao
   * @param attorneyDao the attorneyDao
   * @param collateralIndividualDao the collateralIndividualDao
   * @param serviceProviderDao the serviceProviderDao
   * @param substituteCareProviderDao the substituteCareProviderDao
   * @param reporterDao the reporterDao
   * @return the Code Dao Implementer EnumMap
   */
  private EnumMap<Code, BaseDaoImpl<? extends ApiPersonAware>> deliveredToIndividualCodeToDaoImplementerMap(
      ClientDao clientDao, AttorneyDao attorneyDao, CollateralIndividualDao collateralIndividualDao,
      ServiceProviderDao serviceProviderDao, SubstituteCareProviderDao substituteCareProviderDao,
      ReporterDao reporterDao) {
    EnumMap<Code, BaseDaoImpl<? extends ApiPersonAware>> deliveredToIndividualCodeToDaoImplememterMap =
        new EnumMap<>(Code.class);
    deliveredToIndividualCodeToDaoImplememterMap.put(Code.CLIENT, clientDao);
    deliveredToIndividualCodeToDaoImplememterMap.put(Code.SERVICE_PROVIDER, serviceProviderDao);
    deliveredToIndividualCodeToDaoImplememterMap.put(Code.COLLATERAL_INDIVIDUAL,
        collateralIndividualDao);
    deliveredToIndividualCodeToDaoImplememterMap.put(Code.REPORTER, reporterDao);
    deliveredToIndividualCodeToDaoImplememterMap.put(Code.ATTORNEY, attorneyDao);
    deliveredToIndividualCodeToDaoImplememterMap.put(Code.SUBSTITUTE_CARE_PROVIDER,
        substituteCareProviderDao);
    return deliveredToIndividualCodeToDaoImplememterMap;
  }

  /**
   * Returns the appropriate DAO Implementer for the code
   * 
   * @param code The Delivered To Individual Code
   * @return The doo implementer
   */
  private BaseDaoImpl<? extends ApiPersonAware> getDaoImplementer(Code code) {
    return this.codeToDaoImplememterMap.get(code);
  }

  /**
   * Find the IndividualDeliveredService persistence object for the deliveredServiceEntity
   * 
   * @param individualDeliveredService {@link IndividualDeliveredServiceEntity}
   * @return Person In IndividualDeliveredService
   */
  public PostedIndividualDeliveredService findPerson(
      IndividualDeliveredServiceEntity individualDeliveredService) {
    final DeliveredToIndividualService.Code deliveredToIndividualCode =
        DeliveredToIndividualService.Code.lookupByCodeLiteral(individualDeliveredService
            .getIndividualDeliveredServiceEmbeddable().getDeliveredToIndividualCode());
    final String id = individualDeliveredService.getIndividualDeliveredServiceEmbeddable()
        .getDeliveredToIndividualId();
    return addPersonDetails(this.getDaoImplementer(deliveredToIndividualCode),
        deliveredToIndividualCode, id);
  }

  /**
   * Generic, reusable approach to fetch contact persons.
   * 
   * @param dao person aware DAO
   * @param code referenced table
   * @param id referenced id
   * @return contact
   */
  protected PostedIndividualDeliveredService addPersonDetails(
      final BaseDaoImpl<? extends ApiPersonAware> dao, final Code code, final String id) {
    final ApiPersonAware person = dao.find(id);
    return (person != null)
        ? new PostedIndividualDeliveredService(code.getValue(), id, person.getFirstName(),
            person.getMiddleName(), person.getLastName(), person.getNameSuffix(),
            person.getNameSuffix(), code.getDescription())
        : defaultPostedIndividualDeliveredService(code, id);
  }

  /**
   * Create a default Person when name information is unknown
   * 
   * @param deliveredToIndividualCode the deliveredToIndividualCode
   * @param id the id
   * @return default IndividualDeliveredService with no name info
   */
  private PostedIndividualDeliveredService defaultPostedIndividualDeliveredService(
      Code deliveredToIndividualCode, String id) {
    return new PostedIndividualDeliveredService(deliveredToIndividualCode.getValue(), id, "", "",
        "", "", "", deliveredToIndividualCode.getDescription());
  }

  /**
   * Find the IndividualDeliveredService persistence objects for the deliveredServiceEntity and
   * include the person information for each
   * 
   * @param deliveredServiceEntity The persistence object
   * @return People In IndividualDeliveredService
   */
  public Set<PostedIndividualDeliveredService> getPeopleInIndividualDeliveredService(
      DeliveredServiceEntity deliveredServiceEntity) {
    final Set<PostedIndividualDeliveredService> people = new HashSet<>();
    final IndividualDeliveredServiceEntity[] individualDeliveredServices =
        individualDeliveredServiceDao.findByDeliveredServiceId(deliveredServiceEntity.getId());
    for (IndividualDeliveredServiceEntity individualDeliveredService : individualDeliveredServices) {
      people.add(this.findPerson(individualDeliveredService));
    }
    return people;
  }

  /**
   * Representing a Delivered To Individual Code
   * 
   * This defines each type of participant to whom a service was delivered. Only Clients (C) may
   * participate in any non-contact type of services. Service Providers (P), Collateral Individuals
   * (O), Reporters (R), Attorneys (A), or Substitute Care Providers (S) can only participate in
   * Contacts.
   * 
   * @author CWDS API Team
   *
   */
  public enum Code {
    CLIENT("C", "CLIENT_T", "Client"), SERVICE_PROVIDER("P", "SVC_PVRT",
        "Service Provider"), COLLATERAL_INDIVIDUAL("O", "COLTRL_T",
            "Collateral Individual"), REPORTER("R", "REPTR_T", "Reporter"), ATTORNEY("A",
                "ATTRNY_T",
                "Attorney"), SUBSTITUTE_CARE_PROVIDER("S", "SB_PVDRT", "Substitute Care Provider");

    private final String codeLiteral;
    private final String value;
    private final String description;


    private Code(String codeLiteral, String value, String description) {
      this.codeLiteral = codeLiteral;
      this.value = value;
      this.description = description;
    }

    public String getCodeLiteral() {
      return codeLiteral;
    }

    public String getValue() {
      return value;
    }

    public String getDescription() {
      return description;
    }

    /**
     * Lookup by Code
     * 
     * @param codeLiteral The Delivered to Individual code
     * @return DeliveredToIndividualCode for given code if found.
     */
    public static Code lookupByCodeLiteral(String codeLiteral) {
      if (!StringUtils.isBlank(codeLiteral)) {
        for (Code deliveredToIndividual : Code.values()) {
          if (deliveredToIndividual.getCodeLiteral().equals(codeLiteral.trim())) {
            return deliveredToIndividual;
          }
        }
      }
      throw new ApiException("UNKNOWN DELIVERED TO INDIVIDUAL CODE: " + codeLiteral);
    }

    /**
     * Lookup by Value
     * 
     * @param value The Delivered to Individual code table name
     * @return DeliveredToIndividualCode for given value if found.
     */
    public static Code lookupByValue(String value) {
      if (!StringUtils.isBlank(value)) {
        for (Code deliveredToIndividual : Code.values()) {
          if (deliveredToIndividual.getValue().equals(value.trim())) {
            return deliveredToIndividual;
          }
        }
      }
      throw new ApiException("UNKNOWN DELIVERED TO INDIVIDUAL CODE FOR TABLE_NAME: " + value);
    }

  }

  /**
   * Creates a record in IndividualDeliveredService for each person in people
   * 
   * @param deliveredServiceId the identifier of the Delivered Service
   * @param contactRequest the contact request
   * @param countySpecificCode the county of the Referral
   * 
   */
  public void addPeopleToIndividualDeliveredService(String deliveredServiceId,
      ContactRequest contactRequest, String countySpecificCode) {
    Date endedAt = DomainChef.uncookISO8601Timestamp(contactRequest.getEndedAt());
    Date startedAt = DomainChef.uncookISO8601Timestamp(contactRequest.getStartedAt());
    Integer serviceContactType = Integer.valueOf(contactRequest.getPurpose());
    Set<PostedIndividualDeliveredService> people = contactRequest.getPeople();
    for (PostedIndividualDeliveredService person : people) {
      String deliveredToIndividualCode =
          DeliveredToIndividualService.Code.lookupByValue(person.getTableName()).getCodeLiteral();
      String deliveredToIndividualId = person.getId();
      IndividualDeliveredServiceEntity ids =
          new IndividualDeliveredServiceEntity(deliveredServiceId, deliveredToIndividualCode,
              deliveredToIndividualId, countySpecificCode, endedAt, serviceContactType.shortValue(),
              startedAt, currentUserStaffId, currentRequestStartTime);
      individualDeliveredServiceDao.create(ids);
    }
  }

  /**
   * Updates the entries in IndividualDeliveredService This includes, creates a record in
   * IndividualDeliveredService for each new person in people, deletes the records that were removed
   * from people payload.
   * 
   * @param deliveredServiceId the identifier of the Delivered Service
   * @param contactRequest the contact request
   * @param countySpecificCode the county of the Referral
   * 
   */
  public void updatePeopleToIndividualDeliveredService(String deliveredServiceId,
      ContactRequest contactRequest, String countySpecificCode) {
    Date endedAt = DomainChef.uncookISO8601Timestamp(contactRequest.getEndedAt());
    Date startedAt = DomainChef.uncookISO8601Timestamp(contactRequest.getStartedAt());
    Integer serviceContactType = Integer.valueOf(contactRequest.getPurpose());
    Set<PostedIndividualDeliveredService> people = contactRequest.getPeople();
    IndividualDeliveredServiceEntity[] entities =
        individualDeliveredServiceDao.findByDeliveredServiceId(deliveredServiceId);
    deleteRemovedPeopleFromIndividualDeliveredService(people, entities);
    List<PostedIndividualDeliveredService> peopleToAdd =
        findNewPeopleToAddToIndividualDeliveredService(people, entities);
    for (PostedIndividualDeliveredService newPerson : peopleToAdd) {
      String deliveredToIndividualCode = DeliveredToIndividualService.Code
          .lookupByValue(newPerson.getTableName()).getCodeLiteral();

      IndividualDeliveredServiceEntity ids =
          new IndividualDeliveredServiceEntity(deliveredServiceId, deliveredToIndividualCode,
              newPerson.getId(), countySpecificCode, endedAt, serviceContactType.shortValue(),
              startedAt, currentUserStaffId, currentRequestStartTime);
      individualDeliveredServiceDao.create(ids);
    }

  }

  private List<PostedIndividualDeliveredService> findNewPeopleToAddToIndividualDeliveredService(
      Set<PostedIndividualDeliveredService> people, IndividualDeliveredServiceEntity[] entities) {
    List<PostedIndividualDeliveredService> newPeople = new ArrayList<>();
    for (PostedIndividualDeliveredService person : people) {
      Boolean createEntryInDeliveredToIndividualService = Boolean.TRUE;
      String deliveredToIndividualId = person.getId();
      for (IndividualDeliveredServiceEntity individualDeliveredService : entities) {
        if (individualDeliveredService.getPrimaryKey().getDeliveredToIndividualId()
            .equals(deliveredToIndividualId)) {
          createEntryInDeliveredToIndividualService = Boolean.FALSE;
          break;
        }
      }
      if (createEntryInDeliveredToIndividualService) {
        newPeople.add(person);
      }
    }
    return newPeople;
  }

  private void deleteRemovedPeopleFromIndividualDeliveredService(
      Set<PostedIndividualDeliveredService> people, IndividualDeliveredServiceEntity[] entities) {
    for (IndividualDeliveredServiceEntity individualDeliveredService : entities) {
      Boolean deleteRecordInDeliveredToIndividualService = Boolean.TRUE;
      for (PostedIndividualDeliveredService person : people) {
        String deliveredToIndividualId = person.getId();
        if (individualDeliveredService.getPrimaryKey().getDeliveredToIndividualId()
            .equals(deliveredToIndividualId)) {
          deleteRecordInDeliveredToIndividualService = Boolean.FALSE;
          break;
        }
      }
      if (deleteRecordInDeliveredToIndividualService) {
        individualDeliveredServiceDao.delete(individualDeliveredService.getPrimaryKey());
      }
    }
  }


}
