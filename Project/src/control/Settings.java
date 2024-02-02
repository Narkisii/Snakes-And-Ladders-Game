package control;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;

public class Settings {

    @FXML
    private ComboBox<String> difficulty;

    @FXML
    private ComboBox<Integer> number_of_players;

    @FXML
    public void initialize() {
        // Initialize the difficulty ComboBox
        difficulty.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
        difficulty.getSelectionModel().select("Easy"); // Set default value

        // Initialize the number_of_players ComboBox
        number_of_players.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        number_of_players.getSelectionModel().select(Integer.valueOf(1)); // Set default value
    }
}

