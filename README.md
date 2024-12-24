# User API Project

## Overview

The **User API** is a RESTful service designed for managing user accounts. It allows users to register, generates JWT tokens for authentication, and provides the ability to list all registered users. The project ensures secure validation of user credentials and is built using **Java 17**, **Spring Boot**, and **H2 Database**.

---

## Features

- **User Registration**: Registers a user and generates a secure JWT token.
- **JWT Authentication**: Provides token-based authentication for secure API interactions.
- **Phone Management**: Supports storing and managing multiple phone numbers per user.
- **Swagger Integration**: Interactive API documentation with testing capabilities.

---

## Prerequisites

To run the project, ensure you have the following installed:

- **Java 17 or higher**
- **Gradle** (optional if using the Gradle wrapper provided)

---

## Installation and Running the Project

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/jpzambrano/Sermaluc.git
   cd Sermaluc
   ```

2. **Run the Application**:

   - Using Gradle Wrapper:
     ```bash
     ./gradlew bootRun
     ```

3. **Access Swagger UI**:
   Open your browser and navigate to:
   ```plaintext
   http://localhost:8080/swagger-ui/index.html
   ```

---

## API Endpoints

### **Base URL**

```plaintext
http://localhost:8080/api/users
```

### **1. Register User**

**Endpoint**: `POST /register`

**Description**: Registers a new user with validated credentials and generates a JWT token.

**Request Body**:

```json
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
```

**Response**:

- **201 Created**:
  ```json
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
  ```

---

## Configuration

1. **Application Properties**:

   - Located in `src/main/resources/application.properties`.
   - Example:
     ```properties
     spring.datasource.url=jdbc:h2:mem:testdb
     spring.datasource.username=sa
     spring.datasource.password=
     jwt.secret=your-secure-key
     ```

2. **Swagger**:

   - Accessible at:
     ```plaintext
     http://localhost:8080/swagger-ui/index.html
     ```

3. **H2 Console**:
   - Accessible at:
     ```plaintext
     http://localhost:8080/h2-console
     ```
   - Default credentials:
     - Username: `sa`
     - Password:

---

## Testing

Run unit and integration tests using Gradle:

```bash
./gradlew test
```

---

## Technologies Used

- **Java 17**: Language used for implementation.
- **Spring Boot**: Framework for REST API development.
- **H2 Database**: In-memory database for persistence.
- **JWT**: For token-based authentication.
- **Lombok**: Reduces boilerplate code.
- **Swagger**: Interactive API documentation.
- **Gradle**: Build and dependency management.
