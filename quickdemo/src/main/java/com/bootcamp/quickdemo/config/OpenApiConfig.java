package com.bootcamp.quickdemo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

 @Bean
 public OpenAPI customOpenAPI() {
  return new OpenAPI()
    .info(new Info()
      .title("Hospital Management System API")
      .version("1.0")
      .description(
        "RESTful API for Hospital Management System using Spring Boot with JWT authentication and Role-Based Access Control (RBAC).\n\n"
          +
          "## Authentication\n" +
          "This API uses JWT token-based authentication. To use protected endpoints:\n"
          +
          "1. Register a user or use default users (admin/admin123, user/user123, doctor/doctor123, patient/patient123)\n"
          +
          "2. Login to obtain a JWT token\n" +
          "3. Add the JWT token to the Authorization header as 'Bearer {token}'\n\n"
          +
          "## Role-Based Access\n" +
          "- **ROLE_ADMIN**: Full access to all endpoints\n" +
          "- **ROLE_DOCTOR**: Access to doctor and patient endpoints\n"
          +
          "- **ROLE_PATIENT**: Access to patient-specific endpoints\n"
          +
          "- **ROLE_USER**: Limited access to basic features\n\n"
          +
          "## Error Handling\n" +
          "All endpoints return standardized error responses:\n" +
          "- 400: Bad Request - Invalid input\n" +
          "- 401: Unauthorized - Authentication required\n" +
          "- 403: Forbidden - Insufficient permissions\n" +
          "- 404: Not Found - Resource not found\n" +
          "- 500: Internal Server Error - Server-side issue\n\n" +
          "## Data Models\n" +
          "All data models include schema annotations for comprehensive documentation.")
      .contact(new Contact()
        .name("Developer Mark James Aropon")
        .email("markjamesAropon.srbootcamp2025@gmail.com")
        .url("https://github.com/MarkAropon")))
    .servers(Arrays.asList(
      new Server()
        .url("/")
        .description("Local Development Server"),
      new Server()
        .url("https://springboot-dklm.onrender.com")
        .description("Production Server (Render)")))
    .addSecurityItem(new SecurityRequirement().addList("bearerAuth",
      Arrays.asList("read", "write")))
    .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
    .components(new Components()
      .addSecuritySchemes("bearerAuth", new SecurityScheme()
        .name("bearerAuth")
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .description("JWT Authorization header using the Bearer scheme.\n\n"
          +
          "Enter your token in the format: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`\n\n"
          +
          "You can obtain a token by registering and logging in through the /api/auth endpoints.")));
 }
}