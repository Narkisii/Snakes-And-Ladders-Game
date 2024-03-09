package control;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.HandleExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.History;
import exceptions.NoJsonFileFound;
/**
 * The HistoryControl class manages the history view of the application,
 * displaying records of past game sessions including information such as date,
 * players, difficulty level, play time, and the winner. It also provides
 * functionality to return to the main menu and handles loading of history data
 * from a JSON file.
 */
public class HistoryControl {

	// Pane elements in the FXML layout for layout customization
	@FXML
	private Pane topPane;

	@FXML
	private Pane rightPane;

	@FXML
	private Pane bottomPane;

	@FXML
	private Pane leftPane;

	// Button to return to the main menu
	@FXML
	private Button returnBtn;


	// VBox container for history elements
	@FXML
	private VBox vBoxHistory;

	@FXML
	private Label historyHeader;
	// TableView to display history records
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

	// Path to the JSON file containing history data
	private String path = "/Json/History.txt";

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the FXML file has been loaded. It sets up the TableView columns,
	 * loads history data from a JSON file, and initializes UI component actions.
	 */
	@FXML
	public void initialize() {
		// Setup action to return to the main menu
		returnBtn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));

		// Read the history data from the JSON file
		List<History> historyData = readHistoryFromJson(path);

		PropertyValueFactory<History, String> test = new PropertyValueFactory<>("playTime");
		System.out.println(test);
		// Initialize the TableView with the history data
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		playersCol.setCellValueFactory(new PropertyValueFactory<>("players"));
		difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
		timeCol.setCellValueFactory(new PropertyValueFactory<>("playTime"));
		timeCol.setCellFactory(column -> {
		    return new TableCell<History, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		                setText(null);
		            } else {
		                int timeSeconds = Integer.parseInt(item);
						int hours = timeSeconds / 3600;
						int minutes = (timeSeconds % 3600) / 60;
						int seconds = timeSeconds % 60;

		                setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
		            }
		        }
		    };
		});		winnerCol.setCellValueFactory(new PropertyValueFactory<>("winner"));

		// Convert the List to an ObservableList
		ObservableList<History> observableList = FXCollections.observableArrayList(historyData);

		// Set the data to the TableView
		historyTable.setItems(observableList);
		// Add action for your button here
	}

//	/**
//	 * Reads history data from a specified JSON file.
//	 * 
//	 * @param filePath The file path of the JSON file.
//	 * @return A list of History objects.
//	 * @throws IOException If an I/O error occurs.
//	 * @throws NoJsonFileFound If the JSON file is not found.
//	 */
//	private List<History> readHistoryFromJson(String filePath) throws IOException, NoJsonFileFound {
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			System.out.println(Paths.get(filePath).toFile().length());
//			return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<List<History>>() {});
//		} catch (Exception e) {
//			// Attempt to use a default path if the initial read fails
//			try {
//				filePath = "src/Json/History.txt";
//				System.out.println(Paths.get(filePath).toFile().length());
//				return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<List<History>>() {});
//
//			} catch (Exception e2) {
//				// TODO: handle exception
//				//				System.out.println(e2.getMessage());
//				throw new NoJsonFileFound();
//			}
//		}
//	}
	/**
	 * Reads history data from a specified JSON file.
	 * 
	 * @param filePath The file path of the JSON file.
	 * @return A list of History objects.
	 * @throws IOException If an I/O error occurs.
	 * @throws NoJsonFileFound If the JSON file is not found.
	 */
	private List<History> readHistoryFromJson(String filePath) {
	    ObjectMapper mapper = new ObjectMapper();
	    try {
	        File file = Paths.get(filePath).toFile();
	        if (file.length() == 0) {
	            return new ArrayList<>();
	        }
	        return mapper.readValue(file, new TypeReference<List<History>>() {});
	    } catch (Exception e) {
	        // Attempt to use a default path if the initial read fails
	        try {
	            filePath = "src/Json/History.txt";
	            File file = Paths.get(filePath).toFile();
	            if (file.length() == 0) {
	                return new ArrayList<>();
	            }
	            return mapper.readValue(file, new TypeReference<List<History>>() {});
	        } catch (Exception e2) {
	        }
	    }
        return new ArrayList<>();
	}



	public HistoryControl createHistoryRecord(String players, int difficulty, String playTime, String winner) {
		return null;
		// will create history record to save in JSON file
	}

	/**
	 * Navigates to a different view specified by the FXML file path.
	 * 
	 * @param fxmlFile The path to the FXML file for the new view.
	 */
	private void navigateTo(String fxmlFile) {
		try {
			// Get the current stage from the return button's scene
			Stage stage = (Stage) returnBtn.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();
			// Load the new scene
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
			// Set the new scene on the current stage
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
