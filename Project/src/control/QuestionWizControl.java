package control;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.Question;
import model.QuestionsFromJson;

public class QuestionWizControl {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Tab EasyTab;

	@FXML
	private Tab MediumTab;
	
	@FXML
	private Tab HardTab;

	@FXML
	private Button Return_Btn;

	@FXML
	private AnchorPane hardTab,easyTab,mediumTab;

	@FXML
	private ScrollPane easyScroll;
	@FXML
	private VBox vBox;
	
	@FXML
	private TableView<Question> questionTable;

	@FXML
	private TableColumn<Question, String> id;

	@FXML
	private TableColumn<Question, String> question;

	@FXML
	void initialize() {
	    // Read the question data from the JSON file
	    QuestionsFromJson  questionData;
	    List<Question>questionList;
	    try {
	        questionData = readQuestionFromJson("src\\Json\\Questions.txt");
	    } catch (IOException e) {
	        e.printStackTrace();
	        return;
	    }
	    questionList = questionData.getQuestions(); // Uncomment this line
	    //id.setCellValueFactory(new PropertyValueFactory<>("id"));
	    question.setCellValueFactory(new PropertyValueFactory<>("quest3ion"));

	    // Set the table data
	    ObservableList<Question> data = FXCollections.observableArrayList(questionList); // Uncomment this line
	    questionTable.setItems(data);
	    Return_Btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
	    questionTable.setRowFactory(tv -> {
	        TableRow<Question> row = new TableRow<>();
	        row.setOnMouseClicked(event -> {
	            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
	                 && event.getClickCount() == 2) {

	                Question clickedRow = row.getItem();
	                System.out.println(clickedRow.getQuestion());
	            }
	        });
	        return row ;
	    });

	}


	private void navigateTo(String fxmlFile) {
		try {
			Stage stage = (Stage) Return_Btn.getScene().getWindow();
            double width = stage.getScene().getWidth();
            double height = stage.getScene().getHeight();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean delete_Q(Question q) {
		return true;
	}
	
	 private QuestionsFromJson  readQuestionFromJson(String filePath) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<QuestionsFromJson >() {});
	    }
}
