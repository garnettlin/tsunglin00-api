FROM openjdk:8-jdk-alpine
COPY ./target/*.jar ./mydocker/demo.jar
WORKDIR ./mydocker
RUN sh -c 'touch demo.jar'
ENTRYPOINT ["java","-jar","demo.jar"]
