pipeline {

    agent any

    tools {
        maven 'Maven-3.9'
              }

    environment {
        COMPOSE_FILE = 'docker-compose.yml'
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
                echo 'Compiling application...'
                bat 'mvn clean compile'
            }
        }

//         stage('Test') {
//             steps {
//                 echo 'Running Tests...'
//                 bat 'mvn test'
//             }
//         }
//
stage('SonarQube Analysis') {
    steps {
        script {
            def scannerHome = tool 'SonarScanner'

            withSonarQubeEnv('SonarQube') {

                bat """
                "${scannerHome}\\bin\\sonar-scanner.bat" ^
                -Dsonar.projectKey=employee-management-system ^
                -Dsonar.projectName=employee-management-system ^
                -Dsonar.sources=src ^
                -Dsonar.java.binaries=target/classes
                """

            }
        }
    }
}
stage('Quality Gate') {
    steps {
        timeout(time: 5, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
        }
    }
}

        stage('Package') {
            steps {
                echo 'Packaging Application...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Compose Build') {
            steps {
                echo 'Building Docker Images...'
                bat 'docker compose build'
            }
        }

        stage('Docker Compose Deploy') {
            steps {
                echo 'Stopping old containers...'
                bat 'docker compose down'

                echo 'Starting new containers...'
                bat 'docker compose up -d'
            }
        }

        stage('Verify Deployment') {
            steps {
                echo 'Running Containers'
                bat 'docker ps'
            }
        }
    }

    post {

        success {
            echo '=========================================='
            echo 'BUILD AND DEPLOYMENT SUCCESSFUL'
            echo '=========================================='
        }

        failure {
            echo '=========================================='
            echo 'BUILD FAILED'
            echo '=========================================='
        }

        always {
            cleanWs()
        }
    }
}