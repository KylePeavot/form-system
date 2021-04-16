package co600.weffs.application.internal.model.auth;

import co600.weffs.application.internal.model.error.EnumNotFoundException;
import java.util.Arrays;

public enum SamlRole {

  UNDERGRADUATE("ugtstudent"),
  POSTGRADUATE("pgtstudent"),
  STAFF("staff");

  private final String samlReference;

  SamlRole(String samlReference) {
    this.samlReference = samlReference;
  }

  public static SamlRole getFromSamlReference(String samlReference) {
    return Arrays.stream(SamlRole.values())
        .filter(samlRole -> samlRole.samlReference.equals(samlReference))
        .findFirst()
        .orElseThrow(() -> new EnumNotFoundException(String.format("Unable to find SamlRole with reference [%s]", samlReference)));
  }
}
