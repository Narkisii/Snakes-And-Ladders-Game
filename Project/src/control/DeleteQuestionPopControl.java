package control;

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

    public void setQuestionToDelete(Question question) {
        this.questionToDelete = question;
    }

    @FXML
    public void initialize() {
        yesButton.setOnAction(event -> {
//            QuestionsFromJson questionsFromJson = QuestionsFromJson.getInstance();
//            questionsFromJson.removeQuestion(questionToDelete);
//            QuestionsFromJson.writeQuestionsToJson();
//            closeWindow();
        });

        noButton.setOnAction(event -> closeWindow());
    }

    private void closeWindow() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }
}
