package control;

import javafx.scene.layout.Pane;

/**
 * Factory class for creating different types of game elements.
 */
public class GameElementFactory {
	private Pane canvas;

	/**
	 * Constructor to store the reference to the game board canvas.
	 *
	 * @param canvas The Pane object representing the game board canvas.
	 */
	public GameElementFactory(Pane canvas) {
		this.canvas = canvas;
	}

	/**
	 * Creates and returns a GameElement object based on the specified type.
	 *
	 * @param elementType The type of game element to create ("LADDER" or "SNAKE").
	 * @return An instance of the requested GameElement subclass or null if the type
	 *         is invalid.
	 */
	public GameElement getGameElement(String elementType) {
		if (elementType == null) {
			return null; // Return null for invalid input
		}

		elementType = elementType.toUpperCase(); // Ensure case-insensitive comparison

		if (elementType.equals("LADDER")) {
			return new Ladder_Object(canvas); // Create a Ladder object
		} else if (elementType.equals("SNAKE")) {
			return new Snake_Object(canvas); // Create a Snake object
		} else {
			return null; // Return null for unsupported element types
		}
	}
}
