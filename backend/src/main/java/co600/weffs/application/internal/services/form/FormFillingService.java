package co600.weffs.application.internal.services.form;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.model.formResponse.FormResponseDetail;
import co600.weffs.application.internal.services.team.TeamMemberService;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class FormFillingService {

  TeamMemberService teamMemberService;


  public void sendForm(AppUser assigner, String fillerUserName, Form formToSend) {
    //take assigner, filler, and form
    //create new form response and detail for the current user
    //call formworkflowservice with above



  }

  public void createFormResponse(String assigner, String filler, Form form) {
    FormResponse formResponse = new FormResponse();

    formResponse.setAssignedTo(filler);
    formResponse.setAssignedTimestamp(Instant.now());

    FormResponseDetail formResponseDetail = new FormResponseDetail();

    formResponseDetail.setFormResponse(formResponse);
    formResponseDetail.setStatusControl(true);
    formResponseDetail.setLastUpdatedTimestamp(Instant.now());
//    formResponseDetail.setAssigner(teamMemberService.assigner);
  }

}
