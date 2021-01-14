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

          }
        }

        stage('NPM Install') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            sh 'sudo chown -R `whoami` ~/.npm'
            sh 'sudo chown -R `whoami` /usr/local/lib/node_modules'
            dir(path: 'frontend') {
              sh 'npm install -g @vue/cli'
              sh 'npm install'
            }

          }
        }

      }
    }

    stage('Unit') {
      parallel {
        stage('Unit') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            dir(path: 'frontend') {
              sh 'npm run test:unit'
            }

          }
        }

        stage('E2E') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            sh 'export PATH="$PATH:$(yarn global bin)"'
            dir(path: 'frontend') {
              sh 'yarn global add @vue/cli'
              sh 'yarn test:e2e'
            }

          }
        }

      }
    }

  }
  environment {
    npm_config_cache = '~/npm_cache'
  }
}