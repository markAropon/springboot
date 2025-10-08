package com.bootcamp.quickdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                // Swagger docs
                registry.addMapping("/v3/api-docs/**")
                                .allowedOrigins("*")
                                .allowedMethods("GET")
                                .allowedHeaders("*");

                registry.addMapping("/swagger-ui/**")
                                .allowedOrigins("*")
                                .allowedMethods("GET")
                                .allowedHeaders("*");

                // Allow localhost dev and deployed frontend origins for API
                registry.addMapping("/api/**")
                                .allowedOrigins(
                                                "http://localhost:5173",
                                                "http://localhost",
                                                "http://127.0.0.1",
                                                "https://react-in-bootcamp.vercel.app/"
                                )
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                .allowedHeaders("*")
                                .allowCredentials(true);
        }
}