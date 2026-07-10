

A **Java Spring Boot Microservices** application demonstrating **Product Management** and **Order Management** using modern backend engineering practices.

This project showcases:

- ✅ Spring Boot 3
- ✅ Microservices Architecture
- ✅ RESTful APIs
- ✅ Spring Data JPA
- ✅ H2 Database
- ✅ OpenFeign Inter-Service Communication
- ✅ Resilience4J Circuit Breaker
- ✅ Bean Validation
- ✅ Layered Architecture
- ✅ Exception Handling
- ✅ Maven

---

# Architecture

```
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
```

---

# Features

## Product Management Service

- Create Multiple Products
- Get All Products
- Get Product By ID
- Get Product By Product Code
- Update Product Quantity
- Product Validation
- Inventory Management

---

## Order Management Service

- Place Order
- Validate Product using Product Service
- Verify Available Stock
- Update Inventory after Successful Order
- Calculate Total Order Amount
- Generate Unique Order Number
- Store Order Details
- Circuit Breaker using Resilience4J

---

# Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.x |
| Spring Data JPA | Latest |
| Spring Cloud OpenFeign | Latest |
| Resilience4J | Latest |
| H2 Database | Latest |
| Maven | Latest |
| Lombok | Latest |

---

# Microservices

## Product Service

Runs on

```
http://localhost:8080
```

---

## Order Service

Runs on

```
http://localhost:8082
```

---

# Project Structure

```
GenesysProductOrderManagement
│
├── ProductManagement
│
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── service
│   ├── serviceimpl
│   ├── exception
│   └── configuration
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
    └── circuitbreaker
```

---

# Inter Service Communication

The **Order Service** communicates with the **Product Service** using **Spring Cloud OpenFeign**.

Flow

```
Create Order

        │

        ▼

Fetch Product Details

        │

        ▼

Validate Product Exists

        │

        ▼

Validate Product Active

        │

        ▼

Validate Quantity Available

        │

        ▼

Update Product Inventory

        │

        ▼

Save Order

        │

        ▼

Return Success Response
```

---

# Circuit Breaker

Implemented using **Resilience4J**

If Product Service is unavailable,

```
Order Service
      │
      ▼
Circuit Breaker Opens
      │
      ▼
Fallback Method Executes
      │
      ▼
Graceful Error Response
```

instead of crashing the application.

---

# H2 Console

Product Service

```
http://localhost:8080/h2-console
```

Order Service

```
http://localhost:8082/h2-console
```

Credentials

```
Username : product

Password : product
```

---

# API Execution Order

## Step 1

Start Product Service

```
Port : 8080
```

---

## Step 2

Start Order Service

```
Port : 8082
```

---

## Step 3

Insert Products

```
POST /api/productService/product
```

---

## Step 4

Verify Products

```
GET /api/productService/product
```

---

## Step 5

Create Order

```
POST /api/orderService/order
```

---

## Step 6

Verify Order

```
GET /api/orderService/order
```

---

# Product Service APIs

---

## Create Products (Bulk)

```bash
curl --location 'http://localhost:8080/api/productService/product' \
--header 'Content-Type: application/json' \
--data '[
  {
    "productCode": "PRD1001",
    "productName": "Apple iPhone 16",
    "description": "128GB Black",
    "price": 79999.99,
    "quantity": 50,
    "category": "Mobile",
    "brand": "Apple",
    "supplier": "Apple India",
    "active": true
  },
  {
    "productCode": "PRD1002",
    "productName": "Samsung Galaxy S25",
    "description": "256GB Silver",
    "price": 74999.00,
    "quantity": 40,
    "category": "Mobile",
    "brand": "Samsung",
    "supplier": "Samsung India",
    "active": true
  },
  {
    "productCode": "PRD1003",
    "productName": "Sony WH-1000XM5",
    "description": "Wireless Noise Cancelling Headphones",
    "price": 29999.00,
    "quantity": 60,
    "category": "Accessories",
    "brand": "Sony",
    "supplier": "Sony India",
    "active": true
  },
  {
    "productCode": "PRD1004",
    "productName": "Dell XPS 15",
    "description": "Intel i7, 16GB RAM, 512GB SSD",
    "price": 154999.00,
    "quantity": 15,
    "category": "Laptop",
    "brand": "Dell",
    "supplier": "Dell Technologies",
    "active": true
  },
  {
    "productCode": "PRD1005",
    "productName": "Apple Watch Series 10",
    "description": "GPS 45mm Midnight",
    "price": 49999.00,
    "quantity": 35,
    "category": "Wearables",
    "brand": "Apple",
    "supplier": "Apple India",
    "active": true
  },
  {
    "productCode": "PRD1006",
    "productName": "Logitech MX Master 3S",
    "description": "Wireless Ergonomic Mouse",
    "price": 9999.00,
    "quantity": 100,
    "category": "Accessories",
    "brand": "Logitech",
    "supplier": "Logitech India",
    "active": true
  },
  {
    "productCode": "PRD1007",
    "productName": "HP LaserJet Pro MFP",
    "description": "All-in-One Wireless Printer",
    "price": 18999.00,
    "quantity": 20,
    "category": "Printer",
    "brand": "HP",
    "supplier": "HP India",
    "active": true
  },
  {
    "productCode": "PRD1008",
    "productName": "Apple iPad Air",
    "description": "11-inch Wi-Fi 256GB",
    "price": 69999.00,
    "quantity": 25,
    "category": "Tablet",
    "brand": "Apple",
    "supplier": "Apple India",
    "active": true
  },
  {
    "productCode": "PRD1009",
    "productName": "OnePlus 13",
    "description": "512GB Emerald Green",
    "price": 64999.00,
    "quantity": 45,
    "category": "Mobile",
    "brand": "OnePlus",
    "supplier": "OnePlus India",
    "active": true
  },
  {
    "productCode": "PRD1010",
    "productName": "Lenovo ThinkPad X1 Carbon",
    "description": "Intel Ultra 7, 32GB RAM, 1TB SSD",
    "price": 179999.00,
    "quantity": 10,
    "category": "Laptop",
    "brand": "Lenovo",
    "supplier": "Lenovo India",
    "active": true
  }
]'
```

---

## Get All Products

```bash
curl --location 'http://localhost:8080/api/productService/product'
```

---

## Get Product By ID

```bash
curl --location 'http://localhost:8080/api/productService/product/4'
```

---

## Get Product By Product Code

```bash
curl --location 'http://localhost:8080/api/productService/getProductByProductCode?productCode=PRD1001'
```

---

## Update Product Quantity

```bash
curl --location --request PUT \
'http://localhost:8080/api/productService/updateProduct?productCode=PRD1001&quantity=2'
```

---

# Order Service APIs

---

## Create Order

```bash
curl --location 'http://localhost:8082/api/orderService/order' \
--header 'Content-Type: application/json' \
--data-raw '{
  "productCode": "PRD1005",
  "productName": "Apple Watch Series 10",
  "quantity": 6,
  "customerName": "Anik Sinha",
  "customerEmail": "anik.sinha@example.com",
  "shippingAddress": "Flat 302, Prestige Tech Park, Marathahalli, Bengaluru, Karnataka 560037"
}'
```

---

## Get All Orders

```bash
curl --location 'http://localhost:8082/api/orderService/order'
```

---

## Interview Highlights

This project demonstrates:

- Microservices Architecture
- RESTful API Design
- Spring Boot Best Practices
- OpenFeign Client
- Circuit Breaker Pattern
- Inventory Management
- Transactional Business Flow
- H2 Database Integration
- Exception Handling
- Bean Validation
- Layered Architecture
- UUID-based Order Number Generation
- Clean Code Principles
- Maven Build Management

---

# Future Enhancements

- Docker Support
- Docker Compose
- API Gateway
- Eureka Service Discovery
- Config Server
- JWT Authentication
- Role-Based Authorization
- Kafka Event Driven Communication
- MySQL / PostgreSQL
- Redis Caching
- Swagger / OpenAPI
- Kubernetes Deployment
- CI/CD using GitHub Actions
- Distributed Tracing
- Centralized Logging (ELK)

---


Branches
dev - productManagement
devOrder- orderManagement
# Author

**Anik Sinha**

Backend Engineer | Java | Spring Boot | Microservices | AWS | REST APIs
