package control;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Question;
import model.QuestionsFromJson;

public class EditQuestionPopControl {
    @FXML
    private Button saveButton;

    @FXML
    private ImageView clearButton;

    @FXML
    private TextField ans4, ans3, ans2, ans1, questionTextField;

    @FXML
    private ComboBox<String> correctAns_ComBox, difficulty_ComBox;

    private Question question;
    private Question questionAfterChange;

    public void initialize() {
        // Populate the correctAns_ComBox with values from 1 to 4
        correctAns_ComBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
        // Set the default value to "1"
        correctAns_ComBox.setValue("1");

        // Populate the difficulty_ComBox with values from 1 to 3
        difficulty_ComBox.setItems(FXCollections.observableArrayList("1", "2", "3"));
        // Set the default value to "1"
        difficulty_ComBox.setValue("1");
    }

    public void setQuestion(Question question) {
        this.question = question;
        this.questionAfterChange = new Question();

        String theQ = question.getQuestion();

        // Get the answers from the question
        List<String> answers = question.getAnswers();

        // Populate the TextFields with the answers
        ans1.setText(answers.get(0));
        ans2.setText(answers.get(1));
        ans3.setText(answers.get(2));
        ans4.setText(answers.get(3));
        questionTextField.setText(theQ);
    }

    @FXML
    public void saveQuestion() {
    	QuestionsFromJson questionsFromJson = QuestionsFromJson.getInstance();
    	
        // Get the updated question text
        String updatedQuestionText = questionTextField.getText();

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

        // Update the question list
       // questionsFromJson.removeQuestion(question);
        questionsFromJson.addQuestion(questionAfterChange);
        questionsFromJson.writeQuestionsToJson();
        // Close the window after saving
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }


}
