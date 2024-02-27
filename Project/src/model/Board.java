/**
 * 
 */
package model;

import model.Snake;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import javafx.scene.paint.Color;

import control.BoardControl;
import model.Ladder;

/**
 * @author liorf
 *
 */
public class Board {
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
	//Tile type:
	// 10 = special 10 step forward, -10 - special 10 steps backward, 1 = red snake
	// 4 - question, 5 - ladder, -5 - snake
	/**
	 * @param type
	 */
	public Board(LinkedList<Player> players) {
		this.players = players;
		this.gameboard = initGameBoard();
		this.gameEnd = 0;
//		this.difficulty = difficulty;
		// Initialize each Tile object
		this.tile_is_question = false;
		usedNumbers = new HashSet<>();

	}

//	public Board(int numTilesInARow, LinkedList<Player> players2) {
//		// TODO Auto-generated constructor stub
//		this.numTiles = numTilesInARow;
//		this.players = players2;
//	}

	public int activateTile(int tilenum, Player player) {
		int[] pos = calculatePosition(tilenum);
		int x = pos[0];
		int y = pos[1];
		
		Tile tile = GameData.getInstance().getBoard().getGameboard()[x][y];
		Snake snake = tile.getSnake();
		Ladder ladder = tile.getLadder();
//		Question question = tile.getQuestion();
		int speical = tile.getType();
		int steps = 0;
		int answer;
		if (snake != null) {
			steps = snake.getEnd() - player.getCurrentP();
			player.setCurrentP(snake.getEnd());
			GameData.getInstance().getPlayer(player).addStep(snake.getEnd());
		}

		if (ladder != null) {
			steps = ladder.getEnd() - player.getCurrentP();
			player.setCurrentP(ladder.getEnd());
			GameData.getInstance().getPlayer(player).addStep(ladder.getEnd());
		}

//		if (question != null) {
//			this.tile_is_question = true;
//		}
		if (speical != 0) {
			switch (speical) {
			case (10):
				player.setCurrentP(speical);
				GameData.getInstance().getPlayer(player).addStep(speical);

				break;

			case (-10):
				player.setCurrentP(speical);
				GameData.getInstance().getPlayer(player).addStep(speical);

				break;
			case (1):
				player.setCurrentP(speical);
				GameData.getInstance().getPlayer(player).addStep(speical);
				break;
			}
			steps = speical;
		}
		return steps;
	}

	private Tile[][] initGameBoard() {
		int numTiles = GameData.getInstance().getNumOfTiles();
		Tile[][] gameboard = new Tile[numTiles][numTiles];
		for (int i = 0; i < numTiles; i++) {
			for (int j = 0; j < numTiles; j++) {
				gameboard[i][j] = new TileBuilder().build();
			}
		}
		return gameboard;
	}

	public boolean move(int diceResult, Player player) {
		int numTiles = GameData.getInstance().getNumOfTiles();

		int newPosition = player.getCurrentP();
		if (newPosition != (numTiles * numTiles))
			newPosition = newPosition + diceResult;
		// This if is check if the player as moved so much that he won
		if (newPosition >= (numTiles * numTiles)) {
			gameEnd = 1;
			player.setCurrentP((numTiles * numTiles));
			return false;
		}

//		int[] pos = calculatePosition(newPosition);
//		int x = pos[0];
//		int y = pos[1];
//	    System.out.println("Player name : " +player.getName()+" + "+diceResult+" and now he is in X " + x + " Y " + y);
//	    System.out.println("Tile number "+ newPosition);
//	    System.out.println("");
		GameData.getInstance().getPlayer(player).setCurrentP(newPosition);// update player's position
		GameData.getInstance().getPlayer(player).addStep(newPosition);

//		activateTile(x, y, player); // if ther's a special object - Snake Ladder or question it will update the
									// location of the player

		return true; // move successful, return true
	}

	public Tile getTile(int tile_num) {
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];

		Tile tile = gameboard[x][y];
		if (tile != null) {
			return tile;
		}
		return null;
	}

	public Tile is_question(int tile_num) {
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];

		Tile tile = gameboard[x][y];
		if (tile.getType() == 4) {
			return tile;
		}
		return null;
	}

	private int[] calculatePosition(int newPosition) {
		int[] position = new int[2];
		int numTiles = GameData.getInstance().getNumOfTiles();

		// calculate the new row and column
		position[1] = (newPosition - 1) / numTiles; // y
		position[0] = (newPosition - 1) % numTiles; // x

		// Adjust x if y is odd (for zigzag pattern)
		if (position[1] % 2 == 1) {
			position[0] = numTiles - 1 - position[0];
		}
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

	public void add_LadderToTile(int tile_num, Ladder l) {
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];
//		System.out.println(
//				"Ladder start " + l.getStart() + " ladder end " + l.getEnd() + " x " + pos[0] + " y " + pos[1]);

		Tile tile = gameboard[x][y];
		tile.setLadder(l);
		tile.setType(5);
	}

	public void add_SnakeToTile(int tile_num, Snake s) {
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];
		Tile tile = gameboard[x][y];
		tile.setSnake(s);
		tile.setType(-5);

	}

	public void add_QuestionToTile(int tile_num, Question q) {
		int[] pos = calculatePosition(tile_num);
		int x = pos[0];
		int y = pos[1];
		Tile tile = gameboard[x][y];
		tile.setQuestion(q);
		tile.setType(4);
		tile.setId(tile_num);
		GameData.getInstance().addspecialTiles_list(tile);
	}

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
			add_QuestionToTile(randNum, new Question());// add question
		}
	}

	private void generate_Ladders() {
		Random rand = new Random();
		String gameDiff = GameData.getInstance().getDifficulty();

//		Set<Integer> usedNumbers = new HashSet<>();
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
//			System.out.println("ladder start: " + startRand + " ladder end: " + endRand);
			Ladder l = new Ladder(startRand, endRand, i);
			GameData.getInstance().addLadders(l);
			add_LadderToTile(startRand, l);
		}

	}

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

}
