pipeline {
  agent any
  stages {
    stage('Pull') {
      steps {
        git 'https://bitbucket.org/weffs/form-system'
      }
    }

    stage('Backend') {
      parallel {
        stage('Backend') {
          agent {
            docker {
              image 'maven:3.6.3'
            }

          }
          steps {
            sh 'mvn -B verify --file pom.xml'
          }
        }

        stage('Cypress') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
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