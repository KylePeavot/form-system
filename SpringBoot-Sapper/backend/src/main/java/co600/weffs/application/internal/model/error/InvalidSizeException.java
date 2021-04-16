package co600.weffs.application.internal.model.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Size is not valid")
public class InvalidSizeException extends RuntimeException {

  public InvalidSizeException(String message) {
    super(message);
  }

}
