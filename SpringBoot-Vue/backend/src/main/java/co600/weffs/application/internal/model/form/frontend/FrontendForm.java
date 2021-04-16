package co600.weffs.application.internal.model.form.frontend;

import co600.weffs.application.internal.view.team.TeamView;
import lombok.Data;

import java.util.List;

@Data
public class FrontendForm {

    private String _name;
    private TeamView _team;
    private List<FrontendComponent> _componentList;

}
