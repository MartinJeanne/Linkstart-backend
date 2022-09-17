FROM maven:3.8.6-openjdk-18

WORKDIR ./
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run