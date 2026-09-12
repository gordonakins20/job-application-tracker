# Job Application Tracker

A beginner-friendly full-stack application for tracking job opportunities from a saved role through an offer. It demonstrates Java, Spring Boot, REST APIs, SQL persistence, validation, automated testing, and responsive front-end development.

> **Starter note:** Study and customize this repository before featuring it in a portfolio. Change the sample data, add at least one roadmap feature, and be prepared to explain every part of the application.

## Features

- Create, read, update, and delete job applications
- Filter applications by status
- View totals for applications, interviews, and offers
- Validate required fields in the browser and API
- Persist records in a file-based H2 SQL database
- Display clear API errors for invalid or missing records
- Responsive desktop and mobile interface

## Technology

- Java 17 and Spring Boot 4.1.1
- Spring Web, Spring Data JPA, and Bean Validation
- H2 SQL database
- HTML, CSS, and vanilla JavaScript
- Maven, JUnit 5, and Mockito

## Run locally

Requirements: Java 17+ and Maven 3.6.3+.

```bash
mvn spring-boot:run
```

Open <http://localhost:8080>. Run tests with `mvn test`.

The H2 console is at <http://localhost:8080/h2-console>. Use JDBC URL `jdbc:h2:file:./data/jobtracker`, user `sa`, and a blank password.

## REST API

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/applications` | List all applications |
| GET | `/api/applications?status=INTERVIEW` | Filter by status |
| GET | `/api/applications/{id}` | Get one application |
| POST | `/api/applications` | Create an application |
| PUT | `/api/applications/{id}` | Update an application |
| DELETE | `/api/applications/{id}` | Delete an application |

Example request:

```json
{"company":"Example Software","role":"Junior Java Developer","status":"APPLIED","appliedDate":"2026-08-28","notes":"Submitted through the company website"}
```

## Architecture

The browser calls a REST controller. The controller validates input and delegates business operations to a service. The service uses a Spring Data repository, which maps Java entities to the SQL database.

## Suggested learning roadmap

Complete at least one before placing this project on a resume:

1. Add a recruiter contact and follow-up date.
2. Add text search by company or role.
3. Replace H2 with PostgreSQL using environment variables.
4. Add a dashboard chart showing applications by status.
5. Add authentication so users see only their own data.
6. Deploy the application and add screenshots and a live URL.

## Resume bullet examples

Use these only after understanding and customizing the project:

- Built a full-stack job application tracker using Java, Spring Boot, JavaScript, and SQL with RESTful CRUD operations.
- Implemented server-side validation, centralized error handling, status filtering, and persistent relational storage.
- Created a responsive browser interface and automated tests for core service behavior.

## License

MIT
