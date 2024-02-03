package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class History {

//    @FXML
//    private Pane bottomPane;
//
//    @FXML
//    private TableColumn<?, ?> dateCol;
//
//    @FXML
//    private TableColumn<?, ?> difficultyCol;
//
//    @FXML
//    private Label historyHeader;
//
//    @FXML
//    private TableView<?> historyTable;
//
//    @FXML
//    private Pane leftPane;
//
//    @FXML
//    private TableColumn<?, ?> playersCol;
//
//    @FXML
//    private Button returnButton;
//
//    @FXML
//    private Pane rightPane;
//
//    @FXML
//    private TableColumn<?, ?> timeCol;
//
//    @FXML
//    private Pane topPane;
//
//    @FXML
//    private VBox vBoxHistory;
//
//    @FXML
//    private TableColumn<?, ?> winnerCol;
//
    @FXML
    private Button returnBtn; // Add this line
    private History[] history;
    
    @FXML
    public void initialize() {
        // Add action for your button here
        returnBtn.setOnAction(event -> navigateTo("/view/MenuScreen.fxml"));
    }

    private void navigateTo(String fxmlFile) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)));
            Stage stage = (Stage) returnBtn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
