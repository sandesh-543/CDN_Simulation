FROM openjdk:21-jdk-slim
LABEL authors="sandesh"

WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
