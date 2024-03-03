package control;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controls the Instructions screen in a JavaFX application.
 * This class is responsible for initializing the instructions screen, handling user interactions,
 * and navigating between screens.
 */

public class InstructionsControl {
	
    // Annotation to inject the instructions header label from the FXML
    @FXML
    private Label instructionsHeader;
    // Annotation to inject the dice image view from the FXML
    @FXML
    private ImageView diceImage;
    // Annotation to inject the dice instructions label from the FXML
    @FXML
    private Label diceInstructions;
    // Annotation to inject the bulb image view from the FXML
    @FXML
    private ImageView bulbImage;
    // Annotation to inject the objective instructions label from the FXML
    @FXML
    private Label objectiveInstructions;
    // Annotation to inject the back button from the FXML
    @FXML
    private Button button_back;
    /**
     * Initializes the InstructionsControl class.
     * This method sets up the event handler for the back button, which navigates the user
     * back to the menu screen when clicked.
     */
    @FXML
    public void initialize() {
        // Set up the action for the back button to navigate to the menu screen
    	button_back.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
        
    }
    
    /**
     * Navigates to a specified FXML screen.
     * 
     * @param fxmlFile The path to the FXML file that should be loaded.
     */
    
    private void navigateTo(String fxmlFile) {
        try {
            // Get the current stage from the back button's scene
            Stage stage = (Stage) button_back.getScene().getWindow();
            // Retrieve the current width and height of the scene
            double width = stage.getScene().getWidth();
            double height = stage.getScene().getHeight();
            
            // Load the specified FXML file, setting its width and height to match the current scene
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
            // Set the new scene on the current stage
            stage.setScene(scene);
        } catch (IOException e) {
            // Print the stack trace in case of an IOException
            e.printStackTrace();
        }
    }

}
