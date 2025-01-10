pipeline {
  agent any
  tools {
    maven 'Maven 3.9.9'
  }
  environment {
    DOCKER_REGISTRY = 'harbor.mycompany.com' // URL du registre Docker Harbor
    DOCKER_REPO = 'myproject/myapp' // Nom du dépôt dans Harbor
    DOCKER_CREDENTIALS = 'harbor-credentials-id' // ID des credentials Jenkins
    IMAGE_TAG = "${env.BRANCH_NAME}-${env.BUILD_NUMBER}" // Tag dynamique basé sur la branche et le numéro de build
  }
  
  stages {
    stage ('Build') {
      steps {
        sh 'mvn clean package' // Compile et package le projet
      }
    }
    stage('SonarQube Analysis') {
      steps {
        script {
          def mvnHome = tool 'Maven 3.9.9' // Utilise le nom du tool Maven configuré dans Jenkins
          withSonarQubeEnv('SonarQ') { // 'SonarQ' est le nim de la connexion SonarQube dans Jenkins
            sh "${mvnHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=DriveitBack -Dsonar.projectName='DriveitBack'" // -Dsonar.projectKey & -Dsonar.projectName doivent correspondre au nom du projet créé dans SonarQube
          }
        }
      }
    }
    stage('Build Docker Image') {
      steps {
        script {
            sh "docker build -t ${DOCKER_REGISTRY}/${DOCKER_REPO}:${IMAGE_TAG} ."
        }
      }
    }
    stage('Push Docker Image to Harbor') {
      steps {
        script {
          // Login to Docker registry
          withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS, usernameVariable: 'HARBOR_USER', passwordVariable: 'HARBOR_PASS')]) {
              sh """
              echo "$HARBOR_PASS" | docker login ${DOCKER_REGISTRY} -u "$HARBOR_USER" --password-stdin
              """
          }
          // Push the image
          sh "docker push ${DOCKER_REGISTRY}/${DOCKER_REPO}:${IMAGE_TAG}"
        }
      }
    }
  }
}
