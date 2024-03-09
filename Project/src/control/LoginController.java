package control;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import exceptions.HandleExceptions;
import exceptions.IllegalCharacter;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {

	@FXML
	private Button loginButton;

	@FXML
	private VBox formVBox;

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Label statusLbl;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private Pane statusPane; // status pane for login message

	private QuestionWizControl previousWindow; // hold questionWiz control

	private static boolean status; // login status

	Pattern pattern = Pattern.compile("[a-zA-Z0-9]{0,10}");

	public void initialize() {
		status = false;
		checkUserInput();
		setLoginEvent();
		showLoginMessage(status);

		//		if (status) {
		//			approveLogin();
		//		}
	}

	public void checkUserInput() {
		/*
		 * The login button will be disables at initialize, and enabled only when the
		 * text fields are not empty.
		 */
		loginButton.setDisable(true);
		// Checks the inputs are not empty, if true - enables the login button
		ChangeListener<String> textFieldListener = (observable, oldValue, newValue) -> {
			boolean isEmpty = usernameField.getText().isEmpty() || passwordField.getText().isEmpty();
			loginButton.setDisable(isEmpty);
		};
		setTextFields(textFieldListener);

		// TODO: handle cancel button
	}

	public void setTextFields(ChangeListener<String> listener) {
		usernameField.textProperty().addListener(listener);
		passwordField.textProperty().addListener(listener);
		usernameField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				loginButton.fire();
			}
		});

		passwordField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				loginButton.fire();
			}
		});

		UnaryOperator<TextFormatter.Change> filter = change -> {
			try {
				if (pattern.matcher(change.getControlNewText()).matches()) {
					return change;
				} else {
					throw new IllegalCharacter();
				}
			} catch (IllegalCharacter e) {
				// TODO Auto-generated catch block
				HandleExceptions.showException(e,this, usernameField.getScene().getWindow());
				return null;
			}
		};

		usernameField.setTextFormatter(new TextFormatter<>(filter));
		passwordField.setTextFormatter(new TextFormatter<>(filter));

	}

	public void setLoginEvent() {
		loginButton.setOnAction(event -> {
			// get username and password inputs
			String nameInput = usernameField.getText().toLowerCase();
			String passInput = passwordField.getText().toLowerCase();
			setStatusPane();

			// Check login details
			if (nameInput.equals("admin") && passInput.equals("admin")) {
				// verify login
				status = true;
				approveLogin();
			}
			// show login message according to status
			showLoginMessage(status);
		});
	}

	public void setStatusPane() {
		statusLbl.setVisible(true);
		statusLbl.setId("loginMsg");
		statusLbl.getStyleClass().add("loginMsg");
		statusPane.setVisible(true);
	}

	public void showLoginMessage(boolean isValid) {
		if (isValid) { // login details are correct
			statusLbl.setText("Login successful!");
			statusLbl.setStyle("-fx-text-fill: #367E18");
		}
		else { // login details incorrect
			statusLbl.setText("Login failed!");
			statusLbl.setStyle("-fx-text-fill: #C21010; -fx-font-size: 20pt");
			statusLbl.setWrapText(true);
		}
	}

	public void approveLogin() {
		// Approve the user is admin and give permissions
		previousWindow.enableAdminControls();

		// Create animation event to close the screen after 5 seconds
		PauseTransition delay = new PauseTransition(Duration.seconds(1));
		delay.setOnFinished(event_2 -> {
			Stage stage = (Stage) mainPane.getScene().getWindow();
			stage.close();
		});
		delay.play();
	}

	public void setPreviousWindow(QuestionWizControl questionWizControl2) {
		// TODO Auto-generated method stub
		previousWindow = questionWizControl2;
	}

	public QuestionWizControl getPreviousWindow() {
		return previousWindow;
	}

	public boolean getStatus() {
		return status;
	}

}
