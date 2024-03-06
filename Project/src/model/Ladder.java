package model;

/**
 * The Ladder class represents a ladder object in the game board.
 * Ladders are used to move players to higher positions on the board.
 * Each ladder has a start and end point, as well as a length.
 */
public class Ladder {

	private int length; // the length of the ladder
	private int start; // the starting position of the ladder
	private int end; // the ending position of the ladder

	/**
	 * Constructor for creating a ladder with specified parameters.
	 * @param startP The starting position of the ladder.
	 * @param endP The ending position of the ladder.
	 * @param length The length of the ladder.
	 */
	public Ladder(int startP, int endP, int length) {
		start = startP;
		end = endP;
		setLength(length);
	}

	/**
	 * Get the starting position of the ladder.
	 * @return The starting position of the ladder.
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Set the starting position of the ladder.
	 * @param start The starting position to set.
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * Get the ending position of the ladder.
	 * @return The ending position of the ladder.
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * Set the ending position of the ladder.
	 * @param end The ending position to set.
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * Get the length of the ladder.
	 * @return The length of the ladder.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Set the length of the ladder.
	 * @param length The length to set.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Override of the toString method to provide a string representation of the ladder.
	 * @return A string representation of the ladder object.
	 */
	@Override
	public String toString() {
		return "Ladder [length=" + length + ", start=" + start + ", end=" + end + "]";
	}

}
