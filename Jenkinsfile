/**
 * Runs TestNG E2E + Allure. Petclinic must already be reachable at BASE_URL on the agent
 * (deployed app, sidecar, or run `docker compose -f docker-compose.ci.yml run --rm e2e` on a machine that has Docker).
 * Petclinic on the agent must listen on host port 8090 (e.g. -p 8090:8080), or override BASE_URL.
 */
pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 45, unit: 'MINUTES')
    }

    environment {
        HEADLESS = 'true'
        BASE_URL = 'http://localhost:8090'
    }

    stages {
        stage('E2E') {
            steps {
                sh 'mvn -B clean test'
                sh 'mvn -B allure:report'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-results/**/*', allowEmptyArchive: true
            script {
                if (fileExists('target/allure-results')) {
                    allure([
                        includeProperties : false,
                        jdk               : '',
                        properties        : [],
                        reportBuildPolicy : 'ALWAYS',
                        results           : [[path: 'target/allure-results']]
                    ])
                } else {
                    echo 'No Allure results in target/allure-results.'
                }
            }
        }
    }
}
