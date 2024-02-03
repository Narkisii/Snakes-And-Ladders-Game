package control;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import model.Question;

public class QuestionWiz {
	 @FXML
	    private VBox easyQuestions;
	    @FXML
	    private VBox mediumQuestions;
	    @FXML
	    private VBox hardQuestions;
	   
	    private Question[] easy_Questions;
	    private Question[] mid_Questions;
	    private Question[] hard_Questions;

	    private Button button_edit, button_add, button_return;

	    @FXML
	    public void initialize() {
	        loadQuestionsIntoVBox();
	    }

	    private void loadQuestionsIntoVBox() {
	        try {
	            for (int i = 0; i < 10; i++) {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("question.fxml")); // Ensure this is the correct path to your 'question' FXML file
	                Node question = loader.load();
	                easyQuestions.getChildren().add(question);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Handle exceptions here
	        }
	    }
	    
	    private boolean addQ() {
	    	return true;
	    }
	    
	    private boolean edit_Q(Question q) {
	    	return true;
	    }
	    
	    private boolean delete_Q(Question q) {
	    	return true;
	    }
	    
	}


