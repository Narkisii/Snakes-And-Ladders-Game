/**
 * 
 */
package model;

import enums.Colors;
import javafx.scene.paint.Color;

/**
 * @author liorf
 *
 */
public class Snake {//extends TransferObj {
	private int start;
	private int end ;

	private Color color; // Color of the snake will decide the length of it

	/**
	 * @param startP
	 * @param endP
	 */
	//public Snake(Position startP, Position endP, String color) {
	//	super(startP, endP);
		//this.color = color;
	//}
	
	public Snake(int start, int  end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
		
	}

	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
