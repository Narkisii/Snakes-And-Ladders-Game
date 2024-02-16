package model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionsFromJson {
    @JsonProperty("questions")
    private List<Question> question;

    // Default constructor
    public QuestionsFromJson() {
    }
    
    // The Idea is to sort the Questions by dif and then change the id of the original Question
    // to a counter start from 1
    public List<Question> getQuestionsByDifficulty(int difficulty) {
        AtomicInteger counter = new AtomicInteger(1);
        return this.question.stream()
            .filter(q -> q.getDifficulty() == difficulty)
            .map(q -> {
                Question newQuestion = new Question();
                newQuestion.setId(counter.getAndIncrement());
                newQuestion.setQuestion(q.getQuestion());
                newQuestion.setAnswers(q.getAnswers());
                newQuestion.setCorrectAnswer(q.getCorrectAnswer());
                newQuestion.setDifficulty(q.getDifficulty());
                return newQuestion;
            })
            .collect(Collectors.toList());
    }

    // Getters
    public List<Question> getQuestions() {
        return question;
    }

    // Setters
    public void setQuestions(List<Question> questions) {
        this.question = questions;
    }
}
