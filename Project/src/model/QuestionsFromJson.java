package model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionsFromJson {
    @JsonProperty("questions")
    private List<Question> question;

    // Default constructor
    public QuestionsFromJson() {
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
