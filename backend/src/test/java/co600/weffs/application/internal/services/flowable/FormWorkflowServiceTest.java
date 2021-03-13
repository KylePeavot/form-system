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
import co600.weffs.application.internal.model.formResponse.FormResponse;
import co600.weffs.application.internal.services.form.FormDetailService;
import co600.weffs.application.internal.services.form.FormService;
import co600.weffs.application.internal.services.formResponse.FormResponseService;
import co600.weffs.application.utils.UserTestUtils;
import java.time.Instant;
import java.time.Period;
import java.util.List;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.Deployment;
import org.flowable.spring.impl.test.FlowableSpringExtension;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@ExtendWith(FlowableSpringExtension.class)
@SpringBootTest
class FormWorkflowServiceTest extends MockitoTest {

  @SpyBean
  private RuntimeService runtimeService;

  @SpyBean
  private TaskService taskService;

  @Mock
  private FormResponseService formResponseService;

  private FormWorkflowService formWorkflowService;

  private AppUser assigner;
  private AppUser filler;
  private Form testFormA;
  private FormDetail testFormDetailA;
  private FormResponse testFormResponseA;

  @BeforeEach
  void setUp() {
    this.formWorkflowService = new FormWorkflowService(runtimeService, taskService, formResponseService);

    this.assigner = UserTestUtils.createAppUser("assigner", SamlRole.STAFF);
    this.filler = UserTestUtils.createAppUser("filler", SamlRole.UNDERGRADUATE);

    this.testFormA = new Form();
    this.testFormA.setId(1);

    this.testFormDetailA = new FormDetail();
    this.testFormDetailA.setForm(testFormA);

    this.testFormResponseA = new FormResponse();
    this.testFormResponseA.setFormDetail(testFormDetailA);
    this.testFormResponseA.setId(1);
    this.testFormResponseA.setAssignedTimestamp(Instant.now());
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
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseA);

    Task task = taskService.createTaskQuery().singleResult();

    assertThat(task.getName()).isEqualTo(WorkflowTask.FILL_FORM.getTaskName());

    assertThat(task.getAssignee()).isEqualTo(filler.getUsername());

    assertThat(taskService.getVariables(task.getId())).containsEntry("formResponseId", testFormResponseA.getId());
  }

  @Test
  @Deployment(resources = "processes/form-workflow.bpmn20.xml")
  public void getAllAssignedTasksForAssignee_getsAllAssignedTasks() {
    AppUser newAssigner = UserTestUtils.createAppUser("newAssigner", SamlRole.STAFF);

    Form testFormB = new Form();

    FormDetail testFormDetailB = new FormDetail();
    testFormDetailB.setForm(testFormB);

    FormResponse testFormResponseB = new FormResponse();
    testFormResponseB.setFormDetail(testFormDetailB);
    testFormResponseB.setId(2);

    Form testFormC = new Form();

    FormDetail testFormDetailC = new FormDetail();
    testFormDetailB.setForm(testFormC);

    FormResponse testFormResponseC = new FormResponse();
    testFormResponseB.setFormDetail(testFormDetailC);
    testFormResponseC.setId(3);

    //Assign form to assigner
    formWorkflowService.assignFormToFormFiller(newAssigner.getUsername(), assigner.getUsername(), testFormResponseA);
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseB);
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseC);

    assertThat(taskService.createTaskQuery().list().size()).isEqualTo(3);

    assertThat(formWorkflowService.getAllAssignedTasksForAssignee(filler.getUsername()).size()).isEqualTo(2);
  }

  @Test
  @Deployment(resources = "processes/form-workflow.bpmn20.xml")
  public void getAllAssignedFormViewsForAssignee_getsAllAssignedFormViews() {
    Form testFormB = new Form();

    FormDetail testFormDetailB = new FormDetail();
    testFormDetailB.setForm(testFormB);

    FormResponse testFormResponseB = new FormResponse();
    testFormResponseB.setFormDetail(testFormDetailB);
    testFormResponseB.setId(2);
    testFormResponseB.setAssignedTimestamp(Instant.now());

//    when(formDetailService.getFormDetailByForm(testFormA)).thenReturn(testFormDetailA);
//    when(formDetailService.getFormDetailByForm(testFormB)).thenReturn(testFormDetailB);
//    when(formService.getFormById(1)).thenReturn(testFormA);
//    when(formService.getFormById(2)).thenReturn(testFormB);

    when(formResponseService.getFormResponseById(1)).thenReturn(testFormResponseA);
    when(formResponseService.getFormResponseById(2)).thenReturn(testFormResponseB);

    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseA);
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseB);

    assertThat(taskService.createTaskQuery().list().size()).isEqualTo(2);

    List<AssignedFormView> assignedFormViews = formWorkflowService.getAllAssignedFormViewsForAssignee(filler.getUsername());

    assertThat(assignedFormViews.size()).isEqualTo(2);

    assertThat(assignedFormViews.get(0).getFormResponse()).isEqualTo(testFormResponseA);
    assertThat(assignedFormViews.get(0).getWorkflowTask()).isEqualTo(WorkflowTask.FILL_FORM);

    assertThat(assignedFormViews.get(1).getFormResponse()).isEqualTo(testFormResponseB);
    assertThat(assignedFormViews.get(1).getWorkflowTask()).isEqualTo(WorkflowTask.FILL_FORM);
  }

  @Test
  public void assignFormToFormFiller_startsProcessSuccessfully() {
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseA);

    assertThat(runtimeService.createProcessInstanceQuery().count()).isEqualTo(1);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(1);
  }

  @Test
  public void assignFormToFormFiller_doesNotStartTwoIdenticalProcesses() {
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseA);
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseA);

    assertThat(runtimeService.createProcessInstanceQuery().count()).isEqualTo(1);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(1);
  }

  @Test
  public void deleteForm_removesInProgressTaskSuccessfully() {
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseA);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(1);

    formWorkflowService.deleteFormResponse(filler.getUsername(), testFormResponseA);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(0);
  }

  @Test
  public void deleteForm_doesNothingIfNoTaskExists() {
    formWorkflowService.deleteFormResponse(filler.getUsername(), testFormResponseA);
    verify(taskService, never()).complete(any(), any());
  }

  @Test
  public void submitForm_submitsInProgressTaskSuccessfully() {
    formWorkflowService.assignFormToFormFiller(assigner.getUsername(), filler.getUsername(), testFormResponseA);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(1);

    formWorkflowService.submitFormResponse(filler.getUsername(), testFormResponseA);

    assertThat(taskService.createTaskQuery().count()).isEqualTo(0);
  }

  @Test
  public void submitForm_doesNothingIfNoTaskExists() {
    formWorkflowService.submitFormResponse(filler.getUsername(), testFormResponseA);
    verify(taskService, never()).complete(any(), any());
  }
}