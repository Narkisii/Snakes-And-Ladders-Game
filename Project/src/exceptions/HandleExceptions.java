package exceptions;

import java.io.IOException;

import control.Error_Handler_Control;
import control.LogoutControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * @author Ariel Bubis 205735749
 *
 */
public class HandleExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void showException(Exception e, Object o, Window n) {
//		Alert alert = new Alert(AlertType.ERROR);
//		alert.setTitle(e.getClass().getName());
//		alert.setContentText("Error: \n" + e.getMessage());
//		alert.showAndWait();
		setPopUpStage();

		// Load the FXML file for the pop-up
		FXMLLoader loader = new FXMLLoader(o.getClass().getResource("/view/Error_view.fxml"));
		Parent root = null;
		try {
			root = loader.load(); // Load the FXML file
		} catch (IOException e1) {
			e.printStackTrace(); // Print the stack trace for debugging
		}

		// Get the controller for the pop-up
		Error_Handler_Control err_control = loader.getController();
		err_control.setMsg(e.getMessage());
		if (n != null)
			popupStage.initOwner(n);
		// Set the scene and show the stage
		Scene scene = new Scene(root);
		popupStage.setScene(scene);

		popupStage.show();
	}

	private static Stage popupStage;

	// This method sets the stage for the pop-up
	public static void setPopUpStage() {
		// If a pop-up is already open, do nothing
		if (popupStage != null && popupStage.isShowing()) {
			return;
		} else { // Create a new Stage for the pop-up
			popupStage = new Stage();
			popupStage.setResizable(false);
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
//			popupStage.setAlwaysOnTop(true); // Set always on top
			popupStage.initStyle(StageStyle.UNDECORATED);

		}
	}
}