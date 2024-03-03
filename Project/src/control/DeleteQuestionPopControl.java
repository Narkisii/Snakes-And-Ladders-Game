package control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.NoJsonFileFound;
import model.Question;
import model.QuestionsFromJson;
/**
 * Controller class for the delete question confirmation pop-up window.
 * This class handles the user interaction and logic associated with confirming
 * the deletion of a question. It provides methods to set the question to be deleted,
 * initialize the controller, close the window, and set the previous window controller.
 */
public class DeleteQuestionPopControl {
	@FXML
	private Button yesButton;

	@FXML
	private Button noButton;

	private Question questionToDelete;

	private QuestionWizControl previousWindow;
    /**
     * Sets the question to be deleted.
     * 
     * @param question The question to be deleted
     */

	public void setQuestionToDelete(Question question) {
		this.questionToDelete = question;
	}

	@FXML
	public void initialize() {
		yesButton.setOnAction(event -> {
			QuestionsFromJson questionsFromJson;
			try {
				questionsFromJson = QuestionsFromJson.getInstance().readQuestionsFromJson();
				questionsFromJson.removeQuestion(questionToDelete);
				questionsFromJson.toJson();
				previousWindow.re_init(questionToDelete.getDifficulty());
				closeWindow();
			} catch (IOException | NoJsonFileFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		});

		noButton.setOnAction(event -> closeWindow());
	}
	
    /**
     * Closes the current window.
     */
	private void closeWindow() {
		Stage stage = (Stage) yesButton.getScene().getWindow();
		stage.close();
	}
	
    /**
     * Sets the previous window.
     * 
     * @param questionWizControl2 The previous window controller
     */

	public void setPreviousWindow(QuestionWizControl questionWizControl2) {
		// TODO Auto-generated method stub

		this.previousWindow = questionWizControl2;

	}
}
