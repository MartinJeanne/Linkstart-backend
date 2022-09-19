FROM openjdk:18
ADD target/discordRestApi-0.0.1-SNAPSHOT.jar discordRestApi-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "discordRestApi-0.0.1-SNAPSHOT.jar"]