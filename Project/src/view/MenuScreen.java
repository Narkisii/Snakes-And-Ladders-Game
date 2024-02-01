package view;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class MenuScreen extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // TODO Auto-generated method stub

    	Group root = new Group();
    	AnchorPane innerPane = FXMLLoader.load(getClass().getResource("MenuScreen.fxml"));
    	
    	root.getChildren().add(innerPane);
    	Scene scene = new Scene(root);
    	innerPane.prefWidthProperty().bind(scene.widthProperty());
    	innerPane.prefHeightProperty().bind(scene.heightProperty());
    	
    	stage.setWidth(901);
    	stage.setHeight(771);
        
    	stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}