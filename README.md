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

## 3. Environment Variables
The backend requires a `.env` file in the `/Backend` directory.

1. Copy the example file:
```bash
cp .env.example .env
```

2. Generate a secure JWT secret:
```bash
openssl rand -base64 32
```

3. Paste the output into your `.env` file:
```
JWT_SECRET=<your_generated_secret>
DB_PASSWORD=your_db_password
DB_URL=jdbc:postgresql://localhost:5432/redditdb
DB_USERNAME=postgres
```

> ⚠️ Never commit your `.env` file to version control. It is listed in `.gitignore`.

---

## 4. Backend Setup (Spring Boot)
1. Navigate to the `/Backend` folder.
2. Run the application:
```bash
./mvnw spring-boot:run
```

---

## 5. Frontend Setup (Angular)
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

