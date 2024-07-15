# Conference Room Booking

The set of APIs for managing conference room reservations within an organization. 
This system enables users to check room availability, book rooms for specific time slots, and manage existing reservations.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Build and Run](#build-and-run)
- [Liquibase Integration](#liquibase-integration)
- [Swagger](#swagger)
- [Scheduled Task](#scheduled-task)
- [Postman Collection](#postman-collection)

## Features
- Check room availability
- Book rooms for specific time slots
- Manage existing reservations

## Prerequisites
- Java 17 or later
- Maven 3.6.3 or later
- H2 Database (in-memory for development)
- SpringBoot 3.3.1

## Build and Run

### How to Build
1. Clone the repository:
    ```sh
    git clone <repo-url>
    cd crb-service
    ```
2. Build the project using Maven:
    ```sh
    ./mvnw clean install
    ```

### How to Run
1. Run the application:
    ```sh
    ./mvnw spring-boot:run
    ```

## Test Data

Test data is defined in **data-setup.sql** file.

H2 DB Console: [http://localhost:8080/h2-ui/] ; to test connection, put jdbc url to [jdbc:h2:mem:crb] in case doesn't work. username/password is [sa].

## Swagger
Swagger is available on http://localhost:8080/swagger-ui/index.html

## Scheduled Task
The application includes a scheduled task **ConferenceRoomReleaseScheduler** to automatically release conference rooms 
when their booking time has ended.

The scheduler finds out all the expired booking from the beginning of the day, this is configurable by the property

this scheduler runs every minute, this is also configurable by the property

`app.scheduler.roomRelease.fixedRate=60000`

## Postman Collection

Please find the Postman collection in the repository `/resources/postmanCollection`.

# Curl command to test APIs

1. To register a conference room:
   ```sh 
   curl -X POST 'http://localhost:8080/api/v1/conference-room/register' \
   -H 'Content-Type: application/json' \
   -d '{
   "name": "Alpha",
   "capacity": 50,
   "maintenanceWindowIds": [1,2,3]
   }'


2. To find a conference room by ID:
   ```sh
    curl -X GET 'http://localhost:8080/api/v1/conference-room/4'

3. To find all available conference rooms within a time range:x
   ```sh 
   curl -X GET 'http://localhost:8080/api/v1/conference-room?startTime=17:15:00&endTime=17:30:00'

4. To book a conference room:
   ```sh 
   curl -X POST 'http://localhost:8080/api/v1/book' \
   -H 'Content-Type: application/json' \
   -H 'X-Request-ID: 123456' \
   -d '{
   "startTime": "15-07-2024 12:00",
   "endTime": "15-07-2024 12:30",
   "attendees": 2
   }'

5. To get a booking by ID:
   ```sh 
   curl -X GET 'http://localhost:8080/api/v1/book/1'

6. To delete a booking by ID:
    ```sh 
   curl -X DELETE 'http://localhost:8080/api/v1/book/1'

7. To fetch all conference room bookings
   ```sh 
   curl -X GET 'http://localhost:8080/api/v1/book'

