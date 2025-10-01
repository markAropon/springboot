# Hospital ManagemenThe API is fully documented using OpenAPI/Swagger. You can access the Swagger UI at:

```
http://localhost:8081/swagger-ui.html
```

The OpenAPI JSON definition is available at:

```
http://localhost:8081/v3/api-docs
```I

A comprehensive RESTful API for hospital management built with Spring Boot, featuring JWT authentication, role-based access control, and comprehensive OpenAPI documentation.

## Features

- **User Authentication**: Secure JWT-based authentication
- **Role-Based Access Control**: Different access levels for Admin, Doctor, Patient, and User roles
- **Patient Management**: Create, update, and manage patient records
- **Medical History**: Track and manage patient medical histories
- **Doctor Management**: Manage doctor information and specialties
- **Admission Management**: Handle patient admissions and discharges
- **Insurance Management**: Track patient insurance information
- **Vital Signs**: Record and monitor patient vital signs
- **Comprehensive API Documentation**: Complete OpenAPI/Swagger documentation
- **Error Handling**: Standardized error responses across all endpoints
- **Data Validation**: Robust input validation

## API Documentation

The API is fully documented using OpenAPI/Swagger. You can access the Swagger    UI at:

```
http://localhost:8081/swagger-ui.html
```

The OpenAPI JSON definition is available at:

```
http://localhost:8081/api-docs
```

## Authentication

### Registration

```
POST /api/auth/register
```

Example request body:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "password123"
}
```

### Login

```
POST /api/auth/login
```

Example request body:
```json
{
  "usernameOrEmail": "johndoe",
  "password": "password123"
}
```

Upon successful login, you'll receive a JWT token which should be included in the `Authorization` header for subsequent requests:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## Role-Based Access

The API implements four user roles:

- **ROLE_ADMIN**: Full access to all endpoints
- **ROLE_DOCTOR**: Access to doctor and patient related endpoints
- **ROLE_PATIENT**: Access to personal medical records
- **ROLE_USER**: Basic access to public resources

## Error Handling

All API endpoints return standardized error responses:

- **400 Bad Request**: Invalid input
- **401 Unauthorized**: Authentication required
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server-side issue

Example error response:

```json
{
  "status": 404,
  "success": false,
  "message": "Not Found",
  "data": {
    "timestamp": "2023-05-15T14:30:00",
    "message": "Patient with ID 123 not found",
    "path": "/api/patients/123",
    "errorCode": "NOT_FOUND"
  },
  "errors": null,
  "errorCode": 404,
  "timestamp": "2023-05-15T14:30:00",
  "path": "/api/patients/123"
}
```

## Setting Up the Project

### Prerequisites

- JDK 17+
- Maven 3.6+
- PostgreSQL database

### Configuration

1. Clone the repository
2. Configure the database connection in `application.properties`
3. Run the application using Maven:

```bash
mvn spring-boot:run
```

### Docker Deployment

A Dockerfile is included for containerization:

```bash
docker build -t hospital-management-api .
docker run -p 8081:8081 hospital-management-api
```

## Testing the API

You can use the included test endpoints to verify error handling:

```
GET /test-error?type=badrequest
GET /test-error?type=notfound
GET /test-error?type=servererror
GET /test-error?type=unauthorized
GET /test-error?type=forbidden
```

## Default Users

The system includes default users for testing:

- Admin: username `admin`, password `admin123`
- Doctor: username `doctor`, password `doctor123`
- Patient: username `patient`, password `patient123`
- User: username `user`, password `user123`

## License

MIT License