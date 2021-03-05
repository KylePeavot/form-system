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
    private String name;
    private Instant lastUpdatedTimestamp;
    private String lastUpdatedBy;
    private Boolean statusControl;

}
