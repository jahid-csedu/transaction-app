# Transaction App

## Overview

This Spring Boot application processes transaction data from a text file and stores it in a MySQL database. 
It provides RESTful APIs to search for transactions based on specific criteria (customer ID, account number, 
description) with pagination support. The application also allows updating the transaction description while 
handling concurrent updates. The entire API is secured using basic authentication.

## Run the Application
## Prerequisites

- Docker
- Java 17 or later

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/jahid-csedu/transaction-app.git
cd transaction-app
```

### 2. Run the Database
Run the Database docker container
```bash
docker-compose up
```

### 2. Run the Application
You can run the application using Maven:
```bash
./mvnw spring-boot:run
```
Or build the project and run the JAR file:
```bash
./mvnw clean package
java -jar target/transaction-app-0.0.1-SNAPSHOT.jar
```
### 4. Accessing the Application
The application runs on port 8080 by default. All API endpoints are secured with basic authentication.

Authentication Details:

- Username: admin
- Password: admin
### 5. API Endpoints
####  GET /api/transactions
Search for transactions with optional pagination and filtering by customer ID, account number, or description.
```bash
curl -u admin:admin "http://localhost:8080/api/transactions?customerId=222&page=0&size=10"
```

####  PUT /api/transactions/{transactionId}
Update the description of a transaction with support for concurrent updates.
##### Example Request:
```bash
curl -X PUT -u admin:admin -H "Content-Type: application/json" -d '{"description": "updated description"}' "http://localhost:8080/api/transactions/1"
```

## Details of the System
### Features
- Load the transaction records using batch when the system starts
- Search transactions
  - Using account number
  - Using customer ID
  - Using description (partial matching available)
  - Search Response is paginated (Default page size is 10)
- Update the description of a transaction
  - Concurrency update is handled using optimistic locking mechanism using version of a record. 
    We have used JPA provided `@Version` annotation to handle the locking
- Secured Endpoint
  - All the endpoints are secured. User must authenticate themselves for each API call. 
    We have used basic authentication mechanism for this application as this is a test application.
    But Basic authentication is not a good choice for a production grade application.

### Activity Diagram
Below is the activity diagram for the application:

![Activity Diagram](./activity_diagram.png)

### Class Diagram
Below is the class diagram for the application:

![Class Diagram](./class_diagram.png)

### Design Patterns used
As we are using Frameworks so most of the design patterns are handled by the framework itself. 
We have used Spring Boot framework for the whole system, Spring Batch for batch processing and 
Spring Security for user authentication. Apart from the framework's internal design patterns 
we have used some design pattern for our application side code. The details are given below:

#### 1. Builder Pattern
- **Usage**:
    - `FlatFileItemReaderBuilder` and `StepBuilder`.
- **Description**:
    - The Builder pattern is used to construct complex objects step by step. In this project, the `FlatFileItemReaderBuilder` is an example where various aspects of the reader are configured before it's built.

#### 2. Adapter Pattern
- **Usage**:
    - Converting data types, such as mapping a `FieldSet` to a `Transaction` entity in `TransactionFieldSetMapper`.
- **Description**:
    - The Adapter pattern allows objects with incompatible interfaces to work together. `FieldSetMapper` acts as an adapter to convert a flat file’s data into a domain object.

#### 3. Observer Pattern
- **Usage**:
    - `JobCompletionNotificationListener`.
- **Description**:
    - The Observer pattern defines a one-to-many dependency between objects, so when one object changes state, all its dependents are notified. The `JobCompletionNotificationListener` is notified when a job completes and acts accordingly.
