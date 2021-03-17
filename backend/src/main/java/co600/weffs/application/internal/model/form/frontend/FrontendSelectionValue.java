package co600.weffs.application.internal.model.form.frontend;

import java.util.LinkedHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrontendSelectionValue {

  private String _label;
  private boolean _value;
  private int _questionDetailId;

  public static FrontendSelectionValue fromLinkedHashMap(LinkedHashMap<String, Object> linkedHashMap) {
    return new FrontendSelectionValue(
        (String) linkedHashMap.get(FrontendComponentProps.LABEL.getFrontendName()),
        (Boolean) linkedHashMap.get(FrontendComponentProps.VALUE.getFrontendName()),
        (Integer) linkedHashMap.get(FrontendComponentProps.QUESTION_DETAIL_ID.getFrontendName())
    );
  }
}
