FROM openjdk:18
COPY target/linkstart-backend-0.0.1-SNAPSHOT.jar linkstart-backend-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "linkstart-backend-0.0.1-SNAPSHOT.jar"]