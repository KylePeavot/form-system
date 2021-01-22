pipeline {
  agent any
  stages {

    stage("Notify BitBucket") {
      agent none
      steps {
        bitbucketStatusNotify 'INPROGRESS'
      }
    }

    stage('Tests') {
      parallel {
        stage('Backend') {
          agent {
            docker {
              image 'openjdk:15.0.1-jdk'
            }
          }
          steps {
            dir(path: 'backend') {
              sh 'chmod +x ./mvnw'
              sh './mvnw -B verify --file pom.xml'
            }
          }
        }

        stage('Frontend (Cypress)') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }
          }
          steps {
            sh 'export PATH="$(yarn global bin):$PATH"'
            sh 'yarn global add @vue/cli'
            dir(path: 'frontend') {
              sh 'yarn install'
              sh 'cypress install'
              sh 'yarn test:ci'
            }
          }
        }
      }
    }

  }
  post {

    success {
        bitbucketStatusNotify 'SUCCESSFUL'
    }

    failure {
      bitbucketStatusNotify 'FAILED'
    }

  }
  environment {
    npm_config_cache = '~/npm_cache'
  }
}