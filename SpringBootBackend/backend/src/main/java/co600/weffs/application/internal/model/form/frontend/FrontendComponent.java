package co600.weffs.application.internal.model.form.frontend;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class FrontendComponent {

    private String _componentType;
    private Map<String,Object> _componentProps;
    private Integer _order;

}
