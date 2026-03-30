pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK'
    }

    options {
        timestamps()
        timeout(time: 45, unit: 'MINUTES')
    }

    environment {
        HEADLESS = 'true'
        BASE_URL = 'http://localhost:8090'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/WARDASIU/E2E-Selenium-Selenide.git/',
                    credentialsId: 'github-creds'
            }
        }

        stage('Start Petclinic') {
            steps {
                sh 'docker compose up -d petclinic'
                sh '''
                    echo "Waiting for Petclinic..."
                    for i in $(seq 1 90); do
                        if curl -sf http://localhost:8090; then
                            echo "Petclinic is up."
                            break
                        fi
                        if [ "$i" -eq 90 ]; then
                            echo "Timeout waiting for Petclinic."
                            exit 1
                        fi
                        sleep 2
                    done
                '''
            }
        }

        stage('E2E') {
            steps {
                sh 'mvn -B clean test'
            }
        }

        stage('Allure Report') {
            steps {
                sh 'mvn -B allure:report'
            }
        }
    }

    post {
        always {
            sh 'docker compose down'
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
                    echo 'No Allure results found in target/allure-results.'
                }
            }
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}