pipeline {
    agent any

    environment {
        GIT_CREDENTIALS = credentials('Hongik-Test')
        MAVEN_HOME = tool name: 'Maven', type: 'maven'
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
                            // 프로젝트 루트 디렉토리로 이동
                            dir('softwareengineering') {
                                // Maven Wrapper를 사용하여 빌드
                                bat ".\\mvnw.cmd clean install --settings %MAVEN_SETTINGS%"
                            }
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
                            // 프로젝트 루트 디렉토리로 이동
                            dir('softwareengineering') {
                                // Maven Wrapper를 사용하여 테스트
                                bat ".\\mvnw.cmd test --settings %MAVEN_SETTINGS%"
                            }
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
                            // 프로젝트 루트 디렉토리로 이동
                            dir('softwareengineering') {
                                // Maven Wrapper를 사용하여 성능 테스트
                                bat ".\\mvnw.cmd exec:java -Dexec.mainClass=\"com.example.PerformanceTest\" --settings %MAVEN_SETTINGS%"
                            }
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
