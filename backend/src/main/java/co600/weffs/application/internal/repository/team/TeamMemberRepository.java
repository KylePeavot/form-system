package co600.weffs.application.internal.repository.team;

import co600.weffs.application.internal.model.team.TeamDetail;
import co600.weffs.application.internal.model.team.TeamMember;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember, Integer> {

  List<TeamMember> findAllByUsernameAndStatusControlIsTrue(String username);

  List<TeamMember> findAllByTeamDetailInAndStatusControlIsTrue(Collection<TeamDetail> teamDetails);

  Optional<TeamMember> findByUsernameAndTeamDetail(String username, TeamDetail teamDetail);

  List<TeamMember> findAllByTeamDetail_Team_IdAndStatusControlIsTrue(Integer teamId);
}
