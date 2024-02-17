package model;

public class DuplicateError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateError () {
		super("Duplicate question found");
	}

}
