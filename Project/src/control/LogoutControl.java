package control;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class controls the logout process.
 */
public class LogoutControl {
	@FXML
	private Button yesButton, noButton; // Buttons for confirming or canceling logout

	@FXML
	private AnchorPane logoutPane; // The pane that contains the logout controls

	@FXML
	private Label message_Lbl; // Label to display messages to the user

	private QuestionWizControl previousWindow; // Reference to the previous window (questionWiz)

	/**
	 * This method is called after all @FXML annotated members have been
	 * initialized. It sets up the buttons for the logout process.
	 */
	public void initialize() {
		setButtons();
	}

	/**
	 * This method sets the actions for the yes and no buttons. The yes button
	 * approves the logout, and the no button cancels it.
	 */
	public void setButtons() {
		// Set the action for the yes button
		yesButton.setOnAction(event -> {
			// When the yes button is clicked, approve the logout
			approveLogout();
		});
		// Set the action for the no button
		noButton.setOnAction(event -> {
			// When the no button is clicked, close the current window
			((Stage) noButton.getScene().getWindow()).close();
		});
	}

	/**
	 * This method is called when the user approves the logout.
	 * It displays a success message, disables admin controls in the previous window,
	 * and closes the logout window after a delay.
	 */
	public void approveLogout() {
		// Set the message label to indicate successful logout
		message_Lbl.setText("Successfully logged out");
		message_Lbl.setStyle("-fx-text-fill: #367E18");

		// Disable admin controls in the previous window
		previousWindow.disableAdminControls();

		// Create animation event to close the screen after hold
		PauseTransition delay = new PauseTransition(Duration.seconds(1));
		delay.setOnFinished(event_2 -> {
			// When the delay is over, close the current window
			Stage stage = (Stage) logoutPane.getScene().getWindow();
			stage.close();
		});
		delay.play();
	}

	/**
	 * This method sets the previous window.
	 * 
	 * @param questionWizControl The control of the previous window.
	 */
	public void setPreviousWindow(QuestionWizControl questionWizControl) {
		// TODO Auto-generated method stub
		previousWindow = questionWizControl;
	}
}
