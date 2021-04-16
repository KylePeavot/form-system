package co600.weffs.application.internal.view.team;

import co600.weffs.application.internal.model.team.TeamMember;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamView {

  private Integer teamId;
  private String teamName;
  private List<TeamMember> teamMembers;

}
