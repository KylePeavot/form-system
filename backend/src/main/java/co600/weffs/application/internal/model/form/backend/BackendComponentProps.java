package co600.weffs.application.internal.model.form.backend;

import lombok.Data;

public enum BackendComponentProps {

  QUESTION_DETAIL_ID("questionDetailId");

  private final String name;

  BackendComponentProps(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
