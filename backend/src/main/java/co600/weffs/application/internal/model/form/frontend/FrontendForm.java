package co600.weffs.application.internal.model.form.frontend;

import lombok.Data;

import java.util.List;

@Data
public class FrontendForm {

    private String _name;
    private List<FrontendComponent> _componentList;

}
