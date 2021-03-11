package co600.weffs.application.internal.model.formResponse;

import co600.weffs.application.internal.model.form.QuestionDetail;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.apache.ibatis.annotations.Many;

@Data
@Table
@Entity
public class QuestionResponse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "form_response_detail_id")
  private FormResponseDetail formResponseDetail;

  @ManyToOne
  @JoinColumn(name = "question_detail_id")
  private QuestionDetail questionDetail;

}
