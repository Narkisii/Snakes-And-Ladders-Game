/**
 * 
 */
package model;

import javafx.scene.paint.Color;

/**
 * @author liorf
 *
 */
/**
 * The Snake class represents a snake in a game.
 * The snake has a start position, an end position, and a color.
 * The color of the snake will decide the length of it.
 */
public class Snake {
	// The start position of the snake
	private int start;
	// The end position of the snake
	private int end ;
	// The color of the snake
	private Color color;

	/**
	 * Constructs a new Snake with the given start position, end position, and color.
	 * @param start the start position of the snake
	 * @param end the end position of the snake
	 * @param color the color of the snake
	 */
	public Snake(int start, int  end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}

	/**
	 * Returns the start position of the snake.
	 * @return the start position of the snake
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Sets the start position of the snake.
	 * @param start the new start position of the snake
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * Returns the end position of the snake.
	 * @return the end position of the snake
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * Sets the end position of the snake.
	 * @param end the new end position of the snake
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * Returns the color of the snake.
	 * @return the color of the snake
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the snake.
	 * @param color the new color of the snake
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
