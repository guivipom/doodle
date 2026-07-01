# Doodle API

## Overview

API that simulates a meeting scheduling platform using Spring Boot and Java. The service enables users to manage their
time slots, schedule meetings, and view their custom calendar availability.

## Prerequisites

- **Java 25 JDK** 
- **Docker**
- **Maven 3.6.3+** or use the included Maven wrapper `./mvnw`

## How to Run

```bash
docker-compose up --build
```
The application will be available at http://localhost:8080.
- Health Check: http://localhost:8080/actuator/health

## API Endpoints

### Create a user

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Guillermo Vidal", "email": "guillermo@example.com"}'
```

Response: 201 Created

{
  "id": 1,
  "name": "Guillermo Vidal",
  "email": "guillermo@example.com",
  "createdAt": "..."
}

### Get a user by ID

```bash
curl http://localhost:8080/api/users/1
```

Response: 200 OK
{
  "id": 1,
  "name": "Guillermo Vidal",
  "email": "guillermo@example.com",
  "createdAt": "..."
}

### Get all users
```bash
curl http://localhost:8080/api/users
```
Response: 200 OK
[
  {
    "id": 1,
    "name": "Guillermo Vidal",
    "email": "guillermo@example.com",
    "createdAt": "..."
  },
    {
    "id": 2,
    "name": "Alejandro Vidal",
    "email": "alejandro@example.com",
    "createdAt": "..."
    }
]