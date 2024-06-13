pipeline {
    agent any
    
    environment {
        GIT_CREDENTIALS = credentials('Hongik-Test') // Jenkins에 저장된 크리덴셜 ID 사용
    }
    
    tools {
        // Maven을 자동으로 설치할 수 있도록 설정
        maven 'Maven'
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
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 20, unit: 'MINUTES') { // 타임아웃 설정
                        script {
                            // Maven Wrapper를 사용하여 빌드 실행 (Windows에서는 .\mvnw.cmd를 사용)
                            bat ".\\mvnw.cmd clean install --settings %MAVEN_SETTINGS%"
                        }
                    }
                }
            }
        }
        
        stage('Test') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 20, unit: 'MINUTES') { // 타임아웃 설정
                        script {
                            // Maven Wrapper를 사용하여 테스트 실행 (Windows에서는 .\mvnw.cmd를 사용)
                            bat ".\\mvnw.cmd test --settings %MAVEN_SETTINGS%"
                        }
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
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 30, unit: 'MINUTES') { // 타임아웃 설정
                        script {
                            // Maven Wrapper를 사용하여 성능 테스트 실행 (Windows에서는 .\mvnw.cmd를 사용)
                            bat ".\\mvnw.cmd exec:java -Dexec.mainClass=\"com.example.PerformanceTest\" --settings %MAVEN_SETTINGS%"
                        }
                    }
                }
            }
            post {
                always {
                    // 성능 테스트 결과 아카이브
                    archiveArtifacts artifacts: '**/performance-reports/**', allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            cleanWs() // Workspace 정리
        }
    }
}
