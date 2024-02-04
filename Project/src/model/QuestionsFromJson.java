package model;

import java.util.HashMap;

public class QuestionsFromJson {
	private HashMap<Integer, Question> questionsList;
	
	public QuestionsFromJson(String path) {
		super();
		this.questionsList = new HashMap<Integer, Question>();
		// implement json import method
	}
	public HashMap<Integer, Question> getQuestionsFromJson() {
		return questionsList;
	}

	public HashMap<Integer, Question> getQuestions() {
		return questionsList;
	}	
}
