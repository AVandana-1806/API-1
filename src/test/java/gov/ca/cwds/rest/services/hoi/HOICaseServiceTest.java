package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import gov.ca.cwds.data.cms.StaffPersonDao;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.shiro.authz.AuthorizationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.CaseDao;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.cms.TestSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.fixture.ClientEntityBuilder;
import gov.ca.cwds.fixture.ClientRelationshipEntityBuilder;
import gov.ca.cwds.fixture.CmsCaseEntityBuilder;
import gov.ca.cwds.fixture.StaffPersonEntityBuilder;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import gov.ca.cwds.rest.util.Doofenshmirtz;

/**
 * @author CWDS API Team
 */
public class HOICaseServiceTest extends Doofenshmirtz<Client> {

  private CaseDao caseDao;
  private ClientDao clientDao;
  private ClientRelationshipDao clientRelationshipDao;
  private StaffPersonDao staffPersonDao;

  private HOICaseService target;
  private AuthorizationService authorizationService;
  private HOIRequest request;

  /**
   * Initialize system code cache
   */
  private TestSystemCodeCache testSystemCodeCache = new TestSystemCodeCache();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Test setup
   *
   * @throws Exception - Exception
   */
  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    caseDao = mock(CaseDao.class);
    clientDao = mock(ClientDao.class);
    clientRelationshipDao = mock(ClientRelationshipDao.class);
    staffPersonDao = mock(StaffPersonDao.class);

    final Map<String, Client> clientMap = new HashMap<>();
    Client client1 = new ClientEntityBuilder().setId("FOCUS-CHILD-1").build();
    Client client2 = new ClientEntityBuilder().setId("FOCUS-CHILD-2").build();
    Client client123 = new ClientEntityBuilder().setId("CLIENT-123").build();
    clientMap.put(client1.getId(), client1);
    clientMap.put(client2.getId(), client2);
    clientMap.put(client123.getId(), client123);
    when(clientDao.findClientsByIds(any(Set.class))).thenReturn(clientMap);

    final Map<String, StaffPerson> staffPersons = new HashMap<>();
    staffPersons.put("q1p", new StaffPersonEntityBuilder().build());
    when(staffPersonDao.findByIds(any(Collection.class))).thenReturn(staffPersons);

    authorizationService = new AuthorizationService();
    target = new HOICaseService(caseDao, clientDao, clientRelationshipDao, staffPersonDao,
        authorizationService);
    request = new HOIRequest();
    request.setClientIds(Stream.of("CLIENT-123").collect(Collectors.toSet()));
  }

  @Test
  public void testHandleFind() {
    Map<String, Collection<ClientRelationship>> relationships = new HashMap<>();
    relationships.put("FOCUS-CHILD-1", Arrays
        .asList(new ClientRelationshipEntityBuilder().setPrimaryClientId("FOCUS-CHILD-1").build()));
    relationships.put("FOCUS-CHILD-2", Arrays
        .asList(new ClientRelationshipEntityBuilder().setPrimaryClientId("FOCUS-CHILD-2").build()));
    when(clientRelationshipDao.findByPrimaryClientIds(any(Collection.class)))
        .thenReturn(relationships);

    CmsCase cmscase1 = new CmsCaseEntityBuilder().setId("TAZGOO205C").setStartDate(new Date())
        .setFkchldClt("FOCUS-CHILD-1").build();
    CmsCase cmscase2 = new CmsCaseEntityBuilder().setId("RAZGOO205C").setStartDate(new Date())
        .setFkchldClt("FOCUS-CHILD-2").build();
    Map<String, CmsCase> cmsCases = new HashMap<>();
    cmsCases.put(cmscase1.getId(), cmscase1);
    cmsCases.put(cmscase2.getId(), cmscase2);
    when(caseDao.findByClientIds(any(Collection.class))).thenReturn(cmsCases);

    HOICaseResponse response = target.handleFind(request);
    assertThat(response, notNullValue());
  }

  @Test
  public void testHandleFindWhenNoClientIdsProvided() {
    HOIRequest emptyRequest = new HOIRequest();
    emptyRequest.setClientIds(new HashSet<>());
    HOICaseResponse response = target.handleFind(emptyRequest);
    assertThat(response, notNullValue());
  }

  @Test
  public void testFindParentsBySecondaryRelationship254MotherSonStepSuccess() {
    HOICaseResponse response =
        getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship((short) 254);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsBySecondaryRelationshipType214FatherSonStepSuccess() {
    HOICaseResponse response =
        getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship((short) 214);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsBySecondaryRelationshipType6361MotherSonPresumedSuccess() {
    HOICaseResponse response =
        getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship((short) 6361);
    assertEquals(1, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsByPrimaryRelationshipType254MotherSonStepFails() {
    HOICaseResponse response =
        getCaseResponseWhereChildIsPrimaryClientInTheRelationship((short) 254);
    assertEquals(0, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsByPrimaryRelationshipType296UncleNephewMaternalFails() {
    HOICaseResponse response =
        getCaseResponseWhereChildIsPrimaryClientInTheRelationship((short) 296);
    assertEquals(0, response.getHoiCases().get(0).getParents().size());
  }

  @Test
  public void testFindParentsWhereChildIsSecondaryClientInTheRelationshipForAllValidTypesSuccess() {
    List<Integer> parentToChildTypes = new ArrayList<>(Arrays.asList(243, 254, 5620, 6361));
    for (Integer i = 203; i <= 205; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 207; i <= 211; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 213; i <= 214; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 245; i <= 247; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 249; i <= 252; i++) {
      parentToChildTypes.add(i);
    }

    for (Integer parentToChildType : parentToChildTypes) {
      HOICaseResponse response = getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship(
          parentToChildType.shortValue());
      assertEquals(1, response.getHoiCases().get(0).getParents().size());
    }
  }

  @Test
  public void testFindParentsWhereChildIsPrimaryClientInTheRelationshipForAllValidTypesSuccess() {
    List<Integer> childToParentTypes =
        new ArrayList<>(Arrays.asList(198, 199, 242, 293, 242, 6360));
    for (Integer i = 188; i <= 190; i++) {
      childToParentTypes.add(i);
    }
    for (Integer i = 192; i <= 196; i++) {
      childToParentTypes.add(i);
    }
    for (Integer i = 287; i <= 291; i++) {
      childToParentTypes.add(i);
    }
    for (Integer i = 283; i <= 285; i++) {
      childToParentTypes.add(i);
    }
    for (Integer childToParentType : childToParentTypes) {
      HOICaseResponse response =
          getCaseResponseWhereChildIsPrimaryClientInTheRelationship(childToParentType.shortValue());
      assertEquals(1, response.getHoiCases().get(0).getParents().size());
    }
  }

  @Test(expected = NotImplementedException.class)
  public void testHandleRequest() {
    target.handleRequest(new HOICase());
  }

  private HOICaseResponse getCaseResponseWhereChildIsPrimaryClientInTheRelationship(short type) {
    Map<String, Collection<ClientRelationship>> relationships = new HashMap<>();
    relationships.put("FOCUS-CHILD-1", Arrays.asList(new ClientRelationshipEntityBuilder()
        .setClientRelationshipType(type).setSecondaryClientId("FOCUS-CHILD-1").build()));
    when(clientRelationshipDao.findByPrimaryClientIds(any(Collection.class)))
        .thenReturn(relationships);
    when(clientRelationshipDao.findBySecondaryClientIds(any(Collection.class)))
        .thenReturn(new HashMap());

    CmsCase cmscase =
        new CmsCaseEntityBuilder().setId("TAZGOO205C").setFkchldClt("FOCUS-CHILD-1").build();
    Map<String, CmsCase> cmsCases = new HashMap<>();
    cmsCases.put(cmscase.getId(), cmscase);
    when(caseDao.findByClientIds(any(Collection.class))).thenReturn(cmsCases);

    return target.handleFind(request);
  }

  private HOICaseResponse getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship(
      short type) {
    Map<String, Collection<ClientRelationship>> relationships = new HashMap<>();
    relationships.put("FOCUS-CHILD-2", Arrays.asList(new ClientRelationshipEntityBuilder()
        .setClientRelationshipType(type).setPrimaryClientId("FOCUS-CHILD-2").build()));
    when(clientRelationshipDao.findByPrimaryClientIds(any(Collection.class)))
        .thenReturn(new HashMap());
    when(clientRelationshipDao.findBySecondaryClientIds(any(Collection.class)))
        .thenReturn(relationships);

    CmsCase cmscase =
        new CmsCaseEntityBuilder().setId("TAZGOO205C").setFkchldClt("FOCUS-CHILD-2").build();
    Map<String, CmsCase> cmsCases = new HashMap<>();
    cmsCases.put(cmscase.getId(), cmscase);
    when(caseDao.findByClientIds(any(Collection.class))).thenReturn(cmsCases);

    return target.handleFind(request);
  }

  @Test
  public void testUnAuthorizedClient() {
    authorizationService = mock(AuthorizationService.class);
    target = new HOICaseService(caseDao, clientDao, clientRelationshipDao, staffPersonDao,
        authorizationService);
    final HOICaseService spyTarget = spy(target);
    final HOICaseResponse response = spyTarget.handleFind(request);
    assertThat("No Cases have been returned", response.getHoiCases().isEmpty());
  }

}
