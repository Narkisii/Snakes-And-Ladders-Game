/**
 * 
 */
package model;

/**
 * @author liorf
 *
 */
public abstract class TransferObj {
	private Position startP;
	private Position endP;

	/**
	 * @param startP
	 * @param endP
	 */
	public TransferObj(Position startP, Position endP) {
		super();
		this.startP = startP;
		this.endP = endP;
	}

	/**
	 * @return the startP
	 */
	public Position getStartP() {
		return startP;
	}

	/**
	 * @param startP the startP to set
	 */
	public void setStartP(Position startP) {
		this.startP = startP;
	}

	/**
	 * @return the endP
	 */
	public Position getEndP() {
		return endP;
	}

	/**
	 * @param endP the endP to set
	 */
	public void setEndP(Position endP) {
		this.endP = endP;
	}

}
