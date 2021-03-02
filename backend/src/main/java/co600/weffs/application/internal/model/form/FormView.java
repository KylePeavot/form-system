package co600.weffs.application.internal.model.form;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FormView {
    private Integer id;
    private String name;
    private String createdBy;
    private String createdWhen;
    private String lastUpdatedBy;
    private String lastUpdatedWhen;
}
