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
	/**
	 * @return the image_path
	 */
	public String getImage_path() {
		return image_path;
	}

	/**
	 * @param image_path the image_path to set
	 */
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	private int type; // 10 = special 10 step forward, -10 - special 10 steps backward, 1 = red snake
						// 4 - question
	private Snake snake; // Snake head
	private Ladder ladder; // Ladder start
	private Question question;
	private String image_path;
	private int id;

	public Tile(int id, int type, Snake snake, Ladder ladder) {
		super();
		this.id = id;
		if (snake == null)
			this.snake = snake;
		else
			snake = null;

		if (ladder == null)
			this.ladder = ladder;
		else
			ladder = null;

		if (type != 0)
			this.type = type;
		else
			type = 0;
	}

	// 10 = special 10 step forward, -10 - special 10 steps backward, 1 = red snake
	// 4 - question
	public String get_Image() {
		switch (type) {
		case (10):
			this.image_path = "/view/Images/gift (1).png";
			break;

		case (-10):
			this.image_path = "/view/Images/gift (1).png";
			break;
		case (1):
			this.image_path = "/view/Images/Snakes/red_snake.png";
			break;

		case (4):
			this.image_path = "/view/Images/question (1).png";
			break;
		}
		return image_path;

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
		type = 0;
	}

}
