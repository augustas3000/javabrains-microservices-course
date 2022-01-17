package com.way2java.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @Value("${my.greeting}")
    private String greetingMessage;

    @Value("${app.description}")
    private String descMessage;

    @GetMapping("/greeting")
    public String greeting() {
        return descMessage;
    }
}
