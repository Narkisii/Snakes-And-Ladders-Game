package model;

import control.BoardControl;
import control.questionPopControl;
import javafx.scene.control.RadioButton;

interface Command {
	void execute();
	void execute_question();
}

public class cpu_Player extends Player implements Command {
	int diceResult;
	Board board;
	private questionPopControl question_Controll;
	public cpu_Player(int ID, String color, String name, String token) {
		super(ID, color, name, token);
		System.out.println(this.toString());
	}

	public void execute() {
		// Simulate rolling the dice and store the result
		this.board  = GameData.getInstance().getBoard();
		diceResult = board.get_Dice_Result();		
	}

	public void set_question_controll(questionPopControl questionPopControl) {
		question_Controll = questionPopControl;
//		question_Controll.getAnswerFour().setSelected(false);
	}
	
	public void execute_question() {
		// Simulate rolling the dice and store the result
//		this.board  = GameData.getInstance().getBoard();
        RadioButton selectedRadioButton = question_Controll.getAnswerFour();
        selectedRadioButton.setSelected(true);
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
