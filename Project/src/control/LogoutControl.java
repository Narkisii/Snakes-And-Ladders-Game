package control;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LogoutControl {
	@FXML
	private Button yesButton, noButton;
	
	@FXML
	private AnchorPane logoutPane;
	
	@FXML
	private Label message_Lbl;

	private QuestionWizControl previousWindow; // hold questionWiz control

	public void initialize() {
		setButtons();
	}

	public void setButtons() {
		yesButton.setOnAction(event -> {
			approveLogout();
		});
		noButton.setOnAction(event -> {
			((Stage)noButton.getScene().getWindow()).close();
		});
	}
	
	public void approveLogout() {
		message_Lbl.setText("Successfully logged out");
		message_Lbl.setStyle("-fx-text-fill: #367E18");
		
		previousWindow.disableAdminControls();
		
		// Create animation event to close the screen after 5 seconds
		PauseTransition delay = new PauseTransition(Duration.seconds(1));
		delay.setOnFinished(event_2 -> {
			Stage stage = (Stage) logoutPane.getScene().getWindow();
			stage.close();
		});
		delay.play();
	}

	public void setPreviousWindow(QuestionWizControl questionWizControl) {
		// TODO Auto-generated method stub
		this.previousWindow = questionWizControl;
	}
}
