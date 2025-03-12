# PetroFlow Backend

Backend for the PetroFlow Application.

## Description

This project is the backend service for the PetroFlow application. It is built using Spring Boot and provides various functionalities such as user authentication, data management, and integration with external services.

## Features

- User authentication and authorization
- RESTful API endpoints
- Integration with PostgreSQL database
- JWT-based authentication
- Firebase Admin SDK for mobile integration
- Google OAuth2 client support
- Email service integration
- Hot reloading with Spring Boot DevTools

## Technologies Used

- Java 17
- Spring Boot 3.4.3
- PostgreSQL
- JSON Web Token (JWT)
- Firebase Admin SDK
- Google OAuth2 Client
- Lombok
- Spring Boot DevTools

## Why Spring Boot?

I chose Spring Boot because of its microservices architecture, which allows for the development of scalable and maintainable applications. Additionally, Spring Boot supports role-based access control, where each role has defined permissions, making it easier to manage user access and security.

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/Angera-Silas/petroflow-backend.git
    cd petroflow-backend
    ```

2. Start PostgreSQL and Adminer using Docker:
    ```bash
    docker-compose up -d
    ```

3. Configure the PostgreSQL database in `application.properties`.

4. Build the project:
    ```bash
    mvn clean install
    ```

5. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/Angera-Silas/petroflow-backend/blob/main/LICENSE.md) file for details.

## Contact

Angera Silas - [angerasilas@gmail.com](mailto:angerasilas@gmail.com)

Project Link: [https://github.com/Angera-Silas/petroflow-backend](https://github.com/Angera-Silas/petroflow-backend)