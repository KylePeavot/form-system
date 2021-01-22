pipeline {
  agent any
  stages {

    stage("Notify BitBucket - Start") {
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
            sh 'yarn global add cypress'
            dir(path: 'frontend') {
              sh 'yarn install'
              sh 'yarn test:ci'
            }
          }
        }
      }
    }

    stage("Notify BitBucket - Done") {
      agent none
      steps {
        bitbucketStatusNotify 'SUCCESSFUL'
      }
    }

  }
  post {

    failure {
      bitbucketStatusNotify 'FAILED'
    }

  }
  environment {
    npm_config_cache = '~/npm_cache'
  }
}