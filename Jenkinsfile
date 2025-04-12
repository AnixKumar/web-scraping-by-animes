pipeline {
    agent any

    tools {
        maven 'Maven_3.8.6'      // Make sure this is installed/configured in Jenkins
        jdk 'Java_21'            // Set your actual Java version label from Jenkins tool config
    }

    stages {
        stage('Verify Java Version') {
            steps {
                bat 'java -version'
            }
        }
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/AnixKumar/web-scraping-by-animes.git', branch: 'main'
            }
        }

        stage('Build Project') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test -Dtest=org/ui/automate/WebScrapingTestForWebDriver'
            }
        }

        stage('Archive Test Reports') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.html', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            echo 'Cleaning workspace...'
            cleanWs()
        }

        success {
            echo 'Build successful!'
        }

        failure {
            echo 'Build failed. Investigate errors.'
        }
    }
}
