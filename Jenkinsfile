pipeline {
    agent any
    
    environment {
        GIT_CREDENTIALS = credentials('Hongik-Test')
    }
    
    tools {
        maven 'Maven'
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
                        script {
                            // 디버깅을 위해 파일 목록 출력
                            bat 'dir'
                            // MAVEN_SETTINGS 경로 출력
                            bat 'echo MAVEN_SETTINGS=%MAVEN_SETTINGS%'
                            // Maven을 사용하여 빌드 실행하는 디렉토리 설정
                            dir('softwareengineering') {
                                bat "mvn clean install --settings %MAVEN_SETTINGS%"
                            }
                        }
                    }
                }
            }
        }
        
        // 나머지 stage들은 이하 동일하게 처리
    }

    post {
        always {
            cleanWs() // Workspace 정리
        }
    }
}
