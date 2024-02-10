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
	private Question question ;
	
	public Tile(int type, Snake snake, Ladder ladder) {
		super();
		if(snake == null)
			this.snake = snake;
		else snake = null;
		
		if(ladder == null)
			this.ladder = ladder;
		else ladder = null;
		
		if(type != 0)
			this.type = type;
		else type = 0;
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
	
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
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
