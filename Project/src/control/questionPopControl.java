package control;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;
import model.Question;
import model.cpu_Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class questionPopControl {
	@FXML
	private Label QuestionTitle;

	@FXML
	private RadioButton answerFour;

	@FXML
	private RadioButton answerOne;

	@FXML
	private TextFlow answerTextBox;

	@FXML
	private RadioButton answerThree;

	@FXML
	private RadioButton answerTwo;

	@FXML
	private Button checkAnswerButton;

	@FXML
	private Label chooseAnswerTitle;

	@FXML
	private Label check_Answer_label;
	@FXML
	private Pane leftPane;

	@FXML
	private Label questionTextBox;

	private Question question;

	private ToggleGroup toggleGroup;
	private String corr_answer_str;

	private Integer corr_answer;

	private BoardControl prev_control;

	private Player player;

	public void initialize() {
		// Initialization logic here
		toggleGroup = new ToggleGroup();
		answerOne.setToggleGroup(toggleGroup);
		answerTwo.setToggleGroup(toggleGroup);
		answerThree.setToggleGroup(toggleGroup);
		answerFour.setToggleGroup(toggleGroup);
		checkAnswerButton.setDisable(true);
		// Set listener to the radio buttons, to allow to check answer only if something
		// is chosen
		toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// Enable the checkAnswerButton
				checkAnswerButton.setDisable(false);
			}
		});

		// Set an action listener for the checkAnswerButton
		checkAnswerButton.setOnAction(event -> {
			RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
			if (selectedRadioButton != null) {
				checkAnswerButton.setDisable(false);
				String selectedAnswer = selectedRadioButton.getText();
				// Save the selected answer (you can use it as needed)
				System.out.println("Selected answer: " + selectedAnswer);
				check_Answer(selectedAnswer);
			} else {
				System.out.println("No answer selected.");
			}
		});

	}

	private void check_Answer(String selectedAnswer) {
		int steps = 0;
		if (question.getDifficulty() == 1) {
			steps = -1;
		}
		if (question.getDifficulty() == 2) {
			steps = -2;
		}
		if (question.getDifficulty() == 3) {
			steps = -3;
		}
		if (selectedAnswer.equals(corr_answer_str)) {
			check_Answer_label.setText("Correct!");
			check_Answer_label.setStyle("-fx-background-color: green;"); // Set background to green
			if (question.getDifficulty() == 3) {
				prev_control.move_Player(1, player);
			}
		} else {
			check_Answer_label.setText("you fucked up!");
			check_Answer_label.setStyle("-fx-background-color: red;"); // Set background to green
			prev_control.move_Player(steps, player);

		}
		answerOne.setDisable(true);
		answerTwo.setDisable(true);
		answerThree.setDisable(true);
		answerFour.setDisable(true);
		checkAnswerButton.setDisable(true);

		PauseTransition delay = new PauseTransition(Duration.seconds(2)); // 2 seconds delay
		delay.setOnFinished(event -> {
			Stage stage = (Stage) checkAnswerButton.getScene().getWindow();
			stage.close();
		});
		delay.play();

	}

	public void set_question(Question q) {
		this.question = q;
		System.out.println(question.getQuestion());
		String theQ = question.getQuestion();
		corr_answer = Integer.valueOf(question.getCorrectAnswer());
		// Get the answers from the question
		List<String> answers = question.getAnswers();
		corr_answer_str = answers.get(corr_answer - 1);
		System.out.println(answers);

		System.out.println("corr_answer " + corr_answer + " corr_answer_str " + corr_answer_str);
		if (q.getDifficulty() == 1) {
			QuestionTitle.setText("Easy Question");
		}
		if (q.getDifficulty() == 2) {
			QuestionTitle.setText("Medium Question");
		}
		if (q.getDifficulty() == 3) {
			QuestionTitle.setText("Hard Question");
		}

		// Populate the TextFields with the answers
		answerOne.setText(answers.get(0));
		answerTwo.setText(answers.get(1));
		answerThree.setText(answers.get(2));
		answerFour.setText(answers.get(3));
		questionTextBox.setText(theQ);
	}

	public void prev_window(BoardControl boardControl) {
		this.prev_control = boardControl;
	}

	public void set_player(Player p) {
		// TODO Auto-generated method stub
		this.player = p;
		if (player.getClass().getName() == "model.cpu_Player") {
			cpu_Player cpu_Player = ((cpu_Player) player);
			cpu_Player.set_question_controll(this);
			cpu_Player.execute_question();
		}
	}


	public void selectAnswer(int i) {
		if (i == 1)
			answerOne.setSelected(true);
		if (i == 2)
			answerTwo.setSelected(true);
		if (i == 3)
			answerThree.setSelected(true);
		if (i == 4)
			answerFour.setSelected(true);
	}

	/**
	 * @return the answerFour
	 */
	public RadioButton getAnswerFour() {
		return answerFour;
	}

	/**
	 * @param answerFour the answerFour to set
	 */
	public void setAnswerFour(RadioButton answerFour) {
		this.answerFour = answerFour;
	}

}

//public Question getQuestionFromFields() {
//Question question = new Question();
//question.setQuestion(questionTextField.getText());
//question.setCorrectAnswer(correctAnswerTextField.getText());
//
//LinkedList<String> answers = new LinkedList<String>();
//answers.add(optionOneTextField.getText());
//answers.add(optionOneTextField2.getText());
//answers.add(optionOneTextField21.getText());
//
//question.setAnswers(answers);
//
//return question;
//}