package control;

import java.util.Random;

import enums.Colors;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Ladder_Object implements GameElement {
	private Pane canvas;
	private Pane startTile;

	/**
	 * Constructor for creating a Ladder_Object.
	 * 
	 * @param canvas The canvas on which the ladder object will be drawn.
	 */
	public Ladder_Object(Pane canvas) {
		this.canvas = canvas;
	}

	/**
	 * Sets the starting tile for the ladder.
	 *
	 * @param tile The Pane object representing the starting tile.
	 */
	@Override
	public void set_Tile(Pane tile) {
		startTile = tile;
	}

	@Override
	/**
	 * Adds a visual representation of a ladder to the game board canvas.
	 *
	 * @param startX   X-coordinate of the ladder's starting point.
	 * @param startY   Y-coordinate of the ladder's starting point.
	 * @param endX     X-coordinate of the ladder's ending point.
	 * @param endY     Y-coordinate of the ladder's ending point.
	 * @param distance The distance between the start and end points.
	 */
	public void add(double startX, double startY, double endX, double endY, double distance) {

		// Generate a visually distinct color for the ladder
		Random random = new Random();
		String color2 = "Blue";
		Colors[] colors = Colors.values();
		// Make sure the colors of the ladder are different from any snake color
		while (color2.equals("Blue") || color2.equals("Green") || color2.equals("Yellow")) {
			Colors randomColor = colors[random.nextInt(colors.length)];
			color2 = randomColor.name();
		}
		Color color = Color.web(color2);

		// Calculate the angle of the ladder
		double angle = Math.toDegrees(Math.atan2(startY - endY, startX - endX));

		// Create the rectangle representing the ladder
		Rectangle rectangle = new Rectangle();

		// Create a gradient for visual interest
		int temp = random.nextInt(20) + 20;
		LinearGradient gradient = new LinearGradient(0, temp, temp, temp, false, CycleMethod.REPEAT,
				new Stop(0.5, Color.TRANSPARENT), new Stop(0.5, color));

		// Style the rectangle
		rectangle.setStroke(color); // Set stroke color
		rectangle.setStrokeWidth(5); // Set stroke width
		rectangle.getStrokeDashArray().add(5.0); // Set dashed stroke
		System.out.println("color: " + color + "Angle: " + angle);
		// Position and rotate the rectangle based on the angle
		int rect_width = 50;
		if (angle == 90 || angle == -90) {
			rectangle.setX(endX - (rect_width / 2));
			rectangle.setY(endY);
			rectangle.setWidth(rect_width);
			rectangle.setHeight(distance); // set the height as you need
			gradient = new LinearGradient(temp, 0, temp, temp, false, CycleMethod.REPEAT,
					new Stop(0.5, Color.TRANSPARENT), new Stop(0.5, color));
		} else {
			if (angle < 90) {
				rectangle.setX(endX + (rect_width / 2) - startTile.getWidth() / 2); // Adjust for start tile width
				rectangle.setY(((startY + endY) / 2) - (rect_width / 2));
			} else {
				rectangle.setX(startX + (rect_width / (angle/10)) - startTile.getWidth() / 2); // Adjust for start tile width
				rectangle.setY(((startY + endY) / 2) - (rect_width / 2));
			}
			rectangle.setWidth(distance);
			rectangle.setHeight(rect_width);
			rectangle.setRotate(angle); // Rotate to match angle
		}

		// Apply gradient and opacity
		rectangle.setFill(gradient);
		double op = 0.5 + Math.random() * 0.4; // Random opacity for variation
		rectangle.setOpacity(op);

		// Add the rectangle to the canvas
		canvas.getChildren().add(rectangle);
	}

	@Override
	public void set_Color(Color color) {
		// TODO Auto-generated method stub

	}

}
