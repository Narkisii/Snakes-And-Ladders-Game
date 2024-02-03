package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HistoryControl {
	@FXML
    private Pane bottomPane;

    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private TableColumn<?, ?> difficultyCol;

    @FXML
    private Label historyHeader;

    @FXML
    private TableView<?> historyTable;

    @FXML
    private Pane leftPane;

    @FXML
    private TableColumn<?, ?> playersCol;

    @FXML
    private Button returnButton;

    @FXML
    private Pane rightPane;

    @FXML
    private TableColumn<?, ?> timeCol;

    @FXML
    private Pane topPane;

    @FXML
    private VBox vBoxHistory;

    @FXML
    private TableColumn<?, ?> winnerCol;

}
