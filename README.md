# linkStartBackend
Back-end web services (REST API + DB) developped with Spring Boot, mysql and Docker for a Discord bot !  

## Install & Run
Install dependecies & build jar file:  
```
mvn clean install -Dmaven.test.failure.ignore=true
```

Create the docker image for the rest api:  
```
docker build -t linkstartapi .
```

Create a .env file at the root of the project, and specify this information:
```
MYSQLDB_DATABASE=databaseName
MYSQLDB_ROOT_PASSWORD=theRootPassword
MYSQL_USER=aUser
MYSQL_PASSWORD=TheUserPassword
```

Finally, run the whole infrastructure:  
```
docker-compose up
```
