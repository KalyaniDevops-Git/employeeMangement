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
            archiveArtifacts artifacts: 'target/*.jar'
        }
    }

    stage('Docker Build') {
        steps {
            echo 'Building Docker Image...'
            bat 'docker build -t %IMAGE_NAME% .'
        }
    }

    stage('Deploy Container') {
        steps {
            echo 'Stopping old container if exists...'

            bat '''
            docker stop %CONTAINER_NAME% || exit 0
            docker rm %CONTAINER_NAME% || exit 0
            '''

            echo 'Starting new container...'

            bat '''
            docker run -d ^
            --name %CONTAINER_NAME% ^
            -p 9091:9090 ^
            %IMAGE_NAME%
            '''
        }
    }
}

post {

    success {
        echo 'Build and Deployment Successful'
    }

    failure {
        echo 'Build Failed'
    }

    always {
        cleanWs()
    }
}
```

}
