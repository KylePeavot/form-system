package co600.weffs.application.internal.model.form;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@Data
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Instant createdTimestamp;
    private String createdBy;


}
