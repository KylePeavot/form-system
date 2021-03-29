package co600.weffs.application.internal.controller.flowable;

import co600.weffs.application.internal.model.flowable.frontend.FrontendAssignWorkflowVariables;
import co600.weffs.application.internal.model.flowable.frontend.FrontendDeleteWorkflowVariables;
import co600.weffs.application.internal.model.flowable.frontend.FrontendSubmitWorkflowVariables;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.flowable.FormWorkflowService;
import co600.weffs.application.internal.services.form.FormDetailService;
import co600.weffs.application.internal.services.form.FormService;
import co600.weffs.application.internal.services.formResponse.FormResponseDetailService;
import co600.weffs.application.internal.services.formResponse.FormResponseService;
import co600.weffs.application.internal.services.team.TeamDetailService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/flowable/workflow/form")
public class FormWorkflowController {

  private FormWorkflowService formWorkflowService;

  private FormService formService;

  private FormResponseService formResponseService;

  private FormDetailService formDetailService;

  private FormResponseDetailService formResponseDetailService;

  private TeamDetailService teamDetailService;

  @Autowired
  public FormWorkflowController(
      FormWorkflowService formWorkflowService,
      FormService formService,
      FormResponseService formResponseService,
      FormDetailService formDetailService,
      FormResponseDetailService formResponseDetailService,
      TeamDetailService teamDetailService) {
    this.formWorkflowService = formWorkflowService;
    this.formService = formService;
    this.formResponseService = formResponseService;
    this.formDetailService = formDetailService;
    this.formResponseDetailService = formResponseDetailService;
    this.teamDetailService = teamDetailService;
  }

  @MustBeAuthorized
  @PostMapping("/start")
  public void assignFormToUser(@RequestBody FrontendAssignWorkflowVariables frontendAssignWorkflowVariables) {

    var formDetail = formDetailService.getFormDetailById(frontendAssignWorkflowVariables.get_formDetailId());
    var latestTeamDetail = teamDetailService.getActiveTeamDetailByTeamId(formDetail.getTeam().getId());

    String assigner = frontendAssignWorkflowVariables.get_assigner();
    List<FormResponse> responses = Arrays.stream(frontendAssignWorkflowVariables.get_targetUsers())
        .map(filler ->
            formResponseService.create(
                filler,
                assigner,
                latestTeamDetail,
                formDetail))
        .collect(Collectors.toList());

    formResponseDetailService.create(responses);

    responses.forEach(formResponse ->
        formWorkflowService.assignFormToFormFiller(assigner, formResponse.getAssignedTo(), formResponse));
  }

  @MustBeAuthorized
  @PostMapping("/delete")
  public void deleteForm(@RequestBody FrontendDeleteWorkflowVariables frontendDeleteWorkflowVariables) {
    formWorkflowService.deleteFormResponse(
        frontendDeleteWorkflowVariables.get_fillerUsername(),
        formResponseService.getFormResponseById(frontendDeleteWorkflowVariables.get_formResponseId())
    );
  }

  @MustBeAuthorized
  @PostMapping("submit")
  public void submitForm(@RequestBody FrontendSubmitWorkflowVariables frontendSubmitWorkflowVariables) {
    formWorkflowService.submitFormResponse(
      frontendSubmitWorkflowVariables.get_fillerUsername(),
      formResponseService.getFormResponseById(frontendSubmitWorkflowVariables.get_formResponseId())
    );
  }
}
