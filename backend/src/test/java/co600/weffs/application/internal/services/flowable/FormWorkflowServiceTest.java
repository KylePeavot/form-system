package co600.weffs.application.internal.services.flowable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co600.weffs.application.MockitoTest;
import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.auth.SamlRole;
import co600.weffs.application.internal.model.flowable.AssignedFormView;
import co600.weffs.application.internal.model.flowable.WorkflowTask;
import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;
import co600.weffs.application.internal.services.FormDetailService;
import co600.weffs.application.internal.services.FormService;
import co600.weffs.application.utils.UserTestUtils;
import java.time.Instant;
import java.time.Period;
import java.util.List;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.Deployment;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

//@ExtendWith(FlowableSpringExtension.class)
@SpringBootTest
class FormWorkflowServiceTest extends MockitoTest {

  @SpyBean
  private RuntimeService runtimeService;

  @SpyBean
  private TaskService taskService;

  @Mock
  private FormService formService;

  @Mock
  private FormDetailService formDetailService;

  private FormWorkflowService formWorkflowService;

  private AppUser assigner;
  private AppUser filler;
  private Form testFormA;

  @BeforeEach
  void setUp() {
    this.formWorkflowService = new FormWorkflowService(runtimeService, taskService, formService, formDetailService);

    this.assigner = UserTestUtils.createAppUser("assigner", SamlRole.STAFF);
    this.filler = UserTestUtils.createAppUser("filler", SamlRole.UNDERGRADUATE);

    this.testFormA = new Form();
    this.testFormA.setId(1);
  }

  @AfterEach
  void tearDown() {
    List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();

    for (ProcessInstance processInstance : processInstances) {
      //Just a PSA, the second parameter is just "delete reason" according to flowable's github repo
      runtimeService.deleteProcessInstance(processInstance.getId(), "tearDown");
    }
  }

  @Test
  @Deployment(resources = "processes/form-workflow.bpmn20.xml")
  public void getAssignedTaskForForm_retrievesCorrectTask() {
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormA);

    Task task = taskService.createTaskQuery().singleResult();

    assertThat(task.getName()).isEqualTo(WorkflowTask.FILL_FORM.getTaskName());

    assertThat(task.getAssignee()).isEqualTo(filler.getUsername());

    assertThat(taskService.getVariables(task.getId())).containsEntry("formId", testFormA.getId());
  }

  @Test
  @Deployment(resources = "processes/form-workflow.bpmn20.xml")
  public void getAllAssignedTasksForAssignee_getsAllAssignedTasks() {
    AppUser newAssigner = UserTestUtils.createAppUser("newAssigner", SamlRole.STAFF);

    Form newFormForFiller = new Form();
    newFormForFiller.setId(2);
    //Assign form to assigner
    formWorkflowService.assignFormToFormFiller(newAssigner, assigner.getUsername(), testFormA);
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormA);
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), newFormForFiller);

    assertThat(taskService.createTaskQuery().list().size()).isEqualTo(3);

    assertThat(formWorkflowService.getAllAssignedTasksForAssignee(filler.getUsername()).size()).isEqualTo(2);
  }

  @Test
  @Deployment(resources = "processes/form-workflow.bpmn20.xml")
  public void getAllAssignedFormViewsForAssignee_getsAllAssignedTasks() {
    AppUser newAssigner = UserTestUtils.createAppUser("newAssigner", SamlRole.STAFF);

    FormDetail testFormDetailA = new FormDetail();
    testFormDetailA.setForm(testFormA);
    testFormDetailA.setLastUpdatedTimestamp(Instant.now().minus(Period.ofDays(1)));

    Form testFormB = new Form();
    testFormB.setId(2);

    FormDetail testFormDetailB = new FormDetail();
    testFormDetailB.setForm(testFormA);
    testFormDetailB.setLastUpdatedTimestamp(Instant.now());

    when(formDetailService.getFormDetailByForm(testFormA)).thenReturn(testFormDetailA);
    when(formDetailService.getFormDetailByForm(testFormB)).thenReturn(testFormDetailB);
    when(formService.getFormById(1)).thenReturn(testFormA);
    when(formService.getFormById(2)).thenReturn(testFormB);

    formWorkflowService.assignFormToFormFiller(newAssigner, assigner.getUsername(), testFormA);
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormA);
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormB);

    assertThat(taskService.createTaskQuery().list().size()).isEqualTo(3);

    List<AssignedFormView> assignedFormViews = formWorkflowService.getAllAssignedFormViewsForAssignee(filler.getUsername());

    assertThat(assignedFormViews.size()).isEqualTo(2);

    assertThat(assignedFormViews.get(0).getForm()).isEqualTo(testFormA);
    assertThat(assignedFormViews.get(0).getFormDetail()).isEqualTo(testFormDetailA);
    assertThat(assignedFormViews.get(0).getWorkflowTask()).isEqualTo(WorkflowTask.FILL_FORM);

    assertThat(assignedFormViews.get(1).getForm()).isEqualTo(testFormB);
    assertThat(assignedFormViews.get(1).getFormDetail()).isEqualTo(testFormDetailB);
    assertThat(assignedFormViews.get(1).getWorkflowTask()).isEqualTo(WorkflowTask.FILL_FORM);
  }

  @Test
  public void assignFormToFormFiller_startsProcessSuccessfully() {
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormA);

    assertThat(runtimeService.createProcessInstanceQuery().count()).isEqualTo(1);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(1);
  }

  @Test
  public void assignFormToFormFiller_doesNotStartTwoIdenticalProcesses() {
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormA);
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormA);

    assertThat(runtimeService.createProcessInstanceQuery().count()).isEqualTo(1);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(1);
  }

  @Test
  public void deleteForm_removesInProgressTaskSuccessfully() {
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormA);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(1);

    formWorkflowService.deleteForm(filler, testFormA);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(0);
  }

  @Test
  public void deleteForm_doesNothingIfNoTaskExists() {
    formWorkflowService.deleteForm(filler, testFormA);
    verify(taskService, never()).complete(any(), any());
  }

  @Test
  public void submitForm_submitsInProgressTaskSuccessfully() {
    formWorkflowService.assignFormToFormFiller(assigner, filler.getUsername(), testFormA);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(1);

    formWorkflowService.submitForm(filler, testFormA);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(0);
  }

  @Test
  public void submitForm_doesNothingIfNoTaskExists() {
    formWorkflowService.submitForm(filler, testFormA);
    verify(taskService, never()).complete(any(), any());
  }
}