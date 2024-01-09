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
	private int[][] gameboard;

	/**
	 * @param type
	 */
	public Board(int type) {
		switch (type) {
		case 0:
			setGameboard(new int[7][7]);
			break;
		case 1:
			setGameboard(new int[10][10]);
			break;
		case 2:
			setGameboard(new int[13][13]);
			break;
		}
	}

	/**
	 * @return the gameboard
	 */
	public int[][] getGameboard() {
		return gameboard;
	}

	/**
	 * @param gameboard the gameboard to set
	 */
	public void setGameboard(int[][] gameboard) {
		for (int i = 0; i < gameboard.length; i++) {
			for (int j = 0; j < gameboard[i].length; j++) {
				this.gameboard[i][j] = 0;
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
