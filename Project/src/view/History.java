package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class History extends Application {


	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Group root = new Group();
    	BorderPane outerPane = FXMLLoader.load(getClass().getResource("History.fxml"));
    	
    	root.getChildren().add(outerPane);
    	Scene scene = new Scene(root);
    	outerPane.prefWidthProperty().bind(scene.widthProperty());
    	outerPane.prefHeightProperty().bind(scene.heightProperty());
    	
    	stage.setWidth(901);
    	stage.setHeight(771);
        
    	stage.setScene(scene);
        stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
