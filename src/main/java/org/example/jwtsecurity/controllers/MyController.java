package org.example.jwtsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Public Hello!";
    }

    @GetMapping("/secured")
    public String securedHello() {
        return "Secured Hello!";
    }
}
