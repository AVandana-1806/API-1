package gov.ca.cwds.rest.services.hoi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

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
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.hoi.HOICase;
import gov.ca.cwds.rest.api.domain.hoi.HOICaseResponse;
import gov.ca.cwds.rest.api.domain.hoi.HOIRequest;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.auth.AuthorizationService;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOICaseServiceTest {

  private CaseDao caseDao;
  private ClientRelationshipDao clientRelationshipDao;
  private HOICaseService target;
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
  @Before
  public void setUp() {
    new TestingRequestExecutionContext("02f");
    SystemCodeCache.global().getAllSystemCodes();
    caseDao = mock(CaseDao.class);

    ClientDao clientDao = mock(ClientDao.class);
    Client client123 = new ClientEntityBuilder().setId("CLIENT-123").build();
    when(clientDao.find(any(String.class))).thenReturn(client123);
    Map<String, Client> clientMap = new HashMap<>();
    Client client1 = new ClientEntityBuilder().setId("FOCUS-CHILD-1").build();
    Client client2 = new ClientEntityBuilder().setId("FOCUS-CHILD-2").build();
    clientMap.put(client1.getId(), client1);
    clientMap.put(client2.getId(), client2);
    when(clientDao.findClientsByIds(any(Set.class))).thenReturn(clientMap);

    clientRelationshipDao = mock(ClientRelationshipDao.class);
    AuthorizationService authorizationService = new AuthorizationService();
    target = new HOICaseService(caseDao, clientDao, clientRelationshipDao, authorizationService);
    request = new HOIRequest();
    request.setClientIds(Stream.of("CLIENT-123").collect(Collectors.toSet()));
  }

  @Test
  public void testHandleFind() {
    CmsCase cmscase1 = new CmsCaseEntityBuilder().setId("TAZGOO205C").setStartDate(new Date())
        .setStaffPerson(new StaffPersonEntityBuilder().build()).setFkchldClt("FOCUS-CHILD-1").build();
    CmsCase cmscase2 = new CmsCaseEntityBuilder().setId("RAZGOO205C").setStartDate(new Date())
        .setStaffPerson(new StaffPersonEntityBuilder().build()).setFkchldClt("FOCUS-CHILD-2").build();
    CmsCase[] cases = {cmscase1, cmscase2};
    ClientRelationship relationship = new ClientRelationshipEntityBuilder().build();
    ClientRelationship[] relationships = {relationship};
    when(clientRelationshipDao.findByPrimaryClientId(any(String.class))).thenReturn(relationships);
    when(clientRelationshipDao.findBySecondaryClientId(any(String.class)))
        .thenReturn(relationships);
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(cases);
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
    List<Integer> childToParentTypes = new ArrayList<>(
        Arrays.asList(198, 199, 242, 293, 242, 6360));
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
    CmsCase cmscase = new CmsCaseEntityBuilder().setId("TAZGOO205C")
        .setStaffPerson(new StaffPersonEntityBuilder().build()).setFkchldClt("FOCUS-CHILD-1").build();
    CmsCase[] cases = {cmscase};
    ClientRelationship relationship =
        new ClientRelationshipEntityBuilder().setClientRelationshipType(type).build();
    ClientRelationship[] relationships = {relationship};
    ClientRelationship[] emptyRelationships = {};
    when(clientRelationshipDao.findByPrimaryClientId(any(String.class))).thenReturn(relationships);
    when(clientRelationshipDao.findBySecondaryClientId(any(String.class)))
        .thenReturn(emptyRelationships);
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(cases);
    return target.handleFind(request);
  }

  private HOICaseResponse getCaseResponseWhereChildIsSecondaryClientIdInTheRelationship(short type) {
    CmsCase cmscase = new CmsCaseEntityBuilder().setId("TAZGOO205C")
        .setStaffPerson(new StaffPersonEntityBuilder().build()).setFkchldClt("FOCUS-CHILD-2").build();
    CmsCase[] cases = {cmscase};
    ClientRelationship relationship =
        new ClientRelationshipEntityBuilder().setClientRelationshipType(type).build();
    ClientRelationship[] relationships = {relationship};
    ClientRelationship[] emptyRelationships = {};
    when(clientRelationshipDao.findByPrimaryClientId(any(String.class)))
        .thenReturn(emptyRelationships);
    when(clientRelationshipDao.findBySecondaryClientId(any(String.class)))
        .thenReturn(relationships);
    when(caseDao.findByVictimClientIds(any(Collection.class))).thenReturn(cases);
    return target.handleFind(request);
  }

  @Test(expected = AuthorizationException.class)
  public void testUnAuthorizedClient() {
    HOICaseService spyTarget = spy(target);
    doThrow(AuthorizationException.class).when(spyTarget).authorizeClient("CLIENT-123");
    spyTarget.handleFind(request);
  }

}
