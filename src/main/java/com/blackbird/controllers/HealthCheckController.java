package com.blackbird.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple rest controller to check system availability
 */

@RestController
@RequestMapping("/")
public class HealthCheckController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
