package co600.weffs.application.internal.services;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.model.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
public class FormCreationService {
    private final FormService formService;
    private final FormDetailService formDetailService;
    private final QuestionService questionService;
    private final QuestionDetailService questionDetailService;

    @Autowired
    public FormCreationService(FormService formService, FormDetailService formDetailService, QuestionService questionService, QuestionDetailService questionDetailService) {
        this.formService = formService;
        this.formDetailService = formDetailService;
        this.questionService = questionService;
        this.questionDetailService = questionDetailService;
    }

    @Transactional
    public void createForm(AppUser appUser, FrontendForm frontendForm) {
        var form = new Form();
        form.setCreatedBy(appUser.getUsername());
        form.setCreatedTimestamp(Instant.now());
        formService.save(form);

        var formDetail = new FormDetail();
        formDetail.setForm(form);
        formDetail.setLastUpdatedBy(appUser.getUsername());
        formDetail.setLastUpdatedTimestamp(Instant.now());
        formDetail.setStatusControl(true);
        formDetailService.save(formDetail);

        frontendForm.get_componentList().forEach(frontendComponent -> createQuestion(appUser, frontendComponent, formDetail));
    }

    void createQuestion(AppUser appUser, FrontendComponent frontendComponent, FormDetail formDetail) {

        var question = new Question();
        question.setCreatedBy(appUser.getUsername());
        question.setCreatedTimestamp(Instant.now());
        question.setFormDetail(formDetail);
        questionService.save(question);

        var questionDetail = new QuestionDetail();
        questionDetail.setGuidance((String) frontendComponent.get_componentProps().get("guidance"));
        questionDetail.setLastUpdatedBy(appUser.getUsername());
        questionDetail.setLastUpdatedTimestamp(Instant.now());
        questionDetail.setOrderNumber(frontendComponent.get_order());
        //TODO FS-38 make possible to reveal more questions depending on checkbox/radio state
//        questionDetail.setParentQuestion();
        questionDetail.setQuestion(question);
        questionDetail.setQuestionType(frontendComponent.get_componentType());
        questionDetail.setStatusControl(true);
        questionDetail.setTitle((String) frontendComponent.get_componentProps().get("title"));
        questionDetailService.save(questionDetail);
    }

}
