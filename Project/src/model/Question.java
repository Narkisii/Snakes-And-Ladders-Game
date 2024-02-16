package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class Question {
	private static int nextId = 1; // Static variable to keep track of the next available ID
  //  private int id; // question ID

    @JsonProperty("question")
    private String question;


	@JsonProperty("answers")
    private LinkedList<String> answers;

    @JsonProperty("correct_ans")
    private String correctAnswer;

    @JsonProperty("difficulty")
    private int difficulty;

    // Constructor
    public Question() {
    	//this.id = nextId++;
    	this.question = "yesyyesyyes";
    	this.answers = new LinkedList<String>();
    	this.correctAnswer = "correctAnswercorrectAnswercorrectAnswer";
    	answers.add(correctAnswer);
    	answers.add(correctAnswer);
    	answers.add(correctAnswer);
    	answers.add(correctAnswer);
    }
    


	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

    // Getters and setters for the new fields
    public LinkedList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(LinkedList<String> answers) {
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
