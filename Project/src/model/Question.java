package model;

/**
 * This class represents a question with an ID and four answer options.
 * 
 * @author ItayOlivcovitz
 */
//	[1] -> [1.2]-> [1.3]-> [1.3]-> [1.4]-> [1.5]
//	[2]	-> [2.2]-> [2.3]-> [2.3]-> [2.4]-> [2.5]
//	[3]	-> [3.2]-> [3.3]-> [3.3]-> [3.4]-> [3.5]	
			
public class Question {
    private static int nextId = 0; // Static variable to keep track of the next available ID
    private int id; // question ID
    private String ans1; // answer 1
    private String ans2; // answer 2
    private String ans3; // answer 3
    private String ans4; // answer 4
    
    // Additional parameters
    private String questionText;
    private int correctAnswer; // Change the type to int
    private int difficulty; // Change the type to int

    /**
     * Constructor for the Question class.
     * 
     * @param ans1           The first answer option.
     * @param ans2           The second answer option.
     * @param ans3           The third answer option.
     * @param ans4           The fourth answer option.
     * @param questionText   The text of the question.
     * @param correctAnswer  The correct answer to the question.
     */
    public Question(String ans1, String ans2, String ans3, String ans4, String questionText, int correctAnswer) {
        this.id = nextId++;
        this.ans1 = ans1; 
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Getter for the question ID.
     * 
     * @return The question ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the question ID.
     * 
     * @param id The new question ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the first answer option.
     * 
     * @return The first answer option.
     */
    public String getAns1() {
        return ans1;
    }

    /**
     * Setter for the first answer option.
     * 
     * @param ans1 The new value for the first answer option.
     */
    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    /**
     * Getter for the second answer option.
     * 
     * @return The second answer option.
     */
    public String getAns2() {
        return ans2;
    }

    /**
     * Setter for the second answer option.
     * 
     * @param ans2 The new value for the second answer option.
     */
    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    /**
     * Getter for the third answer option.
     * 
     * @return The third answer option.
     */
    public String getAns3() {
        return ans3;
    }

    /**
     * Setter for the third answer option.
     * 
     * @param ans3 The new value for the third answer option.
     */
    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    /**
     * Getter for the fourth answer option.
     * 
     * @return The fourth answer option.
     */
    public String getAns4() {
        return ans4;
    }

    /**
     * Setter for the fourth answer option.
     * 
     * @param ans4 The new value for the fourth answer option.
     */
    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    /**
     * Getter for the text of the question.
     * 
     * @return The text of the question.
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Setter for the text of the question.
     * 
     * @param questionText The new text of the question.
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Getter for the correct answer to the question.
     * 
     * @return The correct answer to the question.
     */
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Setter for the correct answer to the question.
     * 
     * @param correctAnswer The new correct answer to the question.
     */
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Returns a string representation of the Question object.
     * 
     * @return String representation of the Question object.
     */
    @Override
    public String toString() {
        return "Question{" +
                //"id=" + id +
                ", ans1='" + ans1 + '\'' +
                ", ans2='" + ans2 + '\'' +
                ", ans3='" + ans3 + '\'' +
                ", ans4='" + ans4 + '\'' +
                ", questionText='" + questionText + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
