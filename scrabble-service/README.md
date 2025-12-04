# Scrabble Service

A Spring Boot application that serves as a backend for a Scrabble game. It contains APIs to manage the game's scoring logic. Available endpoints include:
- `POST /api/scores/new`: Saves a new score entry.
- `GET /api/scores/top/{n}`: Retrieves the top N scores.
- `POST /api/scoring-rules/batch`: Saves multiple scoring rules (for letters with the same point value) at once.
- `GET /api/scoring-rules`: Retrieves all scoring rules.
- `GET /api/scoring-rules/{letter}`: Retrieves the scoring rule for a specific letter.

## Technologies Used

- Java (Tested with Java 21)
- Spring Boot
- Lombok
- Gradle
- PostgreSQL
