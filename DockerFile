# Use OpenJDK 21 as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the target directory to the container
COPY target/*.jar /app/assignmentapp.jar

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/assignmentapp.jar"]
