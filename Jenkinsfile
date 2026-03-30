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
                sh 'docker run -d --name selenium-chrome   -p 4444:4444 --shm-size=2g -e SE_SESSION_REQUEST_TIMEOUT=120 -e SE_NODE_SESSION_TIMEOUT=120 selenium/standalone-chrome:latest'
                sh 'docker run -d --name selenium-firefox  -p 4445:4444 --shm-size=2g -e SE_SESSION_REQUEST_TIMEOUT=120 -e SE_NODE_SESSION_TIMEOUT=120 selenium/standalone-firefox:latest'
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
                    echo "Waiting for Selenium Chrome..."
                    for i in $(seq 1 12); do
                        if curl -sf http://host.docker.internal:4444/wd/hub/status; then
                            echo "Selenium Chrome is up."
                            break
                        fi
                        if [ "$i" -eq 12 ]; then echo "Timeout waiting for Selenium Chrome."; exit 1; fi
                        sleep 5
                    done
                '''
                sh '''
                    echo "Waiting for Selenium Firefox..."
                    for i in $(seq 1 12); do
                        if curl -sf http://host.docker.internal:4445/wd/hub/status; then
                            echo "Selenium Firefox is up."
                            break
                        fi
                        if [ "$i" -eq 12 ]; then echo "Timeout waiting for Selenium Firefox."; exit 1; fi
                        sleep 5
                    done
                '''
            }
        }

        stage('E2E') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh 'mvn -B clean test -DsuiteXmlFile=testng-ci.xml'
                }
            }
        }

    }

    post {
        always {
            sh 'docker stop petclinic selenium-chrome selenium-firefox && docker rm petclinic selenium-chrome selenium-firefox || true'
            allure([
                includeProperties : false,
                jdk               : '',
                properties        : [],
                reportBuildPolicy : 'ALWAYS',
                results           : [[path: 'target/allure-results']]
            ])
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}