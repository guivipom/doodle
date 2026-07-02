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
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## API Endpoints

### Create a user

```bash
curl -X POST http://localhost:8080/api/users/create \
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
curl http://localhost:8080/api/users/all
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

### Create a slot:
```bash
   curl -X POST http://localhost:8080/api/users/1/slots \
   -H "Content-Type: application/json" \
   -d '{
   "startTime": "2026-07-01T09:00:00",
   "endTime": "2026-07-01T10:00:00",
   "status": "FREE"
   }'
```
### Get all slots for a user 
```bash
   curl http://localhost:8080/api/users/1/slots
```
### Get a specific slot:
```bash
curl http://localhost:8080/api/users/1/slots/1
```

### Update a slot:
```bash
   curl -X PUT http://localhost:8080/api/users/1/slots/99 \
   -H "Content-Type: application/json" \
   -d '{
   "startTime": "2026-07-02T14:00:00",
   "endTime": "2026-07-02T15:00:00",
   "status": "BUSY"
   }'
```

### Create a meeting
# Converts a FREE slot into a meeting. The slot will be marked BUSY automatically.
```bash
curl -X POST http://localhost:8080/api/users/1/meetings \
-H "Content-Type: application/json" \
-d '{
"slotId": 1,
"title": "Title",
"description": "description",
"participantIds": [2, 3]
}'
```

### Get a meeting by ID
```bash
curl http://localhost:8080/api/users/1/meetings/1
```

### Get all meetings for an organizer
```bash
curl http://localhost:8080/api/users/1/meetings
```
