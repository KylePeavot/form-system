package co600.weffs.application.internal.model.form;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "question_detail")
@Data
public class QuestionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String title;
    private String guidance;
    private String questionType;

    @ManyToOne
    @JoinColumn(name = "parent_question_id")
    private Question parentQuestion;

    private Integer order;
    private Instant lastUpdatedTimestamp;
    private String lastUpdatedBy;
    private Boolean statusControl;
}
