package gov.ca.cwds.rest.filters;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gov.ca.cwds.rest.services.cms.AbstractShiroTest;

public class RequestExecutionContextFilterTest extends AbstractShiroTest {

  @Mock
  ServletRequest request;

  @Mock
  ServletResponse response;

  @Mock
  FilterChain chain;

  @Mock
  FilterConfig filterConfig;

  RequestExecutionContextFilter target;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    chain = mock(FilterChain.class);
    filterConfig = mock(FilterConfig.class);

    Subject mockSubject = mock(Subject.class);
    PrincipalCollection principalCollection = mock(PrincipalCollection.class);

    List list = new ArrayList();
    list.add("msg");

    when(principalCollection.asList()).thenReturn(list);
    when(mockSubject.getPrincipals()).thenReturn(principalCollection);
    setSubject(mockSubject);

    target = new RequestExecutionContextFilter();
  }

  @Test
  public void type() throws Exception {
    assertThat(RequestExecutionContextFilter.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void doFilter_Args__ServletRequest__ServletResponse__FilterChain() throws Exception {
    target.doFilter(request, response, chain);
  }

  @Test(expected = IOException.class)
  public void doFilter_Args__ServletRequest__ServletResponse__FilterChain_T__IOException()
      throws Exception {
    doThrow(new IOException("uh oh")).when(chain).doFilter(request, response);

    target.doFilter(request, response, chain);
    fail("Expected exception was not thrown!");
  }

  @Test(expected = ServletException.class)
  public void doFilter_Args__ServletRequest__ServletResponse__FilterChain_T__ServletException()
      throws Exception {
    doThrow(new ServletException("uh oh")).when(chain).doFilter(request, response);
    target.doFilter(request, response, chain);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void init_Args__FilterConfig() throws Exception {
    target.init(filterConfig);
  }

  // @Test(expected = ServletException.class)
  public void init_Args__FilterConfig_T__ServletException() throws Exception {
    doThrow(new ServletException("uh oh")).when(filterConfig).getFilterName();

    target.init(filterConfig);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void destroy_Args__() throws Exception {
    target.destroy();
  }

}
