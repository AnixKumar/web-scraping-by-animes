pipeline {
    agent any

    tools {
        maven 'Maven_3.8.6'      // Make sure this is installed/configured in Jenkins
        jdk 'Java_21'            // Set your actual Java version label from Jenkins tool config
    }

    parameters {
            booleanParam(name: 'CLEAN_WORKSPACE', defaultValue: false, description: 'Clean workspace after build?')
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
                archiveArtifacts artifacts: 'logs/test-log.log', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            script {
                            if (params.CLEAN_WORKSPACE) {
                                echo 'Cleaning workspace as per user selection...'
                                cleanWs()
                            } else {
                                echo 'Skipping workspace cleanup.'
                            }
                        }
        }

        success {
            echo 'Build successful!'
            emailext (
                        subject: "✅ Jenkins Build Success: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                        body: """
                            <p>Hello,</p>
                            <p>The Jenkins build <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> completed successfully.</p>
                            <p><a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                        """,
                        mimeType: 'text/html',
                        attachmentsPattern: 'logs/test-log.log',
                        to: 'n.pulse7@gmail.com'
                    )
        }

        failure {
            echo 'Build failed. Investigate errors.'
        }
    }
}
