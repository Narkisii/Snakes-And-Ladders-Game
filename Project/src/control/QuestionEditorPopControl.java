package control;

import java.awt.ScrollPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import exceptions.HandleExceptions;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.DuplicateError;
import model.InputIsEmpty;
import model.InputIsNotUnique;
import model.NoJsonFileFound;
import model.Question;
import model.QuestionsFromJson;

public class QuestionEditorPopControl {
	@FXML
	private TextField question_field, ans1, ans2, ans3, ans4;

	@FXML
	private Label main_Label, answerOpt_lbl, correctAnswer_lbl;
	
	@FXML
	private ComboBox<String> difficulty_ComBox, correctAns_ComBox;
	
	@FXML
	private HBox ans_1_HBox, ans_2_HBox, ans_3_HBox, ans_4_HBox;

	@FXML
	private Button saveButton;

	private QuestionWizControl previousWindow;

	@FXML
	private ImageView clearButton;
	
	private ScrollPane qScroll_Pane;

	List<TextField> textFieldList;
	private Question question;
	private Question questionAfterChange;

	private String type;

	@FXML
	public void initialize() {

		textFieldList = new ArrayList<>();
		textFieldList.add(question_field);

		textFieldList.add(ans1);
		textFieldList.add(ans2);
		textFieldList.add(ans3);
		textFieldList.add(ans4);

		clearButton.setOnMouseClicked(event -> {
			clear_text();
		});

		// Populate the correctAns_ComBox with values from 1 to 4
		correctAns_ComBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));

		// Populate the difficulty_ComBox with values from 1 to 3
		difficulty_ComBox.setItems(FXCollections.observableArrayList("1", "2", "3"));

		saveButton.setOnAction(event -> {
			save_Question();
		});
	}

	private boolean save_Question() {
		if (type == "add") {
//      QuestionsFromJson questionsFromJson = QuestionsFromJson.getInstance();
			QuestionsFromJson questionsFromJson = null;
			try {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Save Question");
				alert.setHeaderText(null);
				alert.setContentText("Saved Question");

				questionsFromJson = QuestionsFromJson.getInstance().readQuestionsFromJson();

				// Create a new Question object
				if (checkEmpty()) {
					Question newQuestion = new Question();
					newQuestion.setQuestion(question_field.getText());
					newQuestion.setAnswers(new LinkedList<>(
							Arrays.asList(ans1.getText(), ans2.getText(), ans3.getText(), ans4.getText())));
					newQuestion.setCorrectAnswer(correctAns_ComBox.getValue());
					newQuestion.setDifficulty(Integer.parseInt(difficulty_ComBox.getValue()));
					// Add the new question to the list
					questionsFromJson.addQuestion(newQuestion);
					questionsFromJson.toJson();
					previousWindow.re_init(newQuestion.getDifficulty());
					clear_text();
					alert.showAndWait();

					return true;
				}
			} catch (InputIsEmpty | DuplicateError | IOException | InputIsNotUnique | NoJsonFileFound e) {
				// TODO Auto-generated catch block
				HandleExceptions.showException(e);
			}

		} else

		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Save Question");
			alert.setHeaderText(null);
			alert.setContentText("Saved Edit");
			try {
				/// edit question
				QuestionsFromJson questionsFromJson = QuestionsFromJson.getInstance().readQuestionsFromJson();
				if (checkEmpty()) {

					// Get the updated question text
					String updatedQuestionText = question_field.getText();

					// Get the updated answers
					LinkedList<String> updatedAnswers = new LinkedList<>();
					updatedAnswers.add(ans1.getText());
					updatedAnswers.add(ans2.getText());
					updatedAnswers.add(ans3.getText());
					updatedAnswers.add(ans4.getText());

					// Get the selected correct answer
					String selectedCorrectAnswer = correctAns_ComBox.getValue();

					// Get the selected difficulty
					String selectedDifficulty = difficulty_ComBox.getValue();

					// Update the questionAfterChange object with the updated values
					questionAfterChange.setQuestion(updatedQuestionText);
					questionAfterChange.setAnswers(updatedAnswers);
					questionAfterChange.setCorrectAnswer(selectedCorrectAnswer);
					questionAfterChange.setDifficulty(Integer.parseInt(selectedDifficulty));

//					check_duplicates(questionAfterChange);
					// Update the question list
					questionsFromJson.removeQuestion(question);
					questionsFromJson.addQuestion(questionAfterChange);
					questionsFromJson.toJson();
					previousWindow.re_init(questionAfterChange.getDifficulty());
					alert.showAndWait();

					// Close the window after saving
					Stage stage = (Stage) saveButton.getScene().getWindow();
					stage.close();
					return true;

				}
			} catch (DuplicateError | IOException | InputIsEmpty | InputIsNotUnique | NoJsonFileFound e) {
				// TODO: handle exception
				HandleExceptions.showException(e);

			}
		}
		return false;

	}


	// Clear all text fields
	public void clear_text() {
		for (TextField f : textFieldList) {
			f.clear();
		}
	}

	// Check if any imput is empty
	public boolean checkEmpty() throws InputIsEmpty, InputIsNotUnique {
		Set<String> inputs = new HashSet<>();
		for (TextField f : textFieldList) {
			String input = f.getText();
			if (input.isEmpty()) {
				throw new InputIsEmpty(f.getId());
			} else if (!inputs.add(input.toLowerCase())) {
				throw new InputIsNotUnique(f.getId() + " " + f.getText());
			}
		}
		return true;
	}

	public void setPreviousWindow(QuestionWizControl questionWizControl2) {
		// TODO Auto-generated method stub
		this.previousWindow = questionWizControl2;

	}

	public void setType(String type) {
		// TODO Auto-generated method stub
		if (type == "add") {
			main_Label.setText("Add Question");
			clearButton.setVisible(true);
			// Set the default value to "1"
			correctAns_ComBox.setValue("1");
			// Set the default value to "1"
			difficulty_ComBox.setValue("1");
		}
		if (type == "edit") {
			main_Label.setText("Edit Question");
			clearButton.setVisible(false);
//			init_edit();
		}
		this.type = type;
	}

	public void init_edit(Question question) {
		// TODO Auto-generated method stub
		this.question = question;
		this.questionAfterChange = new Question();
		
		// import question
		String theQ = question.getQuestion();
		question_field.setText(theQ);

		// import difficulty
		difficulty_ComBox.setValue(String.valueOf(question.getDifficulty()));

		if(previousWindow.isAdmin()) {
			// Get answers and show on screen
			List<String> answers = question.getAnswers();

			// Populate the TextFields with the answers
			ans1.setText(answers.get(0));
			ans2.setText(answers.get(1));
			ans3.setText(answers.get(2));
			ans4.setText(answers.get(3));
			correctAns_ComBox.setValue(String.valueOf(question.getCorrectAnswer()));
		}
		else {
			question_field.setEditable(false);
			question_field.setDisable(true);
			
			// hide correct answer
			correctAns_ComBox.setVisible(false);
			correctAnswer_lbl.setVisible(false);
			
			// disable the difficulty choose
			difficulty_ComBox.setDisable(true);
			
			// hide answers
			answerOpt_lbl.setVisible(false);
			ans_1_HBox.setVisible(false);
			ans_2_HBox.setVisible(false);
			ans_3_HBox.setVisible(false);
			ans_4_HBox.setVisible(false);
			
			saveButton.setDisable(true);
		}
	}
}
