pipeline {
  agent any
  stages {
    stage('Backend') {
      parallel {
        stage('Backend') {
          agent {
            docker {
              image 'maven:3.6.3-openjdk-15-slim'
            }

          }
          steps {
            sh 'chmod +x backend/mvnw'
            sh 'cd backend && ./mvnw -B verify --file pom.xml'
          }
        }

        stage('Cypress') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            sh 'cd frontend'
            sh '''yarn install --network-timeout 1000000
       

 '''
            sh 'yarn global add @vue/cli'
            sh 'yarn cypress install'
            sh 'yarn test:ci'
          }
        }

      }
    }

  }
}