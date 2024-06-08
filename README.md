# Snakes and Ladders Board Game

## General Description
**Snakes and Ladders** is a classic board game that involves navigating a player's piece, based on die rolls, from the start (square 1) to the finish (square 100), while encountering various obstacles such as snakes and ladders.

## System Components

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

## Gameplay
The game progresses as players answer questions and use ladders to advance while avoiding snakes. The game ends when the first player reaches the last square.

## Software Structure
- **Data Management**: A central class named `SysData` will hold game history and question data.
- **Additional Instructions**: The project must be written in JAVA using Eclipse or IntelliJ. Version control is managed through GitHub, and question data is stored in a JSON file with a fixed schema.

## Setup and Running
Instructions on how to set up and run the project will be provided here.

## Contributing
Guidelines for contributing to the project will be outlined here.

## License
Information about the project's license will be detailed here.
