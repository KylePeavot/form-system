package co600.weffs.application.internal.repository.team;

import co600.weffs.application.internal.model.team.TeamDetail;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamDetailRepository extends CrudRepository<TeamDetail, Integer> {

  Optional<TeamDetail> findByTeam_IdAndStatusControlIsTrue(Integer teamId);

}
