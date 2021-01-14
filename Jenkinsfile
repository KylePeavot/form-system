pipeline {
  agent any
  stages {
    stage('Backend') {
      parallel {
        stage('Backend') {
          agent {
            docker {
              image 'openjdk:15.0.1-jdk'
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
            sh '''cd frontend && yarn install --network-timeout 1000000
       

 '''
            sh 'yarn global add @vue/cli'
            sh 'yarn global add cypress'
            sh 'cd frontend && yarn cypress install'
            sh 'cd frontend && yarn test:ci'
          }
        }

      }
    }

  }
}