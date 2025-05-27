# Complaint REST API

A Spring Boot-based REST API that allows users to submit and manage complaints about products. It supports geo-location-based country resolution, resilient external API calls, and handles concurrent updates using optimistic locking.

---

## 🔧 Features

- **CRUD for Complaints**: Create, read, update, and delete complaint entries.
- **Geo-location Integration**: Determines country based on client IP using `ipapi.co`.
- **Resilience with Resilience4j**: Retries and fallback for external calls and race conditions.
- **Optimistic Locking**: Handles concurrent updates safely with JPA `@Version`.
- **Global Exception Handling**: Graceful error messages and appropriate HTTP status codes.

---

## 🧰 Tech Stack

- Java 17+
- Spring Boot 3
- Spring Data JPA
- MySQL
- Liquibase
- Resilience4j
- Lombok

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL running on `localhost:3306` with a database named `complaintrestapi`

### Configuration

`application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/complaintrestapi
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=none
