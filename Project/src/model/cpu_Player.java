package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import control.BoardControl;
import control.QuestionPopControl;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.util.Duration;

/**
 * The cpu_Player class extends the Player class and represents a computer-controlled player.
 * It includes methods for rolling the dice, selecting an answer, and pressing a button.
 */
public class cpu_Player extends Player {
	int diceResult;
	Board board;
	String difficulty;
	private QuestionPopControl question_Controll;
	private BoardControl board_Controll;
	
	/**
     * Constructor for the cpu_Player class.
     * Initializes the player with an ID, color, name, and token.
     * Also sets the difficulty level of the game.
     *
     * @param ID The ID of the player.
     * @param color The color of the player.
     * @param name The name of the player.
     * @param token The token of the player.
     */
	public cpu_Player(int ID, String color, String name, String token) {
		super(ID, color, name, token);
		difficulty = GameData.getInstance().getDifficulty();
		System.out.println(this.toString());
	}

	/**
     * Simulates rolling the dice and stores the result.
     * Also calls the roll method of the board controller.
     */
	public void executeDice_roll() {
		// Simulate rolling the dice and store the result
		this.board  = GameData.getInstance().getBoard();
		diceResult = board.get_Dice_Result();	
		board_Controll.roll(diceResult, this);
	}

	 /**
     * Sets the question controller for the player.
     *
     * @param questionPopControl The question controller to be set.
     */
	public void set_question_controll(QuestionPopControl questionPopControl) {
		question_Controll = questionPopControl;
	}
	
	 /**
     * Sets the board controller for the player.
     *
     * @param questionPopControl The board controller to be set.
     */
	
	public void set_board_control(BoardControl questionPopControl) {
		board_Controll = questionPopControl;
	}

	/**
     * Selects an answer for the player based on the game's difficulty level.
     * The method randomly selects an answer with a higher chance of being correct for higher difficulty levels.
     */
	public void selectAnswer() {
	    ArrayList<RadioButton> answers = question_Controll.getAnswerRadioButtons();
	    int getCorr_answer = question_Controll.getCorr_answer();

	    Random rand = new Random();
	    int r;

	    //Theres a 25% for answer right
	    if(difficulty.equals("Easy")) {
	        r = rand.nextInt(4)+1;
	    }
	    //Theres a 50% for answer right
	    else if(difficulty.equals("Medium")) {
	        // 50% chance to select the correct answer
	        if(rand.nextBoolean()) {
	            r = getCorr_answer;
	        } else {
	            do {
	                r = rand.nextInt(4)+1;
	            } while (r == getCorr_answer);
	        }
	    }
	    //Theres a 75% for answer right
	    else if(difficulty.equals("Hard")) {
	        // 75% chance to select the correct answer
	        if(rand.nextInt(4) != 0) {
	            r =  getCorr_answer;
	        } else {
	            do {
	                r = rand.nextInt(4)+1;
	            } while (r == getCorr_answer);
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid difficulty level");
	    }
	    
	    System.out.println(answers + " r " + r);
	    RadioButton selectedAnswer = answers.get(r-1);
	    selectedAnswer.setSelected(true);
	}

	/**
     * Simulates the player pressing a button.
     * The method fires the check answer button of the question controller.
     */
    public void pressButton() {
        Button checkAnswerButton = question_Controll.getCheckAnswerButton();
        checkAnswerButton.fire();
    }


	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

}
