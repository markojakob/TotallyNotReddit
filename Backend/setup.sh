#!/bin/bash

if [ -f .env ]; then
  echo ".env already exists, skipping generation."
  exit 0
fi

# Generate a secure JWT secret
JWT_SECRET=$(openssl rand -base64 32)

cat > .env <<EOF
JWT_SECRET=${JWT_SECRET}
POSTGRES_PASSWORD=password
DB_URL=jdbc:postgresql://postgres:5432/redditdb
POSTGRES_DB=redditdb
POSTGRES_USER=postgres
EOF

echo ".env generated successfully."
echo "Run 'docker compose up --build' to start the backend and database."
echo "Then in /frontend run 'npm install && npm run dev' to start the frontend."