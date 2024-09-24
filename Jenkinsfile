pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    echo 'Building the application...'
                    bat 'mvn clean package'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo 'Running tests...'
                    bat 'mvn test'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo 'Deploying the application...'
                    // Add your Windows deployment commands here
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline complete'
        }
        success {
            echo 'Pipeline succeeded'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}
