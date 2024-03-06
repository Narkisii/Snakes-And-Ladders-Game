package control;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.control.Label;

//import org.jcp.xml.dsig.internal.dom.DOMSubTreeData;
import org.junit.platform.runner.JUnitPlatform;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.NoJsonFileFound;
import model.Question;
import model.QuestionsFromJson;

public class QuestionWizControl {

	// A flag to check if the user is an admin
	public static boolean isAdmin = false;

	// ----------------------------------
	// FXML annotations for UI components
	//
	@FXML
	private ResourceBundle resources; // Resource bundle for localization

	@FXML
	private URL location; // Location URL for the FXML file

	@FXML
	private Button Return_Btn; // Button for returning to the previous screen

	@FXML
	private Button LogIn_Btn, Logout_Btn; // Buttons for login and logout

	@FXML
	private AnchorPane hardTab, easyTab, mediumTab; // Panes for different difficulty tabs

	@FXML
	private ScrollPane easyScroll; // Scroll pane for easy questions

	@FXML
	private Pane messagePane; // Pane for displaying messages

	@FXML
	private Pane topPane, leftPane, rightPane, bottomPane;

	@FXML
	private VBox vBox, center_vBox;

	@FXML
	private HBox bottomMenu_HBox; // Horizontal box for bottom menu

	@FXML
	private TableView<Question> qTable; // Table view for displaying questions

	@FXML
	private TableColumn<Question, String> id_col, q_col; // Columns for question ID and question text

	@FXML
	private Button add_button, rm_button; // Button for add and remove question

	@FXML
	private TextField searchField; // Text field for questions search functionality

	@FXML
	private Label messageLbl; // Label for displaying messages

	@FXML
	private Button easy_button, med_button, hard_button; // Buttons for different difficulty levels

	@FXML
	private BorderPane q_BorderPane; // Border pane for question layout

	@FXML
	private AnchorPane centerPane; // Center pane for main content

	private Stage popupStage; // Stage for pop-up windows

	// Lists for questions of different difficulties
	List<Question> easy_questionList, med_questionList, hard_questionList;

	// Filtered lists for questions of different difficulties
	private FilteredList<Question> easyFilteredData;
	private FilteredList<Question> medFilteredData;
	private FilteredList<Question> hardFilteredData;

	// Tracks the currently displayed filtered list
	private FilteredList<Question> currentFilteredData;

	@FXML
	void initialize() {
		/*
		 * Disable the remove button at initialize to avoid remove erros (like removing
		 * empty question)
		 */
		rm_button.setDisable(true);

		// Check if the user is an admin
		if (!isAdmin) {
			// If not an admin, disable admin controls
			disableAdminControls();
		} else {
			// If an admin, enable admin controls
			enableAdminControls();
		}

		// Update the question table
		update_table();

		// Create observable lists from the question lists
		ObservableList<Question> easyData = FXCollections.observableArrayList(easy_questionList);
		ObservableList<Question> medData = FXCollections.observableArrayList(med_questionList);
		ObservableList<Question> hardData = FXCollections.observableArrayList(hard_questionList);

		// Create filtered lists from the observable lists
		easyFilteredData = new FilteredList<>(easyData, p -> true);
		medFilteredData = new FilteredList<>(medData, p -> true);
		hardFilteredData = new FilteredList<>(hardData, p -> true);

		// Set the current filtered data to the easy questions and update the table
		currentFilteredData = easyFilteredData;
		qTable.setItems(currentFilteredData);
		q_col.setText("Easy Questions");

		// Set the actions for the difficulty buttons
		easy_button.setOnAction(event -> {
			/*
			 * When the easy button is clicked, set the current filtered data to the easy
			 * questions
			 */
			currentFilteredData = easyFilteredData;
			update_table();
			updateFilter();
			qTable.setItems(currentFilteredData);
			q_col.setText("Easy Questions");
			re_init(1);
		});

		med_button.setOnAction(event -> {
			/*
			 * When the medium button is clicked, set the current filtered data to the
			 * medium questions
			 */
			currentFilteredData = medFilteredData;
			update_table();
			updateFilter();
			qTable.setItems(currentFilteredData);
			q_col.setText("Medium Questions");
			re_init(2);
		});

		hard_button.setOnAction(event -> {
			/*
			 * When the hard button is clicked, set the current filtered data to the hard
			 * questions
			 */
			currentFilteredData = hardFilteredData;
			update_table();
			updateFilter();
			qTable.setItems(currentFilteredData);
			q_col.setText("Hard Questions");
			re_init(3);
		});

		// Set the action for the login button
		LogIn_Btn.setOnAction(event -> {
			// When the login button is clicked, open the login pop-up
			setPopUpStage();

			// Load the FXML file for the pop-up
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// import login controller and set this as previous window
			LoginController loginControl = loader.getController();
			loginControl.setPreviousWindow(this);

			// Set the scene and show the stage
			Scene scene = new Scene(root);
			popupStage.setScene(scene);
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
//			popupStage.setAlwaysOnTop(true); // Set always on top
			popupStage.setResizable(false);

			popupStage.show();
		});

		// Set the action for the remove button
		rm_button.setOnAction(event -> {
			// When the remove button is clicked, open the delete question pop-up
			setPopUpStage();

			// Load the FXML file for the pop-up
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteQuestionPop.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Get the controller and pass the selected question to it
			DeleteQuestionPopControl controller = loader.getController();
			Question selectedQuestion = qTable.getSelectionModel().getSelectedItem();
			if (selectedQuestion != null) {
				controller.setQuestionToDelete(selectedQuestion);
			}
			controller.setPreviousWindow(this);

			// Set the scene and show the stage
			Scene scene = new Scene(root);
			popupStage.setScene(scene);
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
			popupStage.setAlwaysOnTop(true); // Set always on top
			popupStage.setResizable(false);

			popupStage.show();
		});

		// Set the action for the add button
		add_button.setOnAction(event -> {
			// When the add button is clicked, open the question editor pop-up
			setPopUpStage();

			// Load the FXML file for the pop-up
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Question_editorPop.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Get the controller and set the type to "add"
			QuestionEditorPopControl controller = loader.getController();
			controller.setPreviousWindow(this);
			controller.setType("add");

			// Set the scene and show the stage
			Scene scene = new Scene(root);
			popupStage.setScene(scene);
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
			popupStage.setAlwaysOnTop(true); // Set always on top
			popupStage.setResizable(false);

			popupStage.show();
		});

		// Add a mouse click event to the rows of the table
		qTable.setRowFactory(tv -> {
			// Create a new table row
			TableRow<Question> row = new TableRow<>();

			// Create a counter for the number of clicks
			final AtomicInteger clickCount = new AtomicInteger(0);

			// Create a timer that resets the click count after 2 seconds
			final Timeline clickTimer = new Timeline(new KeyFrame(Duration.seconds(2), e -> clickCount.set(0)));
			clickTimer.setCycleCount(1);

			// Set the mouse click event for the row
			row.setOnMouseClicked(event -> {
				// Check if the row is not empty and the primary button was clicked
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
					// Increment the click count
					clickCount.incrementAndGet();

					// If the user is an admin, enable the remove button
					if (isAdmin) {
						rm_button.setDisable(false);
					}

					// If the row was double-clicked and there is no pop-up currently showing
					if (clickCount.get() == 2 && (popupStage == null || !popupStage.isShowing())) {
						// Create a new stage for the pop-up
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
						controller.setPreviousWindow(this);
						controller.setType("edit");
						if (row.getItem() != null)
							controller.init_edit(row.getItem());

						// Set the scene and show the stage
						Scene scene = new Scene(root);
						popupStage.setScene(scene);
						popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
						popupStage.setAlwaysOnTop(true); // Set always on top
						popupStage.setResizable(false);

						popupStage.show();

						// Reset click count
						clickCount.set(0);
					}

					// Start the click timer
					clickTimer.playFromStart();
				}
			});
			return row;
		});

		// Set the action for the return button
		Return_Btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));

		// Add a listener to the search field that updates the filter whenever the text
		// changes
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			updateFilter();
		});
	}

	// This method enables admin controls
	public void enableAdminControls() {
		isAdmin = true; // Set the admin flag to true
		setLogoutButton(); // Set the logout button
		swapLogButtons("log in"); // Swap the login button to a logout button
		add_button.setDisable(false); // Enable the add button
		messageLbl.setText("Double click on question to edit"); // Set the message label
	}

	// This method disables admin controls
	public void disableAdminControls() {
		isAdmin = false; // Set the admin flag to false
		swapLogButtons("log out"); // Swap the logout button to a login button
		add_button.setDisable(true); // Disable the add button
		messageLbl.setText("You must log in to edit questions"); // Set the message label
	}

	// This method checks if the user is an admin
	public boolean isAdmin() {
		if (isAdmin)
			return true;
		return false;
	}

	// This method swaps the login and logout buttons
	public void swapLogButtons(String type) {
		switch (type) {
		case "log in": // Hide login button & show logout button
			LogIn_Btn.setVisible(false);
			Logout_Btn.setVisible(true);
			break;

		case "log out": // Hide logout button & show login button
			LogIn_Btn.setVisible(true);
			Logout_Btn.setVisible(false);
			break;
		}
	}

	// This method updates the question table
	public void update_table() {
		QuestionsFromJson questionData;
		try { // Read the questions from JSON
			questionData = QuestionsFromJson.getInstance().readQuestionsFromJson();
		} catch (IOException | NoJsonFileFound e) {
			HandleExceptions.showException(e, this, LogIn_Btn.getScene().getWindow()); // Handle exceptions
			return;
		}

		// Sort the questions by difficulty
		easy_questionList = questionData.getQuestionsByDifficulty(1);
		med_questionList = questionData.getQuestionsByDifficulty(2);
		hard_questionList = questionData.getQuestionsByDifficulty(3);

		// Update the question column
		q_col.setCellValueFactory(new PropertyValueFactory<>("question"));
	}

	// This method updates the filter for the question table
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

	// This method re-initializes the question table based on the difficulty level
	public void re_init(int q_diff) {
		QuestionsFromJson questionData;
		try {
			questionData = QuestionsFromJson.getInstance().readQuestionsFromJson(); // Read the questions from JSON
		} catch (IOException | NoJsonFileFound e) {
			HandleExceptions.showException(e, this, leftPane.getScene().getWindow()); // Handle exceptions
			return;
		}

		rm_button.setDisable(true); // Disable the remove button

		// Sort the questions by difficulty
		easy_questionList = questionData.getQuestionsByDifficulty(1);
		med_questionList = questionData.getQuestionsByDifficulty(2);
		hard_questionList = questionData.getQuestionsByDifficulty(3);

		// Update the question and ID columns
		id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
		q_col.setCellValueFactory(new PropertyValueFactory<>("question"));

		ObservableList<Question> data = FXCollections.observableArrayList(easy_questionList);
		qTable.setItems(data);
		q_col.setText("Easy Questions");

		// Set the table data based on the difficulty level
		if (q_diff == 1) {
			data = FXCollections.observableArrayList(easy_questionList);
			qTable.setItems(data);
			q_col.setText("Easy Questions");
		}
		if (q_diff == 2) {
			data = FXCollections.observableArrayList(med_questionList);
			qTable.setItems(data);
			q_col.setText("Medium Questions");
		}
		if (q_diff == 3) {
			data = FXCollections.observableArrayList(hard_questionList);
			qTable.setItems(data);
			q_col.setText("Hard Questions");
		}
	}

	// This method sets the stage for the pop-up
	public void setPopUpStage() {
		// If a pop-up is already open, do nothing
		if (popupStage != null && popupStage.isShowing()) {
			return;
		} else { // Create a new Stage for the pop-up
			popupStage = new Stage();
			popupStage.setResizable(false);
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
//			popupStage.setAlwaysOnTop(true); // Set always on top

		}
	}

	// This method sets the action for the logout button
	public void setLogoutButton() {
		// When the logout button is clicked
		Logout_Btn.setOnAction(event -> {
			// Create a new stage for the pop-up
			setPopUpStage();

			// Load the FXML file for the pop-up
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Logout.fxml"));
			Parent root = null;
			try {
				root = loader.load(); // Load the FXML file
			} catch (IOException e) {
				e.printStackTrace(); // Print the stack trace for debugging
			}

			// Get the controller for the pop-up
			LogoutControl logoutControl = loader.getController();
			logoutControl.setPreviousWindow(this); // Set the previous window to this

			// Set the scene and show the stage
			Scene scene = new Scene(root);
			popupStage.setScene(scene);
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
			popupStage.setAlwaysOnTop(true); // Set always on top
			popupStage.setResizable(false);

			popupStage.show();
		});
	}

	/*
	 * This method is the screen navigator, and help naviagate between different
	 * FXML files
	 */
	private void navigateTo(String fxmlFile) {
		try {
			// Get the current stage
			Stage stage = (Stage) Return_Btn.getScene().getWindow();

			// Get the current scene's width and height
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();

			// Create a new scene with the specified FXML file
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

			// Set the new scene to the stage
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace(); // Print the stack trace for debugging
		}
	}

}
