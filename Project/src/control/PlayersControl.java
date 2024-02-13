package control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GameData;
import model.Player;

public class PlayersControl {

    @FXML
    private Button return_Btn; // Add this line

    @FXML
    private Button start_game_Btn; // Add this line
    
    @FXML
    
    private VBox playerContainer;
    @FXML
    private ScrollPane PlayersPane;
    
    //private GridPane gridPane;

    private Player[] players;
    
    @FXML
    public void initialize() {
    	
    	PlayersPane.setFitToWidth(true);
        PlayersPane.setFitToHeight(true);
    
        int numberOfPlayers = GameData.getNumberOfPlayers();
        System.out.println("The numbers of players is "+ numberOfPlayers);
         Player[] players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
        	
        	
            HBox playerRow = new HBox(); // Create a new HBox for each player's elements

            Label num = new Label(); // Create a new Label for the player's number
            num.setId("num" + (i+1)); // unique id
            num.setContentDisplay(ContentDisplay.CENTER);
            num.setPrefHeight(67.0);
            num.setPrefWidth(5.0);
            num.getStyleClass().add("normal_Font");
            num.getStylesheets().add("@backGroundAll.css");
            num.setText(Integer.toString(i+1));
            playerRow.getChildren().add(num); // Add to the playerRow HBox
            HBox.setHgrow(num, Priority.ALWAYS); // Make 'num' expand to fill available horizontal space

            TextField playerName = new TextField(); // Create a new playerName for the player's name
            playerName.setId("playerName" + (i+1)); // unique id
            playerName.setPrefHeight(65.0);
            playerName.setPrefWidth(191.0);
            playerName.getStyleClass().add("comboBox_Nornal");
            playerName.getStylesheets().add("@Buttons.css");
            playerRow.getChildren().add(playerName); // Add to the playerRow HBox
            HBox.setHgrow(playerName, Priority.ALWAYS); // Make 'playerName' expand to fill available horizontal space           
          
            ComboBox<String> color = new ComboBox<>(); // Create a new color for the player's color
            color.setId("color" + (i+1)); // unique id
            color.setPrefHeight(65.0);
            color.setPrefWidth(240.0);
            color.getStyleClass().add("comboBox_Nornal");
            color.getStylesheets().add("@Buttons.css");
            playerRow.getChildren().add(color); // Add to the playerRow HBox
            HBox.setHgrow(color, Priority.ALWAYS); // Make 'color' expand to fill available horizontal space
            
            ComboBox<String> token = new ComboBox<>(); // Create a new token for the player's token
            token.setId("token" + (i+1)); // unique id
            token.setPrefHeight(65.0);
            token.setPrefWidth(240.0);
            token.getStyleClass().add("comboBox_Nornal");
            token.getStylesheets().add("@Buttons.css");
            playerRow.getChildren().add(token); // Add 'token' to the playerRow HBox
            HBox.setHgrow(token, Priority.ALWAYS); // Make 'token' expand to fill available horizontal space
            
            // Add token options
            token.getItems().addAll("Horse", "Car", "Plane", "Boat", "Train");
            
            // Add color options
            color.getItems().addAll("Red", "Blue", "Green", "Yellow", "Purple");
            
            playerContainer.getChildren().add(playerRow); // Add the playerRow HBox to your playerContainer VBox
            VBox.setVgrow(playerRow, Priority.ALWAYS); // Make 'playerRow' expand to fill available vertical space
        }
        return_Btn.setOnAction(event -> navigateTo("/view/SettingsView.fxml"));
        start_game_Btn.setOnAction(event -> {
            for (int i = 0; i < numberOfPlayers; i++) {
                // Get the player's name from the TextField
                TextField playerNameField = (TextField) playerContainer.lookup("#playerName" + (i+1));
                String playerName = playerNameField.getText();

                // Get the player's color from the ComboBox
                ComboBox<String> colorBox = (ComboBox<String>) playerContainer.lookup("#color" + (i+1));
                String color = colorBox.getValue();

                // Get the player's token from the ComboBox
                ComboBox<String> tokenBox = (ComboBox<String>) playerContainer.lookup("#token" + (i+1));
                String token = tokenBox.getValue();

                // Construct the Player object
                Player p = new Player(color,playerName, token);
                players[i] = p;
                GameData.setPlayers(players);
            }

            // Navigate to the game board
            navigateTo("/view/BoardView.fxml");
        });

    }

    private void navigateTo(String fxmlFile) {
        try {
            Stage stage = (Stage) return_Btn.getScene().getWindow();
            double width = stage.getScene().getWidth();
            double height = stage.getScene().getHeight();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private PlayersControl[] create_players() {
		return null;
    }

    private void updateGameData_Players(){
    	
    }
}