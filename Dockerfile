# Use OpenJDK as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install necessary tools and download JavaFX SDK
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://download2.gluonhq.com/openjfx/21.0.0.0/openjfx-21.0.0.0_linux-x64_bin-sdk.zip && \
    unzip openjfx-21.0.0.0_linux-x64_bin-sdk.zip && \
    rm openjfx-21.0.0.0_linux-x64_bin-sdk.zip

# Copy the final JAR file to the container
COPY target/AssignmentApp-1.0-SNAPSHOT.jar /app/assignmentapp.jar

# Run the JAR file with the required JavaFX modules
ENTRYPOINT ["java", "--module-path", "/app/javafx-sdk-21.0.0/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "/app/assignmentapp.jar"]
