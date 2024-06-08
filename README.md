# Snakes and Ladders Board Game

## Team Members
- Narkis Rosen
- Itay Olivcovitz
- Lia Roiyhvarg
- Lior Fokin
- Ariel Bubis

## General Description
**Snakes and Ladders** is a classic board game that can be played by two or more players. The objective of the game is to navigate one's game piece, according to die rolls, from the start (position 1) to the finish, helped or hindered by ladders and snakes respectively.

## Launching the System
- **JAR File**: Click on the JAR file to start the system.
- **Eclipse IDE**: Use "Main.java" to launch from Eclipse.

## Tools Used:

- **Java**: Primary programming language for project development.
- **JavaFX**: Utilized for creating GUI and animations.
- **JSON**: Employed for data storage and management.
  
## Design Patterns:

- **Observer Pattern**: Enabled real-time updates to UI components and sound effects based on game events.
- **Command Pattern**: Allowed separation of request initiation and execution, providing flexibility in command management.
- **Factory Pattern**: Centralized creation of game components like ladders and snakes, enhancing scalability.
  
## Singleton Pattern
We have implemented the **Singleton** design pattern to manage the game information. This ensures that there is only one instance of the game information class, providing a global point of access to it throughout the application.

## MVC Architecture
The project is structured using the **MVC** (Model-View-Controller) architecture, which separates the application into three interconnected components. This allows for efficient code organization and easier maintenance.


## Algorithms:

Gameplay Mechanics: Custom algorithms ensured fairness and rule adherence in dice rolls and player movements.
- **Encryption Algorithm**: Secured user data for confidentiality.
- **JSON Read/Write**: Streamlined game data storage and retrieval.
- **Input Validation with Regex**: Maintained data integrity and prevented errors.
- **Bot player**: Simulated intelligent behaviour for CPU player decisions.
- **Animations Algorithm**: JavaFX AnimationTimer used for dynamic visual effects.

## User Interface
- **Menu Screen**: Navigate through "Start", "Question Wizard", "History", "Instructions", and "Sound" options.
- **Settings Screen**: Select difficulty level, number of participants, and configure players.
- **History Screen**: View previous game records.
- **Players Screen**: Choose colors, tokens, and input player names. Add or remove CPU players.
- **Board Screen**: Return to the main menu or roll the dice.
- **Game Question Screen**: Answer multiple-choice questions during gameplay.
- **Question Wizard Screen**: Filter, edit, add, or search for questions.
- **Add/Edit Question Pop-Up**: Input question details and save or delete text.

## System Testing
- **Black Box Tests**: Test game initialization, player turn sequence, dice roll functionality, ladder and snake interactions, and winning conditions.
- **White Box Tests**: Verify dice roll range, player position updates, ladder climb, snake slide mechanics, answer validation logic, and game completion conditions.
- **JUnit Tests**: Assess end-game conditions, snake and ladder interactions, dice roll range, and player movement accuracy.


## System Components

## Gameplay
The game progresses as players answer questions and use ladders to advance while avoiding snakes. If a player rolls a number between 6 and 9, they will receive a question. Answering the question correctly will allow the player to move forward a certain number of squares, while answering incorrectly will cause the player to move backward. The game ends when the first player reaches the last square.

### Game Board
- **Easy Level**: 7x7 grid (49 squares)
- **Medium Level**: 10x10 grid (100 squares)
- **Hard Level**: 13x13 grid (169 squares)

Each square is sequentially numbered according to the level's maximum number.

### Special Squares
- **Question Square**: Marked with a question mark
- **Surprise Square**: Marked with a symbol of your choice
- **Extra Turn Square**: Marked with a plus (+)

### Players
- **Objective**: Reach the end of the board first
- **Starting Position**: Square number 1
- **Number of Players**: At least 2

### Bot Players
- **Purpose**: Play against the player
- **Types**: Difficulty (Chance to be correct when answering a question) is depending on the game's difficulty level

### Ladders
- **Purpose**: Help players advance faster
- **Types**: Defined according to the game level

### Snakes
- **Purpose**: Hinder player progress
- **Types**: Distinguished by color and length

### Game Die
- **Purpose**: Determine the player's action on their turn
- **Options**: Up to 10, depending on the game's difficulty level

## Questions
Questions are structured as multiple-choice and are written by the team members on course topics. There should be at least 30 questions prepared, categorized into easy, medium, and hard levels.
