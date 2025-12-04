# Scrabble Points Calculator

- Frontend (React + Typescript): `scrabble-app`
- Backend (Java + Spring Boot + Postgres): `scrabble-service`

## Getting Started

Clone the repository:

```bash
git clone https://github.com/Heryani/scrabble-calculator.git
```

---

### Backend Setup

1. Open IntelliJ (or your preferred IDE) and navigate to the project directory:
   ```bash
   cd scrabble-service
   ```
2. Configure the database:
   - Ensure PostgreSQL is running.
   - Create a database named `scrabble_db`.
   - In `src/main/resources/application.properties` , update the following if needed:
   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/scrabble_db
   spring.datasource.username=<your username>
   spring.datasource.password=<your password>
   ```
3. Run the application:
   - Open the file `ScrabbleServiceApplication` and click Run (top right of IntelliJ)
   - The service will start on `http://localhost:8080`.
4. Populate the `scoring_rule` table:
   - Copy the CURL command from `curl_command.txt` in the backend root directory
   - Open Postman and import the CURL command to execute it. Run the request.

---

### Frontend Setup

1. Open VS Code (or your preferred IDE) and navigate to the project directory:
   ```bash
   cd scrabble-app
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. Open your browser and go to `http://localhost:5173` to view the app.
