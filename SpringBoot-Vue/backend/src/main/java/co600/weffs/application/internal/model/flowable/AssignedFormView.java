package co600.weffs.application.internal.model.flowable;

import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import lombok.Data;

@Data
public class AssignedFormView {

  private FormResponse formResponse;
  private WorkflowTask workflowTask;

  public AssignedFormView(FormResponse formResponse, WorkflowTask workflowTask) {
    this.formResponse = formResponse;
    this.workflowTask = workflowTask;
  }
}
