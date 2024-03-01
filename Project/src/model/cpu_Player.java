package model;

import java.util.ArrayList;
import java.util.Random;

import control.BoardControl;
import control.questionPopControl;
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
	private questionPopControl question_Controll;
	private BoardControl board_Controll;
	public cpu_Player(int ID, String color, String name, String token) {
		super(ID, color, name, token);
		difficulty = GameData.getInstance().getDifficulty();
		System.out.println(this.toString());
	}

	public void executeDice_roll() {
		// Simulate rolling the dice and store the result
//		this.board  = GameData.getInstance().getBoard();
		diceResult = board.get_Dice_Result();	
		board_Controll.roll(diceResult, this);
	}

	public void set_question_controll(questionPopControl questionPopControl) {
		question_Controll = questionPopControl;
//		question_Controll.getAnswerFour().setSelected(false);
	}
	
	public void set_board_controll(BoardControl questionPopControl) {
		board_Controll = questionPopControl;
//		question_Controll.getAnswerFour().setSelected(false);
	}

    public void selectAnswer() {
        ArrayList<RadioButton> answers = new ArrayList<RadioButton>();
        RadioButton answ1 = question_Controll.getAnswerOne();
        RadioButton answ2 = question_Controll.getAnswerTwo();
        RadioButton answ3 = question_Controll.getAnswerThree();
        RadioButton answ4 = question_Controll.getAnswerFour();
        
        answers.add(answ1);
        answers.add(answ2);
        answers.add(answ3);
        answers.add(answ4);

        Random rand = new Random();
        int r = rand.nextInt(4);
        
        RadioButton slectedAnswer = answers.get(r);
        slectedAnswer.setSelected(true);
    }

    public void pressButton() {
        Button checkAnswerButton = question_Controll.getCheckAnswerButton();
        checkAnswerButton.fire();
    }


	/**
	 * @return the diceResult
	 */
	public int getDiceResult() {
		return diceResult;
	}

	/**
	 * @param diceResult the diceResult to set
	 */
	public void setDiceResult(int diceResult) {
		this.diceResult = diceResult;
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
