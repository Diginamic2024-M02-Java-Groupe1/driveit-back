pipeline {
  agent any
  tools {
    maven 'Maven 3.9.9'
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
  }
}
