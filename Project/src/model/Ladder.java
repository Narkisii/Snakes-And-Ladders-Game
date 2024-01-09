/**
 * 
 */
package model;

/**
 * @author liorf
 *
 */
public class Ladder extends TransferObj {

	private int length; // the length of the ladder

	/**
	 * @param startP
	 * @param endP
	 */
	public Ladder(int[] startP, int[] endP, int length) {
		super(startP, endP);
		setLength(length);
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

}
