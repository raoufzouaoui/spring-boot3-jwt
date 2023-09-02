# Spring Boot 3 JWT Project

This is a Spring Boot project that demonstrates JWT (JSON Web Token) authentication and authorization. It includes user registration, authentication, and secure endpoints for managing products. Below is the README for this project:

## Table of Contents

##### Project Description
##### Prerequisites
##### Installation
##### Usage
##### Endpoints

## Project Description

This Spring Boot project provides a basic authentication and authorization system using JWT. It allows users to register, authenticate, and access secure endpoints for managing products. JWTs are used to secure and authenticate API endpoints.

## Prerequisites

Before you begin, ensure you have met the following requirements:
Java Development Kit (JDK) 8 or higher installed.
Maven installed.
Your preferred Integrated Development Environment (IDE).

## Installation

Clone the repository:
git clone https://github.com/yourusername/spring-boot-3-jwt.git
Open the project in your IDE.
Build the project using Maven:
mvn clean install
Usage
Run the Spring Boot application:
mvn spring-boot:run
The application will start and be accessible at http://localhost:8080.

## Endpoints

/products/welcome (GET): An unsecured endpoint for testing purposes.

/products/new (POST): Register a new user.

/products/authenticate (POST): Authenticate and obtain a JWT token.

/products/all (GET): Secure endpoint accessible to users with the ROLE_ADMIN authority. Retrieve all products.

/products/{id} (GET): Secure endpoint accessible to users with the ROLE_USER authority. Retrieve a specific product by its ID.

Please note that you will need to include a valid JWT token in the request headers for the secure endpoints. You can obtain a JWT token by authenticating using the /products/authenticate endpoint.

