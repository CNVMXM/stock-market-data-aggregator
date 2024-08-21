# Use a base image with OpenJDK
FROM openjdk:21

# Set the working directory in the container
WORKDIR /app

# Copy the packaged Spring Boot JAR file into the container
COPY target/auth-service-4.0.0-SNAPSHOT.jar /app/app.jar

# Expose the port that the application runs on
EXPOSE 8080

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]