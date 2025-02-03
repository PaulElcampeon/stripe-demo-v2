# Stage 1: Build the JAR file
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies (this is done separately to cache the dependencies and speed up future builds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code to the container
COPY src ./src

# Package the application (this will build the JAR file)
RUN mvn package

# Stage 2: Create the final runtime image
FROM eclipse-temurin:21-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder image
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080 for the Spring Boot application
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
