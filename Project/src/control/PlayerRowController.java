package control;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PlayerRowController {

    @FXML
    private ComboBox<String> color;
    @FXML
    private ComboBox<String> token;
    @FXML
    private Label num;
    @FXML
    private TextField playerName;
    
    
    public void initialize(int playerNumber) {
        // Set the text of the label
        num.setText(String.valueOf(playerNumber));
        // Set the id of the ComboBoxes
        color.setId("color" + playerNumber);
        token.setId("token" + playerNumber);

        // Set the items of the ComboBoxes
        color.setItems(FXCollections.observableArrayList("Yellow", "Pink", "Green","blue","red"));
        token.setItems(FXCollections.observableArrayList("hat", "shoe", "ball","horse","fire"));
    }
}

