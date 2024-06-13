pipeline {
    agent any
    
    environment {
        GIT_CREDENTIALS = credentials('Hongik-Test')
    }
    
    tools {
        // Global Tool Configuration에서 설정한 Maven Tool 사용
        maven 'Maven'
    }
    
    stages {
        stage('Checkout SCM') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
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
                    timeout(time: 20, unit: 'MINUTES') {
                        script {
                            // 디버깅을 위해 파일 목록 출력
                            bat 'dir'
                            // MAVEN_SETTINGS 경로 출력
                            bat 'echo MAVEN_SETTINGS=%MAVEN_SETTINGS%'
                            // Maven을 사용하여 빌드 실행
                            bat "mvn clean install --settings %MAVEN_SETTINGS%"
                        }
                    }
                }
            }
        }
        
        stage('Test') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 20, unit: 'MINUTES') {
                        script {
                            // Maven을 사용하여 테스트 실행
                            bat "mvn test --settings %MAVEN_SETTINGS%"
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
                    timeout(time: 30, unit: 'MINUTES') {
                        script {
                            // Maven을 사용하여 성능 테스트 실행
                            bat "mvn exec:java -Dexec.mainClass=\"com.example.PerformanceTest\" --settings %MAVEN_SETTINGS%"
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
