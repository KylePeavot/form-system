package co600.weffs.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Base test controller to setup initial boilerplate code. Should be extended by other tests.
 * By default uses {test} ActiveProfile. Shouldn't be any reason to modify this.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
public class TestableController {

  @Autowired
  protected MockMvc mockMvc;

  protected ObjectMapper jacksonMapper;

  protected static MockHttpServletRequest requestServlet;
  protected static ServletRequestAttributes requestAttributes;

  @BeforeEach
  void testableControllerSetUp() {
    jacksonMapper = new ObjectMapper();
    requestServlet = new MockHttpServletRequest();
    requestAttributes = new ServletRequestAttributes(requestServlet);
    RequestContextHolder.setRequestAttributes(requestAttributes);
  }

  @Test
  public void dummyTest() {
    assertThat(true).isTrue();
  }


}
