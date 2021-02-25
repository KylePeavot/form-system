package co600.weffs.application.internal.service.flowable;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.error.WorkflowTaskNotFound;
import co600.weffs.application.internal.model.flowable.FormInTask;
import co600.weffs.application.internal.model.flowable.FormReviewDecision;
import co600.weffs.application.internal.model.flowable.WorkflowTask;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.services.FormService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.api.Task;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormWorkflowService {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private FormService formService;

  public FormWorkflowService(RuntimeService runtimeService, TaskService taskService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
  }

  public void assignFormToFormFiller(AppUser assigner, AppUser filler, Form form) {
    // start the process
    Map<String, Object> variables = new HashMap<>();
    variables.put("assigner", assigner.getUsername());
    variables.put("filler", filler.getUsername());
    variables.put("formId", form.getId());

    //if the form is already assigned to the filler, return
    if (isFormAssignedToUser(filler.getUsername(), form.getId())) {
      return;
    }

    runtimeService.startProcessInstanceByKey("formWorkflow", variables);

    //Find the task that has been created
    Task task = getAssignedTaskForForm(assigner.getUsername(), form.getId());

    //transition the task to the next task
    taskService.complete(task.getId());
  }

  //when form is in a filler's dashboard, and they hit delete, the form is transitioned to delete
  public void deleteForm(AppUser user, Form formToDelete) {
    //get task for the user with the formToDelete
    //check that the task is in the "fillingForm" stage
    //if it is, transition it with variables

    Task task = getAssignedTaskForForm(user.getUsername(), formToDelete.getId());

    Map<String, Object> variables = Map.of("nextStage", WorkflowTask.DELETE_FORM.getTaskName());

    if(task != null && task.getName().equals(WorkflowTask.FILL_FORM.getTaskName())) {
      taskService.complete(task.getId(), variables);
    }
  }

  public void submitFormForReview(AppUser user, Form formToSubmit) {
    Task task = getAssignedTaskForForm(user.getUsername(), formToSubmit.getId());

    Map<String, Object> variables = Map.of("nextStage", WorkflowTask.REVIEWING_FORM.getTaskName());

    if(task != null && task.getName().equals(WorkflowTask.FILL_FORM.getTaskName())) {
      taskService.complete(task.getId(), variables);
    }
  }

  public void withdrawForm(AppUser user, Form formToWithdraw) {
    Task task = getAssignedTaskForForm(user.getUsername(), formToWithdraw.getId());

    Map<String, Object> variables = Map.of("nextStage", WorkflowTask.WITHDRAW_FORM.getTaskName());

    if(task != null && task.getName().equals(WorkflowTask.REVIEWING_FORM.getTaskName())) {
      taskService.complete(task.getId(), variables);
    }
  }

  public void completeReview(AppUser user, Form formToProcess, FormReviewDecision formReviewDecision) {
    Task task = getAssignedTaskForForm(user.getUsername(), formToProcess.getId());

    Map<String, Object> variables = Map.of("nextStage",
      formReviewDecision.equals(FormReviewDecision.APPROVED)
        ? WorkflowTask.FORM_APPROVED.getTaskName()
        : WorkflowTask.FORM_RETURNED_TO_FORM_FILLER.getTaskName()
    );

    if(task != null && task.getName().equals(WorkflowTask.REVIEWING_FORM.getTaskName())) {
      taskService.complete(task.getId(), variables);
    }

    //TODO implement review stuff FS-76
  }

  public Task getAssignedTaskForForm(String assignee, int formId) {
    return taskService.createTaskQuery().list().stream()
        .filter(taskToReturn -> {
          Map<String, Object> processVariables = taskService.getVariables(taskToReturn.getId());
          return assignee.equals(taskToReturn.getAssignee()) && processVariables.get("formId").equals(formId);
        })
        .findFirst()
        .orElse(null);
  }

  public List<Task> getAllAssignedTasksForAssignee(String assignee) {
    return taskService.createTaskQuery().list().stream()
        .filter(taskToReturn -> assignee.equals(taskToReturn.getAssignee()))
        .collect(Collectors.toList());
  }

  public List<FormInTask> getAllFormInTaskForAssignee(String assignee) {
    //TODO Possible bug where the assignee is also the filler so short term fix where we strip out duplicates FS-77
    return getAllAssignedTasksForAssignee(assignee).stream()
      .map(task -> new FormInTask(
          formService.getFormById((Integer) taskService.getVariables(task.getId()).get("formId")),
          WorkflowTask.getWorkflowTaskFromTaskName(task.getName())))
      .collect(Collectors.toList());
  }

  public boolean isFormAssignedToUser(String assignee, int formId) {
    return getAssignedTaskForForm(assignee, formId) != null;
  }
}
