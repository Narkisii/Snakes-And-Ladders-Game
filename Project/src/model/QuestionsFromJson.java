package model;

import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class QuestionsFromJson {
    @JsonProperty("questions")
    private  List<Question> questions;
    private static String path ="src\\Json\\Questions.txt";
    // Singleton instance
    private static QuestionsFromJson instance;

    // Private constructor
    private QuestionsFromJson(List<Question> questions) {
        this.questions = questions;
    }
    private QuestionsFromJson() {
        this.questions = new ArrayList<>();
    }
    /**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	private static List<Question> readQuestionsFromJson(String filePath) throws IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<List<Question>>() {});
	}

	public static QuestionsFromJson getInstance() {
	    if (instance == null) {
	        try {
	            List<Question> questions = readQuestionsFromJson(path);
	            instance = new QuestionsFromJson(questions);
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Create an empty instance if an error occurs
	            instance = new QuestionsFromJson(new ArrayList<>());
	        }
	    }
	    return instance;
	}




  
    
    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }


    // The Idea is to sort the Questions by dif and then change the id of the original Question
    // to a counter start from 1
    public List<Question> getQuestionsByDifficulty(int difficulty) {
        AtomicInteger counter = new AtomicInteger(1);
        return this.questions.stream()
            .filter(q -> q.getDifficulty() == difficulty)
            .map(q -> {
                Question newQuestion = new Question();
                //newQuestion.setId(counter.getAndIncrement());
                newQuestion.setQuestion(q.getQuestion());
                newQuestion.setAnswers(q.getAnswers());
                newQuestion.setCorrectAnswer(q.getCorrectAnswer());
                newQuestion.setDifficulty(q.getDifficulty());
                return newQuestion;
            })
            .collect(Collectors.toList());
    }

    
    public void editQuestion(Question beforeChange, Question newQuestion) {
        try {
            // Read existing questions from JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            List<Question> questions = objectMapper.readValue(new File("src\\Json\\Questions.txt"), new TypeReference<List<Question>>() {});

            // Find the question to be edited and replace it with the new question
            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                if (q.equals(beforeChange)) {
                    questions.set(i, newQuestion);
                    break;
                }
            }

            // Write the updated questions back to the JSON file
            objectMapper.writeValue(new File("src\\Json\\Questions.txt"), questions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeQuestionsToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing
        try {
            objectMapper.writeValue(new File(path), getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



   
}
