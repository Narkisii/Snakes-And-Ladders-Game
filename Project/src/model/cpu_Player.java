package model;

import java.util.ArrayList;
import java.util.Random;

import control.BoardControl;
import control.QuestionPopControl;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.util.Duration;

//interface Command {
//	void execute();
//	void execute_question();
//}

public class cpu_Player extends Player {
	int diceResult;
	Board board;
	String difficulty;
	private QuestionPopControl question_Controll;
	private BoardControl board_Controll;
	public cpu_Player(int ID, String color, String name, String token) {
		super(ID, color, name, token);
		difficulty = GameData.getInstance().getDifficulty();
		System.out.println(this.toString());
	}

	public void executeDice_roll() {
		// Simulate rolling the dice and store the result
		this.board  = GameData.getInstance().getBoard();
		diceResult = board.get_Dice_Result();	
		board_Controll.roll(diceResult, this);
	}

	public void set_question_controll(QuestionPopControl questionPopControl) {
		question_Controll = questionPopControl;
//		question_Controll.getAnswerFour().setSelected(false);
	}
	
	public void set_board_controll(BoardControl questionPopControl) {
		board_Controll = questionPopControl;
//		question_Controll.getAnswerFour().setSelected(false);
	}

	public void selectAnswer() {
	    ArrayList<RadioButton> answers = question_Controll.getAnswerRadioButtons();
	    int getCorr_answer = question_Controll.getCorr_answer();

	    Random rand = new Random();
	    int r;

	    //Theres a 25% for answer right
	    if(difficulty.equals("Easy")) {
	        r = rand.nextInt(4);
	    }
	    //Theres a 50% for answer right
	    else if(difficulty.equals("Medium")) {
	        // 50% chance to select the correct answer
	        if(rand.nextBoolean()) {
	            r = getCorr_answer;
	        } else {
	            do {
	                r = rand.nextInt(4);
	            } while (r == getCorr_answer);
	        }
	    }
	    //Theres a 75% for answer right
	    else if(difficulty.equals("Hard")) {
	        // 75% chance to select the correct answer
	        if(rand.nextInt(4) != 0) {
	            r = getCorr_answer;
	        } else {
	            do {
	                r = rand.nextInt(4);
	            } while (r == getCorr_answer);
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid difficulty level");
	    }

	    RadioButton selectedAnswer = answers.get(r);
	    selectedAnswer.setSelected(true);
	}


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
