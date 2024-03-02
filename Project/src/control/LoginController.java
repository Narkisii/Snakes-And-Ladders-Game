package control;

import java.util.EventListener;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
	private HBox buttonHBox;

	@FXML
	private Button loginButton, cancelButton;

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

	private QuestionWizControl previousWindow; // hold questionWiz screen

	private static boolean status; // login status

	public void initialize() {
		status = false;
		setLoginButton();
		setLoginEvent();
		showLoginMessage(status);
		if(status) {
			login();
		}
	}

	public void setLoginButton() {
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
				login();
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
		} else { // login details incorrect
			statusLbl.setText("Incorrect username or password");
			statusLbl.setStyle("-fx-text-fill: #C21010;");
		}
	}

	public void login() {
		// Approve the user is admin and give permissions
		previousWindow.setAdmin(status);
		previousWindow.disableAdminControls(false);

		// Create animation event to close the screen after 5 seconds
		PauseTransition delay = new PauseTransition(Duration.seconds(2));
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

	/*
	 * // Method to initialize the controller public void initialize() { assert
	 * mainPane != null :
	 * "fx:id=\"mainPane\" was not injected: check your FXML file 'Login.fxml'.";
	 * 
	 * // Load the image for moving around Image movingImageSrc = new
	 * Image(getClass().getResourceAsStream("/view/Images/BackGround/1.png"));
	 * movingImage.setImage(movingImageSrc);
	 * 
	 * // Load the image for moving around Image movingImageSrc1 = new
	 * Image(getClass().getResourceAsStream("/view/Images/BackGround/2.png"));
	 * movingImage.setImage(movingImageSrc1);
	 * 
	 * // Call method to animate the movement of both images
	 * animateMovingImage(movingImage, 300, 200); // animate movingImage
	 * animateMovingImage(movingImage1, -300, -200); // animate movingImage1 in
	 * mirrored way }
	 * 
	 * // Method to animate the movement of an image private void
	 * animateMovingImage(ImageView imageView, double toX, double toY) {
	 * TranslateTransition transition = new TranslateTransition(Duration.seconds(3),
	 * imageView); transition.setFromX(0); transition.setFromY(0);
	 * transition.setToX(toX); // Destination X coordinate transition.setToY(toY);
	 * // Destination Y coordinate
	 * transition.setCycleCount(TranslateTransition.INDEFINITE); // Repeat
	 * indefinitely transition.setAutoReverse(true); // Reverse direction on
	 * completion transition.play(); // Start the animation }
	 */
}
