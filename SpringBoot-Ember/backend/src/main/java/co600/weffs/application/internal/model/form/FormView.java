package co600.weffs.application.internal.model.form;


import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Setter(AccessLevel.PRIVATE)
public class FormView {
    private Integer formDetailId;
    private String name;
    private String createdBy;
    private String createdWhen;
    private String lastUpdatedBy;
    private String lastUpdatedWhen;
    private Integer teamId;

    private Boolean canModifyForm;

    public FormView(FormDetail formDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        this.formDetailId = formDetail.getId();
        this.name = formDetail.getName();
        this.createdBy = formDetail.getForm().getCreatedBy();
        this.createdWhen = formatter.format(Date.from(formDetail.getForm().getCreatedTimestamp()));
        this.lastUpdatedBy = formDetail.getLastUpdatedBy();
        this.lastUpdatedWhen = formatter.format(Date.from(formDetail.getLastUpdatedTimestamp()));
        this.teamId = formDetail.getTeam().getId();
        this.canModifyForm = false;
    }

    public FormView(FormView formView, Boolean canModifyForm) {
        this.formDetailId = formView.formDetailId;
        this.name = formView.name;
        this.createdBy = formView.createdBy;
        this.createdWhen = formView.createdWhen;
        this.lastUpdatedBy = formView.lastUpdatedBy;
        this.lastUpdatedWhen = formView.lastUpdatedWhen;
        this.teamId = formView.teamId;
        this.canModifyForm = canModifyForm;
    }

}
