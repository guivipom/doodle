# Doodle API

## Overview

API that simulates a meeting scheduling platform using Spring Boot and Java. The service enables users to manage their
time slots, schedule meetings, and view their custom calendar availability.

## Tech Stack & Design Decisions

The service is built with **Spring Boot 4**, **PostgreSQL 17**, and **Spring Data JPA + Hibernate** for persistence. Schema migrations are managed by **Flyway**. **MapStruct** handles DTO mapping at compile time. **springdoc-openapi** auto-generates the Swagger UI directly from the code. Tests are written with **JUnit 5 + Mockito** for unit tests and **Testcontainers** for integration tests against a real PostgreSQL instance. The Docker setup uses a **multi-stage build** to keep the final image lean.

The service follows a classic layered architecture (Controller → Service → Repository) to keep concerns separated. All entity relationships use `FetchType.LAZY` with OSIV disabled (`open-in-view: false`) to avoid unnecessary queries and keep transaction boundaries explicit. Database indexes are placed on the most frequent query paths (`user_id`, `organizer_id`, `(user_id, status)`) to support the expected load of hundreds of users with thousands of slots.

### Concurrency

During the meeting creation two different users could try to book the same slot simultaneously. Currently we handle it at two levels, the entire operation runs inside a `@Transactional` boundary, so the slot status check and the meeting creation are atomic. Second, the `slot_id` column in the `meetings` table has a `UNIQUE` constraint, so even if two transactions pass the FREE status check concurrently, only one will succeed at commit time and the other will get a constraint violation. A further improvement would be to add a pessimistic lock (`SELECT ... FOR UPDATE`) on the slot row at the start of the transaction to fail fast rather than at commit time.

## Prerequisites

- **Docker**
- **Java 25 JDK**

## How to Run

```bash
docker compose up --build
```
The application will be available at http://localhost:8080.
- Health Check: http://localhost:8080/actuator/health
- **Swagger UI: http://localhost:8080/swagger-ui.html** (recommended for exploring the API interactively)
- OpenAPI JSON: http://localhost:8080/v3/api-docs

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

### Delete a slot:
```bash
curl -X DELETE http://localhost:8080/api/users/1/slots/1
```

### Create a meeting
Converts a FREE slot into a meeting. The slot will be marked BUSY automatically.
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

## Future Improvements

### Domain Model
- Introduce a `CalendarService` as an explicit domain concept to encapsulate all slot and meeting management for a user.

### Scheduling Features
- Book next available slot for a user
- Find common availability across multiple participants for group meeting scheduling
- Slot overlap validation when creating or updating slots to prevent double-bookings
- Query slots by status and time range

### Performance
- N+1 query optimization with `JOIN FETCH` queries in `MeetingRepository`
- Pagination support on list endpoints
- Add pessimistic locking (`SELECT ... FOR UPDATE`) on the slot row at the start of `createMeeting` to fail fast on concurrent booking attempts rather than relying on the `UNIQUE` constraint at commit time

### Testing
- Improve test coverage, currently only the User classes include test in all they layers. Including integration test for MeetingService will help testing the transactional operations.

### Miscellaneous
- Add validation to data submitted through the endpoints 
- Add logger and metrics
