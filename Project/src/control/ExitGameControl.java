package control;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
			PauseTransition delay = new PauseTransition(Duration.millis(500));
			delay.setOnFinished(event_2 -> {
				Stage stage = (Stage) popUpPane.getScene().getWindow();
				stage.close();
			});
			delay.play();
			
			previousWindow.clear_all();
		});

		noExitButton.setOnAction(event -> {
			((Stage)noExitButton.getScene().getWindow()).close();
		});
	}
	
	public void setPreviousWindow(BoardControl boardControl) {
		// TODO Auto-generated method stub
		this.previousWindow = boardControl;
	}
}
