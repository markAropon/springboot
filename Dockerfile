# Use OpenJDK 21 as base image
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port 8080 (default for Spring Boot)
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "target/quickdemo-0.0.1-SNAPSHOT.jar"]