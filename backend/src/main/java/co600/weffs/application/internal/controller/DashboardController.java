package co600.weffs.application.internal.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class DashboardController {

    @GetMapping
    public String getDashboardHello() {
        return "Something else";
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
