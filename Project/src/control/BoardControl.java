package control;


import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.GameData;
import javafx.animation.KeyFrame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Duration;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;



public class BoardControl {

    @FXML
    private ResourceBundle resources; // Resource bundle for localization
    
    @FXML // fx:id="boardpane"
    private AnchorPane boardpane; // The main pane that contains the grid
    
    @FXML
    private Button return_btn; // Button to return to the previous screen
    
    @FXML
    private Label turn_Lable;

    @FXML
    private Label countDown_Label;
    
    @FXML
    private Label timer_Label;
    
    private Integer timeSeconds = 0;
    
    private Timeline timeline;

 // Create a HashMap to store the rectangles
    HashMap<Integer, Rectangle> rectangleMap = new HashMap<>();
    
    
    
    // Initialize the timer properties
    private IntegerProperty counter = new SimpleIntegerProperty(60); // Initial time in seconds
    private Timeline timer;

    private GridPane grid; // The grid that will contain the tiles

    @FXML // This method is called by the FXMLLoader when initialization is complete
    
    void initialize() {
    	createCountDown();
    	startCountDown();
    	createTimer();
    	createBoard(GameData.getNumOfTiles());
    	makeALine(3,12);
    	
    	

      	// Set the action for the return button
     	return_btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
    	turn_Lable.setText("Bree");
    	
    }
    
     private void  createBoard(int numTiles) {
    	 grid = new GridPane(); // Initialize the grid
     	int count = 1; // Initialize the counter

     // Loop to create the tiles
     	for (int i = 0; i < numTiles; i++) {
     	    for (int j = 0; j < numTiles; j++) {
     	        // Determine the correct column based on the row
     	        int column = (i % 2 == 0) ? j : (numTiles - 1 - j);

     	        // Create a new square (Rectangle)
     	        Rectangle square = new Rectangle();
     	        square.widthProperty().bind(grid.widthProperty().divide(numTiles));
     	        square.heightProperty().bind(grid.heightProperty().divide(numTiles));

     	        // Set the color of the square
     	        square.setFill(Color.GREEN); // Change this to the color you want

     	        // Set the position of the square
     	        square.setX(column * square.getWidth());
     	        square.setY((numTiles - 1 - i) * square.getHeight());

     	        // Add the square to the HashMap
     	        rectangleMap.put(count, square);

     	        // Create a new label with the current count
     	        Label label = new Label(String.valueOf(count));
     	        label.setFont(new Font("Arial", 20)); // Set the font size to 20

     	        // Create a new StackPane to hold the square and the label
     	        StackPane stackPane = new StackPane();
     	        stackPane.getChildren().addAll(square, label);

     	        // Add the StackPane to the grid
     	        grid.add(stackPane, column, numTiles - 1 - i);

     	        // Increment the count
     	        count++;
     	    }
     	}

     	// Bind the size of the grid to the size of the boardpane
     	grid.prefWidthProperty().bind(boardpane.widthProperty());
     	grid.prefHeightProperty().bind(boardpane.heightProperty());

     	// Make the grid always grow to fill available space
     	GridPane.setVgrow(grid, Priority.ALWAYS);
     	GridPane.setHgrow(grid, Priority.ALWAYS);

     	// Add the grid to the boardpane
     	boardpane.getChildren().add(grid);

     	// Make the grid always fill the boardpane
     	AnchorPane.setTopAnchor(grid, 0.0);
     	AnchorPane.setBottomAnchor(grid, 0.0);
     	AnchorPane.setLeftAnchor(grid, 0.0);
     	AnchorPane.setRightAnchor(grid, 0.0); 
     }
     
     

    

     public void makeALine(int start, int end) {
    	 System.out.println("sad");
         // get the StackPane for the squares
         StackPane startPane = (StackPane) grid.getChildren().get(start - 1);
         StackPane endPane = (StackPane) grid.getChildren().get(end - 1);

         // get the center points of the rectangles
         double startX = startPane.getLayoutX() + startPane.getWidth() / 2;
         double startY = startPane.getLayoutY() + startPane.getHeight() / 2;
         double endX = endPane.getLayoutX() + endPane.getWidth() / 2;
         double endY = endPane.getLayoutY() + endPane.getHeight() / 2;

         // create a new line from start to end
         Line line = new Line(startX, startY, endX, endY);

         // set the color of the line
         line.setStroke(Color.RED); // change this to the color you want

         // add the line to the grid
         grid.getChildren().add(line);
     }


     private void createCountDown()
     {
    	  // Bind the time_Label to the counter property
         countDown_Label.textProperty().bind(counter.asString());

         // Create a timeline for the countdown
         timer = new Timeline(
             new KeyFrame(Duration.seconds(1), e -> {
                 counter.set(counter.get() - 1);
                 if (counter.get() == 0) {
                     // Timer completed, stop the timer
                     timer.stop();
                 }
             })
         );
         timer.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
     }
    

    private void createTimer() {
    	timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds++;
                // update timerLabel
                timer_Label.setText(timeSeconds.toString());
            }
        }));
        timeline.playFromStart();
    
    
    }
    private void startCountDown() {
	       timer.play();
	    }
	
	   
    private void stopTimer() {
	        timer.stop();
	    }
    
    
    // Method to get the number of tiles based on the difficulty
    private int getNumOfTiles() {
        int numTiles = 0;
        String diff = GameData.getDifficulty();
        switch (diff.toLowerCase()) {
        case "easy":
            numTiles = 7;
            break;
        case "medium":
            numTiles = 10;
            break;
        case "hard":
            numTiles = 13;
            break;
        default:        
        }
        return numTiles;
    }

    // Method to navigate to another screen
    private void navigateTo(String fxmlFile) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)));
            Stage stage = (Stage) return_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

	void gameloop(){
    	
    }
    
    void enable_action(int x, int y) {
    	
    }
}



