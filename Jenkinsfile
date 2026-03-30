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
        BASE_URL = 'http://host.docker.internal:8090'
        SELENIUM_REMOTE_URL = 'http://host.docker.internal:4444/wd/hub'
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
                sh 'docker run -d --name petclinic -p 8090:8080 springcommunity/spring-petclinic:latest'
                sh 'docker run -d --name selenium-chrome -p 4444:4444 --shm-size=2g selenium/standalone-chrome:latest'
                sh '''
                    echo "Waiting for Petclinic..."
                    for i in $(seq 1 30); do
                        if curl -sf http://host.docker.internal:8090; then
                            echo "Petclinic is up."
                            break
                        fi
                        if [ "$i" -eq 30 ]; then echo "Timeout waiting for Petclinic."; exit 1; fi
                        sleep 5
                    done
                '''
                sh '''
                    echo "Waiting for Selenium..."
                    for i in $(seq 1 12); do
                        if curl -sf http://host.docker.internal:4444/wd/hub/status; then
                            echo "Selenium is up."
                            break
                        fi
                        if [ "$i" -eq 12 ]; then echo "Timeout waiting for Selenium."; exit 1; fi
                        sleep 5
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
            sh 'docker stop petclinic selenium-chrome && docker rm petclinic selenium-chrome || true'
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