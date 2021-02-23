package co600.weffs.application.internal.model.form;


import lombok.Data;
import java.util.Map;

@Data
public class FrontendComponent {

    private String _componentType;
    private Map<String,?> _componentProps;
    private Integer _order;

}
