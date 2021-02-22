package co600.weffs.application.internal.model.error;

/**
 * Used when throwing an error for when an enum is unable to be resolved.
 * Either wrong value passed to application, value has been updated, or mapped incorrectly internally.
 */
public class EnumNotFoundException extends RuntimeException {

  public EnumNotFoundException(String message) {
    super(message);
  }

}
