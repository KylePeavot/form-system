package co600.weffs.application.internal.services.flowable;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.flowable.AssignedFormView;
import co600.weffs.application.internal.model.flowable.WorkflowTask;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.services.form.FormDetailService;
import co600.weffs.application.internal.services.form.FormService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO FS-52 Update this class to use the id of a (new object called) FormSubmission
@Service
public class FormWorkflowService {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private FormService formService;

  @Autowired
  private FormDetailService formDetailService;

  @Autowired
  public FormWorkflowService(RuntimeService runtimeService, TaskService taskService, FormService formService, FormDetailService formDetailService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
    this.formService = formService;
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

  public List<AssignedFormView> getAllAssignedFormViewsForAssignee(String assignee) {
    return getAllAssignedTasksForAssignee(assignee).stream()
      .map(task -> {
        Form form = formService.getFormById((Integer) taskService.getVariables(task.getId()).get("formId"));
        return new AssignedFormView(
            form,
            formDetailService.getFormDetailByForm(form),
            WorkflowTask.getWorkflowTaskFromTaskName(task.getName())
        );
      })
      .sorted(Comparator.comparing(o -> o.getFormDetail().getLastUpdatedTimestamp()))
      .collect(Collectors.toList());
  }

  public boolean isFormAssignedToUser(String assignee, int formId) {
    return getAssignedTaskForForm(assignee, formId) != null;
  }
}
