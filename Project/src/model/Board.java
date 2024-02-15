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

import model.Ladder;

/**
 * @author liorf
 *
 */
public class Board {
	private String difficulty; // 0 easy game, 1 mid game, 2 hard game
	private Tile[][] gameboard;
	private LinkedList<Player> players;
	private int numTiles; // This is need to be changed to numTilesInRow
	private int dice_Roll;
	// 1 means Game has ended
	private static int gameEnd = 0;

	/**
	 * @param type
	 */
	public Board(LinkedList<Player> players,int numTiles) {
		this.players = players;
		this.numTiles = numTiles;
		this.gameboard = initGameBoard();
//		this.difficulty = difficulty;
		// Initialize each Tile object
	}

//	public Board(int numTilesInARow, LinkedList<Player> players2) {
//		// TODO Auto-generated constructor stub
//		this.numTiles = numTilesInARow;
//		this.players = players2;
//	}

	private static void activateTile(int x, int y, Player player) {
		Tile tile = GameData.getInstance().getBoard().getGameboard()[x][y];
		Snake snake = tile.getSnake();
		Ladder ladder = tile.getLadder();
		Question question = tile.getQuestion();
		int answer;
		if (snake != null) {
			player.setCurrentP(snake.getEnd());
		}

		if (ladder != null) {
			player.setCurrentP(ladder.getEnd());
		}

		if (question != null) {

		}

	}

	private  Tile[][] initGameBoard() {
		Tile[][] gameboard = new Tile[numTiles][numTiles];
		for (int i = 0; i < numTiles; i++) {
			for (int j = 0; j < numTiles; j++) {
				gameboard[i][j] = new Tile();
			}
		}
		return gameboard;
		
	}
	public boolean move(int diceResult, Player player) {

		int newPosition = player.getCurrentP() + diceResult;

		// This if is check if the player as moved so much that he won
		if (newPosition > (numTiles * numTiles)) {
			gameEnd = 1;
			player.setCurrentP(numTiles * numTiles);

			return false;
		}

		// calculate the new row and column
		int y = (newPosition - 1) / gameboard[0].length;
		int x = (newPosition - 1) % gameboard[0].length;

		// Adjust x if y is odd (for zigzag pattern)
		if (y % 2 == 1) {
			x = gameboard[0].length - 1 - x;
		}

//	    System.out.println("Player name : " +player.getName()+" + "+diceResult+" and now he is in X " + x + " Y " + y);
//	    System.out.println("Tile number "+ newPosition);
//	    System.out.println("");
		GameData.getInstance().getPlayer(player).setCurrentP(newPosition);
		GameData.getInstance().getPlayer(player).addStep(newPosition);

//	    player.setCurrentP(newPosition);  // update player's position
		activateTile(x, y, player); // if theres a special object - Snake Ladder or question it will update the
									// location of the player

		return true; // move successful, return true
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

	public static int get_Dice_Result() {
		Random rand = new Random();
		int randomNumber;
		String diff = GameData.getInstance().getDifficulty();
		if (diff == "Easy") // if game mode is easy
			return rand.nextInt(8);
		if (diff == "Medium") { // if game mode is mid
			randomNumber = rand.nextInt(13);
			switch (randomNumber) {
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
				return randomNumber;
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
//
//	/**
//	 * @return the type
//	 */
//	public String getDifficulty() {
//		return difficulty;
//	}

	private void generate_Ladders() {
		Random rand = new Random();
		String gameDiff = GameData.getInstance().getDifficulty();
		int[] laddersizes = null;
		if (gameDiff.equals("Easy"))
			laddersizes = new int[] { 1, 2, 3, 4 };
		if (gameDiff.equals("Medium"))
			laddersizes = new int[] { 1, 2, 3, 4, 5, 6 };
		if (gameDiff.equals("Hard"))
			laddersizes = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		for (int l : laddersizes) {
			System.out.println(l);
		}
		Set<Integer> usedNumbers = new HashSet<>();
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
		}

	}
	public void generate_snakes_ladders() {
		// TODO Auto-generated method stub
		generate_Ladders();
		generate_Snakes();
	}

	private void generate_Snakes() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		String gameDiff = GameData.getInstance().getDifficulty();
		int[] laddersizes = null;
		if (gameDiff.equals("Easy"))
			laddersizes = new int[] { 1, 2, 3, 4 };
		if (gameDiff.equals("Medium"))
			laddersizes = new int[] { 1, 2, 3, 4, 5, 6 };
		if (gameDiff.equals("Hard"))
			laddersizes = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		for (int l : laddersizes) {
			System.out.println(l);
		}
		Set<Integer> usedNumbers = new HashSet<>();
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

			Snake s = new Snake(endRand,startRand ,null);
			GameData.getInstance().addSnake(s);
		}

	}

}
