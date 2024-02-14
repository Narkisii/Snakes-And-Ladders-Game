/**
 * 
 */
package model;

/**
 * @author liorf
 *
 */
public class Ladder {


	private int length; // the length of the ladder
	

	private int start;
	private int end;
//
//	/**
//	 * @param startP
//	 * @param endP
//	 */
//	public Ladder(int startP, Position endP, int length) {
//		super(startP, endP);
//		setLength(length);
//	}
	
	public Ladder(int startP, int endP,int length) {
		// TODO Auto-generated constructor stub
		this.start = startP;
		this.end = endP;
		setLength(length);

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
	@Override
	public String toString() {
		return "Ladder [length=" + length + ", start=" + start + ", end=" + end + "]";
	}

}
