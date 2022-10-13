# Build stage
FROM maven:3.8.6-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.failure.ignore=true

# Package stage
FROM amazoncorretto:18-alpine-jdk
COPY --from=build /home/app/target/linkstart-backend-0.0.1-SNAPSHOT.jar linkstart-backend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "linkstart-backend.jar"]