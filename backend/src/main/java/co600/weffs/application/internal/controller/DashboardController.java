package co600.weffs.application.internal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class DashboardController {

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

}
