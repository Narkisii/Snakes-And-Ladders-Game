package control;

import model.Player;
import model.Question;
import model.Snake;
import model.SoundManager;
import model.Tile;
import model.cpu_Player;

import java.io.IOException;
import java.util.ArrayList;
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
import enums.GameEvent;
import javafx.geometry.Pos;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
import javafx.collections.ObservableMap;

/**
 * This class controls the board functionality for the board game application.
 * It handles initialization, gameplay mechanics, and visual updates for the
 * board.
 */

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

	@FXML
	private CheckBox check_Sound;

	@FXML
	private Button pause_Btn;

	// Initialize the timer properties
	/**
	 * Property representing the game timer counter.
	 */
	private IntegerProperty counter;

	/**
	 * Timeline for the game timer.
	 */
	private Timeline timer;

	/**
	 * Integer representing the time in seconds for the game timer.
	 */
	private Integer timeSeconds = 0;

	/**
	 * Grid pane containing the game tiles.
	 */
	private GridPane grid;

	/**
	 * List of containers for player tokens and name in a vbox.
	 */
	private ArrayList<VBox> players_VBox_Container_list;

	/**
	 * Variable representing the end of the game.
	 */
	private int gameEnd_var;

	/**
	 * List of observers for game events.
	 */
	private List<GameEventObserver> observers = new ArrayList<>();

	/**
	 * Duration for each turn.
	 */
	private int set_turn_time = 45;

	/**
	 * Pause transition for turn pauses.
	 */
	private PauseTransition pause_for_turn;

	/**
	 * Animation timer for dice rolling animation.
	 */
	private AnimationTimer diceRollAnimation;

	/**
	 * Boolean indicating whether the game is paused.
	 */
	private boolean paused = false;

	/**
	 * Map storing the time stopped on each turn.
	 */
	private ObservableMap<String, Duration> time_stopped_on;

	/**
	 * Label for pausing the game.
	 */
	private Label pauseLabel;

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
	/**
	 * Initializes the FXML components and creates the board grid with tiles and
	 * players.
	 */

	@FXML // This method is called by the FXMLLoader when initialization is complete
	/**
	 * This method is called when the FXML scene is loaded and initializes the
	 * various components of the board game application.
	 */
	public void initialize() {

		// Stop theme song and set flag (likely to prevent restarting)
		MenuScreenControl.stopThemeSong();
		MenuScreenControl.setFlagSong(0);

		// Initialize variables
		players_VBox_Container_list = new ArrayList<VBox>(); // VBoxes of the player tokens
		canvas = new Pane(); // Snake and ladders are drawn here separately
		counter = new SimpleIntegerProperty(set_turn_time); // Turn timer property

		// Create and start timers
		createCountDown();
		startCountDown();
		createTimer();

		// Set initial state and listener for sound effects toggle
		check_Sound.setSelected(true);
		check_Sound.selectedProperty().addListener((obs, oldVal, newVal) -> {
			GameData.getInstance().setSoundFX(newVal);
			// Your code here
		});

		// Add listeners for window resize events
		mainPain.widthProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue.intValue() != 0) {
				canvas.getChildren().clear();
				mainPain.getChildren().remove(canvas);
				redrawLines();
				mainPain.getChildren().add(canvas);
				add_SpecialTiles();
			}
		});

		mainPain.heightProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue.intValue() != 0) {
				canvas.getChildren().clear();
				mainPain.getChildren().remove(canvas);
				drawBoardObjectsInSeparateThread();
				mainPain.getChildren().add(canvas);
				add_SpecialTiles();
			}
		});

		// Create and add board grid
		grid = createBoard(GameData.getInstance().getNumOfTiles());
		boardpane.getChildren().add(grid);
		drawBoardObjectsInSeparateThread();

		// Add canvas and set action for buttons
		mainPain.getChildren().add(canvas);
		return_btn.setOnAction(event -> setExitScreen());

		// Update turn label and image
		turn_Lable.setTextFill(Color
				.web(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getColor()));
		turn_Lable.setText(GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getName()
				+ "'s turn");
		Image img = new Image(
				GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getToken());
		pTurn_image.setImage(img);
		rollButton.setOnAction(event -> roll(board.get_Dice_Result(),
				GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn())));
		pause_Btn.setOnAction(event -> pause());

		// Initialize and attach observer to board
		GameData.getInstance().init_board();
		board = GameData.getInstance().getBoard();
		board.generate_board_Objects();
		SoundManager soundManager = new SoundManager();
		board.attach(soundManager);
	}

	/**
	 * Pauses or resumes the game based on the current state.
	 *
	 * This method handles pausing and resuming the game by: - Pausing/playing
	 * timers and the animation timeline (if they exist). - Disabling/enabling the
	 * "roll dice" button. - Updating the pause button text ("Pause Game" or
	 * "Continue Game"). - Displaying/hiding a "Game is paused" label on the canvas.
	 */
	public void pause() {
		if (!paused) {
			// Game is not paused, so pause it
			if (timer != null) {
				timer.pause();
			}
			if (timeline != null) {
				timeline.pause();
			}
			rollButton.setDisable(true); // Disable roll button
			pause_Btn.setText("Continue Game"); // Update button text
			pauseLabel = new Label("Game is paused"); // Create pause label
			pauseLabel.setFont(new Font(50)); // Set label font
			pauseLabel.setLayoutX(10); // Set label position
			pauseLabel.setLayoutY(300); // Set label position

			// Add the label to the canvas
			canvas.getChildren().add(pauseLabel);

			paused = true; // Set game state to paused
		} else {
			// Game is paused, so resume it
			if (timer != null) {
				timer.play();
			}
			if (timeline != null) {
				timeline.play();
			}
			rollButton.setDisable(false); // Enable roll button
			pause_Btn.setText("Pause Game"); // Update button text
			canvas.getChildren().remove(pauseLabel); // Remove pause label

			paused = false; // Set game state to playing
		}
	}

	/**
	 * Creates the visual board grid with tiles and their IDs.
	 *
	 * This method constructs the GridPane representing the game board and populates
	 * it with tiles, labels, and StackPanes. It adjusts tile and label sizes based
	 * on the board size and binds them to grid dimensions for dynamic resizing.
	 * Tiles are given alternating colors for visual clarity.
	 * 
	 * @param numTiles The number of tiles per side of the square board.
	 * @return The generated GridPane representing the visual board.
	 */
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
//				label.setAlignment(Pos.CENTER);
				label.minHeight(0);
				label.minWidth(0);
				label.setAlignment(Pos.TOP_LEFT);

				// Create a new StackPane to hold the square and the label
				Pane stackPane = new StackPane();
				stackPane.setPadding(new Insets(10, 10, 10, 10));
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

	private void createCountDown() {
		// Bind the time_Label to the counter property
		countDown_Label.textProperty().bind(counter.asString());
		// Create a timeline for the countdown
		timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			counter.set(counter.get() - 1);
			if (counter.get() == 0) {// Skip player
				rollButton.setDisable(true);
				turn_Lable.setText("Missed your turn!!");
				turn_Lable.setTextFill(Color.RED);
				next_Turn();

			}
			if (counter.get() < 0) {
				counter.set(0);
			}

		}));
		timer.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
	}

	// Method to create the overall game timer
	private void createTimer() {
		// Initialize the timeline
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		// Add a key frame to update the timer label every second
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timeSeconds++; // Increment timeSeconds by 1 every second
				int hours = timeSeconds / 3600;
				int minutes = (timeSeconds % 3600) / 60;
				int seconds = timeSeconds % 60;
				// Format the time as HH:MM:SS
				String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				// Update timerLabel
				timer_Label.setText(formattedTime);
			}
		}));
		timeline.playFromStart();
	}

	// Start the countdown timer
	public void startCountDown() {
		counter.set(set_turn_time);
		timer.play();
	}

	// Stop the countdown timer
	private void stopTimer() {
		timer.stop();
	}

	/**
	 * Adds visual representations of special tiles to the board.
	 *
	 * This method retrieves a list of special tiles from GameData, looks up their
	 * corresponding Pane elements on the grid, and adds ImageViews with appropriate
	 * images and bindings for dynamic resizing.
	 */
	private void add_SpecialTiles() {
		// Get a list of special tiles
		LinkedList<Tile> specialTiles = GameData.getInstance().getspecialTiles_list();

		// Iterate through each special tile
		for (Tile tile : specialTiles) {
			// Find the corresponding Pane on the grid
			Pane startTile = (Pane) grid.lookup("#" + tile.getId());

			// Check if an ImageView for this tile already exists
			ImageView tile_img = (ImageView) startTile.lookup("#image" + tile.getId());

			// If the ImageView doesn't exist, create it
			if (tile_img == null) {
				Image img = new Image(tile.get_Image()); // Load the tile image
				tile_img = new ImageView(img); // Create the ImageView
				tile_img.setId("image" + tile.getId()); // Set unique ID for future lookups
				tile_img.setPreserveRatio(true); // Maintain aspect ratio
				tile_img.opacityProperty().set(0.8); // Set initial opacity
				tile_img.fitHeightProperty().bind(startTile.heightProperty().multiply(0.8)); // Bind size for dynamic
																								// resizing
				tile_img.fitWidthProperty().bind(startTile.widthProperty().multiply(0.8));
				startTile.getChildren().add(tile_img); // Add the ImageView to the Pane
			}

			// Ensure size bindings are always applied, even if the ImageView already
			// existed
			tile_img.fitHeightProperty().bind(startTile.heightProperty().multiply(0.8));
			tile_img.fitWidthProperty().bind(startTile.widthProperty().multiply(0.8));
		}
	}

	/**
	 * Draws board objects in a separate thread. This method ensures that the board
	 * is fully initialized before drawing the objects on it. It creates a new
	 * thread to execute the drawing operations.
	 */
	public void drawBoardObjectsInSeparateThread() {

		Thread thread = new Thread(() -> {
			// Run the drawing operations on the JavaFX application thread

			Platform.runLater(() -> {
				// Iterate over each player in the player list and initialize their tokens
				for (Player p : GameData.getInstance().getplayer_list()) {
					initiate_Players(p);
				}
				// Iterate over each ladder and add it to the game board
				for (Ladder l : GameData.getInstance().getLadders()) {
					add_GameElement(l.getStart(), l.getEnd(), l);
				}
				// Iterate over each snake and add it to the game board
				for (Snake s : GameData.getInstance().getSnake_list()) {
					add_GameElement(s.getStart(), s.getEnd(), s);
				}
				// Add special tiles (e.g., surprise, question, and red snake tiles) to the game
				// board
				add_SpecialTiles();
				// Resize player token containers based on the size of their respective tiles
				for (VBox playerbox : players_VBox_Container_list) {
					Pane new_pos_pane = (Pane) grid.lookup("#" + playerbox.getId());
					playerbox.prefHeightProperty().bind(new_pos_pane.heightProperty().multiply(0.8));
					playerbox.prefWidthProperty().bind(new_pos_pane.widthProperty().multiply(0.8));
				}
			});
		});
		// Start the thread to execute the drawing operations
		thread.start();
	}

	/**
	 * Initializes the player tokens. This method creates and configures tokens for
	 * each player on the game board.
	 * 
	 * @param p The player for which the token is being initialized.
	 */
	private void initiate_Players(Player p) {
		// Find the pane corresponding to the player's current position on the game
		// board
		Pane new_pos_pane = (Pane) grid.lookup("#" + p.getCurrentP());
		// Find the VBox container for the player's token
		VBox playerbox = (VBox) new_pos_pane.lookup("#VBox" + p.getID());

		Label playerName = null;
		ImageView img = null;
		// If the player's token container doesn't exist yet
		if (playerbox == null) {
			playerbox = new VBox();
			playerbox.setId("VBox" + p.getID());
			// Create a label for the player's name
			playerName = new Label(p.getName());
			playerName.setTextFill(Color.web(p.getColor()));
			playerName.setId(String.valueOf(p.getID()) + p.getName());
			playerName.getStylesheets().add("/view/resources/Css/all_Style.css");
			playerName.getStyleClass().add("player_font");
			// Load the player's token image
			Image img_file = new Image(p.getToken());
			img = new ImageView(img_file);
			img.setId("Image" + p.getID());
			img.setPreserveRatio(true);
			// Add the player's name and token to the token container
			// Because of the small tile size of the hardest difficulty board we won't add
			// the players name
			if (GameData.getInstance().getDifficulty().equals("Hard")) {
				playerbox.getChildren().addAll(img);
			} else {
				playerbox.getChildren().addAll(playerName, img);
			}
			// Add the token container to the list of player token containers
			players_VBox_Container_list.add(playerbox);
			new_pos_pane.getChildren().addAll(playerbox);

			// If the player is a CPU player, set the game board control for it
			if (p instanceof model.cpu_Player) {
				((cpu_Player) p).set_board_control(this);
			}
		} else {
			// If the player's token container already exists, retrieve the player's name
			// label and token image
			playerName = (Label) playerbox.lookup("#" + p.getID() + p.getName());
			img = (ImageView) playerbox.lookup("#Image" + p.getID());
		}
		// Adjust the size of the player's token based on the size of the tile it
		// occupies
		img.fitHeightProperty().bind(new_pos_pane.heightProperty().multiply(0.7));
		img.fitWidthProperty().bind(new_pos_pane.widthProperty().multiply(0.7));

	}

	/**
	 * Adds a game element (snake or ladder) to the game board. This method creates
	 * and adds visual representations of snakes or ladders on the game board
	 * canvas.
	 * 
	 * @param start   The starting position of the game element.
	 * @param end     The ending position of the game element.
	 * @param element The game element (snake or ladder) to be added.
	 */
	public void add_GameElement(int start, int end, Object element) {
		// Create a factory to generate game elements
		GameElementFactory factory = new GameElementFactory(canvas);
		// Disable picking on bounds for the canvas
		canvas.setPickOnBounds(false);

		// Find the pane corresponding to the start and end positions of the game
		// element
		Pane startTile = (Pane) grid.lookup("#" + start);
		Pane endTile = (Pane) grid.lookup("#" + end);
		// If either the start or end tile is not found, return without adding the game
		// element
		if (startTile == null || endTile == null) {
			return;
		}

		// Get the exact point of the start and end tiles local to the grid
		Point2D tileStart = startTile.localToScene(startTile.getWidth() / 2, startTile.getHeight() / 2);
		Point2D tileEnd = endTile.localToScene(endTile.getWidth() / 2, endTile.getHeight() / 2);

		// Calculate the distance between the start and end tiles
		double distance = tileStart.distance(tileEnd);

		// Check the type of game element and use the factory to create and draw it
		if (element instanceof Snake) { // Add the snake to the game
			// Get the start x and y of the tile, get the end tile x and y
			double startX = tileEnd.getX();
			double startY = tileEnd.getY();
			double endX = tileStart.getX();
			double endY = tileStart.getY();
			// Create a snake game element
			GameElement snake = factory.getGameElement("SNAKE");
			snake.set_Color(((Snake) element).getColor());

			snake.add(startX, startY, endX, endY, distance);

		} else if (element instanceof Ladder) { // Add the ladder to the game
			// Get the start x and y of the tile, get the end tile x and y
			System.out.println("tileStart " + start + "tileEnd " + end);
			double startX = tileStart.getX();
			double startY = tileStart.getY();
			double endX = tileEnd.getX();
			double endY = tileEnd.getY();
			// Create a ladder game element
			GameElement ladder = factory.getGameElement("LADDER");
			ladder.set_Tile(startTile);
			ladder.add(startX, startY, endX, endY, distance);
		}

	}

	/**
	 * Initiates a dice roll for the specified player. This method handles the
	 * animation and outcome of the dice roll, updating the UI accordingly.
	 * 
	 * @param dice   The number of dice to roll.
	 * @param player The player rolling the dice.
	 */
	public void roll(int dice, Player player) {
		// Hide the pause button during dice rolling
		pause_Btn.setVisible(false);
		return_btn.setVisible(false);
		// Notify observers about the dice roll event
		board.notifyObservers(GameEvent.DICE_ROLL);
		// Disable the roll button to prevent multiple rolls
		rollButton.setDisable(true);
		final long[] frameCounter = { 0 }; // Counter for animation frames
		final Random random = new Random(); // Random number generator for dice animation
		// Animation timer for dice rolling
		diceRollAnimation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				String path = "view/Images/dice/"; // Path to dice images
				// Check if it's time to update the dice image
				if (frameCounter[0]++ % 3 == 0) { // adjust to control the speed of the animation
					if (frameCounter[0] < 50) { // adjust the 90 to control the duration of the animation
						try {
							// Generate a random dice image
							Image img = new Image(path + (random.nextInt(10)) + ".png");
							diceImage.setImage(img);
						} catch (Exception e) {
							// TODO: handle exception
							path = "Images/dice/";
							Image img = new Image(path + (random.nextInt(10)) + ".png");
							diceImage.setImage(img);
						}
					} else {
						// Show the actual dice result image
						Image img = new Image(path + dice + ".png");
						diceImage.setImage(img);
						this.stop(); // Stop the animation
						// Check if the dice result triggers a special action (question or movement)
						if (dice == 7 || dice == 8 || dice == 9) {
							// Check if there is a question associated with the dice result
							if (GameData.getInstance().get_Question(dice) == null)
								return;
							// Get the question associated with the dice result
							Question q = GameData.getInstance().get_Question(dice);
							// Display the question
							showQuestion(q, player);
						} else {// 1-6
							// Move the player according to the dice result
							move_Player(dice, player);
						}
					}
				}
			}
		};
		// Start the dice rolling animation
		diceRollAnimation.start();
	}

	/**
	 * Moves the player according to the dice roll result. This method updates the
	 * player's position on the board and triggers animations if necessary.
	 * 
	 * @param dice The number rolled on the dice.
	 * @param p    The player to move.
	 */
	public void move_Player(int dice, Player p) {
		// Move the player on the board based on the dice roll result
		if (dice != 0 && dice != -5) {
			GameData.getInstance().getBoard().move(dice, p);
		}
		// Check if the player remains on the same position after moving
		if (p.getCurrentP() == p.getPreviousStep()) {
			// Proceed to the next turn if the player stays on the same position
			next_Turn();
			return;
		}
		// Retrieve the current and previous tiles where the player was and will be
		Pane curr_Tile = (Pane) grid.lookup("#" + p.getCurrentP());
		Pane prev_tile = (Pane) grid.lookup("#" + p.getPreviousStep());
		// Retrieve the player's VBox container
		VBox playerbox = players_VBox_Container_list.get(p.getID() - 1);
		// Animate the player's movement if the dice roll was not zero
		if (dice != 0) {
			animate(playerbox, prev_tile, curr_Tile, p);
		} else {
			// Proceed to the next turn if the dice roll was zero
			next_Turn();
		}
	}

	/**
	 * Animates the movement of the player token from one tile to another. This
	 * method moves the player's VBox container from the start tile to the end tile
	 * on the game board.
	 * 
	 * @param playerbox The VBox container representing the player token.
	 * @param start     The starting tile from which the player moves.
	 * @param end       The destination tile where the player will land.
	 * @param p         The player whose token is being moved.
	 */
	private void animate(VBox playerbox, Pane start, Pane end, Player p) {
		// Notify observers about player movement
		board.notifyObservers(GameEvent.PLAYER_MOVE);

		// Check if the game has ended before proceeding with animation
		if (gameEnd_var != 1) {
			// Check if any necessary objects are null before proceeding
			if (playerbox == null || start == null || end == null || p == null) {
				return;
			}
		}

		// Calculate the exact coordinates of the end and start tiles
		Point2D tileEnd = end.localToScene(end.getWidth() / 2, end.getHeight() / 2);
		Point2D prevTile = start.localToScene(start.getWidth() / 2, start.getHeight() / 2);
		double startX = tileEnd.getX();
		double startY = tileEnd.getY();
		double startX_prev = prevTile.getX();
		double startY_prev = prevTile.getY();
		int curr_P = p.getCurrentP();

		// Create a TranslateTransition for smooth animation
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), playerbox);

		// Set the end position (the new tile)
		tt.setToX(startX - startX_prev);
		tt.setToY(startY - startY_prev);

		// Play Animation
		tt.play();

		// Event handler for when the animation finishes
		tt.setOnFinished(event -> {
			// Reset translation values
			playerbox.setTranslateX(0);
			playerbox.setTranslateY(0);
			// Add player token to the new tile
			end.getChildren().addAll(playerbox);
			// Check the type of the new tile (e.g., if it's a special tile)
			checktile_type(p.getCurrentP(), p);
		});
	}

	/**
	 * Checks the type of the tile the player has landed on and performs appropriate
	 * actions. This method determines the behavior of the player based on the type
	 * of tile landed on.
	 * 
	 * @param tile_num The number representing the tile on the game board.
	 * @param p        The player whose turn it is.
	 */
	private void checktile_type(int tile_num, Player p) {
		// Get the tile object from the board
		Tile tile = board.getTile(tile_num);

		// Check if the tile adds more steps to the player
		if (tile.getType() != 0 && tile.getType() != 4) {// 0 is regular tile, 4 is question tile
			// Activate the tile effect and keep the player in place without adding an extra
			// step
			board.activateTile(tile_num, p);
			move_Player(-5, p);// -5 is basically a flag to keep the player in place and not add an extra step
		}

		if (tile.getType() == 4) {// is a question tile
			// Check if there is a question associated with the tile
			Tile question_tile = board.is_question(tile_num);
			if (question_tile != null) {
				// Display the question to the player
				showQuestion(board.getTile(tile_num).getQuestion(), p);
			}
		}

		// Check if the game has ended
		if (checkwin(GameData.getInstance().getBoard().getGameEnd())) {
			return;
		}

		// Check if the tile is a regular tile
		if (tile.getType() == 0) {// regular tile
			// Proceed to the next player's turn
			next_Turn();
		}
	}

	/**
	 * Proceeds to the next player's turn after a specified duration. This method
	 * updates the game state to the next player's turn and displays relevant
	 * information.
	 */
	private void next_Turn() {
		// Pause for a brief duration before proceeding to the next turn
		pause_for_turn = new PauseTransition(Duration.seconds(2));
		pause_for_turn.setOnFinished(event -> {
			// Ensure there are players in the game
			if (GameData.getInstance().getplayer_list().size() == 0)
				return;
			// Move to the next turn in the game data
			GameData.getInstance().next_turn();

			// Update turn label with current player's information
			turn_Lable.setTextFill(Color.web(
					GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getColor()));
			turn_Lable.setText(
					GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getName()
							+ "'s turn");

			// Set the image of the current player's token
			Image token = new Image(
					GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getToken());
			pTurn_image.setImage(token);

			// Pause briefly to check if it's a CPU player's turn
			PauseTransition pauseForCPUCheck = new PauseTransition(Duration.millis(500));
			pauseForCPUCheck.setOnFinished(eventForCPUCheck -> {
				checkCPU();
			});
			pauseForCPUCheck.play();

			// Start countdown for the current player's turn
			startCountDown();
			// Notify observers about the player's turn
			board.notifyObservers(GameEvent.PLAYER_TURN);// check observer
		});

		// Start the turn transition
		pause_for_turn.play();

	}

	/**
	 * Checks if the current player is a CPU player and performs the corresponding
	 * actions. If the current player is a CPU player, it automatically rolls the
	 * dice for them. Otherwise, it enables the roll button and makes the pause
	 * button visible for manual control.
	 */
	private void checkCPU() {
		// Check if the current player is a CPU player
		if (GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn()).getClass().getName()
				.equals("model.cpu_Player")) {
			cpu_Player cpu_p = (cpu_Player) GameData.getInstance().getplayer_list()
					.get(GameData.getInstance().getPlayerTurn());
			// If the game is not over, hide the pause button and roll the dice for the CPU
			// player
			if (GameData.getInstance().getBoard().getGameEnd() != 1) {
				pause_Btn.setVisible(false);
				return_btn.setVisible(false);
				// Create a CommandInvoker to execute the RollDiceCommand for the current CPU
				// player
				CommandInvoker invoker = new CommandInvoker();
				// Add a RollDiceCommand to the invoker with the current CPU player as the
				// target
				invoker.addCommand(new RollDiceCommand((cpu_Player) GameData.getInstance().getplayer_list()
						.get(GameData.getInstance().getPlayerTurn())));
				// Execute the commands added to the invoker
				invoker.executeCommands();
			}
		} else {
			// If the current player is not a CPU player, enable the roll button and make
			// the pause button visible
			rollButton.setDisable(false);
			pause_Btn.setVisible(true);
			return_btn.setVisible(true);

		}
	}

	/**
	 * Redraws the lines representing ladders and snakes on the game board.
	 */
	public void redrawLines() {
		for (Ladder l : GameData.getInstance().getLadders()) {
			add_GameElement(l.getStart(), l.getEnd(), l);
		}
		for (Snake s : GameData.getInstance().getSnake_list()) {
			add_GameElement(s.getStart(), s.getEnd(), s);
		}
	}

	/**
	 * Displays a question pop-up window. This method stops the timer, notifies
	 * observers about the question pop-up event, and initializes and displays the
	 * question pop-up stage.
	 *
	 * @param q The Question object representing the question to be displayed.
	 * @param p The Player object associated with the question.
	 */

	private void showQuestion(Question q, Player p) {
		stopTimer(); // Stop the timer
		board.notifyObservers(GameEvent.QUESTION_POP); // Notify observers about the question pop-up event

		if (q == null) {
			System.out.println("No question");
			return;
		}
		popupStage = new Stage(); // Create a new stage for the question pop-up window

		try {
			popupStage.initOwner((return_btn).getScene().getWindow()); // Initialize the owner of the pop-up stage
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}
		popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
		popupStage.setAlwaysOnTop(true); // Set always on top
		popupStage.setResizable(false);
		popupStage.getStyle();
		popupStage.initStyle(StageStyle.UNDECORATED);

		// Load the FXML file for the pop-up
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameQuestion.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		QuestionPopControl questionPopControl = loader.getController();
		// Set the question, previous window, and player for the question pop-up control
		questionPopControl.set_question(q);
		questionPopControl.prev_window(this);
		questionPopControl.set_player(p);
		Scene scene = new Scene(root);
		popupStage.setScene(scene);
		popupStage.show();
	}

	/**
	 * Checks if the game has ended based on the provided gameEnd variable. If the
	 * game has ended, disables the roll button, stops the timer, and displays the
	 * win screen.
	 * 
	 * @param gameEnd The variable indicating whether the game has ended (1 for game
	 *                over, 0 otherwise).
	 * @return True if the game has ended, false otherwise.
	 */
	private boolean checkwin(int gameEnd) {
		gameEnd_var = gameEnd;
		if (gameEnd == 1) { // Check if the game has ended
			rollButton.setDisable(true); // Disable the roll button
			timeline.stop(); // Stop the timer
			stopTimer(); // Stop the timer

			popupStage = new Stage(); // Create a new stage for the win screen
			popupStage.initOwner((grid).getScene().getWindow()); // Initialize the owner of the win screen stage
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
			popupStage.setAlwaysOnTop(true); // Set always on top
			popupStage.setResizable(false);
			popupStage.getStyle();
			popupStage.initStyle(StageStyle.UNDECORATED); // Initialize the style of the stage

			// Load the FXML file for the win screen
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WinScreen_view.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Initialize the controller for the win screen
			winScreen_Controller controller = loader.getController();
			Player Player_won = GameData.getInstance().getplayer_list().get(GameData.getInstance().getPlayerTurn());
			GameData.getInstance().setWinner(Player_won.getName());
			GameData.getInstance().setPlayTime(timeSeconds);
			GameData.getInstance().appendGameToJson();
			controller.setWinner(Player_won);
			controller.setPreviousWindow(this);

			Scene scene = new Scene(root);
			// Set the scene and show the stage
			popupStage.setScene(scene);
			popupStage.show();
			return true;
		}
		return false;
	}

	/**
	 * Clears all game-related data and resets the game to its initial state. Stops
	 * any ongoing animations, resets game data, and clears the game interface.
	 * 
	 * If the game was initiated from a menu screen, returns to the menu screen
	 * after a delay.
	 */
	void clear_all() {
		if (diceRollAnimation != null)
			diceRollAnimation.stop(); // Stop the dice roll animation if it's running
		if (pause_for_turn != null)
			pause_for_turn.stop(); // Stop the pause for turn if it's running

		GameData.getInstance().reset(); // Reset the game data

		Stage stage = (Stage) return_btn.getScene().getWindow(); // Get the current stage

		// Clear all nodes from the main pane and grid
		mainPain.getChildren().clear();
		grid.getChildren().clear();

		timer.stop(); // Stop the timer

		double width = stage.getScene().getWidth(); // Get the width of the current scene
		double height = stage.getScene().getHeight(); // Get the height of the current scene

		PauseTransition delay = new PauseTransition(Duration.millis(500));
		delay.setOnFinished(event -> {
			try {
				// Load the main menu after the delay
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/MenuScreenView.fxml")), width,
						height);
				stage.setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		delay.play(); // Start the delay
	}

	/**
	 * Sets up the exit game confirmation screen. Creates a new stage for the pop-up
	 * if it's not already open.
	 */
	public void setExitScreen() {
		pause();
		// this method only need to set the screen and wait for the exit buttons event
		setPopUpStage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/exitGamePop.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get the controller for the exit game pop-up
		ExitGameControl exitControl = loader.getController();
		exitControl.setPreviousWindow(this); // Set the previous window in the exit screen to get to diffrent board
												// methods

		// Set the scene and show the stage
		Scene scene = new Scene(root);
		popupStage.setScene(scene);
		popupStage.show();

	}

	/**
	 * Sets up the pop-up stage for the exit game confirmation screen. If the pop-up
	 * is already open, does nothing. Otherwise, creates a new stage for the pop-up.
	 */
	public void setPopUpStage() {
		// if the pop up is already open - do nothing
		if (popupStage != null && popupStage.isShowing()) {
			return;
		} else { // Create a new Stage for the pop-up
			popupStage = new Stage();
			popupStage.setResizable(false);
			popupStage.initOwner((grid).getScene().getWindow());
			popupStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL
			popupStage.setAlwaysOnTop(true); // Set always on top
			popupStage.setResizable(false);
			popupStage.getStyle();
			popupStage.initStyle(StageStyle.UNDECORATED); // Initialize the style of the stage
		}
	}

	public Button get_rollButton() {
		return rollButton;
	}

	public BoardControl getControlBoardControl() {
		return this;
	}

}
