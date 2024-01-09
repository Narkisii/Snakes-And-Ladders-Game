/**
 * 
 */
package model;

import enums.Colors;

/**
 * @author liorf
 *
 */
public class Snake extends TransferObj {

	private Colors color; // Color of the snake will decide the length of it

	/**
	 * @param startP
	 * @param endP
	 */
	public Snake(int[] startP, int[] endP, Colors color) {
		super(startP, endP);
		setColor(color);
	}

	/**
	 * @return the color
	 */
	public Colors getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Colors color) {
		this.color = color;
	}

}
