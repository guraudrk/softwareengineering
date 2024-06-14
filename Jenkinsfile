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
        }

        stage('Performance Test') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings-xml', variable: 'MAVEN_SETTINGS')]) {
                    timeout(time: 30, unit: 'MINUTES') {
                        bat(/"%MAVEN_HOME%\bin\mvn" exec:java -Dexec.mainClass="com.example.PerformanceTest" --settings "%MAVEN_SETTINGS%"/)
                    }
                }
            }
        }
    }

    post {
  
