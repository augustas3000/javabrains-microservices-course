package com.way2java.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @Value("${test-properties.not-overwritten-by-higher-profiles}")
    private String commonToAllProfiles;

    @Value("${test-properties.overwritten-by-higher-profiles}")
    private String differentIfOverwritten;

    @Value("${spring.profiles.active: default}")
    private String activeProfiles;

    @GetMapping("/config-server-results")
    public String configServerResults() {
        return "Active profiles are: " + activeProfiles + "\n and common value to all profiles: " + commonToAllProfiles + "\n"
                + " differentIfOverwrittent: " + differentIfOverwritten;
    }

}
