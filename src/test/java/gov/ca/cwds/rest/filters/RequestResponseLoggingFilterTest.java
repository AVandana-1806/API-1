package gov.ca.cwds.rest.filters;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import gov.ca.cwds.logging.AuditLogger;
import gov.ca.cwds.rest.api.ApiException;


public class RequestResponseLoggingFilterTest {

  private RequestResponseLoggingFilter filter;

  private HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
  private HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
  private HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
  private FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
  private FilterConfig mockFilterConfig = Mockito.mock(FilterConfig.class);

  private ServletInputStream mockServletInputStream = new ServletInputStream() {
    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("foo".getBytes());

    @Override
    public int read() throws IOException {
      return byteArrayInputStream.read();
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public boolean isReady() {
      return false;
    }

    @Override
    public void setReadListener(ReadListener arg0) {}
  };

  private AuditLogger auditLogger = Mockito.mock(AuditLogger.class);

  @Before
  public void setup() throws Exception {
    filter = new RequestResponseLoggingFilter(auditLogger);

    when(mockHttpServletRequest.toString()).thenReturn("requestToString");
    when(mockHttpServletRequest.getRemoteAddr()).thenReturn("remoteAddress");
    when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);

    when(mockHttpServletRequest.getInputStream()).thenReturn(mockServletInputStream);
    when(mockHttpSession.getId()).thenReturn("sessionid");
  }

  @Test
  public void testDoFilterSetsUpMDC() throws Exception {
    filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

    verify(auditLogger, times(1)).buildMDC("remoteAddress", "STUBBED_USER", "sessionid",
        Thread.currentThread().getName());
  }

  @Test
  public void testDoFilterAuditsHttpServletRequestToString() throws Exception {
    filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

    verify(auditLogger, times(1)).audit("requestToString");
  }

  @Test
  public void testDoFilterAuditsHttpServletRequestContent() throws Exception {
    filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

    verify(auditLogger, times(1)).audit("foo");
  }

  @Test
  public void testDoFilterAuditsHttpServletResponse() throws Exception {
    filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

    verify(auditLogger, times(1)).audit(startsWith("Mock for HttpServletResponse, hashCode"));
  }

  @Test
  public void testDoFilterTearsDownMDCOnSuccess() throws Exception {
    filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

    verify(auditLogger, times(1)).teardownMDC();
  }

  @Test(expected = ApiException.class)
  public void testDoFilterThrowsApiExceptionOnException() throws Exception {
    doThrow(new ApiException()).when(mockFilterChain).doFilter(any(), any());

    filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
  }

  @Test(expected = ApiException.class)
  public void testDoFilterTearsDownMDCOnException() throws Exception {
    doThrow(new ApiException()).when(mockFilterChain).doFilter(any(), any());

    filter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
    verify(auditLogger, times(1)).teardownMDC();
  }

}
