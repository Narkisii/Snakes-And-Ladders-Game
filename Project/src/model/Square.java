/**
 * 
 */
package model;

import java.util.Random;

/**
 * @author liorf
 *
 */
public class Square {
	private int type; // 0 for no special type, 1 for question, 2 for surprise, 3 for extra turn
	private int[] position; // position of certain square on the board.

	/**
	 * @param type
	 * @param position
	 */
	public Square(int[] position) {
		setType();
		this.position = position;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	private void setType() {
		type = new Random().nextInt(4);
	}

	/**
	 * @return the position
	 */
	public int[] getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int[] position) {
		this.position = position;
	}

}
