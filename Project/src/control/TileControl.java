package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class TileControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    

    @FXML
    private Pane tile;

    @FXML
    void initialize() {
    	tile.setMaxWidth(Double.MAX_VALUE);
    	tile.setMaxHeight(Double.MAX_VALUE);
    	tile.setMinWidth(0);
    	tile.setMinHeight(0);
    }

    public void bindSize(ReadOnlyDoubleProperty  widthProperty, ReadOnlyDoubleProperty  heightProperty, int numTiles) {
        tile.prefWidthProperty().bind(widthProperty.divide(numTiles));
        tile.prefHeightProperty().bind(heightProperty.divide(numTiles));
    }

}
