package control;

import java.util.Arrays;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Question;
import model.QuestionsFromJson;

public class AddQuestionPopControl {
    @FXML
    private TextField question, ans1, ans2, ans3, ans4;

    @FXML
    private ComboBox<String> difficulty_ComBox, correctAns_ComBox;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
        // Populate the correctAns_ComBox with values from 1 to 4
        correctAns_ComBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
        // Set the default value to "1"
        correctAns_ComBox.setValue("1");

        // Populate the difficulty_ComBox with values from 1 to 3
        difficulty_ComBox.setItems(FXCollections.observableArrayList("1", "2", "3"));
        // Set the default value to "1"
        difficulty_ComBox.setValue("1");

        saveButton.setOnAction(event -> {
            QuestionsFromJson questionsFromJson = QuestionsFromJson.getInstance();
            
            // Create a new Question object
            Question newQuestion = new Question();
            newQuestion.setQuestion(question.getText());
            newQuestion.setAnswers(new LinkedList<>(Arrays.asList(ans1.getText(), ans2.getText(), ans3.getText(), ans4.getText())));
            newQuestion.setCorrectAnswer(correctAns_ComBox.getValue());
            newQuestion.setDifficulty(Integer.parseInt(difficulty_ComBox.getValue()));

            // Add the new question to the list
            questionsFromJson.addQuestion(newQuestion);
            QuestionsFromJson.writeQuestionsToJson();
        });
    }
}
