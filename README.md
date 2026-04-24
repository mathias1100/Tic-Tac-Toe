# Tic-Tac-Toe JavaFX Desktop Application

This project is a **Java (object-oriented)** Tic-Tac-Toe desktop application designed for **macOS** using **JavaFX**. It supports both **Human vs Human** and **Human vs Computer** gameplay, uses **SQLite** to store match history, and demonstrates clean architecture with multiple design patterns, dependency injection, abstraction, and polymorphism.

## Development Language and Platform
- **Language:** Java (Object-Oriented)
- **Platform:** JavaFX Desktop Application for macOS
- **Database:** SQLite
- **Build Tool:** Gradle

## Features
- JavaFX graphical board
- Human vs Human mode
- Human vs Computer mode
- Match history saved in SQLite
- Background image integrated into the board screen
- Modular package structure
- Object-oriented design with constructor-based dependency injection

## Design Patterns Used

### 1. Strategy Pattern
**Where used:** `MoveStrategy`, `HumanMoveStrategy`, `RandomAIMoveStrategy`

**Description:**
The Strategy pattern is used to separate *how a move is selected* from the `Player` class. A human player uses `HumanMoveStrategy`, while the computer uses `RandomAIMoveStrategy`. Because of this, the game engine can work with different move behaviors without changing its own logic.

**Why it helps:**
- makes move logic replaceable
- supports polymorphism
- makes it easy to upgrade the computer later with a smarter strategy

### 2. Factory Pattern
**Where used:** `PlayerFactory`

**Description:**
The Factory pattern is used to create player objects in one place. Instead of the UI directly creating human or computer players, it asks the factory to build the correct player type.

**Why it helps:**
- centralizes player creation
- reduces repeated object construction code
- makes game setup cleaner

### 3. Observer Pattern
**Where used:** `GameObserver`, observer registration in `GameEngine`, JavaFX app implementing `GameObserver`

**Description:**
The Observer pattern is used so the game engine can notify the interface whenever something changes. After a move, win, or draw, the engine sends an update and the JavaFX screen refreshes the board and status message.

**Why it helps:**
- separates core logic from UI updates
- allows the engine to stay independent from JavaFX code
- improves modularity

### 4. Facade Pattern
**Where used:** `GameFacade`

**Description:**
The Facade pattern gives the JavaFX interface one simple entry point for the whole game. Instead of the UI managing the player factory, database classes, match-history access, and game engine separately, it talks to `GameFacade`, which coordinates those parts behind the scenes.

**Why it helps:**
- simplifies the UI code
- hides setup details behind one clean interface
- makes the architecture easier to explain and maintain

### 5. Singleton Pattern
**Where used:** `DatabaseManager`

**Description:**
The Singleton pattern is used for the database manager so the application shares one database-access object for SQLite setup and connections.

**Why it helps:**
- avoids repeated database initialization
- provides one shared access point
- works well for a small desktop application

## Object-Oriented Principles Demonstrated
- **Abstraction:** interfaces such as `MoveStrategy` and `GameObserver`
- **Polymorphism:** different strategies and player types are used through common interfaces/base classes
- **Dependency Injection:** `GameEngine` receives its players and match-history dependency through its constructor
- **Encapsulation:** board logic, players, database code, and UI responsibilities are separated into their own classes

## Project Structure
```text
src/main/java/tictactoe
├── app         # JavaFX application
├── db          # SQLite connection manager
├── engine      # Core game engine
├── facade      # Facade pattern entry point for the UI
├── factory     # Player creation
├── model       # Board, cell, mark, position, match result
├── observer    # Observer pattern classes
├── player      # Human and computer players
├── repository  # SQLite match history storage classes
├── service     # Game mode enum
├── state       # Internal game state classes
└── strategy    # Strategy pattern classes
```

## How to Run
1. Make sure Java 17 is installed.
2. Open the project in IntelliJ IDEA on macOS, or use Terminal.
3. Run:
   ```bash
   ./gradlew run
   ```
4. The JavaFX window will open.

## How to Test
```bash
./gradlew test
```

## Notes
- Match history is stored in `tictactoe.db` in the project directory.
- The computer currently uses a random available move strategy.
- The architecture is ready for stronger computer strategies later, such as Minimax.
- The UI now uses `GameFacade` so the JavaFX layer does not need to directly manage all lower-level classes.
