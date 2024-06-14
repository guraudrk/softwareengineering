pipeline {
    agent any

    environment {
        GIT_CREDENTIALS = credentials('Hongik-Test')
        MAVEN_HOME = tool name: 'Maven', type: 'maven'
    }

    stages {
        stage('Checkout SCM') {
            steps {
                checkout([$class: 'GitSCM', 
                          branches: [[name: 'main']], 
                          userRemoteConfigs: [[credentialsId: 'Hongik-Test', 
                                                url: 'https://github.com/guraudrk/softwareengineering.git']]])
            }
        }

        stage('Build') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 20, unit: 'MINUTES') {
                        bat(/"%MAVEN_HOME%\bin\mvn" clean install --settings "%MAVEN_SETTINGS%"/)
                    }
                }
            }
        }

        stage('Unit Tests') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 20, unit: 'MINUTES') {
                        bat(/"%MAVEN_HOME%\bin\mvn" test --settings "%MAVEN_SETTINGS%"/)
                    }
                }
            }
            post {
                always {
                   // 테스트 결과 파일을 저장하기 위해 아카이브
 archiveArtifacts 'test_results.txt'

                }
            }
        }

        stage('Performance Test') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 30, unit: 'MINUTES') {
                        bat(/"%MAVEN_HOME%\bin\mvn" exec:java -Dexec.mainClass="com.example.PerformanceTest" --settings "%MAVEN_SETTINGS%"/)
                    }
                }
            }
            post {
                always {
                    // 성능 테스트 결과 아카이브
                    archiveArtifacts artifacts: 'performance-reports/**', allowEmptyArchive: true
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
