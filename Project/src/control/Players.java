package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.collections.FXCollections;

public class Players {

    @FXML
    private TextField name1, name2, name3, name4, name5;

    @FXML
    private ComboBox<String> color1, color2, color3, color4, color5;

    @FXML
    private ComboBox<String> token1, token2, token3, token4, token5;
    
    @FXML
    private Text num1, num2, num3, num4, num5;

    @FXML
    private Button return_Btn; // Add this line

    @FXML
    private Button start_game_Btn; // Add this line

    private Players[] players;
    @FXML
    public void initialize() {
        int numberOfPlayers = GameData.getNumberOfPlayers();
        // Initialize the color ComboBoxes
        color1.setItems(FXCollections.observableArrayList("Yellow", "Pink", "Green"));
        color2.setItems(FXCollections.observableArrayList("Yellow", "Pink", "Green"));
        color3.setItems(FXCollections.observableArrayList("Yellow", "Pink", "Green"));
        color4.setItems(FXCollections.observableArrayList("Yellow", "Pink", "Green"));
        color5.setItems(FXCollections.observableArrayList("Yellow", "Pink", "Green"));

        // Initialize the token ComboBoxes
        token1.setItems(FXCollections.observableArrayList("Hat", "Car", "Flower"));
        token2.setItems(FXCollections.observableArrayList("Hat", "Car", "Flower"));
        token3.setItems(FXCollections.observableArrayList("Hat", "Car", "Flower"));
        token4.setItems(FXCollections.observableArrayList("Hat", "Car", "Flower"));
        token5.setItems(FXCollections.observableArrayList("Hat", "Car", "Flower"));

        // Hide or show the color and token combo boxes based on the number of players
        color3.setVisible(numberOfPlayers >= 3);
        color4.setVisible(numberOfPlayers >= 4);
        color5.setVisible(numberOfPlayers >= 5);
        token3.setVisible(numberOfPlayers >= 3);
        token4.setVisible(numberOfPlayers >= 4);
        token5.setVisible(numberOfPlayers >= 5);

        // Hide or show the name text fields based on the number of players
        name3.setVisible(numberOfPlayers >= 3);
        name4.setVisible(numberOfPlayers >= 4);
        name5.setVisible(numberOfPlayers >= 5);

        // Hide or show the number text fields based on the number of players
        num3.setVisible(numberOfPlayers >= 3);
        num4.setVisible(numberOfPlayers >= 4);
        num5.setVisible(numberOfPlayers >= 5);

        // Add actions for your buttons here
        return_Btn.setOnAction(event -> navigateTo("/view/Settings.fxml"));
        start_game_Btn.setOnAction(event -> navigateTo("/view/Board.fxml"));
    }

    private void navigateTo(String fxmlFile) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)));
            Stage stage = (Stage) return_Btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Players[] create_players() {
		return null;
    }

    private void updateGameData_Players(){
    	
    }
}
