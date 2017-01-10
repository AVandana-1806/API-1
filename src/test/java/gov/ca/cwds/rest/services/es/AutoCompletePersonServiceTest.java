package gov.ca.cwds.rest.services.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.elasticsearch.search.SearchHits;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePerson;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonRequest;
import gov.ca.cwds.rest.api.domain.es.AutoCompletePersonResponse;

public class AutoCompletePersonServiceTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ElasticsearchDao dao;

  @Mock
  private SearchHits hits;

  @InjectMocks
  @Spy
  private AutoCompletePersonService target; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void type() throws Exception {
    assertThat(AutoCompletePersonService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void handleRequest_A$AutoCompletePersonRequest() throws Exception {
    // given
    AutoCompletePersonRequest req = mock(AutoCompletePersonRequest.class);
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    AutoCompletePersonResponse actual = target.handleRequest(req);
    // then
    // e.g. : verify(mocked).called();
    AutoCompletePersonResponse expected = new AutoCompletePersonResponse(new AutoCompletePerson[0]);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void handleFind_A$String() throws Exception {
    // given
    String arg0 = null;
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    AutoCompletePersonResponse actual = target.handleFind(arg0);
    // then
    // e.g. : verify(mocked).called();
    AutoCompletePersonResponse expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
