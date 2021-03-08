package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.services.flowable.FormWorkflowService;
import co600.weffs.application.internal.services.form.FormService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {

    private FormWorkflowService formWorkflowService;

    @Autowired
    public DashboardController(FormWorkflowService formWorkflowService) {
        this.formWorkflowService = formWorkflowService;
    }

    @GetMapping
    public Map<String, ?> getDashboardHello() {
        return Map.of("id", 1);
    }

    @GetMapping("/test")
    public String getWorldMessage() {
        return "World";
    }

    @GetMapping("/custom/{message}")
    public String getCustomMessage(@PathVariable("message") String message) {
        return message;
    }

    @MustBeAuthorized
    @GetMapping("/assigned-tasks")
    public Map<String, ?> getAssignedTasks(@RequestAttribute("User") AppUser appUser) {
        //formWorkflowService.assignFormToFormFiller(new AppUser("ca469", "ksp5", SamlRole.STAFF), "ksp5", );
        return formWorkflowService.getAllAssignedFormViewsForAssignee(appUser.getUsername()).stream()
            .collect(Collectors.toMap(
                assignedFormView  -> String.valueOf(assignedFormView.hashCode()),
                assignedFormView -> assignedFormView)
            );
    }
}
