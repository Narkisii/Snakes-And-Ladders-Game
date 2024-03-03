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
    /**
     * Constructor for creating a Snake_Object.
     * @param canvas The canvas on which the snake object will be drawn.
     */
	public Snake_Object(Pane canvas) {
		this.canvas = canvas;
	}
	
    /**
     * Sets the color of the snake object.
     * @param color The color to set.
     */

	public void set_Color(Color color) {
		this.color = color;
	}
	
	/**
	 * Adds a visual representation of a snake segment to the canvas.
	 * 
	 * @param startX   The starting X-coordinate of the segment.
	 * @param startY   The starting Y-coordinate of the segment.
	 * @param endX     The ending X-coordinate of the segment.
	 * @param endY     The ending Y-coordinate of the segment.
	 * @param distance The distance between the control points of the cubic curve.
	 */
	@Override
	public void add(double startX, double startY, double endX, double endY, double distance) {
		
	    // Create a random number generator for visual variation
		Random rand = new Random();
		
	    // Create the cubic curve representing the snake segment
		CubicCurve cubic = new CubicCurve();
		cubic.setStartX(startX);
		cubic.setStartY(startY);

	    // Calculate control points with some randomness for curvature
		int randomNumber = (rand.nextInt(11) - 5) * 10;// Generate random offset within a range
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
		
	    // Set the ending point of the curve
		cubic.setEndX(endX);
		cubic.setEndY(endY);
		
	    // Style the cubic curve
		cubic.setStroke(color);
		cubic.setStrokeWidth(20);
		cubic.setFill(Color.TRANSPARENT);
		double op = 0.5 + Math.random() * 0.4;
		cubic.setOpacity(op);

	    // Choose a random snake head image
		int rand_Head = rand.nextInt(4) + 1;
		Image image = new Image("/view/Images/Snakes/" + color + "/" + rand_Head + ".png");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		imageView.setPreserveRatio(true);
	    // Position the snake head image
		imageView.setX(endX - imageView.getFitWidth() / 2);
		imageView.setY(endY - imageView.getFitHeight() / 2);
		imageView.setOpacity(op+0.5);// Generate a random opacity value

		
	    // Create a triangle for the snake's tail
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(new Double[] { startX + 10, startY - 10, // Point 1
				startX + 70, startY + 5, // Point 2
				startX + 10, startY + 10 // Point 3
		});
		triangle.setFill(color);
		triangle.setOpacity(op);

	    // Create a small circle at the end of the segment to connect the tail
		Circle c = new Circle(startX + 10, startY, 10, color);
		c.setOpacity(op-0.1);

	    // Add all visual elements to the canvas
		canvas.getChildren().addAll(cubic, imageView, c, triangle);

	}

	@Override
	public void set_Tile(Pane tile) {
		// TODO Auto-generated method stub

	}
}
