package control;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Platform;

/**
 * Sample Skeleton for 'Board_Dif2.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Board {

    @FXML
    private ResourceBundle resources;
    
    
    @FXML // fx:id="boardpane"
    private AnchorPane boardpane; // Value injected by FXMLLoader
    
    @FXML
    private Button return_btn;

    private GridPane grid;
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        grid = new GridPane();
        int numTiles = getNumOfTiles();
        int size = numTiles*numTiles;
        TileController[][] tiles = new TileController[numTiles][numTiles];
        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < numTiles; j++) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tile.fxml"));
                    Pane pane = loader.load();
                    TileController controller = loader.getController();
                    controller.bindSize(grid.widthProperty(), grid.heightProperty(), numTiles);
                    pane.setUserData(controller);
                    tiles[i][j] = controller;
                    // Add the pane to the grid
                    grid.add(pane, i, j);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }       

        grid.prefWidthProperty().bind(boardpane.widthProperty());
        grid.prefHeightProperty().bind(boardpane.heightProperty());
        GridPane.setVgrow(grid, Priority.ALWAYS);
        GridPane.setHgrow(grid, Priority.ALWAYS);


        // Add the StackPane to the boardpane
        boardpane.getChildren().add(grid);     
        AnchorPane.setTopAnchor(grid, 0.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(grid, 0.0);
        AnchorPane.setRightAnchor(grid, 0.0);


        return_btn.setOnAction(event -> navigateTo("/view/MenuScreen.fxml"));
}


	private int getNumOfTiles() {
    int numTiles = 0;
    String diff = GameData.getDifficulty();
    switch (diff.toLowerCase()) {
    case "easy":
    	numTiles = 7;
        break;
    case "medium":
    	numTiles = 10;
        break;
    case "hard":
    	numTiles = 13;
        break;
    default:        
    }
    return numTiles;
}
    private void navigateTo(String fxmlFile) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)));
            Stage stage = (Stage) return_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


