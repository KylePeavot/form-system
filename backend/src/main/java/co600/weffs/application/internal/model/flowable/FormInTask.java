package co600.weffs.application.internal.model.flowable;

import co600.weffs.application.internal.model.form.Form;

public class FormInTask {

  private Form form;
  private WorkflowTask workflowTask;

  public FormInTask(Form form, WorkflowTask workflowTask) {
    this.form = form;
    this.workflowTask = workflowTask;
  }

  public Form getForm() {
    return form;
  }

  public WorkflowTask getWorkflowTask() {
    return workflowTask;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FormInTask) {
      FormInTask formInTaskToCompare = (FormInTask) obj;
      return this.form.equals(formInTaskToCompare.getForm()) && this.workflowTask == formInTaskToCompare.getWorkflowTask();
    }
    return false;
  }
}
