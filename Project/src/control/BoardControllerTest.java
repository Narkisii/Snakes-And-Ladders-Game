package control;

import java.io.IOException;

/**
 * Sample Skeleton for 'Board_Dif2.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class BoardControllerTest {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boardpane"
    private AnchorPane boardpane; // Value injected by FXMLLoader
    
    @FXML // fx:id="tile_test"
    private Pane tile_test; // Value injected by FXMLLoader
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        GridPane grid = new GridPane();
        int numTiles = 10;
        
        for (int i = 0; i < numTiles; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numTiles);
            grid.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numTiles);
            grid.getRowConstraints().add(rowConst);
        }
        
        
        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < numTiles; j++) {
                try {
                    // Load the Pane from the FXML file
                    Pane pane = FXMLLoader.load(getClass().getResource("/view/tile.fxml"));

                    // Bind the dimensions of the pane to the dimensions of the cells in the grid
                    pane.prefWidthProperty().bind(grid.widthProperty().divide(numTiles));
                    pane.prefHeightProperty().bind(grid.heightProperty().divide(numTiles));

                    // Set the minimum dimensions of the pane to allow it to get smaller
                    pane.minWidthProperty().bind(pane.widthProperty());
                    pane.minHeightProperty().bind(pane.heightProperty());
                    // Add the pane to the grid
                    grid.add(pane, i, j);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Create a StackPane to hold the GridPane
        StackPane stackPane = new StackPane();
        grid.prefWidthProperty().bind(boardpane.widthProperty());
        grid.prefHeightProperty().bind(boardpane.heightProperty());

        // Set the minimum dimensions of the grid to allow it to get smaller
        grid.minWidthProperty().bind(grid.widthProperty());
        grid.minHeightProperty().bind(grid.heightProperty());

        stackPane.getChildren().add(grid);

        // Add the StackPane to the boardpane
        boardpane.getChildren().add(stackPane);
//        boardpane.getChildren().add(grid);
    }

}
