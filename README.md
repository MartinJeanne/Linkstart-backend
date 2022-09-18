# linkStartBackend
Back-end web services (REST API + DB) developped with Spring Boot, mysql and Docker for a Discord bot !  
<br>

## Install & Run
Install dependencies & build jar file:  
```
mvn clean install -Dmaven.test.failure.ignore=true
```
<br>

Create the docker image of the rest api:  
```
docker build -t linkstartapi .
```
<br>

Create an .env file at the root of the project, and specify this information:
```
MYSQLDB_DATABASE=databaseName
MYSQLDB_ROOT_PASSWORD=theRootPassword
MYSQL_USER=aUser
MYSQL_PASSWORD=theUserPassword
```
<br>

Finally, run the whole infrastructure:  
```
docker-compose up
```
