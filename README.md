# E-Commerce REST API

## Description

This project is a backend RESTful API for a simple e-commerce application, built using Java and the Spring Boot framework. It provides fundamental e-commerce functionalities, including user management (registration and authentication), browsing items, and managing a shopping cart.

The application uses JWT for securing endpoints and an in-memory H2 database for data persistence, making it easy to run and test without external dependencies.

---

## Technical Requirements

To build and run this project, you will need the following installed on your machine:

- **Java**: Version 17
- **Maven**: Version 3.x

The project is built upon the following core technologies:

- **Spring Boot**: 3.1.0
- **Spring Web**: For building RESTful APIs.
- **Spring Data JPA**: For database interaction and persistence.
- **Spring Security**: For authentication and authorization.
- **Hibernate**: As the JPA provider.
- **H2 Database**: An in-memory database for development and testing.
- **Auth0 Java JWT**: For creating and verifying JSON Web Tokens.
- **Lombok**: To reduce boilerplate code.

---

## How to Run

1.  **Clone the repository:**
    ```bash
    git clone <your-repository-url>
    cd e-commerce
    ```

2.  **Build and run the application using Maven:**

    You can run the application directly using the Spring Boot Maven plugin:
    ```bash
    mvn spring-boot:run
    ```

    Alternatively, you can build the project into a JAR file and then run it:
    ```bash
    # Build the project
    mvn clean install

    # Run the application
    java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
    ```

3.  **Access the application:**

    Once running, the API will be available at `http://localhost:8081`.

---

## Project Structure

The project follows a standard Maven project structure. Here is an overview of the key packages and their purpose:

```
src
├── main
│   ├── java/com/udacity/ecommerce
│   │   ├── bootstrap/      # Contains data initialization logic (e.g., DataInitializer)
│   │   ├── controllers/    # REST API controllers for handling HTTP requests
│   │   ├── exception/      # Custom exception classes for error handling
│   │   ├── model
│   │   │   ├── persistence/  # JPA entities and Spring Data repositories
│   │   │   └── requests/     # Data Transfer Objects (DTOs) for API requests
│   │   ├── security/       # Spring Security configuration, JWT filters, and constants
│   │   └── service/        # Business logic layer
│   │
│   └── resources/
│       └── application.properties  # Application configuration
│
└── test/                   # Unit and integration tests
```