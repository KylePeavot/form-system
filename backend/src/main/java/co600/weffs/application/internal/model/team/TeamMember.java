package co600.weffs.application.internal.model.team;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "team_member")
public class TeamMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "team_detail_id")
  private TeamDetail teamDetail;

  private String username;
  private Boolean canModifyForms;
  private Boolean canManageTeam;
  private Boolean statusControl;
  private String createdBy;
  private Instant createdTimestamp;

}
