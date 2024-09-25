pipeline {
    agent any
    tools {
        jdk 'Java21-JDK'
    }
    stages {
        stage('Check Environment') {
            steps {
                bat 'echo %JAVA_HOME%'
                bat 'echo %MAVEN_HOME%'
                bat 'java -version'
                bat 'mvn -version'
            }
        }
        stage('Build') {
            steps {
                script {
                    // Compile and package the application using Maven
                    bat 'mvn clean package'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    // Run JUnit tests using Maven
                    bat 'mvn test'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat 'mvn clean verify sonar:sonar -Dsonar.projectKey=AssignmentTrackerSonar -Dsonar.projectName="AssignmentTrackerSonar"'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    bat 'docker build -t assignmentapp:latest .'  // Build the Docker image
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                script {
                    bat 'docker run -d -p 8080:8080 --name assignmentapp assignmentapp:latest'  // Run the Docker container
                }
            }
        }
        stage('Release') {
            steps {
                echo 'Running the Release stage...'
            }
        }
        stage('Monitoring and Alerting') {
            steps {
                echo 'Running the Monitoring and Alerting stage...'
            }
        }
    }
}
