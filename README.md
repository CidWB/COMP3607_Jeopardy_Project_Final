# Jeopardy Game

A Java-based multiplayer Jeopardy game simulation for 2-4 players, built for COMP3607.

## Features

- Multiplayer support (2-4 players)
- Multiple question file formats: CSV, JSON, and XML
- Standard Jeopardy scoring (gain/lose points based on correctness)
- Turn-based gameplay with automatic player rotation
- Detailed game logs and reports generated after each session

## Quick Start

### Prerequisites
- Java 8 or higher
- Maven

### Setup and Run

1. **Install dependencies:**
   ```bash
   mvn clean install
   ```

2. **Run the game:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.jeopardyProject.GameRunner"
   ```

3. **Follow the prompts:**
   - Enter a case ID for the game session
   - Provide the path to your question file (or use samples from `src/resources/`)
   - Add 2-4 players with unique IDs
   - Start playing!

## How to Play

1. Players take turns selecting a **category** and **point value**
2. Answer the question by choosing A, B, C, or D
3. Correct answers add points, wrong answers subtract points
4. If a player answers incorrectly, other players get a chance to answer
5. Game ends when all questions are answered or a player types "QUIT"
6. Final scores and detailed reports are automatically saved

## Question Files

Sample question files are provided in `src/resources/`:
- `questions.csv` - CSV format
- `questions.json` - JSON format
- `questions.xml` - XML format

You can use the sample files by providing their ABSOLUTE path (e.g., `C:\Users\cidne\COMP3607_Jeopardy_Project\src\resources\sample.csv`) or create your own files following the same format. Custom files can be placed in the resources folder or accessed via absolute path.

### Question File Format
Each question should include:
- Category
- Point value (100, 200, 300, 400, 500)
- Question text
- Four answer options (A, B, C, D)
- Correct answer

Standard game board: 5 categories × 5 values = 25 questions
Answered questions are represented by 'X'

## Additional Commands

```bash
# Run tests
mvn test

# Run specific test
mvn test -Dtest=ClassName

# Generate documentation
mvn javadoc:javadoc
```

## Output

After each game, two reports are generated:
- **LogReport.csv** - Detailed event log with timestamps
- **TurnReport.txt** - Human-readable game summary with final scores
