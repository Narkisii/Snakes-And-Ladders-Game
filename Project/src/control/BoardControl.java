package control;

import model.Player;
import model.Question;
import model.Snake;
import model.Tile;

import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
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

	private static Stage popupStage;

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

	private Pane canvas;
	@FXML
	private BorderPane mainPain;

	@FXML
	private ImageView diceImage;

	@FXML
	private Button rollButton;
	// rolling dice function

	// Create a HashMap to store the rectangles
	HashMap<Integer, Rectangle> tile_Map;
	int test_Move_counter = 0;
	// Initialize the timer properties
	private IntegerProperty counter; // Initial time in seconds
	private Timeline timer;

	private GridPane grid; // The grid that will contain the tiles
	int set_turn_time;

	@FXML // This method is called by the FXMLLoader when initialization is complete

	void initialize() {
		// Create Board - getNumOfTiles() X getNumOfTiles() = Board
		// the Board constractor gets in Row and calculate the size
		// Set Players
//		for (Player player : GameData.getInstance().getplayer_list()) {
//			System.out.println(player.toString());
//		}
		set_turn_time = 45;
		tile_Map = new HashMap<>();
		canvas = new Pane();
		canvas.opacityProperty().set(0.7);
		counter = new SimpleIntegerProperty(set_turn_time);
		createCountDown();
		startCountDown();
		createTimer();
//		Pane startTile = (Pane) grid.lookup("#" + p.getCurrentP());
		grid = (GridPane) mainPain.lookup("#grid");
//		if (!GameData.getInstance().get_isIngame()) {
//		boolean isRedrawing = new Boolean(false);

		// Add a listener to the width and height properties of the scene
		mainPain.widthProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue.intValue() != 0) {
				System.out.println("widthProperty : observable " + observable.toString() + " oldValue " + oldValue
						+ " newValue " + newValue);
				canvas.getChildren().clear();
				mainPain.getChildren().remove(canvas);
				redrawLines();
				mainPain.getChildren().add(canvas);
			}
		});

		mainPain.heightProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue.intValue() != 0) {
				System.out.println("heightProperty : observable " + observable.toString() + " oldValue " + oldValue
						+ " newValue " + newValue);
				canvas.getChildren().clear();
				mainPain.getChildren().remove(canvas);
				drawLinesInSeparateThread();
				mainPain.getChildren().add(canvas);
			}
		});

//		}
		grid = createBoard(GameData.getInstance().getNumOfTiles());

		boardpane.getChildren().add(grid);
		drawLinesInSeparateThread();
		mainPain.getChildren().add(canvas);

		// Set the action for the return button
		return_btn.setOnAction(event -> navigateTo("/view/MenuScreenView.fxml"));

		turn_Lable.setTextFill(Color
				.web(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getColor()));
		turn_Lable.setText(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getName()
				+ "'s turn");
		rollButton.setOnAction(event -> roll(board.get_Dice_Result(),
				GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn())));

		for (Player p : GameData.getInstance().getplayer_list()) {
//			System.out.println("Adding:" + p.toString());
			initiate_Players(p);
		}

		GameData.getInstance().set_In_game(true);

		GameData.getInstance().init_board();
		board = GameData.getInstance().getBoard();
		board.generate_board_Objects();
		add_SpecialTiles();

	}

	private GridPane createBoard(int numTiles) {
		grid = new GridPane(); // Initialize the grid
		grid.setId("grid");
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
				tile.minHeight(0);
				tile.minWidth(0);
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
				label.prefHeightProperty().bind(grid.heightProperty().divide(numTiles));
				label.setAlignment(Pos.CENTER);
				label.minHeight(0);
				label.minWidth(0);

				// Create a new StackPane to hold the square and the label
				Pane stackPane = new StackPane();
				stackPane.getChildren().addAll(tile, label);
				GridPane.setRowIndex(stackPane, numTiles - 1 - i);
				GridPane.setColumnIndex(stackPane, column);
				stackPane.setId(String.valueOf(count));
				// Add the StackPane to the grid
				stackPane.setMinSize(0, 0);

				grid.add(stackPane, column, numTiles - 1 - i);
				// Increment the count

				count++;
			}

		}

		return grid;

	}

	private void add_SpecialTiles() {
		LinkedList<Tile> specialTiles = GameData.getInstance().getspecialTiles_list();
		for (Tile tile : specialTiles) {
			System.out.println("test");
			Pane startTile = (Pane) grid.lookup("#" + tile.getId());
			System.out.println("startTile " + startTile.getId());
			Image img = new Image(tile.get_Image());
			ImageView tile_img = new ImageView(img);
			System.out.println(startTile.getChildren().add(tile_img));
			tile_img.setFitHeight(90);
			tile_img.setFitWidth(90);
//			tile_img.fitHeightProperty().bind(startTile.heightProperty());
//			tile_img.fitWidthProperty().bind(startTile.widthProperty());

			tile_img.setPreserveRatio(false);
			tile_img.opacityProperty().set(0.8);
		}

	}

	private void clean_Tile(Player p) {
		LinkedList<Integer> placment = p.getPlacment_history();
		for (int i = placment.size() - 2; i >= 0; i--) {
			Pane startTile = (Pane) grid.lookup("#" + p.getPlacment_history().get(i));
			if (startTile.lookup("#VBox" + p.getID()) != null) {
				VBox playerbox = (VBox) startTile.lookup("#VBox" + p.getID());
				Label playerName = (Label) playerbox.lookup("#" + p.getID() + p.getName());
				ImageView img = (ImageView) playerbox.lookup("#Image" + p.getID());
//			System.out.println("clean_Tile old_pos: " + p.getPreviousStep() + "Player: " + p.getID());
				playerbox.getChildren().removeAll(playerName, img);
				startTile.getChildren().removeAll(playerbox);
			}
		}

	}

	private void move_Player(Player p) {
		checkwin(GameData.getInstance().getBoard().getGameEnd());
		if (p.getCurrentP() == p.getPreviousStep()) {
			return;
		}
		clean_Tile(p);
		Pane curr_Tile = (Pane) grid.lookup("#" + p.getCurrentP());
		VBox playerbox = (VBox) curr_Tile.lookup("#VBox" + p.getID());
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

		playerbox.getChildren().addAll(playerName, img);

		curr_Tile.getChildren().addAll(playerbox);

		check_Question(Integer.parseInt(curr_Tile.getId()));
	}

	private void check_Question(int curr_Tile) {
		// TODO Auto-generated method stub
		System.out.println("check_Question " + curr_Tile);
		Tile quetion_tile = board.is_question(curr_Tile);
		if (quetion_tile != null) {
			System.out.println("is a question");
			showQuestion();
		}
	}

	private void initiate_Players(Player p) {
		p.addStep(1);
		Pane new_pos_pane = (Pane) grid.lookup("#" + p.getCurrentP());
		VBox playerbox = (VBox) new_pos_pane.lookup("#VBox" + p.getID());
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

		playerbox.getChildren().addAll(playerName, img);

		new_pos_pane.getChildren().addAll(playerbox);

	}

	public void add_Ladders(int start, int end) {
		Random random = new Random();

		Colors[] colors = Colors.values();
		Colors randomColor = colors[random.nextInt(colors.length)];
		String color2 = randomColor.name(); // 50% transparent
		Color color = Color.web(color2);
		// mix the color
		canvas.setPickOnBounds(false);

		Pane startTile = (Pane) grid.lookup("#" + start);
		Pane endTile = (Pane) grid.lookup("#" + end);
		if (startTile == null || endTile == null) {
			return;
		}
		Point2D tileStart = startTile.localToScene(startTile.getWidth() / 2, startTile.getHeight() / 2);
		Point2D tileEnd = endTile.localToScene(endTile.getWidth() / 2, endTile.getHeight() / 2);
		double startX = tileStart.getX();
		double startY = tileStart.getY();
		double endX = tileEnd.getX();
		double endY = tileEnd.getY();
		double distance = tileStart.distance(tileEnd);
		double angle = Math.toDegrees(Math.atan2(startY - endY, startX - endX));
//		System.out.println(angle);
		Rectangle rectangle = new Rectangle();
		int temp = 40;
		LinearGradient gradient = new LinearGradient(0, temp, temp, temp, false, CycleMethod.REPEAT,
				new Stop(0.5, Color.TRANSPARENT), new Stop(0.5, color));
//		Image image = new Image("/view/Images/Ladders/GreenLadder.png");

		rectangle.setStroke(color); // change this to the color you want
		rectangle.setStrokeWidth(5); // change this to the color you want
		rectangle.getStrokeDashArray().add(5.0);
		int rect_width = 50;
		if (angle == 90 || angle == -90) {
			rectangle.setX(endX);
			rectangle.setY(endY);
			rectangle.setWidth(rect_width);
			rectangle.setHeight(distance); // set the height as you need
			gradient = new LinearGradient(temp, 0, temp, temp, false, CycleMethod.REPEAT,
					new Stop(0.5, Color.TRANSPARENT), new Stop(0.5, color));

		} else {
			if (angle < 90) {
//				System.out.println("angle<90");
				rectangle.setX(endX - startTile.getWidth() / 2);
				rectangle.setY((startY + endY) / 2);
				rectangle.setWidth(distance);
				rectangle.setHeight(rect_width); // set the height as you need
				rectangle.setRotate(angle);

			} else {
//				System.out.println("angle>90");
				rectangle.setX(startX - startTile.getWidth() / 2);
				rectangle.setY((startY + endY) / 2);
				rectangle.setWidth(distance);
				rectangle.setHeight(rect_width); // set the height as you need
				rectangle.setRotate(angle);
			}
		}
//	    ImagePattern radialGradient = new ImagePattern(image, temp, temp, distance, rect_width, false); 

		rectangle.setFill(gradient);
		canvas.getChildren().add(rectangle);
	}

	public void add_Snakes(int start, int end, Color color) {
		canvas.setPickOnBounds(false);

		// get the StackPane for the squares
//		Rectangle startTile = tile_Map.get(start);
//		Rectangle endTile = tile_Map.get(end);
		Pane startTile = (Pane) grid.lookup("#" + start);
		Pane endTile = (Pane) grid.lookup("#" + end);
		if (startTile == null || endTile == null) {
//			System.out.println("null on one of tiles???");
			return;
		}
		Point2D tileStart = startTile.localToScene(startTile.getWidth() / 2, startTile.getHeight() / 2);
		Point2D tileEnd = endTile.localToScene(endTile.getWidth() / 2, endTile.getHeight() / 2);

		double startX = tileEnd.getX();
		double startY = tileEnd.getY();
		double endX = tileStart.getX();
		double endY = tileStart.getY();

		double distance = tileStart.distance(tileEnd);
		double angle = Math.toDegrees(Math.atan2(startY - endY, startX - endX));

		Random rand = new Random();

		CubicCurve cubic = new CubicCurve();
		cubic.setStartX(startX);
		cubic.setStartY(startY);

//		int randomNumber = (rand.nextInt(11) - 5) * 10; 
		int randomNumber = (rand.nextInt(11) - 5) * 10;

		double controlX1 = startX - (distance + randomNumber);
		randomNumber = (rand.nextInt(11) - 5) * 10;
		double controlY1 = startY + randomNumber;
		randomNumber = (rand.nextInt(11) - 5) * 10;
//		randomNumber = (rand.nextInt(20) - 10) * 10; 
		double controlX2 = endX + (distance + randomNumber);
		randomNumber = (rand.nextInt(11) - 5) * 10;
		double controlY2 = endY + randomNumber;
		cubic.setControlX1(controlX1);
		cubic.setControlY1(controlY1);
		cubic.setControlX2(controlX2);
		cubic.setControlY2(controlY2);

		cubic.setEndX(endX);
		cubic.setEndY(endY);
		Image image = new Image("view/Images/Snakes/SnakeHead.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		imageView.setPreserveRatio(true);
//		double angle2 = Math.toDegrees(Math.atan2(startY - (startY-(distance/4))-Math.tan(angle), startX - (startX+Math.cos(angle)*200)));

		imageView.setX(endX - imageView.getFitWidth() / 2);
		imageView.setY(endY - imageView.getFitHeight() / 2);
		Colors[] colors = Colors.values();
		Colors randomColor = colors[rand.nextInt(colors.length)];
		String transparentColor = randomColor.name(); // 50% transparent
//		System.out.println("Start:" + start + "End: " + end + "Color: " + transparentColor);

		cubic.setStroke(color);
		cubic.setStrokeWidth(20);
		cubic.setFill(Color.TRANSPARENT);

		canvas.getChildren().addAll(cubic, imageView);

	}

	public void redrawLines() {
		for (Ladder l : GameData.getInstance().getLadders()) {
//			System.out.println("l.getStart() " + l.getStart() + " l.getEnd() " + l.getEnd());
			add_Ladders(l.getStart(), l.getEnd());
		}
		for (Snake s : GameData.getInstance().getSnake_list()) {
			System.out.println("l.getStart() " + s.getStart() + " l.getEnd() " + s.getEnd());
			add_Snakes(s.getStart(), s.getEnd(), s.getColor());
		}

	}

	public void drawLinesInSeparateThread() {

		Thread thread = new Thread(() -> {
			Platform.runLater(() -> {

				for (Ladder l : GameData.getInstance().getLadders()) {
//					System.out.println("l.getStart() " + l.getStart() + " l.getEnd() " + l.getEnd());
					add_Ladders(l.getStart(), l.getEnd());
				}

				for (Snake s : GameData.getInstance().getSnake_list()) {
//			System.out.println("l.getStart() " + l.getStart() + " l.getEnd() " + l.getEnd());
					add_Snakes(s.getStart(), s.getEnd(), s.getColor());
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
				roll(board.get_Dice_Result(),
						GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()));
//				timer.stop();
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
		counter.set(set_turn_time);
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
						if (dice == 7 || dice == 8 || dice == 9) {
							Question q = GameData.getInstance().get_Question(dice);
							showQuestion();
							
						} else {
							GameData.getInstance().getBoard().move(dice, player);
							move_Player(player);
						}
//						GameData.getInstance().getBoard().move(48, player);
//						move_Player(player);

						if (GameData.getInstance().getPlayerTurn() < GameData.getInstance().getplayer_list().size()
								- 1) {
							GameData.getInstance().setPlayerTurn(GameData.getInstance().getPlayerTurn() + 1);
						} else {
							GameData.getInstance().setPlayerTurn(0);
						}
						turn_Lable.setTextFill(Color.web(GameData.getInstance().getplayer_list()
								.get(GameData.getInstance().getPlayerTurn()).getColor()));
						turn_Lable.setText(GameData.getInstance().getplayer_list()
								.get(GameData.getInstance().getPlayerTurn()).getName() + "'s turn");

					}
				}
			}
		};
		animationTimer.start();
		startCountDown();
	}

	// Method to navigate to another screen
	private void navigateTo(String fxmlFile) {
		try {
			grid.getChildren().clear();
			Stage stage = (Stage) return_btn.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();

			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkwin(int gameEnd) {
		// TODO Auto-generated method stub
		if (gameEnd == 1) {
//			stopTimer();
			timeline.stop();
			timer.stop();
			
			popupStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WinScreen_view.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Load the FXML file for the pop-up
			winScreen_Controller controller = loader.getController();
			Player Player_won = GameData.getInstance().getplayer_list()
					.get(GameData.getInstance().getPlayerTurn());

			controller.setWinner(Player_won);

			Scene scene = new Scene(root);
//			controller.setPreviousWindow(this);
			// Set the scene and show the stage
			popupStage.setScene(scene);
			popupStage.show();
		}
	}

	void gameloop() {

	}

	void enable_action(int x, int y) {

	}

	public void showQuestion() {
		// TODO Auto-generated method stub
		popupStage = new Stage();

		// Load the FXML file for the pop-up
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameQuestion.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set the scene and show the stage
		Scene scene = new Scene(root);
		popupStage.setScene(scene);
		popupStage.show();
		startCountDown();
	}

}
