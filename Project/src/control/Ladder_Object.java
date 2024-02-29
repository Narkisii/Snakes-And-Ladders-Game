package control;

import java.util.Random;

import enums.Colors;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Ladder_Object implements GameElement {
	private Pane canvas;
	private Pane startTile;

	public Ladder_Object(Pane canvas) {
		this.canvas = canvas;
	}

	public void set_Tile(Pane tile) {
		this.startTile = tile;
	}

	@Override
	public void add(double startX, double startY, double endX, double endY, double distance) {
//		System.out
//				.println("startX" + startX + "startY" + startY + "endX" + endX + "endY" + endY + "distance" + distance);
		// TODO Auto-generated method stub
		Random random = new Random();
		String color2 = "Blue";
		Colors[] colors = Colors.values();
		while(color2.equals("Blue") || color2.equals("Green") || color2.equals("Yellow")) {
		Colors randomColor = colors[random.nextInt(colors.length)];
		color2 = randomColor.name(); // 50% transparent
		}
		Color color = Color.web(color2);

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

	@Override
	public void set_Color(Color color) {
		// TODO Auto-generated method stub

	}

}
