package co600.weffs.application.internal.model.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Used when throwing an error for when an enum is unable to be resolved.
 * Either wrong value passed to application, value has been updated, or mapped incorrectly internally.
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Enum could not be mapped")
public class EnumNotFoundException extends RuntimeException {

  public EnumNotFoundException(String message) {
    super(message);
  }

}
