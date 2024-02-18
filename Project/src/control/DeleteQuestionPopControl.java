package control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Question;
import model.QuestionsFromJson;

public class DeleteQuestionPopControl {
	@FXML
	private Button yesButton;

	@FXML
	private Button noButton;

	private Question questionToDelete;

	private QuestionWizControl previousWindow;

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

	private void closeWindow() {
		Stage stage = (Stage) yesButton.getScene().getWindow();
		stage.close();
	}
	
	public void setPreviousWindow(QuestionWizControl questionWizControl2) {
		// TODO Auto-generated method stub

		this.previousWindow = questionWizControl2;

	}
}
