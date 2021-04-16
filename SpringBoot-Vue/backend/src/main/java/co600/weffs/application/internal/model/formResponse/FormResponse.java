package co600.weffs.application.internal.model.formResponse;

import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
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


  @ManyToOne
  @JoinColumn(name = "assigner_team_member_id")
  private TeamMember assignedBy;

  @ManyToOne
  @JoinColumn(name = "assigner_team_detail_id")
  private TeamDetail assignedByTeamDetail;

  private String assignedTo;

  private Instant assignedTimestamp;

}
