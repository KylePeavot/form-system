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

        stage('Yarn Install') {
          agent {
            docker {
              image 'cypress/browsers:node12.18.3-chrome87-ff82'
            }

          }
          steps {
            dir(path: 'frontend') {
              sh 'yarn install --network-timeout 1000000'
              sh 'yarn global add @vue/cli'
            }

            sh 'echo "$PATH"'
            sh 'echo "$(yarn global bin)"'
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
            sh 'export PATH="$PATH:$(yarn global bin)"'
            dir(path: 'frontend') {
              sh 'yarn global add @vue/cli'
              sh 'yarn test:unit'
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
}