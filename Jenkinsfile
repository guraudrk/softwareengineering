pipeline {
    agent any

    environment {
        GIT_CREDENTIALS = credentials('Hongik-Test') // Jenkins에 저장된 크리덴셜 ID 사용
    }

    stages {
        stage('Checkout SCM') {
            steps {
                timeout(time: 10, unit: 'MINUTES') { // 타임아웃 설정
                    checkout([$class: 'GitSCM', 
                              branches: [[name: 'main']], 
                              userRemoteConfigs: [[credentialsId: 'Hongik-Test', 
                                                    url: 'https://github.com/guraudrk/softwareengineering.git']]])
                }
            }
        }
        
        stage('Build') {
            steps {
                timeout(time: 20, unit: 'MINUTES') { // 타임아웃 설정
                    script {
                        def mvnHome = tool name: 'Maven', type: 'maven'
                        bat "${mvnHome}\\bin\\mvn clean install"
                    }
                }
            }
        }
        
        stage('Test') {
            steps {
                timeout(time: 20, unit: 'MINUTES') { // 타임아웃 설정
                    script {
                        def mvnHome = tool name: 'Maven', type: 'maven'
                        bat "${mvnHome}\\bin\\mvn test"
                    }
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Performance Test') {
            steps {
                timeout(time: 30, unit: 'MINUTES') { // 타임아웃 설정
                    script {
                        def mvnHome = tool name: 'Maven', type: 'maven'
                        bat "${mvnHome}\\bin\\mvn exec:java -Dexec.mainClass=\"com.example.PerformanceTest\""
                    }
                }
            }
            post {
                always {
                    archiveArtifacts artifacts: '**/performance-reports/**', allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
