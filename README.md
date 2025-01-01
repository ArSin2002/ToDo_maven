# ToDo App README

This project is a ToDo application built using Dropwizard and MySQL. The following instructions guide you through creating the database inside the MySQL container and interacting with the ToDo app via POST and GET requests.

## Prerequisites

1. Docker and Docker Compose installed on your machine.
2. Java and Maven set up to build and run the ToDo application.

## Step 1: Start Containers with Docker Compose

 `docker-compose.yml` file is already created, simply run the following command to start the containers:

```bash
docker-compose up
```

## Step 2: Create the `tasks` Table in MySQL

Once the containers are running, follow these steps to create the `tasks` table inside the `todo_db` database in MySQL.

### 2.1 Access the MySQL Container

To access the MySQL container, run the following command:

```bash
docker exec -it mysql-todo mysql -u root -p
```
```sql
USE todo_db;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    target_date DATE NOT NULL
);
```

## Step 3: Interact with the ToDo App via POST and GET Requests

Now that your containers are up and the database is ready, you can start interacting with the ToDo app using Postman.

### 3.1 Post a New Task using Postman

You can create a new task by sending a POST request to the ToDo app. Follow these steps in Postman:

1. Open Postman.
2. Set the request type to `POST`.
3. Enter the URL: `http://localhost:8080/tasks`.
4. In the **Body** tab, select `raw` and choose `JSON` from the dropdown.
5. Enter the following JSON body:

```json
{
  "description": "Complete Dropwizard ToDo App",
  "status": "pending",
  "start_date": "2025-01-01",
  "target_date": "2025-02-01"
}

