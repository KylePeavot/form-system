package co600.weffs.application.internal.repository.team;

import co600.weffs.application.internal.model.team.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {

}
