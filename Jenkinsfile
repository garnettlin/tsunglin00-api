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
        /*
        stage("Build image") {
            steps {
                script {
                    myapp = docker.build("gcr.io/macro-resource-353407/tsunglin00:latest")
                }
            }
        }
        stage("Push image") {
            steps {
                script {
                    //docker.withRegistry('https://gcr.io', 'dockerhub') {
                            //myapp.push("latest")
                    //}
                    withDockerRegistry(credentialsId: 'source:macro-resource-353407', toolName: 'docker', url: 'gcr.io/hopeful-theorem-353306/tsunglin00:latest') {
                        myapp.push("latest")
                    }
                }
            }
        }
        */
         stage('Build-Docker') {
                    steps {
                        //sh 'docker build -t gcr.io/macro-resource-353407/tsunglin00:latest .'
                        sh ' chmod 777 start.sh'
                        sh './start.sh'
                    }
        }
    }

}