# **📚 LibriBookshelf-MS**
## **🔗 Microservice for Book Reviews**

This repository contains the Review Microservice of the LibriBookshelf system — a distributed microservices architecture for managing reviews of books, built with Spring Boot, Spring Cloud, JWT Security, RabbitMQ messaging, and communication with other services (User and Catalog).

## **🧠 Overview**

LibriBookshelf-MS is responsible for:

- Registering and storing book reviews

- Updating and deleting reviews

- Listing reviews with enriched data (book titles & user names)

- Communicating with external services via:

    - Feign Client for User Service

    - RabbitMQ messaging for Catalog Service

- Enforcing authentication using JWT tokens issued by the Authentication Service

This microservice fits into a larger ecosystem that includes:

- API Gateway

- User Service

- Catalog Service

- Eureka (Service Discovery)

- Message Broker (RabbitMQ)

## **🚀 Features**
### **📍 Review Management**

- Create a review with title, content, score, and linked book/user IDs

- Update reviews partially

- Delete reviews by ID

- Handle validation with meaningful responses

### **🔗 Inter-Service Integration**

- User Service (via Feign): fetch user name by user ID

- Catalog Service (via RabbitMQ): fetch book title by book ID

### **🔐 Security**

- JWT token validation

- Secure endpoints accessible only with valid JWT tokens

- Token propagation to dependent services for authorization contexts

### **🐇 Resilience & Fault Tolerance**

- RabbitMQ request–response pattern with timeouts

- Graceful degradation on external service failures

- Distributed tracing-ready with logs

## **🏛️ Architecture**

````                        
                        +------------------+
                        |     API Gateway  |
                        +------------------+
                                  |
                                  |
               +------------------------------------------+
               |                  Review MS               |
               +------------------------------------------+
               | - Spring Boot                            |
               | - JWT validation                         |
               | - RabbitMQ client (Catalog)              |
               | - Feign Client (User)                    |
               | - PostgreSQL repository                  |
               +------------------------------------------+
                      |                         |
                      v                         v
            +----------------+        +-----------------------+
            |  User Service  |        |   Catalog Service     |
            +----------------+        +-----------------------+
                Feign                      RabbitMQ Messaging
````

## **🛠️ Technologies**
| **Layer**                      |    **Tech / Tool**     |
|:-------------------------------|:----------------------:|
| Framework                      |      Spring Boot       |
| API Clients                    | Spring Cloud OpenFeign |
| Messaging         |            RabbitMQ            |
| Security                       | JWT |
| Persistence	JPA                |      Spring Data       |
|  Mapping	MapStruct             | Custom Mapper |
|  Service Registry     |      Eureka       |
|         Load Balancing | Spring Cloud LoadBalancer |
|     Build  |     Maven       |

## **📦 Getting Started**
### **Prerequisites**

Make sure you have the following installed:

- Java 17+

- Maven 3+

- Docker & Docker Compose (for RabbitMQ & services)

- Running instances of:

- User Service

- Catalog Service

- Eureka Server

- API Gateway

## **📥 Environment Configuration**
Create an ``.env`` or set environment variables for:

````
# User Service
SERVICES_USER_SERVICE_URL=http://localhost:8082

# RabbitMQ
SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672

# JWT
JWT_SECRET=my-very-strong-secret-key-of-32-characters
````

## **🚀 Run with Docker Compose**

If you have a compose.yaml file included: ``docker compose up``

That command will start:

- RabbitMQ broker

- Dependencies according to your local setup

- The Review microservice container (if Docker support is configured)

## **🧩 API Endpoints**
| **HTTP**           |                   **Path**                    |    **Description**     |
|:-------------------|:---------------------------------------------:| :---------------:|
| POST               |          ``/review/register-review``          |Create a review|
| GET                |       ``/review/list-all-reviews    ``        |List all reviews with enriched info|
| GET                |       ``/reviewlist-only-review/{id}``        |Get review details|
| PATCH              |   ``/review/edit-review/{id}``          |Partial update|
| DELETE             |        ``/review/delete-review/{id}``         |Delete review|

All protected routes require Authorization with a valid JWT token.

## **⛓️ Integration Patterns**
### **📌 Feign Client**

Used to call User Service to retrieve the user’s name by ID.

### **📌 RabbitMQ Request-Reply**

Used to call Catalog Service for book title lookup.

## **📄 License**

This project is licensed under Apache-2.0 License.