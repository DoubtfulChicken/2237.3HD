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
                    // Verify WAR file in the target directory
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
                    // Define environment variables for Tomcat deployment
                    def WAR_FILE = 'C:/Windows/System32/config/systemprofile/AppData/Local/Jenkins/.jenkins/workspace/AssignmentTracker_Pipeline/target/AssignmentApp-1.0-SNAPSHOT.war' 
                    def TOMCAT_USER = 'admin'
                    def TOMCAT_PASS = 'admin'
                    def TOMCAT_URL = 'http://localhost:8090'  // Update to your Tomcat URL and port
                    def DEPLOY_PATH = "http://localhost:8090/manager/text/deploy?path=/your-app&update=true"  // Update the path

                    // Use curl to deploy the WAR file to Tomcat
                    bat """
                    curl --upload-file ${WAR_FILE} \
                         --user ${TOMCAT_USER}:${TOMCAT_PASS} \
                         ${DEPLOY_PATH}
                    """
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
