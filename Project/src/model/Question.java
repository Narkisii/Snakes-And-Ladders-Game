package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import Intrefaces.GameEventObserver;
import Intrefaces.GameEventSubject;
import enums.GameEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a question in the game. Implements the GameEventSubject interface for the observer pattern.
 */
public class Question implements GameEventSubject {
    @JsonProperty("question")
    private String question;

    @JsonProperty("answers")
    private List<String> answers;

    @JsonProperty("correct_ans")
    private String correctAnswer;

    @JsonProperty("difficulty")
    private String difficulty;
    
    private List<GameEventObserver> observers = new ArrayList<>(); //observer list

    /**
     * Attaches an observer to the question.
     *
     * @param observer The observer to be attached.
     */
    @Override
    public void attach(GameEventObserver observer) {
        observers.add(observer);
    }

    /**
     * Detaches an observer from the question.
     *
     * @param observer The observer to be detached.
     */
    @Override
    public void detach(GameEventObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of a game event.
     *
     * @param event The game event.
     */
    @Override
    public void notifyObservers(GameEvent event) {
        for (GameEventObserver observer : observers) {
            observer.onEventTriggered(event);
        }
    }

    /**
     * Constructor for the question. Initializes the question, answers, and correct answer.
     */
    public Question() {
        this.question = "yesyyesyyes";
        this.answers = new ArrayList<String>();
        this.correctAnswer = "1";
        answers.add(correctAnswer);
        answers.add(correctAnswer);
        answers.add(correctAnswer);
        answers.add(correctAnswer);
    }

    /**
     * Gets the question text.
     *
     * @return The question text.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question text.
     *
     * @param question The question text.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the list of answers.
     *
     * @return The list of answers.
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Sets the list of answers.
     *
     * @param list The list of answers.
     */
    public void setAnswers(List<String> list) {
        this.answers = list;
    }

    /**
     * Gets the correct answer.
     *
     * @return The correct answer.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets the correct answer.
     *
     * @param correctAnswer The correct answer.
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the difficulty of the question.
     *
     * @return The difficulty of the question.
     */
    public int getDifficulty() {
        return Integer.parseInt(difficulty);
    }

    /**
     * Sets the difficulty of the question.
     *
     * @param difficulty The difficulty of the question.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = String.valueOf(difficulty);
    }

    /**
     * Overrides the hashCode method for the Question class.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(answers, correctAnswer, difficulty, question);
    }

    /**
     * Overrides the equals method for the Question class.
     *
     * @param obj The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
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
