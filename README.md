# JDBC HTTP Server

This is an HTTP server which connects tp a PosgreSQL database. It does not use any dedicated frameworks.

**Deployed URL**: https://brianjleeofcl-jdbc-server-test.herokuapp.com/

## API Reference
### route `/users`
#### Method `GET`
retrieves all users as a JSON Array.

#### Method `POST`
inserts new user information provided as body of request; data should be sent in JSON.

**required**: `first_name`, `last_name` and `phone_number` 

### route `/users/:id`
#### Method `GET`
retrieves specific user which matches the id on database in JSON.

## Dependencies
##### external:
- slf4j: Logging
- junit: Testing
- posgresql: PosgreSQL Driver
- org.json: JSON utility

##### packages:
- java.sql: JDBC
- com.sun.net.httpserver: HTTP server

