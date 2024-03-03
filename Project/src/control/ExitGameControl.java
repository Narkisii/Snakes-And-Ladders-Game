package control;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ExitGameControl {

	@FXML
	private Button exitButton, noExitButton;

	@FXML
	private AnchorPane popUpPane;
	
	private BoardControl previousWindow; // hold board control
	
	@FXML
	void initialize() {
		setButtons();
	}

	public void setButtons() {
		exitButton.setOnAction(event -> {
			// Create animation event to close the screen after 5 seconds
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished(event_2 -> {
				Stage stage = (Stage) popUpPane.getScene().getWindow();
				stage.close();
			});
			delay.play();
			
			previousWindow.clear_all();
		});

		noExitButton.setOnAction(event -> {
			// close this window
			// keep previous window (board)
			((Stage)noExitButton.getScene().getWindow()).close();
		});
	}
	
	public void setNoExitButton() {
		
	}

	public void setPreviousWindow(BoardControl boardControl) {
		// TODO Auto-generated method stub
		this.previousWindow = boardControl;
	}
}
