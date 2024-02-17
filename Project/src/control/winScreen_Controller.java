package control;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import enums.Colors;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Player;

public class winScreen_Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Pane main_pane;

	@FXML
	private Label winner_label;

	private static final int STAR_COUNT = 20000;

	private final Circle[] nodes1 = new Circle[STAR_COUNT];
	private final Circle[] nodes2 = new Circle[STAR_COUNT];

	private final double[] angles = new double[STAR_COUNT];
	private final long[] start = new long[STAR_COUNT];
	private final int[] randomizer = new int[STAR_COUNT];

	private final Random random = new Random();

	private Player p_winner;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		p_winner = new Player(0, "Black", "Name", null);
	}

	public void setWinner(Player player_won) {
		// TODO Auto-generated method stub
		System.out.println(player_won.getName());
		p_winner = player_won;
		winner_label.setText(p_winner.getName() + " Won the game");
		winner_label.setTextFill(Color.web(p_winner.getColor()));
		anim_init();
	}

	private void anim_init() {

		for (int i = 0; i < STAR_COUNT; i++) {
			int r = random.nextInt(255);
			int g = random.nextInt(255);
			int b = random.nextInt(255);
			Color color = Color.rgb(r, g, b);
//            nodes[i] = new Rectangle(10, 10, color);
//            angles[i] = 2.0 * Math.PI * random.nextDouble();
//            start[i] = random.nextInt(2000000000);
			nodes1[i] = new Circle(3, color);
			nodes2[i] = new Circle(3, color);
			angles[i] = 2.0 * Math.PI * random.nextDouble();
			start[i] = random.nextInt(200000000);
//			randomizer[i] = random.nextInt(200000);
		}
		final Pane pane1 = new Pane(new Group(nodes1));
		final Pane pane2 = new Pane(new Group(nodes2));

		pane1.setOpacity(0.7);
		pane2.setOpacity(0.7);
		main_pane.getChildren().addAll(pane1,pane2);

		new AnimationTimer() {
			@Override
			public void handle(long now) {
				double width = 0.5 * main_pane.getWidth();
				double height = 0.5 * main_pane.getHeight();
				double width1 = -(0.5 * main_pane.getWidth());
				double height1 = -(0.5 * main_pane.getHeight());

//				pane1.setLayoutX(temp);
//				pane1.setLayoutY(temp);
//
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

//					node.setTranslateX(Math.cos(angle) * d + width/randomizer[i]);
//					node.setTranslateY(Math.sin(angle) * d + height/randomizer[i]);
				}
			}
		}.start();
	}

}
