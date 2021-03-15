package co600.weffs.application.internal.model.form;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class FormView {
    private Integer id;
    private String name;
    private String createdBy;
    private String createdWhen;
    private String lastUpdatedBy;
    private String lastUpdatedWhen;

    public FormView(FormDetail formDetail){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        this.id = formDetail.getForm().getId();
        this.name = formDetail.getName();
        this.createdBy = formDetail.getForm().getCreatedBy();
        this.createdWhen = formatter.format(Date.from(formDetail.getForm().getCreatedTimestamp()));
        this.lastUpdatedBy = formDetail.getLastUpdatedBy();
        this.lastUpdatedWhen= formatter.format(Date.from(formDetail.getLastUpdatedTimestamp()));
    }
}
