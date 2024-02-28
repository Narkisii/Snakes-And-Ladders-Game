package control;
import model.History;
import model.NoJsonFileFound;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.HandleExceptions;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class HistoryControl {
	

	 @FXML
	    private Pane topPane;

	    @FXML
	    private Pane rightPane;

	    @FXML
	    private Pane bottomPane;

	    @FXML
	    private Button returnBtn;

	    @FXML
	    private Pane leftPane;

	    @FXML
	    private VBox vBoxHistory;

	    @FXML
	    private Label historyHeader;

	    @FXML
	    private TableView<History> historyTable;
	    
	    @FXML
	    private TableColumn<History, String> dateCol;

	    @FXML
	    private TableColumn<History, List<String>> playersCol;

	    @FXML
	    private TableColumn<History, String> difficultyCol;

	    @FXML
	    private TableColumn<History, String> timeCol;

	    @FXML
	    private TableColumn<History, String> winnerCol;
    
	    private String path = "src/Json/History.txt";
    @FXML
    public void initialize() {
        returnBtn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));

    	// Read the history data from the JSON file
        List<History> historyData;
        try {
            historyData = readHistoryFromJson(path);
        } catch (IOException | NoJsonFileFound e) {
        	HandleExceptions.showException(e);
//            e.printStackTrace();
            return;
        }
        
        
     // Initialize the TableView with the history data
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        playersCol.setCellValueFactory(new PropertyValueFactory<>("players"));
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("playTime"));
        winnerCol.setCellValueFactory(new PropertyValueFactory<>("winner"));

        // Convert the List to an ObservableList
        ObservableList<History> observableList = FXCollections.observableArrayList(historyData);

        // Set the data to the TableView
        historyTable.setItems(observableList);
        // Add action for your button here
    }
    
    private List<History> readHistoryFromJson(String filePath) throws IOException, NoJsonFileFound {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<List<History>>() {});
		} catch (Exception e) {
			// TODO: handle exception
			try {
				filePath = "src/Json/History.txt";
				return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<List<History>>() {});

			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2.getMessage());
				throw new NoJsonFileFound();
			}
		}
    }
    
    public HistoryControl createHistoryRecord(String players, int difficulty, String playTime, String winner) {
		return null;
    	// will create history record to save in JSON file
    }

    private void navigateTo(String fxmlFile) {
        try {
            Stage stage = (Stage) returnBtn.getScene().getWindow();
            double width = stage.getScene().getWidth();
            double height = stage.getScene().getHeight();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
}
