/**
 * 
 */
package model;

/**
 * @author liorf
 *
 */
public abstract class TransferObj {
	private int[] startP;
	private int[] endP;

	/**
	 * @param startP
	 * @param endP
	 */
	public TransferObj(int[] startP, int[] endP) {
		super();
		this.startP = startP;
		this.endP = endP;
	}

	/**
	 * @return the startP
	 */
	public int[] getStartP() {
		return startP;
	}

	/**
	 * @param startP the startP to set
	 */
	public void setStartP(int[] startP) {
		this.startP = startP;
	}

	/**
	 * @return the endP
	 */
	public int[] getEndP() {
		return endP;
	}

	/**
	 * @param endP the endP to set
	 */
	public void setEndP(int[] endP) {
		this.endP = endP;
	}

}
