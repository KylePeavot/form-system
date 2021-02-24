package co600.weffs.application.internal.model.error;

public class WorkflowTaskNotFound extends RuntimeException {

  public WorkflowTaskNotFound(String message) {
    super(message);
  }
}
