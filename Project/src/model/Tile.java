package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Intrefaces.GameEventObserver;
import Intrefaces.GameEventSubject;
import enums.GameEvent;

/**
 * The Tile class represents a single tile on the game board. It implements the
 * GameEventSubject interface to allow observers to subscribe to game events.
 * Each tile can have different types and may contain snakes, ladders, or
 * questions etc.
 */
public class Tile implements GameEventSubject {

	private int type; // 10 = special 10 step forward, -10 - special 10 steps backward, 1 = red snake
						// 4 - question, 5 - ladder, -5 - snake
	private Snake snake; // Snake head
	private Ladder ladder; // Ladder start
	private Question question; // Question associated with the tile
	private String image_path; // Path to the image associated with the tile
	private int id; // Unique identifier for the tile
	private static final Map<Integer, String> TILE_IMAGE_PATHS = createImagePathMap(); // Map containing tile type and
																						// image path mappings
	private List<GameEventObserver> observers = new ArrayList<>(); // List of observers subscribed to this tile

	/**
	 * Constructor for creating a tile with specified parameters.
	 * 
	 * @param id     The unique identifier for the tile.
	 * @param type   The type of the tile.
	 * @param snake  The snake associated with the tile (if any).
	 * @param ladder The ladder associated with the tile (if any).
	 */
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

	// Observer methods

	@Override
	public void attach(GameEventObserver observer) {
		observers.add(observer);
		System.out.println("attached");
	}

	@Override
	public void detach(GameEventObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(GameEvent event) {
		for (GameEventObserver observer : observers) {
			observer.onEventTriggered(event);
		}
	}
	// End of observer methods

	// Method to create image path mappings based on tile types
	private static Map<Integer, String> createImagePathMap() {
		Map<Integer, String> imagePathMap = new HashMap<>();
		imagePathMap.put(10, "/view/Images/gift.png");
		imagePathMap.put(-10, "/view/Images/gift.png");
		imagePathMap.put(1, "/view/Images/Snakes/red_snake.png");
		imagePathMap.put(4, "/view/Images/question.png");
		// Add more mappings as needed
		return imagePathMap;
	}

	/**
	 * Default constructor for creating a tile with random parameters.
	 */
	public Tile() {
		setType();
	}

	/**
	 * Get the image path associated with the tile.
	 * 
	 * @return The image path.
	 */
	public String get_Image() {
		return TILE_IMAGE_PATHS.getOrDefault(type, "/view/Images/default.png");
	}

	/**
	 * Set the image path for the tile.
	 * 
	 * @param image_path The image path to set.
	 */
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	/**
	 * Get the unique identifier of the tile.
	 * 
	 * @return The unique identifier of the tile.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the snake associated with the tile.
	 * 
	 * @return The snake associated with the tile.
	 */
	public Snake getSnake() {
		return snake;
	}

	/**
	 * Set the snake associated with the tile.
	 * 
	 * @param snake The snake to set.
	 */
	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	/**
	 * Get the ladder associated with the tile.
	 * 
	 * @return The ladder associated with the tile.
	 */
	public Ladder getLadder() {
		return ladder;
	}

	/**
	 * Set the ladder associated with the tile.
	 * 
	 * @param ladder The ladder to set.
	 */
	public void setLadder(Ladder ladder) {
		this.ladder = ladder;
	}

	/**
	 * Set the type of the tile.
	 * 
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Get the question associated with the tile.
	 * 
	 * @return The question associated with the tile.
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * Set the question associated with the tile.
	 * 
	 * @param question The question to set.
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * Get the type of the tile.
	 * 
	 * @return The type of the tile.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set the type of the tile to default value (0).
	 */
	private void setType() {
		type = 0;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
