package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Board {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    public void initialize() {
        button1.setOnAction(event -> showAlert("This is a test"));
        button2.setOnAction(event -> showAlert("Fuck my life !!!!!!"));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
