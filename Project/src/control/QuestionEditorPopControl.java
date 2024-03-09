package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import exceptions.HandleExceptions;
import exceptions.InputIsEmpty;
import exceptions.InputIsNotUnique;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DuplicateError;
import exceptions.NoJsonFileFound;
import model.Question;
import model.QuestionsFromJson;

public class QuestionEditorPopControl {
	@FXML
	private TextArea ans1, ans2, ans3, ans4;

	@FXML
	private TextArea question_TextArea;

	@FXML
	private Label main_Label, answerOpt_lbl, correctAnswer_lbl;

	@FXML
	private ComboBox<String> difficulty_ComBox, correctAns_ComBox;

	@FXML
	private HBox ans_1_HBox, ans_2_HBox, ans_3_HBox, ans_4_HBox;

	@FXML
	private VBox q_Vbox;

	@FXML
	private Button saveButton;

	private QuestionWizControl previousWindow;

	@FXML
	private ImageView clearButton;

	@FXML
	private ScrollPane qScroll_Pane;

	@FXML
	private AnchorPane center_Pane;
	
	private Stage savePopUp;

	List<TextArea> textAreaList;
	private Question question;
	private Question questionAfterChange;

	private String type;

	@FXML
	public void initialize() {

		textAreaList = new ArrayList<>();

		textAreaList.add(ans1);
		textAreaList.add(ans2);
		textAreaList.add(ans3);
		textAreaList.add(ans4);

		clearButton.setOnMouseClicked(event -> {
			clear_text();
		});

		// Populate the correctAns_ComBox with values from 1 to 4
		correctAns_ComBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));

		// Populate the difficulty_ComBox with values from 1 to 3
		difficulty_ComBox.setItems(FXCollections.observableArrayList("1", "2", "3"));

		saveButton.setOnAction(event -> {
			save_Question();
			//showQSavePop();
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
				alert.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL

				try {
					alert.initOwner((clearButton).getScene().getWindow()); // Initialize the owner of the pop-up stage
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return false;
				}


				questionsFromJson = QuestionsFromJson.getInstance().readQuestionsFromJson();

				// Create a new Question object
				if (checkEmpty()) {
					Question newQuestion = new Question();
					newQuestion.setQuestion(question_TextArea.getText());
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
					
					// showQSavePop();
					
					return true;
				}
			} catch (InputIsEmpty | DuplicateError | IOException | InputIsNotUnique | NoJsonFileFound e) {
				// TODO Auto-generated catch block
				HandleExceptions.showException(e, this, main_Label.getScene().getWindow());
			}

		} else

		{
			///Snakes_And_Ladders/src/view/QuestionSavedPop.fxml
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Save Question");
			alert.setHeaderText(null);
			alert.setContentText("Saved Edit");
			alert.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
			
			try {
				alert.initOwner((clearButton).getScene().getWindow()); // Initialize the owner of the pop-up stage
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}


			try {
				/// edit question
				QuestionsFromJson questionsFromJson = QuestionsFromJson.getInstance().readQuestionsFromJson();
				if (checkEmpty()) {

					// Get the updated question text
					String updatedQuestionText = question_TextArea.getText();

					// Get the updated answers
					LinkedList<String> updatedAnswers = new LinkedList<>();
					//					updatedAnswers.add(question_TextArea.getText());
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
					
					// showQSavePop();


					// Close the window after saving
					Stage stage = (Stage) saveButton.getScene().getWindow();
					stage.close();
					return true;

				}
			} catch (DuplicateError | IOException | InputIsEmpty | InputIsNotUnique | NoJsonFileFound e) {
				// TODO: handle exception
				HandleExceptions.showException(e, this, main_Label.getScene().getWindow());

			}
		}
		return false;

	}

	// Clear all text fields
	public void clear_text() {
		question_TextArea.clear();
		for (TextArea f : textAreaList) {
			f.clear();
		}
	}

	//	// Check if any input is empty
	//	public boolean checkEmpty() throws InputIsEmpty, InputIsNotUnique {
	//		Set<String> inputs = new HashSet<>();
	//		for (TextField f : textFieldList) {
	//			String input = f.getText().toLowerCase();
	//			if (input.isEmpty()) {
	//				throw new InputIsEmpty(f.getId());
	//			} else if (!inputs.add(input.toLowerCase())) {
	//				throw new InputIsNotUnique(f.getId() + " " + f.getText());
	//			}
	//		}
	//		return true;
	//	}
	public boolean checkEmpty() throws InputIsEmpty, InputIsNotUnique {
		Set<String> inputs = new HashSet<>();
		String temp = question_TextArea.getText().replaceAll("\\p{Punct}", "").replaceAll("\\s", "").replaceAll("\\d", "");
		
		if (temp.isEmpty()) {
			throw new InputIsEmpty(question_TextArea.getId());
		}

		for (TextArea f : textAreaList) {
			String input = f.getText().toLowerCase();
			input = input.replaceAll("\\p{Punct}", "").replaceAll("\\s", "");
			if (input.isEmpty()) {
				throw new InputIsEmpty(f.getId());
			} else if (!inputs.add(input)) {
				throw new InputIsNotUnique(f.getId() + " " + f.getText());
			}
		}
		return true;
	}

	public void setPreviousWindow(QuestionWizControl questionWizControl2) {
		// TODO Auto-generated method stub
		previousWindow = questionWizControl2;

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
		questionAfterChange = new Question();

		// import question
		String theQ = question.getQuestion();
		question_TextArea.setText(theQ);

		// import difficulty
		difficulty_ComBox.setValue(String.valueOf(question.getDifficulty()));

		if (previousWindow.isAdmin) {
			// Get answers and show on screen
			List<String> answers = question.getAnswers();

			// Populate the TextAreas with the answers
			ans1.setText(answers.get(0));
			ans2.setText(answers.get(1));
			ans3.setText(answers.get(2));
			ans4.setText(answers.get(3));
			correctAns_ComBox.setValue(String.valueOf(question.getCorrectAnswer()));
		} else {
			question_TextArea.setEditable(false);
			question_TextArea.setDisable(true);

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
	
	public void showQSavePop() {
		if (savePopUp != null && savePopUp.isShowing()) {
			return;
		} else { // Create a new Stage for the pop-up
			savePopUp = new Stage();
			savePopUp.setResizable(false);
			savePopUp.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
		}

		// Load the FXML file for the pop-up
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionSavedPop.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// import login controller and set this as previous window
		QuestionSavedControl controller = loader.getController();
		controller.setPreviousWindow(this);

		// Set the scene and show the stage
		Scene scene = new Scene(root);
		savePopUp.setScene(scene);
		savePopUp.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
		//			popupStage.setAlwaysOnTop(true); // Set always on top
		savePopUp.setResizable(false);
		savePopUp.show();
	}

	public String getType() {
		return type;
	}
}
