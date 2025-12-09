# Redcare Backend Coding Challenge

This project implements a backend service that fetches GitHub repositories via the GitHub Search API, filters them using user-provided parameters, computes a custom popularity score, and returns sorted results through a REST endpoint.  
The solution focuses on clean code, scalability, clarity, and testability.

---

## 🚀 Features

- Fetch repositories from **GitHub Search API**
- Supports filtering by:
    - programming **language**
    - **createdAfter** date
- Computes a custom **popularity score**
- Sorts repositories by score (descending)
- Clean layered architecture:
    - controller → service → client
- Includes unit tests

---

## 🛠 How to Run

Clone the repository and start the application:

```bash
git clone https://github.com/dowenwow/redcare-test.git
cd redcare-test
./gradlew bootRun
```

## The application will start at:

```
http://localhost:8080
```

## 📡 REST API
Example Request: GET
http://localhost:8080/repos?language=Java&createdAfter=2021-01-01

Example response
```
{
    "total_count": 7154,
    "items": [
        {
            "name": "Chapter08_Implementing_Concurrency_in_Java",
            "url": "https://api.github.com/repos/albonidrizi/Chapter08_Implementing_Concurrency_in_Java",
            "language": "Java",
            "start": 6,
            "forks": 0,
            "score": 62.0
        },
    ]
}
```

Example bad request response
```
{
    "message": "createdAfter must match format yyyy-MM-dd"
}
```

## 📊 Popularity Score Calculation
Each repository receives a score based on GitHub metadata.
Formula:
```ini
score = stars * 2 + forks * 1.5 + recencyScore
```
Where:

stars → number of stargazers (stargazers_count)

forks → number of forks (forks)

daysSinceLastUpdate rewards recently updated repositories:
```ini
daysSinceLastUpdate = DAYS(now - updated_at)
recencyScore = daysSinceLastUpdate < 30 ? 50 : 10 
```


## 🧱 Project Architecture
```css
src/main/java/com.redcare
├── controller        → REST endpoint (/repos)
├── service           → business logic, scoring
├── client            → GitHub API client
├── response          → DTO classes for GitHub response mapping
```

## 🧪 Testing

Unit and HTTP client tests are included.

To run all tests:
```bash
./gradlew test
```