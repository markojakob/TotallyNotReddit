# TotallyNotReddit 

A Reddit clone app. Built with Spring Boot and Angular using postgresql database.

# 🛠️ Tech Stack

**Frontend**

* Angular (v16+)
* TypeScript

**Backend**

* Spring Boot (v4+)
* Java
* Spring Data JPA

**Database**

* PostgreSQL (running in Docker)
* You can view the full visual ER diagram on DBdiagram.io:

(https://dbdiagram.io/d/699b4006bd82f5fce26fd02b)


**Build Tools**

* Maven
* Angular CLI

---

# 🚀 Getting Started

## 1. Prerequisites

Ensure you have the following installed:

* Java JDK 17+
* Node.js (LTS version)
* Angular CLI
* Docker & Docker Desktop

---

## 2. Database Setup (Docker)

We use Docker to spin up the PostgreSQL instance instantly.

1. Locate the `docker-compose.yml` file in the root directory.

2. Run the following command:

```bash
docker-compose up -d
```

This will start Postgres on `localhost:5432` with the credentials defined in your compose file.

---

## 3. Backend Setup (Spring Boot)

1. Navigate to the `/backend` folder.

2. Update `src/main/resources/application.properties` if your DB credentials differ.

3. Run the application:

```bash
./mvnw spring-boot:run
```

---

## 4. Frontend Setup (Angular)

1. Navigate to the `/frontend` folder.

2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
ng serve
```

4. Navigate to:

```
http://localhost:4200/
```

---

# 📁 Project Structure

```
root/
├── backend/
├── frontend/
└── README.md
```

---

# 🧪 Development Notes

* Backend runs on default Spring Boot port `8081`
* Frontend runs on `4200`
* PostgreSQL runs on `5432`
* On first run, the backend will automatically seed the database 
with sample users, subreddits, posts, and comments.
Login with any seeded user or create a new user

---

# 📦 Build for Production

## Backend

```bash
./mvnw clean package
```

## Frontend

```bash
ng build --configuration production
```

