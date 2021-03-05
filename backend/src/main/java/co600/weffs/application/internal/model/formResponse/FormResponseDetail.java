package co600.weffs.application.internal.model.formResponse;

import co600.weffs.application.internal.model.team.TeamMember;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.apache.ibatis.annotations.Many;

@Data
@Table
@Entity
public class FormResponseDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "form_response_id")
  private FormResponse formResponse;

  @ManyToOne
  @JoinColumn(name = "assigner_team_member")
  private TeamMember assigner;

  private Instant lastUpdatedTimestamp;

  private Boolean statusControl;



}
