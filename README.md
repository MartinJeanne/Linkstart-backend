# linkStartBackend
Back-end web services (REST API + DB) developped with Spring Boot, mysql and Docker for a Discord bot !

## Install & Run
Install dependecies & build jar file :
`mvn clean install -Dmaven.test.failure.ignore=true`

Create the docker image for the rest api :
`docker build -t linkstartapi .`

Finally, run the whole infrastructure :
`docker-compose up`
