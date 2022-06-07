FROM openjdk:8-jdk-alpine
COPY ./target/*.jar /Users/tsunglin/desktop/mydocker/demo.jar
WORKDIR /Users/tsunglin/desktop/mydocker
RUN sh -c 'touch demo.jar'
ENTRYPOINT ["java","-jar","demo.jar"]
