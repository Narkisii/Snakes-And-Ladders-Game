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

	/**
	 * @param type
	 */
	public Board(int numTiles) {
	    setGameboard(new Tile[numTiles][numTiles]);
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
