pipeline {
    agent any
    tools {
        jdk 'Java21-JDK'
    }

    environment {
        GITHUB_TOKEN = credentials('github-token')
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
                    bat 'mvn clean package'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    bat 'mvn test'
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
        
        stage('Deploy to Tomcat') {
            steps {
                script {
                    def WAR_FILE = 'C:/Windows/System32/config/systemprofile/AppData/Local/Jenkins/.jenkins/workspace/AssignmentTracker_Pipeline/target/AssignmentApp-1.0-SNAPSHOT.war'
                    def TOMCAT_USER = 'admin'
                    def TOMCAT_PASS = 'admin'
                    def TOMCAT_URL = 'http://localhost:8090'
                    def DEPLOY_PATH = "${TOMCAT_URL}/manager/text/deploy?path=/AssignmentApp&update=true"

                    bat """
                    curl --upload-file ${WAR_FILE} \
                         --user ${TOMCAT_USER}:${TOMCAT_PASS} \
                         "${DEPLOY_PATH}"
                    """
                }
            }
        }
    }

    post {
        always {
            script {
                // Notify Prometheus to scrape Jenkins metrics
                def prometheusURL = 'http://localhost:9090/-/reload'
                bat "curl -X POST ${prometheusURL}"
            }
        }
        failure {
            // Notify team in case of failure
            echo 'Pipeline failed!'
        }
    }
}
