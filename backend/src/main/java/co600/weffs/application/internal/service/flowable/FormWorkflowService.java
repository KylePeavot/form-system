package co600.weffs.application.internal.service.flowable;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.error.EntityNotFoundException;
import co600.weffs.application.internal.model.error.WorkflowTaskNotFound;
import co600.weffs.application.internal.model.form.Form;
import java.util.HashMap;
import java.util.Map;
import org.flowable.task.api.Task;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormWorkflowService {

  @Autowired
  RuntimeService runtimeService;

  @Autowired
  TaskService taskService;

  public void assignFormToFormFiller(AppUser assigner, AppUser filler, Form form) {
    // start the process
    Map<String, Object> variables = new HashMap<>();
    variables.put("currentAssignee", assigner.getUsername());
    variables.put("formId", form.getId());

    runtimeService.startProcessInstanceByKey("formWorkflow", variables);

    //Find the task that has been created
    Task task = taskService.createTaskQuery().list().stream()
      .filter(taskToReturn -> {
        Map<String, Object> processVariables = taskService.getVariables(taskToReturn.getId());
        return processVariables.get("currentAssignee").equals(assigner.getUsername()) && processVariables.get("formId").equals(form.getId());
      })
      .findFirst()
      .orElseThrow(() -> new WorkflowTaskNotFound("Task with assignee: " + assigner.getUsername() + " and formId: " + form.getId()));

    //Set the assignee to be the form filler
    task.setAssignee(filler.getUsername());
    //transition the task to the next task
    taskService.complete(task.getId());
  }




}
