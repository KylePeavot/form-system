package co600.weffs.application.internal.model.form;

public enum FrontendComponentProps {
  TITLE("title"),
  GUIDANCE("guidance"),
  SELECTION_VALUE("selectionValue"),
  SELECTION_VALUES("selectionValues");

  private String frontendName;

  FrontendComponentProps(String frontendName) {
    this.frontendName = frontendName;
  }

  public String getFrontendName() {
    return frontendName;
  }
}
