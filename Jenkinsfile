pipeline {
    agent any

    parameters {
        string(name: 'GIT_REPO', defaultValue: '', trim: true, description: 'Inline Pipeline only: clone URL. Leave empty for Pipeline script from SCM.')
        string(name: 'GIT_BRANCH', defaultValue: 'master', trim: true, description: 'Branch to clone')
    }

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
                script {
                    if (params.GIT_REPO?.trim()) {
                        git branch: params.GIT_BRANCH, url: params.GIT_REPO.trim()
                    } else {
                        checkout scm
                    }
                }
            }
        }
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
