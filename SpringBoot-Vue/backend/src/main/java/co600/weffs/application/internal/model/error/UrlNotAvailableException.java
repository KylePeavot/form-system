package co600.weffs.application.internal.model.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Service could not be reached")
public class UrlNotAvailableException extends RuntimeException {

  public UrlNotAvailableException(String message) {
    super(message);
  }

}
