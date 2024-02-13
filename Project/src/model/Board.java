/**
 * 
 */
package model;
import model.Snake;

import java.util.Random;

import model.Ladder;


/**
 * @author liorf
 *
 */
public class Board {
	private static String difficulty; // 0 easy game, 1 mid game, 2 hard game
	private Tile[][] gameboard;
	private Player [] players ;
	private int numTiles; // This is need to be changed to numTilesInRow
	private int dice_Roll;
	// 1 means Game has ended
	private int gameEnd = 0 ;
	
	/**
	 * @param type
	 */
	public Board(int numTiles , Player [] players ) {
	    this.players = players ;
	    this.numTiles = numTiles;
	    gameboard = new Tile[numTiles][numTiles];
	    difficulty = GameData.getDifficulty();
	    // Initialize each Tile object
	    for (int i = 0; i < numTiles; i++) {
	        for (int j = 0; j < numTiles; j++) {
	            gameboard[i][j] = new Tile(); 
	        }
	    }
	}
	private void activateTile(int x, int y ,Player player)
	{
		Tile tile = gameboard[x][y];
		Snake snake = tile.getSnake();
		Ladder ladder = tile.getLadder();
		Question question = tile.getQuestion(); 
		int answer;
		if( snake != null)
		{
			player.setCurrentP(snake.getEnd());
		}
		
		if( ladder != null)
		{
			player.setCurrentP(ladder.getEnd());
		}
		
		if( question != null)
		{
			
		}
		
	}
	public boolean move(int diceResult, Player player ) {
	    int newPosition = player.getCurrentP() + diceResult;
	    
	    // This if is check if the player as moved so much that he won
	    if (newPosition > ( numTiles*numTiles ))
	    {
	        gameEnd = 1 ;
	        player.setCurrentP(numTiles*numTiles);
	        
	        return false;
	    }
	    
	    // calculate the new row and column
	    int y = (newPosition - 1) / gameboard[0].length;
	    int x = (newPosition - 1) % gameboard[0].length;

	    // Adjust x if y is odd (for zigzag pattern)
	    if (y % 2 == 1) {
	        x = gameboard[0].length - 1 - x;
	    }

	    System.out.println("Player name : " +player.getName()+" + "+diceResult+" and now he is in X " + x + " Y " + y);
	    System.out.println("Tile number "+ newPosition);
	    System.out.println("");
	    
	    player.setCurrentP(newPosition);  // update player's position
	    activateTile(x,y,player); // if theres a special object - Snake Ladder or question it will update the location of the player
	    
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
		if (difficulty == "Easy") // if game mode is easy
			return rand.nextInt(8);
		if (difficulty == "Medium") { // if game mode is mid
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

	/**
	 * @return the type
	 */
	public String getDifficulty() {
		return difficulty;
	}

}
