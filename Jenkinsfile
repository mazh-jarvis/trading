pipeline{
    agent any
    tools {
        maven "M3"
        jdk "java8"
    }

    environment {
        app_name = "trading-app"
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
                echo "app_name is ${env.app_name}"
                archiveArtifacts 'target/*zip'
            }
        }
        stage('Deploy_dev') {
            when { branch 'develop' }
            steps {
                echo "Current branch is ${env.GIT_BRANCH}"
                sh "bash ./scripts/eb_deploy.sh trading-app TradingApp-dev"
            }
        }
        stage('Deploy_prod') {
            when { branch 'master' }
            steps {
                echo "Current branch is ${env.GIT_BRANCH}"
                sh "bash ./scripts/eb_deploy.sh trading-app TradingApp-prod"
            }
        }
    }
}