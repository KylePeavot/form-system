package co600.weffs.application;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Base test controller to setup initial boilerplate code. Should be extended by other tests.
 * By default uses {test} ActiveProfile. Shouldn't be any reason to modify this.
 */
@WebMvcTest(excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ActiveProfiles({"test"})
public class TestableController {

  @Autowired
  protected MockMvc mockMvc;

  protected static MockHttpServletRequest requestServlet;
  protected static ServletRequestAttributes requestAttributes;

  @BeforeEach
  void setUp() {
    requestServlet = new MockHttpServletRequest();
    requestAttributes = new ServletRequestAttributes(requestServlet);
    RequestContextHolder.setRequestAttributes(requestAttributes);
  }

  @BeforeAll
  public static void setup() {
    requestServlet = new MockHttpServletRequest();
    requestAttributes = new ServletRequestAttributes(requestServlet);
    RequestContextHolder.setRequestAttributes(requestAttributes);
  }


}
