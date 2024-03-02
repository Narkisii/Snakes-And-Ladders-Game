package control;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.control.Label;

import org.jcp.xml.dsig.internal.dom.DOMSubTreeData;
import org.junit.validator.PublicClassValidator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.HandleExceptions;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.NoJsonFileFound;
import model.Question;
import model.QuestionsFromJson;

public class QuestionWizControl {

	public static boolean isAdmin = false;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button Return_Btn;

	@FXML
	private Button LogIn_Btn, Logout_Btn;

	@FXML
	private AnchorPane hardTab, easyTab, mediumTab;

	@FXML
	private ScrollPane easyScroll;

	@FXML
	private Pane messagePane;

	@FXML
	private Pane topPane, leftPane, rightPane, bottomPane;

	@FXML
	private VBox vBox, center_vBox;

	@FXML
	private HBox bottomMenu_HBox;

	@FXML
	private TableView<Question> qTable;

	@FXML
	private TableColumn<Question, String> id_col;

	@FXML
	private TableColumn<Question, String> q_col;

	@FXML
	private Button add_button;

	@FXML
	private Button rm_button;

	@FXML
	private TextField searchField;

	@FXML
	private Label messageLbl;

	@FXML
	private Button easy_button, med_button, hard_button;

	@FXML
	private BorderPane q_BorderPane;

	@FXML
	private AnchorPane centerPane;

	private Stage popupStage;

	List<Question> easy_questionList, med_questionList, hard_questionList;

	private FilteredList<Question> easyFilteredData;
	private FilteredList<Question> medFilteredData;
	private FilteredList<Question> hardFilteredData;
	private FilteredList<Question> currentFilteredData; // Tracks the currently displayed filtered list

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

		// disable admin options in initialize
		if (!isAdmin) {
			disableAdminControls(true);
		}
		else {
			LogIn_Btn.setVisible(false);
			Logout_Btn.setVisible(true);

			Logout_Btn.setOnAction(event -> {
				setPopUpStage();

				// Load the FXML file for the pop-up
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Logout.fxml"));
				Parent root = null;
				try {
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// Set the scene and show the stage
				Scene scene = new Scene(root);
				popupStage.setScene(scene);
				popupStage.show();
			});
		}

		update_table();

		ObservableList<Question> easyData = FXCollections.observableArrayList(easy_questionList);
		ObservableList<Question> medData = FXCollections.observableArrayList(med_questionList);
		ObservableList<Question> hardData = FXCollections.observableArrayList(hard_questionList);
		// update_table();
		easyFilteredData = new FilteredList<>(FXCollections.observableArrayList(easy_questionList), p -> true);
		medFilteredData = new FilteredList<>(FXCollections.observableArrayList(med_questionList), p -> true);

		hardFilteredData = new FilteredList<>(FXCollections.observableArrayList(hard_questionList), p -> true);

		currentFilteredData = easyFilteredData;
		qTable.setItems(currentFilteredData);

		// Activate buttons easy, med, and hard
		easy_button.setOnAction(event -> {
			currentFilteredData = easyFilteredData;
			update_table();
			updateFilter();
			qTable.setItems(currentFilteredData);
			q_col.setText("Easy Questions");
			re_init(1);
		});

		med_button.setOnAction(event -> {
			currentFilteredData = medFilteredData;
			update_table();
			updateFilter();
			qTable.setItems(currentFilteredData);
			q_col.setText("Medium Questions");
			re_init(2);
		});

		hard_button.setOnAction(event -> {
			currentFilteredData = hardFilteredData;
			update_table();
			updateFilter();
			qTable.setItems(currentFilteredData);
			q_col.setText("Hard Questions");
			re_init(3);
		});

		// Activate login button
		LogIn_Btn.setOnAction(event -> {
			setPopUpStage();

			// Load the FXML file for the pop-up
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Get the controller and pass the question object
			LoginController loginControl = loader.getController();
			loginControl.setPreviousWindow(this);

			// Set the scene and show the stage
			Scene scene = new Scene(root);
			popupStage.setScene(scene);
			popupStage.show();
		});

		// activate remove button
		rm_button.setOnAction(event -> {
			setPopUpStage();

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

		// Activate add button
		add_button.setOnAction(event -> {
			setPopUpStage();

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

		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			updateFilter();
		});
	}

	public void setAdmin(boolean status) {
		isAdmin = status;
	}

	public boolean isAdmin() {
		if (isAdmin) {
			return true;
		}
		return false;
	}

	public void disableAdminControls(boolean toDisable) {
		add_button.setDisable(toDisable);
		rm_button.setDisable(toDisable);
		LogIn_Btn.setDisable(!toDisable);

		if (toDisable) {
			messageLbl.setText("You must log in to edit questions");
		}
		else {
			messageLbl.setText("Double click on question to edit");
		}
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

	private void updateFilter() {
		if (currentFilteredData != null) {
			currentFilteredData.setPredicate(question -> {
				// If filter text is empty, display all questions.
				if (searchField == null || searchField.getText().isEmpty()) {
					return true;
				}
				// Compare lower case strings for case-insensitive search.
				String lowerCaseFilter = searchField.getText().toLowerCase();
				return question.getQuestion().toLowerCase().contains(lowerCaseFilter);
			});
		}
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

	public void setPopUpStage() {
		// If a pop-up is already open, do nothing
		if (popupStage != null && popupStage.isShowing()) {
			return;
		}
		else { // Create a new Stage for the pop-up
			popupStage = new Stage();
			popupStage.setResizable(false);
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

	/*
	 * private BorderPane adjsutScreen() {
	 * 
	 * }
	 */

//	 private QuestionsFromJson  readQuestionFromJson(String filePath) throws IOException {
//	        ObjectMapper mapper = new ObjectMapper();
//	        return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<QuestionsFromJson >() {});
//	    }
}
