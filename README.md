# 📧 Genesys Notification Service

A lightweight **Spring Boot Microservice** responsible for sending email notifications to customers after successful order placement.

The Notification Service is designed to work independently and is consumed by the **Order Management Service** through **Spring Cloud OpenFeign**, demonstrating asynchronous-ready microservice communication patterns.

---

# Features

- Send Order Confirmation Email
- REST API for Notification Requests
- Email Delivery using Spring Mail
- Integration with Order Service using OpenFeign
- HTML/Text Email Support
- Exception Handling
- Layered Architecture
- Maven Build

---

# Architecture

```
                     +----------------------+
                     |  Order Management    |
                     +----------+-----------+
                                |
                         OpenFeign Client
                                |
                                ▼
                    +-------------------------+
                    | Notification Service    |
                    +------------+------------+
                                 |
                          JavaMail Sender
                                 |
                                 ▼
                         SMTP Mail Server
                                 |
                                 ▼
                              Customer
```

---

# Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.x |
| Spring Web | Latest |
| Spring Mail | Latest |
| Lombok | Latest |
| Maven | Latest |

---

# Running the Application

Runs on

```
http://localhost:8083
```

---

# Project Structure

```
NotificationService
│
├── controller
├── dto
├── service
├── serviceimpl
├── configuration
├── exception
└── resources
```

---

# API

## Send Notification

### Endpoint

```
POST /notification
```

### Sample Request

```json
{
  "customerName": "Anik Sinha",
  "customerEmail": "anik.sinha@example.com",
  "productName": "Apple Watch Series 10",
  "quantity": 2,
  "totalAmount": 99998.00,
  "orderNumber": "ORD-100001"
}
```

### Sample Response

```
Notification sent successfully.
```

---

# API Testing

### Send Notification

```bash
curl --location 'http://localhost:8083/notification' \
--header 'Content-Type: application/json' \
--data '{
  "customerName": "Anik Sinha",
  "customerEmail": "anik.sinha@example.com",
  "productName": "Apple Watch Series 10",
  "quantity": 2,
  "totalAmount": 99998.00,
  "orderNumber": "ORD-100001"
}'
```

---

# Integration with Order Service

Order Service communicates with Notification Service using **Spring Cloud OpenFeign**.

Example Feign Client

```java
@FeignClient(name = "notification-service",
        url = "${notification.service.url}")
public interface EmailFeignClient {

    @PostMapping("/notification")
    void sendNotification(
            @RequestBody NotificationRequestDto request);
}
```

---

# Workflow

```
Customer Places Order

        │

        ▼

Order Service

        │

        ▼

Validate Product

        │

        ▼

Update Inventory

        │

        ▼

Save Order

        │

        ▼

Invoke Notification Service (Feign)

        │

        ▼

Notification Service

        │

        ▼

Send Email

        │

        ▼

Customer Receives Order Confirmation
```

---

# Configuration

Example `application.properties`

```properties
server.port=8083

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

# Error Handling

| Status | Description |
|---------|-------------|
| 200 | Notification Sent Successfully |
| 400 | Invalid Request |
| 404 | Endpoint Not Found |
| 500 | Email Sending Failed |

---

# Future Enhancements

- HTML Email Templates (Thymeleaf)
- Kafka/RabbitMQ Integration
- SMS Notifications
- Push Notifications
- Retry Mechanism
- Resilience4J Retry
- Dead Letter Queue (DLQ)
- Email Scheduling
- Email Tracking
- Audit Logs
- Notification History

---

# Interview Highlights

This service demonstrates:

- Microservice Design
- REST API Development
- OpenFeign Integration
- Email Notification Workflow
- Spring Mail
- Clean Layered Architecture
- Exception Handling
- Externalized Configuration
- Service-to-Service Communication
- Production-Ready Notification Module

---

# Complete Microservice Flow

```
                Product Service (8080)
                        │
                        │
                        ▼
              Order Service (8082)
                        │
         ┌──────────────┴──────────────┐
         │                             │
         ▼                             ▼
 Inventory Update          Notification Service (8083)
                                         │
                                         ▼
                                 Email to Customer
```

---

# Author

**Anik Sinha**

Backend Engineer | Java | Spring Boot | Microservices | OpenFeign | REST APIs | AWS
