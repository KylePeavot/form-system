package co600.weffs.application.utils.forms;

import co600.weffs.application.internal.model.form.Form;
import co600.weffs.application.internal.model.form.FormDetail;

import java.text.SimpleDateFormat;
import java.time.Instant;

public class FormTestUtils {
        private static int formId = 0;
        private static int formDetailId = 0;
        public static Form createBasicForm() {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Form form = new Form();
            form.setId(formId++);
            form.setCreatedBy("testUser");
            form.setCreatedTimestamp(Instant.now());
            return form;
        }
        public static FormDetail createBasicFormDetail(Form form) {
            FormDetail formDetail = new FormDetail();
            formDetail.setId(formDetailId++);
            //TODO FS-65 add a formDetail.setName();
            formDetail.setForm(form);
            formDetail.setLastUpdatedBy("testUser");
            formDetail.setLastUpdatedTimestamp(Instant.now());
            formDetail.setStatusControl(true);
            return formDetail;
        }
    }
