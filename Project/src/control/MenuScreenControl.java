package control;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameData;
import javafx.application.Application;

import javafx.util.Duration;

public class MenuScreenControl {

	@FXML
	private Text gameTitle;

	@FXML
	private Button button_start, button_questionWizard, button_History, quit, button_instructions;

//	private ImageView soundOff_Icon, soundOn_Icon;\
	@FXML
	private ImageView sound_Icon;

	@FXML
	private StackPane soundPane;

	@FXML
	private AnchorPane Menu_Pane;

	private AnchorPane splash_screen_AnchorPane;
	@FXML

	private static boolean first_start = true;

//	private static boolean isSoundOn;

	private Clip splashScreenClip;

	private static Clip themeSongClip;

	private static int flagSong;

	private String pathSoundON = "/view/Images/sound-on.png";
	private String pathSoundOFF = "/view/Images/sound-off.png";

	@FXML
	public void initialize() {
		System.out.println("initialize " + flagSong);
		setSoundButtonEvent();
		splash_Screen();
	}

	private void init() {
		System.out.println("INIT " + flagSong);
//		isSoundOn = true;
		if (flagSong == 0) {
			System.out.println("testtest init flagSong == 0");
			stopThemeSong();
			if (first_start) {
				themeSong();
				flagSong = 1;
				first_start = false;
			}
		} else {
			System.out.println("testtest init flagSong != 0");
		}
		System.out.println("INIT2 " + flagSong);
		if (flagSong == 1) {
			System.out.println("testtest initialize flagSong == 1 ");
			sound_Icon.setImage(new Image(pathSoundON));
		} else {
			System.out.println("testtest initialize flagSong != 1");

			sound_Icon.setImage(new Image(pathSoundOFF));
		}

		button_start.setOnAction(event -> navigateTo("/view/SettingsView.fxml"));
		button_questionWizard.setOnAction(event -> navigateTo("/view/QuestionWizView.fxml"));
		button_History.setOnAction(event -> navigateTo("/view/HistoryView.fxml"));
		button_instructions.setOnAction(event -> navigateTo("/view/Instructions.fxml"));
		quit.setOnAction(event -> ((Stage) quit.getScene().getWindow()).close());
		Menu_Pane.getChildren().remove(splash_screen_AnchorPane);
	}

	private void navigateTo(String fxmlFile) {

		try {
			Stage stage = (Stage) button_start.getScene().getWindow();
			double width = stage.getScene().getWidth();
			double height = stage.getScene().getHeight();
//			if (!GameData.getInstance().get_isIngame()) {
//				Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);
//				stage.setScene(scene);
//			} else {
//				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/BoardView.fxml")), width,
//						height);
//				stage.setScene(scene);
//			}
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)), width, height);

			stage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private void spalshScreenSound() {
		try {
			System.out.println("sound1");

			// Adjust the path to where your sound file is located
			URL soundFile = this.getClass().getResource("/sounds/splashSound.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			splashScreenClip = AudioSystem.getClip();
			splashScreenClip.open(audioIn);
			splashScreenClip.loop(Clip.LOOP_CONTINUOUSLY); // loop the sound
			splashScreenClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	void themeSong() {
		try {
//			setFlagSong(1);
//			flagSong = 1;

			// Adjust the path to where your sound file is located
			URL soundFile = this.getClass().getResource("/sounds/themeSong.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			themeSongClip = AudioSystem.getClip();
			themeSongClip.open(audioIn);

			// Check if the Clip supports volume control
			if (themeSongClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gainControl = (FloatControl) themeSongClip.getControl(FloatControl.Type.MASTER_GAIN);
				float dB = (float) (Math.log(0.15) / Math.log(10.0) * 20.0);
				gainControl.setValue(dB); // Reduce volume by a calculated dB value
			}
			themeSongClip.loop(Clip.LOOP_CONTINUOUSLY); // loop the sound

			themeSongClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void stopSplashScreenSound() {
		if (splashScreenClip != null) {
			splashScreenClip.stop(); // Stop the clip
			splashScreenClip.close(); // Close the clip to release resources
		}
	}

	public static void stopThemeSong() {
		if (themeSongClip != null) {
			themeSongClip.stop(); // Stop the clip
			themeSongClip.close(); // Close the clip to release resources
			flagSong = 0;
		}
	}

	private void splash_Screen() {
		if (first_start) {
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

			/*
			 * // After the fade out is finished, load your menu ft.setOnFinished(event ->
			 * init());
			 * 
			 * root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ft.play());
			 */

			// Stop the sound and then load your menu after the fade out is finished
			ft.setOnFinished(event -> {
				stopSplashScreenSound();
				init();
			});

			splash_screen_AnchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				stopSplashScreenSound(); // Ensure sound is stopped when splash screen is clicked
				ft.play();
//				soundOn_Icon.setVisible(true);
			});
//			first_start = false;
		} else {
			init();
		}
	}

	public void setSoundButtonEvent() {
//		soundOn_Icon.setVisible(false);
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


	public static int getFlagSong() {
		return flagSong;
	}

	public static void setFlagSong(int flagSong) {
		MenuScreenControl.flagSong = flagSong;
	}

}