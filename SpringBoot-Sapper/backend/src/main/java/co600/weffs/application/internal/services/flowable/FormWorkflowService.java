package co600.weffs.application.internal.services.flowable;

import co600.weffs.application.internal.model.flowable.AssignedFormView;
import co600.weffs.application.internal.model.flowable.WorkflowTask;
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.services.formResponse.FormResponseService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormWorkflowService {

  private RuntimeService runtimeService;

  private TaskService taskService;

  private FormResponseService formResponseService;

  @Autowired
  public FormWorkflowService(RuntimeService runtimeService, TaskService taskService,
      FormResponseService formResponseService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
    this.formResponseService = formResponseService;
  }

  @Transactional
  public void assignFormToFormFiller(String assigner, String filler, FormResponse formResponse) {
    // start the process
    Map<String, Object> variables = Map.of(
        "assigner", assigner,
        "filler", filler,
        "formResponseId", formResponse.getId(),
        "formId", formResponse.getFormDetail().getForm().getId()
    );

    //if the form is already assigned to the filler, return
    if (isFormResponseAssignedToUser(filler, formResponse.getFormDetail().getForm().getId())) {
      return;
    }

    runtimeService.startProcessInstanceByKey("formWorkflow", variables);
  }

  public void deleteFormResponse(String filler, FormResponse formResponseToDelete) {
    //get task for the user with the formToDelete
    Task task = getAssignedTaskForFormResponse(filler, formResponseToDelete.getId());

    Map<String, Object> variables = Map.of("nextStage", WorkflowTask.DELETE_FORM.getTaskName());

    //check that the task is in the "fillingForm" stage
    //if it is, transition it with variables
    if (task != null && task.getName().equals(WorkflowTask.FILL_FORM.getTaskName())) {
      taskService.complete(task.getId(), variables);
    }
  }

  public void submitFormResponse(String filler, FormResponse formResponseToSubmit) {
    Task task = getAssignedTaskForFormResponse(filler, formResponseToSubmit.getId());

    Map<String, Object> variables = Map.of("nextStage", WorkflowTask.FORM_SUBMITTED.getTaskName());

    if (task != null && task.getName().equals(WorkflowTask.FILL_FORM.getTaskName())) {
      taskService.complete(task.getId(), variables);
    }
  }

  public Task getAssignedTaskForFormResponse(String assignee, int formResponseId) {
    return taskService.createTaskQuery().list().stream()
        .filter(taskToReturn -> {
          Map<String, Object> processVariables = taskService.getVariables(taskToReturn.getId());
          return assignee.equals(taskToReturn.getAssignee()) && processVariables
              .get("formResponseId").equals(formResponseId);
        })
        .findFirst()
        .orElse(null);
  }

  public Task getAssignedTasksForFormId(String assignee, int formId) {
    return taskService.createTaskQuery().list().stream()
        .filter(taskToReturn -> {
          Map<String, Object> processVariables = taskService.getVariables(taskToReturn.getId());
          return assignee.equals(taskToReturn.getAssignee()) && processVariables
              .get("formId").equals(formId);
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
          FormResponse formResponse = formResponseService.getFormResponseById(
              (Integer) taskService.getVariables(task.getId()).get("formResponseId"));
          return new AssignedFormView(
              formResponse,
              WorkflowTask.getWorkflowTaskFromTaskName(task.getName())
          );
        })
        .sorted(Comparator.comparing(o -> o.getFormResponse().getAssignedTimestamp()))
        .collect(Collectors.toList());
  }

  public boolean isFormResponseAssignedToUser(String assignee, int formId) {
    return getAssignedTasksForFormId(assignee, formId) != null;
  }
}
