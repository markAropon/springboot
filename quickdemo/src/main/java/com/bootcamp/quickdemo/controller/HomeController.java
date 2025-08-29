package com.bootcamp.quickdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.quickdemo.colors.SpanishBlue;
import com.bootcamp.quickdemo.colors.SpanishGreen;
import com.bootcamp.quickdemo.colors.SpanishRed;

@RestController
public class HomeController {
    private final SpanishBlue spanishBlue;
    private final SpanishGreen spanishGreen;
    private final SpanishRed spanishRed;

    public HomeController(SpanishBlue spanishBlue, SpanishGreen spanishGreen, SpanishRed spanishRed) {
        this.spanishBlue = spanishBlue;
        this.spanishGreen = spanishGreen;
        this.spanishRed = spanishRed;
    }

    @GetMapping("/")
    public String home() {
        return "Hi Mark";
    }

    @GetMapping("/colors")
    public String printColor() {
        return spanishBlue.printInColor() + "\n" + spanishGreen.printInColor() + "\n" + spanishRed.printInColor();
    }
}
