/**
 * CI: start Spring PetClinic temporarily, run TestNG E2E tests, publish Allure HTML.
 *
 * Requirements:
 * - Agent with Docker (and Docker Compose v2: `docker compose`) and permission to pull images.
 * - Path A (default): uses docker-compose.ci.yml so tests reach Petclinic at http://petclinic:8080.
 * - Path B: Linux agent with Docker on host + Maven + Chrome on agent: set USE_COMPOSE=false (Petclinic on localhost:8080).
 */
pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 45, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    parameters {
        booleanParam(name: 'USE_COMPOSE', defaultValue: true, description: 'Use docker-compose.ci.yml (recommended). If false, runs Petclinic via docker run and mvn on the agent (needs Chrome + Maven on agent).')
    }

    environment {
        HEADLESS = 'true'
        // Used when USE_COMPOSE=false (Petclinic published to agent localhost)
        BASE_URL = 'http://localhost:8080'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('E2E tests') {
            steps {
                script {
                    if (params.USE_COMPOSE) {
                        sh 'docker compose -f docker-compose.ci.yml run --rm e2e'
                    } else {
                        sh '''
                            docker rm -f petclinic-ci-$BUILD_NUMBER 2>/dev/null || true
                            docker run -d --name petclinic-ci-$BUILD_NUMBER -p 8080:8080 springcommunity/spring-petclinic:latest
                        '''
                        sh '''
                            echo "Waiting for Petclinic on localhost:8080..."
                            for attempt in $(seq 1 90); do
                              if timeout 1 bash -c 'cat < /dev/null > /dev/tcp/127.0.0.1/8080' 2>/dev/null; then
                                echo "Petclinic is up."
                                break
                              fi
                              if [ "$attempt" -eq 90 ]; then
                                echo "Timeout waiting for Petclinic."
                                exit 1
                              fi
                              sleep 2
                            done
                        '''
                        sh 'mvn -B clean test'
                        sh 'mvn -B allure:report'
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                if (params.USE_COMPOSE) {
                    sh 'docker compose -f docker-compose.ci.yml down --remove-orphans 2>/dev/null || true'
                } else {
                    sh 'docker rm -f petclinic-ci-$BUILD_NUMBER 2>/dev/null || true'
                }
            }
            archiveArtifacts artifacts: 'target/allure-results/**/*', allowEmptyArchive: true
            publishHTML([
                allowMissing         : true,
                alwaysLinkToLastBuild: true,
                keepAll              : true,
                reportDir            : 'target/site/allure-maven-plugin',
                reportFiles          : 'index.html',
                reportName           : 'Allure Report'
            ])
        }
    }
}
