pipeline {
    agent any
    tools {
        jdk 'Java21-JDK'
    }
    stages {
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
                    // Run unit tests using Maven
                    bat 'mvn test'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat '"%MAVEN_HOME%/bin/mvn" clean verify sonar:sonar -Dsonar.projectKey=AssignmentTrackerSonar -Dsonar.projectName="AssignmentTrackerSonar"'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Running the Deploy stage...'
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
