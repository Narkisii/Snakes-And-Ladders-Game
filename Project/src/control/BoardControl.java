package control;

import model.Player;
import model.Snake;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

import enums.Colors;
import javafx.geometry.Pos;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Board;
import model.GameData;
import model.Ladder;
import javafx.animation.AnimationTimer;
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

	private Board board;

	private Pane canvas = new Pane();
	@FXML
	private BorderPane mainPain;

	@FXML
	private ImageView diceImage;

	@FXML
	private Button rollButton;

	// rolling dice function
	@FXML

	// Create a HashMap to store the rectangles
	HashMap<Integer, Rectangle> tile_Map = new HashMap<>();
	int test_Move_counter = 0;
	// Initialize the timer properties
	private IntegerProperty counter = new SimpleIntegerProperty(60); // Initial time in seconds
	private Timeline timer;

	private GridPane grid; // The grid that will contain the tiles

	@FXML // This method is called by the FXMLLoader when initialization is complete

	void initialize() {
		// Create Board - getNumOfTiles() X getNumOfTiles() = Board
		// the Board constractor gets in Row and calculate the size
		// Set Players
		for (Player player : GameData.getInstance().getplayer_list()) {
			System.out.println(player.toString());
		}
		createCountDown();
		startCountDown();
		createTimer();
		grid = createBoard(GameData.getInstance().getNumOfTiles());
		boardpane.getChildren().add(grid);
		drawLinesInSeparateThread();
		mainPain.getChildren().add(canvas);

		// Set the action for the return button
		return_btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));
		turn_Lable.setText("Bree");
		rollButton.setOnAction(event -> roll(Board.get_Dice_Result(),
				GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn())));

		// Add a listener to the width and height properties of the scene
		mainPain.widthProperty().addListener((observable, oldValue, newValue) -> {
				canvas.getChildren().clear();
				mainPain.getChildren().remove(canvas);
				drawLinesInSeparateThread();
				mainPain.getChildren().add(canvas);
			
		});

		mainPain.heightProperty().addListener((observable, oldValue, newValue) -> {
				canvas.getChildren().clear();
				mainPain.getChildren().remove(canvas);
				drawLinesInSeparateThread();
				mainPain.getChildren().add(canvas);
			
		});
		for (Player p : GameData.getInstance().getplayer_list()) {
//			System.out.println("Adding:" + p.toString());
			initiate_Players(p);
		}
		board = GameData.getInstance().getBoard();

		board.generate_snakes_ladders();


	}

	private GridPane createBoard(int numTiles) {
		grid = new GridPane(); // Initialize the grid
		int count = 1; // Initialize the counter
		grid.prefWidthProperty().bind(boardpane.widthProperty());
		grid.prefHeightProperty().bind(boardpane.heightProperty());
		AnchorPane.setTopAnchor(grid, 0.0);
		AnchorPane.setBottomAnchor(grid, 0.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		// Make the grid always grow to fill available space
		GridPane.setVgrow(grid, Priority.ALWAYS);
		GridPane.setHgrow(grid, Priority.ALWAYS);

		// Loop to create the tiles
		for (int i = 0; i < numTiles; i++) {
			for (int j = 0; j < numTiles; j++) {
				// Determine the correct column based on the row
				int column = (i % 2 == 0) ? j : (numTiles - 1 - j);
				// Create a new square (Rectangle)

				Rectangle tile = new Rectangle();
				tile.setWidth(grid.widthProperty().divide(numTiles).doubleValue());
				tile.setHeight(grid.heightProperty().divide(numTiles).doubleValue());
//				System.out.println(grid.widthProperty().doubleValue());

				tile.widthProperty().bind(grid.widthProperty().divide(numTiles));
				tile.heightProperty().bind(grid.heightProperty().divide(numTiles));
				tile.setArcWidth(5);
				tile.setArcHeight(5);
				// Set the color of the square
				if (count % 2 == 0)
					tile.setFill(Color.web("#C1F2B0")); // Change this to the color you want
				else
					tile.setFill(Color.web("#65B741")); // Change this to the color you want

				// Set the position of the square
				tile.setX(column * tile.getWidth());
				tile.setY((numTiles - 1 - i) * tile.getHeight());

				// Add the square to the HashMap
				tile_Map.put(count, tile);
//				tile.setId(String.valueOf(count));

				// Create a new label with the current count
				Label label = new Label(String.valueOf(count));
				label.getStylesheets().add("/view/resources/Css/all_Style.css");
				label.getStyleClass().add("tile_Font");
				label.prefWidthProperty().bind(grid.widthProperty().divide(numTiles));
				label.prefHeightProperty().bind(grid.widthProperty().divide(numTiles));
				label.setAlignment(Pos.CENTER);

				// Create a new StackPane to hold the square and the label
				Pane stackPane = new StackPane();
				stackPane.getChildren().addAll(tile, label);
				GridPane.setRowIndex(stackPane, numTiles - 1 - i);
				GridPane.setColumnIndex(stackPane, column);
				stackPane.setId(String.valueOf(count));
				// Add the StackPane to the grid
				grid.add(stackPane, column, numTiles - 1 - i);
				// Increment the count

				count++;
			}

		}

		return grid;

	}

	private void clean_Tile(Player p) {
		Pane startTile = (Pane) grid.lookup("#" + p.getPreviousStep());

		VBox playerbox = (VBox) startTile.lookup("#VBox"+p.getID());
		Label playerName = (Label) playerbox.lookup("#" + p.getID() + p.getName());
		ImageView img = (ImageView) playerbox.lookup("#Image" + p.getID());

		if (playerbox != null) {
			
			System.out.println("clean_Tile old_pos: "+ p.getPreviousStep() + "Player: "+ p.getID());
			playerbox.getChildren().removeAll(playerName,img);
			startTile.getChildren().removeAll(playerbox);
		}
	}

	private void move_Player(Player p) {
		System.out.println("p.getCurrentP() "+p.getCurrentP()+" p.getPreviousStep() "+p.getPreviousStep());
		if (p.getCurrentP() == p.getPreviousStep()) {
			return;
		}
		clean_Tile(p);
//		System.out.println(
//				"initiate_Players - PlayerID: " + p.getID() + " New pos: " + p.getCurrentP() + " Old pos: " + old_pos);
		Pane startTile = (Pane) grid.lookup("#" + p.getCurrentP());
		VBox playerbox = (VBox) startTile.lookup("#VBox" + p.getID());
		Label playerName = null;
		ImageView img = null;
		if (playerbox == null) {
			playerbox = new VBox();
			playerbox.setId("VBox" + p.getID());
			playerName = new Label(p.getName());
			playerName.setTextFill(Color.web(p.getColor()));
			playerName.setId(String.valueOf(p.getID()) + p.getName());
			playerName.getStylesheets().add("/view/resources/Css/all_Style.css");
			playerName.getStyleClass().add("player_font");
			Image img_file = new Image(p.getToken());
			img = new ImageView(img_file);
			img.setId("Image" + p.getID());
			img.setFitHeight(50);
			img.setFitWidth(50);
			img.setPreserveRatio(false);
		} else {
			Pane oldParent = (Pane) playerbox.getParent();
			if (oldParent != null) {
				oldParent.getChildren().remove(playerbox);
			}
			playerName = (Label) playerbox.lookup("#" + p.getID() + p.getName());
			img = (ImageView) playerbox.lookup("#Image" + p.getID());
		}
//		if (old_pos != -1) {
//			clean_Tile(p, old_pos);
//		}
		playerbox.getChildren().addAll(playerName, img);

		startTile.getChildren().addAll(playerbox);
		System.out.println("END initiate_Players " + test_Move_counter);

	}

	@SuppressWarnings("unused")
	private void initiate_Players(Player p) {
//		if(p.getCurrentP() == old_pos) {
//			return;
//		}
		p.addStep(1);
//		System.out.println(
//				"initiate_Players - PlayerID: " + p.getID() + " New pos: " + p.getCurrentP() + " Old pos: " + old_pos);
		Pane new_pos_pane = (Pane) grid.lookup("#" + p.getCurrentP());
//		Pane old_pos_pane = (Pane) grid.lookup("#" + old_pos);
		VBox playerbox = (VBox) new_pos_pane.lookup("#VBox" + p.getID());
//		Pane oldParent = (Pane) playerbox.getParent();
//		if (oldParent != null) {
//			oldParent.getChildren().remove(playerbox);
//
//		}
		Label playerName = null;
		ImageView img = null;
		if (playerbox == null) {
			playerbox = new VBox();
			playerbox.setId("VBox" + p.getID());
			playerName = new Label(p.getName());
			playerName.setTextFill(Color.web(p.getColor()));
			playerName.setId(String.valueOf(p.getID()) + p.getName());
			playerName.getStylesheets().add("/view/resources/Css/all_Style.css");
			playerName.getStyleClass().add("player_font");
			Image img_file = new Image(p.getToken());
			img = new ImageView(img_file);
			img.setId("Image" + p.getID());
			img.setFitHeight(50);
			img.setFitWidth(50);
			img.setPreserveRatio(false);
		} else {
			playerName = (Label) playerbox.lookup("#" + p.getID() + p.getName());
			img = (ImageView) playerbox.lookup("#Image" + p.getID());
		}
//		if(old_pos_pane != null) {
//	        Pane oldParent = (Pane) playerbox.getParent();
//	        if (oldParent != null) {
//	            oldParent.getChildren().remove(playerbox);
//	        }
//		}

		playerbox.getChildren().addAll(playerName, img);

		new_pos_pane.getChildren().addAll(playerbox);
		System.out.println("END initiate_Players " + test_Move_counter);

	}

//	Label playerName = (Label) startTile.lookup("#" + p.getID() + p.getName());
//	ImageView img = (ImageView) startTile.lookup("Image"+p.getID());
//	if (playerName == null) {
//		playerName = new Label(p.getName());
//		playerName.setTextFill(Color.web(p.getColor()));
//		playerName.setId(String.valueOf(p.getID()) + p.getName());
//		playerName.getStylesheets().add("/view/resources/Css/all_Style.css");
//		playerName.getStyleClass().add("player_font");
//		Image img_file = new Image(p.getToken());
//		img = new ImageView(img_file);
//		img.setId("Image"+p.getID());
//		img.setFitHeight(50);
//		img.setFitWidth(50);
//		img.setPreserveRatio(false);
//	}
	public void add_Ladder_Snake(int start, int end) {
//		System.out.println("Start:" + start + "End: " + end);
		canvas.setPickOnBounds(false);

		// get the StackPane for the squares
//		Rectangle startTile = tile_Map.get(start);
//		Rectangle endTile = tile_Map.get(end);
		Pane startTile = (Pane) grid.lookup("#" + start);
		Pane endTile = (Pane) grid.lookup("#" + end);
		if (startTile == null || endTile == null) {
			System.out.println("null on one of tiles???");
			return;
		}
		Point2D tileStart = startTile.localToScene(startTile.getWidth() / 2, startTile.getHeight() / 2);
		Point2D tileEnd = endTile.localToScene(endTile.getWidth() / 2, endTile.getHeight() / 2);

		// get the center points of the rectangles
		double startX = tileStart.getX();
		double startY = tileStart.getY();
		double endX = tileEnd.getX();
		double endY = tileEnd.getY();
//        System.out.println("Tile: startX "+startX+" startY "+startY);

		double distance = tileStart.distance(tileEnd);
		double angle = Math.toDegrees(Math.atan2(startY - endY, startX - endX));
//		System.out.println(angle);

		Rectangle rectangle = new Rectangle();
		if (angle == 90 || angle == -90) {
			rectangle.setX(endX);
			rectangle.setY(endY);
			rectangle.setWidth(10);
			rectangle.setHeight(distance); // set the height as you need

		} else {
			if (angle < 90) {
//				System.out.println("angle<90");
				rectangle.setX(endX - startTile.getWidth() / 2);
				rectangle.setY((startY + endY) / 2);
				rectangle.setWidth(distance);
				rectangle.setHeight(10); // set the height as you need
				rectangle.setRotate(angle);

			} else {
//				System.out.println("angle>90");
				rectangle.setX(startX - startTile.getWidth() / 2);
				rectangle.setY((startY + endY) / 2);
				rectangle.setWidth(distance);
				rectangle.setHeight(10); // set the height as you need
				rectangle.setRotate(angle);
			}
		}
		Random rand = new Random();
		Colors[] colors = Colors.values();
		Colors randomColor = colors[rand.nextInt(colors.length)];
		String transparentColor = randomColor.name(); // 50% transparent

		rectangle.setStroke(Color.web(transparentColor)); // change this to the color you want
		rectangle.setStrokeWidth(10); // change this to the color you want

		canvas.getChildren().add(rectangle);
	}

	public void drawLinesInSeparateThread() {
		Thread thread = new Thread(() -> {
			Platform.runLater(() -> {
//				for (Ladder l : GameData.getInstance().getLadders()) {
////					System.out.println("l.getStart() " + l.getStart() + " l.getEnd() " + l.getEnd());
//					add_Ladder_Snake(l.getStart(), l.getEnd());
//				}
				for (Snake s : GameData.getInstance().getSnake_list()) {
//					System.out.println("l.getStart() " + l.getStart() + " l.getEnd() " + l.getEnd());
					add_Ladder_Snake(s.getStart(), s.getEnd());
				}

			});
		});
		thread.start();
	}

	private void createCountDown() {
		// Bind the time_Label to the counter property
		countDown_Label.textProperty().bind(counter.asString());
		// Create a timeline for the countdown
		timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			counter.set(counter.get() - 1);
			if (counter.get() == 0) {
				// Timer completed, stop the timer
				timer.stop();
			}
		}));
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

	private void roll(int dice, Player player) {
		rollButton.setDisable(true);
		final long[] frameCounter = { 0 };
		final Random random = new Random();
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (frameCounter[0]++ % 3 == 0) { // adjust the 6 to control the speed of the animation
					if (frameCounter[0] < 90) { // adjust the 90 to control the duration of the animation
						File file = new File("src/view/Images/dice/" + (random.nextInt(10)) + ".png");
						diceImage.setImage(new Image(file.toURI().toString()));
					} else {
						File file = new File("src/view/Images/dice/" + dice + ".png");
						diceImage.setImage(new Image(file.toURI().toString()));
						rollButton.setDisable(false);
						this.stop();
						GameData.getInstance().getBoard().move(dice, player);
//						for (Player p : GameData.getInstance().getplayer_list()) {
//							initiate_Players(p, old_post);
//						}
						move_Player(player);
						test_Move_counter++;

						if (GameData.getInstance().getPlayerTurn() < GameData.getInstance().getplayer_list().size()
								- 1) {
							GameData.getInstance().setPlayerTurn(GameData.getInstance().getPlayerTurn() + 1);
						} else {
							GameData.getInstance().setPlayerTurn(0);
						}

					}
				}
			}
		};
		animationTimer.start();
	}

	// Method to navigate to another screen
	private void navigateTo(String fxmlFile) {
		try {
			Stage stage = (Stage) return_btn.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();

			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void gameloop() {

	}

	void enable_action(int x, int y) {

	}

}
