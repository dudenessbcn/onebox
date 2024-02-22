FROM maven:3.8.4-openjdk-11 as maven-builder
COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean package -DskipTests
FROM openjdk:11

COPY --from=maven-builder app/target/onebox-1.0-SNAPSHOT.jar /app-service/onebox-1.0-SNAPSHOT.jar
WORKDIR /app-service

EXPOSE 8080
ENTRYPOINT ["java","-jar","onebox-1.0-SNAPSHOT.jar"]