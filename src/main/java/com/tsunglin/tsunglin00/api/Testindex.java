/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testindex {

    @GetMapping("/")
    public String hello() {
        return "Hello Spring Boot with Docker";
    }

}
/*
docker
cd /Users/tsunglin/desktop/workspace-spring-tool-suite-4-4.13.0.RELEASE/springbootvue3/springboot-api
建立Dockerfile於根目錄
docker build -t tsunglin00 .
docker run -p 28019:28019 tsunglin00
停止docker ps或docker container ls
docker stop <container_id>或docker stop <container_name>
docker image rm tsunglin00

maven -> install

java -jar tsunglin00-3.0.0-SNAPSHOT.jar


docker login
docker tag tsunglin00 garnettlin21/tsunglin00
docker push garnettlin21/tsunglin00
docker rmi garnettlin21/tsunglin00 tsunglin00   docker image rm tsunglin00
docker run -p 28019:28019 garnettlin21/tsunglin00

docker run -p 28019:28019 gcr.io/docker-with-kubernetes-engine/tsunglin00:latest

docker image rm gcr.io/docker-with-kubernetes-engine/tsunglin00:latest
sudo docker build --platform linux/amd64 -t gcr.io/docker-with-kubernetes-engine/tsunglin00:latest .
sudo docker build --platform linux/arm64 -t gcr.io/docker-with-kubernetes-engine/tsunglin00:latest .

https://cloud.google.com/sdk/docs/install-sdk
gcloud auth configure-docker
gcloud auth application-default login
sudo docker build -t gcr.io/docker-with-kubernetes-engine/tsunglin00:latest .
sudo docker -- push gcr.io/docker-with-kubernetes-engine/tsunglin00:latest

創建GCM   運算   docker-with-kubernetes
docker-k8s-demo-deployment.yaml             image位址以工作負載/部屬/選取容器映像檔
docker-k8s-demo-service.yaml

利用面板把剛創建docker-k8s-demo-deployment.yaml docker-k8s-demo-service.yaml    上傳
kubectl apply -f . //一把發布所有Yaml檔
kubectl get all //顯示所有資源
kubectl delete Deployment docker-k8s-demo-deployment

https://kubernetes.io/docs/tasks/tools/install-kubectl-macos/
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/darwin/arm64/kubectl"
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/darwin/arm64/kubectl.sha256"
echo "$(cat kubectl.sha256)  kubectl" | shasum -a 256 --check
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
sudo chown root: /usr/local/bin/kubectl
kubectl version --client
kubectl version --client --output=yaml



刪除集群上已部署的POD
kubectl delete -f deployment.yml
重新部署更新Kubernetes部署
kubectl apply -f deployment.yml







/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java -jar /Users/tsunglin/desktop/工具/jenkins/jenkins.war
http://localhost:8080/
86e76224206f4346b13edf3bfb36bb72

Global Tool Configuration
JDK     /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/
MAVEN   /Users/tsunglin/Desktop/工具/jenkins/apache-maven-3.8.5
*/