package co600.weffs.application.internal.model.form;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "form_detail")
@Data
public class FormDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;
    //TODO FS-65 add name field
    private Instant lastUpdatedTimestamp;
    private String lastUpdatedBy;
    private Boolean statusControl;
}
