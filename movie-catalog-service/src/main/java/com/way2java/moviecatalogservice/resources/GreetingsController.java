package com.way2java.moviecatalogservice.resources;

import com.way2java.moviecatalogservice.properties.DbProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingsController {

    @Value("${my.greeting: default value}")
    private String greetingMessage;

    @Value("some static message")
    private String staticMessage;

    @Value("${my.app.description}")
    private String descMessage;

    @Value("${my.list.values}")
    private List<String> listValues;

//    should work but doesnt? investigate
//    @Value("#{$dbValues}}")
//    private Map<String, String> dbValues;

    @Autowired
    private DbProperties dbProperties;

    @GetMapping("/greeting")
    public String greeting() {
        return (greetingMessage + " " + staticMessage);
    }

    @GetMapping("/list-values")
    public List<String> listOfValuesFromProperties() {
        return listValues;
    }

    @GetMapping("/list-db-values")
    public String listOfDbValuesFromProperties() {
        return dbProperties.getConnection() + " " + dbProperties.getPort();
    }
}
