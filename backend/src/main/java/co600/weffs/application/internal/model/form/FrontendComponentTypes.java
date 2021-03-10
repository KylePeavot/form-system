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

  public static boolean hasMultipleNestedQuestions(String componentType) {
    return CHECKBOX_GROUP.componentType.equals(componentType) || RADIO_GROUP.componentType.equals(componentType);
  }

  public static boolean hasSingleNestedQuestion(String componentType) {
    return CHECKBOX_QUESTION.componentType.equals(componentType);
  }
}
