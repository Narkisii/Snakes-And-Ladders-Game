package control;

import java.io.IOException;

import exceptions.HandleExceptions;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.GameData;

import exceptions.Questions_empty;

/**
 * The SettingsControl class is responsible for handling the user interactions
 * with the settings UI, allowing the user to select the difficulty level and
 * number of players for the game. It interacts with the GameData model to
 * update these settings across the application.
 */
public class SettingsControl {
	// ComboBox for selecting the game's difficulty level
	@FXML
	private ComboBox<String> difficulty;
	// ComboBox for selecting the number of players
	@FXML
	private ComboBox<Integer> number_of_players;
	// Button to confirm the selection of players
	@FXML
	private Button set_players_btn;
	// Button to return to the previous menu
	@FXML
	private Button return_Btn;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded. It sets up the UI components and
	 * initializes the GameData with default or existing selections.
	 */

	@FXML
	public void initialize() {
		// Initialize game data instance
		GameData.getInstance().init();
		try {
			GameData.getInstance().areListsEmpty();
		} catch (Questions_empty e) {
			// TODO Auto-generated catch block
			HandleExceptions.showException(e, this, null);
		}

		// Retrieve current settings from GameData
		String diff = GameData.getInstance().getDifficulty();
		int num_Players = GameData.getInstance().getNumberOfPlayers();

		// Initialize the difficulty ComboBox with options and set the current selection
		difficulty.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
		if (diff != "Easy") {
			difficulty.getSelectionModel().select(diff);
			// Set default value
		} else {
			difficulty.getSelectionModel().select("Easy");
			// Set default value
		}

		// Initialize the number_of_players ComboBox with options and set the current selection
		number_of_players.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
		if (num_Players != 1) {
			number_of_players.getSelectionModel().select(Integer.valueOf(num_Players)); // Set default value
		} else {
			number_of_players.getSelectionModel().select(Integer.valueOf(1)); // Set default value
		}
		// Update the number of players in GameData when it changes
		number_of_players.valueProperty().addListener((obs, oldVal, newVal) -> {
			GameData.getInstance().setNumberOfPlayers(newVal);
		});

		// Update the difficulty in GameData when it changes
		difficulty.valueProperty().addListener((obs, oldVal, newVal) -> {// Fix to translate to string
			GameData.getInstance().setDifficulty(newVal);
		});

		// Set up button actions to navigate to different views
		set_players_btn.setOnAction(event -> navigateTo("/view/PlayersView.fxml"));
		return_Btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
	}

	/**
	 * Gets the selected number of players from the ComboBox.
	 * 
	 * @return The number of players selected.
	 */

	public int getNumberOfPlayers() {
		return number_of_players.getSelectionModel().getSelectedItem();
	}

	/**
	 * Gets the selected game difficulty from the ComboBox.
	 * 
	 * @return The game difficulty selected.
	 */

	public String getGameDifficulty() {
		return difficulty.getSelectionModel().getSelectedItem();
	}

	/**
	 * Navigates to a different view specified by the fxmlFile parameter.
	 * 
	 * @param fxmlFile The path to the FXML file that describes the next view.
	 */

	private void navigateTo(String fxmlFile) {
		if(fxmlFile.equals("/view/PlayersView.fxml"))
		try {
			GameData.getInstance().areListsEmpty();
		} catch (Questions_empty e) {
			// TODO Auto-generated catch block
			HandleExceptions.showException(e, this, (Stage) set_players_btn.getScene().getWindow());
			return;
		}
		System.out.println(GameData.getInstance().toString());
		try {
			// Get the current stage from the set_players_btn's scene
			Stage stage = (Stage) set_players_btn.getScene().getWindow();
			// Create a new scene with the specified FXML file
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();
			//			stage.setAlwaysOnTop(true);

			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
			// Set the stage with the new scene
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private void updateGameData_Settings() {

	}

}
