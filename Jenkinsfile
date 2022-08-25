pipeline {
    agent any
    tools {
       maven 'maven' //這裡填剛才全局配置maven時候的寫的哪個名稱.
       dockerTool 'docker' //這裡填剛才全局配置maven時候的寫的哪個名稱
    }
    stages {
        stage('Build-Package') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
         stage('Build-Docker') {
                    steps {
                        //sh 'docker build -t gcr.io/macro-resource-353407/tsunglin00:latest .'
                        sh ' chmod 777 start.sh'
                        sh './start.sh'
                    }
        }
    }

}