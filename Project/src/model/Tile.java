/**
 * 
 */
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
 * @author Lior f. & Itay o. & Ariel B.
 *
 */
public class Tile implements GameEventSubject{
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	private int type; // 10 = special 10 step forward, -10 - special 10 steps backward, 1 = red snake
						// 4 - question, 5 - ladder, -5 - snake
	private Snake snake; // Snake head
	private Ladder ladder; // Ladder start
	private Question question;
	private String image_path;
	private int id;
    private static final Map<Integer, String> TILE_IMAGE_PATHS = createImagePathMap();
    private List<GameEventObserver> observers = new ArrayList<>(); //observer list
    
    
    
    
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
	
	
	//OBSERVER METHODS
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
		        }}
		    //END
	
	
	

	// 10 = special 10 step forward, -10 - special 10 steps backward, 1 = red snake
	// 4 - question
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
	 * @param type
	 * @param position
	 */
	public Tile() {
		setType();

	}
	/**
	 * @return the image_path
	 */
    public String get_Image() {
        return TILE_IMAGE_PATHS.getOrDefault(type, "/view/Images/default.png");
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
