package co600.weffs.application.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Utility class aimed to prevent unchecked casting errors from test files.
 * Errors will be thrown for invalid casts.
 *
 * @param <T> Type to convert to
 */
public class ValueMapUtils<T> {

  public T mapResponse(MockHttpServletResponse response)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return (T) mapper.readValue(response.getContentAsByteArray(), Object.class);
  }

  public T safeMap(Object o) {
    return (T) o;
  }

}
