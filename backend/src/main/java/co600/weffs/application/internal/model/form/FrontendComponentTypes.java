package co600.weffs.application.internal.model.form;

public enum FrontendComponentTypes {
  TEXT_FIELD("TextField"),
  TEXT_AREA("TextArea"),
  CHECKBOX_QUESTION("CheckboxQuestion"),
  CHECKBOX_GROUP("CheckboxGroup"),
  RADIO_GROUP("RadioGroup"),
  NESTED_QUESTION("Nested question");

  private String componentType;

  FrontendComponentTypes(String componentType) {
    this.componentType = componentType;
  }

  public String getComponentType() {
    return componentType;
  }
}
