package control;


import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {

	@FXML
	private HBox buttonHBox;

	@FXML
	private Button loginButton;

	@FXML
	private Button cancelButton;

	@FXML
	private VBox formVBox;

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private ImageView movingImage; // New ImageView for the moving image
	@FXML
	private ImageView movingImage1; // New ImageView for the moving image
	
	private QuestionWizControl previousWindow;
	
	private static boolean status;

	public void initialize() {
		status = false;
		
		Pane statusPane = new Pane();
		statusPane.setVisible(false);

		Label statusLbl = new Label("");
		statusLbl.setVisible(false);
		
		statusPane.getChildren().add(statusLbl);
		statusLbl.setAlignment(Pos.CENTER);
		formVBox.getChildren().add(statusPane);
		
		loginButton.setDisable(true); // disable button at initialize 

		// listener that will keep log in button disabled while the text fields are empty 
		ChangeListener<String> textFieldListener = (observable, oldValue, newValue) -> {
			boolean isEmpty = usernameField.getText().isEmpty() || passwordField.getText().isEmpty();
			loginButton.setDisable(isEmpty);
		};
		
		// add listeners to the text field 
		usernameField.textProperty().addListener(textFieldListener);
		passwordField.textProperty().addListener(textFieldListener);
		
		// add listener to login button 
		loginButton.setOnAction(event -> {
	        String nameInput = usernameField.getText().toLowerCase();
	        String passInputString = passwordField.getText().toLowerCase();
	        
	        // check login details 
	        if (nameInput.equals("admin") && passInputString.equals("admin")) {
	            status = true;
	            statusLbl.setVisible(true);
	            statusPane.setVisible(true);
	            statusLbl.setText("Login successful");
	            
	            previousWindow.setAdmin(status);
	            previousWindow.disableAdminControls(false);
	            
	            PauseTransition delay = new PauseTransition(Duration.seconds(2));
	            delay.setOnFinished( event_2 -> {
	                // close the screen
	                // assuming you have a reference to the current stage
	                Stage stage = (Stage) mainPane.getScene().getWindow();
	                stage.close();
	            });
	            
	            // make the screen wait for 5 seconds then close it
	            delay.play();
	            return;
	        } else {
	            statusLbl.setText("Invalid credentials");
	        }	        
	    });	
	}
	
	public void setPreviousWindow(QuestionWizControl questionWizControl2) {
		// TODO Auto-generated method stub
		this.previousWindow = questionWizControl2;
	}

//	public boolean getLoginStatus() {
//		return status;
//	}
	
	public QuestionWizControl getPreviousWindow() {
		return previousWindow;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	/*
	 * // Method to initialize the controller public void initialize() { assert
	 * mainPane != null :
	 * "fx:id=\"mainPane\" was not injected: check your FXML file 'Login.fxml'.";
	 * 
	 * // Load the image for moving around Image movingImageSrc = new
	 * Image(getClass().getResourceAsStream("/view/Images/BackGround/1.png"));
	 * movingImage.setImage(movingImageSrc);
	 * 
	 * // Load the image for moving around Image movingImageSrc1 = new
	 * Image(getClass().getResourceAsStream("/view/Images/BackGround/2.png"));
	 * movingImage.setImage(movingImageSrc1);
	 * 
	 * // Call method to animate the movement of both images
	 * animateMovingImage(movingImage, 300, 200); // animate movingImage
	 * animateMovingImage(movingImage1, -300, -200); // animate movingImage1 in
	 * mirrored way }
	 * 
	 * // Method to animate the movement of an image private void
	 * animateMovingImage(ImageView imageView, double toX, double toY) {
	 * TranslateTransition transition = new TranslateTransition(Duration.seconds(3),
	 * imageView); transition.setFromX(0); transition.setFromY(0);
	 * transition.setToX(toX); // Destination X coordinate transition.setToY(toY);
	 * // Destination Y coordinate
	 * transition.setCycleCount(TranslateTransition.INDEFINITE); // Repeat
	 * indefinitely transition.setAutoReverse(true); // Reverse direction on
	 * completion transition.play(); // Start the animation }
	 */
}
