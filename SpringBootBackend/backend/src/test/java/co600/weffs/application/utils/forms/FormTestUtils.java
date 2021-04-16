package co600.weffs.application.utils.forms;

import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;

import co600.weffs.application.internal.model.team.Team;
import java.time.Instant;

public class FormTestUtils {
        private static int formId = 0;
        private static int formDetailId = 0;
        public static Form createBasicForm() {
            Form form = new Form();
            form.setId(formId++);
            form.setCreatedBy("test_user");
            form.setCreatedTimestamp(Instant.now());
            return form;
        }
        public static FormDetail createBasicFormDetail() {
          return createBasicFormDetail(createBasicForm());
        }
        public static FormDetail createBasicFormDetail(Form form) {
            FormDetail formDetail = new FormDetail();
            formDetail.setId(formDetailId++);
            formDetail.setName("The Name");
            formDetail.setForm(form);
            formDetail.setTeam(new Team());
            formDetail.setLastUpdatedBy("test_user");
            formDetail.setLastUpdatedTimestamp(Instant.now());
            formDetail.setStatusControl(true);
            return formDetail;
        }
    }
