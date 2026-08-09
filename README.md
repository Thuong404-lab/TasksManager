# Task Manager

A simple web application for managing personal and assigned tasks, built with
Jakarta EE, Servlet, JSP/JSTL, and Microsoft SQL Server.

## Features

- Sign in, sign out, and remember account details.
- Role-based access for administrators and users.
- Manage tasks, users, and priority levels.
- Regular users can only manage tasks assigned to them.
- Track completed tasks and upcoming deadlines.
- BCrypt password hashing, CSRF protection, and server-side validation.

## Tech Stack

- Java 11
- Jakarta EE 10
- Servlet, JSP, and JSTL
- JDBC
- Microsoft SQL Server
- Bootstrap 5
- Maven
- JUnit 5

## Demo Accounts

```yaml
Admin: admin / admin123
User:  user  / 123123123
```

> Use these accounts only for local development and demonstration purposes.

## Getting Started

### 1. Create the database

Run the following SQL scripts in order:

```text
database/schema.sql
database/seed.sql
```

### 2. Configure the database connection

Set the following environment variables:

```text
TASK_DB_URL=jdbc:sqlserver://127.0.0.1:1433;databaseName=TaskManagerDB;encrypt=false
TASK_DB_USER=sa
TASK_DB_PASSWORD=your_password
```

If these variables are not set, the application uses the local defaults defined
in `DatabaseConnectionProvider`.

### 3. Build and test

```bash
mvn clean package
```

The generated WAR file is available at:

```text
target/task-manager-1.0.war
```

### 4. Run the application

Deploy the WAR file to Tomcat 10.1, then open:

```text
http://localhost:8080/ProjectTaskManager/login
```

## Project Structure

```text
src/main/java/com/taskmanager
├── config
├── controller
├── dao
├── filter
├── model
└── util

src/main/webapp/WEB-INF/views
├── auth
├── fragments
├── priorities
├── tasks
└── users
```

## Security Notes

- Never commit database credentials or `.env` files.
- Use HTTPS and a restricted database account in production.
- Change all demo passwords before deploying publicly.
