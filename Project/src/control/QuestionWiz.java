package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuestionWiz {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab EasyTab;

    @FXML
    private Tab MediumTab;

    @FXML
    private Button Return_Btn;

    @FXML
    private AnchorPane hardTab;
    
    @FXML
    private ScrollPane easyScroll;
    @FXML
    private VBox vBox;
    @FXML
    void initialize() {
    	easyScroll.setFitToWidth(true);
    	easyScroll.setFitToHeight(true);
    	
    	 for (int i = 1; i <= 5; i++) {
             Button button = new Button("Button " + i);
             button.setOnAction(event -> {
                 // Handle the button click here
                 System.out.println("Button clicked: " + button.getText());
             });

             HBox questionRow = new HBox(button); // Create a new HBox for each button
             vBox.getChildren().add(questionRow); // Add the HBox to the VBox
         }

    	Return_Btn.setOnAction(event -> navigateTo("/view/MenuScreen.fxml"));

    }
    private void navigateTo(String fxmlFile) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)));
            Stage stage = (Stage) Return_Btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






//package control;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import model.Question;
//
//public class QuestionWiz {
//	 @FXML
//	    private VBox easyQuestions;
//	    @FXML
//	    private VBox mediumQuestions;
//	    @FXML
//	    private VBox hardQuestions;
//	   
//	    private Question[] easy_Questions;
//	    private Question[] mid_Questions;
//	    private Question[] hard_Questions;
//
//	    private Button button_edit, button_add, button_return;
//
//	    @FXML
//	    public void initialize() {
//	        loadQuestionsIntoVBox();
//	    }
//
//	    private void loadQuestionsIntoVBox() {
//	        try {
//	            for (int i = 0; i < 10; i++) {
//	                FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionWiz.fxml")); // Ensure this is the correct path to your 'question' FXML file
//	                Node question = loader.load();
//	                easyQuestions.getChildren().add(question);
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            // Handle exceptions here
//	        }
//	    }
//	    
//	    private boolean addQ() {
//	    	return true;
//	    }
//	    
//	    private boolean edit_Q(Question q) {
//	    	return true;
//	    }
//	    
//	    private boolean delete_Q(Question q) {
//	    	return true;
//	    }
//	    
//	}
//
//
