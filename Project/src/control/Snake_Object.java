package control;

import java.util.Random;

import enums.Colors;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

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
		Image image = new Image("view/Images/Snakes/SnakeHead.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		imageView.setPreserveRatio(true);
//		double angle2 = Math.toDegrees(Math.atan2(startY - (startY-(distance/4))-Math.tan(angle), startX - (startX+Math.cos(angle)*200)));

		imageView.setX(endX - imageView.getFitWidth() / 2);
		imageView.setY(endY - imageView.getFitHeight() / 2);
		Colors[] colors = Colors.values();
		Colors randomColor = colors[rand.nextInt(colors.length)];
		String transparentColor = randomColor.name(); // 50% transparent
//		System.out.println("Start:" + start + "End: " + end + "Color: " + transparentColor);

		cubic.setStroke(color);
		cubic.setStrokeWidth(20);
		cubic.setFill(Color.TRANSPARENT);

		canvas.getChildren().addAll(cubic, imageView);
	}

	@Override
	public void set_Tile(Pane tile) {
		// TODO Auto-generated method stub
		
	}
}

