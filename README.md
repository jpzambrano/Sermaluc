User API Project
Description
The User API is a RESTful API that manages user registration and authentication with JWT tokens. It validates user credentials using configurable regular expressions and allows listing all registered users.

Features
Register users with validated email and password.
Automatically generate JWT tokens upon registration.
Store and retrieve user phone details.
List all active users.
Swagger UI integration for API documentation.
Prerequisites
To run this project, ensure you have:

Java 17+
Gradle (or use the provided Gradle Wrapper)
An IDE (e.g., IntelliJ IDEA, VS Code) or terminal access
Installation
Clone the Repository:

bash
Copiar código
git clone https://github.com/jpzambrano/Sermaluc.git
cd Sermaluc
Run the Application:

Using Gradle Wrapper:
bash
Copiar código
./gradlew bootRun
Access Swagger Documentation:

Open your browser and navigate to:
plaintext
Copiar código
http://localhost:8080/swagger-ui/index.html
Endpoints
Base URL
plaintext
Copiar código
http://localhost:8080/api/users

1. Register User
   Endpoint: POST /register

Description: Registers a new user with validated credentials and generates a JWT token.

Request Body:

json
Copiar código
{
"name": "John Doe",
"email": "john.doe@example.com",
"password": "Password@123",
"phones": [
{
"number": "123456789",
"cityCode": "1",
"countryCode": "57"
}
]
}
Response:

201 Created:
json
Copiar código
{
"id": "123e4567-e89b-12d3-a456-426614174000",
"name": "John Doe",
"email": "john.doe@example.com",
"created": "2024-12-22T15:00:00",
"modified": "2024-12-22T15:00:00",
"lastLogin": "2024-12-22T15:00:00",
"token": "eyJhbGciOiJIUzI1NiIsInR5...",
"active": true
}
400 Bad Request: Invalid email or password format.
409 Conflict: Email already registered. 2. List All Users
Endpoint: GET /list

Description: Retrieves all active users.

Response:

200 OK:
json
Copiar código
[
{
"id": "123e4567-e89b-12d3-a456-426614174000",
"name": "John Doe",
"email": "john.doe@example.com",
"active": true,
"phones": [
{
"number": "123456789",
"cityCode": "1",
"countryCode": "57"
}
]
}
]
404 Not Found: No users registered.
How to Use Swagger
Swagger provides an interactive UI for testing the API.

Start the application.
Open your browser and navigate to:
plaintext
Copiar código
http://localhost:8080/swagger-ui/index.html
Explore and test the endpoints.

Database
H2 In-Memory Database:
The API uses H2 for persistence during runtime.
Access the H2 Console (optional) at:
plaintext
Copiar código
http://localhost:8080/h2-console
Default credentials:
Username: sa
Password:
Testing
Run the tests using Gradle:

bash
Copiar código
./gradlew test
Expected Output:

Unit tests validate key functionalities (e.g., user validation, JWT token generation).
Integration tests confirm end-to-end functionality.
Technologies Used
Spring Boot: REST API framework.
H2 Database: In-memory persistence.
SpringDoc OpenAPI: Swagger integration.
Lombok: Simplifies code with annotations.
JWT: Authentication via JSON Web Tokens.
Gradle: Build and dependency management.
