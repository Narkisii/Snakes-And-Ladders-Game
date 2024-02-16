package control;

import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import javafx.util.Duration;
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
	private TableView<Question> easy_QTable;

	@FXML
	private TableColumn<Question, String> id_col_easy;

	@FXML
	private TableColumn<Question, String> q_col_easy;
	
	@FXML
	private TextField med_search_input, hard_search_input, easy_search_input;
	
	@FXML
	private Button add_easy_button;
	
	@FXML
	private Button rm_med_button;
	
	@FXML
	private Button easy_button,med_button,hard_button;
	
    private Stage popupStage;

	@FXML
	void initialize() {
	    // Read the question data from the JSON file
	    QuestionsFromJson  questionData;
	    List<Question>easy_questionList,med_questionList,hard_questionList;
	    try {
	        questionData = readQuestionFromJson("src\\Json\\Questions.txt");
	    } catch (IOException e) {
	        e.printStackTrace();
	        return;
	    }
	    
	    //Sort the Json 
	    easy_questionList = questionData.getQuestionsByDifficulty(1);
	    med_questionList = questionData.getQuestionsByDifficulty(2);
	    hard_questionList = questionData.getQuestionsByDifficulty(3);
	    
	    // Update col
	    id_col_easy.setCellValueFactory(new PropertyValueFactory<>("id"));
	    q_col_easy.setCellValueFactory(new PropertyValueFactory<>("question"));

	    // Set the table data
	    ObservableList<Question> data = FXCollections.observableArrayList(med_questionList); // Uncomment this line
	    easy_QTable.setItems(data);
	    
	    //Activate buttons easy, med, and hard
	    easy_button.setOnAction(event -> {
	        ObservableList<Question> easyData = FXCollections.observableArrayList(easy_questionList);
	        easy_QTable.setItems(easyData);
	    });

	    med_button.setOnAction(event -> {
	        ObservableList<Question> medData = FXCollections.observableArrayList(med_questionList);
	        easy_QTable.setItems(medData);
	    });

	    hard_button.setOnAction(event -> {
	        ObservableList<Question> hardData = FXCollections.observableArrayList(hard_questionList);
	        easy_QTable.setItems(hardData);
	    });
	    
	 // Add a mouse click event to the rows of the table
	    easy_QTable.setRowFactory(tv -> {
	        TableRow<Question> row = new TableRow<>();
	        final AtomicInteger clickCount = new AtomicInteger(0);
	        final Timeline clickTimer = new Timeline(new KeyFrame(Duration.seconds(2), e -> clickCount.set(0)));
	        clickTimer.setCycleCount(1);

	        row.setOnMouseClicked(event -> {
	            if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
	                clickCount.incrementAndGet();
	                if (clickCount.get() == 2 && (popupStage == null || !popupStage.isShowing())) {
	                    // Create a new Stage for the pop-up
	                    popupStage = new Stage();

	                    // Load the FXML file for the pop-up
	                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditQuestionPop.fxml"));
	                    Parent root = null;
	                    try {
	                        root = loader.load();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }

	                    // Get the controller and pass the question object
	                    EditQuestionPopControl controller = loader.getController();
	                    controller.setQuestion(row.getItem() );

	                    // Set the scene and show the stage
	                    Scene scene = new Scene(root);
	                    popupStage.setScene(scene);
	                    popupStage.show();

	                    // Reset click count
	                    clickCount.set(0);
	                }
	                clickTimer.playFromStart();
	            }
	        });
	        return row;
	    });


	    
	    //Configure buttons
	    Return_Btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
	    add_easy_button.setOnAction(event -> navigateTo("/view/addQuestionPop.fxml"));
	   

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
