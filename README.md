# Expanse Tracker 📊

ExpanseTracker is a comprehensive Expense Tracking REST API built with Spring Boot. It provides a robust backend for managing personal finances, including tracking incomes, expenses, categorized transactions, and recurring expenses.

## 🚀 Features

*   **User Authentication & Security**: Secured API endpoints using Spring Security.
*   **Income Management**: Add, update, delete, and retrieve income records.
*   **Expense Management**: Track expenses with detailed information.
*   **Categories**: Manage transaction categories (e.g., Food, Transport, Salary) to easily organize finances.
*   **Recurring Transactions**: Automatically manage recurring incomes or expenses based on frequency.
*   **Financial Statistics & Graphs**: APIs to fetch statistical data and graph-ready datasets for easy frontend integration.

## 🛠️ Technology Stack

*   **Java 17**: Core programming language.
*   **Spring Boot 3.4.4**: Application framework.
    *   Spring Web (REST APIs)
    *   Spring Data JPA (ORM)
    *   Spring Security (Authentication & Authorization)
*   **MySQL**: Relational database for persistent storage.
*   **Lombok**: Reduces boilerplate code (getters, setters, constructors).
*   **Maven**: Dependency management and build tool.

## 📂 Project Architecture

The project follows a standard Spring Boot layered architecture:
*   `controller`: Exposes REST endpoints to interact with the application.
*   `dto`: Data Transfer Objects for client-server communication.
*   `entity`: JPA entities representing database tables.
*   `repository`: Interfaces extending `JpaRepository` for database operations.
*   `services`: Contains the core business logic.
*   `security`: Configuration classes for Spring Security.

## ⚙️ Setup & Installation

### Prerequisites

*   JDK 17 or higher
*   MySQL Server
*   Maven

### Configuration

1.  Clone the repository and open the project in your favorite IDE (IntelliJ IDEA, Eclipse, VS Code).
2.  Open `src/main/resources/application.properties`.
3.  Configure your MySQL database connection:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/expansetracker
    spring.datasource.username=root
    spring.datasource.password=your_database_password
    ```
4.  *(Optional)* Update the default security user if necessary:
    ```properties
    spring.security.user.name=ad
    spring.security.user.password=ad
    ```

### Running the Application

Navigate to the project root directory (where `pom.xml` is located) and run the following Maven command:

```bash
mvn spring-boot:run
```

Alternatively, you can run `ExpanseTrackerApplication.java` directly from your IDE.

## 🛡️ Endpoints (Overview)

The application provides endpoints under the `/api` prefix:
*   **Auth**: `/api/auth/*`
*   **Expenses**: `/api/expense/*`
*   **Incomes**: `/api/income/*`
*   **Categories**: `/api/category/*`
*   **Recurring Transactions**: `/api/recurring/*`
*   **Statistics**: `/api/stats/*`

*Note: Access to endpoints may require proper authentication headers depending on the Spring Security configuration.*
