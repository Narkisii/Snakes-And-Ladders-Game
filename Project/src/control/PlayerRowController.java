package control;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PlayerRowController {

    @FXML
    private ComboBox<String> color;
    @FXML
    private ComboBox<String> token;
    @FXML
    private Label num;
    @FXML
    private TextField playerName;

    @FXML
    private Pane paneRow;
    @FXML
    private HBox hbox_Players;
    
    public void initialize(int playerNumber) {
        // Set the text of the label
        num.setText(String.valueOf(playerNumber));
        // Set the id of the ComboBoxes
        color.setId("color" + playerNumber);
        token.setId("token" + playerNumber);

        // Set the items of the ComboBoxes
        color.setItems(FXCollections.observableArrayList("Yellow", "Pink", "Green","blue","red"));
        token.setItems(FXCollections.observableArrayList("hat", "shoe", "ball","horse","fire"));
//        paneRow.setMaxWidth(Double.MAX_VALUE);
//        paneRow.setMaxHeight(Double.MAX_VALUE);
//        paneRow.setMinWidth(0);
//        paneRow.setMinHeight(0);
//        
//        hbox_Players.setMaxWidth(Double.MAX_VALUE);
//        hbox_Players.setMaxHeight(Double.MAX_VALUE);
//        hbox_Players.setMinWidth(0);
//        hbox_Players.setMinHeight(0);
    }
    
    public void bindSize(ReadOnlyDoubleProperty  widthProperty, ReadOnlyDoubleProperty  heightProperty, int numTiles) {
    	paneRow.prefWidthProperty().bind(widthProperty.divide(numTiles));
    	paneRow.prefHeightProperty().bind(heightProperty.divide(numTiles));
    	hbox_Players.prefWidthProperty().bind(widthProperty.divide(numTiles));
    	hbox_Players.prefHeightProperty().bind(heightProperty.divide(numTiles));

    }
}

