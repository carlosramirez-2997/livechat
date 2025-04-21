# Live Chat Backend Application

This is the backend of a real-time Live Chat application built with **Java 21** and **Spring Boot**. It supports multiple chat rooms with real-time messaging using **WebSocket (STOMP)**, secure authentication via **JWT**, and persistent message storage with **PostgreSQL**.

---

## 🚀 Features

- ✅ Real-time messaging with WebSocket (STOMP)
- ✅ Multiple chat rooms support (up to 10 users per room)
- ✅ Join/leave chat rooms without page reload
- ✅ JWT-based authentication (login/register)
- ✅ Role-based access (e.g. `ADMIN`/`USER`)
- ✅ Message persistence in PostgreSQL
- ✅ H2 database available for testing
- ✅ REST API to manage users and rooms
- ✅ CORS and WebSocket security configuration
- ✅ Configurable backend (port, allowed origins, etc.)

---

## 🛠️ Tech Stack

| Layer              | Technology                        |
|-------------------|-----------------------------------|
| Language           | Java 21                           |
| Framework          | Spring Boot 3.x                   |
| WebSocket          | Spring Messaging (STOMP over SockJS) |
| Auth               | JWT + Spring Security             |
| DB                 | PostgreSQL (H2 for tests)         |
| Testing            | JUnit 5, Mockito                  |
| Migration          | Flyway                            |
| Persistence        | Spring Data JPA                   |
| Build Tool         | Maven or Gradle                   |

---

## 🔐 Authentication
🔑 Login
POST /api/auth/login
Returns JWT token upon success.

🧾 Register
POST /api/auth/register
Creates a new user and issues a token.

🔒 Secure Endpoints
All /api/rooms/** endpoints require a valid JWT token in the header:

```
Authorization: Bearer <your-jwt-token>
```
