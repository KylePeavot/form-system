package co600.weffs.application.internal.model.flowable.frontend;

import co600.weffs.application.internal.model.form.frontend.FrontendForm;
import lombok.Data;

@Data
public class FormResponseSubmissionVariables {

  private int responseId;
  private FrontendForm form;

}
