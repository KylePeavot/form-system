package co600.weffs.application.internal.model.form.frontend;

public enum FrontendComponentProps {
  TITLE("title"),
  GUIDANCE("guidance"),
  SELECTION_VALUE("selectionValue"),
  SELECTION_VALUES("selectionValues"),
  TEXT_VALUE("textValue"),
  DATE_VALUE("dateValue"),
  LABEL("_label"),
  VALUE("_value"),
  QUESTION_DETAIL_ID("_questionDetailId");

  private String frontendName;

  FrontendComponentProps(String frontendName) {
    this.frontendName = frontendName;
  }

  public String getFrontendName() {
    return frontendName;
  }
}
