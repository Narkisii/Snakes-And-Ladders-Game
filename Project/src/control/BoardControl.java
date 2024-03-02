package control;

import model.Player;
import model.Question;
import model.Snake;
import model.SoundManager;
import model.Tile;
import model.cpu_Player;

import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Intrefaces.GameEventObserver;
import Intrefaces.GameEventSubject;
import enums.Colors;
import enums.GameEvent;
import javafx.geometry.Pos;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
import javafx.scene.layout.Region;
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
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.Board;
import model.GameData;
import model.Ladder;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Duration;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BoardControl implements GameEventSubject {

	private Stage popupStage;

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

	@FXML
	private ImageView pTurn_image;

	// Create a HashMap to store the rectangles
//	private HashMap<Integer, Rectangle> tile_Map;

	// Initialize the timer properties
	private IntegerProperty counter; // Initial time in seconds
	private Timeline timer;

	private GridPane grid; // The grid that will contain the tiles

	private Group group;

	private ArrayList<VBox> players_VBox_Container_list;

	private int gameEnd_var;

	private List<GameEventObserver> observers = new ArrayList<>(); // observer list

	// Set Count down of each turn
	int set_turn_time = 45;

	private int flagSong = 0;

	private Clip boardSongClip;

	// OBSERVER METHODS
	@Override
	public void attach(GameEventObserver observer) {
		observers.add(observer);
	}

	@Override
	public void detach(GameEventObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(GameEvent event) {
		for (GameEventObserver observer : observers) {
			observer.onEventTriggered(event);
		}
	}
	// END

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		// Create Board - getNumOfTiles() X getNumOfTiles() = Board
		// the Board constractor gets in Row and calculate the size

		// Set Players
//		for (Player player : GameData.getInstance().getplayer_list()) {
//			System.out.println(player.toString());
//		}
		// STOP THEME SONG
		MenuScreenControl.stopThemeSong();
		themeSong();
		/**********************/
		players_VBox_Container_list = new ArrayList<VBox>();// VBoxes of the player tokens
//		tile_Map = new HashMap<>();
		canvas = new Pane();// Snake and ladders are drawn here separately
		canvas.opacityProperty().set(0.7);
		counter = new SimpleIntegerProperty(set_turn_time);
		/**********************/

		createCountDown();
		startCountDown();
		createTimer();
		/**********************/

//		grid = (GridPane) mainPain.lookup("#grid");

		// Add a listener to the width and height properties of the scene
		mainPain.widthProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue.intValue() != 0) {
//				System.out.println("widthProperty : observable " + observable.toString() + " oldValue " + oldValue
//						+ " newValue " + newValue);
				canvas.getChildren().clear();
				mainPain.getChildren().remove(canvas);
				redrawLines();
				mainPain.getChildren().add(canvas);
				add_SpecialTiles();
			}
		});

		mainPain.heightProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue.intValue() != 0) {
//				System.out.println("heightProperty : observable " + observable.toString() + " oldValue " + oldValue
//						+ " newValue " + newValue);
				canvas.getChildren().clear();
				mainPain.getChildren().remove(canvas);
				drawBoardObjectsInSeparateThread();
				mainPain.getChildren().add(canvas);
				add_SpecialTiles();

			}
		});

//		}
		grid = createBoard(GameData.getInstance().getNumOfTiles());

		boardpane.getChildren().add(grid);
		drawBoardObjectsInSeparateThread();

		mainPain.getChildren().add(canvas);
		// Set the action for the return button
		return_btn.setOnAction(event -> clear_all());

		turn_Lable.setTextFill(Color
				.web(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getColor()));
		turn_Lable.setText(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getName()
				+ "'s turn");
		Image img = new Image(
				GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getToken());
		pTurn_image.setImage(img);
		rollButton.setOnAction(event -> roll(board.get_Dice_Result(),
				GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn())));

		GameData.getInstance().init_board();
		board = GameData.getInstance().getBoard();
		board.generate_board_Objects();
//		add_SpecialTiles();

		// attaching observer
		SoundManager soundManager = new SoundManager();
		board.attach(soundManager);

	}

	// Create the gridpane of the board with the initialized tiles and thiers id
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
//				tile_Map.put(count, tile);
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
				stackPane.toBack();
				// Increment the count

				count++;
			}

		}
		return grid;

	}

	// add all special tiles (Surprise, question and red snake)
	private void add_SpecialTiles() {
		LinkedList<Tile> specialTiles = GameData.getInstance().getspecialTiles_list();
		for (Tile tile : specialTiles) {
			Pane startTile = (Pane) grid.lookup("#" + tile.getId());
			ImageView tile_img = (ImageView) startTile.lookup("#image" + tile.getId());
			if (tile_img == null) {
				Image img = new Image(tile.get_Image());
				tile_img = new ImageView(img);
				tile_img.setId("image" + tile.getId());
				tile_img.setPreserveRatio(true);
				tile_img.opacityProperty().set(0.8);
				tile_img.fitHeightProperty().bind(startTile.heightProperty().multiply(0.8));
				tile_img.fitWidthProperty().bind(startTile.widthProperty().multiply(0.8));
				startTile.getChildren().add(tile_img);
			}
			tile_img.fitHeightProperty().bind(startTile.heightProperty().multiply(0.8));
			tile_img.fitWidthProperty().bind(startTile.widthProperty().multiply(0.8));
		}
	}

	private void checktile_type(int tile_num, Player p) {
		// Check if the tile is adding more steps to the players
		Tile tile = board.getTile(tile_num);
		if (tile.getType() != 0 && tile.getType() != 4) {
			board.activateTile(tile_num, p);
			move_Player(-5, p);
		}
		if (tile.getType() == 4) {
//			System.out.println("check_Question " + curr_Tile);
			Tile quetion_tile = board.is_question(tile_num);
			if (quetion_tile != null) {
				showQuestion(board.getTile(tile_num).getQuestion(), p);
			}
		}
		if(checkwin(GameData.getInstance().getBoard().getGameEnd())) {
			return;
		}

		if (tile.getType() == 0) {
//			GameData.getInstance().next_turn();
//			turn_Lable.setTextFill(Color.web(GameData.getInstance().getplayer_list()
//					.get(GameData.getInstance().getPlayerTurn()).getColor()));
//			turn_Lable.setText(GameData.getInstance().getplayer_list()
//					.get(GameData.getInstance().getPlayerTurn()).getName() + "'s turn");
//			Image token = new Image(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getToken());
//			pTurn_image.setImage(token);
			next_Turn();
		}
	}

	// move player
	public void move_Player(int dice, Player p) {

//		board.notifyObservers(GameEvent.PLAYER_HIT_LADDER);//check observer

		if (dice != 0 && dice != -5) {
//			System.out.println(dice);
			GameData.getInstance().getBoard().move(dice, p);
		}
//		System.out.println("Boarcontrol move_Player " + p.toString());
//		System.out.println(p.getPlacment_history());

//		if(checkwin(GameData.getInstance().getBoard().getGameEnd())) {
//			return;
//		}
//		checkwin(GameData.getInstance().getBoard().getGameEnd());
		if (p.getCurrentP() == p.getPreviousStep()) {
//			GameData.getInstance().next_turn();
//			turn_Lable.setTextFill(Color.web(GameData.getInstance().getplayer_list()
//					.get(GameData.getInstance().getPlayerTurn()).getColor()));
//			turn_Lable.setText(GameData.getInstance().getplayer_list()
//					.get(GameData.getInstance().getPlayerTurn()).getName() + "'s turn");
//			Image token = new Image(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getToken());
//			pTurn_image.setImage(token);
			next_Turn();
			return;
		}
		Pane curr_Tile = (Pane) grid.lookup("#" + p.getCurrentP());
		Pane prev_tile = (Pane) grid.lookup("#" + p.getPreviousStep());
		VBox playerbox = players_VBox_Container_list.get(p.getID() - 1);
		if (dice != 0) {
			animate(playerbox, prev_tile, curr_Tile, p);
		} else {
			next_Turn();
		}
	}

	private void next_Turn() {
		
		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(event -> {
			GameData.getInstance().next_turn();
			turn_Lable.setTextFill(Color.web(
					GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getColor()));
			turn_Lable.setText(
					GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getName()
							+ "'s turn");
			Image token = new Image(
					GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getToken());
			pTurn_image.setImage(token);
			PauseTransition pauseForCPUCheck = new PauseTransition(Duration.millis(500));
			pauseForCPUCheck.setOnFinished(eventForCPUCheck -> {
				
				checkCPU(); // Call the checkCPU function here
			});
			pauseForCPUCheck.play();
			startCountDown();
			board.notifyObservers(GameEvent.PLAYER_TURN);//check observer

		});
		pause.play();

	}

	private void checkCPU() {
		// TODO Auto-generated method stub
		System.out.println("checkCPU"
				+ GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getClass()
						.getName()
				+ "Name: "
				+ GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getName());
		if (GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getClass().getName()
				.equals("model.cpu_Player")) {
			cpu_Player cpu_p = (cpu_Player) GameData.getInstance().getplayer_list()
					.get(GameData.getInstance().getPlayerTurn());
			if(GameData.getInstance().getBoard().getGameEnd() != 1) {
				CommandInvoker invoker = new CommandInvoker();
//				invoker.addCommand(new DelayCommand(2));
				invoker.addCommand(new RollDiceCommand((cpu_Player) cpu_p));
				invoker.executeCommands();
			}
		} else {
			rollButton.setDisable(false);
		}
	}

	private void animate(VBox playerbox, Pane start, Pane end, Player p) {
		board.notifyObservers(GameEvent.PLAYER_MOVE);// check observer

		if (gameEnd_var != 1)
//			startCountDown();

			if (playerbox == null || start == null || end == null || p == null) {
				return;
			}
//		if(p.getCurrentP() == Integer.valueOf(end.getId())) {
//			return;
//		}
		Point2D tileEnd = end.localToScene(end.getWidth() / 2, end.getHeight() / 2);
		Point2D prevTile = start.localToScene(start.getWidth() / 2, start.getHeight() / 2);
		double startX = tileEnd.getX();
		double startY = tileEnd.getY();
		double startX_prev = prevTile.getX();
		double startY_prev = prevTile.getY();
		int curr_P = p.getCurrentP();

		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), playerbox);

		// Set the end position (the new tile)
		tt.setToX(startX - startX_prev);
		tt.setToY(startY - startY_prev);
		// Play Animation
		tt.play();
		tt.setOnFinished(event -> {
			System.out.println(end.getId());
			System.out.println("prev_tile" + start.getId());
			playerbox.setTranslateX(0);
			playerbox.setTranslateY(0);
			end.getChildren().addAll(playerbox);
			checktile_type(p.getCurrentP(), p);
		});
	}

	// initiate the players tokens
	private void initiate_Players(Player p) {
//		p.addStep(1);
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
//			img.setFitHeight(image_size);
//			img.setFitWidth(image_size);
			img.setPreserveRatio(true);
			if (GameData.getInstance().getDifficulty().equals("Hard")) {
				playerbox.getChildren().addAll(img);
			} else {
				playerbox.getChildren().addAll(playerName, img);
			}
			players_VBox_Container_list.add(playerbox);
			new_pos_pane.getChildren().addAll(playerbox);
			if (p instanceof model.cpu_Player) {
				((cpu_Player) p).set_board_controll(this);
			}
		} else {
			playerName = (Label) playerbox.lookup("#" + p.getID() + p.getName());
			img = (ImageView) playerbox.lookup("#Image" + p.getID());
		}
		img.fitHeightProperty().bind(new_pos_pane.heightProperty().multiply(0.7));
		img.fitWidthProperty().bind(new_pos_pane.widthProperty().multiply(0.7));

	}

	public void add_GameElement(int start, int end, Object element) {
		GameElementFactory factory = new GameElementFactory(canvas);
		canvas.setPickOnBounds(false);
//		System.out.println("add_GameElement: " + element.getClass().getName() + " Start: " + start + " End" + end);

		Pane startTile = (Pane) grid.lookup("#" + start);
		Pane endTile = (Pane) grid.lookup("#" + end);
		if (startTile == null || endTile == null) {
//			System.out.println("null on one of tiles???");
			return;
		}
		// Grab the exact point of the tiles local to the grid, basically grabs the
		// exact point from the board
		Point2D tileStart = startTile.localToScene(startTile.getWidth() / 2, startTile.getHeight() / 2);
		Point2D tileEnd = endTile.localToScene(endTile.getWidth() / 2, endTile.getHeight() / 2);

		// distance between tiles
		double distance = tileStart.distance(tileEnd);

		// Check what kind of Object we have, is it a snake or a ladder and use factory
		// to create the object to draw
		if (element instanceof Snake) { // Add the snake to the game
			// Get the start x and y of the tile, get the end tile x and y
			double startX = tileEnd.getX();
			double startY = tileEnd.getY();
			double endX = tileStart.getX();
			double endY = tileStart.getY();

			GameElement snake = factory.getGameElement("SNAKE");
			snake.set_Color(((Snake) element).getColor());

			snake.add(startX, startY, endX, endY, distance);

		} else if (element instanceof Ladder) { // Add the ladder to the game
			// Get the start x and y of the tile, get the end tile x and y
			double startX = tileStart.getX();
			double startY = tileStart.getY();
			double endX = tileEnd.getX();
			double endY = tileEnd.getY();

			GameElement ladder = factory.getGameElement("LADDER");
			ladder.set_Tile(startTile);
			ladder.add(startX, startY, endX, endY, distance);
		}

	}

	public void redrawLines() {
		for (Ladder l : GameData.getInstance().getLadders()) {
//			System.out.println("l.getStart() " + l.getStart() + " l.getEnd() " + l.getEnd());
//			add_Ladders(l.getStart(), l.getEnd());
			add_GameElement(l.getStart(), l.getEnd(), l);
		}
		for (Snake s : GameData.getInstance().getSnake_list()) {
//			System.out.println("l.getStart() " + s.getStart() + " l.getEnd() " + s.getEnd());
//			add_Snakes(s.getStart(), s.getEnd(), s.getColor());
			add_GameElement(s.getStart(), s.getEnd(), s);
		}
//		add_SpecialTiles();
	}

	public void drawBoardObjectsInSeparateThread() {

		Thread thread = new Thread(() -> {
			Platform.runLater(() -> {
				for (Player p : GameData.getInstance().getplayer_list()) {
//					System.out.println("Adding:" + p.toString());
					initiate_Players(p);
				}

				for (Ladder l : GameData.getInstance().getLadders()) {
//					System.out.println("l.getStart() " + l.getStart() + " l.getEnd() " + l.getEnd());
					add_GameElement(l.getStart(), l.getEnd(), l);
				}

				for (Snake s : GameData.getInstance().getSnake_list()) {
//			System.out.println("l.getStart() " + l.getStart() + " l.getEnd() " + l.getEnd());
					add_GameElement(s.getStart(), s.getEnd(), s);
				}
				add_SpecialTiles();

				for (VBox playerbox : players_VBox_Container_list) {
					Pane new_pos_pane = (Pane) grid.lookup("#" + playerbox.getId());
					playerbox.prefHeightProperty().bind(new_pos_pane.heightProperty().multiply(0.8));
					playerbox.prefWidthProperty().bind(new_pos_pane.widthProperty().multiply(0.8));
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
			if (counter.get() == 0) {// Skip player
//				roll(board.get_Dice_Result(),
//						GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()));
				rollButton.setDisable(true);
//				board.notifyObservers(GameEvent.PLAYER_MISSES_TURN);// obserevr for missed turn
				turn_Lable.setText("Missed your turn!!");
				turn_Lable.setTextFill(Color.RED);
				next_Turn();
//				turn_Lable.setTextFill(null);

			}
			if (counter.get() < 0) {
				counter.set(0);
			}
//			if (counter.get() == 40) {//In the mean time this is what makes the CPU run, we should do a better job with that
//				
////				System.out.println(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn())
////						.getClass().getName());
//				
//				if (GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getClass()
//						.getName().equals("model.cpu_Player")) {
//					cpu_Player cpu_p = (cpu_Player) GameData.getInstance().getplayer_list()
//							.get(GameData.getInstance().getPlayerTurn());
//					CommandInvoker invoker = new CommandInvoker();
//					invoker.addCommand(new RollDiceCommand((cpu_Player) cpu_p));
//					invoker.executeCommands();
//				}
////				timer.stop();
//			}

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

	public void startCountDown() {
		counter.set(set_turn_time);
		timer.play();
	}

	private void stopTimer() {
		timer.stop();
	}

	public void roll(int dice, Player player) {
		board.notifyObservers(GameEvent.DICE_ROLL);// obserevr for roll dice
		rollButton.setDisable(true);
		final long[] frameCounter = { 0 };
		final Random random = new Random();
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				String path = "view/Images/dice/";
				if (frameCounter[0]++ % 3 == 0) { // adjust the 6 to control the speed of the animation
					if (frameCounter[0] < 50) { // adjust the 90 to control the duration of the animation
						try {
							Image img = new Image(path + (random.nextInt(10)) + ".png");
							diceImage.setImage(img);
						} catch (Exception e) {
							// TODO: handle exception
							path = "Images/dice/";
							Image img = new Image(path + (random.nextInt(10)) + ".png");
							diceImage.setImage(img);
						}
					} else {
						Image img = new Image(path + dice + ".png");
						diceImage.setImage(img);
//						rollButton.setDisable(false);
						this.stop();
						if (dice == 7 || dice == 8 || dice == 9) {
							
//							Question q = GameData.getInstance().get_Question(dice);
//							showQuestion(q, player);
							
							move_Player(dice, player);

						} else {
//							GameData.getInstance().getBoard().move(dice, player);
							move_Player(dice, player);
						}

					}
				}
			}
		};
		animationTimer.start();
//		startCountDown();
	}

	// Method to navigate to another screen
//	private void navigateTo(String fxmlFile) {
//		try {
//			Stage stage = (Stage) return_btn.getScene().getWindow();
//			double width = stage.getScene().getWidth();
//			double height = stage.getScene().getHeight();
//			System.out.print(GameData.getInstance().toString());
//			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
//			mainPain.getChildren().clear();
//
//			stage.setScene(scene);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	private boolean checkwin(int gameEnd) {
		gameEnd_var = gameEnd;
		// TODO Auto-generated method stub
		if (gameEnd == 1) {
//			stopTimer();
			stopThemeSong();
			rollButton.setDisable(true);
			timeline.stop();
			timer.stop();
			popupStage = new Stage();
			popupStage.initOwner((grid).getScene().getWindow());
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
			popupStage.setAlwaysOnTop(true); // Set always on top
			popupStage.setResizable(false);
			popupStage.getStyle();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WinScreen_view.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Load the FXML file for the pop-up
			winScreen_Controller controller = loader.getController();
			Player Player_won = GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn());
			GameData.getInstance().setWinner(Player_won.getName());
			GameData.getInstance().setPlayTime(timeSeconds);
			GameData.getInstance().appendGameToJson();
			controller.setWinner(Player_won);

			Scene scene = new Scene(root);
//			controller.setPreviousWindow(this);
			// Set the scene and show the stage
			popupStage.setScene(scene);
			popupStage.show();
			return true;
		}
		return false;
	}

	private void showQuestion(Question q, Player p) {
		timer.stop();
		board.notifyObservers(GameEvent.QUESTION_POP);// obserevr for QUESTION_POP

//		Question q = board.getTile(tile_num).getQuestion();
		if (q == null) {
			System.out.println("No question");
			return;
		}
//		timer.stop();
		popupStage = new Stage();
		popupStage.initOwner((return_btn).getScene().getWindow());
		popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
		popupStage.setAlwaysOnTop(true); // Set always on top
		popupStage.setResizable(false);
		popupStage.getStyle();
//		popupStage.initStyle(StageStyle.UNDECORATED);
		// Load the FXML file for the pop-up
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameQuestion.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		QuestionPopControl questionPopControl = loader.getController();
		// Set the scene and show the stage
		questionPopControl.set_question(q);
		questionPopControl.prev_window(this);
		questionPopControl.set_player(p);
		Scene scene = new Scene(root);
//		popupStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
//			System.out.println("WINDOW_CLOSE_REQUEST");
//			startCountDown();
//		});
		popupStage.setScene(scene);
		popupStage.show();
	}

	private void clear_all() {
		// "/view/MenuScreenView.fxml"
		try {
			stopThemeSong();
			GameData.getInstance().reset();
			Stage stage = (Stage) return_btn.getScene().getWindow();
			mainPain.getChildren().clear();
			grid.getChildren().clear();
			timer.stop();
			
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();
			System.out.print(GameData.getInstance().toString());
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/MenuScreenView.fxml")), width,
					height);
			stage.setScene(scene);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Button get_rollButton() {
		return rollButton;
	}

	public BoardControl getControlBoardControl() {
		return this;
	}
	
	
	private void themeSong() {
	    try {
			flagSong=1;

	        // Adjust the path to where your sound file is located
	        URL soundFile = this.getClass().getResource("/sounds/BOARD_JUNGLE_SONG.wav");
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
	        boardSongClip = AudioSystem.getClip();
	        boardSongClip.open(audioIn);

	        // Check if the Clip supports volume control
	        if (boardSongClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
	            FloatControl gainControl = (FloatControl) boardSongClip.getControl(FloatControl.Type.MASTER_GAIN);
	            float dB = (float) (Math.log(0.15) / Math.log(10.0) * 20.0);
	            gainControl.setValue(dB); // Reduce volume by a calculated dB value
	        }
	        boardSongClip.loop(Clip.LOOP_CONTINUOUSLY); // loop the sound

//	        boardSongClip.start();
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}
	public void stopThemeSong() {
	    if (boardSongClip != null) {
	    	boardSongClip.stop(); // Stop the clip
	    	boardSongClip.close(); // Close the clip to release resources
	    }
	}
}
