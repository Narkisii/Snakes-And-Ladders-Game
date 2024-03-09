package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class QuestionSavedControl {
	@FXML
	private Label message_Lbl;

	@FXML
	private Button okButton;
	
	private QuestionEditorPopControl previousWindow; // hold question editor control
	
	public void initialize() {
		
	}
	
	public void setPreviousWindow(QuestionEditorPopControl questionWizControl2) {
		// TODO Auto-generated method stub
		previousWindow = questionWizControl2;

	}
}
