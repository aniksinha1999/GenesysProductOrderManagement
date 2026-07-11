================================================================================
Genesys Product Order Management System
Java Spring Boot Microservices
================================================================================

Author
------
Anik Sinha
Backend Engineer | Java | Spring Boot | Microservices | AWS | REST APIs

--------------------------------------------------------------------------------
Project Overview
--------------------------------------------------------------------------------

This project demonstrates a production-style Java Spring Boot Microservices
application implementing Product Management and Order Management.

The solution follows clean architecture principles with separated services,
inter-service communication using OpenFeign, resilience through Circuit Breaker,
layered architecture, validation, centralized exception handling, and REST APIs.

The primary objective is to simulate a simplified e-commerce backend while
showcasing modern backend engineering practices.

--------------------------------------------------------------------------------
Key Features
--------------------------------------------------------------------------------

✔ Spring Boot 3
✔ Java 21
✔ Microservices Architecture
✔ RESTful APIs
✔ Spring Data JPA
✔ H2 Database
✔ OpenFeign Communication
✔ Resilience4J Circuit Breaker
✔ Bean Validation
✔ Layered Architecture
✔ Exception Handling
✔ Maven Build
✔ Inventory Management
✔ Order Processing

--------------------------------------------------------------------------------
Architecture
--------------------------------------------------------------------------------

                         +------------------------+
                         |     Client/Postman     |
                         +-----------+------------+
                                     |
                 -------------------------------------------
                 |                                         |
                 |                                         |
        Product Management Service              Order Management Service
                 |                                         |
                 |<------------ OpenFeign ---------------->|
                 |      Product Validation & Stock Update  |
                 |                                         |
             Spring Data JPA                          Spring Data JPA
                 |                                         |
              H2 Database                              H2 Database

--------------------------------------------------------------------------------
Microservices
--------------------------------------------------------------------------------

Product Service
---------------
Runs on:
http://localhost:8080

Responsibilities

- Product CRUD
- Inventory Management
- Product Validation
- Product Lookup
- Quantity Update

Order Service
-------------
Runs on:
http://localhost:8082

Responsibilities

- Place Order
- Product Validation
- Stock Verification
- Inventory Update
- Total Price Calculation
- Order Persistence
- Circuit Breaker

--------------------------------------------------------------------------------
Project Structure
--------------------------------------------------------------------------------

GenesysProductOrderManagement

├── ProductManagement
│
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── service
│   ├── serviceimpl
│   ├── configuration
│   └── exception
│
└── OrderManagement

    ├── controller
    ├── dto
    ├── entity
    ├── repository
    ├── service
    ├── serviceimpl
    ├── feign
    ├── configuration
    ├── circuitbreaker
    └── exception

--------------------------------------------------------------------------------
Order Processing Flow
--------------------------------------------------------------------------------

Create Order

      │

      ▼

Call Product Service

      │

      ▼

Validate Product

      │

      ▼

Check Available Stock

      │

      ▼

Update Inventory

      │

      ▼

Calculate Amount

      │

      ▼

Save Order

      │

      ▼

Return Success Response

--------------------------------------------------------------------------------
Circuit Breaker Flow
--------------------------------------------------------------------------------

Order Service

      │

      ▼

Product Service Unavailable

      │

      ▼

Circuit Breaker Opens

      │

      ▼

Fallback Method

      │

      ▼

Graceful Error Response

Instead of propagating failures to the client, the application returns a
controlled response and prevents repeated failures from overwhelming the
Product Service.

--------------------------------------------------------------------------------
Technology Stack
--------------------------------------------------------------------------------

Java                      21

Spring Boot               3.x

Spring Data JPA

Spring Cloud OpenFeign

Resilience4J

Hibernate

H2 Database

Maven

Lombok

Bean Validation

--------------------------------------------------------------------------------
API Execution Order
--------------------------------------------------------------------------------

1. Start Product Service

Port : 8080

2. Start Order Service

Port : 8082

3. Insert Products

POST

/api/productService/product

4. Verify Products

GET

/api/productService/product

5. Create Order

POST

/api/orderService/order

6. Verify Orders

GET

/ api/orderService/order

--------------------------------------------------------------------------------
Swagger
--------------------------------------------------------------------------------

Product Service

http://localhost:8080/swagger-ui/index.html

--------------------------------------------------------------------------------
H2 Console
--------------------------------------------------------------------------------

Product Service

http://localhost:8080/h2-console

Order Service

http://localhost:8082/h2-console

Username

product

Password

product

--------------------------------------------------------------------------------
Design Decisions
--------------------------------------------------------------------------------

1. Separate Product and Order Services

Reason

Product and Order represent different business capabilities.

Benefits

• Independent deployment
• Better scalability
• Clear ownership
• Easier maintenance

Trade-off

• Requires inter-service communication
• Network latency
• Distributed system complexity

--------------------------------------------------------------------------------

2. OpenFeign

Reason

Provides declarative REST clients and reduces boilerplate HTTP code.

Benefits

• Clean code
• Easy integration
• Spring Cloud support
• Automatic serialization

Trade-off

• Tight runtime dependency between services
• Additional latency compared to local method calls

--------------------------------------------------------------------------------

3. Resilience4J Circuit Breaker

Reason

A failed Product Service should not continuously affect the Order Service.

Benefits

• Prevents cascading failures
• Improves system stability
• Graceful fallback responses

Trade-off

• Adds operational complexity
• Requires careful threshold tuning
• Temporary service failures may still reject valid requests until recovery

--------------------------------------------------------------------------------

4. Spring Data JPA

Reason

Simplifies persistence with repositories instead of handwritten SQL.

Benefits

• Less boilerplate
• Database abstraction
• Faster development

Trade-off

• Slight performance overhead
• Complex queries sometimes require native SQL

--------------------------------------------------------------------------------

5. Layered Architecture

Controller

↓

Service

↓

Repository

Reason

Separates business logic from API and persistence.

Benefits

• Better maintainability
• Easier testing
• Loose coupling

Trade-off

• More classes
• Slightly more code for small applications

--------------------------------------------------------------------------------

6. Bean Validation

Reason

Reject invalid requests before business processing.

Benefits

• Cleaner code
• Better API contracts
• Consistent validation

Trade-off

• Additional annotations
• Complex validation rules may require custom validators

--------------------------------------------------------------------------------

7. H2 Database

Reason

Chosen for demonstration and interview purposes.

Benefits

• Zero installation
• Fast startup
• Easy testing

Trade-off

• Not suitable for production
• Data is lost after restart unless configured for persistence

--------------------------------------------------------------------------------

8. Bulk Product Creation API

Reason

Allows loading sample inventory quickly.

Benefits

• Faster testing
• Reduced API calls

Trade-off

• Larger payloads consume more memory
• Partial failure handling becomes more complex

--------------------------------------------------------------------------------

9. Synchronous Communication

Reason

Immediate inventory validation is required before confirming an order.

Benefits

• Strong consistency
• Simple implementation

Trade-off

• Higher response time
• Order Service depends on Product Service availability

Alternative

For high-scale production systems, asynchronous messaging using Kafka or RabbitMQ
could improve availability and throughput.

--------------------------------------------------------------------------------
Current Limitations
--------------------------------------------------------------------------------

• Uses H2 instead of PostgreSQL/MySQL
• No API Gateway
• No Service Discovery (Eureka)
• No Distributed Tracing
• No Authentication or Authorization
• No Docker/Kubernetes deployment
• No Messaging Queue
• No Distributed Transactions

--------------------------------------------------------------------------------
Future Enhancements
--------------------------------------------------------------------------------

• Docker Support
• Kubernetes Deployment
• Eureka Discovery Server
• Spring Cloud Gateway
• PostgreSQL
• Redis Caching
• Kafka Event Streaming
• JWT Authentication
• Prometheus Metrics
• Grafana Dashboard
• Zipkin Distributed Tracing
• Centralized Logging using ELK
• CI/CD Pipeline
• Unit Tests and Integration Tests

--------------------------------------------------------------------------------
Branches
--------------------------------------------------------------------------------

dev
Product Management

devOrder
Order Management

--------------------------------------------------------------------------------
Conclusion
--------------------------------------------------------------------------------

This project demonstrates the implementation of a clean and modular
microservices architecture using Spring Boot. It focuses on separation of
concerns, maintainability, resiliency, and modern backend development
practices.

The chosen technologies prioritize readability, rapid development, and
interview-friendly design while highlighting the trade-offs involved in
building distributed systems. The architecture can be extended toward a
production-grade solution by integrating service discovery, centralized
configuration, messaging, security, observability, containerization, and
cloud-native deployment practices.

================================================================================
