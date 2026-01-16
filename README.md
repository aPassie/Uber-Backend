# Uber-Clone Backend (Spring Boot + MongoDB + JWT)

A mini ride-sharing backend application built using **Spring Boot**, **MongoDB**, **JWT Authentication**, **DTO Validation**, and **Global Exception Handling**. This project demonstrates a clean architecture using **Controller → Service → Repository** with role-based access (`ROLE_USER`, `ROLE_DRIVER`).

---

## Tech Stack

- **Java 17+**
- **Spring Boot 4.0.0**
- **Spring Data MongoDB**
- **Spring Security (JWT)**
- **JJWT 0.11.5** for token handling
- **Jakarta Validation**
- **Lombok**
- **BCrypt** for password encoding

---

## Project Structure

```
src/main/java/com/example/Uber_Clone/
├── controller/
│   ├── AuthController.java
│   ├── RideController.java
│   └── DriverRideController.java
├── service/
│   ├── AuthService.java
│   ├── RideService.java
│   └── CustomUserDetailsService.java
├── repository/
│   ├── UserRepository.java
│   └── RideShareRepository.java
├── model/
│   ├── User.java
│   └── Ride.java
├── dto/
│   ├── AuthRegisterRequest.java
│   ├── AuthLoginRequest.java
│   ├── AuthResponse.java
│   ├── CreateRideRequest.java
│   └── RideResponse.java
├── config/
│   ├── SecurityConfig.java
│   └── JwtAuthenticationFilter.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── BadRequestException.java
│   └── NotFoundException.java
└── util/
    └── JwtUtil.java

src/main/resources/
├── application.properties
└── application.yaml
```

---

## Authentication Flow (JWT)

1. **Register** user → `POST /api/auth/register`
2. **Login** → `POST /api/auth/login`
3. **Receive JWT** token
4. **Send token** in header for all protected endpoints:
   ```
   Authorization: Bearer <your_jwt_token>
   ```

---

## API Endpoints

### Public Endpoints

#### Register User / Driver

**Endpoint:** `POST /api/auth/register`

**Request Body:**
```json
{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"
}
```

---

#### Login

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "username": "john",
  "password": "1234"
}
```

**Response:**
```json
{
  "token": "<jwt_token>",
  "username": "john",
  "role": "ROLE_USER"
}
```

---

### USER Endpoints

#### Request a Ride

**Endpoint:** `POST /api/v1/rides`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

**Request Body:**
```json
{
  "pickupLocation": "A",
  "dropLocation": "B"
}
```

---

#### View My Rides

**Endpoint:** `GET /api/v1/user/rides`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

---

#### Complete a Ride

**Endpoint:** `POST /api/v1/rides/{rideId}/complete`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

---

### DRIVER Endpoints

#### View Pending Ride Requests

**Endpoint:** `GET /api/v1/driver/rides/requests`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

---

#### Accept a Ride

**Endpoint:** `POST /api/v1/driver/rides/{rideId}/accept`

**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

---

## Testing with Postman / cURL

### Register a User

```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"1234","role":"ROLE_USER"}'
```

---

### Login

```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"1234"}'
```

**Save the JWT token from the response.**

---

### Create a Ride (User)

```bash
curl -X POST http://localhost:8081/api/v1/rides \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{"pickupLocation":"A","dropLocation":"B"}'
```

---

### View Ride Requests (Driver)

```bash
curl -X GET http://localhost:8081/api/v1/driver/rides/requests \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

### Accept a Ride (Driver)

```bash
curl -X POST http://localhost:8081/api/v1/driver/rides/{rideId}/accept \
  -H "Authorization: Bearer <your_jwt_token>"
```

---

## How to Run

### Prerequisites
- **Java 17+** installed
- **Maven** installed
- **MongoDB** running locally on default port `27017`

### Steps

1. **Clone the repository:**
   ```bash
   git clone <repository_url>
   cd Uber-Clone
   ```

2. **Configure MongoDB** in `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/rideshare_db
   ```

3. **Build the project:**
   ```bash
   mvn clean package
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API** at:
   ```
   http://localhost:8081
   ```

---

## Features

- JWT-based Authentication & Authorization
- Role-Based Access Control (`ROLE_USER`, `ROLE_DRIVER`)
- Secure Password Encoding with **BCrypt**
- MongoDB Repository Pattern
- DTO Validation with **Jakarta Validation**
- Global Exception Handler
- Passenger Ride Request System
- Driver Accept Ride
- User/Driver Complete Ride
- Clean Folder Structure

---

## Important Notes

- **POST requests** work fine; if browser GET shows error → this is expected (use Postman/cURL).
- Use **Postman** or **cURL** for testing request-based operations.
- Ensure **MongoDB** is running locally before starting the application.
- Replace `<your_jwt_token>` with the actual token received from the login endpoint.

---

## Configuration

### application.properties

```properties
# Application
spring.application.name=rideshare-backend

# Server Configuration
server.port=8081

# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/rideshare_db

# JWT Configuration
app.jwt.secret=your-secret-key-here
app.jwt.expiration-ms=3600000

# Logging
logging.level.org.springframework.security=INFO
```
