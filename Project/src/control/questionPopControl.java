package control;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Question;

import java.util.ArrayList;
import java.util.List;

public class questionPopControl {
    @FXML
    private TextField questionTextField;

    @FXML
    private TextField correctAnswerTextField;

    @FXML
    private TextField optionOneTextField;

    @FXML
    private TextField optionOneTextField2;

    @FXML
    private TextField optionOneTextField21;

    public void initialize() {
        // Initialization logic here
    }

    public Question getQuestionFromFields() {
        Question question = new Question();
        question.setQuestion(questionTextField.getText());
        question.setCorrectAnswer(correctAnswerTextField.getText());

        List<String> answers = new ArrayList<>();
        answers.add(optionOneTextField.getText());
        answers.add(optionOneTextField2.getText());
        answers.add(optionOneTextField21.getText());

        question.setAnswers(answers);

        return question;
    }
}
