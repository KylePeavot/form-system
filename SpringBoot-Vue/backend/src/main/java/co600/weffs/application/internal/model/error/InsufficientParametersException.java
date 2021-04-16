package co600.weffs.application.internal.model.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientParametersException extends Exception {

    public InsufficientParametersException(String message) {
        super(message);
    }
}
