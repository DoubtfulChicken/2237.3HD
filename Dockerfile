# Use OpenJDK as the base image
FROM openjdk:21-jdk-slim

# Install Xvfb and JavaFX dependencies
RUN apt-get update && apt-get install -y xvfb libgl1-mesa-glx libpulse0

# Set the working directory inside the container
WORKDIR /app

# Copy the final JAR file to the container
COPY target/AssignmentApp-1.0-SNAPSHOT.jar /app/assignmentapp.jar

# Run the JAR file using Xvfb to simulate a display
ENTRYPOINT ["xvfb-run", "--auto-servernum", "java", "-jar", "/app/assignmentapp.jar"]
