# Build stage
FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /usr/linkstart-api
COPY src src
COPY pom.xml .
RUN mvn -f pom.xml clean package -Dmaven.test.failure.ignore=true

# Package stage
FROM amazoncorretto:18-alpine-jdk
COPY --from=build target/linkstart-backend-0.0.1-SNAPSHOT.jar linkstart-backend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "linkstart-backend.jar"]