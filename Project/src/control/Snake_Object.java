package control;

import java.util.Random;

import enums.Colors;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

public class Snake_Object implements GameElement {

	Color color;

	private Pane canvas;

	public Snake_Object(Pane canvas) {
		this.canvas = canvas;
	}

	public void set_Color(Color color) {
		this.color = color;
	}

	@Override
	public void add(double startX, double startY, double endX, double endY, double distance) {
		// TODO Auto-generated method stub

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
		int rand_Head = rand.nextInt(4) + 1;
		System.out.println("/view/Images/Snakes/" + color + "/" + rand_Head + ".png");
		Image image = new Image("/view/Images/Snakes/" + color + "/" + rand_Head + ".png");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		imageView.setPreserveRatio(true);
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(new Double[] { startX + 10, startY - 10, // Point 1
				startX + 70, startY + 5, // Point 2
				startX + 10, startY + 10 // Point 3
		});
		triangle.setFill(color);
		Circle c = new Circle(startX + 10, startY, 10, color);
		imageView.setX(endX - imageView.getFitWidth() / 2);
		imageView.setY(endY - imageView.getFitHeight() / 2);
//		int temp = rand.nextInt(20)+20;
//		LinearGradient gradient = new LinearGradient(0, temp, temp, temp, false, CycleMethod.REPEAT, new Stop(0.45, color),
//				new Stop(0.40, color.brighter()), new Stop(0.45, color.darker()));
//
//		cubic.setStroke(gradient);
		cubic.setStroke(color);

		cubic.setStrokeWidth(20);
		cubic.setFill(Color.TRANSPARENT);
//		cubic.getStrokeDashArray().addAll(20.0);
//		cubic.setStrokeDashOffset(10);
//		double dash_length = 15.0d;
//		double gap_length = 50 * 0.2d; // 8 pixels gap
//
//		cubic.getStrokeDashArray().addAll(dash_length, gap_length);
//		cubic.setStrokeDashOffset(0);

//		cubic.setStrokeType(StrokeType.CENTERED);
//		cubic.setStrokeLineJoin(StrokeLineJoin.ROUND);
		double op = 0.5 + Math.random() * 0.4;
		cubic.setOpacity(op);
		imageView.setOpacity(op+0.5);
		c.setOpacity(op-0.1);
		triangle.setOpacity(op);
		canvas.getChildren().addAll(cubic, imageView, c, triangle);
//		canvas.getChildren().addAll(cubic, imageView);

	}

	@Override
	public void set_Tile(Pane tile) {
		// TODO Auto-generated method stub

	}
}
