# JDBC HTTP Server

This is an HTTP server which connects tp a PosgreSQL database. It does not use any dedicated frameworks.

**Deployed URL**: https://brianjleeofcl-jdbc-server-test.herokuapp.com/

## API Reference
### route `/users`
#### Method `GET`
retrieves all users as a JSON Array; will send `[]` if no users are saved in database.

#### Method `POST`
inserts new user information provided as body of request; data should be sent in JSON.

**required**: `first_name`, `last_name` and `phone_number` 

### route `/users/:id`
#### Method `GET`
retrieves specific user which matches the id on database in JSON.

## Dependencies
##### development:
- Java 8
- PostgreSQL 
- Gradle
- IntelliJ IDEA

##### external:
- slf4j: Logging
- junit: Testing
- posgresql: PosgreSQL Driver
- org.json: JSON utility

##### packages:
- java.sql: JDBC
- com.sun.net.httpserver: HTTP server

## Local Development
- Database defaults to `testdb`; run `$ createdb testdb` prior to running locally.
- `$ gradle run` starts the server with default port 5000