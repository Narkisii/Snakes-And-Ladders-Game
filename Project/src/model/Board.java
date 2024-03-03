/**
 * 
 */
package model;

import model.Snake;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import Intrefaces.GameEventObserver;
import Intrefaces.GameEventSubject;
import javafx.scene.paint.Color;

import control.BoardControl;
import enums.GameEvent;
import model.Ladder;

/**
 * The Board class represents the game board for a Snake and Ladder game.
 * It manages the game board, players' movement, and various game events.
 * Implements the GameEventSubject interface to allow observation of game events.
 *
 */
public class Board implements GameEventSubject {
	private boolean tile_is_question;
	// private String difficulty; // 0 easy game, 1 mid game, 2 hard game
	private Tile[][] gameboard;
	private LinkedList<Player> players;
//	private int dice_Roll;
	// 1 means Game has ended
	private int gameEnd;
	private Set<Integer> usedNumbers;
	private int[] laddersizes;
	private int[] snakesizes;
	private int question_tiles;
	private int max_steps;
	private int red_snakes;
	private int surprise_tiles;
	private List<GameEventObserver> observers = new ArrayList<>(); //observer list

	// Tile type:
	// 10 = special 10 step forward, -10 - special 10 steps backward, 1 = red snake
	// 4 - question, 5 - ladder, -5 - snake

	/**
	 * Constructor for the Board class.
	 * Initializes the game board and sets the game end condition to 0.
	 * Also initializes each Tile object and a set of used numbers.
	 *
	 * @param players A LinkedList of Player objects participating in the game.
	 */
	public Board(LinkedList<Player> players) {
	    this.players = players;
	    this.gameboard = initGameBoard();
	    this.gameEnd = 0;

	    // Initialize each Tile object
	    this.tile_is_question = false;
	    usedNumbers = new HashSet<>();
	}

	// OBSERVER METHODS

	/**
	 * Attaches an observer to the list of observers.
	 *
	 * @param observer The GameEventObserver to be attached.
	 */
	@Override
	public void attach(GameEventObserver observer) {
	    observers.add(observer);
	    System.out.println("attached");
	}

	/**
	 * Detaches an observer from the list of observers.
	 *
	 * @param observer The GameEventObserver to be detached.
	 */
	@Override
	public void detach(GameEventObserver observer) {
	    observers.remove(observer);
	}

	/**
	 * Notifies all observers of a specific event.
	 *
	 * @param event The GameEvent that has been triggered.
	 */
	@Override
	public void notifyObservers(GameEvent event) {
	    for (GameEventObserver observer : observers) {
	        observer.onEventTriggered(event);
	    }
	}


	/**
	 * Activates a tile on the game board. This method handles the logic for when a player lands on a tile.
	 * It checks if the tile has a snake, a ladder, or a special effect, and updates the player's position accordingly.
	 * It also notifies the observers of the event that occurred.
	 *
	 * @param tilenum The number of the tile to be activated.
	 * @param player The player who landed on the tile.
	 * @return The number of steps the player should move as a result of landing on the tile.
	 */
	public int activateTile(int tilenum, Player player) {
	    // Calculate the position of the tile
	    int[] pos = calculatePosition(tilenum);
	    int x = pos[0];
	    int y = pos[1];

	    // Get the tile from the game board
	    Tile tile = GameData.getInstance().getBoard().getGameboard()[x][y];

	    // Attach a sound manager to the tile
	    SoundManager soundManager = new SoundManager();
	    tile.attach(soundManager);

	    // Check if the tile has a snake or a ladder
	    Snake snake = tile.getSnake();
	    Ladder ladder = tile.getLadder();

	    // Get the type of the tile
	    int speical = tile.getType();
	    int steps = 0;

	    // If the tile has a snake, move the player to the end of the snake
	    if (snake != null) {
	        tile.notifyObservers(GameEvent.PLAYER_HIT_SNAKE); // Notify observers that the player hit a snake
	        steps = snake.getEnd() - player.getCurrentP();
	        player.setCurrentP(snake.getEnd());
	        GameData.getInstance().getPlayer(player).addStep(snake.getEnd());
	    }

	    // If the tile has a ladder, move the player to the end of the ladder
	    if (ladder != null) {
	        tile.notifyObservers(GameEvent.PLAYER_HIT_LADDER); // Notify observers that the player hit a ladder
	        steps = ladder.getEnd() - player.getCurrentP();
	        player.setCurrentP(ladder.getEnd());
	        GameData.getInstance().getPlayer(player).addStep(ladder.getEnd());
	    }

	    // If the tile has a special effect, apply the effect
	    if (speical != 0) {
	        switch (speical) {
	            case (10): // Surprise: add 10 steps
	                tile.notifyObservers(GameEvent.GOOD_SURPRISE); // Notify observers of the good surprise
	                player.setCurrentP(speical);
	                GameData.getInstance().getPlayer(player).addStep(speical);
	                break;

	            case (-10): // Surprise: subtract 10 steps
	                tile.notifyObservers(GameEvent.BAD_URPRISE); // Notify observers of the bad surprise
	                player.setCurrentP(speical);
	                GameData.getInstance().getPlayer(player).addStep(speical);
	                break;

	            case (1): // Red snake: go to the first tile
	                tile.notifyObservers(GameEvent.RED_SNAKE); // Notify observers that the player hit a red snake
	                player.setCurrentP(speical);
	                GameData.getInstance().getPlayer(player).addStep(speical);
	                break;
	        }
	        steps = speical;
	    }

	    // Return the number of steps the player should move
	    return steps;
	}

	/**
	 * Initializes the game board with a specified number of tiles.
	 * Each tile is created using a TileBuilder.
	 *
	 * @return A 2D array representing the game board, with each element being a Tile object.
	 */
	private Tile[][] initGameBoard() {
	    // Get the number of tiles from the game data
	    int numTiles = GameData.getInstance().getNumOfTiles();

	    // Initialize the game board as a 2D array of Tiles
	    Tile[][] gameboard = new Tile[numTiles][numTiles];

	    // Loop through each position in the game board
	    for (int i = 0; i < numTiles; i++) {
	        for (int j = 0; j < numTiles; j++) {
	            // Create a new Tile using a TileBuilder and assign it to the current position
	            gameboard[i][j] = new Tile();
	        }
	    }

	    // Return the initialized game board
	    return gameboard;
	}

	/**
	 * Moves a player on the game board based on the result of a dice roll.
	 * The method checks if the player has won the game or if the new position is valid.
	 * It then updates the player's position and notifies the observers of the move.
	 *
	 * @param diceResult The result of the dice roll.
	 * @param player The player who is moving.
	 * @return A boolean indicating whether the move was successful. Returns false if the player has won the game, true otherwise.
	 */
	public boolean move(int diceResult, Player player) {
	    // Get the number of tiles from the game data
	    int numTiles = GameData.getInstance().getNumOfTiles();

	    // Calculate the new position of the player
	    int newPosition = player.getCurrentP();
	    if (newPosition != (numTiles * numTiles))
	        newPosition = newPosition + diceResult;

	    // Check if the player has won the game
	    if (newPosition >= (numTiles * numTiles)) {
	        newPosition = numTiles * numTiles;
	        gameEnd = 1;
	        player.setCurrentP((numTiles * numTiles));
	        return false; // The player has won the game, return false
	    }

	    // Check if the new position is valid
	    if (newPosition <= 0) {
	        newPosition = 1;
	        player.setCurrentP(1);
	    }

	    // Calculate the x and y coordinates of the new position
	    int[] pos = calculatePosition(newPosition);
	    int x = pos[0];
	    int y = pos[1];

	    // Print the player's name, the dice result, and the new position
	    System.out.println("Player name : " + player.getName() + " + " + diceResult + " and now he is in X " + x + " Y " + y);

	    // Update the player's position if it is different from the previous step
	    if (newPosition != player.getPreviousStep() || player.getCurrentP() != 1 || player.getCurrentP() != (numTiles * numTiles)) {
	        GameData.getInstance().getPlayer(player).setCurrentP(newPosition); // Update player's position
	        GameData.getInstance().getPlayer(player).addStep(newPosition); // Add the new position to the player's steps
	    }

	    return true; // The move was successful, return true
	}

	/**
	 * Retrieves a tile from the game board based on its number.
	 * The method calculates the position of the tile and returns the Tile object at that position.
	 *
	 * @param tile_num The number of the tile to be retrieved.
	 * @return The Tile object at the specified position. If no tile exists at that position, returns null.
	 */
	public Tile getTile(int tile_num) {
	    // Calculate the position of the tile
	    int[] pos = calculatePosition(tile_num);
	    int x = pos[0];
	    int y = pos[1];

	    // Get the tile from the game board
	    Tile tile = gameboard[x][y];

	    // Check if the tile exists
	    if (tile != null) {
	        return tile; // If the tile exists, return it
	    }

	    // If no tile exists at the specified position, return null
	    return null;
	}


	/**
	 * Checks if a tile on the game board is a question tile based on its number.
	 * The method calculates the position of the tile and checks the type of the Tile object at that position.
	 *
	 * @param tile_num The number of the tile to be checked.
	 * @return The Tile object if it is a question tile. If it is not a question tile, returns null.
	 */
	public Tile is_question(int tile_num) {
	    // Calculate the position of the tile
	    int[] pos = calculatePosition(tile_num);
	    int x = pos[0];
	    int y = pos[1];

	    // Get the tile from the game board
	    Tile tile = gameboard[x][y];

	    // Check if the tile is a question tile
	    if (tile.getType() == 4) {
	        return tile; // If the tile is a question tile, return it
	    }

	    // If the tile is not a question tile, return null
	    return null;
	}


	/**
	 * Calculates the position of a tile on the game board based on its number.
	 * The method calculates the row and column of the tile, adjusting for a zigzag pattern.
	 *
	 * @param newPosition The number of the tile.
	 * @return An array of two integers representing the row and column of the tile.
	 */
	private int[] calculatePosition(int newPosition) {
	    // Initialize an array to hold the position
	    int[] position = new int[2];

	    // Get the number of tiles from the game data
	    int numTiles = GameData.getInstance().getNumOfTiles();

	    // Calculate the row (y-coordinate) and column (x-coordinate) of the tile
	    position[1] = (newPosition - 1) / numTiles; // y
	    position[0] = (newPosition - 1) % numTiles; // x

	    // Adjust the x-coordinate if the y-coordinate is odd, accounting for the zigzag pattern
	    if (position[1] % 2 == 1) {
	        position[0] = numTiles - 1 - position[0];
	    }

	    // Return the calculated position
	    return position;
	}


	public int getGameEnd() {
		return gameEnd;
	}

	public void setGameEnd(int gameEnd) {
		this.gameEnd = gameEnd;
	}

	/**
	 * @return the gameboard
	 */
	public Tile[][] getGameboard() {
		return gameboard;
	}

	/**
	 * @param gameboard the gameboard to set
	 */
	public void setGameboard(Tile[][] gameboard) {
		for (int i = 0; i < gameboard.length; i++) {
			for (int j = 0; j < gameboard[i].length; j++) {
				this.gameboard[i][j] = new Tile();
			}
		}
	}

	/**
	 * Adds a Ladder object to a specific tile on the game board.
	 *
	 * @param tile_num The number of the tile where the ladder will be added.
	 * @param l The Ladder object to be added to the tile.
	 */
	public void add_LadderToTile(int tile_num, Ladder l) {
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];
		
		Tile tile = gameboard[x][y];
		tile.setLadder(l);
		tile.setType(5);
	}

	/**
	 * Adds a Snake object to a specific tile on the game board.
	 *
	 * @param tile_num The number of the tile where the snake will be added.
	 * @param s The Snake object to be added to the tile.
	 */
	public void add_SnakeToTile(int tile_num, Snake s) {
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];
		Tile tile = gameboard[x][y];
		tile.setSnake(s);
		tile.setType(-5);

	}

	/**
	 * Adds a question to a specific tile on the game board.
	 *
	 * @param tile_num The number of the tile where the question will be added.
	 * @param difficulty The difficulty level of the question.
	 */
	public void add_QuestionToTile(int tile_num, int difficulty) {
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];
		Tile tile = gameboard[x][y];
		tile.setQuestion(GameData.getInstance().get_Question(difficulty));
		tile.setType(4);
		tile.setId(tile_num);
		GameData.getInstance().addspecialTiles_list(tile);
	}

	/**
	 * Adds a special effect to a specific tile on the game board.
	 *
	 * @param tile_num The number of the tile where the special effect will be added.
	 * @param Type The type of the special effect. 10 = special 10 step forward, -10 = special 10 steps backward, 1 = red snake.
	 */
	public void add_specialToTile(int tile_num, int Type) {// 10 = special 10 step forward, -10 - special 10 steps
															// backward, 1 = red snake
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];
		Tile tile = gameboard[x][y];
		tile.setType(Type);
		tile.setId(tile_num);
		GameData.getInstance().addspecialTiles_list(tile);
	}

	/**
	 * Generates a dice result based on the game's difficulty level.
	 *
	 * @return The result of the dice roll.
	 */
	public int get_Dice_Result() {
		Random rand = new Random();
		int randomNumber;
		String diff = GameData.getInstance().getDifficulty();
		int dice = -1;

		if (diff == "Easy") { // if game mode is easy
			dice = rand.nextInt(8);// 0-4 - steps 5,6,7 - questions
			switch (dice) {
			case 5:
				return 7;
			case 6:
				return 8;
			case 7:
				return 9;
			default:
				return dice;
			}
		}
		if (diff == "Medium") { // if game mode is mid
			dice = rand.nextInt(13);// 0-6 - steps 7,8,9,10,11,12 - questions
			switch (dice) {
			case 7:
			case 8:
				return 7;
			case 9:
			case 10:
				return 8;
			case 11:
			case 12:
				return 9;
			default:
				return dice;
			}
		} else { // if game mode is hard
			randomNumber = rand.nextInt(15);
			switch (randomNumber) {
			case 7:
			case 8:
				return 7;
			case 9:
			case 10:
				return 8;

			case 11:
			case 12:
			case 13:
			case 14:
				return 9;
			default:
				return randomNumber;
			}
		}
	}
	
	/**
	 * Generates red snakes and surprise tiles on the game board.
	 * The method randomly selects tiles for these objects, ensuring that each tile is unique.
	 */
	private void generate_RedSnake_and_Surprize() {
		Random rand = new Random();
		int num_of_tiles = GameData.getInstance().getNumOfTiles();
		int randNum;
		for (int i = 0; i < surprise_tiles; i++) {
			// Add surprise tile 10 steps for or 10 steps back
			do {
				randNum = rand.nextInt(num_of_tiles * num_of_tiles - 12 + 1) + 12;
			} while (usedNumbers.contains(randNum)); // Ensure endRand doesn't exceed 49 and numbers are unique
			usedNumbers.add(randNum);
			int typeOfSurprize = rand.nextInt(2) * 2 - 1; // if good surprise or bad surprise
			add_specialToTile(randNum, 10 * typeOfSurprize);// add surprise
		}

		// Add red snake
		for (int i = 0; i < red_snakes; i++) {
			do {
				randNum = rand.nextInt(num_of_tiles * num_of_tiles - 12) + 1;
			} while (usedNumbers.contains(randNum)); // Ensure endRand doesn't exceed 49 and numbers are unique
			usedNumbers.add(randNum);
			add_specialToTile(randNum, 1);// add red snake
		}

		// Add question tile
		for (int i = 0; i < question_tiles; i++) {
			do {
				randNum = rand.nextInt(num_of_tiles * num_of_tiles - 12) + 1;
			} while (usedNumbers.contains(randNum)); // Ensure endRand doesn't exceed 49 and numbers are unique
			usedNumbers.add(randNum);
			add_QuestionToTile(randNum, 7 + i);// add question
		}
	}

	/**
	 * Generates ladders on the game board.
	 * The method randomly selects start and end tiles for each ladder, ensuring that each tile is unique.
	 */
	private void generate_Ladders() {
		Random rand = new Random();
		String gameDiff = GameData.getInstance().getDifficulty();

		for (int i : laddersizes) {
			int num_of_tiles = GameData.getInstance().getNumOfTiles();
			int startRand;
			int endRand;
			do {
				startRand = rand.nextInt(num_of_tiles * (num_of_tiles - i)) + 1; // Random tile in the first (7-i) rows
				endRand = startRand + (num_of_tiles * (i)); // Tile in a row that is (i+1) rows down
			} while (endRand > (num_of_tiles * num_of_tiles) || usedNumbers.contains(startRand)
					|| usedNumbers.contains(endRand)); // Ensure endRand doesn't exceed 49 and numbers are unique
			usedNumbers.add(startRand);
			usedNumbers.add(endRand);
			Ladder l = new Ladder(startRand, endRand, i);
			GameData.getInstance().addLadders(l);
			add_LadderToTile(startRand, l);
		}

	}

	/**
	 * Generates snakes on the game board.
	 * The method randomly selects start and end tiles for each snake, ensuring that each tile is unique.
	 */
	private void generate_Snakes() {
		Random rand = new Random();

		Color[] snake_color = { Color.YELLOW, Color.GREEN, Color.BLUE };

		for (int i : snakesizes) {
			int num_of_tiles = GameData.getInstance().getNumOfTiles();
			int startRand;
			int endRand;
			do {
				endRand = rand.nextInt(num_of_tiles * (num_of_tiles - i)) + 1; // Random tile in the first (7-i) rows
				startRand = endRand + (num_of_tiles * (i)); // Tile in a row that is (i+1) rows down
			} while (startRand > (num_of_tiles * num_of_tiles) || usedNumbers.contains(startRand)
					|| usedNumbers.contains(endRand)); // Ensure startRand doesn't exceed 49 and numbers are unique
			usedNumbers.add(startRand);
			usedNumbers.add(endRand);

			Snake s = new Snake(startRand, endRand, snake_color[i - 1]);
			GameData.getInstance().addSnake(s);
			add_SnakeToTile(startRand, s);
		}
	}

	/**
	 * Generates all objects on the game board.
	 * The method sets the sizes of ladders and snakes, and the number of red snakes, question tiles, maximum steps, and surprise tiles based on the game's difficulty level.
	 * It then calls the methods to generate ladders, snakes, red snakes, and surprise tiles.
	 */
	public void generate_board_Objects() {
		int num_of_tiles = GameData.getInstance().getNumOfTiles();
		String gameDiff = GameData.getInstance().getDifficulty();
		if (gameDiff.equals("Easy")) {
			laddersizes = new int[] { 1, 2, 3, 4 };
			snakesizes = new int[] { 1, 2, 3 };
			red_snakes = 1;
			question_tiles = 3;
			max_steps = 4;
			surprise_tiles = 0;
		}
		if (gameDiff.equals("Medium")) {
			laddersizes = new int[] { 1, 2, 3, 4, 5, 6 };
			snakesizes = new int[] { 2, 2, 3, 1 };
			red_snakes = 2;
			question_tiles = 3;
			max_steps = 6;
			surprise_tiles = 1;
		}
		if (gameDiff.equals("Hard")) {
			laddersizes = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
			snakesizes = new int[] { 1, 1, 2, 2, 3, 3 };
			red_snakes = 2;
			question_tiles = 3;
			max_steps = 6;
			surprise_tiles = 2;
		}

		usedNumbers.add(1);
		usedNumbers.add(num_of_tiles * num_of_tiles);
		generate_Ladders();
		generate_Snakes();
		generate_RedSnake_and_Surprize();
	}

}
