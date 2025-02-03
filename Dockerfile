# Use Eclipse Temurin JDK 19 and Alpine Linux as the base image for the final image
FROM eclipse-temurin:21-alpine
#FROM openjdk:17-jdk-slim

## Set the working directory inside the container
#WORKDIR /app

# Copy the built JAR file from the build stage to the final image
COPY target/*.jar app.jar

# Expose the port on which the Spring Boot application will listen
EXPOSE 8080

# Set the command to run the Spring Boot application when the container starts
CMD ["java", "", "-jar", "app.jar"]
#CMD ["java", "-Djwt_secret=${jwt_secret} -Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME} -Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD} -Dspring.datasource.url=${SPRING_DATASOURCE_URL}", "-jar", "app.jar"]
