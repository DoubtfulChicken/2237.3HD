# Use OpenJDK as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the shaded JAR file to the container
COPY target/AssignmentApp-1.0-SNAPSHOT-shaded.jar /app/assignmentapp.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/assignmentapp.jar"]
