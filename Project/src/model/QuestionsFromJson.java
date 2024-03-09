package model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Class for handling questions from a JSON file.
 */
public class QuestionsFromJson {
	private static QuestionsFromJson instance;
	private HashMap<Integer, List<Question>> questionMap;

	@JsonProperty("questions")
	private List<Question> questions;

	private String path;
	File file;

	/**
	 * Constructor that initializes the question map and file.
	 */
	private QuestionsFromJson() {
		super();
		file = returnFile() ;
		questionMap = new HashMap<Integer, List<Question>>();
	}

	/**
	 * Static method to get the singleton instance of QuestionsFromJson. If the instance is null, a new QuestionsFromJson is created.
	 *
	 * @return The singleton instance of QuestionsFromJson.
	 */
	public static QuestionsFromJson getInstance() {
		if (instance == null) {
			instance = new QuestionsFromJson();
		}
		return instance;
	}

	/**
	 * Gets the list of questions.
	 *
	 * @return The list of questions.
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * Initializes the question map with questions of different difficulties.
	 *
	 * @return The question map.
	 */
	public HashMap<Integer, List<Question>> init_QuestionMap(){
		questionMap.put(7, getQuestionsByDifficulty(1));
		questionMap.put(8, getQuestionsByDifficulty(2));
		questionMap.put(9, getQuestionsByDifficulty(3));

		return questionMap;
	}

	/**
	 * Sets the list of questions.
	 *
	 * @param questions The list of questions.
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * Reads questions from a JSON file.
	 *
	 * @return The QuestionsFromJson instance.
	 * @throws IOException If an I/O error occurs.
	 * @throws NoJsonFileFound If the JSON file is not found.
	 */
	public QuestionsFromJson readQuestionsFromJson() throws NoJsonFileFound  {
		ObjectMapper mapper = new ObjectMapper();
		try {
			path = "src/Json/Questions.txt";
			file = new File(path);
			QuestionsFromJson questions_class = mapper.readValue(file, QuestionsFromJson.class);
			return questions_class;

		} catch (Exception e) {
			try {
				path = "Json/Questions.txt";
				file = new File(path);
				QuestionsFromJson questions_class = mapper.readValue(file, QuestionsFromJson.class);
				return questions_class;
			} catch (Exception e1) {
				throw new NoJsonFileFound();
			}
		}
	}

	/**
	 * Returns the file from which the questions are read.
	 *
	 * @return The file.
	 */
	public File returnFile() {
//		ObjectMapper mapper = new ObjectMapper();
		file = null;
		try {
			path = "src/Json/Questions.txt";
			file = new File(path);
			return file;

		} catch (Exception e) {
			try {
				path = "Json/Questions.txt";
				file = new File(path);
				return file;
			} catch (Exception e1) {
			}
		}
		return file;
	}

	/**
	 * Removes a question from the list of questions.
	 *
	 * @param question The question to be removed.
	 */
	public void removeQuestion(Question question) {
		int index = -1;
		for (int i = 0; i < questions.size(); i++) {
			if (question.equals(questions.get(i))) {
				index = i;
			}
		}
		if (index != -1) {
			questions.remove(index);
		}
	}

	/**
	 * Adds a question to the list of questions.
	 *
	 * @param question The question to be added.
	 * @throws DuplicateError If the question is a duplicate.
	 */
	public void addQuestion(Question question) throws DuplicateError {
		for (Question q : questions) {
			if (q.equals(question)) {
				throw new DuplicateError();
			}
		}
		questions.add(question);
	}

	/**
	 * Gets the questions of a specific difficulty.
	 *
	 * @param difficulty The difficulty of the questions.
	 * @return The list of questions of the specified difficulty.
	 */
	public List<Question> getQuestionsByDifficulty(int difficulty) {
		return questions.stream().filter(q -> q.getDifficulty() == difficulty).map(q -> {
			Question newQuestion = new Question();
			newQuestion.setQuestion(q.getQuestion());
			newQuestion.setAnswers(q.getAnswers());
			newQuestion.setCorrectAnswer(q.getCorrectAnswer());
			newQuestion.setDifficulty(q.getDifficulty());
			return newQuestion;
		}).collect(Collectors.toList());
	}

	/**
	 * Edits a question in the list of questions.
	 *
	 * @param beforeChange The question to be edited.
	 * @param newQuestion The new question to replace the old one.
	 */
	public void editQuestion(Question beforeChange, Question newQuestion) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			List<Question> questions = objectMapper.readValue(file, new TypeReference<List<Question>>() {});
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.equals(beforeChange)) {
					questions.set(i, newQuestion);
					break;
				}
			}
			objectMapper.writeValue(file, questions);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the current instance of QuestionsFromJson to a JSON file.
	 *
	 * @throws IOException If an I/O error occurs.
	 */
	public void toJson() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing

		mapper.writeValue(file, this);
	}

}



