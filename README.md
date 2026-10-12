# Placement Intelligence System

A Java-based desktop application designed to help college placement teams manage student records, evaluate placement readiness, rank students, and generate placement analytics.

## Features

- **Student Management:** Add, search, display, and delete student records.
- **Placement Readiness:** Classify students as Placement Ready, Needs Improvement, or Not Eligible.
- **Student Rankings:** Rank students based on their overall performance.
- **Placement Analytics:** View reports on student performance, departments, coding scores, and technical skills.
- **CSV Persistence:** Store and retrieve student records using a CSV file.
- **Graphical User Interface:** Interactive desktop dashboard built with Java Swing.

## Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java |
| Frontend | Java Swing |
| Business Logic | Java |
| Data Storage | CSV |
| IDE | Visual Studio Code |
| Version Control | Git and GitHub |

## Project Structure

```text
PlacementIntelligenceSystem/
├── src/
│   ├── com/hcl/placement/
│   │   ├── AnalyticsService.java
│   │   ├── CSVReader.java
│   │   ├── Dashboard.java
│   │   ├── Main.java
│   │   ├── PlacementEvaluator.java
│   │   ├── PlacementService.java
│   │   ├── RankingService.java
│   │   ├── Student.java
│   │   └── ValidationService.java
│   └── module-info.java
├── students.csv
├── .gitignore
└── README.md
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) compatible with the project.
- Visual Studio Code or another Java-compatible IDE.

### Run the Application

1. Clone or download this repository.
2. Open the project folder in VS Code.
3. Ensure the Java extensions and required JDK are installed.
4. Run the `Main.java` file to launch the application.

## Data Storage

Student records are stored in `students.csv`. Keep a backup of the file before making changes. Use sample data when sharing the project publicly.

## Future Enhancements

- Export placement reports.
- Add more detailed student performance visualizations.
- Improve report filtering and presentation.

## Author

**Sai Revanth Sattenapalli**

GitHub: [Revanth1712](https://github.com/Revanth1712)

## License

No license has been specified yet. Contact the repository owner before reusing or distributing this project.
