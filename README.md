# ChâTop

## Description
The ChaTop API operates as the server for the ChaTop application.
Built with Spring Boot 3 and Java 17, it integrates Spring-doc OpenAPI and Swagger UI to provide thorough documentation.  

A Front-End application using this API is here :  
- https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring

## Installation

Prior to executing the ChaTop Backend API, adhere to these installation procedures.:

1. Clone this repository.
2. Install JDK 17 (Java Development Kit) on your local machine.
3. Install Maven locally.
4. Install MySQL (on your local machine or in a Docker container) and create a database for the application.
5. Configure the necessary environment variables in your system or within your IDE before running the application.

### 1. Create a new database in your MySQL server:

   Open a terminal and run the command

  ```bash
  mysql -u mysql_username -p < chatop.sql
  ```

Important Note: Replace `mysql_username` with your actual MySQL username.

### 2. Update the application.properties file with the correct server, database, and security variables:

  You may use a .env file (recommended), or replace the variables in the application.properties file directly.
  
  The following variables need to updated with your own values
  
  ```properties
  server.port=${SERVER_PORT} #example: 3001
  server.url=${SERVER_URL} #example: http://localhost:3001
  
  #use the database URL, username, and password from your MySQL server
  spring.datasource.url=${DB_URL}
  spring.datasource.username=${DB_USERNAME}
  spring.datasource.password=${DB_PASSWORD}
  
  jwt.key=${JWT_KEY} #a secure, random string (UUID recommended)
  client.url=${CLIENT_URL} #example: http://localhost:4200
  springdoc.swagger-ui.oauth.clientSecret=${JWT_KEY} #the same secure, random string (UUID recommended) used for jwt.key
  ```

### 3. Build the project and install its dependencies:

```bash
mvn clean install
```

### 4. Launch the application

```bash
mvn spring-boot:run
```

Once the server is running locally, you can access the API endpoints at http://localhost:3001/api/.

Note: Be sure to replace `3001` with your server port in the URL.

## Swagger Documentation

After launching the application locally, the Swagger documentation can be found at http://localhost:3001/swagger-ui/index.html

Note: Be sure to replace `3001` with your server port in the URL.

From here, you can access all routes that do not require authentication.

To access authenticated routes:
- Either login with the test user or register a new user and login. 
- Copy the returned jwt token, click the authorize button, paste the token into the form in the modal and click authorize. 
- You will now be able to access authenticated routes as this user until you click the authorize button again and then click logout.
