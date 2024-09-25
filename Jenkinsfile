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
       stage('Deploy to Tomcat') {
            steps {
                script {
                    // Define environment variables for Tomcat deployment
                    def WAR_FILE = 'target/your-javafx-app.war'  // Update with your WAR file name
                    def TOMCAT_USER = 'admin'
                    def TOMCAT_PASS = 'admin'
                    def TOMCAT_URL = 'http://localhost:8090'  // Update to your Tomcat URL and port
                    def DEPLOY_PATH = "http://localhost:8090/manager/text/deploy?path=/your-app&update=true"  // Update the path

                    // Use curl to deploy the WAR file to Tomcat
                    bat """
                    curl --upload-file target/APPENDWITHNEW.war \
                         --user admin:admin \
                         http://localhost:8090/manager/text/deploy?path=/your-app&update=true
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
