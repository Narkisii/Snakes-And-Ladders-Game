/**
 * 
 */
package model;

import java.util.Random;

/**
 * @author Lior f. & Itay o. & Ariel B.
 *
 */
public class Tile {
	private int type; // 0 for no special type, 1 for question, 2 for surprise
	private Snake snake; // Snake head
	private Ladder ladder; // Ladder start
	
	
	public Tile(int type, Snake snake, Ladder ladder) {
		super();
		this.type = type;
		this.snake = snake;
		this.ladder = ladder;
	}

	/**
	 * @param type
	 * @param position
	 */
	public Tile() {
		setType();
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public Ladder getLadder() {
		return ladder;
	}

	public void setLadder(Ladder ladder) {
		this.ladder = ladder;
	}

	public void setType(int type) {
		this.type = type;
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

	

}
