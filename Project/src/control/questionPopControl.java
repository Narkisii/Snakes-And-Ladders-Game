package control;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Question;

import java.util.ArrayList;
import java.util.LinkedList;
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
    Question question = new Question();

    public void initialize() {
        // Initialization logic here
    	init_question(question);
    }

    public Question getQuestionFromFields() {
        Question question = new Question();
//        question.setQuestion(questionTextField.getText());
//        question.setCorrectAnswer(correctAnswerTextField.getText());
//
//        LinkedList<String> answers = new LinkedList<String>();
//        answers.add(optionOneTextField.getText());
//        answers.add(optionOneTextField2.getText());
//        answers.add(optionOneTextField21.getText());
//
//        question.setAnswers(answers);

        return question;
    }
    
    
    public void set_question(Question q) {
    	this.question = q;

    }
    private void init_question(Question question) {
        question.setQuestion(questionTextField.getText());
        question.setCorrectAnswer(correctAnswerTextField.getText());

        LinkedList<String> answers = new LinkedList<String>();
        answers.add(optionOneTextField.getText());
        answers.add(optionOneTextField2.getText());
        answers.add(optionOneTextField21.getText());

        question.setAnswers(answers);

    }
}
