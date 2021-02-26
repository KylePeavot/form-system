package co600.weffs.application.internal.model.flowable;

import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;

public class AssignedFormView {

  private Form form;
  private FormDetail formDetail;
  private WorkflowTask workflowTask;

  public AssignedFormView(Form form, FormDetail formDetail, WorkflowTask workflowTask) {
    this.form = form;
    this.formDetail = formDetail;
    this.workflowTask = workflowTask;
  }

  public Form getForm() {
    return form;
  }

  public WorkflowTask getWorkflowTask() {
    return workflowTask;
  }
}
