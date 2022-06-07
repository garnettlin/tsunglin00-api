pipeline {

    agent any
    tools {
       maven 'maven' //這裡填剛才全局配置maven時候的寫的哪個名稱
    }
    stages {
        stage('Build-Package') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

         stage('Build-Docker') {
                    steps {
                        sh ' chmod 777 start.sh'

                        sh './start.sh'
                    }
        }

    }

}