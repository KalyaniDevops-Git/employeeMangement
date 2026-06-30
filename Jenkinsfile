pipeline {

    agent any

    tools {
        maven 'Maven-3.9'
    }

    environment {
        IMAGE_NAME = 'employee-management-system'
        CONTAINER_NAME = 'employee-management-container'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building application...'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running test cases...'
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Creating JAR...'
                bat 'mvn package -DskipTests'
            }
        }

        stage('Archive Artifact') {
            steps {
                echo 'Archiving JAR...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Compose Build') {
            steps {
                echo 'Building Docker Images...'

                bat '''
                docker compose build
                '''
            }
        }

        stage('Deploy Using Docker Compose') {
            steps {

                echo 'Stopping old containers...'

                bat '''
                docker compose down || exit 0
                '''

                echo 'Starting containers...'

                bat '''
                docker compose up -d
                '''
            }
        }

        stage('Verify Deployment') {
            steps {

                echo 'Checking running containers...'

                bat '''
                docker ps
                '''

                echo 'Checking Docker Compose Services...'

                bat '''
                docker compose ps
                '''
            }
        }

    }

    post {

        success {

            echo '====================================='
            echo ' Build and Deployment Successful'
            echo ' Spring Boot URL : http://localhost:9091'
            echo '====================================='

        }

        failure {

            echo '====================================='
            echo ' Build Failed'
            echo ' Check Jenkins Console Output'
            echo '====================================='

        }

        always {
            cleanWs()
        }

    }

}