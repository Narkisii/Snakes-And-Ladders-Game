package control;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameData;

public class MenuScreenControl {

	@FXML
	private Text gameTitle;

	@FXML
	private Button button_start, button_questionWizard, button_History, quit, button_instructions;

	@FXML
	public void initialize() {

		button_start.setOnAction(event -> navigateTo("/view/SettingsView.fxml"));
		button_questionWizard.setOnAction(event -> navigateTo("/view/QuestionWizView.fxml"));
		button_History.setOnAction(event -> navigateTo("/view/HistoryView.fxml"));
		button_instructions.setOnAction(event -> navigateTo("/view/Instructions.fxml"));
		quit.setOnAction(event -> ((Stage) quit.getScene().getWindow()).close());
	}

	private void navigateTo(String fxmlFile) {
		try {
			Stage stage = (Stage) button_start.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();
//			if (!GameData.getInstance().get_isIngame()) {
//				Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
//				stage.setScene(scene);
//			} else {
//				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/BoardView.fxml")), width,
//						height);
//				stage.setScene(scene);
//			}
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}