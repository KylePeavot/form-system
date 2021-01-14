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
            dir(path: 'backend') {
              sh 'chmod +x ./mvnw'
              sh './mvnw -B verify --file pom.xml'
            }

            bitbucketStatusNotify 'INPROGRESS'
          }
        }

        stage('Yarn Global') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            sh 'export PATH="$(yarn global bin):$PATH"'
            sh 'yarn global add @vue/cli'
          }
        }

      }
    }

    stage('Frontend') {
      parallel {
        stage('Unit') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            sh 'export PATH="$(yarn global bin):$PATH"'
            dir(path: 'frontend') {
              sh 'yarn install'
              sh 'yarn test:unit'
            }

          }
        }

        stage('E2E Yarn') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            sh 'export PATH="$(yarn global bin):$PATH"'
            dir(path: 'frontend') {
              sh 'yarn install'
              sh 'yarn test:e2e'
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