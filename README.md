# TotallyNotReddit
A Reddit clone app. Built with Spring Boot and Angular using PostgreSQL.

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
* [View the ER diagram on DBdiagram.io](https://dbdiagram.io/d/699b4006bd82f5fce26fd02b)

**Build Tools**
* Maven
* Angular CLI

---

# 🚀 Getting Started

## Prerequisites
Ensure you have the following installed:
* Docker & Docker Desktop
* Node.js (LTS version)
* Angular CLI

---

## 1. Clone & Setup Environment

Navigate to the `/Backend` folder and run the setup script to generate your `.env`:

```bash
cd Backend
./setup.sh
```

This automatically generates a secure JWT secret and creates the required `.env` file.

> ⚠️ Never commit your `.env` file to version control. It is listed in `.gitignore`.

---

## 2. Start the Backend & Database (Docker)

From the `/Backend` folder:

```bash
docker compose up --build
```

This starts both PostgreSQL and the Spring Boot backend. No Java installation required.

* Backend runs on `http://localhost:8081`
* PostgreSQL runs on `localhost:5432`

---

## 3. Start the Frontend (Angular)

In a separate terminal, navigate to the `/frontend` folder:

```bash
npm install
ng serve
```

Navigate to `http://localhost:4200/`

---

# 📁 Project Structure
```
root/
├── Backend/
│   ├── Dockerfile
│   ├── compose.yaml
│   ├── setup.sh
│   └── src/
├── frontend/
└── README.md
```

---

# 🧪 Development Notes
* Backend runs on port `8081`
* Frontend runs on port `4200`
* PostgreSQL runs on port `5432`
* On first run, the backend automatically seeds the database with sample users, subreddits, posts, and comments.
* Login with any seeded user or create a new account.

