package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class QuestionsFromJson {
	private static QuestionsFromJson instance;
	private HashMap<Integer, List<Question>> questionMap;
	/**
	 * 
	 */

	@JsonProperty("questions")
	private List<Question> questions;
//	private String path = "src/Json/Questions.txt";

	private String path;
	File file;

	public QuestionsFromJson() {
		super();
		this.file = returnFile();
		questionMap = new HashMap<Integer, List<Question>>();

	}

	public static QuestionsFromJson getInstance() {
		if (instance == null) {
			instance = new QuestionsFromJson();
		}
		return instance;
	}

//	// Private constructor
//	private get_QuestionsFromJson(List<Question> questions) {
//		this.questions = questions;
//	}
//
//	private set_QuestionsFromJson() {
//		this.questions = new ArrayList<>();
//	}

	/**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	
	public HashMap<Integer, List<Question>> init_QuestionMap(){
		questionMap.put(7, getQuestionsByDifficulty(1));
		questionMap.put(8, getQuestionsByDifficulty(2));
		questionMap.put(9, getQuestionsByDifficulty(3));

		return questionMap;
		
	}
	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public QuestionsFromJson readQuestionsFromJson() throws IOException, NoJsonFileFound {
		ObjectMapper mapper = new ObjectMapper();

		try {
			path = "src/Json/Questions.txt";
			this.file = new File(path);
			QuestionsFromJson questions_class = mapper.readValue(file, QuestionsFromJson.class);
			return questions_class;

		} catch (Exception e) {
			// TODO: handle exception
			try {
				this.path = "Json/Questions.txt";
				this.file = new File(path);
				QuestionsFromJson questions_class = mapper.readValue(file, QuestionsFromJson.class);
				return questions_class;
			} catch (Exception e1) {
				// TODO: handle exception
				throw new NoJsonFileFound();
			}
		}
	}

	public File returnFile() {
		ObjectMapper mapper = new ObjectMapper();
		file = null;
		try {
			path = "src/Json/Questions.txt";
			this.file = new File(path);
			QuestionsFromJson questions_class = mapper.readValue(file, QuestionsFromJson.class);
			return file;

		} catch (Exception e) {
			// TODO: handle exception
			try {
				this.path = "Json/Questions.txt";
				this.file = new File(path);
				QuestionsFromJson questions_class = mapper.readValue(file, QuestionsFromJson.class);
				return file;
			} catch (Exception e1) {
				// TODO: handle exception
			}
		}
		return file;

		// List<Question> questions = new
//		QuestionsFromJson questions_class = mapper.readValue(new File(path), QuestionsFromJson.class);
//		QuestionsFromJson questions_class = mapper.readValue(file, QuestionsFromJson.class);

//        questions_class.questions = questions_class.getQuestions();
//		return questions_class;
	}

	public void removeQuestion(Question question) {
		System.out.println("questions.size() " + questions.size());
		int index = -1;
		for (int i = 0; i < questions.size(); i++) {
			if (question.equals(questions.get(i))) {
				System.out.println(i);
				index = i;
			}
		}
		if (index != -1) {
			this.questions.remove(index);
		} else {
			return;
		}
	}

	public void addQuestion(Question question) throws DuplicateError {
		for (Question q : this.questions) {
			if (q.equals(question)) {
				throw new DuplicateError();
			}
		}
		questions.add(question);

	}

	// The Idea is to sort the Questions by dif and then change the id of the
	// original Question
	// to a counter start from 1
	public List<Question> getQuestionsByDifficulty(int difficulty) {
//		AtomicInteger counter = new AtomicInteger(1);
		return this.questions.stream().filter(q -> q.getDifficulty() == difficulty).map(q -> {
			Question newQuestion = new Question();
			// newQuestion.setId(counter.getAndIncrement());
			newQuestion.setQuestion(q.getQuestion());
			newQuestion.setAnswers(q.getAnswers());
			newQuestion.setCorrectAnswer(q.getCorrectAnswer());
			newQuestion.setDifficulty(q.getDifficulty());
			return newQuestion;
		}).collect(Collectors.toList());
	}

	public void editQuestion(Question beforeChange, Question newQuestion) {
		try {
			// Read existing questions from JSON file
			ObjectMapper objectMapper = new ObjectMapper();
//			List<Question> questions = objectMapper.readValue(new File(path),
//					new TypeReference<List<Question>>() {
//					});
			List<Question> questions = objectMapper.readValue(file, new TypeReference<List<Question>>() {
			});
			// Find the question to be edited and replace it with the new question
			for (int i = 0; i < questions.size(); i++) {
				Question q = questions.get(i);
				if (q.equals(beforeChange)) {
					questions.set(i, newQuestion);
					break;
				}
			}

			// Write the updated questions back to the JSON file
//			objectMapper.writeValue(new File(path), questions);
			objectMapper.writeValue(file, questions);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//    public static void writeQuestionsToJson() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing
//        try {
//            objectMapper.writeValue(new File(path), getInstance());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

	public void toJson() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing

		mapper.writeValue(file, this);

//		mapper.writeValue(new File(path), this);
	}

}
