# linkStartBackend
Back-end web services (REST API + DB) developped with Spring Boot, mysql and Docker for a Discord bot !  
<br>

## Install
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

## Run
Run the whole infrastructure:  
```
docker-compose up
```
<br>

## Test
Make request with Postman, curl, or other tool to test the API.
<br>
<br>

Add a member with this HTTP POST method:
```
http://localhost:8080/api/members
```
And this body:
```
{
    "username": "Martin",
    "honor": 1
}
```
The server should albready return you the member created.
<br>
<br>

Retrieve the members with HTTP GET method:
```
http://localhost:8080/api/members
```
