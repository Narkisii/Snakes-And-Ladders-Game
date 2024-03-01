package control;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameData;
import javafx.application.Application;

import javafx.util.Duration;

public class MenuScreenControl {

	@FXML
	private Text gameTitle;

	@FXML
	private Button button_start, button_questionWizard, button_History, quit, button_instructions;
    @FXML
    private AnchorPane Menu_Pane;

	private AnchorPane root;
	@FXML
	
	private static boolean first_start = true;
	public void initialize() {
		splash_Screen();
	}

	
	private void init() {		
       // System.getProperty("user.dir");

		button_start.setOnAction(event -> navigateTo("/view/SettingsView.fxml"));
		button_questionWizard.setOnAction(event -> navigateTo("/view/QuestionWizView.fxml"));
		button_History.setOnAction(event -> navigateTo("/view/HistoryView.fxml"));
		button_instructions.setOnAction(event -> navigateTo("/view/Instructions.fxml"));
		quit.setOnAction(event -> ((Stage) quit.getScene().getWindow()).close());
        Menu_Pane.getChildren().remove(root);
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
	
	private void splash_Screen() {
		if(first_start) {
        Image image = new Image("/view/Images/BackGround/Scorpion_SplashScreen.png");
        ImageView imageView = new ImageView(image);
        
        // Create a StackPane to hold the image
        root = new AnchorPane();
        root.getChildren().add(imageView);
        root.setPickOnBounds(false);
        root.prefWidthProperty().bind(Menu_Pane.widthProperty());
        root.prefHeightProperty().bind(Menu_Pane.heightProperty());

        imageView.fitWidthProperty().bind(root.widthProperty());
        imageView.fitHeightProperty().bind(root.heightProperty());

        Menu_Pane.getChildren().add(root);
		AnchorPane.setTopAnchor(root, 0.0);
		AnchorPane.setBottomAnchor(root, 0.0);
		AnchorPane.setLeftAnchor(root, 0.0);
		AnchorPane.setRightAnchor(root, 0.0);


        PauseTransition pt = new PauseTransition(Duration.millis(10));

        FadeTransition ft = new FadeTransition(Duration.millis(500), imageView);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        pt.setOnFinished(event -> ft.play());

        // After the fade out is finished, load your menu
        ft.setOnFinished(event -> init());
        
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ft.play());
        first_start = false;
		}else {
			init();
		}
        
	}
}