package control;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Error_Handler_Control {

	@FXML
	private AnchorPane bottomAnchorPane;

	@FXML
	private Label error_msg;

	@FXML
	private Button exit_btn;

	@FXML
	private Pane leftPane;

	@FXML
	private Label loginLabel;

	@FXML
	private BorderPane mainBorderPane;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private Pane rightPane;

	@FXML
	private AnchorPane topAnchorPane;

	@FXML
	private ImageView image_2;

	@FXML
	private ImageView image_1;

	@FXML
	void initialize() {
		exit_btn.setDisable(true);
		image_1.setImage(new Image("view/Images/error.png"));
		image_2.setImage(new Image("view/Images/error.png"));
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.setOnFinished(e -> exit_btn.setDisable(false));
		pause.play();
		exit_btn.setOnAction(event -> {
			Stage stage = (Stage) mainPane.getScene().getWindow();
			stage.close();
		});

		exit_btn.setOnKeyPressed(event -> {
			exit_btn.fire();
		});

	}

	public void setMsg(String e) {
		if (e.isEmpty()) {
			error_msg.setText("Error??");
		}
		error_msg.setText(e);

	}

}
