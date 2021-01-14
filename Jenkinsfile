pipeline {
  agent any
  stages {
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

    stage('Frontend') {
      parallel {
        stage('Unit') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            sh 'npm config set prefix \'~/.npm-global\''
            sh 'export PATH=~/.npm-global/bin:$PATH'
            dir(path: 'frontend') {
              sh 'npm install -g @vue/cli'
              sh 'npm install'
              sh 'npm run test:unit'
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
              sh 'yarn global add @vue/cli'
              sh 'yarn install'
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