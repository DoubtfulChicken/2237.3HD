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
                    // List the contents of the target directory to verify the JAR is built
                    bat 'dir target'
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
                    bat 'docker build -t assignmentapp:latest .'
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                script {
                    // remove any existing container with the name 'assignmentapp'
                    bat 'docker rm -f assignmentapp || true'
                    
                    // Run the new Docker container on port 8085 (or any available port)
                    bat 'docker run -d -p 8085:8080 --name assignmentapp assignmentapp:latest'
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
