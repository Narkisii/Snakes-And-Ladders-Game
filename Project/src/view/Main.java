package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MenuScreenView.fxml"));
//		Parent root = FXMLLoader.load(getClass().getResource("AdminSettings.fxml"));

		Scene scene = new Scene(root);

		primaryStage.getIcons().add(new Image("/view/Images/icon/icon.png"));
		//	    primaryStage.setAlwaysOnTop(true);
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(720);
		primaryStage.setMinWidth(1280);
		primaryStage.setTitle("Snakes And Ladder");
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
