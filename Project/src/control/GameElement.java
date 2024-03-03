package control;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/**
 * An interface representing elements that can be added to a visual game board.
 */
public interface GameElement {
    /**
     * Adds a visual representation of the element to the game board.
     *
     * @param startX X-coordinate of the element's starting point.
     * @param startY Y-coordinate of the element's starting point.
     * @param endX X-coordinate of the element's ending point (if applicable).
     * @param endY Y-coordinate of the element's ending point (if applicable).
     * @param distance The distance between the start and end points (if applicable).
     */
    void add(double startX, double startY, double endX,double endY, double distance);
    /**
     * Sets the color of the element's visual representation.
     *
     * @param color The desired color.
     */
    public void set_Color(Color color);
    
    /**
     * Sets the starting tile for the element (if applicable).
     *
     * @param tile The Pane object representing the starting tile.
     */
    public void set_Tile(Pane tile);

}
