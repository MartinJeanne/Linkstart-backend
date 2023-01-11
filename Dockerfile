# Build stage
FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /usr/linkstart-api
COPY src src
COPY pom.xml .
RUN mvn -f pom.xml clean package -Dmaven.test.skip

# Package stage
FROM amazoncorretto:18-alpine-jdk
WORKDIR /usr/linkstart-api
COPY .env .
COPY --from=build /usr/linkstart-api/target/linkstart-api-1.0.0-SNAPSHOT.jar linkstart-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "linkstart-api.jar"]
