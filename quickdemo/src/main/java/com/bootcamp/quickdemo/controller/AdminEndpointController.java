package com.bootcamp.quickdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/admin")
public class AdminEndpointController {
    @Operation(summary = "Get admin resources",description = "Retrieve a list of admin resources")
    @GetMapping("/get")
    public String ShowUserAccess() {
        return "You have access to the following resources: ";
    }
}
