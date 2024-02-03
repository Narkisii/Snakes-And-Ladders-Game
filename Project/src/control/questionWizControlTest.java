package control;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class questionWizControlTest {
	 @FXML
	    private VBox easyQuestions;
	    @FXML
	    private VBox mediumQuestions;
	    @FXML
	    private VBox hardQuestions;
	   
	    

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
	    
	}


