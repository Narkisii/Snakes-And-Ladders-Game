package control;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuestionSavedControl {
	@FXML
	private Label message_Lbl;

	@FXML
	private Button okButton;

	@FXML
	private AnchorPane pop_anchorPane;

	@FXML
	private VBox pop_vBox;

	private QuestionEditorPopControl previousWindow; // hold question editor control

	public void initialize() {
		setMessage();
		okButton.setOnAction(event -> {
			((Stage) okButton.getScene().getWindow()).close();

		});
	}

	/*
	 * Set the message by the type of action:
	 * create new question or edit existing one
	 */
	public void setMessage() {
		if (previousWindow.getType() == "add") {
			message_Lbl.setText("Question successfully added!");
		}
		else {
			message_Lbl.setText("Question saved");
		}
	}
	
	public void setButtonEvent() {
//		okButton.setOnAction(event -> {
//			PauseTransition delay = new PauseTransition(Duration.millis(500));
//			delay.setOnFinished(event_2 -> {
//				Stage stage = (Stage) okButton.getScene().getWindow();
//				stage.close();
//			});
//			delay.play();
//		});
	}

	public void setPreviousWindow(QuestionEditorPopControl controller) {
		// TODO Auto-generated method stub
		previousWindow = controller;

	}
}
