package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Screen");

        // Create buttons
        Button startButton = new Button("Start");
        Button questionWizardButton = new Button("Question Wizard");
        Button historyButton = new Button("History");

        // Create layout and add buttons
        VBox vbox = new VBox(startButton, questionWizardButton, historyButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        // Create scene and add layout
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
