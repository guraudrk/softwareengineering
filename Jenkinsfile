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
                            // 프로젝트 디렉토리로 이동
                            dir('softwareengineering') {
                                // Maven을 MAVEN_HOME 경로에서 실행하여 빌드
                                bat "\"%MAVEN_HOME%\\bin\\mvn\" clean install --settings \"%MAVEN_SETTINGS%\""
                            }
                        }
                    }
                }
            }
        }

        stage('Unit Tests') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 20, unit: 'MINUTES') {
                        script {
                            // 프로젝트 디렉토리로 이동
                            dir('softwareengineering') {
                                // Maven을 MAVEN_HOME 경로에서 실행하여 단위 테스트
                                bat "\"%MAVEN_HOME%\\bin\\mvn\" test --settings \"%MAVEN_SETTINGS%\""
                            }
                        }
                    }
                }
            }
            post {
                always {
                    // JUnit 테스트 결과 아카이브
                    junit '**/softwareengineering/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Performance Test') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 30, unit: 'MINUTES') {
                        script {
                            // 프로젝트 디렉토리로 이동
                            dir('softwareengineering') {
                                // Maven을 MAVEN_HOME 경로에서 실행하여 성능 테스트
                                bat "\"%MAVEN_HOME%\\bin\\mvn\" exec:java -Dexec.mainClass=\"com.example.PerformanceTest\" --settings \"%MAVEN_SETTINGS%\""
                            }
                        }
                    }
                }
            }
            post {
                always {
                    // 성능 테스트 결과 아카이브
                    archiveArtifacts artifacts: '**/softwareengineering/performance-reports/**', allowEmptyArchive: true
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
