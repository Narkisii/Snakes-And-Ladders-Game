package exceptions;

import javax.lang.model.type.ErrorType;
import javax.swing.JOptionPane;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Ariel Bubis 205735749
 *
 */
public class HandleExceptions extends JOptionPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static void showException(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(e.getClass().getName());
		alert.setContentText("Error: \n" + e.getMessage());
		alert.showAndWait();

//		JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}