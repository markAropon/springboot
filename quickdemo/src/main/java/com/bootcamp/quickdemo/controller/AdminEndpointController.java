package com.bootcamp.quickdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.quickdemo.common.RateLimit;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/admin")
public class AdminEndpointController {
    @RateLimit(limit = 3, durationSeconds = 15)
    @Operation(summary = "Get admin resources", description = "Retrieve a list of admin resources")
    @GetMapping("/get")
    public String ShowUserAccess() {
        return "You have access to the following resources: ";
    }
}
