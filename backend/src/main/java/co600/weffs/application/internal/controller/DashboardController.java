package co600.weffs.application.internal.controller;

import co600.weffs.application.internal.model.auth.AppUser;
import co600.weffs.application.internal.security.jwt.MustBeAuthorized;
import co600.weffs.application.internal.service.flowable.FormWorkflowService;
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

    @Autowired
    private FormWorkflowService formWorkflowService;

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
        return formWorkflowService.getAllFormInTaskForAssignee(appUser.getUsername()).stream()
            .collect(Collectors.toMap(formInTask -> formInTask.getForm().getId().toString(), formInTask -> formInTask));
    }

}
