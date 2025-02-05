# Stage 1: Build the JAR file
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY . .

# Download the dependencies specified in the pom.xml file
RUN mvn dependency:go-offline -B

# Build the application using Maven
RUN mvn package -DskipTests

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
