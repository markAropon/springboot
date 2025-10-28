package com.bootcamp.quickdemo.services.impl;

import org.springframework.stereotype.Service;

@Service
public class TestingAspectLogging {
    public void testLogging() {
        System.out.println("Hello from TestingAspectLogging service!");
    }
}
