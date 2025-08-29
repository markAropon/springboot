package com.bootcamp.quickdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bootcamp.quickdemo.colors.BluePrinter;
import com.bootcamp.quickdemo.colors.ColorPrinter;
import com.bootcamp.quickdemo.colors.ColorPrinterImpl;
import com.bootcamp.quickdemo.colors.GreenPrinter;
import com.bootcamp.quickdemo.colors.RedPrinter;

@Configuration
public class PrinterConfig {
    @Bean
    public RedPrinter redPrinter() {
        return new RedPrinter();
    }

    @Bean
    public GreenPrinter greenPrinter() {
        return new GreenPrinter();
    }

    @Bean
    public BluePrinter bluePrinter() {
        return new BluePrinter();
    }

    @Bean
    public ColorPrinter colorPrinter() {
        return new ColorPrinterImpl();
    }
}
