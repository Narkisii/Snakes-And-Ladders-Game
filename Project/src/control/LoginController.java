package control;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    public void initialize() {
    	loginButton.setDisable(true);
    	ChangeListener<String> textFieldListener = 
    }
    /*
    // Method to initialize the controller
    public void initialize() {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'Login.fxml'.";

        // Load the image for moving around
        Image movingImageSrc = new Image(getClass().getResourceAsStream("/view/Images/BackGround/1.png"));
        movingImage.setImage(movingImageSrc);
        
     // Load the image for moving around
        Image movingImageSrc1 = new Image(getClass().getResourceAsStream("/view/Images/BackGround/2.png"));
        movingImage.setImage(movingImageSrc1);

        // Call method to animate the movement of both images
        animateMovingImage(movingImage, 300, 200); // animate movingImage
        animateMovingImage(movingImage1, -300, -200); // animate movingImage1 in mirrored way
    }

    // Method to animate the movement of an image
    private void animateMovingImage(ImageView imageView, double toX, double toY) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(3), imageView);
        transition.setFromX(0);
        transition.setFromY(0);
        transition.setToX(toX); // Destination X coordinate
        transition.setToY(toY); // Destination Y coordinate
        transition.setCycleCount(TranslateTransition.INDEFINITE); // Repeat indefinitely
        transition.setAutoReverse(true); // Reverse direction on completion
        transition.play(); // Start the animation
    }
    */
}


