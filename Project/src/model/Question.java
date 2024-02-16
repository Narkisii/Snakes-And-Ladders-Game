package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Question {
	private static int nextId = 1; // Static variable to keep track of the next available ID
    private int id; // question ID

    @JsonProperty("question")
    private String question;

    /**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	@JsonProperty("answers")
    private List<String> answers;

    @JsonProperty("correct_ans")
    private String correctAnswer;

    @JsonProperty("difficulty")
    private int difficulty;

    // Constructor
    public Question() {
    	this.id = nextId++;
    }

    // Getters and setters for the new fields
    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
