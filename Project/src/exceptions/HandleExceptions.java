package exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Ariel Bubis 205735749
 *
 */
public class HandleExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static void showException(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(e.getClass().getName());
		alert.setContentText("Error: \n" + e.getMessage());
		alert.showAndWait();

	}
	
}