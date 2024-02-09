/**
 * 
 */
package model;

/**
 * @author liorf
 *
 */
public class Board {
	private int type; // 0 easy game, 1 mid game, 2 hard game
	private Tile[][] gameboard;
	private Player [] players ;
	private int numTiles; // This is need to be changed to numTilesInRow
	
	// 1 means Game has ended
	private int gameEnd = 0 ;

	/**
	 * @param type
	 */
	public Board(int numTiles , Player [] players ) {
		this.players = players ;
		this.numTiles = numTiles;
		gameboard =new Tile[numTiles][numTiles];
	}

	public boolean move(int diceResult, Player player ) {
	    int newPosition = player.getCurrentP() + diceResult;
	    
	    // This if is check if the player as moved so much that he won
	    if (newPosition >= ( numTiles*numTiles ))
	    {
	    	gameEnd = 1 ;
	    	return false;
	    }
	    player.setCurrentP(newPosition);  // update player's position
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

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

}
