package co600.weffs.application.internal.model.form.frontend;

public enum FrontendComponentProps {
  TITLE("title"),
  GUIDANCE("guidance"),
  //how do we do text fields rn??
  SELECTION_VALUE("selectionValue"),
  SELECTION_VALUES("selectionValues"),
  TEXT_VALUE("textValue");

  private String frontendName;

  FrontendComponentProps(String frontendName) {
    this.frontendName = frontendName;
  }

  public String getFrontendName() {
    return frontendName;
  }
}
