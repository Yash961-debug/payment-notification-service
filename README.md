# Payment Notification Service

A Spring Boot REST API for managing payment notifications and payment status transitions.

The application allows users to create payment notifications, retrieve payment details, update payment status, validate payment state transitions, and retrieve payments based on their status. It follows a layered architecture using Spring Boot, Spring Data JPA, and an H2 in-memory database.

---

# Tech Stack

- Java 21
- Spring Boot 3.5.4
- Spring Web
- Spring Data JPA
- Bean Validation (Jakarta Validation)
- H2 In-Memory Database
- Maven
- Spring Boot Test
- JUnit 5
- Mockito
- MockMvc
- JaCoCo

---

# Features

- Create Payment Notification
- Retrieve Payment by ID
- Update Payment Status
- Retrieve Payments by Status
- Payment State Transition Validation
- Global Exception Handling
- Request Validation
- Logging Interceptor
- Unit Testing
- Controller Integration Testing
- Spring Boot Application Context Testing
- JaCoCo Code Coverage

---

# Project Structure

```
src
├── main
│   ├── java
│   │   └── com.example.paymentnotificationservice
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── interceptor
│   │       ├── repository
│   │       ├── service
│   │       ├── validator
│   │       └── PaymentNotificationServiceApplication.java
│   │
│   └── resources
│       └── application.properties
│
└── test
    └── java
        └── com.example.paymentnotificationservice
            ├── PaymentControllerTest.java
            ├── PaymentServiceTest.java
            └── PaymentNotificationServiceApplicationTests.java
```

---

# Payment State Flow

```
SCHEDULING
      |
      ▼
PROCESSING
   /       \
  ▼         ▼
COMPLETED RETURNED
```

### Allowed State Transitions

- SCHEDULING → PROCESSING
- PROCESSING → COMPLETED
- PROCESSING → RETURNED

Invalid state transitions return an appropriate error response.

---

# Prerequisites

- Java 21
- Maven 3.9+
- Git

---

# Clone the Repository

```bash
git clone <repository-url>
cd payment-notification-service
```

---

# Running the Application

## Windows

Start the application

```cmd
.\mvnw.cmd spring-boot:run
```

---

## Linux / macOS

```bash
./mvnw spring-boot:run
```

---

The application starts on

```
http://localhost:8080
```

The project uses an **H2 In-Memory Database**, so no additional database installation is required.

---

# Running Tests

## Windows

Run all tests

```cmd
.\mvnw.cmd test
```

Clean the project and run all tests

```cmd
.\mvnw.cmd clean test
```

---

## Linux / macOS

Run all tests

```bash
./mvnw test
```

Clean the project and run all tests

```bash
./mvnw clean test
```

---

# Generate JaCoCo Report

## Windows

```cmd
.\mvnw.cmd clean test
```

## Linux / macOS

```bash
./mvnw clean test
```

Open the generated report:

```
target/site/jacoco/index.html
```

---

# REST API Summary

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/v1/payments/notify` | Create a new payment |
| GET | `/api/v1/payments/{id}` | Retrieve payment by ID |
| PATCH | `/api/v1/payments/{id}` | Update payment status |
| GET | `/api/v1/payments?status={status}` | Retrieve payments by status |

---

# API Examples

## Create Payment

**POST**

```
/api/v1/payments/notify
```

### Request

```json
{
  "applicationId": "APP1001",
  "amount": 2500,
  "paymentMethod": "CREDIT_CARD"
}
```

### Response

```json
{
  "id": 1,
  "applicationId": "APP1001",
  "amount": 2500,
  "paymentMethod": "CREDIT_CARD",
  "status": "SCHEDULING"
}
```

---

## Get Payment

**GET**

```
/api/v1/payments/1
```

---

## Update Payment Status

**PATCH**

```
/api/v1/payments/1
```

### Request

```json
{
  "status": "PROCESSING"
}
```

---

## Get Payments by Status

**GET**

```
/api/v1/payments?status=PROCESSING
```

---

# cURL Examples

## Create Payment

```bash
curl -X POST http://localhost:8080/api/v1/payments/notify \
-H "Content-Type: application/json" \
-d "{\"applicationId\":\"APP1001\",\"amount\":2500,\"paymentMethod\":\"CREDIT_CARD\"}"
```

---

## Get Payment

```bash
curl http://localhost:8080/api/v1/payments/1
```

---

## Update Payment Status

```bash
curl -X PATCH http://localhost:8080/api/v1/payments/1 \
-H "Content-Type: application/json" \
-d "{\"status\":\"PROCESSING\"}"
```

---

## Get Payments by Status

```bash
curl http://localhost:8080/api/v1/payments?status=PROCESSING
```

---

# Testing

The project includes unit tests, integration tests, and application context tests to verify business logic, REST APIs, and Spring Boot configuration.

## Testing Frameworks

- Spring Boot Test
- JUnit 5
- Mockito
- MockMvc
- JaCoCo

---

## Test Classes

### PaymentServiceTest

Unit tests for the service layer using Mockito.

Covered Scenarios:

- Create payment notification
- Retrieve payment by ID
- Payment not found exception
- Update payment status
- Validate payment state transitions
- Completed payment notification
- Returned payment notification
- Retrieve payments by status

---

### PaymentControllerTest

Integration tests using Spring Boot Test and MockMvc.

Covered APIs:

- POST `/api/v1/payments/notify`
- GET `/api/v1/payments/{id}`

---

### PaymentNotificationServiceApplicationTests

Spring Boot application context test.

Purpose:

- Verifies the Spring Boot application starts successfully.
- Ensures the Spring Application Context loads correctly.
- Validates bean initialization and application configuration.

---

# Code Coverage

JaCoCo is used to generate code coverage reports.

### Current Coverage

-  Service Layer Coverage: **93%**
-  Unit Tests Implemented
-  Controller Integration Tests Implemented
-  Spring Boot Application Context Test Implemented

Coverage Report:

```
target/site/jacoco/index.html
```

---


