package co600.weffs.application.internal.model.formResponse;

import co600.weffs.application.internal.model.form.FormDetail;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table
@Entity
public class FormResponse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "form_detail_id")
  private FormDetail formDetail;

  private String assignedTo;

  private Instant assignedTimestamp;

}
