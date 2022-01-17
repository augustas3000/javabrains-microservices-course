package com.way2java.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingsController {

    @Value("${my.greeting: default value}")
    private String greetingMessage;

    @Value("some static message")
    private String staticMessage;

    @Value("${app.description}")
    private String descMessage;

    @Value("${my.list.values}")
    private List<String> listValues;

//    should work but doesnt? investigate
//    @Value("#{$dbValues}}")
//    private Map<String, String> dbValues;

    @GetMapping("/greeting")
    public String greeting() {
        return (greetingMessage + " " + staticMessage);
    }

    @GetMapping("/list-values")
    public List<String> listOfValuesFromProperties() {
        return listValues;
    }

//    @GetMapping("/list-db-values")
//    public Map<String, String> listOfDbValuesFromProperties() {
//        return dbValues;
//    }
}
