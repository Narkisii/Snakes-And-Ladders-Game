package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.HandleExceptions;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.NoJsonFileFound;
import model.QuestionsFromJson;

/**
 * Controller class for the menu screen of the game. This class handles user
 * interaction with the menu elements and plays/stops background music and
 * splash screen sound.
 */
public class MenuScreenControl {

	@FXML
	private Text gameTitle;

	@FXML
	private Button button_start, button_questionWizard, button_History, quit, button_instructions;

	// private ImageView soundOff_Icon, soundOn_Icon;\
	@FXML
	private ImageView sound_Icon;

	@FXML
	private StackPane soundPane;

	@FXML
	private AnchorPane Menu_Pane;

	private AnchorPane splash_screen_AnchorPane;
	@FXML

	private static boolean first_start = true;

	// private static boolean isSoundOn;

	private Clip splashScreenClip;

	private static Clip themeSongClip;

	private static int flagSong;

	private String pathSoundON = "/view/Images/sound-on.png";
	private String pathSoundOFF = "/view/Images/sound-off.png";

	private String path;

	/**
	 * Initializes the controller. - Sets up the sound button event handler. -
	 * Displays the splash screen if it's the first time the game is launched.
	 */
	@FXML
	public void initialize() {
		setSoundButtonEvent();
		splash_Screen();

	}

	/**
	 * Initializes the menu screen after the splash screen fades out. - Plays theme
	 * music if sound is enabled. - Sets the sound icon based on the current sound
	 * state. - Attaches event handlers to menu buttons.
	 */
	private void init() {
		if (flagSong == 0) {
			stopThemeSong();
			if (first_start) {
				themeSong();
				flagSong = 1;
				first_start = false;
			}
		}

		if (flagSong == 1) {
			sound_Icon.setImage(new Image(pathSoundON));
		} else {
			sound_Icon.setImage(new Image(pathSoundOFF));
		}

		button_start.setOnAction(event -> navigateTo("/view/SettingsView.fxml"));
		button_questionWizard.setOnAction(event -> navigateTo("/view/QuestionWizView.fxml"));
		button_History.setOnAction(event -> navigateTo("/view/HistoryView.fxml"));
		button_instructions.setOnAction(event -> navigateTo("/view/Instructions.fxml"));
		quit.setOnAction(event -> ((Stage) quit.getScene().getWindow()).close());
		Menu_Pane.getChildren().remove(splash_screen_AnchorPane);

	}

	/**
	 * Navigates to a different view based on the provided FXML file path.
	 * 
	 * @param fxmlFile The path to the FXML file of the target view.
	 */
	private void navigateTo(String fxmlFile) {
		if (!createJsonFilesIfMissing()) {
			return;
		}

		try {
			Stage stage = (Stage) button_start.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
			// stage.setAlwaysOnTop(true);
			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Plays the splash screen sound effect.
	 */
	private void spalshScreenSound() {
		try {
			System.out.println("sound1");

			URL soundFile = this.getClass().getResource("/sounds/splashSound.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			splashScreenClip = AudioSystem.getClip();
			splashScreenClip.open(audioIn);

			// Volume control
			double volume = 0.15;
			if (splashScreenClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gainControl = (FloatControl) splashScreenClip.getControl(FloatControl.Type.MASTER_GAIN);
				float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
				gainControl.setValue(dB); // Reduce volume by a calculated dB value
			}
			splashScreenClip.loop(Clip.LOOP_CONTINUOUSLY); // loop the sound
			splashScreenClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays the theme song in a loop with adjusted volume for a quieter experience.
	 */

	void themeSong() {
		try {

			URL soundFile = this.getClass().getResource("/sounds/themeSong.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			themeSongClip = AudioSystem.getClip();
			themeSongClip.open(audioIn);

			// Volume control
			double volume = 0.15;
			if (themeSongClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gainControl = (FloatControl) themeSongClip.getControl(FloatControl.Type.MASTER_GAIN);
				float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
				gainControl.setValue(dB); // Reduce volume by a calculated dB value
			}
			themeSongClip.loop(Clip.LOOP_CONTINUOUSLY); // loop the sound
			themeSongClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops the splash screen sound effect if it's playing.
	 */
	private void stopSplashScreenSound() {
		if (splashScreenClip != null) {
			splashScreenClip.stop(); // Stop the clip
			splashScreenClip.close(); // Close the clip to release resources
		}
	}

	/**
	 * Stops the theme song if it's playing and updates the flag indicating sound
	 * state.
	 */
	public static void stopThemeSong() {
		if (themeSongClip != null) {
			themeSongClip.stop(); // Stop the clip
			themeSongClip.close(); // Close the clip to release resources
			flagSong = 0;
		}
	}

	/**
	 * Displays and animates the splash screen if it's the first time the game is
	 * launched.
	 */
	private void splash_Screen() {
		if (first_start) {// If first launch. show splash screen
			spalshScreenSound();
			Image image = new Image("/view/Images/BackGround/Scorpion_SplashScreen.png");
			ImageView imageView = new ImageView(image);

			// Create a StackPane to hold the image
			splash_screen_AnchorPane = new AnchorPane();
			splash_screen_AnchorPane.getChildren().add(imageView);
			splash_screen_AnchorPane.setPickOnBounds(false);
			splash_screen_AnchorPane.prefWidthProperty().bind(Menu_Pane.widthProperty());
			splash_screen_AnchorPane.prefHeightProperty().bind(Menu_Pane.heightProperty());

			imageView.fitWidthProperty().bind(splash_screen_AnchorPane.widthProperty());
			imageView.fitHeightProperty().bind(splash_screen_AnchorPane.heightProperty());

			Menu_Pane.getChildren().add(splash_screen_AnchorPane);
			AnchorPane.setTopAnchor(splash_screen_AnchorPane, 0.0);
			AnchorPane.setBottomAnchor(splash_screen_AnchorPane, 0.0);
			AnchorPane.setLeftAnchor(splash_screen_AnchorPane, 0.0);
			AnchorPane.setRightAnchor(splash_screen_AnchorPane, 0.0);

			PauseTransition pt = new PauseTransition(Duration.millis(10));

			FadeTransition ft = new FadeTransition(Duration.millis(500), imageView);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);

			pt.setOnFinished(event -> ft.play());

			// Stop the sound and then load your menu after the fade out is finished
			ft.setOnFinished(event -> {
				stopSplashScreenSound();
				init();
			});

			splash_screen_AnchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				stopSplashScreenSound(); // Ensure sound is stopped when splash screen is clicked
				ft.play();
			});
		} else {// Just initiate without splash screen
			init();
		}
	}

	/**
	 * Sets up the event handler for the sound button. Toggles sound on/off and
	 * updates the sound icon accordingly.
	 */
	public void setSoundButtonEvent() {
		sound_Icon.setOnMouseClicked(event -> {
			if (flagSong == 1) {
				sound_Icon.setImage(new Image(pathSoundOFF));
				stopThemeSong();
				flagSong = 0;
			} else {
				sound_Icon.setImage(new Image(pathSoundON));
				flagSong = 1;
				themeSong();
			}
		});
	}

	/**
	 * This method checks for the existence of necessary JSON files and creates them
	 * if they are missing. It also populates the "Questions.txt" file with some
	 * initial content if it's newly created.
	 *
	 * @return true if files are successfully checked or created, false otherwise
	 */
	private boolean createJsonFilesIfMissing() {
		try {
			/**
			 * An array of File objects representing the paths to the JSON files.
			 */
			File[] jsonFiles = { new File("src/Json/Questions.txt"), new File("Json/Questions.txt"), // Duplicate path
																										// likely
																										// unintentional
					new File("src/Json/History.txt"), new File("Json/History.txt") // Duplicate path likely
																					// unintentional
			};

			for (File jsonFile : jsonFiles) {
				// Ensure the parent directory of the JSON file exists.
				jsonFile.getParentFile().mkdirs();

				// Check if the JSON file already exists.
				if (!jsonFile.exists()) {
					// Create a new empty file.
					jsonFile.createNewFile();

					if (jsonFile.getName().equals("Questions.txt")) {
						// This block only executes if the newly created file is Questions.txt
						String jsonContent = "{\n" + "  \"questions\": [\n" + "    {\n"
								+ "      \"question\": \"Filler question, Add more questions please\",\n"
								+ "      \"answers\": [\"ans1\", \"ans2\", \"ans3\", \"ans4\"],\n"
								+ "      \"correct_ans\": \"1\",\n" + "      \"difficulty\": 1\n" + "    }\n" + "  ]\n"
								+ "}";

						/**
						 * Writes the initial JSON content to the newly created Questions.txt file using
						 * a try-with-resources block for automatic resource management.
						 */
						try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile))) {
							writer.write(jsonContent);
						}
					}
				}
			}
			// If no exceptions occurred, return true indicating successful file operations.
			return true;
		} catch (IOException e) {
			// Handle any IOException that might occur during file operations.
			HandleExceptions.showException(new NoJsonFileFound(), this, button_start.getScene().getWindow());
			// Return false to indicate an error occurred.
			return false;
		}
	}

	public static int getFlagSong() {
		return flagSong;
	}

	public static void setFlagSong(int flagSong) {
		MenuScreenControl.flagSong = flagSong;
	}

}