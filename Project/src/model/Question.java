package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Question {
//	private static int nextId = 1; // Static variable to keep track of the next available ID
//    private int id; // question ID

    @JsonProperty("question")
    private String question;


	@JsonProperty("answers")
    private List<String> answers;

    @JsonProperty("correct_ans")
    private String correctAnswer;

    @JsonProperty("difficulty")
    private String difficulty;

    // Constructor
    public Question() {
//    	this.id = nextId++;
    	this.question = "yesyyesyyes";
    	this.answers = new ArrayList<String>();
    	this.correctAnswer = "1";
    	answers.add(correctAnswer);
    	answers.add(correctAnswer);
    	answers.add(correctAnswer);
    	answers.add(correctAnswer);
    }
    

//    /**
//	 * @return the id
//	 */
//	public int getId() {
//		return id;
//	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
//
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(int id) {
//		this.id = id;
//	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

    // Getters and setters for the new fields
    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> list) {
        this.answers = list;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getDifficulty() {
        return Integer.parseInt(difficulty);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = String.valueOf(difficulty);
    }


	@Override
	public int hashCode() {
		return Objects.hash(answers, correctAnswer, difficulty, question);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Objects.equals(answers, other.answers) && Objects.equals(correctAnswer, other.correctAnswer)
				&& Objects.equals(difficulty, other.difficulty) && Objects.equals(question, other.question);
	}
}
