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

import exceptions.HandleExceptions;
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
	private AnchorPane hardTab, easyTab, mediumTab;

	@FXML
	private ScrollPane easyScroll;
	@FXML
	private VBox vBox;

	@FXML
	private TableView<Question> qTable;

	@FXML
	private TableColumn<Question, String> id_col;

	@FXML
	private TableColumn<Question, String> q_col;

	@FXML
	private TextField med_search_input, hard_search_input, easy_search_input;

	@FXML
	private Button add_button;

	@FXML
	private Button rm_button;

	@FXML
	private Button easy_button, med_button, hard_button;

	private Stage popupStage;
	List<Question> easy_questionList, med_questionList, hard_questionList;

	@FXML
	void initialize() {
		// Read the question data from the JSON file
//		QuestionsFromJson questionData;
//		List<Question> easy_questionList, med_questionList, hard_questionList;
//		try {
////	        questionData = readQuestionFromJson("src\\Json\\Questions.txt");
//			questionData = QuestionsFromJson.readQuestionsFromJson();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		// Sort the Json
//		easy_questionList = questionData.getQuestionsByDifficulty(1);
//		med_questionList = questionData.getQuestionsByDifficulty(2);
//		hard_questionList = questionData.getQuestionsByDifficulty(3);

		update_table();

		// Set the table data
//		ObservableList<Question> data = FXCollections.observableArrayList(easy_questionList); // Uncomment this line
//		qTable.setItems(data);
		q_col.setText("Easy Questions");
		// Activate buttons easy, med, and hard
		easy_button.setOnAction(event -> {
			update_table();

			ObservableList<Question> easyData = FXCollections.observableArrayList(easy_questionList);
			update_table();
			qTable.setItems(easyData);
			q_col.setText("Easy Questions");

		});

		med_button.setOnAction(event -> {
			update_table();

			ObservableList<Question> medData = FXCollections.observableArrayList(med_questionList);
			qTable.setItems(medData);
			q_col.setText("Medium Questions");

		});

		hard_button.setOnAction(event -> {
			update_table();

			ObservableList<Question> hardData = FXCollections.observableArrayList(hard_questionList);
			qTable.setItems(hardData);
			q_col.setText("Hard Questions");
		});

		// remove
		rm_button.setOnAction(event -> {
			if (popupStage != null && popupStage.isShowing()) {
				// If a pop-up is already open, do nothing
				return;
			}

			// Create a new Stage for the pop-up
			popupStage = new Stage();

			// Load the FXML file for the pop-up
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteQuestionPop.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Get the controller and pass the question object
			DeleteQuestionPopControl controller = loader.getController();
			Question selectedQuestion = qTable.getSelectionModel().getSelectedItem();
			if (selectedQuestion != null) {
				controller.setQuestionToDelete(selectedQuestion);

			}
			controller.setPreviousWindow(this);

			// Set the scene and show the stage
			Scene scene = new Scene(root);
			popupStage.setScene(scene);
			popupStage.show();
		});

		// Add question
		add_button.setOnAction(event -> {
			if (popupStage != null && popupStage.isShowing()) {
				// If a pop-up is already open, do nothing
				return;
			}

			// Create a new Stage for the pop-up
			popupStage = new Stage();

			// Load the FXML file for the pop-up
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Question_editorPop.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Get the controller and pass the question object
			QuestionEditorPopControl controller = loader.getController();
			// Pass controller so I can update the table when question added
			controller.setPreviousWindow(this);
			controller.setType("add");

			// Set the scene and show the stage
			Scene scene = new Scene(root);
			popupStage.setScene(scene);
			popupStage.show();
		});

		// Add a mouse click event to the rows of the table
		qTable.setRowFactory(tv -> {
			TableRow<Question> row = new TableRow<>();
			final AtomicInteger clickCount = new AtomicInteger(0);
			final Timeline clickTimer = new Timeline(new KeyFrame(Duration.seconds(2), e -> clickCount.set(0)));
			clickTimer.setCycleCount(1);

			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
					clickCount.incrementAndGet();
					if (clickCount.get() == 2 && (popupStage == null || !popupStage.isShowing())) {

						popupStage = new Stage();

						// Load the FXML file for the pop-up
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Question_editorPop.fxml"));
						Parent root = null;
						try {
							root = loader.load();
						} catch (IOException e) {
							e.printStackTrace();
						}

						// Get the controller and pass the question object
						QuestionEditorPopControl controller = loader.getController();
						// Pass controller so I can update the table when question added
						controller.setPreviousWindow(this);
						controller.setType("edit");
						if (row.getItem() != null)
							controller.init_edit(row.getItem());

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

		// Configure buttons
		Return_Btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
		// add_easy_button.setOnAction(event ->
		// navigateTo("/view/addQuestionPop.fxml"));

	}

	public void update_table() {
		QuestionsFromJson questionData;
//		List<Question> easy_questionList, med_questionList, hard_questionList;
		try {
//	        questionData = readQuestionFromJson("src\\Json\\Questions.txt");
			questionData = QuestionsFromJson.getInstance().readQuestionsFromJson();
		} catch (IOException | NoJsonFileFound e) {
			HandleExceptions.showException(e);
			return;
		}

		// Sort the Json
		easy_questionList = questionData.getQuestionsByDifficulty(1);
		med_questionList = questionData.getQuestionsByDifficulty(2);
		hard_questionList = questionData.getQuestionsByDifficulty(3);

		// Update col
//		id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
		q_col.setCellValueFactory(new PropertyValueFactory<>("question"));

	}

	public void re_init(int q_diff) {
		QuestionsFromJson questionData;
		try {
//	        questionData = readQuestionFromJson("src\\Json\\Questions.txt");
			questionData = QuestionsFromJson.getInstance().readQuestionsFromJson();
		} catch (IOException | NoJsonFileFound e) {
//			e.printStackTrace();
			HandleExceptions.showException(e);
			return;
		}

		// Sort the Json
		easy_questionList = questionData.getQuestionsByDifficulty(1);
		med_questionList = questionData.getQuestionsByDifficulty(2);
		hard_questionList = questionData.getQuestionsByDifficulty(3);

		// Update col
		id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
		q_col.setCellValueFactory(new PropertyValueFactory<>("question"));
		
		ObservableList<Question> data = FXCollections.observableArrayList(easy_questionList); // Uncomment this line
		qTable.setItems(data);
		q_col.setText("Easy Questions");

		// Set the table data
		if (q_diff == 1) {
			data = FXCollections.observableArrayList(easy_questionList); // Uncomment this line
			qTable.setItems(data);
			q_col.setText("Easy Questions");

		}
		if (q_diff == 2) {
			data = FXCollections.observableArrayList(med_questionList); // Uncomment this line
			qTable.setItems(data);
			q_col.setText("Medium Questions");

		}
		if (q_diff == 3) {
			data = FXCollections.observableArrayList(hard_questionList); // Uncomment this line
			qTable.setItems(data);
			q_col.setText("Hard Questions");

		}
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

//	 private QuestionsFromJson  readQuestionFromJson(String filePath) throws IOException {
//	        ObjectMapper mapper = new ObjectMapper();
//	        return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<QuestionsFromJson >() {});
//	    }
}
