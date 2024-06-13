pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // 소스코드 체크아웃
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                // Java 파일들을 컴파일하여 생성된 클래스 파일을 classes 디렉토리에 저장
                bat 'javac -encoding UTF-8 -d classes Practice\\src\\**\\*.java'
            }
        }
        
        stage('Test') {
            steps {
                // JUnit 5 테스트 실행을 위한 classpath 설정
                script {
                    def classpath = "classes;lib\\;path\\to\\eclipse\\plugins\\junit-platform-console-standalone-1.7.1.jar"
                    // JUnit 5 테스트 실행
                    bat "java -cp ${classpath} org.junit.platform.console.ConsoleLauncher --scan-classpath > test_results.txt"
                }
            }
        }
    }
    
    post {
        always {
            // 테스트 결과 파일을 저장하기 위해 아카이브
            archiveArtifacts 'test_results.txt'
        }
        failure {
            echo 'Build or test failed'
        }
        success {
            echo 'Build and test succeeded'
        }
    }
}
