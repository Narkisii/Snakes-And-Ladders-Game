package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuScreen extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("MenuScreen.fxml"));
		Scene scene = new Scene(root);

		BorderPane outerPane = (BorderPane) root;
		AnchorPane innerAnchorPane = (AnchorPane) outerPane.getCenter(); // Access the inner AnchorPane
		
		outerPane.setCenter(innerAnchorPane);
		BorderPane.setAlignment(innerAnchorPane, Pos.CENTER);

		// Set constraints to center the AnchorPane in the Scene

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
