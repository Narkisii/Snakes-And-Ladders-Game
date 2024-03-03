package control;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import enums.Colors;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;

public class winScreen_Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane main_pane;

	@FXML
	private Label winner_label;

	@FXML
	private ImageView winner_img;

	@FXML
	private Button back_toMenu_Btn;

	@FXML
	private Pane party_pane;

	private static final int STAR_COUNT = 20000;

	private final Circle[] nodes1 = new Circle[STAR_COUNT];
	private final Circle[] nodes2 = new Circle[STAR_COUNT];

	private final double[] angles = new double[STAR_COUNT];
	private final long[] start = new long[STAR_COUNT];
	private final int[] randomizer = new int[STAR_COUNT];

	private final Random random = new Random();

	private Player p_winner;

	private BoardControl previousWindow;

	private Clip winner_theme;

	/**
	 * Initializes the win screen controller.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		p_winner = new Player(0, "Black", "Name", null);
		back_toMenu_Btn.setOnAction(event -> returnToMenu());
	}

	/**
	 * Handles the action of returning to the main menu.
	 */
	private void returnToMenu() {
		// Create animation event to close the screen after waiting
		PauseTransition delay = new PauseTransition(Duration.millis(100));
		delay.setOnFinished(event_2 -> {
			Stage stage = (Stage) back_toMenu_Btn.getScene().getWindow();
			stage.close();
		});
		delay.play();
		stopThemeSong();
		previousWindow.clear_all();
	}

	/**
	 * Sets the winner of the game.
	 * 
	 * @param player_won The player who won the game.
	 */
	public void setWinner(Player player_won) {
		// TODO Auto-generated method stub
		p_winner = player_won;
		winner_label.setText(p_winner.getName() + " Won the game");
		winner_label.setTextFill(Color.web(p_winner.getColor()));
		winner_img.setImage(new Image(player_won.getToken()));
		anim_init();
		themeSong();

	}

	/**
	 * Sets the previous window control to enable action from same controller.
	 * 
	 * @param boardControl The BoardControl of the previous window.
	 */
	public void setPreviousWindow(BoardControl boardControl) {
		// TODO Auto-generated method stub
		this.previousWindow = boardControl;
	}

	/**
	 * Initializes the animation for the win screen.
	 */
	private void anim_init() {
		for (int i = 0; i < STAR_COUNT; i++) {
			int r = random.nextInt(255);
			int g = random.nextInt(255);
			int b = random.nextInt(255);
			Color color = Color.rgb(r, g, b);
			nodes1[i] = new Circle(3, color);
			nodes2[i] = new Circle(3, color);
			angles[i] = 2.0 * Math.PI * random.nextDouble();
			start[i] = random.nextInt(200000000);
		}
		final Pane pane1 = new Pane(new Group(nodes1));
		final Pane pane2 = new Pane(new Group(nodes2));

		pane1.setOpacity(0.7);
		pane2.setOpacity(0.7);
		party_pane.getChildren().addAll(pane1, pane2);

		new AnimationTimer() {
			@Override
			public void handle(long now) {
				double width = 0.5 * party_pane.getWidth();
				double height = 0.5 * party_pane.getHeight();
				double width1 = -(0.5 * party_pane.getWidth());
				double height1 = -(0.5 * party_pane.getHeight());
				double radius1 = Math.sqrt(2) * Math.max(width, height);
				double radius2 = Math.sqrt(2) * height1;

				for (int i = 0; i < STAR_COUNT; i++) {
					Node node1 = nodes1[i];
					Node node2 = nodes2[i];
					double angle = angles[i];
					long t = (now - start[i]) % 2000000000;
					double d1 = t * radius1 / 2000000000.0;
					double d2 = t * radius2 / 2000000000.0;
					node1.setTranslateX(Math.cos(angle) * d1 + (width++));
					node1.setTranslateY(Math.sin(angle) * d1 + (height++));
					node2.setTranslateX(Math.cos(angle) * d2 + (width1++));
					node2.setTranslateY(Math.sin(angle) * d2 + (height1++));
				}
			}
		}.start();
	}

	/**
	 * Plays the theme song for the win screen.
	 */
	private void themeSong() {

		try {
			// Adjust the path to where your sound file is located
			URL soundFile = this.getClass().getResource("/sounds/Win/PLAYER_WINS.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			winner_theme = AudioSystem.getClip();
			winner_theme.open(audioIn);

			// Check if the Clip supports volume control
			if (winner_theme.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gainControl = (FloatControl) winner_theme.getControl(FloatControl.Type.MASTER_GAIN);
				float dB = (float) (Math.log(0.05) / Math.log(10.0) * 20.0);
				gainControl.setValue(dB); // Reduce volume by a calculated dB value
			}
			winner_theme.loop(Clip.LOOP_CONTINUOUSLY); // loop the sound
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Stops the theme song.
	 */
	public void stopThemeSong() {
		if (winner_theme != null) {
			winner_theme.stop(); // Stop the clip
			winner_theme.close(); // Close the clip to release resources
		}
	}

}
