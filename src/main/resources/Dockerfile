FROM eclipse-temurin:21.0.3_9-jdk-alpine
ARG JAR_FILE=arrowdb-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]