package control;

import java.io.IOException;
import java.util.EventListener;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
		this.previousWindow = questionWizControl2;
	}

	public QuestionWizControl getPreviousWindow() {
		return previousWindow;
	}

	public boolean getStatus() {
		return status;
	}

}
