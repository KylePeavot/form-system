package co600.weffs.application.internal.model.form;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "question")
@Data
public class Question {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @JoinColumn(name = "form_detail_id")
        private FormDetail formDetail;

        private Instant createdTimestamp;
        private String createdBy;
}


