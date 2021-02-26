package co600.weffs.application.internal.model.flowable;

public enum WorkflowTask {
  ASSIGN_FORM_TO_FORM_FILLER("Assign form to form filler"),
  FILL_FORM("Filling form"),
  DELETE_FORM("Delete form"),
  FORM_SUBMITTED("Form submitted");


  private final String taskName;

  WorkflowTask(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskName() {
    return taskName;
  }

  public static WorkflowTask getWorkflowTaskFromTaskName(String taskName) {
    for(WorkflowTask workflowTask : WorkflowTask.values()) {
      if(workflowTask.taskName.equals(taskName)) {
        return workflowTask;
      }
    }
    return null;
  }
}
