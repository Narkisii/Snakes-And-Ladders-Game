package control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class InstructionsControl {

    @FXML
    private Label instructionsHeader;

    @FXML
    private ImageView diceImage;

    @FXML
    private Label diceInstructions;

    @FXML
    private ImageView bulbImage;

    @FXML
    private Label objectiveInstructions;
    
    @FXML
    private Button button_back;

    @FXML
    public void initialize() {
    	button_back.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
        
    }
    
    private void navigateTo(String fxmlFile) {
        try {
            Stage stage = (Stage) button_back.getScene().getWindow();
            double width = stage.getScene().getWidth();
            double height = stage.getScene().getHeight();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add methods to handle events, update the UI, etc.
}
