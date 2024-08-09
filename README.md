# Library Management System

## Project Overview

The Library Management System (LMS) is a Spring Boot application designed to manage books, patrons, and borrowing records. The application supports two types of users:
- **Patrons**: Can borrow books.
- **Librarians**: Can manage books and patrons (add, delete, update books only).

Both librarians and patrons can log in and register to access their respective functionalities. The application uses JWT (JSON Web Token) for authentication and role-based authorization to control access to different features.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure Java 17 SDK is installed on your machine.
- **MySQL Database**: Make sure MySQL is installed and running.
- **MySQL Workbench or Command-Line Interface**: For database management.

## Setup Instructions

### 1. Create the MySQL Database

1. Open MySQL Workbench or Command-Line Interface.
2. Create a new database named `librarydb`:

   ```sql
   1.CREATE DATABASE librarydb;
   ```   
### 2. Configure the Spring Boot Application
Set Up Environment Variables

You need to configure the environment variables for your MySQL database credentials. Create or modify the environment variables as follows:

MYSQL_USERNAME: Your MySQL username.
MYSQL_PASSWORD: Your MySQL password.
Update Application Properties

Ensure your application.properties file in the src/main/resources directory is configured as follows:

```
spring.application.name=Library Management System
spring.datasource.url=jdbc:mysql://localhost:3306/librarydb
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}
```
### 3. Build and Run the Application
Import Using IntelliJ Idea ide or

Navigate to Your Project Directory

Open a terminal or command prompt and navigate to the root directory of your Spring Boot project.

Build the Project

Use Maven to build the project:
```
mvn clean install
```
Run the Application

After building the project, run the application using the following command:
```
mvn spring-boot:run
```
### 4. Access the Application
Once the application is running, you can access it through a web browser or API client. Typically, the application will be available at `http://localhost:8080`.


