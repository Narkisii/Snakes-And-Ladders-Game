package control;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.GameData;

public class SettingsControl {

	@FXML
	private ComboBox<String> difficulty;

	@FXML
	private ComboBox<Integer> number_of_players;

	@FXML
	private Button set_players_btn;

	@FXML
	private Button return_Btn;

	@FXML
	public void initialize() {
		GameData.getInstance().init();
		String diff = GameData.getInstance().getDifficulty();
		int num_Players = GameData.getInstance().getNumberOfPlayers();
		// Initialize the difficulty ComboBox
		difficulty.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
		if (diff != "Easy") {
			difficulty.getSelectionModel().select(diff);
			; // Set default value
		} else {
			difficulty.getSelectionModel().select("Easy");
			; // Set default value
		}
		// Initialize the number_of_players ComboBox
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

		// Add actions for your buttons here
		set_players_btn.setOnAction(event -> navigateTo("/view/PlayersView.fxml"));
		return_Btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
	}

	public int getNumberOfPlayers() {
		return number_of_players.getSelectionModel().getSelectedItem();
	}

	public String getGameDifficulty() {
		return difficulty.getSelectionModel().getSelectedItem();
	}

	private void navigateTo(String fxmlFile) {
		System.out.println(GameData.getInstance().toString());
		try {
			Stage stage = (Stage) set_players_btn.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();

			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private void updateGameData_Settings() {

	}

}
