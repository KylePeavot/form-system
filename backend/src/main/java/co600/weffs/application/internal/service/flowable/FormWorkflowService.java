package co600.weffs.application.internal.service.flowable;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.flowable.AssignedFormView;
import co600.weffs.application.internal.model.flowable.WorkflowTask;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.services.FormDetailService;
import co600.weffs.application.internal.services.FormService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
  private FormDetailService formDetailService;

  public FormWorkflowService(RuntimeService runtimeService, TaskService taskService, FormDetailService formDetailService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
    this.formDetailService = formDetailService;
  }

  public void assignFormToFormFiller(AppUser assigner, String filler, Form form) {
    // start the process
    Map<String, Object> variables = Map.of(
      "assigner", assigner.getUsername(),
      "filler", filler,
      "formId", form.getId()
    );

    //if the form is already assigned to the filler, return
    if (isFormAssignedToUser(filler, form.getId())) {
      return;
    }

    runtimeService.startProcessInstanceByKey("formWorkflow", variables);

    //Find the task that has been created
    Task task = getAssignedTaskForForm(assigner.getUsername(), form.getId());

    //transition the task to the next task
    taskService.complete(task.getId());
  }

  public void deleteForm(AppUser user, Form formToDelete) {
    //get task for the user with the formToDelete
    Task task = getAssignedTaskForForm(user.getUsername(), formToDelete.getId());

    Map<String, Object> variables = Map.of("nextStage", WorkflowTask.DELETE_FORM.getTaskName());

    //check that the task is in the "fillingForm" stage
    //if it is, transition it with variables
    if(task != null && task.getName().equals(WorkflowTask.FILL_FORM.getTaskName())) {
      taskService.complete(task.getId(), variables);
    }
  }

  public void submitForm(AppUser user, Form formToSubmit) {
    Task task = getAssignedTaskForForm(user.getUsername(), formToSubmit.getId());

    Map<String, Object> variables = Map.of("nextStage", WorkflowTask.FORM_SUBMITTED.getTaskName());

    if(task != null && task.getName().equals(WorkflowTask.FILL_FORM.getTaskName())) {
      taskService.complete(task.getId(), variables);
    }
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

  public List<AssignedFormView> getAllFormInTaskForAssignee(String assignee) {
    return getAllAssignedTasksForAssignee(assignee).stream()
      .map(task -> {
        Form form = formService.getFormById((Integer) taskService.getVariables(task.getId()).get("formId"));
        return new AssignedFormView(
            form,
            formDetailService.getFormDetailByForm(form),
            WorkflowTask.getWorkflowTaskFromTaskName(task.getName())
        );
      })
      .collect(Collectors.toList());
  }

  public boolean isFormAssignedToUser(String assignee, int formId) {
    return getAssignedTaskForForm(assignee, formId) != null;
  }
}
